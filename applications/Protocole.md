##Le protocole

Habituellement, les protocoles sont définis en hexadécimal. Afin que le projet reste lisible, on foncionnera pour l'instant par mots clés de deux ou trois caractères.

###Master or slave

Il existe deux type de commande. Les commandes venant du téléphone en direction du potager. On appellera ces commandes **master**. Celles allant dans l'autre sens, du potager vers le téléphone, seront appelée **slave**. Ces termes viennent du fonctionnement du Bluetooth qui est souvent associé à l'une ou l'autre de ces catégorie selon sa capacité à initier la connection. On peut aussi parler de server/client.

###mas
Le mot clé pour désigner une commande master est **mas**. Toute commande envoyée depuis le téléphone commencera par ce mot clé.

####Les instructions on/off.
Il existe plusieurs fonctionnalités nécessitant d'être allumée ou éteinte. Voici la liste des mots clé utilisés pour ces fonctionnalités

* **man** : Mode manuel
* **pum** : Pompe à eau
* **lig** : Lumière
* **cle** : Mode nettoyage

Ces mots clés doivent être suivis de l'instruction servant à allumer ou éteindre:

* **on** : sert à passer la fonctionnalitée à on
* **of** : sert à passer la fonctionnalitée à off

> Attention ! **Of** n'a qu'un seul *f*, de façon à rester sur deux lettres.

#####Exemples

	masmanon

Permet de passer en mode manuel.

	mascleof

Permet d'éteindre le mode nettoyage

####L'update
L'update des information, causée par l'action de l'utilisateur sur un bouton, est une instruction du téléphone vers le potager. Il s'agit d'une demande qui doit être suivie d'une commande en provenance du potager.

Pour envoyer la demande, il faut envoyer l'instruction **upd**. Ce qui donne la commande suivante

	masupd
	
###sla
Le mot clé pour désigner une commande slave est **sla**. Toute commande envoyée depuis le potager commencera par ce mot clé.

####L'update
La commande envoyée par le téléphone nécessite une réponse. L'instruction pour désigner cette réponse est **upd**. La suite comporte les données sous la forme

	temperature/humidity/light/nextWatering/isLightOn

Ainsi, la commande d'update venant du potager soit avoir cette forme:

	slaupd25/75/80/50/ON
	
* La température est en degrès celcius
* L'humidité est en %
* La lumière est en %
* Le prochain arrosage est en secondes
* Le renseignement sur l'allumage de la lumière est au format ON/OFF

Ainsi, en java par exemple, on pourra récupérer les données ainsi:


```java
String commandDatas = command.substring(4,command.length()-1);
String[] splitString = commandDatas.split("/");
String temperature = splitString[0] + "°C";
String humidity = splitString[1] + "%";
String light = splitString[2] + "%";
String nextWatering = "";
int time = Integer.parseInt(splitString[3]);
if (time > 60)
	nextWatering = time / 60 + "mn";
else
	nextWatering = time + "s";
String isLightOn = splitString[4];
```

###Les notifications
Le potager doit pouvoir envoyer des alertes, des notifications, en cas de comportement étrange ou à risque. À terme, selon les critère que l'utilisateur aura renseigné.

Le mot clé des notification est **not**

Pour l'instant, un seul type de notification est implémenté: celle de la lumière. L'instruction est à nouveau **lig** et doit être suivit de données, en l'occurence le pourcentage de lumière.

La commande qui permet de lever une alerte lors d'un changement important de luminosité est donc, par exemple

	slanotlig42


