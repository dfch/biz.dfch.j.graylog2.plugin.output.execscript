biz.dfch.j.graylog2.plugin.output.execscript 
============================================

Plugin: biz.dfch.j.graylog2.plugin.output.execscript

d-fens GmbH, General-Guisan-Strasse 6, CH-6300 Zug, Switzerland

This Graylog Output Plugin lets you run arbitrary scripts on a Graylog node.

See [Creating a Graylog2 Output Plugin](http://d-fens.ch/2015/01/07/howto-creating-a-graylog2-output-plugin/) for further description and examples on how to use the plugin.

You can [download the binary](https://github.com/dfch/biz.dfch.j.graylog2.plugin.output.execscript/releases) [![Build Status](https://drone.io/github.com/dfch/biz.dfch.j.graylog2.plugin.output.execscript/status.png)](https://drone.io/github.com/dfch/biz.dfch.j.graylog2.plugin.output.execscript/latest).

This plugin works with Graylog v1.

Getting started for users
-------------------------
To use this plugin, copy the JAR into the Graylog plugin directory (which is defined in `Graylog.conf`) and restart the Graylog node and make sure you deploy the plugin to every Graylog node in your environment.

You can then create scripts in any JSR.223 supported script engine (by default support for `javascript`, `Python` as `JPython` (v2.7-rc2) and Groovy (2.4.3) is installed). When configuring the plugin, you have to specify the full path to a script file that should be executed (and the name of the engine (i.e. `javascript`, `python` and `groovy`). 
Every time the specified script is executed it will be passed the `message` of type [`org.graylog2.plugin.Message`](https://github.com/Graylog2/graylog2-server/blob/master/graylog2-plugin-interfaces/src/main/java/org/graylog2/plugin/Message.java) as a variable into the script scope from where you can process it according to your needs. 

Getting started for developers
------------------------------

This project is using Maven and requires Java 7 or higher.

* Clone this repository.
* Run `mvn package` to build a JAR file.
* Optional: Run `mvn jdeb:jdeb` and `mvn rpm:rpm` to create a DEB and RPM package respectively.
* Copy generated jar file in target directory to your Graylog2 server plugin directory (```/opt/graylog2/plugin``` if you are using the Docker image).
* Restart the Graylog server.

Other Plugins
-------------

* [biz.dfch.j.graylog2.plugin.alarm.execscript](https://github.com/dfch/biz.dfch.j.graylog2.plugin.alarm.execscript) 
  
  This is an [AlarmCallback](https://www.graylog2.org/resources/documentation/general/plugins) plugin that does essentially the same as the output plugin, but lets you run scripts based on alarm conditions (similar to [graylog2-plugin-alarmcallback-exec](https://github.com/lennartkoopmann/graylog2-plugin-alarmcallback-exec))

* [biz.dfch.j.graylog2.plugin.filter.auditlog](https://github.com/dfch/biz.dfch.j.graylog2.plugin.filter.auditlog) 
  
  This is a [Filter](https://www.graylog2.org/resources/documentation/general/plugins) plugin that does essentially process every message it encounters and sends it to another location.

* Check our web site and search for *Graylog* or *Graylog2* to find more information and more plugins.