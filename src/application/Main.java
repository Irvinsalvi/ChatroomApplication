package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("view/loginScene.fxml")); //grabs the login scene page to display
			Scene scene = new Scene(root);
			
			Image icon = new Image(getClass().getResource("util/images/applicationIcon.png").toURI().toString());
			primaryStage.getIcons().add(icon);
			
			String css = this.getClass().getResource("util/css/application.css").toExternalForm();
			scene.getStylesheets().add(css);
			
			primaryStage.setTitle("Chat Room"); //sets the title of the whole window
			primaryStage.setScene(scene); //sets the scene that you want to the window
			primaryStage.show(); //shows the window
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
