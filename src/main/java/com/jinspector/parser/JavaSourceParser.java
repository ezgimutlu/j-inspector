package com.jinspector.parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.jinspector.analyzer.Analyzer;
import com.jinspector.analyzer.MethodLengthAnalyzer;
import com.jinspector.analyzer.CyclomaticComplexityAnalyzer;
import com.jinspector.model.Issue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList; // 1. Bunu eklemeyi unutma!
import java.util.List;

public class JavaSourceParser {

    private final List<Analyzer> analyzers = List.of(
            new MethodLengthAnalyzer(),
            new CyclomaticComplexityAnalyzer()
    );

    // 2. Listeyi burada "new ArrayList<>()" diyerek oluşturuyoruz
    private final List<Issue> allIssues = new ArrayList<>();

    public void parse(String path) {
        File root = new File(path);
        if (!root.exists()) {
            System.out.println("Path not found: " + path);
            return;
        }
        scan(root);
    }

    private void scan(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    scan(f);
                }
            }
        } else if (file.getName().endsWith(".java")) {
            parseJavaFile(file);
        }
    }

    private void parseJavaFile(File file) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(file);
            for (Analyzer analyzer : analyzers) {
                List<Issue> issues = analyzer.analyze(cu, file.getName());
                allIssues.addAll(issues); // Artık burası hata vermez
                issues.forEach(System.out::println); // Ekrana da yazdıralım
            }
        } catch (IOException e) {
            System.out.println("Failed to parse: " + file.getPath());
        }
    }

    // 3. DOĞRU GETTER: Listeyi Main'den çağırmak için bu metodu kullanacağız
    public List<Issue> getAllIssues() {
        return allIssues;
    }
}


