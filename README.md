# Bridge Designer
## Sites d'origine
* [Bridge contest](https://www.bridgecontest.org).
* [Source forge](https://sourceforge.net/projects/wpbdc/). Le site de la source.
* [Github](https://github.com/egeland/bridge-designer). Une autre version de la source.

## Description
* Simulation de pont 2D.
* [Vidéo de présentation](https://bridgedesigner.org/tutorial/).

## Mes modifications
### Modifications fonctionnelles
L'objectif de ces modifications (outre le français) est de produire une simulation proche des maquettes que nous faisons faire aux élèves en carton bois et MDF.
* Traduction en français.
* Coût en euros.
* Ajout de matériaux pour les maquettes (carton et MDF).
* Ajout de la section rectangulaire pour les matériaux.
* Ajout de la modification de la charge en live avec 2 camions 225 kN et 480 kN qui ont une charge répartie 20 % sur l’essieu avant et 80 % sur l’essieu arrière et une charge libre (0 à 99 999 kN) répartie 50 % sur chaque essieu. Le but de cette dernière est de prévoir la charge à laquelle le pont rompra dès la simulation. Les changements sont possible uniquement dans la simulation  haute qualitée mais ils influent sur les 2 simulations.
### Modifications cosmétiques
* Image du pont de Saint-Nazaire, cartouche et modification du setupWizard pour correspondre à nos TP.
* Changement des icônes et de quelques images un rien monotones.
### Modifications techniques
* Suppression de la signature des fichiers (voir le **build - Original.xml** pour la restaurer). J'en suis désolé mais je n'ai pas réussis à le faire signer. Peut-être une prochaine version.
* Update de JDK6 vers JDK7 pour supprimer le bug des polices dans les jFolderOpen.
* Création d'un package MSI pour déploiement lycée.

## Comment compiler  
* Récupérer le projet sur github.
* Installer JDK 1.7 (jdk-7u80-windows-x64).
* Installer NetBean 7.0.
* Ouvrir Netbean et de lui, le projet WPBD.
* Restaurer le lien vers JDK 1.7 :
    * Clic droit sur le projet > Properties,
    * Dans la fenêtre à gauche choisir libraries,
    * Choisir le jdk 1.7 que vous venez d’installer,
* Restaurer les liens vers les librairies (Tools > Libraries).
* Copier le jre7 dans le dossier racine du projet pour l'installation ou modifier le fichier nsis pour qu'il le trouve.

    Les librairies NetBeans doivent être ok. Le dossier **lib** est celui sous la racine du projet. 
    
    ![Alt text](LiensLib.png)
        