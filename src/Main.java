import Entities.Evenement;
import Entities.Publicite;
import Services.ServiceEvenement;
import Services.ServicePublicite;
import utils.Myconnexion;

import java.sql.Date;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello world!");
        Myconnexion m = Myconnexion.getInstance();
      /*  ServiceEvenement serviceEvenement=new ServiceEvenement();
        Evenement evenement = new Evenement(1,"Test", "Description test", "Lieu test", "https://example.com/image.jpg", new Date(System.currentTimeMillis()));

        // Créer une instance de la classe ServiceEvenement

        // Ajouter l'événement à la base de données
        serviceEvenement.ajouterEvenement(evenement);
        System.out.println(serviceEvenement.afficherEvenements());

        // Modifier l'événement dans la base de données
        evenement.setDescription("Description modifiée");
        serviceEvenement.modifierEvenement(evenement);
        System.out.println(serviceEvenement.afficherEvenements());


        // Supprimer l'événement de la base de données
        serviceEvenement.supprimerEvenement(evenement.getId());
        System.out.println(serviceEvenement.afficherEvenements());





           // Publicite p=Publicite(1,"ezdzed","zedzd", new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()),1)


            // Afficher la liste des événements dans la base de données */


        Publicite p1 = new Publicite("Publicite 1", "image1.jpg",new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()), 2);
        Publicite p2 = new Publicite("Publicite 2", "image2.jpg",new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 2);

        // Add the Publicite objects
        ServicePublicite service = new ServicePublicite();
        service.ajouterPublicite(p1);
        service.ajouterPublicite(p2);
        System.out.println(service.afficherPublicites());

        // Modify the first Publicite object
        p1.setNom("New Publicite 1");
        service.modifierPublicite(p1);
        System.out.println(service.afficherPublicites());


        // Delete the second Publicite object
        service.supprimerPublicite(p2.getId());

    }
}