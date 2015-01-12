package biz.dfch.j.graylog2.plugin.output;

import java.io.*;
import java.util.List;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.graylog2.plugin.configuration.Configuration;
import org.graylog2.plugin.configuration.ConfigurationRequest;
import org.graylog2.plugin.configuration.fields.BooleanField;
import org.graylog2.plugin.configuration.fields.ConfigurationField;
import org.graylog2.plugin.configuration.fields.TextField;
import org.graylog2.plugin.Message;
import org.graylog2.plugin.outputs.*;

/**
 * This is the plugin. Your class should implement one of the existing plugin
 * interfaces. (i.e. AlarmCallback, MessageInput, MessageOutput)
 */
public class dfchBizExecScript implements MessageOutput
{
    private static final String DF_PLUGIN_NAME = "d-fens SCRIPT Output";
    private static final String DF_PLUGIN_HUMAN_NAME = "biz.dfch.java.graylog2.plugin.output.execscript1";
    private static final String DF_PLUGIN_DOC_LINK = "https://github.com/dfch/biz.dfch.java.graylog2.plugin.output.execscript1";

    private static final String DF_SCRIPT_ENGINE = "DF_SCRIPT_ENGINE";
    private static final String DF_SCRIPT_PATH_AND_NAME = "DF_SCRIPT_PATH_AND_NAME";
    private static final String DF_DISPLAY_SCRIPT_OUTPUT = "DF_DISPLAY_SCRIPT_OUTPUT";
    private static final String DF_SCRIPT_CACHE_CONTENTS = "DF_SCRIPT_CACHE_CONTENTS";

    private boolean _isRunning = false;
    private Configuration _configuration;

    private ScriptEngineManager _scriptEngineManager;
    private ScriptEngine _scriptEngine;
    private ScriptContext _scriptContext;
    private File _file;

    @Override
    public void initialize(final Configuration configuration)
    {
        try
        {
            String s = "*** " + DF_PLUGIN_NAME + "::initialize()";
            System.out.println(s);

            _configuration = configuration;
            _isRunning = true;

            System.out.printf("DF_SCRIPT_ENGINE         : %s\r\n", _configuration.getString("DF_SCRIPT_ENGINE"));
            System.out.printf("DF_SCRIPT_PATH_AND_NAME  : %s\r\n", _configuration.getString("DF_SCRIPT_PATH_AND_NAME"));
            System.out.printf("DF_DISPLAY_SCRIPT_OUTPUT : %b\r\n", _configuration.getBoolean("DF_DISPLAY_SCRIPT_OUTPUT"));
            System.out.printf("DF_SCRIPT_CACHE_CONTENTS : %b\r\n", _configuration.getBoolean("DF_SCRIPT_CACHE_CONTENTS"));

            _scriptEngineManager = new ScriptEngineManager();
            _file = new File(_configuration.getString("DF_SCRIPT_PATH_AND_NAME"));

        }
        catch(Exception ex)
        {
            _isRunning = false;

            System.out.println("*** " + DF_PLUGIN_NAME + "::write() - Exception");
            ex.printStackTrace();
        }

        return;
    }

    @Override
    public void stop()
    {
        _isRunning = false;
    }

    @Override
    public boolean isRunning()
    {
        return _isRunning;
    }

    @Override
    public void write(Message msg) throws Exception
    {
        if(!_isRunning) return;

        try
        {
            _scriptEngine = _scriptEngineManager.getEngineByName(_configuration.getString("DF_SCRIPT_ENGINE"));
            _scriptContext = _scriptEngine.getContext();
            StringWriter stringWriter = new StringWriter();
            _scriptContext.setWriter(stringWriter);
            _scriptEngine.put("message", msg);
            if(!_configuration.getBoolean("DF_SCRIPT_CACHE_CONTENTS"))
            {
                _file = new File(_configuration.getString("DF_SCRIPT_PATH_AND_NAME"));
            }
            Reader _reader = new FileReader(_file);
            _scriptEngine.eval(_reader);
            if(_configuration.getBoolean("DF_DISPLAY_SCRIPT_OUTPUT"))
            {
                System.out.printf("%s\r\n", stringWriter.toString());
            }

        }
        catch(Exception ex)
        {
            System.out.println("*** " + DF_PLUGIN_NAME + "::write() - Exception");
            ex.printStackTrace();
        }

        return;
    }

    @Override
    public void write(List<Message> messages) throws Exception
    {
        if(!_isRunning) return;

        for(Message msg : messages)
        {
            write(msg);
        }

        return;
    }

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
                                "/opt/graylog2/plugin/helloworld.js"
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

    @Override
    public String getName()
    {
        return DF_PLUGIN_NAME;
    }

    @Override
    public String getHumanName()
    {
        return DF_PLUGIN_HUMAN_NAME;
    }

    @Override
    public String getLinkToDocs()
    {
        return DF_PLUGIN_DOC_LINK;
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
