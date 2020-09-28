# MeteoroLog
Android application to watch weather which was set on a server. (Projet fait dans le cadre du module de développement mobile en Licence Pro).

Ce projet a été réalisé avec Eloi Cambray.

Je me suis occupé de la partie analyse/conception.
Je me suis également occupé de la partie BDD/API.

Au niveau du code :
-j'ai fait la gestion de la connexion à la base, notamment au travers de la classe Requester (et de ses utilisations).
-je me suis occupé du parsing des objets en JSON pour permettre leur utilisation dans nos vues.
-j'ai effectué l'activité Meteo dans son intégralité (vue, affichage des données de la station météo)
-mon collègue a fait l'activité Settings, et la liste dans l'activité Main
-j'ai effectué le reste de l'activité Main (récupération des données pour la liste, affichage des données de la station préférée).

Cette application, bien que l'interface reste à améliorer, fonctionnait très bien et affichait les données météo qu'on lui demandait.

Attention, pour avoir des données prises en compte dans l'application, il faudra que la date des données dans la table meteo soit cohérente.
