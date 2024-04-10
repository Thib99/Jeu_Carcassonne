package JeuTuilesGenerique.Modele;
import java.io.Serializable;
import java.util.*;


public class Pioche implements Serializable{
    public List<Tuile> pioche;

    public Pioche(){
        pioche = new ArrayList<Tuile>() ;
    }

     // Ajoute à la pioche des tuiles vides.
    public Pioche(int nombreTuiles){
        this() ;
        for (int i = 0; i < nombreTuiles; i++) {
            pioche.add(new Tuile());
        }
    }

    public void giveTilesAGame(Partie partie) {
        for (int i = 0; i < pioche.size(); i++) {
            pioche.get(i).setPartie(partie);
        }
    }

    // Renvoie un élément de la pioche, ou null si cette dernière est vide.
    public Tuile pickOne(){
        if (!pioche.isEmpty()) return pioche.remove(0);
        return null ;
    }

    public void add(Tuile tuile){
        pioche.add(tuile) ;
        Collections.shuffle(pioche);
    }

    public boolean isEmpty() {
        return pioche.isEmpty();
    }

    public int getSize() {
        return pioche.size();
    }
}
