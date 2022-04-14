package application.controllers;

import java.io.IOException;

import application.model.User;
import application.model.userHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class CreateAccountController {
	
	SceneController switchScene = new SceneController();
	
	public static User u;
	
	private int currentPicture;
	
	@FXML
    private Button createAccountBtn;
	
	@FXML
    private Button cancelAccountBtn;
	
	@FXML
	private RadioButton button0;
	
	@FXML
	private RadioButton button1;
	
	@FXML
	private RadioButton button2;
	
	@FXML
	private RadioButton button3;
	
	@FXML
	private RadioButton button4;
	
	@FXML
	private RadioButton button5;
	
	@FXML
	private RadioButton button6;
	
	@FXML
	private RadioButton button7;
	
	@FXML
	private RadioButton button8;
	
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
		passwordsDontMatch.setText("");
		usernameBlank.setText("");
		passwordBlank.setText("");
		confirmPasswordBlank.setText("");
		
		if(validateCreateAccount()) {
			selectCurrentPicture();
			u = new User(username.getText(), password.getText(), currentPicture, true);	
			userHolder holder = (userHolder) userHolder.getInstance();
			holder.setUser(u);
			switchScene.chatRoomScene(event);
		}
		checkTextFields();
	}

	private void selectCurrentPicture() {
		if(button0.isSelected() == true) {
			currentPicture = 0;
		}
		if(button1.isSelected() == true) {
			currentPicture = 1;
		}
		if(button2.isSelected() == true) {
			currentPicture = 2;
		}
		
		if(button3.isSelected() == true) {
			currentPicture = 3;
		}
		if(button4.isSelected() == true) {
			currentPicture = 4;
		}
		if(button5.isSelected() == true) {
			currentPicture = 5;
		}
		if(button6.isSelected() == true) {
			currentPicture = 6;
		}
		if(button7.isSelected() == true) {
			currentPicture = 7;
		}
		if(button8.isSelected() == true) {
			currentPicture = 8;
		}
	}

	private void checkTextFields() {
		if(username.getText().isEmpty()){
			usernameBlank.setText("Username field cannot be empty!");
		}if(password.getText().isEmpty()){
			passwordBlank.setText("Password field cannot be empty!");
		}if(confirmPassword.getText().isEmpty()){
			confirmPasswordBlank.setText("Confirm Password field cannot be empty!");
		}if(password.getText().toString().equals(confirmPassword.getText().toString()) == false && confirmPassword.getText().isEmpty() && passwordBlank.getText().isEmpty()){
			passwordsDontMatch.setText("Passwords do not match.");
		}if(password.getText().toString().equals(confirmPassword.getText().toString()) == false){
			passwordsDontMatch.setText("Passwords do not match.");
		}
	}
	
	@FXML
	void onCancelAccountBtnClicked(ActionEvent event) throws IOException {
			switchScene.loginFormScene(event);
	}
	
	public boolean validateCreateAccount() {
		if(!username.getText().isEmpty() && !password.getText().isEmpty() && !confirmPassword.getText().isEmpty() && password.getText().toString().equals(confirmPassword.getText().toString()))
		{
			return true;
		}
		else
			return false;	
	}
}
