# UrbanPotager
UrbanPotager - Potager d'Intétrieur Automatisé et Inteligent

##Présentation du Projet
![image presentation](http://doc.faclab.org/wp-content/uploads/2015/01/Banner-1024x398.png)

L’Urban Potager est un projet de jardin d’intérieur automatisé et open-source, développé par UrbanaPlant en partenariat avec le FacLab.

L’objectif de concevoir et développer un micro-jardin compact. Celui-ci pourra être installé dans tout type de logements, même sans surface cultivable (jardin, terrasse, balcon) et quelque soit son exposition au soleil.

###Fonctionnalités
![image fonctionnalités](http://doc.faclab.org/wp-content/uploads/2015/01/Presentation-UrbanPotager3-1024x713.png)
L’Urban Potager a pour objectif d’aider au maximum le cultivateur urbain à prendre de soin de ses plantes. Voici donc la liste des principales fonctionnalités :

* Possibilité de cultiver toute l’année 4 petites plantes aromatiques en hydro-culture, à partir de graines ou de boutures.
* Gestion des cycles de lumière, par un contrôle de l’éclairage piloté par minuteur, tout en favorisant l’éclairage naturel
* Gestion de l’irrigation sur la base de cycles prédéfinis
* Affichage d’une alerte en cas de faible niveau d’eau, et indication sur le volume d’eau à ajouter.
* Affichage des données du potager sur écran LCD (température, humidité, luminosité, heure, durée avant prochain cycle d’irrigation)

###Choix Techniques

####Irrigation
J’ai choisi d’intégrer un système hydroponique (culture en substrat neutre, et arrosage par dessous) pour sa simplicité de mise en œuvre, sa faible maintenance et son efficacité.

####Eclairage
Après avoir étudier et tester différentes options (lampes éco, ampoule CFL, néons T5, LED, etc…) mon choix s’est porté sur une barre de LED 12v bon marché, proposant un spectre adéquat à la croissance de plantes aromatiques (6400K) et de puissance lumineuse correcte (>1000 lumens)

####Contrôleur
J’ai choisi d’utiliser un Arduino et des capteurs compatibles pour la facilité de mise en œuvre, sa documentation et son faible coût.

###Liste du Matériel
La liste du matériel nécessaire est disponible au téléchargement ici: [MicroEdition_v3-Parts-List.xls ](http://www.urbanpotager.com/wp-content/uploads/2015/02/MicroEdition_v3-Parts-List.xls)

###Organisation du dépôt
Le dépôt est organisé en deux dossiers principaux.

* app: Ce dossier contient l'application Android qui peut être connectée au potager. Cette application est actuellement en cours de prototypage
* object: Ce dossier contient tout ce qui concerne la fabrication de son propre potager, de la réalisation physique au montage électronique.

##Réalisation de la Structure

La structure proposée a été conçue avec l’outil en ligne [MakerCase](http://www.makercase.com/) et modifié avec **Adobe Illustrator** sur la base des dimensions exactes du bac de rangement et de l’épaisseur du MDF sélectionné pour ce projet. Les dimensions de la structure sont donc spécifiques à ce modèle.

> **Attention**
Il existe des variations de taille des écrans LCD et du bloc prise. Il est fortement conseillé de vérifier les dimensions de ces éléments et d’adapter si besoin les ouvertures prévues au niveau du panneau de la face avant et arrière.

Si vous souhaitez utiliser un autre bac (plus grand, plus large, plus haut) ou un autre type de structure (MDF, plexiglass, peuplier, etc…), vous devrez alors réaliser votre propre structure avec [MakerCase](http://www.makercase.com/) et l’adapter avec un logiciel permettant de gérer les images vectorielles comme [Inkscape](https://inkscape.org/fr/) (gratuit) ou **Adobe Illustrator** (payant)

###Téléchargement des fichiers DXF
Les fichiers sont disponibles en téléchargement ici : [MicroEdition_v3_Structure_files.zip](http://www.urbanpotager.com/wp-content/uploads/2015/01/MicroEdition_v3_Structure_files.zip)

![Structure](http://doc.faclab.org/wp-content/uploads/2015/01/1-Structure-01-300x234.png)

###Découpe des 5 panneaux de MDF à la découpe laser
Pour la découpe et la gravure, nous avons utilisé la découpeuse laser **Laserscript 6840** du FacLab avec les paramètres suivants pour du MDF 6mm :

* **Découpe** : Speed=10 / Power=100 / Corner=85 / Scan Gap=0,5 / Passes=2
* **Gravure** : Speed=300 / Power=50 / Corner=15 / Passes=1

> **Attention** Il est conseillé de réaliser des tests de découpe sur le matériel que vous avez choisi avant de procéder à la découpe du 1er panneau. Cela permettra de valider ou modifier les paramètres de découpe, afin de limiter les traces de brulure au niveau du bois. Dans notre exemple, nous avons estimé qu’en augmentant la vitesse de découpe, et en procédant à 2 passages, nous obtenons une découpe propre sans trop de trace de brulure.

D’autres paramètres sont disponibles sur le [Wiki du FacLab](http://wiki.faclab.org/index.php?title=Laserscript_6840#Puissances_de_coupe_en_fonction_du_mat.C3.A9riau)

###Ponçage des panneaux
Il est conseillé de poncer les panneaux afin de supprimer les traces de brulure produites par le laser. Pour cela nous avons utilisé une ponceuse circulaire avec du papier à poncer fin (180-220).

> **Attention** Ne pas poncer les parties qui ont été gravées, au risque de les effacer.

###Montage et collage de la structure
Assembler les différentes faces de la structure avec de la colle à bois blanche/transparente dans l’ordre suivant :

* Coller la plaque du bas (fond) avec plaque de la face avant
* Ajouter et coller la plaque du milieu (celle qui contiendra l’électronique)
* Ajouter et coller les plaques des cotés
* Maintenez la structure avec des serre-joints le temps du séchage

![](http://doc.faclab.org/wp-content/uploads/2015/01/Structure-Installation-Vue1-150x150.png) ![](http://doc.faclab.org/wp-content/uploads/2015/01/Structure-Installation-Vue2-150x150.png) 

###Installation de la prise secteur et des fils
![](http://doc.faclab.org/wp-content/uploads/2015/01/1-Structure-Step5a-150x150.png)

Préparez 2 fils `Vert` et `Marron` d’environ 1,20m, et soudez-les à la prise d’alimentation comme présenter sur la photo.

![](http://doc.faclab.org/wp-content/uploads/2015/01/1-Structure-Step5b-150x150.png) 

Afin de maintenir les fils ensemble, nous les avons torsadés à l’aide d’une perceuse.

![](http://doc.faclab.org/wp-content/uploads/2015/01/1-Structure-Step5c-150x150.png)

Installez ensuite la prise dans l’emplacement prévu à l’arrière du potager, et faites les trous nécessaires afin de la fixer avec 2 vis à écrou.

##Préparation du Bac Hydro-Culture
Nous avons choisi d’utiliser le système de culture hydroponique, qui fournit de bons résultats et permet également de limiter les projections d’eau en dehors du bac de culture.

###Présentation du concept
![](http://doc.faclab.org/wp-content/uploads/2015/01/Concept-Hydroponie-300x300.png)

* 1 : Une solution nutritive est contenue dans le bac, poussée par une pompe à eau
* 2 : La pompe amène la solution nutritive au niveau des paniers hydroponiques, et les arrose les racines par le dessous
* 3 : Les plantes, installées dans un substrat neutre (billes d’argile, laine de roche, etc…), sont directement irriguées au niveau des racines par la solution nutritive
* 4 : Les racines absorbent leurs besoins, et le surplus retombe dans le bac, permettant une oxygénation de la solution. C’est un cycle fermé.
Vous trouverez plus d’information sur ce type de culture sur le site [Hydroponie.org](http://www.hydroponie.org/lhydroponie)

###Téléchargement du fichier DXF de découpe des trous
![](http://doc.faclab.org/wp-content/uploads/2015/01/Bac_Hydro_Pots-300x231.png)

Les fichiers sont disponibles en téléchargement ici : [Bac_Hydro_Pots_files.zip](http://www.urbanpotager.com/wp-content/uploads/2015/01/Bac_Hydro_Pots_files1.zip)

###Découpe des trous et des passages de câbles
Pour la découpe, nous avons utilisé la découpeuse laser Laserscript 6840 du FacLab avec les paramètres suivants pour le couvercle du bac :

* **Découpe** : Speed=30 / Power=90 / Corner=55 / Passes=1

D’autres paramètres sont disponibles sur le [Wiki du FacLab](http://wiki.faclab.org/index.php?title=Laserscript_6840#Puissances_de_coupe_en_fonction_du_mat.C3.A9riau)

###Installation du système d’irrigation
![](http://doc.faclab.org/wp-content/uploads/2015/01/2-Bac-01-150x150.png)

Connectez les jonctions en « T » Gardena avec le tube comme présenté sur la photo, puis installez le tout sous le capot.

Fixez le tout avec des Serflex plastiques.

![](http://doc.faclab.org/wp-content/uploads/2015/01/2-Bac-03-150x150.png)

Connectez et installez ensuite la pompe à la jonction en « T », et faites passer le câble d’alimentation dans le trou prévu à cet effet.

##Intégration de l'Arduino

La partie Arduino va permettre de contrôler notre potager, et de nous donner des indications sur son environnement :

* Température ambiante
* Humidité ambiante
* Niveau de luminosité
* Prochain cycle d’irrigation

Le montage permettra également de piloter :

* La pompe à eau, en fonction d’un cycle prédéfini (20 secondes, toutes les 20mn)
* La lampe LED, en fonction de la luminosité ambiante et d’un cycle prédéfini (de 7h00 à 23h00)
* Un écran LCD, qui fournira visuellement des indications à l’utilisateur

Description des différents affichages :

![](http://doc.faclab.org/wp-content/uploads/2015/01/LCD-Screens.png)

###Installation de l’Arduino et de ses composants  
Afin de représenter au mieux le schéma de notre projet Arduino, j’ai utilisé l’outil de modélisation [Fritzing](http://fritzing.org/home/) (gratuit). Ce logiciel contient la plupart des principaux types d’Arduino, mais également une bibliothèque de composants permettant de modéliser facilement vos projets.

![](http://doc.faclab.org/wp-content/uploads/2015/01/MicroEdition-v3-1024x637.png)

Les fichiers sont disponibles en téléchargement ici : [MicroEdition_v3-Fritzing.zip](http://www.urbanpotager.com/wp-content/uploads/2015/01/MicroEdition_v3-Fritzing.zip)

###Téléversement du code Arduino
Dans un premier temps, récupérer la dernière version du code source pour l’Arduino, disponible en téléchargement ici : [MicroEdition_v3-Arduino.zip](http://www.urbanpotager.com/wp-content/uploads/2015/01/MicroEdition_v3-Arduino.zip)

Téléchargez et installez les librairies nécessaires au bon fonctionnement du programme Arduino dans le répertoire `librairies` de votre Arduino IDE : [MicroEdition_v3-Arduino-Lib.zip](http://www.urbanpotager.com/wp-content/uploads/2015/02/MicroEdition_v3-Arduino-Lib.zip)

Lancez votre Arduino IDE sur votre ordinateur, puis sélectionnez `Arduino UNO` comme type de carte, puis le bon port de communication (en général, `tty.usbserial-XXXXXX` sur un Mac et `COM X` sur PC). Ensuite, ouvrez le fichier `MicroEdition_v3.ino`

> **Attention** 
Lors du premier téléversement du programme, il faudra activer la ligne suivante dans la fonction « void setup() » afin d’initialiser l’horloge temps réelle avec l’horloge de votre ordinateur: 

	RTC.adjust(DateTime(__DATE__, __TIME__));
	
> Ensuite, remettez-là en commentaire, puis téléversez à nouveau le programme. 

Une fois le programme chargé, vous devriez voir apparaitre sur l’écran LCD les informations présentées précédemment, ainsi que l’heure.

###Modification des paramètres
Les paramètres par défaut, définis dans le programme sont les suivants :

* Interrogation des capteurs : toutes les 5 secondes
* Délais entre 2 cycles d’irrigation : 20 minutes
* Durée d’un cycle d’irrigation : 20 secondes
* Heure de démarrage de la lumière (jour) : 07h00 
* Heure d’extinction de la lumière (nuit) : 23h00
* Niveau de luminosité naturelle (*) : 90%

>  (*) La lumière artificielle se déclenchera si la luminosité ambiante se trouve en dessous de ce niveau (définit après quelques tests), et si le potager est dans un cycle de journée.

###Connexion de l’alimentation 12V/5V et du régulateur de tension
J’ai utilisé une alimentation T40-12-5 qui permet de convertir du courant 220V en 2 sorties 5V et 12V. Voici une explication pour connecter votre alimentation, alimenter l’Arduino, la pompe à eau et la lumière (via le module relais) :

![](http://doc.faclab.org/wp-content/uploads/2015/01/PowerSupply-Schema1-579x1024.png)

###Installation dans la structure
L’installation de l’Arduino et de ses composants (écran LCD, capteurs, LED, etc…) dans la structure n’est pas forcément évidente, et peut amener des problèmes avec les connexions.

Voici quelques images présentant l’installation choisie et l’emplacement des composants :

![](http://doc.faclab.org/wp-content/uploads/2015/01/Arduino-Installation-Vue1-300x170.png) ![](http://doc.faclab.org/wp-content/uploads/2015/01/Arduino-Installation-Vue2-300x170.png) ![](http://doc.faclab.org/wp-content/uploads/2015/01/Arduino-Installation-Vue3-300x170.png)

##Mise en Route et Conseils

	En cours
	