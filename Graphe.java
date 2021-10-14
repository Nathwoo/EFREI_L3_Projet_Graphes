package com.company;

public class Graphe {

    private int nb_sommets;
    public int getNb_sommets(){
        return nb_sommets;
    }
    public void setNb_sommets(int nb_sommets) {
        this.nb_sommets = nb_sommets;
    }

    private int nb_aretes;
    public int getNb_aretes() {
        return nb_aretes;
    }
    public void setNb_aretes(int nb_aretes) {
        this.nb_aretes = nb_aretes;
    }

    private int[][] matrice_aretes = new int[nb_aretes][3];
    public int[][] getMatrice_aretes(){
        return matrice_aretes;
    }
    public void setMatrice_aretes(int[][] matrice_aretes) {
        this.matrice_aretes = matrice_aretes;
    }
}
