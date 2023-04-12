package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventDash implements Initializable {
    @FXML
    private VBox content;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    private void setContentfxml(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node node = loader.load();
            content.getChildren().clear();
            content.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleajouterEvent(ActionEvent event) {
        setContentfxml("AjouterEvenment.fxml");
    }
    public void handleconsulterEvent(ActionEvent event) {
        setContentfxml("ConusulterEventdash.fxml");

    }



    public void handleconsulterPub(ActionEvent event) {
        setContentfxml("ConsulterPublicite.fxml");

    }
    public void handleajouterPublicite(ActionEvent event) {

        setContentfxml("AjouterPublicite.fxml");
    }

    public void handleajouterTicket(ActionEvent event) {

        setContentfxml("AjouterTicket.fxml");
    }

    public void handleConsulterTicket(ActionEvent event) {

        setContentfxml("ConsulterTicket.fxml");

    }


}

