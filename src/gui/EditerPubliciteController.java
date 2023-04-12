package gui;

import Entities.Evenement;
import Entities.Publicite;
import Services.ServicePublicite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class EditerPubliciteController implements Initializable {

    ServicePublicite servicePublicite = new ServicePublicite();
    @FXML
    private TextField fxNom;


    @FXML
    private TextField imageField;
    @FXML
    private DatePicker fxDateDebut;
    @FXML
    private DatePicker fxDateFin;
    @FXML
    private TextField dateFinField;
    @FXML
    private Button saveButton;

    private Publicite publicite;

    @FXML
    private Button btnIcon;

    @FXML
    private TextField fxImage;
    @FXML
    private ImageView imageaffi;
    @FXML
    private ChoiceBox<String> choiceBoxEvenement;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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


// Add the ImageView object to your user interface
// For example, if you have a VBox layout, you can add the ImageView like this:
        imageaffi.setImage(i);
    }


    public void savePublicite(ActionEvent actionEvent) {

        String nom = fxNom.getText();
        String image = fxImage.getText();
        LocalDate localDateDebut = fxDateDebut.getValue();
        LocalDate localDateFin = fxDateFin.getValue();
        Integer evenementNom = Integer.parseInt(choiceBoxEvenement.getValue());

        Instant instantDebut = Instant.from(localDateDebut.atStartOfDay(ZoneId.systemDefault()));
        Instant instantFin = Instant.from(localDateFin.atStartOfDay(ZoneId.systemDefault()));

        Date dateDebut = Date.from(instantDebut);
        Date dateFin = Date.from(instantFin);
        // Vérifier si tous les champs obligatoires sont remplis
        if (nom.isEmpty() || image.isEmpty() || dateDebut == null || dateFin == null || evenementNom == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }
        Publicite event = new Publicite();
        event.setNom(nom);
        event.setImage(image);
        java.sql.Date sqlDateDebut = new java.sql.Date(dateDebut.getTime());
        event.setDateDebut(sqlDateDebut);

        java.sql.Date sqlDateFin = new java.sql.Date(dateFin.getTime());
        event.setDateFin(sqlDateFin);
        publicite.setIdevenement(Integer.parseInt(evenementNom.toString()));

        // Ajouter l'objet Event à une liste
        servicePublicite.modifierPublicite(event);

        // Afficher un message de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Publicité a été modifiée avec succès.");
        alert.showAndWait();
    }





}

















