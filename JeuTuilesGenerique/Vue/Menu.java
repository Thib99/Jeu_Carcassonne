package JeuTuilesGenerique.Vue;

import java.awt.*;
import  java.awt.event.FocusEvent ;
import  java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import JeuTuilesGenerique.Modele.Joueurs;
import JeuTuilesGenerique.Modele.Joueurs.Joueur;

public class Menu implements Serializable{
    
    // GameView vuePartie ;
    Launcher launcher;
    String nomPartie;
    JPanel container ;
    private int widthFrame; 
    private int heightFrame; 
    private boolean carcassonneBoolean;

    // toutes les interfaces du menu
    SelectGame selectGame ;
    SelectSave selectSave ;
    ManagePlayer managePlayer ;


    public Menu(Launcher launcher){
        this.launcher = launcher;
        widthFrame = launcher.getWidth() ;
        heightFrame = launcher.getHeight() ;

        container = new JPanel() ;
        container.setVisible(true);
        container.setLayout(null);
        launcher.getContentPane().add(container);

        if (selectGame == null)  selectGame = new SelectGame() ;
    }

    // Objet avec des définition spécial

    class ButtonImage extends JButton{

        ButtonImage(String NameImage){
            this(NameImage, new Rectangle(15,15, 50 , 50)) ;
        }

        ButtonImage(String NameImage, Rectangle d){

            try {
                Image img;
                img = ImageIO.read(getClass().getResource("Images/" + NameImage));
                this.setIcon(new ImageIcon(img));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.setBounds(d);
            this.setOpaque(false);
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
            this.setVisible(true);
            container.add(this) ; 
        }
    }

    private class FocusPlaceholder implements FocusListener{
        JTextField field ; 
        String[] motplacerholder ;
        String hold ;

        FocusPlaceholder(JTextField field, String[] motplacerholder){
            this.field = field ;
            this.motplacerholder = motplacerholder.clone() ;
            field.setForeground(Color.GRAY);
            
        }

        public void focusGained(FocusEvent e) {
            for (String string : motplacerholder) {
                if (field.getText().equals(string)) {
                    hold = field.getText() ;
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
                
            }
        }
        @Override
        public void focusLost(FocusEvent e) {
            if (field.getText().isEmpty()) {
                field.setForeground(Color.GRAY);
                field.setText(hold);
            }
        }
    }


    class SelectGame implements Serializable{
        
        // JButtons
        JButton carcassonne ;
        JButton dominos ;

        // fermer le jeu
        ButtonImage fermer ;


        SelectGame(){

            // fermer le jeu
            fermer = new ButtonImage("croix.png") ;
            
            fermer.addActionListener(event -> {
                System.exit(0);
            });

            
            // initialisation JButtons
            carcassonne = new JButton("Carcassonne") ;
            dominos = new JButton("Dominos") ;


            // définition de la taille
            carcassonne.setBounds(0, 0, 200, 40);
            dominos.setBounds(0, 0, 200, 40);
        

            // action 
            carcassonne.addActionListener(event -> {
                carcassonneBoolean = true ;
                nextInterfaceMenu() ;
            });
            
            dominos.addActionListener(event -> {
                carcassonneBoolean = false ;
                nextInterfaceMenu() ;
            });
            
            // ajout
            container.add(carcassonne) ;
            container.add(dominos) ;
            
            setLocationObjet();
            changevisibility(true);
        }

        private void setLocationObjet(){
            carcassonne.setLocation(widthFrame/2 -carcassonne.getWidth()/2, heightFrame/2 - 50);
            dominos.setLocation(widthFrame/2 -dominos.getWidth()/2, heightFrame/2 + 50);
            
        }

        public void changevisibility(boolean visibility){
            carcassonne.setVisible(visibility);
            dominos.setVisible(visibility);
            fermer.setVisible(visibility);
        }

        public void nextInterfaceMenu(){
            changevisibility(false);
            // if (selectSave == null) selectSave = new SelectSave() ;
            // else selectSave.changevisibility(true);

            selectSave = new SelectSave() ;     


            // set the backgound image 
                // Image background = Toolkit.getDefaultToolkit().createImage("Image/background_" + (carcassonneBoolean? "carcassonne.jpg": "domino.jpg"));
                // container.drawImage(background, 0, 0, null);
        }

    }

    class SelectSave implements Serializable{

        JTextField newGame ;

        JPanel conteneurSelectionPartie ;
        JButton chargerNouvellePartie ;
        JButton chargerPartieSauvegardee ;
        JComboBox<String> listsaveComboBox  ;
        
        String[] listtouteSauvegarde  ;
        
        ButtonImage retour ;
        JLabel indication ;
        

        String[] aide = new String[]{"Rentrer le nom de la nouvelle partie", "Nom déjà utilisé"};

        SelectSave(){

            // set button retour
            retour = new ButtonImage("retour50p.png") ;
            
            retour.addActionListener(event -> {
                previousInterfaceMenu() ;
            });



            // autre fonction
            newGame = new JTextField() ;
            chargerNouvellePartie = new JButton();
            chargerPartieSauvegardee = new JButton();

            newGame.setText("Rentrer le nom de la nouvelle partie");
            newGame.setForeground(Color.GRAY) ;
            newGame.setMinimumSize(new Dimension(240, 20));
            newGame.setPreferredSize(newGame.getMinimumSize());


            chargerNouvellePartie.setText("Charger la partie");
            chargerPartieSauvegardee.setText("Charger la partie");

            conteneurSelectionPartie = new JPanel() ;


            // ajout d' élement de la JComboBox
                addAllSave();

            // setLayout

            conteneurSelectionPartie.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            

            c.fill = GridBagConstraints.HORIZONTAL;
            
                c.gridwidth = 4 ; 
                c.gridx = 0;
                c.gridy = 0;
                conteneurSelectionPartie.add(newGame, c);

            
            c.fill = GridBagConstraints.HORIZONTAL;
                c.gridwidth = 4;
                c.gridx = 0;
                c.gridy = 2;
                conteneurSelectionPartie.add(listsaveComboBox, c);
            
            c.fill = GridBagConstraints.HORIZONTAL;
            c.fill = GridBagConstraints.VERTICAL ;
                c.gridwidth = 1;
                c.gridx = 5;
                c.gridy = 0;
                conteneurSelectionPartie.add(chargerNouvellePartie, c);

            
                c.gridwidth = 1;
                c.gridx = 5;
                c.gridy = 2;
                conteneurSelectionPartie.add(chargerPartieSauvegardee, c);
            
           
            // placement indication
            indication = new JLabel("Veuillez créer une partie ou en choisir une déjà existante :") ;

            indication.setSize(widthFrame/3, heightFrame/3);
            indication.setLocation(widthFrame/2 - indication.getWidth()/2, 100);
            container.add(indication) ;
            indication.setVisible(true ) ;

            // placement container

            container.add(conteneurSelectionPartie) ;
            conteneurSelectionPartie.setVisible(true);

            conteneurSelectionPartie.setSize(widthFrame/2, heightFrame/2) ;
            conteneurSelectionPartie.setLocation(widthFrame/2 - conteneurSelectionPartie.getWidth()/2, heightFrame/2- conteneurSelectionPartie.getHeight()/2) ;

            newGame.setVisible(true);
            listsaveComboBox.setVisible(true);
            chargerNouvellePartie.setVisible(true);
            chargerPartieSauvegardee.setVisible(true);

            // action 
            
            
            chargerNouvellePartie.addActionListener(event -> {
                if (isFree(newGame.getText())){
                    nomPartie = newGame.getText();
                    nextInterfaceMenu() ;
                } else {
                    newGame.setText("Nom déjà utilisé");
                    newGame.setForeground(Color.GRAY);
                }
            });

            chargerPartieSauvegardee.addActionListener(event -> {
                if (listsaveComboBox.getSelectedItem() != null ){
                    try {
                        launcher.launchRunningGame("Sauvegardes/"+ (carcassonneBoolean? "Carcassonne/" : "Dominos/" ) + listsaveComboBox.getSelectedItem()) ;
                        container.setVisible(false);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }   

            });

            newGame.addFocusListener(new FocusPlaceholder(newGame, aide));
            changevisibility(true);
        }

        private boolean isFree(String nom){
            for (String string : listtouteSauvegarde) {
                if (nom.equals(string)) return false ;
            }
            for (String string : aide) {
                if (string.equals(nom)) return false ;
            }
            return true ;
        }

        private void addAllSave(){
            // faire en fonction du mode de jeu 

            
            String path = "./Sauvegardes/"+ (carcassonneBoolean? "Carcassonne" : "Dominos" ) ;
            
            
            try {
                File[] dir  = (new File(path)).listFiles();

                listtouteSauvegarde = new String[dir.length] ;
                for (int i = 0 ; i < listtouteSauvegarde.length ; i++){
                    listtouteSauvegarde[i] = dir[i].getName() ;
                }
                
            } catch (Exception e) {
                listtouteSauvegarde = new  String[1] ;
                listtouteSauvegarde[0] = ("Aucun fichier n'a été trouvé !");
            }
            listsaveComboBox  = new JComboBox<String>((listtouteSauvegarde)) ;
            
        }

        public void changevisibility(boolean visibility){
            newGame.setVisible(visibility);
            conteneurSelectionPartie.setVisible(visibility);
            retour.setVisible(visibility);
            indication.setVisible(visibility);
            

        }

        public void previousInterfaceMenu(){
            changevisibility(false);
            
            selectGame = new SelectGame() ;
        }
        
        public void nextInterfaceMenu(){
            changevisibility(false);
            managePlayer = new ManagePlayer() ;
        }
        
    }


    class ManagePlayer implements Serializable{

        ButtonImage retour ;
        ConteneurAddPlayer conteneurAddPlayer ;
        JPanel dispPlayer;
        Joueurs joueurs;
        JPanel panelmanage ;
        JLabel IndicationAjout ;
        JLabel IndicationPresent ;

        // ButtonImage play ;
        JButton play ;

        ManagePlayer(){
            joueurs = new Joueurs();
            // bouton retour
                retour = new ButtonImage("retour50p.png") ;
                retour.addActionListener(event -> {
                    previousInterfaceMenu() ;
                });

            // Barre ajout de joueur :
                conteneurAddPlayer= new ConteneurAddPlayer() ;
                conteneurAddPlayer.setVisible(true);
                container.add(conteneurAddPlayer) ;
               
                conteneurAddPlayer.setSize(widthFrame/3, 100);
                conteneurAddPlayer.setLocation(widthFrame/2 -conteneurAddPlayer.getWidth()/2, heightFrame/4);
                
                // conteneurAddPlayer.nom.setSize(100, conteneurAddPlayer.IA.getWidth());
                // conteneurAddPlayer.nom.setPreferredSize(new Dimension( 100, conteneurAddPlayer.IA.getWidth()));
                
            // les joueurs déjà présents :
                dispPlayer = new JPanel() ;
                dispPlayer.setLayout(new BoxLayout(dispPlayer, BoxLayout.PAGE_AXIS));
                
                for (Joueur joueur : joueurs.getList()){
                    dispPlayer.add(new ConteneurPlayer(joueur)) ;
                }

                dispPlayer.setSize(widthFrame/3, 300);
                dispPlayer.setLocation(widthFrame/2 -dispPlayer.getWidth()/2, heightFrame/2);
                
                dispPlayer.setVisible(true);
                container.add(dispPlayer) ;
                
                
            // Texte d'aide :
                IndicationAjout = new JLabel("Ajouter un nouveau joueur :") ;
                IndicationPresent = new JLabel("Liste des joueurs de la partie (min:2, max:5) : ") ;
                
                IndicationAjout.setSize(widthFrame/3, 100);
                IndicationAjout.setLocation(widthFrame/2 -IndicationAjout.getWidth()/2, heightFrame/4-100);
                
                IndicationPresent.setSize(widthFrame/3, 100);
                IndicationPresent.setLocation(widthFrame/2 -IndicationPresent.getWidth()/2, heightFrame/2-100);

                container.add(IndicationAjout) ;
                container.add(IndicationPresent) ;

            // Boutton play :
                    play = new ButtonImage("play.png", new Rectangle(50, 50, widthFrame-50 , heightFrame/2  )) ;
                    // play = new JButton("play");  
                
                    play.setSize(50,50);
                    play.setLocation((widthFrame*5 )/6, heightFrame/2  );
                    container.add(play) ;

                    play.addActionListener(event -> {
                        if (joueurs.nbJoueurs() >= 2){
                            container.setVisible(false);
                            changevisibility(false);
                            if (carcassonneBoolean)
                                try { launcher.launchCarcassonne(joueurs, nomPartie);
                                } catch (IOException e) { System.out.println("Impossible de lancer Carcassonne, pioche non constituée"); }
                            else launcher.launchDominos(joueurs, nomPartie);
                        }
                    });

            changevisibility(true);
        }
        
        
        public void previousInterfaceMenu(){
            changevisibility(false);
            // vuePartie.getPartie().save();
            selectSave = new SelectSave() ;
        }
    

        public void changevisibility(boolean visibility){
            dispPlayer.setVisible(visibility);
            conteneurAddPlayer.setVisible(visibility);
            retour.setVisible(visibility);
            IndicationAjout.setVisible(visibility);
            IndicationPresent.setVisible(visibility);
            play.setVisible(visibility);
        }

        private class ConteneurAddPlayer extends JPanel {

            JButton add;
            JTextField nom ;
            JButton IA ;
            boolean isIA ;

            String[] aide = new String[]{"Nom déjà utilisé", "Le nombre maximun de joueurs est atteint !", "Entrer le nom du joueur"} ;

            ConteneurAddPlayer(){

                // définir les J
                IA  = new JButton("IA") ;
                isIA = false ;
                setColor();

                nom = new JTextField("Entrer le nom du joueur") ;
                nom.setForeground(Color.GRAY);

                nom.setMinimumSize(new Dimension(240, 20));
                nom.setPreferredSize(nom.getMinimumSize());

                add = new JButton("+") ;

                // action des boutons

                IA.addActionListener(event -> {
                    isIA = !isIA ;
                    setColor();
                });

                add.addActionListener(event -> {
                    if (textIsPlaceHolder()){
                        nom.setText("Entrer le nom du joueur") ;
                        nom.setForeground(Color.GRAY);
                    }
                    else if (NameFree()){
                        if (joueurs.addPlayer(nom.getText(), isIA)){
                            dispPlayer.add( new ConteneurPlayer(joueurs.getLast())) ;
                            dispPlayer.revalidate();
                            dispPlayer.repaint();
                            nom.setText("Entrer le nom du joueur") ;
                            nom.setForeground(Color.GRAY);
                        }else{
                            nom.setText("Le nombre maximun de joueurs est atteint !");
                            nom.setForeground(Color.GRAY);
                        }
                    }else{
                        nom.setText("Nom déjà utilisé");
                        nom.setForeground(Color.GRAY);
                    }


                    if (joueurs.listfull()){
                        add.setEnabled( ! joueurs.listfull());
                        nom.setText("Le nombre maximun de joueurs est atteint !");
                        nom.setForeground(Color.GRAY);
                    }

                });

                // permet l'effacement du texte pré-écrit lorsqu'on passe sur le texte
                nom.addFocusListener(new FocusPlaceholder(nom, aide));

                // placer
                setLayout(new FlowLayout()) ;

                add(IA) ;
                add(nom) ;
                add(add) ;
                
                // rendre visible 
                IA.setVisible(true);
                nom.setVisible(true);
                add.setVisible(true);
                setVisible(true );
            }

            private void setColor(){
                Color color =  isIA ? Color.GREEN : Color.RED ;
                IA.setBackground(color) ;
            }

            private boolean textIsPlaceHolder(){
                String name = nom.getText();
                for (String s : aide){
                    if (s.equals(name)) return true ;
                }
                return false ;
            }

            private boolean NameFree(){
                String name = nom.getText() ;
                if (name.equals("")) return false ;
                
                for (Joueur jo : joueurs.getList()) {
                    if (name.equals(jo.getName())) return false ;
                }
                
                return true ;
            }

        }
        
        private class ConteneurPlayer extends JPanel {

            JButton remove;
            JLabel nom ;
            JButton IA ;

            ConteneurPlayer(Joueur joueur){

                // définir les JElements
                IA  = new JButton("IA") ;
                setColor(joueur.isIA()) ;

                nom = new JLabel(joueur.getName()) ;

                remove = new JButton("-") ;

                // action des boutons

                IA.addActionListener(event -> {
                    joueur.setIA(! joueur.isIA());
                    setColor(joueur.isIA());
                });

                remove.addActionListener(event -> {
                    joueurs.getList().remove(joueur) ;
                    dispPlayer.remove(this);
                    dispPlayer.revalidate();
                    dispPlayer.repaint();

                    conteneurAddPlayer.add.setEnabled( ! joueurs.listfull());


                });

                // placer
                setLayout(new FlowLayout()) ;

                add(IA) ;
                add(nom) ;
                add(remove) ;
                

                // rendre visible 
                IA.setVisible(true);
                nom.setVisible(true);
                remove.setVisible(true);
                setVisible(true );
            }

            public void setColor(boolean IA_IsON){
                Color color =  IA_IsON ? Color.GREEN : Color.RED ;
                IA.setBackground(color) ;
            }
        }
    }
}
