package gui;
import Entities.Evenement;
import Entities.Publicite;
import Services.IServiceEvenement;
import Services.ServiceEvenement;
import Services.ServicePublicite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
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

public class ConsulterPubliciteController implements Initializable {
    @FXML
    private VBox content;
    @FXML
    private TableView<Publicite> tableView;
    @FXML
    private TableColumn<Publicite, Integer> idColumn;
    @FXML
    private TableColumn<Publicite, String> nomColumn;
    @FXML
    private TableColumn<Publicite, String> imageColumn;
    @FXML
    private TableColumn<Publicite, Date> dateDebutColumn;
    @FXML
    private TableColumn<Publicite, String> eventColumn;

    @FXML
        private TableColumn<Publicite, Date> dateFinColumn;


    TableColumn<Publicite, Void> supprimerColumn = new TableColumn<>("Supprimer");
    TableColumn<Publicite, Void> editerColumn = new TableColumn<>("Editer");
    ServicePublicite servicePublicite = new ServicePublicite();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        supprimerColumn.setCellFactory(param -> new TableCell<Publicite, Void>() {
            private final Button button = new Button("Supprimer");

            {
                // Ajouter un gestionnaire d'événements pour le bouton "Supprimer"
                button.setOnAction(event -> {
                    Publicite p = getTableRow().getItem();

                    boolean deleted = servicePublicite.supprimerPublicite(p.getId());
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

        editerColumn.setCellFactory(param -> new TableCell<Publicite, Void>() {
            private final Button button = new Button("Editer");

            {
                // Ajouter un gestionnaire d'événements pour le bouton "Editer"
                button.setOnAction(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditerPublicite.fxml"));
                        Node node = loader.load();
                        EditerPubliciteController controller = loader.getController();
                        Publicite p = getTableRow().getItem();
                        controller.servicePublicite.ajouterPublicite(p);

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
            List<Publicite> publicites = servicePublicite.afficherPublicites();
            ObservableList<Publicite> listPublicites = FXCollections.observableArrayList(publicites);
            tableView.setItems(listPublicites);

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
            dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
            imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
            imageColumn.setCellFactory(col -> new ImageTableCell());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static class ImageTableCell extends TableCell<Publicite, String> {

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



