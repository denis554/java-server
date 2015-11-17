## java-server

Provides a Jetty-based server container for the tracker, cbioPortal, and other 
Java-based web applications.

### Usage

Designed to run as a daemon task. Use as:

    java -jar java-server.jar [config_file]
    
If `config_file` is provided, it is a Jetty configuration file which sets up servlets, 
war files, JNDI settings, and anything else that's needed. The default config file is called `jetty.xml`
(although a Java property `JETTY_CONFIG` can override this) and the default location
is the working directory, 

### Now available at Maven Central

    <dependency>
        <groupId>ca.uhnresearch.pughlab</groupId>
        <artifactId>java-server</artifactId>
        <version>1.0.3</version>
    </dependency>

### Includes:

The package includes the following Jetty components as standard:

 * `org.eclipse.jetty:jetty-annotations`
 * `org.eclipse.jetty:jetty-webapp`
 * `org.eclipse.jetty:jetty-xml`
 * `org.eclipse.jetty:jetty-jmx`
 * `org.eclipse.jetty:jetty-servlet`
 * `org.eclipse.jetty.websocket:websocket-server`
 * `org.eclipse.jetty:apache-jsp`
 * `org.eclipse.jetty:apache-jstl`
 * `mysql:mysql-connector-java`

### To build:

    mvn test site package

