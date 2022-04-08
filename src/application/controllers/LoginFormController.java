package application.controllers;

import java.io.IOException;

import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginFormController {
	SceneController switchScene = new SceneController();
	private Stage stage;
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
			setUser(username.getText(), password.getText());
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
	
	private void setUser(String un, String pw) {
		u = new User(un, pw, "avatar", true, true);
	}
	
	public static User getUser() {
		return u;
	}
	
	
}
