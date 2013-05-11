package org.slstudio.acs.util;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-11
 * Time: ÏÂÎç3:56
 */
public class ActiveMQStarter {
    private static final Log log = LogFactory.getLog(ActiveMQStarter.class);

    private Properties systemProperties = new Properties();
    private String homeDir = null;
    private String configUri = "broker:(tcp://localhost:61616)?useJmx=false&persistent=false";
    private boolean fork = false;

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public String getHomeDir() {
        return homeDir;
    }

    public void setHomeDir(String homeDir) {
        this.homeDir = homeDir;
    }

    public String getConfigUri() {
        return configUri;
    }

    public void setConfigUri(String configUri) {
        this.configUri = configUri;
    }

    public void start() throws Exception{
        setSystemProperties();
        log.info("Loading broker configUri: " + configUri);

        final BrokerService broker = BrokerFactory.createBroker(configUri);
        if (fork) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        broker.start();
                        waitForShutdown(broker);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            broker.start();
            waitForShutdown(broker);
        }

    }
    /***
     * Wait for a shutdown invocation elsewhere
     *
     * @throws Exception
     */
    protected void waitForShutdown(BrokerService broker) throws Exception {
        final boolean[] shutdown = new boolean[] {
                false
        };
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                synchronized (shutdown) {
                    shutdown[0] = true;
                    shutdown.notify();
                }
            }
        });

        // Wait for any shutdown event
        synchronized (shutdown) {
            while (!shutdown[0]) {
                try {
                    shutdown.wait();
                } catch (InterruptedException e) {
                }
            }
        }

        // Stop broker
        broker.stop();
    }

    protected void setSystemProperties() {
        // Set the default properties
        System.setProperty("activemq.base",homeDir);
        System.setProperty("activemq.home", homeDir);
        System.setProperty("org.apache.activemq.UseDedicatedTaskRunner", "true");
        System.setProperty("org.apache.activemq.default.directory.prefix", homeDir);
        System.setProperty("derby.system.home", homeDir);
        System.setProperty("derby.storage.fileSyncTransactionLog", "true");

        // Overwrite any custom properties
        System.getProperties().putAll(systemProperties);
    }
}
