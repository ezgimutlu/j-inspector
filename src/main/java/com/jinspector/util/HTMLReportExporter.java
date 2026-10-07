package com.jinspector.util;

import com.jinspector.model.Issue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class HTMLReportExporter {

    public String generateAiFixSuggestion(String ruleType) {
        return switch (ruleType) {
            case "EMPTY_CATCH_BLOCK" -> "💡 AI Fix: Exception'ı yutmak yerine loglayın veya rethrow edin -> logger.error(\"Error occurred\", e);";
            case "LONG_METHOD" -> "💡 AI Fix: Metodu Single Responsibility prensibine göre alt yardımcı metotlara bölün.";
            case "MAGIC_NUMBER" -> "💡 AI Fix: Sabit sayıyı sınıf düzeyinde 'private static final int TIMEOUT_MS = ...' şeklinde tanımlayın.";
            case "UNCLOSED_RESOURCE" -> "💡 AI Fix: Kaynak sızıntısını önlemek için try-with-resources bloğu kullanın -> try (FileReader fr = new FileReader(...))";
            case "HARDCODED_CREDENTIALS" -> "💡 AI Fix: Şifre/Token bilgilerini koda gömmek yerine System.getenv(\"SECRET_KEY\") ile okuyun.";
            default -> "💡 AI Fix: Clean Code prensiplerine uygun şekilde refactor edin.";
        };
    }

    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    public void exportToHtml(List<Issue> issues, String fileName) {
        try {
            File safeFile = new File(fileName).getCanonicalFile();

            try (PrintWriter writer = new PrintWriter(new FileWriter(safeFile))) {
                writer.println("<!DOCTYPE html>");
                writer.println("<html lang='tr'><head><meta charset='UTF-8'>");
                writer.println("<title>J-Inspector Analysis Dashboard</title>");
                writer.println("<style>");
                writer.println("body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; margin: 0; padding: 30px; background-color: #0f172a; color: #f8fafc; }");
                writer.println(".container { max-width: 1200px; margin: 0 auto; }");
                writer.println("h1 { font-size: 28px; font-weight: 700; color: #38bdf8; border-bottom: 1px solid #1e293b; padding-bottom: 15px; }");
                writer.println(".card { background: #1e293b; border-radius: 12px; padding: 20px; margin-bottom: 25px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.3); }");
                writer.println("table { width: 100%; border-collapse: collapse; text-align: left; }");
                writer.println("th, td { padding: 14px; border-bottom: 1px solid #334155; }");
                writer.println("th { background-color: #0284c7; color: white; text-transform: uppercase; font-size: 12px; letter-spacing: 1px; }");
                writer.println("tr:hover { background-color: #334155; }");
                writer.println(".badge { padding: 4px 8px; border-radius: 6px; font-size: 11px; font-weight: 700; }");
                writer.println(".CRITICAL { background-color: #ef4444; color: white; }");
                writer.println(".HIGH { background-color: #f97316; color: white; }");
                writer.println(".MEDIUM { background-color: #eab308; color: black; }");
                writer.println(".LOW { background-color: #22c55e; color: white; }");
                writer.println(".ai-fix { color: #38bdf8; font-size: 13px; font-style: italic; }");
                writer.println("</style></head><body>");

                writer.println("<div class='container'>");
                writer.println("<h1>🔍 J-Inspector Static Code Analysis Dashboard</h1>");
                writer.println("<div class='card'><h3>📊 Toplam İhlal Sayısı: <span style='color:#38bdf8;'>" + issues.size() + "</span></h3></div>");
                writer.println("<div class='card'><table>");
                writer.println("<tr><th>Kural Tipi</th><th>Dosya</th><th>Satır</th><th>Şiddet</th><th>Mesaj</th><th>AI Refactoring Önerisi</th></tr>");

                for (Issue issue : issues) {
                    writer.println("<tr>");
                    writer.println("<td><strong>" + escapeHtml(issue.getType()) + "</strong></td>");
                    writer.println("<td>" + escapeHtml(issue.getFile()) + "</td>");
                    writer.println("<td>" + issue.getLine() + "</td>");
                    writer.println("<td><span class='badge " + escapeHtml(issue.getSeverity().toString()) + "'>" + escapeHtml(issue.getSeverity().toString()) + "</span></td>");
                    writer.println("<td>" + escapeHtml(issue.getMessage()) + "</td>");
                    writer.println("<td class='ai-fix'>" + escapeHtml(generateAiFixSuggestion(issue.getType())) + "</td>");
                    writer.println("</tr>");
                }

                writer.println("</table></div></div></body></html>");
                System.out.println("✅ Profesyonel HTML Dashboard oluşturuldu: " + safeFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("❌ HTML Rapor oluşturulurken hata: " + e.getMessage());
        }
    }
}