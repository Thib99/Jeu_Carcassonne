package JeuDominos;

import JeuTuilesGenerique.Modele.Bord;

public class BordDomino extends Bord{
    
     int n1, n2, n3;

    public BordDomino(int n1, int n2, int n3) {
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
    }

    public int getN1() {
        return n1;
    }

    public int getN2() {
        return n2;
    }

    public int getN3() {
        return n3;
    }
    
    public int[] getNumeros() {
        int[] numeros = {n1,n2,n3};
        return numeros;
    }

    public int sommeBords(){
        return n1+n2+n3;
    }

    public boolean estCompatibleAvec(Bord bD) {;
        if (bD == null || (n1 == ((BordDomino)bD).getN1() && n2 == ((BordDomino)bD).getN2() && n3 == ((BordDomino)bD).getN3())) return true;
        return false;
    }
}
