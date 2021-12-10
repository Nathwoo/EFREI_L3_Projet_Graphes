# EFREI_L3_Projet_Graphes
Implémentation de l'algortihme de Floyd-Warshall (recherche des plus courts chemins dans un graphe) en Java.
Les graphes sont représentés dans un fichier txt sous cette forme :
Ligne 1 : nombre de sommets
Ligne 2 : nombre d'arcs
Lignes suivantes : numéro du sommet initial, numéro du sommet final, valeur de l'arc.
Avec ce modèle de fichier, s’il contient le texte suivant:
4
5
3 1 25
1 0 12
2 0 -5
0 1 0
2 1 7
cela correspond à un graphe contenant 4 sommets numérotés de 0 à 3 (numérotation contiguë), et 5 arcs (3,1), (1,0), (2,0), (0,1), (2,1) de valeurs respectives 25, 12, -5, 0 et 7.
