package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * Methods to update and create user information
 * 
 *
 */
public class accountInfo{
	
	/**
	 * for testing
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		Boolean response = createAccount("Frank", "password", 5);
		System.out.println(response);
	}

	/**
	 * Updates current users settings by updating the user in the user table
	 */
	public static void updateAccount() {
		//TODO ADD UPDATE USER CODE TO READ PHP RESPONSE
	}

	/**
	 * Creates new user in user table
	 * 
	 * @param Name username of the user
	 * @param Password password for the user
	 * @param Avatar avatar file path
	 * @return boolean true if user was added, false if not
	 * @throws IOException
	 */
	public static boolean createAccount(String Name, String Password, int Avatar) throws IOException {
    String toSend = "https://www.robertmorelliworkspace.biz/chat-app/createAccount.php?data="
            + new String(Base64.getEncoder().encode(
                    ("{ \"Name\": \"" + Name + "\", \"Password\": \"" + Password + "\", \"Avatar\": \"" + Avatar + "\" }")
                            .getBytes()));
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
    return responseTotal.equals("true");
	}
}