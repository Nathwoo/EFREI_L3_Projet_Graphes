package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        //On utilise la classe File pour importer le fichier txt
        File fichier_txt = new File("C:\\Users\\nprov\\IdeaProjects\\Projet_Graphes\\src\\com\\company\\graphe_test.txt");
        //On utilise la classe Scanner pour lire le fichier txt
        Scanner scan = new Scanner(fichier_txt);
        //On définit une nouvelle instance de Graphe
        Graphe graphe_test = new Graphe();

        //On enregistre les données du graphe dans notre instance

        int nb_sommets = Integer.parseInt(scan.nextLine());
        graphe_test.setNb_sommets(nb_sommets);

        int nb_aretes = Integer.parseInt(scan.nextLine());
        graphe_test.setNb_aretes(nb_aretes);

        //Définission des variables pour la matrice d'aretes
        int[][] matriceAretes = new int[nb_aretes][3];
        int[] TableauEntiers = new int[3];
        String chaineArete;

        //Remplissage de la matrice
        for (int i=0;i<nb_aretes;i++){
            chaineArete=scan.nextLine();

            String[] tableauDeChaines = chaineArete.split(" ",3);

            for (int j=0; j<3; j++){
                TableauEntiers[j]=Integer.parseInt(tableauDeChaines[j]);
            }

            matriceAretes[i]=TableauEntiers;
        }

        graphe_test.setMatrice_aretes(matriceAretes);

        System.out.print(Arrays.deepToString(graphe_test.getMatrice_aretes()));


    }
}
