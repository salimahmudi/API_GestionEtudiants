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
        html.append("            <div class=\"stat-item\">\n");
        html.append("                <span class=\"stat-number\">").append(freeMemory).append("MB</span>\n");
        html.append("                <span class=\"stat-label\">\uD83D\uDCBE ").append(languageUtil.getMessage("stats.available.memory", lang)).append("</span>\n");
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

        // Main content grid
        html.append("        <div class=\"grid\">\n");
        html.append(getAPIV2Card(lang));
        html.append(getAPIV1Card(lang));
        html.append(getDocumentationCard(lang));
        html.append("        </div>\n");

        // Footer
        html.append("        <div class=\"footer\">\n");
        html.append("            <div class=\"quick-links\">\n");
        html.append("                <a href=\"/swagger-ui.html\">\n");
        html.append("                    <i class=\"fas fa-book-open\"></i> ").append(languageUtil.getMessage("footer.documentation", lang)).append("\n");
        html.append("                </a>\n");
        html.append("                <a href=\"/api/v2/etudiants\">\n");
        html.append("                    <i class=\"fas fa-rocket\"></i> ").append(languageUtil.getMessage("footer.api.v2", lang)).append("\n");
        html.append("                </a>\n");
        html.append("                <a href=\"/database/info\">\n");
        html.append("                    <i class=\"fas fa-database\"></i> ").append(languageUtil.getMessage("footer.database", lang)).append("\n");
        html.append("                </a>\n");
        html.append("                <a href=\"/api/info?lang=").append(lang).append("\">\n");
        html.append("                    <i class=\"fas fa-info-circle\"></i> ").append(languageUtil.getMessage("footer.api.info", lang)).append("\n");
        html.append("                </a>\n");
        html.append("                <a href=\"/api/status\">\n");
        html.append("                    <i class=\"fas fa-heartbeat\"></i> ").append(languageUtil.getMessage("footer.status", lang)).append("\n");
        html.append("                </a>\n");
        html.append("                <a href=\"/api/v2/etudiants/statistics\">\n");
        html.append("                    <i class=\"fas fa-chart-bar\"></i> ").append(languageUtil.getMessage("card.api.v2.statistics", lang)).append("\n");
        html.append("                </a>\n");
        html.append("            </div>\n");
        html.append("            <p><strong>").append(languageUtil.getMessage("footer.copyright", lang)).append("</strong></p>\n");
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

    private String getDocumentationCard(String lang) {
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

    private String getJavaScript() {
        return "<script>\n" +
               "    // Simple performance monitoring\n" +
               "    console.log('🚀 Student Management API Dashboard loaded successfully!');\n" +
               "    console.log('📊 Performance: ' + (performance.now()).toFixed(2) + 'ms');\n" +
               "</script>\n";
    }
}
