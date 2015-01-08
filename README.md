biz.dfch.java.graylog2.plugin.output.execscript1 
================================================

Plugin: biz.dfch.java.graylog2.plugin.output.execscript1

d-fens GmbH, General-Guisan-Strasse 6, CH-6300 Zug, Switzerland

This Graylog2 Output Plugin lets you run arbitrary scripts on a Graylog2 node.

See [d-fens WebSite](http://d-fens.ch/2015/01/07/howto-creating-a-graylog2-output-plugin/) for further description and examples on how to use the plugin.

Getting started for users
-------------------------

This project is using Maven and requires Java 7 or higher.

* Clone this repository.
* Run `mvn package` to build a JAR file.
* Optional: Run `mvn jdeb:jdeb` and `mvn rpm:rpm` to create a DEB and RPM package respectively.
* Copy generated jar file in target directory to your Graylog2 server plugin directory (```/opt/graylog2/plugin``` if you are using the Docker image).
* Restart the Graylog2 server.
