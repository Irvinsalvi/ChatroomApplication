package application.controllers;

import java.io.IOException;

import application.model.User;
import application.model.userHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginFormController {
	SceneController switchScene = new SceneController();
	public static User u;

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
			u = new User(username.getText(), password.getText(), "1", true);
			userHolder holder = (userHolder) userHolder.getInstance();
			holder.setUser(u);
			
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
