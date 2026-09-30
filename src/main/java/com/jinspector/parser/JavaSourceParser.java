package com.jinspector.parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.jinspector.analyzer.*;
import com.jinspector.model.Issue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaSourceParser {

    private final List<Analyzer> analyzers = List.of(
            new MethodLengthAnalyzer(),
            new CyclomaticComplexityAnalyzer(),
            new EmptyCatchAnalyzer(),
            new MagicNumberAnalyzer(),
            new TooManyParametersAnalyzer()
    );

    // Thread-safe liste yapısı
    private final List<Issue> allIssues = Collections.synchronizedList(new ArrayList<>());

    public void parse(String path) {
        File root = new File(path);
        if (!root.exists()) {
            System.err.println("❌ Yol bulunamadı: " + path);
            return;
        }

        List<File> javaFiles = new ArrayList<>();
        collectJavaFiles(root, javaFiles);

        // Paralel iş parçacıkları (Multithreading) ile hızlı tarama
        javaFiles.parallelStream().forEach(this::parseJavaFile);
    }

    private void collectJavaFiles(File file, List<File> javaFiles) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    collectJavaFiles(f, javaFiles);
                }
            }
        } else if (file.getName().endsWith(".java")) {
            javaFiles.add(file);
        }
    }

    private void parseJavaFile(File file) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(file);
            for (Analyzer analyzer : analyzers) {
                List<Issue> issues = analyzer.analyze(cu, file.getName());
                allIssues.addAll(issues);
            }
        } catch (IOException e) {
            System.err.println("❌ Dosya ayrıştırılamadı: " + file.getPath());
        }
    }

    public List<Issue> getAllIssues() {
        return new ArrayList<>(allIssues);
    }
}


