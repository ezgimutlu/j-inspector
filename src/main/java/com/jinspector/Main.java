package com.jinspector;

import com.jinspector.model.Issue;
import com.jinspector.parser.JavaSourceParser;
import com.jinspector.util.HTMLReportExporter;
import com.jinspector.util.ReportExporter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "j-inspector", mixinStandardHelpOptions = true, version = "J-Inspector 3.0",
        description = "Java projeleri için yüksek performanslı statik kod analiz aracı.")
public class Main implements Callable<Integer> {

    @Option(names = {"-p", "--path"}, description = "Analiz edilecek proje veya dosya yolu", defaultValue = ".")
    private String projectPath;

    @Option(names = {"-o", "--output"}, description = "Çıktı rapor dosya adı (örneğin: report)", defaultValue = "jinspector_report")
    private String outputFileName;

    @Option(names = {"-f", "--format"}, description = "Rapor formatı: json, html veya all", defaultValue = "all")
    private String format;

    @Override
    public Integer call() {
        System.out.println("=================================================");
        System.out.println(" 🔍 J-Inspector Static Code Analysis Engine v3.0 ");
        System.out.println("=================================================");
        System.out.println("📂 Hedef Dizin : " + projectPath);

        long startTime = System.currentTimeMillis();

        JavaSourceParser parser = new JavaSourceParser();
        parser.parse(projectPath);

        List<Issue> issuesFound = parser.getAllIssues();
        long duration = System.currentTimeMillis() - startTime;

        System.out.println("-------------------------------------------------");
        System.out.println("✅ Analiz Tamamlandı! (Süre: " + duration + " ms)");
        System.out.println("🚨 Toplam Tespit Edilen İhlal: " + issuesFound.size());

        if (!issuesFound.isEmpty()) {
            if ("json".equalsIgnoreCase(format) || "all".equalsIgnoreCase(format)) {
                new ReportExporter().exportToJson(issuesFound, outputFileName + ".json");
            }
            if ("html".equalsIgnoreCase(format) || "all".equalsIgnoreCase(format)) {
                new HTMLReportExporter().exportToHtml(issuesFound, outputFileName + ".html");
            }
        } else {
            System.out.println("✨ Mükemmel! Kod tabanında hiçbir kural ihlaline rastlanmadı.");
        }

        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}
