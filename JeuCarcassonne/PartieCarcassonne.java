package JeuCarcassonne;

import JeuTuilesGenerique.Modele.*;

public class PartieCarcassonne extends Partie{

    public PartieCarcassonne(Joueurs joueurs, Plateau plateau, Pioche pioche, String nomPartie) {
        super(joueurs, plateau, pioche, nomPartie);
        // Les joueurs d'une partie Carcassone ont au départ 7 pions.
        for (int i = 0; i < joueurs.players.size(); i++) {
            joueurs.players.get(i).setNbrPion(7);
        }
    }
    
    public PartieCarcassonne(String nomPartie){
        super(nomPartie) ;
        pioche = new Pioche(72);
        nouvelleTuileAjouer();
    }

    public int nbPoints(Tuile t, int x, int y){
        return 0 ;
    }

    public String cheminDossierSauvegardes() {
        return "Carcassonne/";
    }
    
    // Vérifie si une tuile est plaçable, la place le cas échéant et prépare le tour suivant.
    public void jouer(int x, int y) {
        if (check(x, y)) { 
            // l'appel de nbPoints à besoin d'être mis avant l'ajout de la tuile au plateau
            // car les coordonnées de la tuile peuvent changer si le plateau devient plus grand.
            joueurs.joueurAuTrait().addScore(nbPoints(x, y));
    
            // aggrandit le plateau si la tuile est placée en bordure de la grille du GUI.
            if (plateau.add(aJouer, x, y)) {plateau.giveTilesAGame(this); gui.repaintGrille();}
            else gui.updateGrille(aJouer, x, y);
            // Spécificité Jeu Carcassonne
            if(!joueurs.joueurAuTrait().isIA() && joueurs.joueurAuTrait().getNbrPion() != 0) {
                ((VueCarcassonne)gui).demanderSiPosePion();
                return ;
            }
            if (!joueurs.joueurAuTrait().isIA()) tourSuivant() ;
        }
    }

    // TODO règles Carcassonne.
}
