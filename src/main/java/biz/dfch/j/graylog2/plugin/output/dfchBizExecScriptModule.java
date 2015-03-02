package biz.dfch.j.graylog2.plugin.output;

//import com.google.inject.multibindings.MapBinder;
import org.graylog2.plugin.PluginConfigBean;
import org.graylog2.plugin.PluginModule;
//import org.graylog2.plugin.outputs.MessageOutput;

import java.util.Collections;
import java.util.Set;

/**
 * Extend the PluginModule abstract class here to add you plugin to the system.
 */
public class dfchBizExecScriptModule extends PluginModule {
    /**
     * Returns all configuration beans required by this plugin.
     *
     * Implementing this method is optional. The default method returns an empty {@link Set}.
     */
    @Override
    public Set<? extends PluginConfigBean> getConfigBeans()
    {
        return Collections.emptySet();
    }

    @Override
    protected void configure()
    {
        // use addMessageOutput instead of MapBinder/installOuput
        // see https://github.com/Graylog2/graylog2-plugin-archetype/issues/2#issuecomment-75053951
        addMessageOutput(dfchBizExecScript.class, dfchBizExecScript.Factory.class);
//        final MapBinder<String, MessageOutput.Factory<? extends MessageOutput>> outputMapBinder = outputsMapBinder();
//        installOutput(outputMapBinder, dfchBizExecScript.class, dfchBizExecScript.Factory.class);
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
