package base.app;

/**
 * @author Roman Boegli
 */

public class Point {
	
	private static long nCount;
	long nID;
	double x;
	double y;
	double radius;
	
	public Point(double x, double y) {

		this.nID = ++nCount;
		this.x = x;
		this.y = y;
		this.radius = 5.0;
		
	}
	
	public double getX() {
		return x;
	}


	public void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return y;
	}


	public void setY(double y) {
		this.y = y;
	}


	public double getRadius() {
		return radius;
	}


	public void setRadius(double radius) {
		this.radius = radius;
	}
	

	@Deprecated
	public String getInfo() {
		
		String cReturn = "";
				
		cReturn = this.nID + "\t" + this.x + "\t" + this.y + "\t" ;
		
		return cReturn;
		
	}
	
	
	
}
