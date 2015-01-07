package biz.dfch;

import org.graylog2.plugin.PluginMetaData;
import org.graylog2.plugin.Version;
import java.net.URI;

/**
 * Implement the PluginMetaData interface here.
 */
public class DfchTestOutputMetadata implements PluginMetaData
{
    @Override
    public String getUniqueId()
    {
        return "258d90ff-4dbe-4569-86d0-c55446b6550f";
    }
    @Override
    public String getName()
    {
        return "d-fens SCRIPT Output";
    }
    @Override
    public String getAuthor()
    {
        return "Ronald Rink, d-fens GmbH";
    }
    @Override
    public URI getURL()
    {
        return URI.create("http://www.d-fens.ch");
    }
    @Override
    public Version getVersion()
    {
        return new Version(1, 0, 0);
    }
    @Override
    public String getDescription()
    {
        return "d-fens SCRIPT Output. With this plugin you can call arbitrary scripts from within Graylog2.";
    }
    @Override
    public Version getRequiredVersion()
    {
        return new Version(0, 90, 0);
    }
}
