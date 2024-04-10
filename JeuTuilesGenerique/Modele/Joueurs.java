package JeuTuilesGenerique.Modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;

public class Joueurs implements Serializable{
    
    public List<Joueur> players ; 
    private static int nombreMaximalDeJoueur = 5 ;
    private static int index = 0;
    Color[] couleurs;

    public Joueurs(){
        players = new ArrayList<Joueur>() ;
        couleurs = new Color[]{Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE};
    }

    public Joueurs (int nbJoueurs) {
        this();
        nbJoueurs = Math.max(nbJoueurs, 2); // Nombre de joueurs min: 2.
        nbJoueurs = Math.min(nbJoueurs, 5); // Nombre de joueurs max: 5.
        for (int i = 0; i < nbJoueurs; i++) {
            players.add( new Joueur(Character.toString(65+i), false) );
        }
    }

    public int nbJoueurs() {
        return players.size();
    }

    public boolean listfull(){return players.size() >= nombreMaximalDeJoueur ; }

    public boolean addPlayer(String nom, boolean IA){
        if (players.size() < nombreMaximalDeJoueur ){
            players.add(new Joueur( nom,  IA)) ;
            return true ;
        }
        return false ;
    }

    public boolean addPlayer(Joueur playeur){
        if (players.size() <= nombreMaximalDeJoueur ){
            players.add(playeur) ;
            return true ;
        }
        return false ;
    }

    public int getIndex(){
        index = index%4 ;
        return  index++ ;
    }

    public Joueur getPlayers (int index) {return players.get(index);}
    public List<Joueur> getList() {return players;}

    public Joueur getLast(){
        if (players.size() != 0 ){
            return players.get(players.size() - 1) ;
        }
        return null ;
    }

    public boolean nomLibre(String name){
        if (name.equals("")) return false ;
        for (Joueur joueur : players) {
            if (name.equals(joueur.getName())) return false ;
        }
        return true ;
    }

    public Joueur joueurAuTrait() {
        for (Joueur j: players) {
            if (j.auTrait) return j;
        }
        return null;
    }

    public Joueur getSuivant(Joueur joueur) {
        for(int i = 0; i < players.size(); i++) {
            if (players.get(i).equals(joueur)) {
                if (i == players.size()-1) return players.get(0);
                return players.get(i+1);
            }
        }
        return null;
    }

    public void nextJoueurAuTrait() {
        Joueur joueurAuTrait = joueurAuTrait();
        if (joueurAuTrait == null) players.get(0).setAuTrait(true);
        else {
            joueurAuTrait.setAuTrait(false);
            getSuivant(joueurAuTrait).setAuTrait(true);
        }
    }

    public Joueur vainqueur() {
        Joueur vainqueur = null;
        for (Joueur j: players) {
            if (vainqueur == null || j.score > vainqueur.score) vainqueur = j;
        }
        return vainqueur;
    }

    public class Joueur implements Serializable{

        public int score ;
        public final String nom ;
        private int NbrPion ;
        private boolean IA ;  
        boolean auTrait;
        Color couleur;
    
        public Joueur(String nom, boolean IA){
            this.nom = nom ;
            score = 0 ;
            NbrPion = 0 ;
            this.IA = IA ;
            couleur = Joueurs.this.couleurs[getIndex()];
        }

        public void setAuTrait(boolean auTrait) {
            this.auTrait = auTrait;
        }

        public Color getCouleur() {
            return couleur;
        }
    
        public int getScore() {return score;}
    
        public void addScore(int ajout) {score += ajout;}
    
        public String getName() {return nom;}
    
        public int getNbrPion() {
            return NbrPion;
        }
        public void enleverUnPion(){
            NbrPion--;
        }
    
        public void setNbrPion(int nbrPion) {
            NbrPion = nbrPion;
        }
    
        public boolean isIA(){return IA ;}
        public void setIA(boolean IA){this.IA = IA ;}

        public class PanelJoueur extends JPanel {
            
            JPanel conteneurHaut;
            JLabel nom;
            JLabel points;
            JPanel conteneurBas;
            JLabel pionsRestants;

            public PanelJoueur() {
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
                // setBackground(Joueur.this.couleur);
                setLayout(new GridLayout(2,1));
                // JPanel conteneurHaut
                conteneurHaut = new JPanel();
                conteneurHaut.setLayout(new BoxLayout(conteneurHaut, BoxLayout.LINE_AXIS));
                nom = new JLabel();
                points = new JLabel();
                nom.setFont(new Font("Arial", Font.BOLD, 20));
                points.setFont(new Font("Arial", Font.BOLD, 20));
                conteneurHaut.add(Box.createRigidArea(new Dimension(20,0)));
                conteneurHaut.add(nom);
                conteneurHaut.add(Box.createHorizontalGlue());
                conteneurHaut.add(points);
                conteneurHaut.add(Box.createRigidArea(new Dimension(20,0)));
                add(conteneurHaut);
                // JPanel ConteneurBas utilisé pour les jeux à pions.
                conteneurBas = new JPanel();
                conteneurBas.setLayout(new BoxLayout(conteneurBas, BoxLayout.LINE_AXIS));
                add(conteneurBas);
                if (Joueur.this.auTrait) nom.setText("--> " + Joueur.this.getName());
                else nom.setText(Joueur.this.getName());
                points.setText(String.valueOf("    " +Joueur.this.getScore()) + "pts");
            }

            public void updatePanel() {
                if (Joueur.this.auTrait) nom.setText("--> " + Joueur.this.getName());
                else nom.setText(Joueur.this.getName());
                points.setText(String.valueOf("    " +Joueur.this.getScore()) + "pts");
                revalidate();
                repaint();
            }

            public void affichePionsRestants() {
                conteneurBas.removeAll();
                conteneurBas.add(Box.createRigidArea(new Dimension(20,0)));
                pionsRestants = new JLabel("Pions restants : " + String.valueOf(Joueur.this.getNbrPion()));
                conteneurBas.add(pionsRestants);
                conteneurBas.revalidate();
                conteneurBas.repaint();
            }
        }
    }
}


