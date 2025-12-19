package com.jinspector.parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.jinspector.analyzer.Analyzer;
import com.jinspector.analyzer.MethodLengthAnalyzer;
import com.jinspector.model.Issue;
import com.jinspector.analyzer.CyclomaticComplexityAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JavaSourceParser {

    // 🔽 BU SATIR CLASS'IN İÇİNDE OLMAK ZORUNDA
    private final List<Analyzer> analyzers = List.of(
            new MethodLengthAnalyzer(),
            new CyclomaticComplexityAnalyzer()
    );

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
                issues.forEach(System.out::println);
            }

        } catch (IOException e) {
            System.out.println("Failed to parse: " + file.getPath());
        }
    }
}


