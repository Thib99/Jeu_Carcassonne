## Projet de POOIG

# Lancement du jeu

Le jeu se lance depuis la classe JeuTuilesGenerique/Vue/Launcher.java. Lancer l'intégralité de l'interface de jeu (avec menu) est fait en appelant la fonction launcher() de cette classe depuis une fonction main.
Par défaut, la fonction main de Launcher.java appelle launcher(), donc il suffit d'**éxécuter JeuTuilesGenerique/Vue/Launcher.java pour lancer l'intégralité de l'interface de jeu**.

Le menu est constistué d'une interface permettant le choix du jeu (Carcassonne ou Dominos), d'une interface permettant de charger une nouvelle partie (en indiquant son nom) ou une partie sauvegardée, et d'une interface permettant d'ajouter des joueurs avec leur nom puis de lancer une partie du jeu choisi.
Pour lancer directement une partie Carcassonne, une partie Dominos ou une partie sauvegardée sans passer par les paramétrages du menu, il est possible d'appeler les méthodes launchCarcassone(), launchDomino() ou 
launchRunningGame() de Launcher.java en leur passant les arguments nécéssaires (nom de la partie et liste de joueurs si la partie est nouvelle). Une préparation à ces appels est donnée en commentaires dans la
fonction main de Launcher.java.

Il faut **éxécuter le fichier JeuDominos/DominoTerminal pour lancer le jeu Dominos sur le terminal**.

# Utilisation du jeu

Une fois sur l'interface d'une partie, il est possible de la sauvegarder en appuyant sur le bouton "save". Clore la fenêtre en appuyant sur la croix noire le fait par défaut. Il sera ensuite possible de recharger
cette partie en la selectionnant depuis le menu déroulant du menu.

# Dépôt en ligne

Le dépôt utilisé pour le projet est disponible à cette adresse: https://gaufre.informatique.univ-paris-diderot.fr/poux/projet-de-pooig

