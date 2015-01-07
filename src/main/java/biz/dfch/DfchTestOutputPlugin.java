package biz.dfch;

import java.util.Collection;
import org.graylog2.plugin.Plugin;
import org.graylog2.plugin.PluginModule;

import java.util.Collections;

/**
 * Implement the Plugin interface here.
 */
public class DfchTestOutputPlugin implements Plugin {
    @Override
    public Collection<PluginModule> modules () {
        return Collections.<PluginModule>singleton(new DfchTestOutputModule());
    }
}
