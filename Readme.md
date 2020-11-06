# Optimisation et Evaluation Expérimentale de CSP Binaires

## Introduction

Ce projet s'attache à mettre en place des optimisations au resolveur de CSP.

Il est demandé de réaliser au moins une des optimisations suivantes : 
- heuristique d'assignation des variables, 
- test de viol de contraintes même si toutes les variables de la contrainte ne sont pas assignées, 
- arc-consistance, 
- forward checking...

Le projet est suivi d'un travail d'évaluation complet des effets des différentes optimisations.

## Les Optimisations implémenter

### Heuristique d'assignation des variables

À implèmenter

### Test de viol de contraintes

À implèmenter

### Arc-consistance

À implèmenter

## Fonctionnemnet du code

### gnuplot

Génére un fichier graph.png `gnuplot -c plot.p`

### Arborescence du projet

- src/: Dossier contenant tous les fichiers sources des différents programmes testé
  + csp/: code source du CSP. 
- bin/: Dossier contenant les fichier binaires et executables
- rapport/: Dossier contenant le rapport au format LaTeX et PDF
- networks/: Dossier contenant les divers jeux d'essais
- info/: Dossier contenant divers informations sur le déroulement du projet
- resultats/: Dossier contenant les résultats des tests
- exec.sh: script qui permet l'éxecution des tests

### Éxecution des programmes

pour éxecuter le programme il suffit de :
+ Ouvrire un terminal et se rendre au dossier src/
+ Ajouter les droits d'éxecutions au programme exec.sh
+ Puis l'éxecuter `exec.sh`

Le script génère un benchmark puis éxecuter l'algorithme de résolution sur ce dernier et gnuplot sur le resultat. Vous aurez donc à la fin un fichier graphe.png qui contient la courbe du temps d'éxecution ainsi que le tracé de transition de phase.

### Géneration de CSB binaire

## Auteurs

Ce projets est réaliser par :

- Elhouti Chakib -> celhouti@gmail.com
- Kezzoul Massili -> massy.kezzoul@gmail.com
