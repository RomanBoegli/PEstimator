package base.app;


import java.util.Random;
import base.abstrac.Controller;
import base.entry.ServiceLocator;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

/**
 * @author Roman Boegli
 */

public class AppController extends Controller<AppModel, AppView>{
	
	//---------------------------------------
	//GLOBAL VARIABLES
	//---------------------------------------	
	
	ServiceLocator serviceLocator;
	Thread PointMachine;
	boolean lStopp;
	boolean lAlreadyStarted;
	volatile boolean lWait;
	
	
	
	/**
 	 * public constructor, handling following events:
 	 *  - displaying value of slider after slider was moved
	 *  - starting process of adding new points to the painting
	 *  - terminating process of adding new points to the painting
	 *  - rearranging controls after window width was changed
	 *  - rearranging controls after window height was changed
	 *  
	 *  @param model object of type AppModel
	 *  @param view object of type AppView
	 *  
	 */
	public AppController(AppModel model, AppView view) {
		
		super(model, view);
		
		
	    //provide resources
		serviceLocator = ServiceLocator.getServiceLocator();  
	    
	    
		//update the current value of the slider (=speed)
		view.slider.valueProperty().addListener(
    		
    		(nID, nOldValue , nNewValue) -> {
    			Double nValue =  ((double) (Math.round(nNewValue.doubleValue()*100000)) / 100000);
    			Platform.runLater( 
    					()-> { view.txtSpeed.setText(nValue.toString()); 
    					       view.mediaPlayer.setRate(nValue.doubleValue() / 5);  });
    		}	
		);
   
		
	    //start the process of adding new points
		view.cmdStart.setOnAction( (ActionEvent) -> { if(!lAlreadyStarted){TurnPointMachineOn();}  
													  serviceLocator.getLogger().info("start adding points");   });
	    
		
	    //terminate the process of adding new points
	    view.cmdEnd.setOnAction( (ActionEvent) -> {  TurnPointMachineOff();
	    										     serviceLocator.getLogger().info("stopp adding points");  });
	     
	    
	    
		//toggle cheat mode
		view.chkCheat.selectedProperty().addListener(
    		
    		(nID, nOldValue , nNewValue) -> { model.inCheatMode = view.chkCheat.isSelected();
    		 								  serviceLocator.getLogger().info("cheat mode: " + model.inCheatMode);  }	
		
		);
	    	    
    
		//toggle sound
		view.chkSound.selectedProperty().addListener(
    		
			(ID, OldValue , NewValue) -> {
	    			
	    			double nVol_off = 0.0 ;
	    			double nVol_on = 1.0;
	    			
	    			Platform.runLater(
	    					()-> { double nVol = view.chkSound.isSelected() ? nVol_on : nVol_off ;
	    						   view.mediaPlayer.setVolume(nVol); });
	    			
	    			serviceLocator.getLogger().info("sound: " + view.chkSound.isSelected());
	    			
	    	}	
		);
    
		
		
		//listen for shortcuts
	    view.root.setOnKeyPressed(KeyEvent -> {
	    	
	    	
	    	//cheat-mode: CTRL + Shift + C
	    	KeyCodeCombination SC_cheat = new KeyCodeCombination(KeyCode.C, 
	    													     KeyCodeCombination.SHIFT_DOWN,
	    													     KeyCodeCombination.CONTROL_DOWN);
	    	
	    	//sound: CTRL + Shift + S
	    	KeyCodeCombination SC_sound = new KeyCodeCombination(KeyCode.S, 
																 KeyCodeCombination.SHIFT_DOWN,
																 KeyCodeCombination.CONTROL_DOWN);
	    	
	    	if (SC_cheat.match(KeyEvent)) {
	    		Platform.runLater( 
    					()-> { view.chkCheat.setSelected(!view.chkCheat.isSelected()); });
	    	}
	    	
	    	if (SC_sound.match(KeyEvent)) {
	    		Platform.runLater( 
						()-> { view.chkSound.setSelected(!view.chkSound.isSelected()); });
	    	}
	    	
	    	
	    });
	    
		
		
	    //listen for resizing the window width
	    view.getStage().widthProperty().addListener(
	    		
	    		(ID, OldValue , NewValue) -> {
	    			
	    			lWait = true;
	    			
	    			double nOldPaintingWidth = view.painting.nWidth;
	    			    			
	    			view.painting.nWidth = ((double) NewValue) - view.listing.getWidth() - view.slider.getWidth() ;
	    			double nRatio = 1 + (view.painting.nWidth - nOldPaintingWidth)/nOldPaintingWidth;
	    			Platform.runLater(
	    					()-> {	    												
	    						   view.painting.setWidth(view.painting.nWidth);
	    					       view.painting.drawBase();
	    					       model.adjustCoordinate_X(nRatio);
	    					       model.reassessRelevance( (view.painting.getWidth() + view.painting.getHeight()) / 2 );
	    					       view.paintPoints(model.PointSet);  });
	    			
	    			lWait = false;
	    		}	
	    		
	    		
	    );
	    
    
	    //listen for resizing the window height
	    view.getStage().heightProperty().addListener(
	    		
	    		(ID, OldValue , NewValue) -> {
	    			
	    			lWait = true;
	
	    			double nOldPaintingHeight = view.painting.nHeight;		
	    			view.painting.nHeight = ((double) NewValue) - view.txt_CurrentPi.getHeight() -50; //TODO: why the fuck -50?
	    			double nRatio = 1 + (view.painting.nHeight - nOldPaintingHeight)/nOldPaintingHeight;
	    			
	    			Platform.runLater(
	    					()-> { view.painting.setHeight(view.painting.nHeight);
	    					       view.painting.drawBase();
	    					       model.adjustCoordinate_Y(nRatio);
	    					       model.reassessRelevance( (view.painting.getWidth() + view.painting.getHeight()) / 2  );
	    					       view.paintPoints(model.PointSet); });
	    			
	    			lWait = false;
	    		}	
	    );
    
     
	    serviceLocator.getLogger().info("Application controller initialized");
    
	}
	
	
	
	
	
	
	
	
	
	//---------------------------------------
	//TOOLBOX
	//---------------------------------------
	
	/**
	 * Starts the process of adding new points to the painting
	 * and approximating Pi
	 */
	private void TurnPointMachineOn() {
		
    	Task<Void> task1 = new Task<Void>() {
    		
			@Override
			protected Void call() throws Exception {
										
				lStopp = false;
	        	while(!lStopp) {
	        		
	        		lAlreadyStarted = true;
	        		
	        		
	        		while(lWait) {
	        			System.out.println("currently in wainting loop");
	        			try {
	        				Thread.sleep(5000);
	        			} catch (InterruptedException e) {
	        				//handle here
	        			}
	        		} 
	        			
	        		
	        		try {
	        			long nSpeed = (long) view.slider.getValue();
						if ( nSpeed < 10 ) {
							long nMilisecs =  -10 * nSpeed + 100 ;
							Thread.sleep(nMilisecs);
						}
	     			} catch (InterruptedException e) {
						e.printStackTrace();
					}
	        		
	        		//retrieve current measurements
	        		double nWidth = view.painting.getWidth();
	        		double nHeight = view.painting.getHeight();
	        		
	        		//create a new point
	        		Point p = new Point(nWidth * new Random().nextDouble(), nHeight * new Random().nextDouble());
	        		
	        		//store point appropriately
	        		model.store(p, view.painting.getWidth());

	        		//draw the point on the painting
	        		view.paintPoint(p);
	        		
	        		//calculate current Pi
	        		Double nPi = model.getCurrentPi();
	        		
	        		//retrieve some info-text for the point
	        		String cPointInfo = model.getInfo(p);
	        		
	        		//add current Pi to the info string
	        		String cInfo = cPointInfo + "\t" + nPi.toString() + "\r\n";
	        		
	        		//append info-text to TextArea on GUI
	        		Platform.runLater(()-> { view.listing.appendText(cInfo); });
	        		
	        		//write info-text into PointBook
	        		model.writeToPointBook(cInfo);
	        		
	        		//retrieve current Pi and display it
	        		Platform.runLater(()-> { view.txt_CurrentPi.setText(nPi.toString()); });
	        		
        		}
		        		
	        	lAlreadyStarted = false;
	        	return null;
			}
			
    	};
    	
    	PointMachine = new Thread(task1);
    	PointMachine.setDaemon(true);  //stops Thread when closing the application
    	PointMachine.start();
    
	}
	
	
	/**
	 * Terminates the process of adding new points to the painting
	 * and approximating Pi
	 */
	private void TurnPointMachineOff() {
		lStopp = true;
	}
	
	

	
}
