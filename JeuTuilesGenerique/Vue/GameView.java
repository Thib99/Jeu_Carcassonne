package JeuTuilesGenerique.Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.io.Serializable;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;
import java.util.List;

import JeuTuilesGenerique.Modele.Partie;
import JeuTuilesGenerique.Modele.Tuile;
import JeuTuilesGenerique.Modele.Joueurs.Joueur;
import JeuTuilesGenerique.Modele.Joueurs.Joueur.PanelJoueur;

public class GameView implements Serializable{

    public Partie partie;
    Launcher fenetre ;
    public JPanel conteneurGlobal;
    JPanel bandeauSup;
    JPanel conteneurTitre;
    public JLabel titre;
    JPanel conteneurBoutons;
    JButton infos;
    JButton sauvegarder;
    JPanel bordureGauche;
    JPanel bordureDroite;
    JPanel bandeauInf;
    JLabel credits;
    public JPanel coeur;
    public JPanel conteneurGrille;
    JPanel grille;
    public JPanel conteneurInfos;
    JPanel conteneurInfosCoup;
    public JPanel conteneurTuileAJouer;
    JButton retourMenu ;
    JButton quitter ;
    JButton defausser ;
    JButton rotationDroite ;
    JButton rotationGauche ;
    JPanel conteneurButtonsRotate ;
    JPanel conteneurInfosCoupMilieu;
    JLabel tuilesRestantes;
    public List<PanelJoueur> panelJoueurs;
    JLabel winMessage;

    public GameView(Partie partie){

        this.partie = partie;
        partie.setGui(this);
        // JPanel conteneurGlobal
        conteneurGlobal = new JPanel();
        conteneurGlobal.setLayout(new BorderLayout());

        // JPanel BandeauSup (nom du jeu + 2 boutons)
        bandeauSup = new JPanel();
        bandeauSup.setLayout(new BoxLayout(bandeauSup, BoxLayout.LINE_AXIS));
        bandeauSup.setBorder(BorderFactory.createEmptyBorder(20,0,10,0));
        conteneurGlobal.add(bandeauSup, BorderLayout.PAGE_START);

        // Bouton quiter, retour 
            quitter = new ButtonImage("croix.png", new Rectangle(40,40, 0, 0 )); 
            retourMenu = new ButtonImage("retour50p.png", new Rectangle(40,40, 0, 0 ));
            bandeauSup.add(quitter);
            bandeauSup.add(retourMenu);

            // actions JButtons quitter et menu 
            quitter.addActionListener(event -> {
                partie.save();
                System.exit(0);
            });
            retourMenu.addActionListener(event -> {
                partie.save() ;
                fenetre.remove(conteneurGlobal);
                new Menu(fenetre) ;
                fenetre.repaint() ;
                fenetre.revalidate() ;
            });

            // JPanel conteneurTitre et JLabel titre
            conteneurTitre = new JPanel();
            titre = new JLabel("Jeu à Tuiles");
            titre.setFont(new Font("Arial", Font.BOLD, 36));
            conteneurTitre.add(titre);
            bandeauSup.add(conteneurTitre);
            bandeauSup.add(Box.createHorizontalGlue());

            // JPanel conteneurBouttons, JButton infos et sauvegarder
            conteneurBoutons = new JPanel();
            bandeauSup.add(conteneurBoutons);
            infos = new JButton("?");
            conteneurBoutons.add(infos);
            sauvegarder = new JButton("save");
            conteneurBoutons.add(sauvegarder);

            // ajout de l'action de sauvegarder :
                sauvegarder.addActionListener(event -> {
                    partie.save() ;
                });
        
        // JPanel bordureGauche
        bordureGauche = new JPanel();
        conteneurGlobal.add(bordureGauche, BorderLayout.LINE_START);

        // JPanel bordureDroite
        bordureDroite = new JPanel();
        conteneurGlobal.add(bordureDroite, BorderLayout.LINE_END);

        // JPanel bandeauInf (sert de bordure en bas + crédits)
        bandeauInf = new JPanel();
        bandeauInf.setLayout(new FlowLayout(FlowLayout.RIGHT));
        credits = new JLabel("Credits: A. Tomasi et T. Poux");
        bandeauInf.add(credits);
        conteneurGlobal.add(bandeauInf, BorderLayout.PAGE_END);
        
        // JPanel coeur
        coeur = new JPanel();
        conteneurGlobal.add(coeur, BorderLayout.CENTER);
        coeur.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;

            // JPanel conteneurGrille
            conteneurGrille = new JPanel();
            gbc.weightx = 10; // Grandit 4 fois plus vite qu'un weigthx = 1.
            coeur.add(conteneurGrille, gbc);
            conteneurGrille.setLayout(new BoxLayout(conteneurGrille, BoxLayout.X_AXIS));
            conteneurGrille.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
            conteneurGrille.setMaximumSize(conteneurGrille.getSize());

            // JPanel grille
            repaintGrille();

            // JPanel conteneurInfos
            conteneurInfos = new JPanel();
            conteneurInfos.setLayout(new GridLayout(partie.joueurs.nbJoueurs() + 1,1,-1,-1));
            gbc.weightx = 1;
            coeur.add(conteneurInfos, gbc);

                // Les différents PanelJoueur
                panelJoueurs = new ArrayList<PanelJoueur>() ;
                PanelJoueur tmp;
                for (int i = 0; i < partie.joueurs.nbJoueurs(); i++) {
                    tmp =  partie.joueurs.players.get(i).new PanelJoueur();
                    conteneurInfos.add(tmp);
                    panelJoueurs.add(tmp);
                }

                // JPanel conteneurInfosCoup
                conteneurInfosCoup = new JPanel();
                conteneurInfosCoup.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                conteneurInfosCoup.setLayout(new BoxLayout(conteneurInfosCoup, BoxLayout.LINE_AXIS));
                conteneurInfos.add(conteneurInfosCoup);

                    // Buttons defausser
                    defausser = new JButton("Jeter la tuile");
                    defausser.addActionListener(evnt -> {
                        partie.tourSuivant();
                    });
                    defausser.setBounds(5, 100, defausser.getPreferredSize().width, defausser.getPreferredSize().height);
                    conteneurInfosCoup.add(defausser);
            
                    // JPanel conteneurInfosCoupMilieu
                    conteneurInfosCoupMilieu = new JPanel();
                    conteneurInfosCoupMilieu.setLayout(new BoxLayout(conteneurInfosCoupMilieu, BoxLayout.PAGE_AXIS));
                    conteneurInfosCoup.add(conteneurInfosCoupMilieu);

                        // JPanel conteneurButtonsRotate et JButtons rotationDroite et rotationGauche
                        conteneurButtonsRotate = new JPanel() ;
                        conteneurButtonsRotate.setLayout(new FlowLayout());
                        conteneurButtonsRotate.setMaximumSize(new Dimension(200, 50));
                        rotationDroite = new JButton("-->");
                        rotationGauche = new JButton("<--");
                        rotationDroite.addActionListener(event -> {
                            partie.aJouer.rotate(true) ;
                        });
                        rotationGauche.addActionListener(event -> {
                            partie.aJouer.rotate(false) ;
                        });
                        conteneurButtonsRotate.add(rotationGauche);
                        conteneurButtonsRotate.add(rotationDroite);
                        conteneurInfosCoupMilieu.add(conteneurButtonsRotate); 

                        // JPanel conteneurTuileAJouer
                        conteneurTuileAJouer = new JPanel();
                        conteneurTuileAJouer.setLayout(new BorderLayout());
                        conteneurTuileAJouer.setMaximumSize(new Dimension(200, 200));
                        conteneurTuileAJouer.setBorder(new EmptyBorder(0, 0, 10, 0));
                        conteneurInfosCoupMilieu.add(conteneurTuileAJouer);

                    // JPanel conteneurInfosCoupDroite et JLabel tuiles restantes
                    tuilesRestantes = new JLabel();
                    tuilesRestantes.setMaximumSize(new Dimension(50, 50));
                    tuilesRestantes.setFont(new Font("Arial", Font.BOLD, 16));
                    tuilesRestantes.setHorizontalAlignment(0);
                    conteneurInfosCoup.add(tuilesRestantes);
                    tuilesRestantes.setBorder(new EmptyBorder(0,30,0,0));
        
        // Lorsque le GUI est prêt, on lance la partie
        partie.tourSuivant();

        // TODO à mieux gérer aussi  ici
        // au cas ou on lance une partie sauvegarder,
        // cela permet de redéfinir les variables externes des tuiles
        partie.getPlateau().giveTilesAGame(partie);
    }

    public Partie getPartie() {return partie;}
    public void setLauncher(Launcher l) {fenetre = l;}
    public Launcher getLauncher() {return fenetre;}
    public JButton getDefausser() {return defausser;}
    public JButton getRotationDroite() {return rotationDroite;}
    public JButton getRotationGauche() {return rotationGauche;}

    // Affiche visuellement la tuile qui est à jouer aux coordonnées indiquées.
    public void updateGrille(Tuile t, int x, int y) {
        // On ajuste les coordonnées du plateau à celles du gridLayout.
        grille.remove((x-1)*(partie.plateau.largeur-2)+(y-1)); // Enlève tuile vide.
        grille.add(t, (x-1)*(partie.plateau.largeur-2)+(y-1)); // Remplace par la tuile jouée.
        grille.repaint(); // Repeint GUI.
        grille.revalidate(); // Revalide GUI.
    }

    // Enlève toutes les tuiles de la grille et les remet suivant celles se trouvant dans le plateau.
    public void repaintGrille() {
        conteneurGrille.removeAll();
        grille = new JPanel();
        grille.setLayout(new GridLayout(partie.plateau.hauteur-2, partie.plateau.largeur-2, -1, -1));
        conteneurGrille.add(grille);
        // Remplissage de la grille avec tuiles intérieures du plateau.
        for (int i = 1; i < (partie.plateau.hauteur - 1); i++) {
            for (int j = 1; j < (partie.plateau.largeur - 1); j++) {
                grille.add(partie.plateau.plateau[i][j]);
            }
        }
        grille.repaint(); // Repeint GUI.
        grille.revalidate(); // Revalide GUI.
    }

    public void updatePanelJoueurs() {
        for (int i = 0; i < partie.joueurs.nbJoueurs(); i++) {
            panelJoueurs.get(i).updatePanel();
        }
        conteneurInfos.repaint(); // Repeint GUI.
        conteneurInfos.revalidate(); // Revalide GUI.
    }

    public void repaintTuileAJouer() {
        conteneurTuileAJouer.removeAll();
        conteneurTuileAJouer.add(partie.aJouer, BorderLayout.CENTER);
        conteneurTuileAJouer.repaint(); // Repeint GUI.
        conteneurTuileAJouer.revalidate(); // Revalide GUI.
    }

    public void updateTuilesRestantes() {
        tuilesRestantes.setText(String.valueOf(partie.pioche.getSize()));
        conteneurInfosCoup.repaint(); // Repeint GUI.
        conteneurInfosCoup.revalidate(); // Revalide GUI.
    }

    public void winMessage() {
        conteneurInfosCoup.removeAll();
        conteneurInfosCoup.setLayout(new BorderLayout());
        Joueur vainqueur = partie.joueurs.vainqueur();
        winMessage = new JLabel("Le vainqueur est " + vainqueur.nom + " avec " + String.valueOf(vainqueur.score) + " points !");
        winMessage.setFont(new Font("Arial", Font.BOLD, 16));
        conteneurInfosCoup.add(winMessage, BorderLayout.CENTER);
        winMessage.setHorizontalAlignment(0);
        conteneurInfosCoup.revalidate();
        conteneurInfosCoup.repaint();
    }

    public void desactiverBoutonsTuileAJouer() {
        rotationDroite.setEnabled(false);
        rotationGauche.setEnabled(false);
        defausser.setEnabled(false);
    }

    public void activerBoutonsTuileAJouer() {
        rotationDroite.setEnabled(true);
        rotationGauche.setEnabled(true);
        defausser.setEnabled(true);
    }
}
