package com.jinspector;

import com.jinspector.model.Issue;
import com.jinspector.parser.JavaSourceParser;
import com.jinspector.util.ReportExporter;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // 1. Yol Belirleme (Nokta mevcut projeyi, istersen tam yol da verebilirsin)
        String projectPath = ".";

        System.out.println("🚀 J-Inspector Analizi Başlıyor...");
        System.out.println("------------------------------------");

        // 2. Analiz Başlatma
        JavaSourceParser parser = new JavaSourceParser();
        parser.parse(projectPath);

        // 3. Sonuçları Listeye Alalım
        List<Issue> issuesFound = parser.getAllIssues();

        System.out.println("------------------------------------");
        System.out.println("✅ Analiz Tamamlandı!");
        System.out.println("🔍 Toplam Bulunan Sorun: " + issuesFound.size());

        // 4. JSON Raporu Oluşturma (FINAL ADIM)
        if (!issuesFound.isEmpty()) {
            ReportExporter exporter = new ReportExporter();
            exporter.exportToJson(issuesFound, "jinspector_report.json");
        } else {
            System.out.println("✨ Tertemiz bir kod! Hiç hata bulunamadı.");
        }
    }
}
