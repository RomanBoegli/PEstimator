package base.app;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import base.abstrac.View;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Roman Boegli
 */

public class AppView extends View<AppModel> {
	
	//---------------------------------------
	//GLOBAL VARIABLES
	//---------------------------------------
	
	BorderPane root;
	MediaPlayer mediaPlayer;
	Media media;
		
	//top
	Label txt_CurrentPi;
	
	//left
	TextField txtSpeed ;
	Slider slider;
	Button cmdStart;
	Button cmdEnd;
	CheckBox chkSound;
	CheckBox chkCheat;
	
	//center
	Painting painting;
	
	//right
	TextArea listing;
	
	
	
	
	
	
	
	
	//Constructor
	public AppView(Stage stage, AppModel model) {
		
		super(stage, model);
		stage.setTitle("PEstimator");
	}
	
	
	
	
	
	
	
	
	//---------------------------------------
	//BUILDING THE BORDERPANE
	//---------------------------------------
	
	@Override
	protected Scene createGUI() {
					
		root = new BorderPane();
		root.setId("app");
		
		root.setTop(getTop());
		root.setLeft(getLeft());
		root.setCenter(getCenter());			
		root.setRight(getRight());
		root.setBottom(getBottom());
		
		URL resource = getClass().getResource("pi_as_music.mp3");
		media = new Media(resource.toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		mediaPlayer.setRate(1);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		
		
		Scene scene = new Scene(root,1168,630);
		scene.getStylesheets().addAll(
                this.getClass().getResource("appStyle.css").toExternalForm());
			
		return scene;
		
	}
	
	
	
	
	
	
	
	
	//---------------------------------------
	//BUILDING EACH SECTION OF A BOARDERPANE
	//---------------------------------------
	
	private Pane getTop() {
		
		//Pi: 3.14..
		Label lbl_txt_CurrentPi= new Label("Pi:");
		lbl_txt_CurrentPi.setId("lbl_txt_CurrentPi");
		txt_CurrentPi = new Label("0.000000000000000000000");
		txt_CurrentPi.setId("txt_CurrentPi");
		
		
		//main-container
		HBox holder = new HBox(lbl_txt_CurrentPi, txt_CurrentPi);
		holder.setId("holder_top");
				
		return holder;
	}
	
	
	private Pane getLeft() {
		
		//Speed regulation
		Label lblSpeed = new Label("Speed:");
		txtSpeed = new TextField();
		txtSpeed.setPrefWidth(80);
		txtSpeed.setEditable(false);
		HBox hb1 = new HBox(lblSpeed, txtSpeed);
		slider = new Slider(1, 9, 5);
		slider.setTooltip(new Tooltip("slide me to change speed of adding new points"));
	    txtSpeed.setText(Math.round(slider.getValue()) + "");
	    
		//Start & Stop Buttons
	    cmdStart = new Button("Start");
	    cmdEnd = new Button("Stop");
		HBox hb2 = new HBox(cmdStart, cmdEnd);
		hb2.setSpacing(20);
		
		//Cheater-Mode on-off
		chkCheat = new CheckBox("Cheat");
		chkCheat.setSelected(false);
		
		//Sound on-off
	    chkSound = new CheckBox("Sound");
	    chkSound.setSelected(true);
	    
	    
		//main-container
		VBox holder = new VBox(hb1, slider, hb2, chkCheat, chkSound);
		holder.setId("holder_left");
		holder.setAlignment(Pos.TOP_LEFT);	
		
		return holder;
	}
	
	
	private Pane getCenter() {
		
		painting = getPainting();
		
		//main-container
		StackPane holder = new StackPane();
		holder.setId("holder_center");
		holder.setAlignment(Pos.TOP_LEFT);
		holder.getChildren().add(painting);
		
		return holder;
	}
	
	
	private Pane getRight() {
		
		listing = new TextArea("ID\t" + "X\t" + "Y\t" + "relevant\t" + "Pi\t\r\n");	
	    listing.setPrefWidth(600);
		
	    //main-container
	    StackPane holder = new StackPane();
		holder.setId("holder_right");		
		holder.setAlignment(Pos.TOP_RIGHT);	
		holder.getChildren().add(listing);
		
		return holder;
	}
	
	
	private Pane getBottom() {
		
		//main-container (just empty)
		StackPane holder = new StackPane();
				
		return holder;
				
	}
	
	
	
	
	
	
	
	
	//---------------------------------------
	//SUPPORTING THE SECTION BUILDING PROCESS
	//---------------------------------------
	
	public Painting getPainting() {
		
        /*create painting if not already existing
		 *as the view need only one painting.
		 */
		if (painting == null) {
			painting = new Painting();
		}
		
		return painting;
	}
		
	
    protected void paintPoint(Point p) {
		
		GraphicsContext gc = painting.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillOval(p.x, p.y, p.radius, p.radius);
		
	}
	
	
	protected void paintPoints(ArrayList<Point> aList) {
		
		GraphicsContext gc = painting.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		
		Iterator<Point> handle = aList.iterator();
		while ( handle.hasNext() ) {
			Point p = handle.next();
			gc.fillOval(p.x, p.y, p.radius, p.radius);;
		}
		
	}
	
	
	
	
	
	
	
	
	
}
