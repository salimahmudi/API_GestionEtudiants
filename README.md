# 🎓 API de Gestion des Étudiants avec Service de Notifications

<div align="center">

![Java](https://img.shields.io/badge/Java-11+-orange?style=for-the-badge&logo=java)
![Java EE](https://img.shields.io/badge/Java%20EE-8-blue?style=for-the-badge&logo=java)
![Maven](https://img.shields.io/badge/Maven-3.6+-red?style=for-the-badge&logo=apache-maven)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

*Une API RESTful complète pour la gestion des étudiants avec système de notifications intégré*

</div>

---

## 📋 Table des Matières

- [🎯 Description](#-description)
- [🏗️ Architecture](#️-architecture)
- [🛠️ Technologies](#️-technologies)
- [⚡ Installation](#-installation)
- [🚀 Utilisation](#-utilisation)
- [📧 Service de Notifications](#-service-de-notifications)
- [🌐 Support Multilingue](#-support-multilingue)
- [❌ Gestion des Erreurs](#-gestion-des-erreurs)
- [📚 Documentation API](#-documentation-api)
- [🔒 Sécurité](#-sécurité)
- [🧪 Tests](#-tests)
- [📞 Support](#-support)

---

## 🎯 Description

Cette API RESTful moderne permet une **gestion complète des étudiants** avec un système de notifications automatiques intégré. Développée avec Java EE 8, elle offre une solution robuste et scalable pour les institutions éducatives.

### ✨ Fonctionnalités Principales

- ✅ **CRUD complet** sur l'entité Étudiant
- 📱 **Service de notifications** automatiques (Email, SMS, Push)
- 🌍 **Support multilingue** (Français, Anglais)
- 🛡️ **Gestion robuste des erreurs**
- 📊 **Versioning des APIs**
- 📖 **Documentation OpenAPI/Swagger**
- 🔍 **Recherche avancée**
- ⚡ **Traitement asynchrone**

---

## 🏗️ Architecture

Le projet suit une **architecture en couches** claire et maintenable :

```
EtudiantNotificationAPI/
├── 📁 config/          # Configuration de l'application
├── 📁 controller/      # Contrôleurs REST (JAX-RS)
├── 📁 dto/            # Objets de transfert de données
├── 📁 entity/         # Entités JPA/Hibernate
├── 📁 repository/     # Couche d'accès aux données
├── 📁 service/        # Logique métier et services
├── 📁 exception/      # Gestion centralisée des exceptions
├── 📁 util/          # Utilitaires (internationalisation)
└── 📁 resources/     # Fichiers de configuration et messages
```

### 🔄 Flux de Données

```
Client HTTP → Controller Layer → Service Layer → Repository Layer → Database
                    ↓
            Notification Service → Email/SMS/Push
```

---

## 🛠️ Technologies

| Technologie | Version | Usage |
|-------------|---------|-------|
| **Java EE** | 8 | Framework principal |
| **JAX-RS** | 2.1 | Services REST |
| **JPA/Hibernate** | 2.2 | Persistance des données |
| **CDI** | 2.0 | Injection de dépendances |
| **Bean Validation** | 2.0 | Validation des données |
| **MicroProfile OpenAPI** | 1.1 | Documentation API |
| **H2 Database** | 1.4 | Base de données (développement) |
| **Maven** | 3.6+ | Gestion des dépendances |

---

## ⚡ Installation

### 📋 Prérequis

- ☕ **Java 11** ou supérieur
- 📦 **Maven 3.6+**
- 🖥️ **Serveur d'application Java EE** (WildFly, Payara, GlassFish)

### 🔧 Étapes d'Installation

1. **Cloner le repository**
   ```bash
   git clone https://github.com/salimahmudi/etudiant-notification-api.git
   cd etudiant-notification-api
   ```

2. **Compilation**
   ```bash
   mvn clean compile
   ```

3. **Exécution des tests**
   ```bash
   mvn test
   ```

4. **Packaging**
   ```bash
   mvn clean package
   ```

5. **Déploiement**
   ```bash
   # Copier le fichier WAR généré vers votre serveur d'application
   cp target/etudiant-notification-api.war $WILDFLY_HOME/standalone/deployments/
   ```

### 🗂️ Configuration des Messages

Les messages multilingues sont configurés dans :

```
src/main/resources/
├── messages_fr.properties  # Messages en français
└── messages_en.properties  # Messages en anglais
```

---

## 🚀 Utilisation

### 🛣️ Endpoints Principaux

| Méthode | Endpoint | Description | Statut |
|---------|----------|-------------|--------|
| `GET` | `/v1/etudiants` | Récupérer tous les étudiants | ✅ |
| `GET` | `/v1/etudiants/{id}` | Récupérer un étudiant par ID | ✅ |
| `POST` | `/v1/etudiants` | Créer un nouvel étudiant | ✅ |
| `PUT` | `/v1/etudiants/{id}` | Mettre à jour un étudiant | ✅ |
| `DELETE` | `/v1/etudiants/{id}` | Supprimer un étudiant | ✅ |
| `GET` | `/v1/etudiants/search` | Rechercher des étudiants | ✅ |
| `GET` | `/v1/etudiants/count` | Compter les étudiants | ✅ |

### 💡 Exemples d'Utilisation

#### 📝 Créer un étudiant

```bash
curl -X POST http://localhost:8080/etudiant-notification-api/api/v1/etudiants \
  -H "Content-Type: application/json" \
  -H "Accept-Language: fr" \
  -d '{
    "nom": "HAMMOUDI",
    "prenom": "Salima",
    "email": "salima.hammoudi@email.com",
    "dateNaissance": "2003-03-27",
    "niveau": "Master 1",
    "telephone": "0123456789"
  }'
```

**Réponse :**
```json
{
  "id": 1,
  "nom": "HAMMOUDI",
  "prenom": "Salima",
  "email": "salima.hammoudi@email.com",
  "dateNaissance": "2003-03-27",
  "niveau": "Master 1",
  "telephone": "0123456789",
  "dateCreation": "2025-01-15T10:30:00Z"
}
```

#### 📋 Récupérer tous les étudiants

```bash
curl -X GET "http://localhost:8080/etudiant-notification-api/api/v1/etudiants?lang=fr&page=0&size=10"
```

#### 🔍 Rechercher par email

```bash
curl -X GET "http://localhost:8080/etudiant-notification-api/api/v1/etudiants/search?email=salima.hammoudi@email.com&lang=fr"
```

#### 📊 Obtenir le nombre d'étudiants

```bash
curl -X GET "http://localhost:8080/etudiant-notification-api/api/v1/etudiants/count"
```

---

## 📧 Service de Notifications

Le service de notifications supporte **plusieurs canaux de communication** :

### 📱 Canaux Supportés

| Canal | Description | Statut |
|-------|-------------|--------|
| 📧 **Email** | Notifications par courrier électronique | ✅ Actif |
| 📱 **SMS** | Messages texte | ✅ Actif |
| 🔔 **Push** | Notifications push | 🚧 En développement |

### 🔄 Déclencheurs Automatiques

Les notifications sont envoyées automatiquement lors des opérations suivantes :

- ➕ **Création** d'un étudiant
- ✏️ **Modification** d'un étudiant
- 🗑️ **Suppression** d'un étudiant

### ⚡ Traitement Asynchrone

```java
@Asynchronous
public void envoyerNotification(TypeNotification type, Etudiant etudiant) {
    // Traitement asynchrone pour ne pas bloquer l'opération principale
}
```

> **Note :** En cas d'échec du service de notification, l'opération sur l'étudiant reste valide.

---

## 🌐 Support Multilingue

### 🗣️ Langues Supportées

- 🇫🇷 **Français** (par défaut)
- 🇬🇧 **Anglais**

### 🔧 Utilisation

Ajoutez le paramètre `lang` à vos requêtes :

```bash
# Français (par défaut)
curl -X GET "http://localhost:8080/api/v1/etudiants?lang=fr"

# Anglais
curl -X GET "http://localhost:8080/api/v1/etudiants?lang=en"
```

### 📝 Configuration des Messages

```properties
# messages_fr.properties
etudiant.cree.succes=Étudiant créé avec succès
etudiant.non.trouve=Étudiant non trouvé

# messages_en.properties
etudiant.cree.succes=Student created successfully
etudiant.non.trouve=Student not found