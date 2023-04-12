package gui;

import Entities.Evenement;
import Entities.Ticket;
import Services.ServiceTicket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditerTicketController implements Initializable {

    ServiceTicket serviceTicket=new ServiceTicket();

    @FXML
    private TextField fxPrix;
    @FXML
    private TextField fxnbrTickets;
    @FXML
    private TextField fxEtat;
    @FXML
    private TextField fxEventID;
    @FXML
    private TextField fxType;
    @FXML
    private Button btnIcon;

    @FXML
    private Button fxButton;

    private Ticket ticketSelectionne;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherTicketSelectionne();
    }
    private void afficherTicketSelectionne() {
        fxEventID.setText(String.valueOf(ticketSelectionne.getEvenement_id()));
        fxPrix.setText(String.valueOf(ticketSelectionne.getPrix()));
        fxType.setText(ticketSelectionne.getType());
        fxnbrTickets.setText(String.valueOf(ticketSelectionne.getNbrTicket()));
        fxEtat.setText(String.valueOf(ticketSelectionne.isEtat()));
    }
    public void setTicketSelectionne(Ticket ticketSelectionne) {
        this.ticketSelectionne = ticketSelectionne;
    }
    public void saveTicket(ActionEvent event){
        String evenement_id = fxEventID.getText();
        String prix = fxPrix.getText();
        String type = fxType.getText();
        String nbr_ticket = fxnbrTickets.getText();
        String etat = fxEtat.getText();

        if (evenement_id == null || prix == null || type == null || nbr_ticket == null || etat == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        ticketSelectionne.setEvenement_id(Integer.parseInt(evenement_id));
        ticketSelectionne.setPrix(Float.parseFloat(prix));
        ticketSelectionne.setType(type);
        ticketSelectionne.setNbrTicket(Integer.parseInt(nbr_ticket));
        ticketSelectionne.setEtat(Boolean.parseBoolean(etat));

        serviceTicket.modifierTicket(ticketSelectionne);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Le ticket a été modifié avec succès.");
        alert.showAndWait();
    }



}





