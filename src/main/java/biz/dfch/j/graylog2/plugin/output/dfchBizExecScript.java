package biz.dfch.j.graylog2.plugin.output;

import java.io.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Inject;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.google.inject.assistedinject.Assisted;
import org.graylog2.plugin.configuration.Configuration;
import org.graylog2.plugin.configuration.ConfigurationRequest;
import org.graylog2.plugin.configuration.fields.BooleanField;
import org.graylog2.plugin.configuration.fields.ConfigurationField;
import org.graylog2.plugin.configuration.fields.TextField;
import org.graylog2.plugin.Message;
import org.graylog2.plugin.outputs.*;

import org.graylog2.plugin.streams.Stream;
import org.msgpack.annotation.NotNullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the plugin. Your class should implement one of the existing plugin
 * interfaces. (i.e. AlarmCallback, MessageInput, MessageOutput)
 */
public class dfchBizExecScript implements MessageOutput
{
    private static final String DF_PLUGIN_NAME = "d-fens SCRIPT Output";

    private static final String DF_SCRIPT_ENGINE = "DF_SCRIPT_ENGINE";
    private static final String DF_SCRIPT_PATH_AND_NAME = "DF_SCRIPT_PATH_AND_NAME";
    private static final String DF_DISPLAY_SCRIPT_OUTPUT = "DF_DISPLAY_SCRIPT_OUTPUT";
    private static final String DF_SCRIPT_CACHE_CONTENTS = "DF_SCRIPT_CACHE_CONTENTS";

    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private Configuration configuration;
    private String streamTitle;

    private static final ScriptEngineManager _scriptEngineManager = new ScriptEngineManager();
    private ScriptEngine _scriptEngine;
    private ScriptContext _scriptContext;
    StringWriter _stringWriter = new StringWriter();
    private File _file;

    private static final Logger LOG = LoggerFactory.getLogger(dfchBizExecScript.class);

    @Inject
    public dfchBizExecScript
            (
                    @NotNullable @Assisted Stream stream,
                    @NotNullable @Assisted Configuration configuration
            )
            throws MessageOutputConfigurationException
    {
        try
        {
            LOG.debug("Verifying configuration ...");

            this.configuration = configuration;

            streamTitle = stream.getTitle();
            if(null == streamTitle || streamTitle.isEmpty())
            {
                throw new MessageOutputConfigurationException(String.format("streamTitle: Parameter validation FAILED. Value cannot be null or empty."));
            }
            
            LOG.trace("DF_SCRIPT_ENGINE         : %s\r\n", configuration.getString("DF_SCRIPT_ENGINE"));
            LOG.trace("DF_SCRIPT_PATH_AND_NAME  : %s\r\n", configuration.getString("DF_SCRIPT_PATH_AND_NAME"));
            LOG.trace("DF_DISPLAY_SCRIPT_OUTPUT : %b\r\n", configuration.getBoolean("DF_DISPLAY_SCRIPT_OUTPUT"));
            LOG.trace("DF_SCRIPT_CACHE_CONTENTS : %b\r\n", configuration.getBoolean("DF_SCRIPT_CACHE_CONTENTS"));

            _file = new File(configuration.getString("DF_SCRIPT_PATH_AND_NAME"));
            _scriptEngine = _scriptEngineManager.getEngineByName(configuration.getString("DF_SCRIPT_ENGINE"));
            _scriptContext = _scriptEngine.getContext();

            isRunning.set(true);

            LOG.info("Verifying configuration SUCCEEDED.");
        }
        catch(Exception ex)
        {
            isRunning.set(false);

            LOG.error("*** " + DF_PLUGIN_NAME + "::write() - Exception\r\n" + ex.getMessage() + "\r\n");
            ex.printStackTrace();
        }
    }

    @Override
    public void stop()
    {
        isRunning.set(false);
    }

    @Override
    public boolean isRunning()
    {
        return isRunning.get();
    }

    @Override
    public void write(Message msg) throws Exception
    {
        if(!isRunning.get())
        {
            return;
        }

        try
        {
            _stringWriter.getBuffer().setLength(0);
            _scriptContext.setWriter(_stringWriter);
            _scriptEngine.put("message", msg);
            if(!configuration.getBoolean("DF_SCRIPT_CACHE_CONTENTS"))
            {
                _file = new File(configuration.getString("DF_SCRIPT_PATH_AND_NAME"));
            }
            Reader _reader = new FileReader(_file);
            _scriptEngine.eval(_reader);
            if(configuration.getBoolean("DF_DISPLAY_SCRIPT_OUTPUT")) {
                LOG.info(String.format("%s\r\n", _stringWriter.toString()));
                LOG.trace("%s\r\n", _stringWriter.toString());
            }

        }
        catch(Exception ex)
        {
            LOG.error("*** " + DF_PLUGIN_NAME + "::write() - Exception");
            ex.printStackTrace();
        }
    }

    @Override
    public void write(List<Message> messages) throws Exception
    {
        if(!isRunning.get()) return;

        for(Message msg : messages)
        {
            write(msg);
        }
    }

    public static class Config extends MessageOutput.Config
    {
        @Override
        public ConfigurationRequest getRequestedConfiguration()
        {
            final ConfigurationRequest configurationRequest = new ConfigurationRequest();

            configurationRequest.addField(new TextField
                            (
                                    DF_SCRIPT_ENGINE
                                    ,
                                    "Script Engine"
                                    ,
                                    "javascript"
                                    ,
                                    "Specify the name of the script engine to use."
                                    ,
                                    ConfigurationField.Optional.NOT_OPTIONAL
                            )
            );
            configurationRequest.addField(new TextField
                            (
                                    DF_SCRIPT_PATH_AND_NAME
                                    ,
                                    "Script Path"
                                    ,
                                    "/opt/graylog2/plugin/bizDfchMessageOutputScript.js"
                                    ,
                                    "Specify the full path and name of the script to execute."
                                    ,
                                    ConfigurationField.Optional.NOT_OPTIONAL
                            )
            );
            configurationRequest.addField(new BooleanField
                            (
                                    DF_DISPLAY_SCRIPT_OUTPUT
                                    ,
                                    "Show script output"
                                    ,
                                    false
                                    ,
                                    "Show the script output on the console."
                            )
            );
            configurationRequest.addField(new BooleanField
                            (
                                    DF_SCRIPT_CACHE_CONTENTS
                                    ,
                                    "Cache script contents"
                                    ,
                                    true
                                    ,
                                    "Cache the contents of the script upon plugin initialisation."
                            )
            );
            return configurationRequest;
        }
    }

    public interface Factory extends MessageOutput.Factory<dfchBizExecScript>
    {
        @Override
        dfchBizExecScript create(Stream stream, Configuration configuration);

        @Override
        Config getConfig();

        @Override
        Descriptor getDescriptor();
    }
    public static class Descriptor extends MessageOutput.Descriptor
    {
        public Descriptor()
        {
            super((new dfchBizExecScriptMetadata()).getName(), false, "", (new dfchBizExecScriptMetadata()).getDescription());
        }
    }
}

/**
 *
 *
 * Copyright 2015 Ronald Rink, d-fens GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
