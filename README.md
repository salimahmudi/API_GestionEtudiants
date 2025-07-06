# API de Gestion des Étudiants avec Service de Notifications

## Description

Cette API RESTful permet la gestion complète des étudiants avec un système de notifications intégré. Elle est développée en Java EE et offre les fonctionnalités suivantes :

- **CRUD complet** sur l'entité Étudiant
- **Service de notifications** automatiques (email, SMS, push)
- **Support multilingue** (français, anglais)
- **Gestion robuste des erreurs**
- **Versioning des APIs**
- **Documentation OpenAPI/Swagger**

## Architecture

Le projet suit une architecture en couches avec les composants suivants :

EtudiantNotificationAPI/
- **config/** # Configuration de l'application
- **controller/** # Contrôleurs REST
- **dto/** # Objets de transfert de données
- **entity/** # Entités JPA
- **repository/** # Couche d'accès aux données
- **service/** # Logique métier
- **exception/** # Gestion des exceptions
- **util/** # Utilitaires (internationalisation)

## Technologies Utilisées

- **Java EE 8** - Framework principal
- **JAX-RS** - Services REST
- **JPA/Hibernate** - Persistance des données
- **CDI** - Injection de dépendances
- **Bean Validation** - Validation des données
- **MicroProfile OpenAPI** - Documentation API
- **H2 Database** - Base de données (développement)
- **Maven** - Gestion des dépendances

## Installation et Déploiement

### Prérequis

- Java 11 ou supérieur
- Maven 3.6+
- Serveur d'application Java EE (WildFly, Payara, etc.)

### Compilation
mvn clean compile

### Tests
mvn test

### Packaging
mvn clean package


### Messages Multilingues

Les messages sont configurés dans les fichiers :
- \`messages_fr.properties\` (français)
- \`messages_en.properties\` (anglais)

## Utilisation de l'API

### Endpoints Principaux

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | \`/v1/etudiants\` | Récupérer tous les étudiants |
| GET | \`/v1/etudiants/{id}\` | Récupérer un étudiant par ID |
| POST | \`/v1/etudiants\` | Créer un nouvel étudiant |
| PUT | \`/v1/etudiants/{id}\` | Mettre à jour un étudiant |
| DELETE | \`/v1/etudiants/{id}\` | Supprimer un étudiant |
| GET | \`/v1/etudiants/search\` | Rechercher des étudiants |
| GET | \`/v1/etudiants/count\` | Compter les étudiants |

### Exemples d'Utilisation

#### Créer un étudiant
curl -X POST http://localhost:8080/etudiant-notification-api/api/v1/etudiants \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "test",
    "prenom": "test",
    "email": "test.test@email.com",
    "dateNaissance": "1995-05-15",
    "niveau": "Master 1",
    "telephone": "0123456789"
  }'

#### Récupérer tous les étudiants
curl -X GET http://localhost:8080/etudiant-notification-api/api/v1/etudiants?lang=fr


#### Rechercher par email
curl -X GET "http://localhost:8080/etudiant-notification-api/api/v1/etudiants/search?email=jean.dupont@email.com"


### Support Multilingue

Ajoutez le paramètre \`lang\` à vos requêtes :
- \`lang=fr\` pour le français (par défaut)
- \`lang=en\` pour l'anglais

## Service de Notifications

Le service de notifications supporte plusieurs canaux :

- **Email** - Notifications par email
- **SMS** - Messages texte
- **Push** - Notifications push

Les notifications sont envoyées automatiquement lors des opérations :
- Création d'un étudiant
- Modification d'un étudiant  
- Suppression d'un étudiant

### Configuration des Notifications

Les notifications sont envoyées de manière asynchrone pour ne pas bloquer les opérations principales. En cas d'échec du service de notification, l'opération sur l'étudiant reste valide.

## Gestion des Erreurs

L'API utilise un système de gestion d'erreurs centralisé avec :

- **Codes d'erreur standardisés**
- **Messages multilingues**
- **Détails des erreurs de validation**
- **Logging complet**

### Codes d'Erreur

| Code | Type | Description |
|------|------|-------------|
| VALIDATION_001 | Validation | Erreurs de validation des données |
| BUSINESS_001 | Métier | Violation des règles métier |
| RESOURCE_001 | Ressource | Ressource non trouvée |
| EXTERNAL_001 | Service externe | Service externe indisponible |
| INTERNAL_001 | Interne | Erreur interne du serveur |

## Documentation API

La documentation complète de l'API est disponible via OpenAPI/Swagger :

- **Spécification OpenAPI** : \`/openapi.yaml\`
- **Interface Swagger UI** : Accessible via le serveur d'application

## Versioning

L'API utilise un versioning par URL avec le préfixe \`/v1/\`. Les versions futures utiliseront \`/v2/\`, \`/v3/\`, etc.

## Sécurité

### Authentification (À implémenter)

L'API est prête pour l'authentification JWT. Ajoutez votre implémentation d'authentification selon vos besoins.

### Validation des Données

Toutes les données d'entrée sont validées avec Bean Validation :
- Validation des formats (email, téléphone)
- Validation des longueurs
- Validation des champs obligatoires

## Monitoring et Logs

L'application utilise le logging Java standard avec différents niveaux :
- **INFO** : Opérations normales
- **WARNING** : Problèmes non critiques
- **SEVERE** : Erreurs critiques

## Tests

### Tests Unitaires

Exécutez les tests unitaires :
mvn test

### Tests d'Intégration

Les tests d'intégration utilisent une base de données H2 en mémoire.


## Licence

Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.

## Support

Pour toute question ou problème :
- Email : salimahammoudi1@gmail.com
