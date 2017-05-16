package rias;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Program extends Application {
    
     public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
      Parent root = FXMLLoader.load(getClass().getResource("/vista/FXML_Loging.fxml"));
      Scene scene = new Scene(root, 300, 275);
      stage.setTitle("Aplicación de ejemplo");
      stage.setScene(scene);
      stage.show();       
    }    
}
