package JeuCarcassonne;

import java.io.Serializable;

import JeuTuilesGenerique.Modele.Bord;

public class BordCarcassonne extends Bord{
    
    Structure structure;
    boolean pion ;

    public boolean contientPion() {
        return pion;
    }

    public void setPion(boolean pion) {
        this.pion = pion;
    }

    public Structure getStructure() {
        return structure;
    }

    public BordCarcassonne(Structure structure) {
        this.structure = structure;
    }

    public BordCarcassonne(String initiale) {
        // S'il y a un bouclier
        if (initiale.contains("b")) this.structure = new Structure.Ville(true);
        else {
            switch (initiale) {
                case "V": 
                    this.structure = new Structure.Ville();
                    break;
                case "C":
                    this.structure = new Structure.Champ();
                    break;
                case "R":
                    this.structure = new Structure.Route();
                    break;
                default:
                    this.structure = null;
            }
        }
    }

    public boolean estCompatibleAvec(Bord bD) {
        if (bD == null || (bD.getStructure().getClass().equals(structure.getClass()))) return true;
        return false;
    }

    public static class Structure implements Serializable {
        public static class Route extends Structure {}
        public static class Champ extends Structure {}
        public static class Ville extends Structure {
            boolean bouclier;
            public Ville() {
                this.bouclier = false;}
            public Ville(boolean bouclier) {
                this.bouclier = bouclier;}
        }
    }
}
