package Entities;

public class Ticket {
    private int id;
    private float prix;
    private String type;
    private boolean etat;
    private int evenement_id;
    private int nbrTicket;
    Evenement evenement;

    public Ticket(int id, float prix, String type, boolean etat, int evenement_id, int nbrTicket) {
        this.id = id;
        this.prix = prix;
        this.type = type;
        this.etat = etat;
        this.evenement_id = evenement_id;
        this.nbrTicket = nbrTicket;
    }




    public Ticket(float prix, String type, boolean etat, int nbrTicket) {
        this.prix = prix;
        this.type = type;
        this.etat = etat;
        this.evenement_id = evenement_id;
        this.nbrTicket = nbrTicket;
    }




    public Ticket() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public int getEvenement_id() {
        return evenement_id;
    }

    public void setEvenement_id(int evenement_id) {
        this.evenement_id = evenement_id;
    }

    public int getNbrTicket() {
        return nbrTicket;
    }

    public void setNbrTicket(int nbrTicket) {
        this.nbrTicket = nbrTicket;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", prix=" + prix +
                ", type='" + type + '\'' +
                ", etat=" + etat +
                ", evenement_id=" + evenement_id +
                ", nbrTicket=" + nbrTicket +
                '}';
    }


    public Evenement getEvenement() {

        return evenement;
    }
}
