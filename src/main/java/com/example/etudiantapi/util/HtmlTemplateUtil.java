package com.example.etudiantapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class HtmlTemplateUtil {

    @Autowired
    private LanguageUtil languageUtil;

    public String generateHomePage(String lang, String welcomeMessage, long totalStudents) {
        // Get system information
        String javaVersion = System.getProperty("java.version");
        String osName = System.getProperty("os.name");
        long freeMemory = Runtime.getRuntime().freeMemory() / 1024 / 1024; // MB
        long totalMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024; // MB
        String serverTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String fullDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"").append(lang).append("\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>\uD83C\uDF93 ").append(languageUtil.getMessage("home.title", lang)).append("</title>\n");
        html.append("    <link href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css\" rel=\"stylesheet\">\n");
        html.append("    <style>\n");
        html.append(getCSS());
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <div class=\"header\">\n");
        html.append("            <h1>\uD83C\uDF93 ").append(welcomeMessage).append("</h1>\n");
        html.append("            <p class=\"subtitle\">").append(languageUtil.getMessage("home.subtitle", lang)).append("</p>\n");
        html.append("            <div class=\"version-badge\">✨ ").append(languageUtil.getMessage("home.version.badge", lang)).append("</div>\n");
        html.append("        </div>\n");

        // Stats bar
        html.append("        <div class=\"stats-bar\">\n");
        html.append("            <div class=\"stat-item\">\n");
        html.append("                <span class=\"stat-number\">").append(totalStudents).append("</span>\n");
        html.append("                <span class=\"stat-label\">\uD83D\uDC65 ").append(languageUtil.getMessage("stats.total.students", lang)).append("</span>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"stat-item\">\n");
        html.append("                <span class=\"stat-number\">2</span>\n");
        html.append("                <span class=\"stat-label\">\uD83D\uDE80 ").append(languageUtil.getMessage("stats.api.versions", lang)).append("</span>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"stat-item\">\n");
        html.append("                <span class=\"stat-number\">2</span>\n");
        html.append("                <span class=\"stat-label\">\uD83C\uDF0D ").append(languageUtil.getMessage("stats.languages", lang)).append("</span>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"stat-item\">\n");
        html.append("                <span class=\"stat-number\">").append(serverTime).append("</span>\n");
        html.append("                <span class=\"stat-label\">⏰ ").append(languageUtil.getMessage("stats.server.time", lang)).append("</span>\n");
        html.append("            </div>\n");
        html.append("        </div>\n");

        // Language selector
        html.append("        <div class=\"language-selector\">\n");
        html.append("            <a href=\"/?lang=fr\" class=\"lang-btn\">\n");
        html.append("                <span>\uD83C\uDDEB\uD83C\uDDF7</span> ").append(languageUtil.getMessage("language.french", lang)).append("\n");
        html.append("            </a>\n");
        html.append("            <a href=\"/?lang=en\" class=\"lang-btn\">\n");
        html.append("                <span>\uD83C\uDDEC\uD83C\uDDE7</span> ").append(languageUtil.getMessage("language.english", lang)).append("\n");
        html.append("            </a>\n");
        html.append("        </div>\n");

        // Quick access buttons
        html.append("        <div class=\"quick-access-buttons\">\n");
        html.append("            <a href=\"/swagger-ui.html\" target=\"_blank\" class=\"quick-btn swagger-btn\">\n");
        html.append("                <i class=\"fas fa-book-open\"></i>\n");
        html.append("                <span>").append(languageUtil.getMessage("card.doc.swagger", lang)).append("</span>\n");
        html.append("            </a>\n");
        html.append("            <a href=\"/h2-console\" target=\"_blank\" class=\"quick-btn h2-btn\">\n");
        html.append("                <i class=\"fas fa-database\"></i>\n");
        html.append("                <span>").append(languageUtil.getMessage("card.h2.console.title", lang)).append("</span>\n");
        html.append("            </a>\n");
        html.append("            <a href=\"/api/v2/etudiants/health\" target=\"_blank\" class=\"quick-btn health-btn\">\n");
        html.append("                <i class=\"fas fa-heartbeat\"></i>\n");
        html.append("                <span>").append(languageUtil.getMessage("card.health.check.title", lang)).append("</span>\n");
        html.append("            </a>\n");
        html.append("        </div>\n");

        // Main content grid - only 3 cards
        html.append("        <div class=\"grid\">\n");
        html.append(getAPIV2Card(lang));
        html.append(getAPIV1Card(lang));
        html.append(getAllEndpointsCard(lang));
        html.append("        </div>\n");

        // Full width testing card
        html.append("        <div class=\"full-width-card\">\n");
        html.append(getTestingCard(lang));
        html.append("        </div>\n");

        html.append("    </div>\n");

        html.append(getJavaScript());
        html.append("</body>\n");
        html.append("</html>\n");

        return html.toString();
    }

    private String getCSS() {
        return "* {\n" +
               "    margin: 0;\n" +
               "    padding: 0;\n" +
               "    box-sizing: border-box;\n" +
               "}\n\n" +
               "body {\n" +
               "    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;\n" +
               "    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
               "    color: #333;\n" +
               "    min-height: 100vh;\n" +
               "    padding: 20px;\n" +
               "}\n\n" +
               ".container {\n" +
               "    max-width: 1200px;\n" +
               "    margin: 0 auto;\n" +
               "    background: rgba(255, 255, 255, 0.95);\n" +
               "    border-radius: 16px;\n" +
               "    padding: 40px;\n" +
               "    box-shadow: 0 8px 32px rgba(0,0,0,0.1);\n" +
               "    backdrop-filter: blur(10px);\n" +
               "}\n\n" +
               ".header {\n" +
               "    text-align: center;\n" +
               "    margin-bottom: 40px;\n" +
               "}\n\n" +
               ".header h1 {\n" +
               "    color: #1a202c;\n" +
               "    font-size: 3em;\n" +
               "    margin-bottom: 10px;\n" +
               "    font-weight: 700;\n" +
               "    background: linear-gradient(135deg, #667eea, #764ba2);\n" +
               "    -webkit-background-clip: text;\n" +
               "    -webkit-text-fill-color: transparent;\n" +
               "    background-clip: text;\n" +
               "}\n\n" +
               ".header .subtitle {\n" +
               "    color: #718096;\n" +
               "    font-size: 1.2em;\n" +
               "    margin-bottom: 20px;\n" +
               "    font-weight: 400;\n" +
               "}\n\n" +
               ".header .version-badge {\n" +
               "    background: #38a169;\n" +
               "    color: white;\n" +
               "    padding: 8px 16px;\n" +
               "    border-radius: 20px;\n" +
               "    font-size: 0.9em;\n" +
               "    font-weight: 600;\n" +
               "    display: inline-block;\n" +
               "    margin-top: 10px;\n" +
               "}\n\n" +
               ".stats-bar {\n" +
               "    background: linear-gradient(135deg, #667eea, #764ba2);\n" +
               "    color: white;\n" +
               "    padding: 20px;\n" +
               "    border-radius: 12px;\n" +
               "    margin-bottom: 40px;\n" +
               "    display: grid;\n" +
               "    grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));\n" +
               "    gap: 20px;\n" +
               "}\n\n" +
               ".stat-item {\n" +
               "    text-align: center;\n" +
               "    padding: 15px;\n" +
               "    background: rgba(255, 255, 255, 0.1);\n" +
               "    border-radius: 8px;\n" +
               "    transition: background-color 0.2s ease;\n" +
               "}\n\n" +
               ".stat-item:hover {\n" +
               "    background: rgba(255, 255, 255, 0.2);\n" +
               "}\n\n" +
               ".stat-number {\n" +
               "    font-size: 2em;\n" +
               "    font-weight: 700;\n" +
               "    display: block;\n" +
               "    margin-bottom: 5px;\n" +
               "}\n\n" +
               ".stat-label {\n" +
               "    font-size: 0.9em;\n" +
               "    opacity: 0.9;\n" +
               "    font-weight: 500;\n" +
               "}\n\n" +
               ".language-selector {\n" +
               "    text-align: center;\n" +
               "    margin-bottom: 40px;\n" +
               "}\n\n" +
               ".language-selector .lang-btn {\n" +
               "    margin: 0 10px;\n" +
               "    padding: 12px 24px;\n" +
               "    background: linear-gradient(135deg, #667eea, #764ba2);\n" +
               "    color: white;\n" +
               "    text-decoration: none;\n" +
               "    border-radius: 24px;\n" +
               "    font-weight: 600;\n" +
               "    transition: all 0.2s ease;\n" +
               "    display: inline-flex;\n" +
               "    align-items: center;\n" +
               "    gap: 8px;\n" +
               "    font-size: 1em;\n" +
               "}\n\n" +
               ".language-selector .lang-btn:hover {\n" +
               "    transform: translateY(-1px);\n" +
               "    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);\n" +
               "}\n\n" +
               ".quick-access-buttons {\n" +
               "    display: flex;\n" +
               "    justify-content: center;\n" +
               "    gap: 20px;\n" +
               "    margin-bottom: 40px;\n" +
               "    flex-wrap: wrap;\n" +
               "}\n\n" +
               ".quick-btn {\n" +
               "    display: flex;\n" +
               "    flex-direction: column;\n" +
               "    align-items: center;\n" +
               "    padding: 20px;\n" +
               "    background: white;\n" +
               "    border-radius: 12px;\n" +
               "    text-decoration: none;\n" +
               "    color: #4a5568;\n" +
               "    transition: all 0.2s ease;\n" +
               "    box-shadow: 0 4px 12px rgba(0,0,0,0.1);\n" +
               "    min-width: 120px;\n" +
               "    border: 2px solid transparent;\n" +
               "}\n\n" +
               ".quick-btn:hover {\n" +
               "    transform: translateY(-3px);\n" +
               "    box-shadow: 0 8px 20px rgba(0,0,0,0.15);\n" +
               "}\n\n" +
               ".quick-btn i {\n" +
               "    font-size: 2em;\n" +
               "    margin-bottom: 10px;\n" +
               "}\n\n" +
               ".quick-btn span {\n" +
               "    font-weight: 600;\n" +
               "    font-size: 0.9em;\n" +
               "    text-align: center;\n" +
               "}\n\n" +
               ".swagger-btn {\n" +
               "    border-color: #3182ce;\n" +
               "}\n\n" +
               ".swagger-btn:hover {\n" +
               "    background: #3182ce;\n" +
               "    color: white;\n" +
               "}\n\n" +
               ".h2-btn {\n" +
               "    border-color: #38a169;\n" +
               "}\n\n" +
               ".h2-btn:hover {\n" +
               "    background: #38a169;\n" +
               "    color: white;\n" +
               "}\n\n" +
               ".health-btn {\n" +
               "    border-color: #e53e3e;\n" +
               "}\n\n" +
               ".health-btn:hover {\n" +
               "    background: #e53e3e;\n" +
               "    color: white;\n" +
               "}\n\n" +
               ".grid {\n" +
               "    display: grid;\n" +
               "    grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));\n" +
               "    gap: 24px;\n" +
               "    margin-bottom: 40px;\n" +
               "}\n\n" +
               ".card {\n" +
               "    padding: 24px;\n" +
               "    border-radius: 12px;\n" +
               "    box-shadow: 0 4px 16px rgba(0,0,0,0.1);\n" +
               "    transition: all 0.2s ease;\n" +
               "    border: 1px solid #e2e8f0;\n" +
               "    background: white;\n" +
               "}\n\n" +
               ".card:hover {\n" +
               "    transform: translateY(-2px);\n" +
               "    box-shadow: 0 8px 24px rgba(0,0,0,0.15);\n" +
               "}\n\n" +
               ".card.current {\n" +
               "    background: linear-gradient(135deg, #f0fff4, #c6f6d5);\n" +
               "    border-left: 4px solid #38a169;\n" +
               "}\n\n" +
               ".card.deprecated {\n" +
               "    background: linear-gradient(135deg, #fef5e7, #fed7aa);\n" +
               "    border-left: 4px solid #ed8936;\n" +
               "}\n\n" +
               ".card.documentation {\n" +
               "    background: linear-gradient(135deg, #ebf8ff, #bee3f8);\n" +
               "    border-left: 4px solid #3182ce;\n" +
               "}\n\n" +
               ".card.quick-access {\n" +
               "    background: linear-gradient(135deg, #f0f9ff, #c7d2fe);\n" +
               "    border-left: 4px solid #3b82f6;\n" +
               "}\n\n" +
               ".card.endpoints {\n" +
               "    background: linear-gradient(135deg, #fef3c7, #fde68a);\n" +
               "    border-left: 4px solid #f59e0b;\n" +
               "}\n\n" +
               ".card.testing {\n" +
               "    background: linear-gradient(135deg, #fce7f3, #f3e8ff);\n" +
               "    border-left: 4px solid #d946ef;\n" +
               "}\n\n" +
               ".card h2 {\n" +
               "    color: #1a202c;\n" +
               "    margin-bottom: 16px;\n" +
               "    font-size: 1.5em;\n" +
               "    display: flex;\n" +
               "    align-items: center;\n" +
               "    gap: 10px;\n" +
               "    font-weight: 600;\n" +
               "}\n\n" +
               ".card .emoji {\n" +
               "    font-size: 1.8em;\n" +
               "}\n\n" +
               ".card p {\n" +
               "    margin-bottom: 12px;\n" +
               "    line-height: 1.6;\n" +
               "    color: #4a5568;\n" +
               "}\n\n" +
               ".card .endpoint {\n" +
               "    background: rgba(255,255,255,0.7);\n" +
               "    padding: 12px 16px;\n" +
               "    border-radius: 8px;\n" +
               "    font-family: 'SF Mono', Monaco, monospace;\n" +
               "    font-size: 0.9em;\n" +
               "    margin: 8px 0;\n" +
               "    border: 1px solid rgba(0,0,0,0.1);\n" +
               "    transition: background-color 0.2s ease;\n" +
               "}\n\n" +
               ".card .endpoint:hover {\n" +
               "    background: rgba(255,255,255,0.9);\n" +
               "}\n\n" +
               ".link {\n" +
               "    color: #3182ce;\n" +
               "    text-decoration: none;\n" +
               "    font-weight: 600;\n" +
               "    transition: color 0.2s ease;\n" +
               "}\n\n" +
               ".link:hover {\n" +
               "    color: #2c5282;\n" +
               "}\n\n" +
               ".features-list {\n" +
               "    list-style: none;\n" +
               "    padding: 0;\n" +
               "}\n\n" +
               ".features-list li {\n" +
               "    padding: 6px 0;\n" +
               "    display: flex;\n" +
               "    align-items: center;\n" +
               "    gap: 10px;\n" +
               "}\n\n" +
               ".features-list li:before {\n" +
               "    content: '✓';\n" +
               "    color: #38a169;\n" +
               "    font-weight: 600;\n" +
               "    font-size: 1.1em;\n" +
               "}\n\n" +
               ".status-badge {\n" +
               "    display: inline-block;\n" +
               "    padding: 4px 12px;\n" +
               "    border-radius: 16px;\n" +
               "    font-size: 0.8em;\n" +
               "    font-weight: 600;\n" +
               "    text-transform: uppercase;\n" +
               "    letter-spacing: 0.5px;\n" +
               "}\n\n" +
               ".status-badge.current {\n" +
               "    background: #38a169;\n" +
               "    color: white;\n" +
               "}\n\n" +
               ".status-badge.deprecated {\n" +
               "    background: #ed8936;\n" +
               "    color: white;\n" +
               "}\n\n" +
               ".footer {\n" +
               "    text-align: center;\n" +
               "    margin-top: 40px;\n" +
               "    padding-top: 24px;\n" +
               "    border-top: 1px solid #e2e8f0;\n" +
               "    color: #718096;\n" +
               "}\n\n" +
               ".quick-links {\n" +
               "    display: flex;\n" +
               "    justify-content: center;\n" +
               "    gap: 16px;\n" +
               "    margin: 24px 0;\n" +
               "    flex-wrap: wrap;\n" +
               "}\n\n" +
               ".quick-links a {\n" +
               "    padding: 12px 20px;\n" +
               "    background: #f7fafc;\n" +
               "    border: 1px solid #e2e8f0;\n" +
               "    border-radius: 8px;\n" +
               "    text-decoration: none;\n" +
               "    color: #4a5568;\n" +
               "    transition: all 0.2s ease;\n" +
               "    font-weight: 500;\n" +
               "    display: flex;\n" +
               "    align-items: center;\n" +
               "    gap: 8px;\n" +
               "}\n\n" +
               ".quick-links a:hover {\n" +
               "    background: #667eea;\n" +
               "    color: white;\n" +
               "    border-color: #667eea;\n" +
               "    transform: translateY(-1px);\n" +
               "}\n\n" +
               ".endpoint-list {\n" +
               "    margin-top: 15px;\n" +
               "}\n\n" +
               ".endpoint-item {\n" +
               "    margin-bottom: 10px;\n" +
               "}\n\n" +
               ".endpoint-label {\n" +
               "    display: block;\n" +
               "    font-weight: 600;\n" +
               "    color: #4a5568;\n" +
               "    margin-bottom: 5px;\n" +
               "    font-size: 0.9em;\n" +
               "}\n\n" +
               ".test-form {\n" +
               "    background: rgba(255, 255, 255, 0.9);\n" +
               "    padding: 20px;\n" +
               "    border-radius: 8px;\n" +
               "    margin-top: 15px;\n" +
               "    border: 1px solid rgba(0,0,0,0.1);\n" +
               "}\n\n" +
               ".form-group {\n" +
               "    margin-bottom: 15px;\n" +
               "}\n\n" +
               ".form-group label {\n" +
               "    display: block;\n" +
               "    margin-bottom: 5px;\n" +
               "    font-weight: 600;\n" +
               "    color: #4a5568;\n" +
               "}\n\n" +
               ".form-group input, .form-group select {\n" +
               "    width: 100%;\n" +
               "    padding: 10px;\n" +
               "    border: 1px solid #e2e8f0;\n" +
               "    border-radius: 5px;\n" +
               "    font-size: 14px;\n" +
               "    box-sizing: border-box;\n" +
               "    transition: border-color 0.2s ease;\n" +
               "}\n\n" +
               ".form-group input:focus, .form-group select:focus {\n" +
               "    outline: none;\n" +
               "    border-color: #667eea;\n" +
               "    box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);\n" +
               "}\n\n" +
               ".btn {\n" +
               "    background: linear-gradient(135deg, #667eea, #764ba2);\n" +
               "    color: white;\n" +
               "    padding: 12px 24px;\n" +
               "    border: none;\n" +
               "    border-radius: 6px;\n" +
               "    cursor: pointer;\n" +
               "    font-weight: 600;\n" +
               "    font-size: 14px;\n" +
               "    transition: all 0.2s ease;\n" +
               "    display: inline-flex;\n" +
               "    align-items: center;\n" +
               "    gap: 8px;\n" +
               "}\n\n" +
               ".btn:hover {\n" +
               "    transform: translateY(-1px);\n" +
               "    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);\n" +
               "}\n\n" +
               ".btn:active {\n" +
               "    transform: translateY(0);\n" +
               "}\n\n" +
               ".result {\n" +
               "    margin-top: 15px;\n" +
               "    padding: 15px;\n" +
               "    background: #f7fafc;\n" +
               "    border-radius: 6px;\n" +
               "    border: 1px solid #e2e8f0;\n" +
               "    white-space: pre-wrap;\n" +
               "    font-family: 'SF Mono', Monaco, monospace;\n" +
               "    font-size: 13px;\n" +
               "    display: none;\n" +
               "    max-height: 300px;\n" +
               "    overflow-y: auto;\n" +
               "}\n\n" +
               ".result.success {\n" +
               "    background: #f0fff4;\n" +
               "    border-color: #9ae6b4;\n" +
               "    color: #22543d;\n" +
               "}\n\n" +
               ".result.error {\n" +
               "    background: #fff5f5;\n" +
               "    border-color: #fed7d7;\n" +
               "    color: #742a2a;\n" +
               "}\n\n" +
               ".full-width-card {\n" +
               "    margin-top: 40px;\n" +
               "}\n\n" +
               ".full-width-card .card {\n" +
               "    width: 100%;\n" +
               "    max-width: none;\n" +
               "}\n\n" +
               "@media (max-width: 768px) {\n" +
               "    .container {\n" +
               "        padding: 20px;\n" +
               "    }\n\n" +
               "    .header h1 {\n" +
               "        font-size: 2.5em;\n" +
               "    }\n\n" +
               "    .grid {\n" +
               "        grid-template-columns: 1fr;\n" +
               "    }\n\n" +
               "    .stats-bar {\n" +
               "        grid-template-columns: repeat(2, 1fr);\n" +
               "    }\n\n" +
               "    .quick-links {\n" +
               "        flex-direction: column;\n" +
               "        align-items: center;\n" +
               "    }\n\n" +
               "    .quick-links a {\n" +
               "        width: 100%;\n" +
               "        max-width: 280px;\n" +
               "        justify-content: center;\n" +
               "    }\n" +
               "}\n";
    }

    private String getAPIV2Card(String lang) {
        return "<div class=\"card current\">\n" +
               "    <h2>\n" +
               "        <span class=\"emoji\">✅</span>\n" +
               "        " + languageUtil.getMessage("card.api.v2.title", lang) + " <span class=\"status-badge current\">" + languageUtil.getMessage("card.api.v2.status", lang) + "</span>\n" +
               "    </h2>\n" +
               "    <p><strong>🎯 " + languageUtil.getMessage("card.api.v2.primary.endpoint", lang) + ":</strong></p>\n" +
               "    <div class=\"endpoint\">\n" +
               "        <a href=\"/api/v2/etudiants\" class=\"link\"><i class=\"fas fa-rocket\"></i> /api/v2/etudiants</a>\n" +
               "    </div>\n" +
               "    <p><strong>🚀 " + languageUtil.getMessage("card.api.v2.enhanced.features", lang) + ":</strong></p>\n" +
               "    <ul class=\"features-list\">\n" +
               "        <li>" + languageUtil.getMessage("card.api.v2.feature.multilingual", lang) + "</li>\n" +
               "        <li>" + languageUtil.getMessage("card.api.v2.feature.search", lang) + "</li>\n" +
               "        <li>" + languageUtil.getMessage("card.api.v2.feature.json", lang) + "</li>\n" +
               "        <li>" + languageUtil.getMessage("card.api.v2.feature.analytics", lang) + "</li>\n" +
               "        <li>" + languageUtil.getMessage("card.api.v2.feature.monitoring", lang) + "</li>\n" +
               "        <li>" + languageUtil.getMessage("card.api.v2.feature.restful", lang) + "</li>\n" +
               "    </ul>\n" +
               "    <p><strong>🔧 " + languageUtil.getMessage("card.api.v2.quick.actions", lang) + ":</strong></p>\n" +
               "    <div class=\"endpoint\">\n" +
               "        <a href=\"/api/v2/etudiants/health\" class=\"link\"><i class=\"fas fa-heartbeat\"></i> " + languageUtil.getMessage("card.api.v2.health.check", lang) + "</a> |\n" +
               "        <a href=\"/api/v2/etudiants/statistics\" class=\"link\"><i class=\"fas fa-chart-bar\"></i> " + languageUtil.getMessage("card.api.v2.statistics", lang) + "</a>\n" +
               "    </div>\n" +
               "</div>\n";
    }

    private String getAPIV1Card(String lang) {
        return "<div class=\"card deprecated\">\n" +
               "    <h2>\n" +
               "        <span class=\"emoji\">⚠️</span>\n" +
               "        " + languageUtil.getMessage("card.api.v1.title", lang) + " <span class=\"status-badge deprecated\">" + languageUtil.getMessage("card.api.v1.status", lang) + "</span>\n" +
               "    </h2>\n" +
               "    <p><strong>📍 " + languageUtil.getMessage("card.api.v1.legacy.endpoint", lang) + ":</strong></p>\n" +
               "    <div class=\"endpoint\">\n" +
               "        <a href=\"/api/v1/etudiants\" class=\"link\"><i class=\"fas fa-exclamation-triangle\"></i> /api/v1/etudiants</a>\n" +
               "    </div>\n" +
               "    <p><strong>⚠️ " + languageUtil.getMessage("card.api.v1.deprecation.notice", lang) + "</strong></p>\n" +
               "    <p><strong>🔄 " + languageUtil.getMessage("card.api.v1.migration.guide", lang) + "</strong></p>\n" +
               "    <p><strong>🛠️ " + languageUtil.getMessage("card.api.v1.support.level", lang) + "</strong></p>\n" +
               "</div>\n";
    }

    private String getSwaggerCard(String lang) {
        return "<div class=\"card documentation\">\n" +
               "    <h2>\n" +
               "        <span class=\"emoji\">📚</span>\n" +
               "        " + languageUtil.getMessage("card.doc.title", lang) + "\n" +
               "    </h2>\n" +
               "    <p><strong>🎮 " + languageUtil.getMessage("card.doc.interactive", lang) + ":</strong></p>\n" +
               "    <div class=\"endpoint\">\n" +
               "        <a href=\"/swagger-ui.html\" class=\"link\"><i class=\"fas fa-book-open\"></i> " + languageUtil.getMessage("card.doc.swagger", lang) + "</a>\n" +
               "    </div>\n" +
               "    <p><strong>ℹ️ " + languageUtil.getMessage("card.doc.api.info", lang) + ":</strong></p>\n" +
               "    <div class=\"endpoint\">\n" +
               "        <a href=\"/api/info?lang=" + lang + "\" class=\"link\"><i class=\"fas fa-info-circle\"></i> " + languageUtil.getMessage("card.doc.detailed.info", lang) + "</a>\n" +
               "    </div>\n" +
               "</div>\n";
    }

    private String getH2ConsoleCard(String lang) {
        return "<div class=\"card documentation\">\n" +
               "    <h2>\n" +
               "        <span class=\"emoji\">🗄️</span>\n" +
               "        " + languageUtil.getMessage("card.h2.console.title", lang) + "\n" +
               "    </h2>\n" +
               "    <p><strong>🔑 " + languageUtil.getMessage("card.h2.console.access", lang) + ":</strong></p>\n" +
               "    <div class=\"endpoint\">\n" +
               "        <a href=\"/h2-console\" target=\"_blank\" class=\"link\"><i class=\"fas fa-database\"></i> " + languageUtil.getMessage("card.h2.console.open", lang) + "</a>\n" +
               "    </div>\n" +
               "    <p><strong>📖 " + languageUtil.getMessage("card.h2.console.guide", lang) + ":</strong></p>\n" +
               "    <div class=\"endpoint\">\n" +
               "        <a href=\"/api/info?lang=" + lang + "\" class=\"link\"><i class=\"fas fa-info-circle\"></i> " + languageUtil.getMessage("card.h2.console.details", lang) + "</a>\n" +
               "    </div>\n" +
               "</div>\n";
    }

    private String getHealthCheckCard(String lang) {
        return "<div class=\"card quick-access\">\n" +
               "    <h2>\n" +
               "        <span class=\"emoji\">❤️‍🩹</span>\n" +
               "        " + languageUtil.getMessage("card.health.check.title", lang) + "\n" +
               "    </h2>\n" +
               "    <p>" + languageUtil.getMessage("card.health.check.description", lang) + "</p>\n" +
               "    <div class=\"endpoint\">\n" +
               "        <a href=\"/api/v2/etudiants/health\" class=\"link\"><i class=\"fas fa-heartbeat\"></i> " + languageUtil.getMessage("card.api.v2.health.check", lang) + "</a>\n" +
               "        <a href=\"/api/v2/etudiants/statistics\" class=\"link\"><i class=\"fas fa-chart-bar\"></i> " + languageUtil.getMessage("card.api.v2.statistics", lang) + "</a>\n" +
               "    </div>\n" +
               "    <p><strong>" + languageUtil.getMessage("card.quick.access.tools", lang) + ":</strong></p>\n" +
               "    <ul class=\"features-list\">\n" +
               "        <li>" + languageUtil.getMessage("card.quick.access.tool1", lang) + "</li>\n" +
               "        <li>" + languageUtil.getMessage("card.quick.access.tool2", lang) + "</li>\n" +
               "        <li>" + languageUtil.getMessage("card.quick.access.tool3", lang) + "</li>\n" +
               "    </ul>\n" +
               "</div>\n";
    }

    private String getAllEndpointsCard(String lang) {
        return "<div class=\"card endpoints\">\n" +
               "    <h2>\n" +
               "        <span class=\"emoji\">📡</span>\n" +
               "        " + languageUtil.getMessage("card.endpoints.title", lang) + "\n" +
               "    </h2>\n" +
               "    <p>" + languageUtil.getMessage("card.endpoints.description", lang) + "</p>\n" +
               "    <div class=\"endpoint-list\">\n" +
               "        <div class=\"endpoint-item\">\n" +
               "            <span class=\"endpoint-label\">" + languageUtil.getMessage("card.endpoints.student.creation", lang) + ":</span>\n" +
               "            <div class=\"endpoint\">\n" +
               "                <a href=\"/swagger-ui.html#!/Etudiant%20V2%20Controller/createStudentUsingPOST\" target=\"_blank\" class=\"link\"><i class=\"fas fa-plus\"></i> POST /api/v2/etudiants</a>\n" +
               "            </div>\n" +
               "        </div>\n" +
               "        <div class=\"endpoint-item\">\n" +
               "            <span class=\"endpoint-label\">" + languageUtil.getMessage("card.endpoints.student.list", lang) + ":</span>\n" +
               "            <div class=\"endpoint\">\n" +
               "                <a href=\"/swagger-ui.html#!/Etudiant%20V2%20Controller/getAllStudentsUsingGET\" target=\"_blank\" class=\"link\"><i class=\"fas fa-list\"></i> GET /api/v2/etudiants</a>\n" +
               "            </div>\n" +
               "        </div>\n" +
               "        <div class=\"endpoint-item\">\n" +
               "            <span class=\"endpoint-label\">" + languageUtil.getMessage("card.endpoints.student.details", lang) + ":</span>\n" +
               "            <div class=\"endpoint\">\n" +
               "                <a href=\"/swagger-ui.html#!/Etudiant%20V2%20Controller/getStudentByIdUsingGET\" target=\"_blank\" class=\"link\"><i class=\"fas fa-info\"></i> GET /api/v2/etudiants/{id}</a>\n" +
               "            </div>\n" +
               "        </div>\n" +
               "        <div class=\"endpoint-item\">\n" +
               "            <span class=\"endpoint-label\">" + languageUtil.getMessage("card.endpoints.student.update", lang) + ":</span>\n" +
               "            <div class=\"endpoint\">\n" +
               "                <a href=\"/swagger-ui.html#!/Etudiant%20V2%20Controller/updateStudentUsingPUT\" target=\"_blank\" class=\"link\"><i class=\"fas fa-edit\"></i> PUT /api/v2/etudiants/{id}</a>\n" +
               "            </div>\n" +
               "        </div>\n" +
               "        <div class=\"endpoint-item\">\n" +
               "            <span class=\"endpoint-label\">" + languageUtil.getMessage("card.endpoints.student.delete", lang) + ":</span>\n" +
               "            <div class=\"endpoint\">\n" +
               "                <a href=\"/swagger-ui.html#!/Etudiant%20V2%20Controller/deleteStudentUsingDELETE\" target=\"_blank\" class=\"link\"><i class=\"fas fa-trash\"></i> DELETE /api/v2/etudiants/{id}</a>\n" +
               "            </div>\n" +
               "        </div>\n" +
               "        <div class=\"endpoint-item\">\n" +
               "            <span class=\"endpoint-label\">" + languageUtil.getMessage("card.endpoints.student.search", lang) + ":</span>\n" +
               "            <div class=\"endpoint\">\n" +
               "                <a href=\"/swagger-ui.html#!/Etudiant%20V2%20Controller/searchStudentsUsingGET\" target=\"_blank\" class=\"link\"><i class=\"fas fa-search\"></i> GET /api/v2/etudiants/search</a>\n" +
               "            </div>\n" +
               "        </div>\n" +
               "        <div class=\"endpoint-item\">\n" +
               "            <span class=\"endpoint-label\">" + languageUtil.getMessage("card.endpoints.health.check", lang) + ":</span>\n" +
               "            <div class=\"endpoint\">\n" +
               "                <a href=\"/swagger-ui.html#!/Etudiant%20V2%20Controller/healthCheckUsingGET\" target=\"_blank\" class=\"link\"><i class=\"fas fa-heartbeat\"></i> GET /api/v2/etudiants/health</a>\n" +
               "            </div>\n" +
               "        </div>\n" +
               "        <div class=\"endpoint-item\">\n" +
               "            <span class=\"endpoint-label\">" + languageUtil.getMessage("card.endpoints.statistics", lang) + ":</span>\n" +
               "            <div class=\"endpoint\">\n" +
               "                <a href=\"/swagger-ui.html#!/Etudiant%20V2%20Controller/getStatisticsUsingGET\" target=\"_blank\" class=\"link\"><i class=\"fas fa-chart-bar\"></i> GET /api/v2/etudiants/statistics</a>\n" +
               "            </div>\n" +
               "        </div>\n" +
               "    </div>\n" +
               "    <div class=\"endpoint\" style=\"margin-top: 20px; text-align: center;\">\n" +
               "        <a href=\"/swagger-ui.html\" target=\"_blank\" class=\"link\"><i class=\"fas fa-external-link-alt\"></i> " + languageUtil.getMessage("card.endpoints.view.all.swagger", lang) + "</a>\n" +
               "    </div>\n" +
               "</div>\n";
    }

    private String getTestingCard(String lang) {
        return "<div class=\"card testing\">\n" +
               "    <h2>\n" +
               "        <span class=\"emoji\">🧪</span>\n" +
               "        " + languageUtil.getMessage("card.testing.title", lang) + "\n" +
               "    </h2>\n" +
               "    <p>" + languageUtil.getMessage("card.testing.description", lang) + "</p>\n" +
               "    <div class=\"test-form\">\n" +
               "        <div class=\"form-group\">\n" +
               "            <label>" + languageUtil.getMessage("form.label.nom", lang) + ":</label>\n" +
               "            <input type=\"text\" id=\"nom\" value=\"Dupont\" required>\n" +
               "        </div>\n" +
               "        <div class=\"form-group\">\n" +
               "            <label>" + languageUtil.getMessage("form.label.prenom", lang) + ":</label>\n" +
               "            <input type=\"text\" id=\"prenom\" value=\"Jean\" required>\n" +
               "        </div>\n" +
               "        <div class=\"form-group\">\n" +
               "            <label>" + languageUtil.getMessage("form.label.email", lang) + ":</label>\n" +
               "            <input type=\"email\" id=\"email\" value=\"jean.dupont@example.com\" required>\n" +
               "        </div>\n" +
               "        <div class=\"form-group\">\n" +
               "            <label>" + languageUtil.getMessage("form.label.dateNaissance", lang) + ":</label>\n" +
               "            <input type=\"date\" id=\"dateNaissance\" value=\"2000-01-01\" required>\n" +
               "        </div>\n" +
               "        <div class=\"form-group\">\n" +
               "            <label>" + languageUtil.getMessage("form.label.niveau", lang) + ":</label>\n" +
               "            <select id=\"niveau\" required>\n" +
               "                <option value=\"Licence 1\">Licence 1</option>\n" +
               "                <option value=\"Licence 2\">Licence 2</option>\n" +
               "                <option value=\"Licence 3\">Licence 3</option>\n" +
               "                <option value=\"Master 1\" selected>Master 1</option>\n" +
               "                <option value=\"Master 2\">Master 2</option>\n" +
               "                <option value=\"Doctorat\">Doctorat</option>\n" +
               "            </select>\n" +
               "        </div>\n" +
               "        <div class=\"form-group\">\n" +
               "            <label>" + languageUtil.getMessage("form.label.telephone", lang) + ":</label>\n" +
               "            <input type=\"tel\" id=\"telephone\" value=\"0123456789\">\n" +
               "        </div>\n" +
               "        <button class=\"btn\" onclick=\"createStudent()\">\n" +
               "            <i class=\"fas fa-plus\"></i> " + languageUtil.getMessage("form.button.create", lang) + "\n" +
               "        </button>\n" +
               "        <div id=\"result\" class=\"result\"></div>\n" +
               "    </div>\n" +
               "</div>\n";
    }

    private String getJavaScript() {
        return "<script>\n" +
               "    // Simple performance monitoring\n" +
               "    console.log('🚀 Student Management API Dashboard loaded successfully!');\n" +
               "    console.log('📊 Performance: ' + (performance.now()).toFixed(2) + 'ms');\n" +
               "\n" +
               "    // Student creation function\n" +
               "    async function createStudent() {\n" +
               "        const student = {\n" +
               "            nom: document.getElementById('nom').value,\n" +
               "            prenom: document.getElementById('prenom').value,\n" +
               "            email: document.getElementById('email').value,\n" +
               "            dateNaissance: document.getElementById('dateNaissance').value,\n" +
               "            niveau: document.getElementById('niveau').value,\n" +
               "            telephone: document.getElementById('telephone').value\n" +
               "        };\n" +
               "\n" +
               "        const resultDiv = document.getElementById('result');\n" +
               "        resultDiv.style.display = 'block';\n" +
               "        resultDiv.className = 'result';\n" +
               "        resultDiv.textContent = 'Creating student...';\n" +
               "\n" +
               "        try {\n" +
               "            const response = await fetch('/api/v2/etudiants', {\n" +
               "                method: 'POST',\n" +
               "                headers: {\n" +
               "                    'Content-Type': 'application/json',\n" +
               "                },\n" +
               "                body: JSON.stringify(student)\n" +
               "            });\n" +
               "\n" +
               "            if (response.ok) {\n" +
               "                const result = await response.json();\n" +
               "                resultDiv.className = 'result success';\n" +
               "                resultDiv.textContent = `✅ Student created successfully!\\n\\n${JSON.stringify(result, null, 2)}`;\n" +
               "                \n" +
               "                // Generate a new email for the next test\n" +
               "                const timestamp = new Date().getTime();\n" +
               "                document.getElementById('email').value = `test${timestamp}@example.com`;\n" +
               "            } else {\n" +
               "                const error = await response.json();\n" +
               "                resultDiv.className = 'result error';\n" +
               "                resultDiv.textContent = `❌ Error: ${error.message || 'Unknown error'}\\n\\n${JSON.stringify(error, null, 2)}`;\n" +
               "            }\n" +
               "        } catch (error) {\n" +
               "            resultDiv.className = 'result error';\n" +
               "            resultDiv.textContent = `❌ Connection error: ${error.message}`;\n" +
               "        }\n" +
               "    }\n" +
               "</script>\n";
    }
}
