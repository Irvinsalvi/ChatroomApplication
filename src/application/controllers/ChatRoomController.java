package application.controllers;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChatRoomController {
	SceneController switchScene = new SceneController();

    @FXML
    private TextArea messageField;
    
    @FXML
    private Button btnSend, emojiBtn, gifBtn;
    
    @FXML
    private AnchorPane svgPane, chatPane, rootPane;
    
    @FXML
    private ScrollPane activeUserScroll, scrollPane;
    
    @FXML
    private VBox activeUserBox, detailBox;
    
    @FXML
    private HBox iconBox;
    
    @FXML
    private ImageView logoutBtn, settingsBtn;
    
    @FXML
    void leaveChatRoom(MouseEvent event) throws IOException {
    	switchScene.loginFormScene(event);
    	//TODO clear session 
    }

    @FXML
    void openSettingsPane(MouseEvent event) {
    	System.out.println("open settings button clicked");
    	//TODO show settings pane or dialog box
    }

    @FXML
    void sendMessage(ActionEvent event) {
    	System.out.println("send message button clicked");
    	//TODO send message to server and display in thread
    }
    
    @FXML
    void addEmoji(ActionEvent event) {
    	System.out.println("add emoji button clicked");
    	//TODO add emoji to message field

    }

    @FXML
    void addGIF(ActionEvent event) {
    	System.out.println("add GIF button clicked");
    	//TODO add GIF to message field
    }

}
