package application.controllers;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController
{
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void login(ActionEvent e)
	{
		//this method will ran be once the users clicks on login
		//several methods will be ran like checking if username is available
		//making sure username field is not empty
		//user gets connected to server
		//switching to the next scene if the all the previous methods are met

	}
	
	public void createAccountScene(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(Main.class.getResource("view/CreateAccount.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	
	//howdy
}
