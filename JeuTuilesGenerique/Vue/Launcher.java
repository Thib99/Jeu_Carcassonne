package JeuTuilesGenerique.Vue;

import javax.swing.JFrame;

import JeuCarcassonne.PartieCarcassonne;
import JeuCarcassonne.PiocheCarcassonne;
import JeuCarcassonne.VueCarcassonne;
import JeuDominos.PartieDominos;
import JeuDominos.PiocheDominos;
import JeuDominos.VueDominos;
import JeuTuilesGenerique.Modele.Joueurs;
import JeuTuilesGenerique.Modele.Plateau;

import java.awt.* ;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream; 

public class Launcher extends JFrame{

    public void createWinwow() {
        setVisible(true);
        setTitle("JEU");
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize(); // Récupère taille de l'écran utilisateur.
        setSize((int) size.getWidth(), (int) size.getHeight()); // Met la fenêtre en plein écran.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void launch() {
        createWinwow();
        new Menu(this); // On passe au menu un objet de cette classe pour qu'il puisse appeler
        // launchCarcassonne ou launchDomino.
    }

    public void launchRunningGame(String cheminPartie) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(cheminPartie);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj = ois.readObject();
        if (obj instanceof PartieCarcassonne) {
            VueCarcassonne vueC = new VueCarcassonne((PartieCarcassonne) obj);
            vueC.setLauncher(this);
            getContentPane().add(vueC.conteneurGlobal);
        } else {
            VueDominos vueD = new VueDominos((PartieDominos) obj);
            vueD.setLauncher(this);
            getContentPane().add(vueD.conteneurGlobal);
        }
        ois.close();
    }

    public void launchCarcassonne(Joueurs joueurs, String nomPartie) throws IOException {
        Plateau plateau = new Plateau();
        PiocheCarcassonne piocheC = new PiocheCarcassonne();
        PartieCarcassonne partieC = new PartieCarcassonne(joueurs, plateau, piocheC, nomPartie);
        VueCarcassonne vueC = new VueCarcassonne(partieC);
        vueC.setLauncher(this);
        getContentPane().add(vueC.conteneurGlobal);
    }

    public void launchDominos(Joueurs joueurs, String nomPartie) {
        Plateau plateau = new Plateau();
        PiocheDominos piocheD = new PiocheDominos(true);
        PartieDominos partieD = new PartieDominos(joueurs, plateau, piocheD, nomPartie);
        VueDominos vueD = new VueDominos(partieD);
        vueD.setLauncher(this);
        getContentPane().add(vueD.conteneurGlobal);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Lancer l'intégralité de l'interface de jeu
            new Launcher().launch();

        // Lancer une partie sans passer par le menu:
            // Launcher l = new Launcher();
            // l.createWinwow();

            // Lancer une partie Carcassonne nommée "Partie1"
            // l.launchRunningGame("Sauvegardes/Carcassonne/Partie1");

            // Lancer une partie Dominos nommée "partieDominos1" contenant deux joueurs, Pierre et Paul.
                // Joueurs j1 = new Joueurs();
                // j1.addPlayer(j1.new Joueur("Pierre", false));
                // j1.addPlayer(j1.new Joueur("Paul", true));
                // l.launchDominos(j1, "partieDominos1");

            // Lancer une partie Carcassonne nommée "partieCarcassonne1" contenant deux joueurs,
            // Bob et Jacques.
                // Joueurs j2 = new Joueurs();
                // j2.addPlayer(j2.new Joueur("Bob", false));
                // j2.addPlayer(j2.new Joueur("Jacques", false));
                // l.launchCarcassonne(j2, "partieCarcassone1");
    }
}
