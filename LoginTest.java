package rias;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.conf.Configuration;

 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import twitter4j.Query;
import twitter4j.QueryResult;
 
public final class LoginTest {
 
    public static void main(String[] args) throws IOException {
        
        String testStatus="";
 
        ConfigurationBuilder cb = new ConfigurationBuilder();
         
         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //the following is set without accesstoken- desktop client
        cb.setDebugEnabled(true)
      .setOAuthConsumerKey("AtbKWHZ24fBAVqqc25zBTB3OA")
      .setOAuthConsumerSecret("yz7j9x5Q2dmOJcP1EyX7jMJrdcrXO94hvD4odmxwW64Xa7Cjp7");
   
        try {
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();
             
            try {
                System.out.println("-----");
 
                // get request token.
                // this will throw IllegalStateException if access token is already available
                // this is oob, desktop client version
                RequestToken requestToken = twitter.getOAuthRequestToken();
 
                System.out.println("Got request token.");
                System.out.println("Request token: " + requestToken.getToken());
                System.out.println("Request token secret: " + requestToken.getTokenSecret());
 
                System.out.println("|-----");
 
                AccessToken accessToken = null;
 
               
                 
                while (null == accessToken) {
                    System.out.println("Open the following URL and grant access to your account:");
                    System.out.println(requestToken.getAuthorizationURL());
                    System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                    String pin = br.readLine();
                
                    try {
                        if (pin.length() > 0) {
                            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                        } else {
                            accessToken = twitter.getOAuthAccessToken(requestToken);
                        }
                    } catch (TwitterException te) {
                        if (401 == te.getStatusCode()) {
                            System.out.println("Unable to get the access token.");
                        } else {
                            te.printStackTrace();
                        }
                    }
                }
                System.out.println("Got access token.");
                System.out.println("Access token: " + accessToken.getToken());
                System.out.println("Access token secret: " + accessToken.getTokenSecret());
                 
            } catch (IllegalStateException ie) {
                // access token is already available, or consumer key/secret is not set.
                if (!twitter.getAuthorization().isEnabled()) {
                    System.out.println("OAuth consumer key/secret is not set.");
                    System.exit(-1);
                }
            }
           System.out.println("Ingrese un tweet para publicar: ");
           testStatus = br.readLine(); 
           Status status = twitter.updateStatus(testStatus);
 
           System.out.println("Successfully updated the status to [" + status.getText() + "].");
             try {
                    System.out.println("Busque algun tweet ponga la palabra polla!! por ejemplo");
                    String busqueda = br.readLine();
                    Query query = new Query(busqueda);
                    QueryResult result;
                    do {
                        result = twitter.search(query);
                        List<Status> tweets = result.getTweets();
                        for (Status tweet : tweets) {
                            System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                        }
                    } while ((query = result.nextQuery()) != null);
                    System.exit(0);
                } catch (TwitterException te) {
                    te.printStackTrace();
                    System.out.println("Failed to search tweets: " + te.getMessage());
                    System.exit(-1);
                }
           
           
           System.out.println("ready exit");
             
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }
}
