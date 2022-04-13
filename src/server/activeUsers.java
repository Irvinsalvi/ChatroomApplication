package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * Retrieves active user list from the user table
 * 
 * @author steph
 *
 */
public class activeUsers {
	
	/**
	 * for testing
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		String[][] test = getActiveUsers();
		
		for(String[] s : test) {
			System.out.println(s[0] + " " + s[1]);
		}

		
	}
	
	/**
	 * Gets all users currently active in user table
	 * 
	 * @return userTable array of usernames and avatar number
	 * @throws IOException
	 */
	public static String[][] getActiveUsers() throws IOException {
		URL url = new URL("https://www.robertmorelliworkspace.biz/chat-app/GetActive.php");

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
		String userTotal = new String(content);
		int numOfUsers = userTotal.length() - userTotal.replaceAll(";", "").length() + 1;
		String[][] userTable = new String[numOfUsers][2];
		int userNum = 0;
		for (String s : userTotal.split(";")) {
			String[] line = s.split(",");
			userTable[userNum][0] = new String(Base64.getDecoder().decode(line[0]));//username
			userTable[userNum][1] = line[1];//user avatar number
			userNum++;//increment to next user
		}
		in.close();
		con.disconnect();
		return userTable;

	}

}
