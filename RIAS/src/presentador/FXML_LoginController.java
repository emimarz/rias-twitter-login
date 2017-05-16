package presentador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.TwitterOauth;
import twitter4j.TwitterException;

public class FXML_LoginController implements Initializable {    
    @FXML
    private Text actiontarget;
    @FXML
    private TextArea txtArea;
    @FXML
    private TextField txtPIN;
    
    private TwitterOauth twitter;
    
    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
        String pin = txtPIN.getText();
        
        if (twitter.Login(pin)) {
            Parent root;
            Stage stage = (Stage)actiontarget.getScene().getWindow();
            try {
                root = FXMLLoader.load(getClass().getResource("/vista/FXML_Main.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXML_LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML protected void handleSubmitButtonAction2(ActionEvent event) {
        String tweet = txtArea.getText();
        
        try {
            twitter.UpdateStatus(tweet);
        } catch (TwitterException ex) {
            Logger.getLogger(FXML_LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            twitter = new TwitterOauth();
            twitter.Initialize();
        } catch (Exception ex) {
            Logger.getLogger(FXML_LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
