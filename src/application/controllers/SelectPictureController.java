package application.controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class SelectPictureController {
	
	SceneController switchScene = new SceneController();
	String avatarPictures[]=new String[] { "/application/util/images/defaultAvatar.jpg", "/application/util/images/avatarPicture1.png",  "/application/util/images/avatarPicture2.png",  "/application/util/images/avatarPicture3.png" };
	
	@FXML
    private Button backBtn;
	
	@FXML
    private Button nextBtn;
	
	@FXML
    private Button confirmBtn;
	
	ImageView myImageView;
	
	@FXML
	void onNextBtnClicked(ActionEvent event) throws IOException {
		
		displayImage();
	}
	
	public void displayImage() throws FileNotFoundException {
		
		myImageView = new ImageView();
		FileInputStream input = new FileInputStream("src/application/util/images/avatarPicture1.png");
		Image myImage = new Image(input);
        System.out.println("Next button pressed");
		myImageView.setImage(myImage);
	}
}
