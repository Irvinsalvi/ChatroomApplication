package application.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CreateAccountController {
	
	SceneController switchScene = new SceneController();
	
	@FXML
    private Button createAccountBtn;
	
	@FXML
    private Button cancelAccountBtn;
	
	@FXML
    private Button selectPictureBtn;
	
	@FXML
	void onCreateAccountBtnClicked(ActionEvent event) throws IOException {
			switchScene.chatRoomScene(event);
	}
	
	@FXML
	void onCancelAccountBtnClicked(ActionEvent event) throws IOException {
			switchScene.loginFormScene(event);
	}
}
