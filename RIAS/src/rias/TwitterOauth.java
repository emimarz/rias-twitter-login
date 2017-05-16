package rias;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterOauth {
    private AccessToken accessToken;
    private RequestToken requestToken;
    private Twitter twitter;
    
    public void Initialize() throws TwitterException, URISyntaxException, IOException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
         
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //the following is set without accesstoken- desktop client
        cb.setDebugEnabled(true)
        .setOAuthConsumerKey("AtbKWHZ24fBAVqqc25zBTB3OA")
        .setOAuthConsumerSecret("yz7j9x5Q2dmOJcP1EyX7jMJrdcrXO94hvD4odmxwW64Xa7Cjp7");
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        
        requestToken = twitter.getOAuthRequestToken();
                    
        Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
    }
    
    public boolean Login(String pin) {
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
        return true;
    }
    
    public TwitterOauth() {
        accessToken = null;
    }
}
