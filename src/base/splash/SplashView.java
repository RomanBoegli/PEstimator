package base.splash;

import base.abstrac.View;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Roman Boegli
 */

public class SplashView extends View<SplashModel> {
    
	ProgressBar progress;
    
    
	public SplashView(Stage stage, SplashModel model) {
		super(stage, model);
		stage.initStyle(StageStyle.TRANSPARENT);
		//stage.setTitle("PEstimator");
				
	}

	
	@Override
	protected Scene createGUI() {
		
		BorderPane root = new BorderPane();
		root.setId("splash");
		
        progress = new ProgressBar();
        progress.setPrefWidth(1000);
        
        HBox bottomBox = new HBox();
        bottomBox.setId("progressbox");
        bottomBox.getChildren().add(progress);
        root.setBottom(bottomBox);
        
        Scene scene = new Scene(root, 600, 600, Color.TRANSPARENT);
        scene.getStylesheets().addAll(
                this.getClass().getResource("splashStyle.css").toExternalForm());

        
        return scene;
		
	}
	
	
	

}
