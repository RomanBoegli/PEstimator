package base.app;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Roman Boegli
 */

public class Painting extends Canvas{
	
	private static long nCount;
	public long nID;
	public double nWidth;
	public double nHeight;
	
	
	
	
	
	// Constructro 1 (default)
	public Painting() {
		
        super();
				
        this.nID = ++nCount;
		this.nWidth = getWidth(); //<~~ get current width of canvas, cause app is resizable
		this.nHeight = getHeight(); //<~~ get current height of canvas, cause app is resizable
								
		drawBase();	
	}
	
	
	// Constructor 2 (not in use but cool)
	@Deprecated
	public Painting(double w, double h) {
			
        super();
				
        this.nID = ++nCount;
		this.nWidth = w; 
		this.nHeight = h; 
							
		drawBase();		
	}
	

	
	
	
	
	
	/**
	 * Draws the base onto the GraphicsContext of the
	 * Painting object. The base consists of a black frame
	 * (strokeRect), a yellow rectangle (fillRect) and a
	 * brown Oval (fillOval).
	 */
	public void drawBase() {
		
        double width = getWidth();
        double height = getHeight();
       
        GraphicsContext gc = getGraphicsContext2D();

        resize(width, height);
        gc.setStroke(Color.BLACK);
		gc.strokeRect(0, 0, width, height);
		gc.setFill(Color.YELLOW);
		gc.fillRect(0, 0, width, height);
		
		gc.setFill(Color.BROWN);
		gc.fillOval(-width, -height, width*2, height*2);
		
    }
	
	
	
	
	
	public double getPaintingWidth() {
		return this.nWidth;
	}
	
	public double getPaintingHeight() {
		return this.nHeight;
	}
	
	
}
