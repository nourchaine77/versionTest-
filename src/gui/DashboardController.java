package gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DashboardController implements Initializable {

    @FXML
    private Button manageUsersButton;

    @FXML
    private Button manageProductsButton;

    @FXML
    private Button viewReportsButton;
    
    @FXML
    private Label  welcomeLabel;
    
     @FXML
    private Button commande;
     
      @FXML
    private Button facture;
      
    @FXML
    private HBox c;
    
    @FXML
    private HBox f;
    @FXML
    private HBox f2;

    @FXML
    private VBox content;
      
   @FXML
    private TableView commandeTable;
   


// Define the rest of the columns here...
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
     @FXML
    void handleManageUsers(ActionEvent event)  {
      welcomeLabel.setVisible(true);
        c.setVisible(false);
         f.setVisible(false);
         commandeTable.setVisible(false);
    }


    @FXML
    void handleManageProducts(ActionEvent event) {
       welcomeLabel.setVisible(false);
        c.setVisible(true);
         f.setVisible(true);
         commandeTable.setVisible(false);
    }

    

    
     @FXML
    void consultercmd(ActionEvent event) {
          try {

       } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleEvent(ActionEvent event) {
    setContentfxml("EventDash.fxml");

    }

}
 