package biz.dfch;

/**
 * This is the plugin. Your class should implement one of the existing plugin
 * interfaces. (i.e. AlarmCallback, MessageInput, MessageOutput)
 */

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

public class DfchTestOutputOutput implements MessageOutput
{
    private static final String DF_PLUGIN_NAME = "d-fens SCRIPT Output";
    private static final String DF_PLUGIN_HUMAN_NAME = "biz.dfch.java.graylog2.plugin.output.execscript1";
    private static final String DF_PLUGIN_DOC_LINK = "https://www.github.com/dfch/";

    private static final String DF_SCRIPT_ENGINE = "DF_SCRIPT_ENGINE";
    private static final String DF_SCRIPT_PATH_AND_NAME = "DF_SCRIPT_PATH_AND_NAME";
    private static final String DF_DISPLAY_SCRIPT_OUTPUT = "DF_DISPLAY_SCRIPT_OUTPUT";

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
                true
                ,
                "Show the script output on the console."
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
