package JeuDominos;

import JeuTuilesGenerique.Modele.*;

public class PartieDominos extends Partie {

    public PartieDominos(Joueurs joueurs, Plateau plateau, Pioche pioche, String nomPartie) {
        super(joueurs, plateau, pioche, nomPartie);
    }
    
    public PartieDominos(String nomPartie){
        super(nomPartie) ;
        pioche = new PiocheDominos(false);
        nouvelleTuileAjouer();
    }

    // Retourne le nombre de points que donnerait le placement de la tuileAJouer aux coordonnées x,y.
    public int nbPoints(int x, int y){
        int pts = 0; 
        if (getBordAlEst(x,y) != null) pts += ((BordDomino)aJouer.est).sommeBords();
        if (getBordAlOuest(x,y) != null) pts += ((BordDomino)aJouer.ouest).sommeBords();
        if (getBordAuNord(x,y) != null) pts += ((BordDomino)aJouer.nord).sommeBords();
        if (getBordAuSud(x,y) != null) pts += ((BordDomino)aJouer.sud).sommeBords();
        return pts ;
    }

    public String cheminDossierSauvegardes() {
        return "Dominos/";
    }
}
