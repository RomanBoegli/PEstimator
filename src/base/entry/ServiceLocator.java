package base.entry;

import java.util.logging.Logger;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * The singleton instance of this class provide central storage for resources
 * used by the program. It also defines application-global constants, such as
 * the application name.
 * 
 * @author Brad Richards
 */

public class ServiceLocator {
    private static ServiceLocator serviceLocator; // singleton

    // Application-global constants
    final private String APPNAME = "PEstimator";

    // Resources
    private Logger logger;
    
    final private String LogBookDir   = System.getProperty("user.dir") + "\\Logbook\\";
    final private String LogBookFile = LogBookDir + "PEstimator" + "_tbl" + "_%u" + "_%g" + ".log";
    final private String PointBookDir = System.getProperty("user.dir") + "\\PointBook\\";
    final private String PointBookFile = PointBookDir + "Points.txt";		
    
    

    /**
     * Factory method for returning the singleton
     *
     * @return The singleton resource locator
     */
    public static ServiceLocator getServiceLocator() {
        if (serviceLocator == null)
            serviceLocator = new ServiceLocator();
        return serviceLocator;
    }

    
    
    /**
     * Private constructor, because this class is a singleton
     */
    private ServiceLocator() {
        // Currently nothing to do here. We must define this constructor anyway,
        // because the default constructor is public
    }

    
    
    public String getAPPNAME() {
        return APPNAME;
    }

    
    
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
    
    public Logger getLogger() {
        return logger;
    }

   
    
    public String getLogBookDir() {
    	return LogBookDir;
    }
    
    public String getLogBookFile() {
    	return LogBookFile;
    }

    
    
    public String getPointBookDir() {
    	return PointBookDir;
    }
    
    public String getPointBookFile() {
    	return PointBookFile;
    }
    

    
}
