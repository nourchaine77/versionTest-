
package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Myconnexion;

import java.io.IOException;

public class FirstWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        Myconnexion m = Myconnexion.getInstance();

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            Scene scene = new Scene(root);


            primaryStage.setTitle("Gestion Përsonne!");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
