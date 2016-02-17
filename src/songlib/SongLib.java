package songlib;

//@author Xi Chen, Deep Patel

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utility.Controller;


public class SongLib extends Application {

	public void start(Stage primaryStage) 
			 throws Exception { 
			 FXMLLoader loader = new FXMLLoader(); 
			 loader.setLocation( 
			 getClass().getResource("/utility/TitledPane2.fxml")); 
			 AnchorPane root= (AnchorPane)loader.load();
			 
			 Controller controller = loader.getController();
			 controller.start(primaryStage);
		
			 Scene scene = new Scene(root, 570,400); 
			 primaryStage.setScene(scene); 
			 primaryStage.show(); 
			 primaryStage.setResizable(false);
	}
	public static void main(String[] args){
		launch(args);

}
}	