package modelo;

import java.awt.Desktop;
import java.io.IOException;
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
    private ConfigurationBuilder cb;
    private RequestToken requestToken;
    private TwitterFactory tf;
    private Twitter twitter;
    
    public void Initialize() throws TwitterException, URISyntaxException, IOException {
        cb = new ConfigurationBuilder();
        
        cb.setDebugEnabled(true)
        .setOAuthConsumerKey("AtbKWHZ24fBAVqqc25zBTB3OA")
        .setOAuthConsumerSecret("yz7j9x5Q2dmOJcP1EyX7jMJrdcrXO94hvD4odmxwW64Xa7Cjp7");
        
        tf = new TwitterFactory(cb.build());
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
                System.out.println("Error al obtener el token de acceso");
            } else {
                te.printStackTrace();
            }
        }
        
        return true;
    }
    
    public TwitterOauth() {
        accessToken = null;
    }
    
    public void UpdateStatus(String tweet) throws TwitterException {
        twitter.updateStatus(tweet);
    }
}
