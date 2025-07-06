package com.example.etudiantapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"fr\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>API de Gestion des Étudiants</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
                "            margin: 0;\n" +
                "            padding: 20px;\n" +
                "            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                "            color: #333;\n" +
                "            min-height: 100vh;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 1200px;\n" +
                "            margin: 0 auto;\n" +
                "            background: white;\n" +
                "            border-radius: 15px;\n" +
                "            padding: 30px;\n" +
                "            box-shadow: 0 10px 30px rgba(0,0,0,0.2);\n" +
                "        }\n" +
                "        h1 {\n" +
                "            text-align: center;\n" +
                "            color: #4a5568;\n" +
                "            margin-bottom: 10px;\n" +
                "            font-size: 2.5em;\n" +
                "        }\n" +
                "        .subtitle {\n" +
                "            text-align: center;\n" +
                "            color: #718096;\n" +
                "            margin-bottom: 40px;\n" +
                "            font-size: 1.2em;\n" +
                "        }\n" +
                "        .section {\n" +
                "            margin-bottom: 40px;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 10px;\n" +
                "            background: #f7fafc;\n" +
                "            border-left: 5px solid #4299e1;\n" +
                "        }\n" +
                "        .section h2 {\n" +
                "            color: #2d3748;\n" +
                "            margin-bottom: 20px;\n" +
                "            font-size: 1.5em;\n" +
                "        }\n" +
                "        .links-grid {\n" +
                "            display: grid;\n" +
                "            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));\n" +
                "            gap: 15px;\n" +
                "        }\n" +
                "        .link-card {\n" +
                "            background: white;\n" +
                "            padding: 15px;\n" +
                "            border-radius: 8px;\n" +
                "            border: 1px solid #e2e8f0;\n" +
                "            transition: all 0.3s ease;\n" +
                "        }\n" +
                "        .link-card:hover {\n" +
                "            transform: translateY(-2px);\n" +
                "            box-shadow: 0 5px 15px rgba(0,0,0,0.1);\n" +
                "        }\n" +
                "        .link-card a {\n" +
                "            text-decoration: none;\n" +
                "            color: #4299e1;\n" +
                "            font-weight: 600;\n" +
                "            display: block;\n" +
                "            margin-bottom: 5px;\n" +
                "        }\n" +
                "        .link-card a:hover {\n" +
                "            color: #2b6cb0;\n" +
                "        }\n" +
                "        .description {\n" +
                "            color: #718096;\n" +
                "            font-size: 0.9em;\n" +
                "        }\n" +
                "        .method {\n" +
                "            display: inline-block;\n" +
                "            padding: 2px 8px;\n" +
                "            border-radius: 4px;\n" +
                "            font-size: 0.8em;\n" +
                "            font-weight: bold;\n" +
                "            margin-right: 10px;\n" +
                "        }\n" +
                "        .get { background: #c6f6d5; color: #22543d; }\n" +
                "        .post { background: #fed7d7; color: #742a2a; }\n" +
                "        .put { background: #feebc8; color: #7c2d12; }\n" +
                "        .delete { background: #e2e8f0; color: #4a5568; }\n" +
                "        .status {\n" +
                "            text-align: center;\n" +
                "            padding: 20px;\n" +
                "            background: #f0fff4;\n" +
                "            border-radius: 10px;\n" +
                "            border: 1px solid #9ae6b4;\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "        .status h3 {\n" +
                "            color: #22543d;\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "        .test-section {\n" +
                "            background: #fff5f5;\n" +
                "            border-left-color: #f56565;\n" +
                "        }\n" +
                "        .test-form {\n" +
                "            background: white;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            margin-top: 15px;\n" +
                "        }\n" +
                "        .form-group {\n" +
                "            margin-bottom: 15px;\n" +
                "        }\n" +
                "        .form-group label {\n" +
                "            display: block;\n" +
                "            margin-bottom: 5px;\n" +
                "            font-weight: 600;\n" +
                "            color: #4a5568;\n" +
                "        }\n" +
                "        .form-group input, .form-group select {\n" +
                "            width: 100%;\n" +
                "            padding: 10px;\n" +
                "            border: 1px solid #e2e8f0;\n" +
                "            border-radius: 5px;\n" +
                "            font-size: 14px;\n" +
                "            box-sizing: border-box;\n" +
                "        }\n" +
                "        .btn {\n" +
                "            background: #4299e1;\n" +
                "            color: white;\n" +
                "            padding: 10px 20px;\n" +
                "            border: none;\n" +
                "            border-radius: 5px;\n" +
                "            cursor: pointer;\n" +
                "            font-weight: 600;\n" +
                "        }\n" +
                "        .btn:hover {\n" +
                "            background: #3182ce;\n" +
                "        }\n" +
                "        #result {\n" +
                "            margin-top: 15px;\n" +
                "            padding: 15px;\n" +
                "            background: #f7fafc;\n" +
                "            border-radius: 5px;\n" +
                "            border: 1px solid #e2e8f0;\n" +
                "            white-space: pre-wrap;\n" +
                "            font-family: monospace;\n" +
                "            display: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>🎓 API de Gestion des Étudiants</h1>\n" +
                "        <p class=\"subtitle\">Interface de navigation et de test</p>\n" +
                "        \n" +
                "        <div class=\"status\">\n" +
                "            <h3>✅ API Opérationnelle</h3>\n" +
                "            <p>Serveur démarré avec succès - Toutes les fonctionnalités sont disponibles</p>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"section\">\n" +
                "            <h2>📚 Documentation et Outils</h2>\n" +
                "            <div class=\"links-grid\">\n" +
                "                <div class=\"link-card\">\n" +
                "                    <a href=\"/swagger-ui.html\" target=\"_blank\">📖 Documentation Swagger</a>\n" +
                "                    <p class=\"description\">Interface interactive pour tester l'API</p>\n" +
                "                </div>\n" +
                "                <div class=\"link-card\">\n" +
                "                    <a href=\"/h2-console\" target=\"_blank\">🗄️ Console Base de Données</a>\n" +
                "                    <p class=\"description\">Accès direct à la base de données H2</p>\n" +
                "                </div>\n" +
                "                <div class=\"link-card\">\n" +
                "                    <a href=\"/test\" target=\"_blank\">🧪 Test Simple</a>\n" +
                "                    <p class=\"description\">Vérifier que l'API répond</p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"section\">\n" +
                "            <h2>👥 Gestion des Étudiants</h2>\n" +
                "            <div class=\"links-grid\">\n" +
                "                <div class=\"link-card\">\n" +
                "                    <span class=\"method get\">GET</span>\n" +
                "                    <a href=\"/api/v1/etudiants\" target=\"_blank\">Tous les étudiants</a>\n" +
                "                    <p class=\"description\">Récupérer la liste complète des étudiants</p>\n" +
                "                </div>\n" +
                "                <div class=\"link-card\">\n" +
                "                    <span class=\"method get\">GET</span>\n" +
                "                    <a href=\"/api/v1/etudiants/count\" target=\"_blank\">Nombre d'étudiants</a>\n" +
                "                    <p class=\"description\">Compter le total des étudiants enregistrés</p>\n" +
                "                </div>\n" +
                "                <div class=\"link-card\">\n" +
                "                    <span class=\"method get\">GET</span>\n" +
                "                    <a href=\"/api/v1/etudiants/search?niveau=Master 1\" target=\"_blank\">Recherche par niveau</a>\n" +
                "                    <p class=\"description\">Exemple de recherche par niveau d'études</p>\n" +
                "                </div>\n" +
                "                <div class=\"link-card\">\n" +
                "                    <span class=\"method get\">GET</span>\n" +
                "                    <a href=\"/api/v1/etudiants/1\" target=\"_blank\">Étudiant par ID</a>\n" +
                "                    <p class=\"description\">Récupérer un étudiant spécifique (ID: 1)</p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"section test-section\">\n" +
                "            <h2>🧪 Test Rapide - Créer un Étudiant</h2>\n" +
                "            <div class=\"test-form\">\n" +
                "                <div class=\"form-group\">\n" +
                "                    <label>Nom:</label>\n" +
                "                    <input type=\"text\" id=\"nom\" value=\"Nom\" required>\n" +
                "                </div>\n" +
                "                <div class=\"form-group\">\n" +
                "                    <label>Prénom:</label>\n" +
                "                    <input type=\"text\" id=\"prenom\" value=\"Prenom\" required>\n" +
                "                </div>\n" +
                "                <div class=\"form-group\">\n" +
                "                    <label>Email:</label>\n" +
                "                    <input type=\"email\" id=\"email\" value=\"nom.prenom@gmail.com\" required>\n" +
                "                </div>\n" +
                "                <div class=\"form-group\">\n" +
                "                    <label>Date de naissance:</label>\n" +
                "                    <input type=\"date\" id=\"dateNaissance\" value=\"year-month-day\" required>\n" +
                "                </div>\n" +
                "                <div class=\"form-group\">\n" +
                "                    <label>Niveau:</label>\n" +
                "                    <select id=\"niveau\" required>\n" +
                "                        <option value=\"Licence 1\">Licence 1</option>\n" +
                "                        <option value=\"Licence 2\">Licence 2</option>\n" +
                "                        <option value=\"Licence 3\">Licence 3</option>\n" +
                "                        <option value=\"Master 1\" selected>Master 1</option>\n" +
                "                        <option value=\"Master 2\">Master 2</option>\n" +
                "                        <option value=\"Doctorat\">Doctorat</option>\n" +
                "                    </select>\n" +
                "                </div>\n" +
                "                <div class=\"form-group\">\n" +
                "                    <label>Téléphone:</label>\n" +
                "                    <input type=\"tel\" id=\"telephone\" value=\"0123456789\">\n" +
                "                </div>\n" +
                "                <button class=\"btn\" onclick=\"createStudent()\">Créer l'Étudiant</button>\n" +
                "                <div id=\"result\"></div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"section\">\n" +
                "            <h2>🔧 Endpoints API Complets</h2>\n" +
                "            <div class=\"links-grid\">\n" +
                "                <div class=\"link-card\">\n" +
                "                    <span class=\"method post\">POST</span>\n" +
                "                    <strong>/api/v1/etudiants</strong>\n" +
                "                    <p class=\"description\">Créer un nouvel étudiant (avec notification)</p>\n" +
                "                </div>\n" +
                "                <div class=\"link-card\">\n" +
                "                    <span class=\"method put\">PUT</span>\n" +
                "                    <strong>/api/v1/etudiants/{id}</strong>\n" +
                "                    <p class=\"description\">Mettre à jour un étudiant existant</p>\n" +
                "                </div>\n" +
                "                <div class=\"link-card\">\n" +
                "                    <span class=\"method delete\">DELETE</span>\n" +
                "                    <strong>/api/v1/etudiants/{id}</strong>\n" +
                "                    <p class=\"description\">Supprimer un étudiant (avec notification)</p>\n" +
                "                </div>\n" +
                "                <div class=\"link-card\">\n" +
                "                    <span class=\"method get\">GET</span>\n" +
                "                    <strong>/api/v1/etudiants/search</strong>\n" +
                "                    <p class=\"description\">Rechercher par email ou niveau</p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"section\">\n" +
                "            <h2>📱 Support Multilingue</h2>\n" +
                "            <div class=\"links-grid\">\n" +
                "                <div class=\"link-card\">\n" +
                "                    <a href=\"/api/v1/etudiants?lang=fr\" target=\"_blank\">🇫🇷 Version Française</a>\n" +
                "                    <p class=\"description\">Messages et erreurs en français</p>\n" +
                "                </div>\n" +
                "                <div class=\"link-card\">\n" +
                "                    <a href=\"/api/v1/etudiants?lang=en\" target=\"_blank\">🇬🇧 English Version</a>\n" +
                "                    <p class=\"description\">Messages and errors in English</p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <script>\n" +
                "        async function createStudent() {\n" +
                "            const student = {\n" +
                "                nom: document.getElementById('nom').value,\n" +
                "                prenom: document.getElementById('prenom').value,\n" +
                "                email: document.getElementById('email').value,\n" +
                "                dateNaissance: document.getElementById('dateNaissance').value,\n" +
                "                niveau: document.getElementById('niveau').value,\n" +
                "                telephone: document.getElementById('telephone').value\n" +
                "            };\n" +
                "\n" +
                "            const resultDiv = document.getElementById('result');\n" +
                "            resultDiv.style.display = 'block';\n" +
                "            resultDiv.textContent = 'Création en cours...';\n" +
                "\n" +
                "            try {\n" +
                "                const response = await fetch('/api/v1/etudiants', {\n" +
                "                    method: 'POST',\n" +
                "                    headers: {\n" +
                "                        'Content-Type': 'application/json',\n" +
                "                    },\n" +
                "                    body: JSON.stringify(student)\n" +
                "                });\n" +
                "\n" +
                "                if (response.ok) {\n" +
                "                    const createdStudent = await response.json();\n" +
                "                    resultDiv.textContent = `✅ Étudiant créé avec succès !\\n\\n${JSON.stringify(createdStudent, null, 2)}`;\n" +
                "                    resultDiv.style.background = '#f0fff4';\n" +
                "                    resultDiv.style.borderColor = '#9ae6b4';\n" +
                "                    \n" +
                "                    // Générer un nouvel email pour le prochain test\n" +
                "                    const timestamp = new Date().getTime();\n" +
                "                    document.getElementById('email').value = `test${timestamp}@email.com`;\n" +
                "                } else {\n" +
                "                    const error = await response.json();\n" +
                "                    resultDiv.textContent = `❌ Erreur: ${error.message}\\n\\n${JSON.stringify(error, null, 2)}`;\n" +
                "                    resultDiv.style.background = '#fff5f5';\n" +
                "                    resultDiv.style.borderColor = '#fed7d7';\n" +
                "                }\n" +
                "            } catch (error) {\n" +
                "                resultDiv.textContent = `❌ Erreur de connexion: ${error.message}`;\n" +
                "                resultDiv.style.background = '#fff5f5';\n" +
                "                resultDiv.style.borderColor = '#fed7d7';\n" +
                "            }\n" +
                "        }\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
    }
}
