package JeuDominos;

import JeuTuilesGenerique.Modele.Partie;
import JeuTuilesGenerique.Vue.GameView;

public class VueDominos extends GameView {

    public VueDominos(Partie partie) {
        super(partie);
        super.titre.setText("Jeu de Dominos");
        // TODO ajouter spécificités VueDomino
    }
}

