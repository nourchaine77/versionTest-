package gui;

import Entities.Evenement;
import Entities.Publicite;
import Entities.Ticket;
import Services.ServiceEvenement;
import Services.ServicePublicite;
import Services.ServiceTicket;
import javafx.collections.FXCollections;
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
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EditerPubliciteController implements Initializable {

    public TextField fxDescription;
    public TextField fxTitre;
    ServicePublicite servicePublicite = new ServicePublicite();
    @FXML
    private TextField fxNom;

    private final ServicePublicite servicePub = new ServicePublicite();
    @FXML
    private TextField imageField;
    @FXML
    private DatePicker fxDateDebut;
    @FXML
    private DatePicker fxDateFin;
    @FXML
    private TextField dateFinField;
    @FXML
    private Button fxButton;

    private Publicite publicite;

    @FXML
    private Button btnIcon;

    @FXML
    private TextField fxImage;
    @FXML
    private ImageView imageaffi;
    @FXML
    private ChoiceBox<Evenement> choiceBoxEvenement;
    ServiceEvenement serviceEvenement = new ServiceEvenement();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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


// Add the ImageView object to your user interface
// For example, if you have a VBox layout, you can add the ImageView like this:
        imageaffi.setImage(i);
    }


    public void savePublicite(ActionEvent actionEvent) {

        String nom = fxNom.getText();
        String image = fxImage.getText();
        LocalDate localDateDebut = fxDateDebut.getValue();
        LocalDate localDateFin = fxDateFin.getValue();
        Evenement evenementNom = choiceBoxEvenement.getValue();
        Instant instantDebut = Instant.from(localDateDebut.atStartOfDay(ZoneId.systemDefault()));
        Instant instantFin = Instant.from(localDateFin.atStartOfDay(ZoneId.systemDefault()));

        Date dateDebut = Date.from(instantDebut);
        Date dateFin = Date.from(instantFin);
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

        // Vérifier si tous les champs obligatoires sont remplis
        if (nom.isEmpty() || image.isEmpty() || dateDebut == null || dateFin == null || evenementNom == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }
        java.sql.Date sqlDateFin = new java.sql.Date(dateFin.getTime());
        java.sql.Date sqlDateDebut = new java.sql.Date(dateDebut.getTime());

        publicite.setNom(nom);
        publicite.setImage(image);
        publicite.setDateDebut(sqlDateDebut);
        publicite.setDateFin(sqlDateFin);
        publicite.setIdevenement(evenementNom.getId());


        // Ajouter l'objet Event à une liste
        boolean isModificationSucceeded = servicePublicite.modifierPublicite(publicite);

        if (isModificationSucceeded) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La pub a été modifiée avec succès.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Une erreur est survenue ! ");
            alert.showAndWait();
        }
    }

    Publicite pub = new Publicite();

    public void setPublicite(Publicite publicite) {
pub=publicite;
this.publicite = publicite;
        if (fxNom != null) {
            fxNom.setText(publicite.getNom());
        }
        fxImage.setText(publicite.getImage());
        fxDateDebut.setValue(publicite.getDateDebut().toLocalDate());
        fxDateFin.setValue(publicite.getDateFin().toLocalDate());
        Evenement evenement = publicite.getEvenement();
        if (evenement != null) {
            choiceBoxEvenement.getItems().stream()
                    .filter(e -> e.equals(evenement))
                    .findFirst()
                    .ifPresent(e -> choiceBoxEvenement.setValue(e));
        }

    }
}
















