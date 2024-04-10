package JeuTuilesGenerique.Modele;

import java.io.Serializable;

import JeuCarcassonne.BordCarcassonne.Structure;

public class Bord implements Serializable{

    public boolean estCompatibleAvec(Bord b) {
        return false;
    }

    public int[] getNumeros() {
        return new int[]{};
    }
    public Structure getStructure() {
        return null;
    }
}
