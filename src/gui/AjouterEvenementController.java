package gui;

import Entities.Evenement;
import Services.ServiceEvenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

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

public class AjouterEvenementController implements Initializable {

    ServiceEvenement serviceEvenement=new ServiceEvenement();



    @FXML
    private Button btnIcon;

    @FXML
    private Button fxButton;

    @FXML
    private DatePicker fxDate;

    @FXML
    private TextField fxDescription;

    @FXML
    private TextField fxImage;

    @FXML
    private TextField fxLieu;

    @FXML
    private TextField fxNom;

    @FXML
    private ImageView imageaffi;

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
        imageaffi.setImage(i);    }
    @FXML
    public void saveEvenement(ActionEvent actionEvent) {


        String nom = fxNom.getText();
        String description = fxDescription.getText();
        String lieu = fxLieu.getText();
        String image = fxImage.getText();
        LocalDate localDate = fxDate.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        // Vérifier si tous les champs obligatoires sont remplis
        if (nom.isEmpty() || description.isEmpty() || lieu.isEmpty() || image.isEmpty() || date == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        // Créer un objet Event avec les valeurs saisies
        Evenement event = new Evenement();
        event.setNom(nom);
        event.setDescription(description);
        event.setLieu(lieu);
        event.setImageUrl(image);
        event.setDate_evenement(date);

        // Ajouter l'objet Event à une liste
        serviceEvenement.ajouterEvenement(event);

        // Afficher un message de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("L'événement a été ajouté avec succès.");
        alert.showAndWait();


    }
}
