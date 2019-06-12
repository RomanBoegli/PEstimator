package base.app;

/**
 * @author Roman Boegli
 */


public class Cheater {
	
	private static Cheater instance ; //= null;
	private Double nPi = 0.0;
	private int nRuns = 1;
	
	
	/**
	 * private constructor
	 */
	private Cheater() {
		//nothing needed here
	}
	
	
	
	/**
	 * Factory method returning a singleton of the object Cheater
	 * 
	 * @return the one and only instance of the Cheater object
	 */
	public static Cheater getInstance() {
		
		if(instance == null) {
	         instance = new Cheater();
	      }
	    return instance;
	}
	
	
	
	/**
	 * Approximates Pi with each call of this method more precisely.
	 */
	public void approximatePi() {
		
		double nPi = this.nPi / 4;
		
		nPi += (Math.pow(-1.0,nRuns+1.0) / ((nRuns * 2.0) - 1.0));
		
		nRuns++;
		
		this.nPi  = nPi * 4;
		
	}
	
	
	/**
	 * Approximates Pi according to the number of runs given. 
	 * The more runs, the higher the accuracy of Pi.
	 * 
	 * @param nRuns number of runs to approximate Pi
	 * 
	 * @return approximated Pi as an Double object
	 */
    public static Double approximatePi(double nRuns) {
		
		double nReturn = 0;
		
		for( double i=nRuns ; i>0 ; i--) {
			
			nReturn += Math.pow(-1, i+1) / (2*i-1);
			if( i==1 ) {
				nReturn *= 4;
			}
		}

		return nReturn;
	}
	
    
	
	/**
     * Getter for the current (approximated) Pi
	 *
	 * @return approximated Pi as an Double object
	 */
	public Double getPi() {
		return this.nPi;
	}
	
	

	
}
