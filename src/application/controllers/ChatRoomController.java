package application.controllers;


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
    void leaveChatRoom(MouseEvent event) {
    	//clear session and show login screen
    }

    @FXML
    void openSettingsPane(MouseEvent event) {
    	//show settings pane or dialog box
    }

    @FXML
    void sendMessage(ActionEvent event) {
    	//send message to server
    }
    
    @FXML
    void addEmoji(ActionEvent event) {
    	//add emoji to message field

    }

    @FXML
    void addGIF(ActionEvent event) {
    	//add GIF to message field
    }

}
