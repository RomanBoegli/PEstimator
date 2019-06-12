package base.app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import base.abstrac.Model;
import base.entry.ServiceLocator;

/**
 * @author Roman Boegli
 */

public class AppModel extends Model{
		
	//---------------------------------------
	//GLOBAL VARIABLES
	//---------------------------------------
	
	ServiceLocator serviceLocator;
	ArrayList<Point> PointSet = new ArrayList<>();
	ArrayList<Point> relevantPoints = new ArrayList<>();
	double nRadiusSquared; 
	boolean inCheatMode = false;
	
	
	
	/**
	 * Constructor
	 */
	public AppModel() {
        
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("Application model initialized");
    }
	
	


	/**
	 * Stores the committed point in an ArrayList considered to be
	 * the PointSet. If the point is relevant, it will additionally be
	 * stored in a second ArrayList considered to be the relevantPoints
	 * 
	 * @param p an object of type Point
	 * @param nRadius radius of the quarter circle shown in 
	 *                the painting.
	 */
	public void store(Point p, double nRadius) {
        
		PointSet.add(p);
		
		if (isRelevant(p, nRadius)) {
			relevantPoints.add(p);
		}
	}
	
	
	

	
	/**
	 * Returns a one line string which is tab separated including the most
	 * important information about one Point. 
	 * 
	 * @param  p an object of type Point
	 * 
	 * @return tab separated string with point describing information in it
	 */
	public String getInfo(Point p) {
		
		String cRelevant = "N";
		String cReturn = null;
		
		if (relevantPoints.contains(p)) {
			cRelevant = "Y";
		}
		
		cReturn =  p.nID + "\t" + p.x + "\t" + p.y + "\t" +  cRelevant;
		
		return cReturn;
		
	}
	
	
	/**
	 * Appends a string to the text file stored in 
	 * «current project path + "\PointBook\Points.txt"».
	 * 
	 * @param c a string, ideally ending with a line break (CRLF)
	 * 
	 */
	public void writeToPointBook(String c) {
		
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter(serviceLocator.getPointBookFile(),true));		
			fw.write(c);
			fw.flush();
			fw.close();
			fw =null;
		} catch (IOException e) { 
			e.printStackTrace();
		}
	
	}
	
	
	
	/**
	 * Checks if a Point is placed within the quarter circle 
	 * (relevant) or if it is placed outside of the quarter 
	 * circle (not relevant).
	 * 
	 * @param p an object of type Point
	 * @param nRadius the radius of the quarter circle shown in the
	 * 		 		 padding
	 * 
	 * @return true if the Point is relevant, false if not
	 */
    private boolean isRelevant(Point p, double nRadius) {
    	
    	//assuming point is not relevant
    	boolean lReturn = false;
    	double nCheckSum = 0;
    	
    	//check if point is relevant ~~> (x^2 + y^2) < (R^2)
		nCheckSum = Math.pow(p.x, 2) + Math.pow(p.y, 2); 

		if (nCheckSum < Math.pow(nRadius, 2)) {
    		lReturn = true;
    	}
		
    	return lReturn;
    }
    
    

	
    
	/**
	 * Calculates Pi and returns it as an Double object. Calculation
	 * depends on whether CheatMode is on or not. If CheatMode is on,
	 * Pi gets approximated according to an unbound mathematical 
	 * approach, accessible in the Cheater class. If CheatMode is off,
	 * Pi gets approximated according to current ratio between relevant
	 * points and number of total points. Points which are within the
	 * quarter circle are considered as relevant.
	 *  
	 * @return approximated Pi as an Double object
	 */
    public Double getCurrentPi() {
    	
    	double nPoints = PointSet.size();
    	double nRelevantPoints = relevantPoints.size();
    	double nReturn = 0; 	
    	
    	if (inCheatMode) {
    		Cheater cheat = Cheater.getInstance();
    		cheat.approximatePi();
    		nReturn =  cheat.getPi();
    	} else {
    		nReturn = nRelevantPoints / nPoints * 4;
    	}
    	
    	return nReturn;
    }
    
    
    
    

	
       
	/**
	 * adjusts the x-coordinate of all Points existing
	 * according to the ratio of the alteration of the
	 * stage's window width.
	 * 
	 * @param nRatio a double describing the relative 
	 *               alteration (ratio) of the stage's 
	 *               window width (eg. 1.15 when 
	 *               enlarging the window horizontally by
	 *               15%)
	 */
    public void adjustCoordinate_X(double nRatio) {
       	
    	//shift X-coordinate proportionally
    	Iterator<Point> handler = PointSet.iterator();
    	while ( handler.hasNext() ) {
    		Point p = handler.next();
    		p.x = p.x * nRatio;
    	}
    	
    }
    
	/**
	 * adjusts the y-coordinate of all Points existing
	 * according to the ratio of the alteration of the
	 * stage's window height.
	 * 
	 * @param nRatio a double describing the relative 
	 *               alteration (ratio) of the stage's 
	 *               window height (eg. 0.98 when 
	 *               reducing the window vertically by
	 *               2%)
	 */
    public void adjustCoordinate_Y(double nRatio) {
       	
    	//shift Y-coordinate proportionally
    	Iterator<Point> handler = PointSet.iterator();
    	while ( handler.hasNext() ) {
    		Point p = handler.next();
    		p.y = p.y * nRatio;
    	}
    	
    }
     
    
    
	/**
	 * After the window was resized and all points have
	 * been replaced in the painting, their relevance of
	 * being inside the quarter circle or not has to be
	 * reassessed. Hence, this method will loop through
	 * all the points existing and reassess their
	 * relevance individually since this is crucial for
	 * the Pi-calculation. 
	 * 
	 * @param nRadius radius of the quarter circle shown in 
	 *                the painting.
	 */
   public void reassessRelevance(double nRadius) {
	   
	   ArrayList<Point> Set = new ArrayList<Point>();
	   
	   //reassess relevance of all existing points
	   Iterator<Point> handler = PointSet.iterator();
	   while ( handler.hasNext() ) {
		   Point p = handler.next();
	   	   if (isRelevant(p, nRadius)) {
	   		Set.add(p);
	   	   }
	   }
	   
	   relevantPoints = Set;
	   
   }
    
    
    
    
	
}
