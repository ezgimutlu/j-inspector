package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;

public class UnusedImportsAnalyzer implements Analyzer {

    @Override
    public List<Issue> analyze(CompilationUnit cu, String filePath) {
        List<Issue> issues = new ArrayList<>();
        String fullContent = cu.toString();

        cu.getImports().forEach(importDecl -> {
            // Asterisk (*) ile biten paket importlarını atla (ör. java.util.*)
            if (!importDecl.isAsterisk()) {
                String importedClass = importDecl.getName().getIdentifier();

                // Class isminin metin içinde geçme sıklığını kontrol et
                long count = countOccurrences(fullContent, importedClass);

                // Sadece import bildiriminin kendisinde geçiyorsa unused demektir
                if (count <= 1) {
                    int lineNumber = importDecl.getBegin().map(pos -> pos.line).orElse(0);
                    issues.add(new Issue(
                            "UNUSED_IMPORT",
                            String.format("Unused import detected: '%s'", importDecl.getNameAsString()),
                            lineNumber,
                            Severity.LOW,
                            filePath
                    ));
                }
            }
        });

        return issues;
    }

    private long countOccurrences(String text, String word) {
        String[] tokens = text.split("\\W+");
        long count = 0;
        for (String token : tokens) {
            if (token.equals(word)) {
                count++;
            }
        }
        return count;
    }
}