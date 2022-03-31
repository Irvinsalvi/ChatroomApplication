package application.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginFormController {
	SceneController switchScene = new SceneController();

    @FXML
    private Button loginBtn;
    
    @FXML
    private Label validationMessage;
    
    @FXML
    private TextField username;
    
    @FXML
    private PasswordField password;
    
	@FXML
	void onLoginClicked(ActionEvent event) throws IOException {
		if(validateLogin()) {
			switchScene.chatRoomScene(event);
		}else if(username.getText().isEmpty() || password.getText().isEmpty()){
			validationMessage.setText("Username or Password is empty!");
		}
		else
		{
			validationMessage.setText("Username or Password is incorrect!");
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
		if(username.getText().toString().equals("admin") && password.getText().toString().equals("password"))
		{
			return true;
		}
		else
			return false;
		
	}
	
	
}
