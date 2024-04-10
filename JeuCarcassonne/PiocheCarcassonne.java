package JeuCarcassonne;

import java.io.IOException;
import JeuTuilesGenerique.Modele.Pioche;

public class PiocheCarcassonne extends Pioche{

    public PiocheCarcassonne() throws IOException {
        super(); // Création d'une arraylist de tuiles.
        // Ajout à la pioche des 24 différents types de tuiles en leur nombre correspondant.
        addSeveralTimes("CCCCV", 4); // Abbaye
        addSeveralTimes("CCRCV", 2);
        addSeveralTimes("CCRRC", 9);
        addSeveralTimes("CRRRV", 4);
        addSeveralTimes("CVbCVV", 2);
        addSeveralTimes("CVCVC", 3);
        addSeveralTimes("CVCVV", 2);
        addSeveralTimes("RCRCR", 8);
        addSeveralTimes("RRRRV", 1);
        addSeveralTimes("VbCCVC", 2);
        addSeveralTimes("VbRRVC", 2);
        addSeveralTimes("VbVCVV", 1);
        addSeveralTimes("VbVRVV", 1);
        addSeveralTimes("VbVVVV", 1);
        addSeveralTimes("VCCCC", 5);
        addSeveralTimes("VCCVC", 3);
        addSeveralTimes("VCRRC", 3);
        addSeveralTimes("VRCRR", 4);
        addSeveralTimes("VRRCC", 3);
        addSeveralTimes("VRRRR", 3);
        addSeveralTimes("VRRVC", 3); // On considère qu'une structure est au centre seulement si elle y est isolée ou si elle traverse la tuile de part en part.
        addSeveralTimes("VVCCC", 2);
        addSeveralTimes("VVCVV", 3);
        addSeveralTimes("VVRVV", 1);
    }

    public void addSeveralTimes(String description, int n) throws IOException {
        for (int i = 0; i < n; i++) { add(new TuileCarcassonne(description)); }
    }
}
