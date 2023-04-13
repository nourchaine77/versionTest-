package gui;

import Entities.Evenement;
import Entities.Ticket;
import Services.ServiceEvenement;
import Services.ServiceTicket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class EditerTicketController implements Initializable {

    ServiceTicket serviceTicket = new ServiceTicket();
    ServiceEvenement serviceEvenement = new ServiceEvenement();

    @FXML
    private TextField fxPrix;
    @FXML
    private TextField fxnbrTickets;
    @FXML
    private TextField fxEtat;
    @FXML
    private ChoiceBox<Evenement> cbEvenement;
    @FXML
    private Button btnIcon;
    @FXML
    private Button fxButton;

    private Ticket ticketSelectionne;

    @FXML
    private ChoiceBox<String> cbType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Remplir le ChoiceBox de types de tickets
        serviceTicket = new ServiceTicket();

        // initialize ticketSelectionne
        ticketSelectionne = new Ticket();

        // Remplir le ChoiceBox de types de tickets
        cbType.getItems().addAll("Normal", "VIP");

        // Remplir le ChoiceBox d'événements
        try {
            cbEvenement.getItems().addAll(serviceEvenement.afficherEvenements());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveTicket(ActionEvent event) {
        String prix = fxPrix.getText();
        String type = cbType.getValue();
        String nbr_ticket = fxnbrTickets.getText();
        String etatStr = fxEtat.getText();
        boolean etat;
        if (etatStr.equalsIgnoreCase("true")) {
            etat = true;
        } else if (etatStr.equalsIgnoreCase("false")) {
            etat = false;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("La valeur de l'état doit être 'true' ou 'false'.");
            alert.showAndWait();
            return;
        }
        float prixValue = Float.parseFloat(prix);
        if (prixValue <= 0) {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Le prix doit être un entier positif.");
            alert.showAndWait();
            return;
        }
        int nbrTicketValue = Integer.parseInt(nbr_ticket);
        if (nbrTicketValue <= 0) {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Le nombre de tickets doit être un entier positif.");
            alert.showAndWait();
            return;
        }

        if (prix == null || type == null || nbr_ticket == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        ticketSelectionne.setPrix(Float.parseFloat(prix));
        ticketSelectionne.setType(type);
        ticketSelectionne.setNbrTicket(Integer.parseInt(nbr_ticket));
        ticketSelectionne.setEtat(etat);

        boolean isModificationSucceeded = serviceTicket.modifierTicket(ticketSelectionne);

        if (isModificationSucceeded) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le ticket a été modifié avec succès.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Une erreur est survenue lors de la modification du ticket.");
            alert.showAndWait();
        }
    }
    Ticket ev=new Ticket();

    public void setTicket(Ticket ticket) {
        ev=ticket;
        ticketSelectionne = ticket;
        fxPrix.setText(String.valueOf(ticket.getPrix()));
        cbType.setValue(ticket.getType());
        fxnbrTickets.setText(String.valueOf(ticket.getNbrTicket()));
        fxEtat.setText(String.valueOf(ticket.isEtat()));
    }





}
