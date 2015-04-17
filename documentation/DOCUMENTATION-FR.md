# UrbanPotager
UrbanPotager - Potager d'Intétrieur Automatisé et Inteligent

##Présentation du Projet UrbanPotager
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

La liste du matériel est également disponible ici , sur github: <https://github.com/UrbanaPlant/urbanpotager/blob/master/object/List.xls>

##Réalisation de la Structure

La structure proposée a été conçue avec l’outil en ligne [MakerCase](http://www.makercase.com/) et modifié avec **Adobe Illustrator** sur la base des dimensions exactes du bac de rangement et de l’épaisseur du MDF sélectionné pour ce projet. Les dimensions de la structure sont donc spécifiques à ce modèle.

> **Attention**
Il existe des variations de taille des écrans LCD et du bloc prise. Il est fortement conseillé de vérifier les dimensions de ces éléments et d’adapter si besoin les ouvertures prévues au niveau du panneau de la face avant et arrière.

Si vous souhaitez utiliser un autre bac (plus grand, plus large, plus haut) ou un autre type de structure (MDF, plexiglass, peuplier, etc…), vous devrez alors réaliser votre propre structure avec [MakerCase](http://www.makercase.com/) et l’adapter avec un logiciel permettant de gérer les images vectorielles comme [Inkscape](https://inkscape.org/fr/) (gratuit) ou **Adobe Illustrator** (payant)

###Téléchargement des fichiers DXF
Les fichiers sont disponibles en téléchargement ici : [MicroEdition_v3_Structure_files.zip](http://www.urbanpotager.com/wp-content/uploads/2015/01/MicroEdition_v3_Structure_files.zip)

Vous pouvez également les trouver [sur github](https://github.com/UrbanaPlant/urbanpotager/tree/master/object/UrbanPotager%20-%20MicroEdition%20-%20v3/Structure).

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

Vous pouvez également les télécharger [sur github](https://github.com/UrbanaPlant/urbanpotager/tree/master/object/UrbanPotager%20-%20MicroEdition%20-%20v3/Water%20tank).

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

Intégration de l'Arduino

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

Ils sont également disponible [sur github](https://github.com/UrbanaPlant/urbanpotager/tree/master/object/UrbanPotager%20-%20MicroEdition%20-%20v3/Arduino/Elec).

###Téléversement du code Arduino
Dans un premier temps, récupérer la dernière version du code source pour l’Arduino, disponible en téléchargement ici : [MicroEdition_v3-Arduino.zip](http://www.urbanpotager.com/wp-content/uploads/2015/01/MicroEdition_v3-Arduino.zip)

Téléchargez et installez les librairies nécessaires au bon fonctionnement du programme Arduino dans le répertoire `librairies` de votre Arduino IDE : [MicroEdition_v3-Arduino-Lib.zip](http://www.urbanpotager.com/wp-content/uploads/2015/02/MicroEdition_v3-Arduino-Lib.zip)

Les [librairies](https://github.com/UrbanaPlant/urbanpotager/tree/master/object/UrbanPotager%20-%20MicroEdition%20-%20v3/Arduino/libraries) et le [code](https://github.com/UrbanaPlant/urbanpotager/tree/master/object/UrbanPotager%20-%20MicroEdition%20-%20v3/Arduino/UrbanPotager) se trouvent également sur github.

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



	En cours
	


## Creative Commons

# Attribution-NonCommercial-ShareAlike 4.0 International

Creative Commons Corporation (“Creative Commons”) is not a law firm and does not provide legal services or legal advice. Distribution of Creative Commons public licenses does not create a lawyer-client or other relationship. Creative Commons makes its licenses and related information available on an “as-is” basis. Creative Commons gives no warranties regarding its licenses, any material licensed under their terms and conditions, or any related information. Creative Commons disclaims all liability for damages resulting from their use to the fullest extent possible.

### Using Creative Commons Public Licenses

Creative Commons public licenses provide a standard set of terms and conditions that creators and other rights holders may use to share original works of authorship and other material subject to copyright and certain other rights specified in the public license below. The following considerations are for informational purposes only, are not exhaustive, and do not form part of our licenses.

* __Considerations for licensors:__ Our public licenses are intended for use by those authorized to give the public permission to use material in ways otherwise restricted by copyright and certain other rights. Our licenses are irrevocable. Licensors should read and understand the terms and conditions of the license they choose before applying it. Licensors should also secure all rights necessary before applying our licenses so that the public can reuse the material as expected. Licensors should clearly mark any material not subject to the license. This includes other CC-licensed material, or material used under an exception or limitation to copyright. [More considerations for licensors](http://wiki.creativecommons.org/Considerations_for_licensors_and_licensees#Considerations_for_licensors).

* __Considerations for the public:__ By using one of our public licenses, a licensor grants the public permission to use the licensed material under specified terms and conditions. If the licensor’s permission is not necessary for any reason–for example, because of any applicable exception or limitation to copyright–then that use is not regulated by the license. Our licenses grant only permissions under copyright and certain other rights that a licensor has authority to grant. Use of the licensed material may still be restricted for other reasons, including because others have copyright or other rights in the material. A licensor may make special requests, such as asking that all changes be marked or described. Although not required by our licenses, you are encouraged to respect those requests where reasonable. [More considerations for the public](http://wiki.creativecommons.org/Considerations_for_licensors_and_licensees#Considerations_for_licensees).

## Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International Public License

By exercising the Licensed Rights (defined below), You accept and agree to be bound by the terms and conditions of this Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International Public License ("Public License"). To the extent this Public License may be interpreted as a contract, You are granted the Licensed Rights in consideration of Your acceptance of these terms and conditions, and the Licensor grants You such rights in consideration of benefits the Licensor receives from making the Licensed Material available under these terms and conditions.

### Section 1 – Definitions.

a. __Adapted Material__ means material subject to Copyright and Similar Rights that is derived from or based upon the Licensed Material and in which the Licensed Material is translated, altered, arranged, transformed, or otherwise modified in a manner requiring permission under the Copyright and Similar Rights held by the Licensor. For purposes of this Public License, where the Licensed Material is a musical work, performance, or sound recording, Adapted Material is always produced where the Licensed Material is synched in timed relation with a moving image.

b. __Adapter's License__ means the license You apply to Your Copyright and Similar Rights in Your contributions to Adapted Material in accordance with the terms and conditions of this Public License.

c. __BY-NC-SA Compatible License__ means a license listed at [creativecommons.org/compatiblelicenses](http://creativecommons.org/compatiblelicenses), approved by Creative Commons as essentially the equivalent of this Public License.

d. __Copyright and Similar Rights__ means copyright and/or similar rights closely related to copyright including, without limitation, performance, broadcast, sound recording, and Sui Generis Database Rights, without regard to how the rights are labeled or categorized. For purposes of this Public License, the rights specified in Section 2(b)(1)-(2) are not Copyright and Similar Rights.

e. __Effective Technological Measures__ means those measures that, in the absence of proper authority, may not be circumvented under laws fulfilling obligations under Article 11 of the WIPO Copyright Treaty adopted on December 20, 1996, and/or similar international agreements.

f. __Exceptions and Limitations__ means fair use, fair dealing, and/or any other exception or limitation to Copyright and Similar Rights that applies to Your use of the Licensed Material.

g. __License Elements__ means the license attributes listed in the name of a Creative Commons Public License. The License Elements of this Public License are Attribution, NonCommercial, and ShareAlike.

h. __Licensed Material__ means the artistic or literary work, database, or other material to which the Licensor applied this Public License.

i. __Licensed Rights__ means the rights granted to You subject to the terms and conditions of this Public License, which are limited to all Copyright and Similar Rights that apply to Your use of the Licensed Material and that the Licensor has authority to license.

h. __Licensor__ means the individual(s) or entity(ies) granting rights under this Public License.

i. __NonCommercial__ means not primarily intended for or directed towards commercial advantage or monetary compensation. For purposes of this Public License, the exchange of the Licensed Material for other material subject to Copyright and Similar Rights by digital file-sharing or similar means is NonCommercial provided there is no payment of monetary compensation in connection with the exchange.

j. __Share__ means to provide material to the public by any means or process that requires permission under the Licensed Rights, such as reproduction, public display, public performance, distribution, dissemination, communication, or importation, and to make material available to the public including in ways that members of the public may access the material from a place and at a time individually chosen by them.

k. __Sui Generis Database Rights__ means rights other than copyright resulting from Directive 96/9/EC of the European Parliament and of the Council of 11 March 1996 on the legal protection of databases, as amended and/or succeeded, as well as other essentially equivalent rights anywhere in the world.

l. __You__ means the individual or entity exercising the Licensed Rights under this Public License. Your has a corresponding meaning.

### Section 2 – Scope.

a. ___License grant.___

    1. Subject to the terms and conditions of this Public License, the Licensor hereby grants You a worldwide, royalty-free, non-sublicensable, non-exclusive, irrevocable license to exercise the Licensed Rights in the Licensed Material to:

        A. reproduce and Share the Licensed Material, in whole or in part, for NonCommercial purposes only; and

        B. produce, reproduce, and Share Adapted Material for NonCommercial purposes only.

    2. __Exceptions and Limitations.__ For the avoidance of doubt, where Exceptions and Limitations apply to Your use, this Public License does not apply, and You do not need to comply with its terms and conditions.

    3. __Term.__ The term of this Public License is specified in Section 6(a).

    4. __Media and formats; technical modifications allowed.__ The Licensor authorizes You to exercise the Licensed Rights in all media and formats whether now known or hereafter created, and to make technical modifications necessary to do so. The Licensor waives and/or agrees not to assert any right or authority to forbid You from making technical modifications necessary to exercise the Licensed Rights, including technical modifications necessary to circumvent Effective Technological Measures. For purposes of this Public License, simply making modifications authorized by this Section 2(a)(4) never produces Adapted Material.

    5. __Downstream recipients.__

        A. __Offer from the Licensor – Licensed Material.__ Every recipient of the Licensed Material automatically receives an offer from the Licensor to exercise the Licensed Rights under the terms and conditions of this Public License.

        B. __Additional offer from the Licensor – Adapted Material.__ Every recipient of Adapted Material from You automatically receives an offer from the Licensor to exercise the Licensed Rights in the Adapted Material under the conditions of the Adapter’s License You apply.

        C. __No downstream restrictions.__ You may not offer or impose any additional or different terms or conditions on, or apply any Effective Technological Measures to, the Licensed Material if doing so restricts exercise of the Licensed Rights by any recipient of the Licensed Material.

    6. __No endorsement.__ Nothing in this Public License constitutes or may be construed as permission to assert or imply that You are, or that Your use of the Licensed Material is, connected with, or sponsored, endorsed, or granted official status by, the Licensor or others designated to receive attribution as provided in Section 3(a)(1)(A)(i).

b. ___Other rights.___

    1. Moral rights, such as the right of integrity, are not licensed under this Public License, nor are publicity, privacy, and/or other similar personality rights; however, to the extent possible, the Licensor waives and/or agrees not to assert any such rights held by the Licensor to the limited extent necessary to allow You to exercise the Licensed Rights, but not otherwise.

    2. Patent and trademark rights are not licensed under this Public License.

    3. To the extent possible, the Licensor waives any right to collect royalties from You for the exercise of the Licensed Rights, whether directly or through a collecting society under any voluntary or waivable statutory or compulsory licensing scheme. In all other cases the Licensor expressly reserves any right to collect such royalties, including when the Licensed Material is used other than for NonCommercial purposes.

### Section 3 – License Conditions.

Your exercise of the Licensed Rights is expressly made subject to the following conditions.

a. ___Attribution.___

    1. If You Share the Licensed Material (including in modified form), You must:

        A. retain the following if it is supplied by the Licensor with the Licensed Material:

            i. identification of the creator(s) of the Licensed Material and any others designated to receive attribution, in any reasonable manner requested by the Licensor (including by pseudonym if designated);

            ii. a copyright notice;

            iii. a notice that refers to this Public License;

            iv. a notice that refers to the disclaimer of warranties;

            v. a URI or hyperlink to the Licensed Material to the extent reasonably practicable;

        B. indicate if You modified the Licensed Material and retain an indication of any previous modifications; and

        C. indicate the Licensed Material is licensed under this Public License, and include the text of, or the URI or hyperlink to, this Public License.

    2. You may satisfy the conditions in Section 3(a)(1) in any reasonable manner based on the medium, means, and context in which You Share the Licensed Material. For example, it may be reasonable to satisfy the conditions by providing a URI or hyperlink to a resource that includes the required information.

    3. If requested by the Licensor, You must remove any of the information required by Section 3(a)(1)(A) to the extent reasonably practicable.

b. ___ShareAlike.___

In addition to the conditions in Section 3(a), if You Share Adapted Material You produce, the following conditions also apply.

1. The Adapter’s License You apply must be a Creative Commons license with the same License Elements, this version or later, or a BY-NC-SA Compatible License.

2. You must include the text of, or the URI or hyperlink to, the Adapter's License You apply. You may satisfy this condition in any reasonable manner based on the medium, means, and context in which You Share Adapted Material.

3. You may not offer or impose any additional or different terms or conditions on, or apply any Effective Technological Measures to, Adapted Material that restrict exercise of the rights granted under the Adapter's License You apply.

### Section 4 – Sui Generis Database Rights.

Where the Licensed Rights include Sui Generis Database Rights that apply to Your use of the Licensed Material:

a. for the avoidance of doubt, Section 2(a)(1) grants You the right to extract, reuse, reproduce, and Share all or a substantial portion of the contents of the database for NonCommercial purposes only;

b. if You include all or a substantial portion of the database contents in a database in which You have Sui Generis Database Rights, then the database in which You have Sui Generis Database Rights (but not its individual contents) is Adapted Material, including for purposes of Section 3(b); and

c. You must comply with the conditions in Section 3(a) if You Share all or a substantial portion of the contents of the database.

For the avoidance of doubt, this Section 4 supplements and does not replace Your obligations under this Public License where the Licensed Rights include other Copyright and Similar Rights.

### Section 5 – Disclaimer of Warranties and Limitation of Liability.

a. __Unless otherwise separately undertaken by the Licensor, to the extent possible, the Licensor offers the Licensed Material as-is and as-available, and makes no representations or warranties of any kind concerning the Licensed Material, whether express, implied, statutory, or other. This includes, without limitation, warranties of title, merchantability, fitness for a particular purpose, non-infringement, absence of latent or other defects, accuracy, or the presence or absence of errors, whether or not known or discoverable. Where disclaimers of warranties are not allowed in full or in part, this disclaimer may not apply to You.__

b. __To the extent possible, in no event will the Licensor be liable to You on any legal theory (including, without limitation, negligence) or otherwise for any direct, special, indirect, incidental, consequential, punitive, exemplary, or other losses, costs, expenses, or damages arising out of this Public License or use of the Licensed Material, even if the Licensor has been advised of the possibility of such losses, costs, expenses, or damages. Where a limitation of liability is not allowed in full or in part, this limitation may not apply to You.__

c. The disclaimer of warranties and limitation of liability provided above shall be interpreted in a manner that, to the extent possible, most closely approximates an absolute disclaimer and waiver of all liability.

### Section 6 – Term and Termination.

a. This Public License applies for the term of the Copyright and Similar Rights licensed here. However, if You fail to comply with this Public License, then Your rights under this Public License terminate automatically.

b. Where Your right to use the Licensed Material has terminated under Section 6(a), it reinstates:

    1. automatically as of the date the violation is cured, provided it is cured within 30 days of Your discovery of the violation; or

    2. upon express reinstatement by the Licensor.

    For the avoidance of doubt, this Section 6(b) does not affect any right the Licensor may have to seek remedies for Your violations of this Public License.

c. For the avoidance of doubt, the Licensor may also offer the Licensed Material under separate terms or conditions or stop distributing the Licensed Material at any time; however, doing so will not terminate this Public License.

d. Sections 1, 5, 6, 7, and 8 survive termination of this Public License.

### Section 7 – Other Terms and Conditions.

a. The Licensor shall not be bound by any additional or different terms or conditions communicated by You unless expressly agreed.

b. Any arrangements, understandings, or agreements regarding the Licensed Material not stated herein are separate from and independent of the terms and conditions of this Public License.

### Section 8 – Interpretation.

a. For the avoidance of doubt, this Public License does not, and shall not be interpreted to, reduce, limit, restrict, or impose conditions on any use of the Licensed Material that could lawfully be made without permission under this Public License.

b. To the extent possible, if any provision of this Public License is deemed unenforceable, it shall be automatically reformed to the minimum extent necessary to make it enforceable. If the provision cannot be reformed, it shall be severed from this Public License without affecting the enforceability of the remaining terms and conditions.

c. No term or condition of this Public License will be waived and no failure to comply consented to unless expressly agreed to by the Licensor.

d. Nothing in this Public License constitutes or may be interpreted as a limitation upon, or waiver of, any privileges and immunities that apply to the Licensor or You, including from the legal processes of any jurisdiction or authority.

```
Creative Commons is not a party to its public licenses. Notwithstanding, Creative Commons may elect to apply one of its public licenses to material it publishes and in those instances will be considered the “Licensor.” Except for the limited purpose of indicating that material is shared under a Creative Commons public license or as otherwise permitted by the Creative Commons policies published at [creativecommons.org/policies](http://creativecommons.org/policies), Creative Commons does not authorize the use of the trademark “Creative Commons” or any other trademark or logo of Creative Commons without its prior written consent including, without limitation, in connection with any unauthorized modifications to any of its public licenses or any other arrangements, understandings, or agreements concerning use of licensed material. For the avoidance of doubt, this paragraph does not form part of the public licenses.

Creative Commons may be contacted at creativecommons.org
```

