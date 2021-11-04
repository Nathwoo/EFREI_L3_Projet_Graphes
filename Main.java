package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    //0-VARIABLES STATIQUES
    static String[] cheminsGraphe;
    static int nbSommets, nbArrêtes;
    static double[][] matriceAdjacence, matriceGraphe, matriceDistance, matricePredecesseur;

    //1-DEFINITION DU TABLEAU DES CHEMINS VERS LES FICHIERS TXT
    static void chargementChemins(){
        System.out.println("Combien de graphes faut-il enregistrer ?");
        Scanner lecteur = new Scanner(System.in);
        int reponse = lecteur.nextInt();
        cheminsGraphe = new String[reponse];
        if (reponse == 1){
            System.out.println("Adresse du graphe (fichier .txt) :");
            cheminsGraphe[0] = lecteur.next();
        }else{
            for(int i=0; i<reponse; i++){
                System.out.println("Adresse du graphe numéro "+(i+1)+" (fichier .txt) :");
                cheminsGraphe[i] = lecteur.next();
            }
        }
    }


    //2-FONCTION DE REPRESENTATION MEMOIRE DU GRAPHE A TESTER
    static void representationMemoire(int numGraphe) throws FileNotFoundException {

        //On utilise la classe File pour importer le fichier txt
        File fichier_txt = new File(cheminsGraphe[numGraphe-1]);

        //On utilise la classe Scanner pour lire le fichier txt
        Scanner scan = new Scanner(fichier_txt);

        //On enregistre les données du graphe
        nbSommets = Integer.parseInt(scan.nextLine());
        nbArrêtes = Integer.parseInt(scan.nextLine());

        //Création et remplissage des matrices du graphe
        matriceGraphe = new double[nbSommets][nbSommets];
        matriceAdjacence = new double[nbSommets][nbSommets];
        int[] prochaineArete = new int[3];

        do {
            //Transformation de la chaine décrivant l'arête en tableau d'entiers si elle existe
            if (scan.hasNextLine()) {
                String chaineArete = scan.nextLine();
                String[] tableauDeChaines = chaineArete.split(" ", 3);
                for (int i = 0; i < 3; i++) {
                    prochaineArete[i] = Integer.parseInt(tableauDeChaines[i]);
                }
                //implémentation de la valeur de l'arête dans les matrices
                int i = prochaineArete[0];
                int j = prochaineArete[1];
                matriceAdjacence[i][j] = 1;
                matriceGraphe[i][j] = prochaineArete[2];
            } else {
                prochaineArete = null;
            }

        }while (prochaineArete != null);

        //Remplissage des coûts des arêtes inexistantes par l'infini
        for (int i=0; i<nbSommets; i++){
            for (int j=0; j<nbSommets; j++){
                if (matriceAdjacence[i][j]==0 && i!=j){
                    matriceGraphe[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }

        //Affichage des matrices
        System.out.println("Matrice d'adjacence :\n");
        afficher(matriceAdjacence);

        System.out.println("Représentation matricielle du graphe :\n");
        afficher(matriceGraphe);
        //System.out.println(Arrays.deepToString(matriceGraphe)+"\n");

    }

    //3-FONCTION D'AFFICHAGE DES MATRICES
    static void afficher(double[][] matrice){
        int[] minLargeurColonne = new int[nbSommets];
        //Taille minimale de chaque colonne
        for(int i=0; i<nbSommets; i++){
            String valeur = Double.toString(matrice[0][i]);
            minLargeurColonne[i]= valeur.length();
            for(int j=1; j<nbSommets; j++){
                valeur = Double.toString(matrice[j][i]);
                if (valeur.length()>minLargeurColonne[i]){
                    minLargeurColonne[i]=valeur.length();
                }
            }
        }
        //System.out.println(Arrays.toString(minLargeurColonne));
        //Conversion vers un double tableau de chaines pour justifier les colonnes et centrer les valeurs
        String[][] res = new String[nbSommets][nbSommets];
        for (int i=0; i<nbSommets; i++){
            for (int j=0; j<nbSommets; j++){
                String valeur = Double.toString(matrice[i][j]);
                int marge = minLargeurColonne[j]-valeur.length();
                int décalage = valeur.length() + marge/2;
                String result = String.format("%-"+décalage+"s",valeur);
                //System.out.println(minLargeurColonne[j]);
                //System.out.println(result);
                result = String.format("%"+minLargeurColonne[j]+"s",result);
                res[i][j] = result;
            }
        }
        //Affichage ligne par ligne
        for (int i=0; i<nbSommets; i++){
            System.out.println(Arrays.toString(res[i]));
        }
        System.out.println("\n");
    }

    //4-ALGORITHME DE FLOYD-WARSHALL
    public static void floydWarshall(){
        //Initialisation des matrices de distances et de prédécesseurs
        matriceDistance = new double[nbSommets][nbSommets];
        matricePredecesseur = new double[nbSommets][nbSommets];
        for (int i=0; i<nbSommets; i++){
            for (int j=0; j<nbSommets; j++){
                matriceDistance[i][j]=matriceGraphe[i][j];
                if (matriceAdjacence[i][j] != 0){
                    matricePredecesseur[i][j] = i;
                }
            }
        }
        System.out.println("Initialisation Floyd-Warshall :\n");
        System.out.println("Matrice des coûts :");
        afficher(matriceDistance);
        System.out.println("Matrice des prédecesseurs :");
        afficher(matricePredecesseur);
        System.out.println("Itérations Foyd-Warshall :\n");

        int n = 0; //nombre d'itérations
        for (int k=0; k<nbSommets; k++){
            for (int i=0; i<nbSommets; i++){
                for (int j=0; j<nbSommets; j++){
                    if (matriceDistance[i][j] > matriceDistance[i][k] + matriceDistance[k][j]){
                        matriceDistance[i][j] = matriceDistance[i][k] + matriceDistance[k][j];
                        matricePredecesseur[i][j] = matricePredecesseur[k][j];
                        n+=1;
                        System.out.println("Itération "+n);
                        System.out.println("Matrice des coûts :");
                        afficher(matriceDistance);
                        System.out.println("Matrice des prédecesseurs :");
                        afficher(matricePredecesseur);
                    }
                }
            }
        }

    }

    //5-DETECTION DE CIRCUITS ABSORBANTS
    public static boolean detectionCircuitAbsorbant(){
        for (int i=0; i<nbSommets; i++){
            if(matriceDistance[i][i]<0){
                return true;
            }
        }
        return false;
    }

    //6-AFFICHAGE DES PLUS COURTS CHEMINS
    public static void affichageChemins(){
        int[] pile = new int[nbArrêtes];
        for (int i=0; i<nbSommets; i++){
            for (int j=0; j<nbSommets ; j++){
                if (matriceDistance[i][j] != Double.POSITIVE_INFINITY && j != i){ //évacuation des chemins inexistants
                    String chemin = Integer.toString(i);
                    //si le prédecesseur n'est pas l'origine du chemin :
                    if ((int) matricePredecesseur[i][j]!=i){
                        int k = j; //sommet à stocker dans la pile
                        int n = 0; //nb de sommets dans la pile
                        //On déroule le chemin jusqu'à ce que prédecesseur = origine
                        while (matricePredecesseur[i][k] != i){
                            pile[n]=k;
                            n+=1;
                            k=(int)matricePredecesseur[i][k];
                        }
                        //On ajoute le sommet courant au chemin
                        chemin = chemin + " " + Integer.toString(k);
                        //System.out.println(Arrays.toString(pile));
                        //On dépile tous les sommets restants
                        while(n!=0){
                            n-=1;
                            chemin = chemin + " " + Integer.toString(pile[n]);
                        }
                    }else{
                        chemin = chemin + " " + Integer.toString(j);
                    }

                    System.out.println("Plus court chemin de "+i+" à "+j+" :");
                    System.out.println(chemin);
                }
            }
        }

    }


    public static void main(String[] args) throws FileNotFoundException {

        chargementChemins();

        boolean work = true;
        while (work==true){
            Scanner lecteur = new Scanner(System.in);
            int numGraphe;
            if (cheminsGraphe.length > 1){
                System.out.println("Quel graphe voulez vous tester ?");
                numGraphe = lecteur.nextInt();
            }else{
                numGraphe = 1;
            }

            representationMemoire(numGraphe);

            floydWarshall();

            if (detectionCircuitAbsorbant()){
                System.out.println("Le graphe possède un circuit absorbant.");
            }else{
                affichageChemins();
            }
            String rep;
            System.out.print("Voulez-vous tester un autre graphe ? (o/n)");
            rep = lecteur.next();
            if (rep.equals("n")){
                work=false;
            }
        }
    }
}