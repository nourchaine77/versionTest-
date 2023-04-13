package gui;
import Services.*;

import Entities.Publicite;
import Entities.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ConsulterTicketController implements Initializable {

    ServiceEvenement serviceEvenement=new ServiceEvenement();

    @FXML
    private VBox content;

    @FXML
    private TableView<Ticket> tableView;
    @FXML
    private TableColumn<Ticket, Integer> idColumn;
    @FXML
    private TableColumn<Ticket, String> typeColumn;
    @FXML
    private TableColumn<Ticket, Boolean> etatColumn;
    @FXML
    private TableColumn<Ticket, Float> prixColumn;
    @FXML
    private TableColumn<Ticket, Integer> nbTicketColumn;

    @FXML
    private TableColumn<Ticket, String> eventColumn;

    TableColumn<Ticket, Void> supprimerColumn = new TableColumn<>("Supprimer");
    TableColumn<Ticket, Void> editerColumn = new TableColumn<>("Editer");
    ServiceTicket serviceTicket = new ServiceTicket();





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        supprimerColumn.setCellFactory(param -> new TableCell<Ticket, Void>() {
            private final Button button = new Button("Supprimer"); {
                button.setOnAction(event -> {
                    Ticket p = getTableRow().getItem();

                    boolean deleted = serviceTicket.supprimerTicket(p.getId());
                    if (deleted) {
                        getTableView().getItems().remove(p);
                    }
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });

        editerColumn.setCellFactory(param -> new TableCell<Ticket, Void>() {
            private final Button button = new Button("Editer");

            {

                button.setOnAction(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditerTicket.fxml"));
                        Node node = loader.load();
                        EditerTicketController controller = loader.getController();
                        Ticket p = getTableRow().getItem();
                        controller.setTicket(p);

                        content.getChildren().clear();
                        content.getChildren().add(node);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });

        tableView.getColumns().add(supprimerColumn);
        tableView.getColumns().add(editerColumn);

        try {
            List<Ticket> tickets= serviceTicket.afficherTickets();
            ObservableList<Ticket> listTickets = FXCollections.observableArrayList(tickets);
            tableView.setItems(listTickets);
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            eventColumn.setCellValueFactory(new PropertyValueFactory<>("evenement_id"));
            prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
            nbTicketColumn.setCellValueFactory(new PropertyValueFactory<>("NbrTicket"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        }

    }
