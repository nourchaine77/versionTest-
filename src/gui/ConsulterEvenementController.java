package gui;

import Entities.Evenement;
import Services.ServiceEvenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import utils.Myconnexion;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ConsulterEvenementController implements Initializable {

    @FXML
    private VBox content;

    @FXML
    private TableView<Evenement> tableView;

    @FXML
    private TableColumn<Evenement, Integer> idColumn;

    @FXML
    private TableColumn<Evenement, String> nomColumn;

    @FXML
    private TableColumn<Evenement, String> descriptionColumn;

    @FXML
    private TableColumn<Evenement, String> lieuColumn;

    @FXML
    private TableColumn<Evenement, String> imageColumn;

    @FXML
    private TableColumn<Evenement, Date> dateColumn;

    private ServiceEvenement serviceEvenement = new ServiceEvenement();

    TableColumn<Evenement, Void> supprimerColumn = new TableColumn<>("Supprimer");
    TableColumn<Evenement, Void> editerColumn = new TableColumn<>("Editer");




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
            Myconnexion m = Myconnexion.getInstance();


        supprimerColumn.setCellFactory(param -> new TableCell<Evenement, Void>() {
            private final Button button = new Button("Supprimer");

            {
                // Ajouter un gestionnaire d'événements pour le bouton "Supprimer"
                button.setOnAction(event -> {
                    Evenement e = getTableRow().getItem();

                    boolean deleted = serviceEvenement.supprimerEvenement(e.getId());
                    if (deleted) {
                        getTableView().getItems().remove(e);
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


        editerColumn.setCellFactory(param -> new TableCell<Evenement, Void>() {
            private final Button button = new Button("editer");

            {
                // Ajouter un gestionnaire d'événements pour le bouton "Supprimer"
                button.setOnAction(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditerEvenement.fxml"));
                        Node node = loader.load();
                        EditerEvenementController controller = loader.getController();
                        Evenement e = getTableRow().getItem();
                        controller.SetEvent(e);


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
                List<Evenement> evenements = serviceEvenement.afficherEvenements();
                System.out.println(serviceEvenement.afficherEvenements());
                ObservableList<Evenement> evenementList = FXCollections.observableArrayList(evenements);
                tableView.setItems(evenementList);

                idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
                descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
                lieuColumn.setCellValueFactory(new PropertyValueFactory<>("lieu"));
                imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("date_evenement"));

                imageColumn.setCellFactory(col -> new ImageTableCell());

            } catch (SQLException e) {
                e.printStackTrace();
            }
    }


    public static class ImageTableCell extends TableCell<Evenement, String> {

        private final ImageView imageView = new ImageView();
        private final double imageSize = 50; // taille de l'image

        public ImageTableCell() {
            setAlignment(Pos.CENTER);
            setGraphic(imageView);
        }

        @Override
        protected void updateItem(String imagePath, boolean empty) {
            super.updateItem(imagePath, empty);
            if (empty || imagePath == null) {
                imageView.setImage(null);
                setText(null);
                setGraphic(null);
            } else {
                Path destinationPath = Paths.get("src", "image", imagePath);
                File file = destinationPath.toFile();
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
                imageView.setFitWidth(imageSize);
                imageView.setFitHeight(imageSize);
                setGraphic(imageView);
            }
        }
    }
        }

