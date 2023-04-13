package gui;

import Entities.Evenement;
import Entities.Publicite;
import Services.ServiceEvenement;
import Services.ServicePublicite;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AjouterPubliciteController {

    @FXML
    private TextField fxNom;
    @FXML
    private TextField fxImage;
    @FXML
    private DatePicker fxDateDebut;

    @FXML
    private DatePicker fxDateFin;

    @FXML
    private ChoiceBox<Evenement> choiceBoxEvenement;

    @FXML
    private Button btnIcon;

    @FXML
    private ImageView imageaffi;

    private final ServicePublicite servicePub = new ServicePublicite();

    @FXML
    public void initialize() throws SQLException {
        // Ajouter les noms des événements dans la ChoiceBox
        ServiceEvenement serviceEvenement = new ServiceEvenement();
        List<Evenement> evenements = null;
        try {
            evenements = serviceEvenement.afficherEvenements();
            choiceBoxEvenement.setItems(FXCollections.observableList(evenements));


                } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(btnIcon.getScene().getWindow());
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            Path sourcePath = selectedFile.toPath();
            Path destinationPath = Paths.get("src", "image", fileName);
            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied to: " + destinationPath.toAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(selectedFile.getName());
        fxImage.setText(selectedFile.getName());

        Path destinationPath = Paths.get("src", "image", fxImage.getText());

        // Create a file object from the destination path
        File file = destinationPath.toFile();

        // Create an Image object from the file
        Image i = new Image(file.toURI().toString());

        // Set the image to an ImageView object
        imageaffi.setImage(i);
    }

    @FXML
    void savePublicite(ActionEvent actionEvent) {
        String nom = fxNom.getText();
        String image = fxImage.getText();
        LocalDate localDateDebut = fxDateDebut.getValue();
        LocalDate localDateFin = fxDateFin.getValue();
        Evenement evenementNom = choiceBoxEvenement.getValue();
        if (nom.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Le champ nom ne peut pas être vide.");
            alert.showAndWait();
            return;
        }
        int idEvenement = evenementNom.getId();
        try {
            if (servicePub.hasPublicite(idEvenement)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Cet événement a déjà une publicité associée.");
                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Vérifier si tous les champs obligatoires sont remplis
        if (nom.isEmpty() || image.isEmpty() || localDateDebut == null || localDateFin == null || evenementNom == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }
        LocalDate today = LocalDate.now();
        if (localDateDebut.isBefore(today)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("La date de début doit être antérieure ou égale à la date d'aujourd'hui.");
            alert.showAndWait();
            return;
        }
        if (localDateFin.isBefore(today)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("La date de fin doit être postérieure ou égale à la date d'aujourd'hui.");
            alert.showAndWait();
            return;
        }

//// Vérifier si l'événement sélectionné a déjà une publicité associée


        List<Evenement> evenements = null;
        try {
            evenements = servicePub.getEvenements();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        Date dateDebut = java.sql.Date.valueOf(localDateDebut);
        Date dateFin = java.sql.Date.valueOf(localDateFin);

        Publicite publicite = new Publicite(nom, image, dateDebut, dateFin, idEvenement);

        servicePub.ajouterPublicite(publicite);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Publicité ajoutée avec succès.");
        alert.showAndWait();
    }
}