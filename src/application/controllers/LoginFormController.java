package application.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginFormController {
	SceneController switchScene = new SceneController();

    @FXML
    private Button loginBtn;
    
	@FXML
	void onLoginClicked(ActionEvent event) throws IOException {
		if(validateLogin()) {
			switchScene.chatRoomScene(event);
		}else{
			System.out.println("login failed");
			//TODO add label field in login UI to output failed login message to client
		}
	}
	
	@FXML
	void onCreateAccountClicked(ActionEvent event) throws IOException {
			switchScene.createAccountScene(event);
	}
	
	/**
	 * validate username and password text fields
	 */
	public boolean validateLogin() {
		return true;
		//TODO add validation code
	}
	
	
}
