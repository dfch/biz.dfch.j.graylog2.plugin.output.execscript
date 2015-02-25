biz.dfch.j.graylog2.plugin.output.execscript 
============================================

Plugin: biz.dfch.j.graylog2.plugin.output.execscript

d-fens GmbH, General-Guisan-Strasse 6, CH-6300 Zug, Switzerland

This Graylog Output Plugin lets you run arbitrary scripts on a Graylog node.

See [Creating a Graylog2 Output Plugin](http://d-fens.ch/2015/01/07/howto-creating-a-graylog2-output-plugin/) for further description and examples on how to use the plugin.

You can [download the binary](https://drone.io/github.com/dfch/biz.dfch.j.graylog2.plugin.output.execscript/files) [![Build Status](https://drone.io/github.com/dfch/biz.dfch.j.graylog2.plugin.output.execscript/status.png)](https://drone.io/github.com/dfch/biz.dfch.j.graylog2.plugin.output.execscript/latest) at our [drone.io](https://drone.io/github.com/dfch) account, which gets built every time we commit something to the master branch of the repository.

This plugin now works with Graylog v1.

Getting started for users
-------------------------

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