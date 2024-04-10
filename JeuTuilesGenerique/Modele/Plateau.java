package JeuTuilesGenerique.Modele;

import java.io.Serializable;


public class Plateau implements Serializable{

    public Tuile[][] plateau;
    public int hauteur;
    public int largeur;
    
    public Plateau () {
        hauteur = 5;
        largeur = 5;
        // On remplit le plateau de tuiles vides à l'initialisation.
        plateau = newplateauFullTuileVide(hauteur, largeur) ;
    }

    private Tuile[][] newplateauFullTuileVide(int hauteur, int largeur){
        this.hauteur = hauteur;
        this.largeur = largeur;
        plateau = new Tuile[hauteur][largeur];
        // On remplit le plateau de tuiles vides à l'initialisation.
        for (int i = 0; i < (hauteur); i++) {
            for (int j = 0; j < largeur; j++) {
                Tuile t = new Tuile();
                t.setPlateau(this);
                t.setCoordonnées(i, j);
                plateau[i][j] = t;
            }
        }
        return plateau ;
    }

    public void giveTilesAGame(Partie partie) {
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                plateau[i][j].setPartie(partie);
            }
        }
    }

    public void disableReponsivity() {
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                plateau[i][j].disableResponsivity();
            }
        } 
    }

    public void enableReponsivity() {
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                plateau[i][j].enableResponsivity();
            }
        } 
    }
    
    public boolean add(Tuile tuile, int x, int y) {
        boolean aggrandissement = false;
        if (x == 1){
            aggrandissement = true;
            Tuile[][] tab = plateau.clone() ;
            plateau = newplateauFullTuileVide(plateau.length+1, plateau[0].length) ;
            for (int i = 0; i < tab.length; i++){
                for (int j = 0; j < tab[0].length ;j++){
                    plateau[i+1][j] = tab[i][j];
                    plateau[i+1][j].setCoordonnées(i+1, j); 
                }
            }
            x = x+1;
        }
        else if (x == hauteur-2){
            aggrandissement = true;
            Tuile[][] tab = plateau.clone() ;
            plateau = newplateauFullTuileVide(plateau.length+1, plateau[0].length) ;
            for (int i = 0; i < tab.length; i++){
                for (int j = 0; j < tab[0].length ;j++){
                    plateau[i][j] = tab[i][j] ;
                    plateau[i][j].setCoordonnées(i, j);
                }
            }
        }
        if (y == 1){
            aggrandissement = true;
            Tuile[][] tab = plateau.clone() ;
            plateau = newplateauFullTuileVide(plateau.length, plateau[0].length+1) ;
            
            for (int i = 0; i < tab.length; i++){
                for (int j = 0; j < tab[0].length ;j++){
                    plateau[i][j+1] = tab[i][j] ;
                    plateau[i][j+1].setCoordonnées(i, j+1);
                }
            }
            y = y +1 ;
        }
        else if (y == largeur-2){
            aggrandissement = true;
            Tuile[][] tab = plateau.clone() ;
            plateau = newplateauFullTuileVide(plateau.length, plateau[0].length+1) ;
            
            for (int i = 0; i < tab.length; i++){
                for (int j = 0; j < tab[0].length ;j++){
                    plateau[i][j] = tab[i][j] ; 
                    plateau[i][j].setCoordonnées(i, j);
                }
            }
        }
        // ajout sur le plateau
        plateau[x][y] = tuile;
        tuile.setCoordonnées(x, y);
        tuile.setPlateau(this);
        return aggrandissement;
    }
}