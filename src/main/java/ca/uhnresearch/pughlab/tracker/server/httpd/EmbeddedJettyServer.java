package ca.uhnresearch.pughlab.tracker.server.httpd;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.log.JavaUtilLog;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.xml.XmlConfiguration;

public class EmbeddedJettyServer {
	
	/**
	 * The default config file name
	 */
	private static final String DEFAULT_CONFIG_FILE = "jetty.xml";
	
	private URL configXMLURL;
	
    private static final Logger LOG = Logger.getLogger(EmbeddedJettyServer.class.getName());

    private Server server;

	/**
	 * Main entry point to the program, used to initialize an embedded
	 * Jetty server, start it, and then wait.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
    
        LoggingUtil.config();
        Log.setLog(new JavaUtilLog());

        EmbeddedJettyServer main = new EmbeddedJettyServer();
        main.initalizeConfig(args);
        
        main.start();
        main.waitForInterrupt();
    }
	
	/**
	 * Initializes the server instance with an appropriate configuration file
	 * location. 
	 * 
	 * @param args
	 * @throws MalformedURLException
	 */
	private void initalizeConfig(String[] args) throws MalformedURLException {

		String configFileName;
		if (args.length == 1) {
			configFileName  = args[0];
		} else {
	        String config = System.getProperty("JETTY_CONFIG");
	        if (config == null) {
	        	configFileName = DEFAULT_CONFIG_FILE;
	        } else {
	        	configFileName = config;
	        }
		}
		File configFile = new File(configFileName);
		if (! configFile.isAbsolute()) {
			String userDir = System.getProperty("user.dir");
			configFile = new File(userDir, configFileName);
		}
        
        // This should be a directory where a config file *might* exist. If not,
        // call through to the classpath. 
        
        if (configFile.exists()) {
        	configXMLURL = configFile.toURI().toURL();
        } else {
        	configXMLURL = this.getClass().getResource("/" + DEFAULT_CONFIG_FILE);
        }
        
        if (configXMLURL == null) {
        	LOG.severe("Failed to find configuration file: exiting");
        	System.exit(1);
        }
	}

	/**
	 * Starts the server from the passed configuration URL
	 * @throws Exception
	 */
    public void start() throws Exception {
        
		LOG.info("Configuring web server from: " + configXMLURL);
		Resource input = new FileResource(configXMLURL);
	    XmlConfiguration configuration = new XmlConfiguration(input.getInputStream());
	    server = (Server)configuration.configure();
	    input.close();

        // Start Server
        server.start();

    }

    public void stop() throws Exception {
        server.stop();
    }

    /**
     * Cause server to keep running until it receives a Interrupt.
     * <p>
     * Interrupt Signal, or SIGINT (Unix Signal), is typically seen as a result of a kill -TERM {pid} or Ctrl+C
     * @throws InterruptedException if interrupted
     */
    public void waitForInterrupt() throws InterruptedException {
        server.join();
    }
}
