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

public class CreateAccountController {
	
	SceneController switchScene = new SceneController();
	public static User u;
	
	@FXML
    private Button createAccountBtn;
	
	@FXML
    private Button cancelAccountBtn;
	
	@FXML
    private TextField username;
    
    @FXML
    private PasswordField password;
    
    @FXML
    private PasswordField confirmPassword;
    
    @FXML
    private Label usernameBlank;
    
    @FXML
    private Label passwordBlank;
    
    @FXML
    private Label confirmPasswordBlank;
    
    @FXML
    private Label passwordsDontMatch;
    
    
	@FXML
	void onCreateAccountBtnClicked(ActionEvent event) throws IOException {
//		System.out.println(username.getText().toString());
		if(validateCreateAccount()) {

			u = new User(username.getText(), password.getText(), "1", true);	
			userHolder holder = (userHolder) userHolder.getInstance();
			holder.setUser(u);
			
			switchScene.chatRoomScene(event);
		}else if(username.getText().isEmpty()){
			usernameBlank.setText("Username field cannot be empty!");
		}else if(password.getText().isEmpty()){
			passwordBlank.setText("Password field cannot be empty!");
		}else if(confirmPassword.getText().isEmpty()){
			confirmPasswordBlank.setText("Confirm Password field cannot be empty!");
		}else if(password.getText() != confirmPassword.getText()){
			passwordsDontMatch.setText("Passwords do not match.");
		}
		else
		{
			
		}
	}
	
	@FXML
	void onCancelAccountBtnClicked(ActionEvent event) throws IOException {
			switchScene.loginFormScene(event);
	}
	
	public boolean validateCreateAccount() {
		if(!username.getText().isEmpty() && !password.getText().isEmpty() && !confirmPassword.getText().isEmpty())
		{
			return true;
		}
		else
			return false;	
	}
}
