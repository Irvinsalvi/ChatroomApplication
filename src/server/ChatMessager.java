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
		String response = sendRequestTo(
				"https://www.robertmorelliworkspace.biz/chat-app-dev/login.php?data=" + new String(Base64.getEncoder()
						.encode(("{ \"Name\": \"" + Name + "\", \"Password\": \"" + Password + "\"}").getBytes())),
				"https://www.robertmorelliworkspace.biz/chat-app-dev");
		return response.equals("true");
	}

	public boolean AmILoggedIn() throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendRequestTo("https://www.robertmorelliworkspace.biz/chat-app-dev/amlogged.php",
				"https://www.robertmorelliworkspace.biz/chat-app-dev");
		return response.equals("true");
	}

	public String[][] GetComments()
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendRequestTo("https://www.robertmorelliworkspace.biz/chat-app-dev/GetComments.php",
				"https://www.robertmorelliworkspace.biz/chat-app-dev");
		if (response.trim().equals("false"))
			return new String[0][0];
		String[][] Table = new String[response.length() - response.replace(";", "").length() + 1][4];
		String[] split = response.split(";");
		for (int x = 0; x < Table.length; x++) {
			Table[x] = split[x].split(",");
			Table[x][0] = new String(Base64.getDecoder().decode(Table[x][0].getBytes()));
			Table[x][1] = new String(Base64.getDecoder().decode(Table[x][1].getBytes()));
		}
		return Table;
	}

	public boolean CreateAccount(String Name, String Password, int Avatar)
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendRequestTo(
				"https://www.robertmorelliworkspace.biz/chat-app-dev/createAccount.php?data="
						+ new String(Base64.getEncoder()
								.encode(("{ \"Name\": \"" + Name + "\", \"Password\": \"" + Password
										+ "\", \"Avatar\": \"" + Avatar + "\" }").getBytes())),
				"https://www.robertmorelliworkspace.biz/chat-app-dev");
		return response.equals("true");
	}

	public boolean SendMessage(String Message)
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendRequestTo(
				"https://www.robertmorelliworkspace.biz/chat-app-dev/SendComment.php?data="
						+ new String(Base64.getEncoder().encode(("{ \"Comment\": \"" + Message + "\"}").getBytes())),
				"https://www.robertmorelliworkspace.biz/chat-app-dev");
		return response.equals("true");
	}

	public boolean UpdateAcount(int avatar, String NewName)
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendRequestTo(
				"https://www.robertmorelliworkspace.biz/chat-app-dev/SendComment.php?data="
						+ new String(Base64.getEncoder()
								.encode(("{ \"NameNew\": \"" + NewName + "\",\"Avatar\": " + avatar + "}").getBytes())),
				"https://www.robertmorelliworkspace.biz/chat-app-dev");
		return response.equals("true");
	}

	public boolean UpdateAvatar(int avatar)
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendRequestTo(
				"https://www.robertmorelliworkspace.biz/chat-app-dev/SendComment.php?data="
						+ new String(Base64.getEncoder().encode(("{\"Avatar\": " + avatar + "}").getBytes())),
				"https://www.robertmorelliworkspace.biz/chat-app-dev");
		return response.equals("true");
	}

	public boolean UpdateName(String NewName)
			throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendRequestTo(
				"https://www.robertmorelliworkspace.biz/chat-app-dev/SendComment.php?data="
						+ new String(Base64.getEncoder().encode(("{ \"NameNew\": \"" + NewName + " \"}").getBytes())),
				"https://www.robertmorelliworkspace.biz/chat-app-dev");
		return response.equals("true");
	}

	public boolean LogOut() throws MalformedURLException, IOException, URISyntaxException, InterruptedException {
		String response = sendRequestTo("https://www.robertmorelliworkspace.biz/chat-app-dev/logOut.php",
				"https://www.robertmorelliworkspace.biz/chat-app-dev");
		return response.equals("true");
	}

}
