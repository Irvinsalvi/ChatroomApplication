package application.controllers;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import server.ChatMessager;
import server.activeUsers;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import application.Main;
import application.model.User;
import application.model.UserHolder;

public class ChatRoomController implements Initializable {
	SceneController switchScene = new SceneController();
	User u;
	activeUsers onlineUsers = new activeUsers();
	ChatMessager chatmessager;

	String name = "";
	String status = "";
	String room = "";

	@FXML
	private TextArea messageField;

	@FXML
	private Button btnSend, emojiBtn, gifBtn, btn, avatarBtn, usernameBtn, passwordBtn;

	@FXML
	private AnchorPane svgPane, chatPane, rootPane, pane1, pane2, settingsPane;

	@FXML
	private ScrollPane activeUserScroll, scrollPane;

	@FXML
	private VBox activeUserBox, detailBox, chatBox, avatarBox, usernameBox, passwordBox;

	@FXML
	private HBox iconBox;

	@FXML
	private ImageView logoutBtn, settingsBtn;

	@FXML
	private Label loggedInAsName, currentUser;

	@FXML
	private TextField messageBubble;

	@FXML
	void leaveChatRoom(MouseEvent event) throws IOException, URISyntaxException, InterruptedException {	
		chatmessager.LogOut();
		switchScene.loginFormScene(event);
		// TODO clear session
	}

	@FXML
	void openSettingsPane(MouseEvent event) {
		System.out.println("open settings button clicked");
		// TODO show settings pane or dialog box
		
		//slide animation
		TranslateTransition slide = new TranslateTransition();
		slide.setDuration(Duration.millis(350));
		slide.setNode(pane2);

		//Open settings pane
		if (!pane1.isVisible()) {
			pane1.setVisible(true);
			slide.setByY(+1000);
			slide.play();
		}
		
		//close settings pane
		else {
			pane1.setVisible(false);
			slide.setByY(-1000);
			slide.play();

			//close all open settings tabs
	    	closeAll();
		}
	}

	@FXML
	void sendMessage(ActionEvent event) {
		String m = messageField.getText();

		try {
			chatmessager.SendMessage(m);
			messageField.clear();
		} catch (IOException | URISyntaxException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void addEmoji(ActionEvent event) {
		System.out.println("add emoji button clicked");
		// TODO add emoji to message field

	}

	@FXML
	void addGIF(ActionEvent event) {
		System.out.println("add GIF button clicked");
		// TODO add GIF to message field
	}

	public void displayMessage(String username, String message, String timestamp) throws MalformedURLException {

		HBox msgHBox = new HBox();
//		msgHBox.setId("outgoingMsgHBox");
		chatBox.getChildren().add(msgHBox);
		if (u.getUsername().equals(username)) {
			msgHBox.setStyle("-fx-alignment:TOP_RIGHT;");
		} else {
			msgHBox.setStyle("-fx-alignment:TOP_LEFT;");
		}

		ImageView msgPofilePic = new ImageView();
		msgHBox.getChildren().add(msgPofilePic);

		VBox msgVBox = new VBox();
		msgHBox.getChildren().add(msgVBox);
		msgVBox.setStyle("-fx-background-color:rgba(0,0,0,0.6);");

		TextArea msg = new TextArea(message);
		msg.setEditable(false);
		msg.setId("outgoingMsgBox");

		Label time = new Label(timestamp);
		time.setStyle("-fx-text-fill:white;");
		time.setId("outgoingMessageTimestamp");
		Label senderName = new Label(username);

		senderName.setStyle("-fx-text-fill:white;");
		msgVBox.getChildren().add(msg);
		msgVBox.getChildren().add(time);
		msgVBox.getChildren().add(senderName);

	}
	
    @FXML
    void openChangeAvatar(MouseEvent event) {
    	slide(avatarBox);
    }

    @FXML
    void openChangePassword(MouseEvent event) {
    	slide(passwordBox);

    }

    @FXML
    void openChangeUsername(MouseEvent event) {
    	slide(usernameBox);

    }

    //slide animations for settings tabs
    public void slide(VBox box) {
    	TranslateTransition slide = new TranslateTransition();
		slide.setDuration(Duration.millis(350));
		slide.setNode(box);

		if(!box.isVisible()) {
			closeAll();

			settingsPane.setVisible(true);
			box.setVisible(true);
			slide.setByY(1000);
			slide.play();
		}
		else {
			slide.setNode(box);
			slide.setByY(-1000);
			slide.play();
			box.setVisible(false);
			settingsPane.setVisible(false);
		}
    }

    //closes all open settings tabs
    public void closeAll() {
    	TranslateTransition closeAll = new TranslateTransition();
		closeAll.setDuration(Duration.millis(350));
		if(settingsPane.isVisible()) {
			if(avatarBox.isVisible()) {
				closeAll.setNode(avatarBox);
				avatarBox.setVisible(false);
			}
			else if (usernameBox.isVisible()) {
				closeAll.setNode(usernameBox);
				usernameBox.setVisible(false);
			}
			else if (passwordBox.isVisible()) {
				closeAll.setNode(passwordBox);
				passwordBox.setVisible(false);
			}
			settingsPane.setVisible(false);
		}
		closeAll.setByY(-1000);
		closeAll.play();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		UserHolder holder = (UserHolder) UserHolder.getInstance();//get instance of this user 
		u = holder.getUser();//assign user from instance of this user
		chatmessager = holder.getChatter();//get chatter assigned to this user
		name = u.getUsername();//get username of this user

		loggedInAsName.setText(name);//displays username on left side pane
		currentUser.setText(u.getUsername());//what label does this belong to?

		scrollPane.vvalueProperty().bind(chatBox.heightProperty());//sets scroll pane to bottom position
		
		//gets active user list
		try {
			String[][] usersActive = chatmessager.GetActive();

			for (String[] strings : usersActive) {
				displayUsers(strings[0], strings[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Settings Pane: move all off-screen
		pane1.setVisible(false);
		TranslateTransition slide = new TranslateTransition();
		slide.setDuration(Duration.millis(350));
		slide.setNode(pane2);
		slide.setByY(-1000);
		slide.play();

		TranslateTransition setAvPane = new TranslateTransition();
		setAvPane.setDuration(Duration.millis(350));
		setAvPane.setByY(-1000);
		setAvPane.setNode(avatarBox);
		setAvPane.play();

		TranslateTransition setUnPane = new TranslateTransition();
		setUnPane.setDuration(Duration.millis(350));
		setUnPane.setByY(-1000);
		setUnPane.setNode(usernameBox);
		setUnPane.play();

		TranslateTransition setPwPane = new TranslateTransition();
		setPwPane.setDuration(Duration.millis(350));
		setPwPane.setByY(-1000);
		setPwPane.setNode(passwordBox);
		setPwPane.play();
		
		//timer runs every 2 seconds
	    Timer timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	        @Override
	        public void run() {
	        	Platform.runLater(new Runnable() {
	                @Override
	                public void run() {
	            		//gets all messages not seen by this instance of chatter
	            		//and displays them in messages bubbles
	            		try {
	            			for (String[] s : chatmessager.GetComments()) {
	            				displayMessage(s[0], s[1], s[2]);
	            				}
	            		} catch (IOException | URISyntaxException | InterruptedException e1) {
	            			e1.printStackTrace();
	            		}
	                }
	            });
	        }
	    }, 0, 2000);
		
	}

	/*
	 * grabs the string number from the array and converts it into the corresponding
	 * image
	 * 
	 * Still a work in progress...
	 * 
	 * @return image that will display in the active users
	 * 
	 */
	public String getImage(String num) throws FileNotFoundException, URISyntaxException {
		String imagepath = "";

		switch (num) {
		case "0":
			imagepath = "avatar-0.png";
			break;
		case "1":
			imagepath = "avatar-1.png";
			break;
		case "2":
			imagepath = "avatar-2.png";
			break;
		case "3":
			imagepath = "avatar-3.png";
			break;
		case "4":
			imagepath = "avatar-4.png";
			break;
		case "5":
			imagepath = "avatar-5.png";
			break;
		case "6":
			imagepath = "avatar-6.png";
			break;
		case "7":
			imagepath = "avatar-7.png";
			break;
		case "8":
			imagepath = "avatar-8.png";
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + num);
		}

		return imagepath;

	}
	
		public void displayUsers(String username, String avatar) throws MalformedURLException, FileNotFoundException, URISyntaxException {
	    activeUserBox.setSpacing(10);
	
	    HBox userHBox = new HBox();
	    userHBox.setSpacing(10);
	    activeUserBox.getChildren().add(userHBox);
	
	    Image image = new Image(getImage(avatar), 50, 50, true, true);
	    ImageView userAvatar = new ImageView();
	    userAvatar.setImage(image);
	
	    Label user = new Label(username);
	    user.setStyle("-fx-text-fill:white;-fx-font-size:16px;");
	
	    //Circle circle = new Circle(5, 5, 10);
	    //circle.setFill(javafx.scene.paint.Color.GREEN);
	
	
	    userHBox.getChildren().add(userAvatar);
	    userHBox.getChildren().add(user);
	    //userHBox.getChildren().add(circle);
	
	}

}
