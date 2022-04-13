package application.controllers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import server.activeUsers;

import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.ResourceBundle;

import application.Main;
import application.model.User;


public class ChatRoomController implements Initializable{
	SceneController switchScene = new SceneController();
	User u;
	activeUsers onlineUsers = new activeUsers();
	
	  String name = "";
	  String status = "";
	  String room = "";

	  
	@FXML
	private TextArea messageField;

	@FXML
	private Button btnSend, emojiBtn, gifBtn, btn;

	@FXML
	private AnchorPane svgPane, chatPane, rootPane;

	@FXML
	private ScrollPane activeUserScroll, scrollPane;

	@FXML
	private VBox activeUserBox, detailBox, chatBox;

	@FXML
	private HBox iconBox;

	@FXML
	private ImageView logoutBtn, settingsBtn;
	
	@FXML
	private Label loggedInAsName;
	

    @FXML
    private TextField messageBubble;
	

	@FXML
	void leaveChatRoom(MouseEvent event) throws IOException {
		switchScene.loginFormScene(event);
		// TODO clear session
	}

	@FXML
	void openSettingsPane(MouseEvent event) {
		System.out.println("open settings button clicked");
		// TODO show settings pane or dialog box
	}

	@FXML
	boolean sendMessage(ActionEvent event) {
		String m = messageField.getText();
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpledateformat = new SimpleDateFormat(pattern);
		String currTime = simpledateformat.format(new Date());

		System.out.println("send message button clicked");
		boolean messageStatus = false;

		try {
		String toSend = "https://www.robertmorelliworkspace.biz/chat-app/SendComment.php?data="
				+ new String(Base64.getEncoder().encode(("{ \"Name\": \"" + name + "\", \"Comment\": \""
						+ m + "\", \"Room\": \"" + room + "\" }").getBytes()));
		URL url = new URL(toSend);
		// url object for url purposes

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		con.setDoOutput(true);

		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		String responseTotal = new String(content);
		in.close();
		con.disconnect();
		messageStatus =  responseTotal.equals("comment sent");
		displayMessage(name,m, currTime);
		messageField.clear();
		}catch(IOException e) {
			//TODO DISPLAY MESSAGE NOT SENT ERROR 
			System.out.println("Houston, we have a problem.");
		}
		
		return messageStatus;
	}
	
	public static String[][] getComments(String room) throws IOException {
		String roomString = new String(Base64.getEncoder().encode(room.getBytes()));
		URL url = new URL("https://www.robertmorelliworkspace.biz/chat-app/GetComments.php?roomREF="
				+ ( roomString.length()>20?roomString.substring(0, 20):roomString  ));
		// url object for url purposes

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		con.setDoOutput(true);

		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		String responseTotal = new String(content);
		int commentNumber = responseTotal.length() - responseTotal.replaceAll(";", "").length() + 1;
		String[][] responseTable = new String[commentNumber][3];
        int reponseNumber = 0;
        for (String s : responseTotal.split(";")) {
            String[] line = s.split(",");
            responseTable[reponseNumber][0] = new String(Base64.getDecoder().decode(line[0]));
            responseTable[reponseNumber][1] = new String(Base64.getDecoder().decode(line[1]));
            responseTable[reponseNumber++][2] = line[2];
        }
		in.close();
		con.disconnect();
		return responseTable;
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
		
		HBox incomingMsg = new HBox();
		incomingMsg.setId("outgoingMsgHBox");
		chatBox.getChildren().add(incomingMsg);
		if(u.getUsername().equals(username)) {
			incomingMsg.setStyle("-fx-alignment:TOP_RIGHT;");
		}else {
			incomingMsg.setStyle("-fx-alignment:TOP_LEFT;");
		}
		
		
		ImageView senderPic = new ImageView();
		incomingMsg.getChildren().add(senderPic);		
		
		VBox messageBox = new VBox();
		incomingMsg.getChildren().add(messageBox);
		messageBox.setStyle("-fx-background-color:rgba(0,0,0,0.6);");
		

		TextArea msg = new TextArea(message);
		msg.setEditable(false);
		msg.setId("outgoingMsgBox");
		
		
		Label time = new Label(timestamp);
		time.setStyle("-fx-text-fill:white;");
		time.setId("outgoingMessageTimestamp");
		Label senderName = new Label(username);
		senderName.setId("outgoingMsgName");
		senderName.setStyle("-fx-text-fill:white;");
		messageBox.getChildren().add(msg);
		messageBox.getChildren().add(time);
		messageBox.getChildren().add(senderName);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		u = LoginFormController.getUser();
		name = u.getUsername();
		loggedInAsName.setText(u.getUsername());
		String[][] messages = null;
		scrollPane.setVvalue(1.0);
		
		try {
			messages = getComments("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < messages.length; i++) {
			try {
				displayMessage(messages[i][0], messages[i][1], messages[i][2]);
				System.out.println(messages[i][0] + " : " + messages[i][1]+ " : " + messages[i][2]);//
				System.out.println(i);
				System.out.println(messages.length);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
//			messageBubble.appendText(messages[i][0]);
//			messageBubble.appendText(messages[i++][1]);
		}
		
		try
		{
			String[][] usersActive = activeUsers.getActiveUsers();
			
			for (String[] strings : usersActive)
			{
				activeUserBox.getChildren().add(new Label(strings[0]));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * grabs the string number from the array and converts it into the corresponding image
	 * 
	 * Still a work in progress...
	 * 
	 * @return image that will display in the active users
	 * 
	 */
	public Image getImage(String num) throws FileNotFoundException, URISyntaxException
	{
		String imagepath = "";
		Circle circle = new Circle (50, 50, 25);
		
		switch (num)
		{
		case "0":
			imagepath = "@..\\util\\images\\avatar-0.png";
			break;
		case "1":
			imagepath = "@..\\util\\images\\avatar-1.png";
			break;
		case "2":
			imagepath = "@..\\util\\images\\avatar-2.png";
			break;
		case "3":
			imagepath = "@..\\util\\images\\avatar-3.png";
			break;
		case "4":
			imagepath = "@..\\util\\images\\avatar-4.png";
			break;
		case "5":
			imagepath = "../util/images/avatar-5.png";
			break;
		case "6":
			imagepath = "@..\\util\\images\\avatar-6.png";
			break;
		case "7":
			imagepath = "@..\\util\\images\\avatar-7.png";
			break;
		case "8":
			imagepath = "@..\\util\\images\\avatar-8.png";
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + num);
		}
		
		Image image = new Image(Main.class.getResource(imagepath).toURI().toString());
		circle.setFill(new ImagePattern(image));
		
		return image;
		
		
	}
	
}
