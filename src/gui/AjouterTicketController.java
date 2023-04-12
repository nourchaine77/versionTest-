package gui;

import Entities.Evenement;
import Entities.Ticket;
import Services.ServiceEvenement;
import Services.ServiceTicket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjouterTicketController implements Initializable {
    @FXML
    private TextField tfPrix;

    @FXML
    private ChoiceBox<String> cbType;

    @FXML
    private ChoiceBox<Evenement> cbEvenement;

    @FXML
    private TextField tfNbrTicket;

    private ServiceTicket serviceTicket;
    private ServiceEvenement serviceEvenement;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceTicket = new ServiceTicket();
        serviceEvenement = new ServiceEvenement();

        // Remplir le ChoiceBox de types de tickets
        cbType.getItems().addAll("Normal", "VIP");

        // Remplir le ChoiceBox d'événements
        try {
            cbEvenement.getItems().addAll(serviceEvenement.afficherEvenements());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @FXML
    public void saveTicket(ActionEvent actionEvent) {
// Récupérer les valeurs saisies dans les champs
        float prix = Float.parseFloat(tfPrix.getText());
        String type = cbType.getValue();
        int nbrTicket = Integer.parseInt(tfNbrTicket.getText());
        Evenement evenement = cbEvenement.getValue();
        // Vérifier si tous les champs obligatoires sont remplis
        if (type == null || evenement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        // Créer un objet Ticket avec les valeurs saisies
        Ticket ticket = new Ticket();
        ticket.setEvenement_id(cbEvenement.getValue().getId());
        ticket.setPrix(prix);
        ticket.setType(type);
        ticket.setNbrTicket(nbrTicket);
        ticket.setEtat(true);


        // Ajouter l'objet Ticket à une liste
        serviceTicket.ajouterTicket(ticket);

        // Afficher un message de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Le ticket a été ajouté avec succès.");
        alert.showAndWait();




    }






}