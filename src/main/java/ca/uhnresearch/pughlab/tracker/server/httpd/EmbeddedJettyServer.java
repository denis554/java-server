package ca.uhnresearch.pughlab.tracker.server.httpd;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.log.JavaUtilLog;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.xml.XmlConfiguration;

public class EmbeddedJettyServer {
	
	private static final String CONFIG_FILE = "tracker.xml";
	
	public static void main(String[] args) throws Exception
    {
        LoggingUtil.config();
        Log.setLog(new JavaUtilLog());

        EmbeddedJettyServer main = new EmbeddedJettyServer();

        main.start();
        main.waitForInterrupt();
    }

    private static final Logger LOG = Logger.getLogger(EmbeddedJettyServer.class.getName());

    private Server server;

    public void start() throws Exception
    {

        System.setProperty("org.apache.jasper.compiler.disablejsr199", "false");
        
        File configFile;
        String config = System.getProperty("PORTAL_CONFIG");
        if (config == null) {
        	config = System.getProperty("user.dir");
        	configFile = new File(config);
        	configFile = new File(configFile, CONFIG_FILE);
        } else {
        	configFile = new File(config);
        }
        
        // This should be a directory where a config file *might* exist. If not,
        // call through to the classpath. 
        
        URL configXMLURL;
        
        if (configFile.exists()) {
        	configXMLURL = configFile.toURI().toURL();
        } else {
        	configXMLURL = this.getClass().getResource("/" + CONFIG_FILE);
        }
        
        if (configXMLURL == null) {
        	LOG.severe("Failed to find configuration file: exiting");
        	System.exit(1);
        }
        
		LOG.info("Configuring web server from: " + configXMLURL);
		Resource input = new FileResource(configXMLURL);
	    XmlConfiguration configuration = new XmlConfiguration(input.getInputStream());
	    server = (Server)configuration.configure();
	    input.close();

        // Start Server
        server.start();

    }

    public void stop() throws Exception
    {
        server.stop();
    }

    /**
     * Cause server to keep running until it receives a Interrupt.
     * <p>
     * Interrupt Signal, or SIGINT (Unix Signal), is typically seen as a result of a kill -TERM {pid} or Ctrl+C
     * @throws InterruptedException if interrupted
     */
    public void waitForInterrupt() throws InterruptedException
    {
        server.join();
    }
}
