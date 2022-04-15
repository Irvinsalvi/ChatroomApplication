package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.net.*;

public class ChatMessager {

	public Integer lastChat;
	public CookieManager cookieManager;

	public ChatMessager() {
		lastChat = 0;
		cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);
		CookiePolicy cookiePolicy = CookiePolicy.ACCEPT_ALL;
		cookieManager.setCookiePolicy(cookiePolicy);
	}

	public String sendRequestTo(String urlString, String urIForCookies)
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {

		// make connection
		URL url = new URL(urlString);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

		// add cookies to connection
		List<HttpCookie> cookies = cookieManager.getCookieStore().get(new URI(urIForCookies));
		String param = cookies.stream().map(HttpCookie::toString).collect(Collectors.joining(";"));
		httpURLConnection.setRequestProperty("Cookie", param);

		// get data
		InputStream inputStream = (InputStream) httpURLConnection.getContent();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String string = bufferedReader.lines().parallel().collect(Collectors.joining("\n")).trim();

		// clean up connection
		httpURLConnection.disconnect();
		inputStream.close();
		inputStreamReader.close();
		bufferedReader.close();

		// store the cookies
		CookieStore cookieStore = cookieManager.getCookieStore();
		List<HttpCookie> cookieList = cookieStore.getCookies();
		for (HttpCookie cookie : cookieList)
			cookieManager.getCookieStore().add(new URI(urIForCookies), cookie);

		// do stuff with the result
		return string;
	}
	
	public boolean Login(String Name, String Password)
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendToRMWithSuffixAndData("login.php",
				"{ \"Name\": \"" + NE(Name) + "\", \"Password\": \"" + NE(Password) + "\"}");
		return response.equals("true");
	}

	public boolean AmILoggedIn() throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendToRMWithSuffix("amlogged.php");
		return response.equals("true");
	}

	public String[][] GetComments()
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendToRMWithSuffix("GetComments.php");
		if (response.trim().equals("false"))
			return new String[0][0];
		String[][] Table = new String[response.length() - response.replace(";", "").length() + 1][5];
		String[] split = response.split(";");
		for (int x = 0; x < Table.length; x++) {
			Table[x] = split[x].split(",");
			Table[x][0] = FromB64String(Table[x][0]);
			Table[x][1] = FromB64String(Table[x][1]);
		}
		return Table;
	}

	public String[][] GetActive() throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendToRMWithSuffix("GetActive.php");
		if (response.trim().equals("false"))
			return new String[0][0];
		String[][] Table = new String[response.length() - response.replace(";", "").length() + 1][2];
		String[] split = response.split(";");
		for (int x = 0; x < Table.length; x++) {
			Table[x] = split[x].split(",");
			Table[x][0] = FromB64String(Table[x][0]);
		}
		return Table;
	}

	public boolean CreateAccount(String Name, String Password, int Avatar)
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendToRMWithSuffixAndData("createAccount.php", "{ \"Name\": \"" + NE(Name)
				+ "\", \"Password\": \"" + NE(Password) + "\", \"Avatar\": \"" + NE(Avatar + "") + "\" }");
		return response.equals("true");
	}

	public boolean SendMessage(String Message)
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendToRMWithSuffixAndData("SendComment.php", "{ \"Comment\": \"" + NE(Message) + "\"}");
		return response.equals("true");
	}

	public boolean UpdateAccount(int avatar, String NewName)
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendToRMWithSuffixAndData("updateAccount.php",
				"{ \"NameNew\": \"" + NE(NewName) + "\",\"Avatar\": " + NE(avatar + "") + "}");
		return response.equals("true");
	}

	public boolean UpdateAvatar(int avatar)
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendToRMWithSuffixAndData("updateAvatar.php", "{\"Avatar\": " + NE(avatar + "") + "}");
		return response.equals("true");
	}

	public boolean UpdateName(String NewName)
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendToRMWithSuffixAndData("updateName.php", "{ \"NameNew\": \"" + NE(NewName) + "\"}");
		return response.equals("true");
	}

	public boolean UpdatePassword(String NewPass)
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendToRMWithSuffixAndData("updatePassword.php",
				"{ \"newpassword\": \"" + NE(NewPass) + "\"}");
		return response.equals("true");
	}

	public boolean LogOut() throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		return sendToRMWithSuffix("logOut.php").equals("true");
	}

	public String sendToRMWithSuffix(String suffix)
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		return sendRequestTo("https://www.robertmorelliworkspace.biz/chat-app-dev/" + suffix,
				"https://www.robertmorelliworkspace.biz/chat-app-dev");
	}

	public String sendToRMWithSuffixAndData(String suffix, String Data)
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		return sendRequestTo(
				"https://www.robertmorelliworkspace.biz/chat-app-dev/" + suffix + "?data=" + B64String(Data),
				"https://www.robertmorelliworkspace.biz/chat-app-dev");
	}

	public String B64String(String data) {
		return new String(Base64.getEncoder().encode(data.getBytes()));
	}
	public String FromB64String(String data) {
		return new String(Base64.getDecoder().decode(data.getBytes()));
	}

	public String NE(String toEscape) {
		return toEscape.replace("\"", "\\\"");
	}

}
