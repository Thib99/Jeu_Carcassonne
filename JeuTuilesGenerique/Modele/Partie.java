package JeuTuilesGenerique.Modele;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import JeuTuilesGenerique.Vue.GameView;

public class Partie implements Serializable {

    public Joueurs joueurs ;
    public Plateau plateau;
    public Pioche pioche;
    public Tuile aJouer;
    public String nomPartie ;
    public transient GameView gui;

    public Partie(Joueurs joueurs, Plateau plateau, Pioche pioche, String nomPartie) {
        this.joueurs = joueurs;
        this.plateau = plateau;
        this.pioche = pioche;
        this.nomPartie = nomPartie;
        // On donne aux tuiles vides du plateau et aux tuiles de la pioche un attribut partie.
        // TODO mieux gérer cette  manière de faire.
        plateau.giveTilesAGame(this);
        pioche.giveTilesAGame(this);
        premiereTuile();
    }

    public Partie(String nomPartie){
        joueurs = new Joueurs() ;
        this.nomPartie = nomPartie ;
        plateau = new Plateau();
    }

    public void setGui(GameView gui) {
        this.gui = gui;
    }

    public void premiereTuile() {
        plateau.add(pioche.pickOne(), plateau.hauteur/2, plateau.largeur/2);
    }

    public void nouvelleTuileAjouer() {
        aJouer = pioche.pickOne();
        aJouer.setPartie(this);
    }

    // Vérifie si une tuile est plaçable.
    public boolean check(int x, int y) {
        if (plateau.plateau[x][y].getClass().equals(aJouer.getClass())) return false; // L'endroit où l'on
        // veut poser une tuile doit contenir une tuile vide (classe différente).
        Bord bordAuNord = getBordAuNord(x, y);
        Bord bordAlOuest = getBordAlOuest(x, y);
        Bord bordAuSud = getBordAuSud(x, y);
        Bord bordAlEst = getBordAlEst(x, y);
        // On ne peut pas poser une tuile si elle n'est adjacente à aucune autre tuile.
        if (bordAuNord == null && bordAlOuest == null && bordAuSud == null && bordAlEst == null)
            return false;
        if (!(aJouer.nord.estCompatibleAvec(bordAuNord))) return false;
        if (!(aJouer.ouest.estCompatibleAvec(bordAlOuest))) return false;
        if (!(aJouer.sud.estCompatibleAvec(bordAuSud))) return false;
        if (!(aJouer.est.estCompatibleAvec(bordAlEst))) return false;
        return true;
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
            if (!joueurs.joueurAuTrait().isIA()) tourSuivant() ;
        }
    }

    public void tourSuivant(){
        if (partieFinie()) {gui.winMessage(); return;}
        joueurs.nextJoueurAuTrait();
        gui.updatePanelJoueurs();
        nouvelleTuileAjouer();
        gui.repaintTuileAJouer();
        gui.updateTuilesRestantes();
        if(joueurs.joueurAuTrait().isIA()) gestionTourIA();
    }

    public void gestionTourIA(){
        // empêche le joueur d'éffectuer des actions de jeu pendant le tour de l'IA
        gui.desactiverBoutonsTuileAJouer();
        plateau.disableReponsivity();

        int[] meilleureTuile = recursiveIA(plateau.largeur/2, plateau.hauteur/2, new ArrayList<Tuile>()); 
        // Le tableau meilleureTuile resterait null dans le cas où tuileAJouer n'est plaçable nulle part.
        if (meilleureTuile != null) {
            for (int i = 0; i < meilleureTuile[2]; i++) {
                aJouer.rotate(true);
            }
            // la commande jouer est exécutée une seconde plus tard sur un thread en backround pour ne pas bloquer l'interface.
            new Thread (new Runnable() {
                public void run()  {
                    try  {Thread.sleep( 1000 );}
                    catch (InterruptedException ie) {}
                    jouer(meilleureTuile[0], meilleureTuile[1]);
                    // réactivation de l'interface.
                    gui.activerBoutonsTuileAJouer();
                    plateau.enableReponsivity();
                    tourSuivant();
                }
            }).start();
        }
    }

    // Retourne un tableau d'entiers de taille 4 contenant les coordonnées de la meilleure tuile sur laquelle placer
    // tuileAJouer (par rapport au nombre de points rapportés), le nombre de rotations de tuileAjouer correspondant
    // à ce placement, et le nombre de points que rapporte ce placement.
    public int[] recursiveIA (int x, int y, List<Tuile> tuilesTestees) {
         // Pour ne pas tester 2 fois la même tuile et donc tomber dans une boucle infinie.
        if (tuilesTestees.contains(plateau.plateau[x][y])) return null;
        tuilesTestees.add(plateau.plateau[x][y]) ;
        // TODO optimiser IA en retournant directement null si la tuile en x,y est vide et ses voisines aussi (il faudra alors changer le premier argument passé à recursive IA)
        int[] meilleureTuile = null;
        // Teste si la tuileAJouer peut être posée aux coordonnées passées en argument dans les 4 sens possibles.
        for (int i = 0; i < 4; i++) {
            if (check(x, y)) {
                int pointsRapportes = nbPoints(x, y);
                if (meilleureTuile == null || pointsRapportes > meilleureTuile[3]) meilleureTuile = new int[]{x, y, i, pointsRapportes};
            }
            aJouer.rotate(true);
        }
        // recursif sur tuiles adjacentes :
        if (x != 1) {
            int[] bestAuNord = recursiveIA(x-1, y, tuilesTestees);
            if (bestAuNord != null && (meilleureTuile == null || bestAuNord[3] > meilleureTuile[3])) meilleureTuile = bestAuNord;
        } if (x != plateau.hauteur-2) {
            int[] bestAuSud = recursiveIA(x+1, y, tuilesTestees);
            if (bestAuSud != null && (meilleureTuile == null || bestAuSud[3] > meilleureTuile[3])) meilleureTuile = bestAuSud;
        } if (y != 1) {
            int[] bestAlOuest = recursiveIA(x, y-1, tuilesTestees);
            if (bestAlOuest != null && (meilleureTuile == null || bestAlOuest[3] > meilleureTuile[3])) meilleureTuile = bestAlOuest;
        } if (y != plateau.largeur-2) {
            int[] bestAlEst = recursiveIA(x, y+1, tuilesTestees);
            if (bestAlEst != null && (meilleureTuile == null || bestAlEst[3] > meilleureTuile[3])) meilleureTuile = bestAlEst;
        }
        return meilleureTuile;
    }

    public boolean partieFinie() {
        return pioche.pioche.isEmpty();
    }

    // à redéfinir dans chaque variante du jeu
    public int nbPoints(int x, int y){
        return 0;
    }

    public Joueurs getJoueurs() {return joueurs;}
    public String getNomPartie() {return nomPartie;}

    public Bord getBordAuNord(int x, int y) {
        return plateau.plateau[x-1][y].sud;
    }

    public Bord getBordAlOuest(int x, int y) {
        return plateau.plateau[x][y-1].est;
    }

    public Bord getBordAuSud(int x, int y) {
        return plateau.plateau[x+1][y].nord;
    }

    public Bord getBordAlEst(int x, int y) {
        return plateau.plateau[x][y+1].ouest;
    }

    public Pioche getPioche(){return pioche ;}
    public Plateau getPlateau(){return plateau ;}

    // A rédéfninir plus précisément pour chaque variante du jeu.
    public String cheminDossierSauvegardes() {
        return "";
    }

    public void save(){
        String path = "Sauvegardes/" + cheminDossierSauvegardes();
        // enregistrer un objet
        try {  
            // Saving of object in a file
            FileOutputStream file = new FileOutputStream(path + this.getNomPartie());
            ObjectOutputStream out = new ObjectOutputStream(file);
            // Method for serialization of object
            out.writeObject(this);
            out.close();
            file.close();
            // Success message
            System.out.println("Object has been serialized");
        } catch(IOException ex) {
            System.out.println(ex); 
        }
    }
}