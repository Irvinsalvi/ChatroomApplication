package application.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;

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
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;

public class ChatRoomController {
	SceneController switchScene = new SceneController();
	String name = "McTesting Tester";
	String room = "";

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
		// TODO clear session
	}

	@FXML
	void openSettingsPane(MouseEvent event) {
		System.out.println("open settings button clicked");
		// TODO show settings pane or dialog box
	}

	@FXML
	boolean sendMessage(ActionEvent event) {
		// TODO display in thread
		System.out.println("send message button clicked");
		boolean messageStatus = false;

		try {
		String toSend = "https://www.robertmorelliworkspace.biz/chat-app/SendComment.php?data="
				+ new String(Base64.getEncoder().encode(("{ \"Name\": \"" + name + "\", \"Comment\": \""
						+ messageField.getText() + "\", \"Room\": \"" + room + "\" }").getBytes()));
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
		}catch(IOException e) {
			System.out.println("Houston, we have a problem.");
		}
		
		return messageStatus;
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

}
