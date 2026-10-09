package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.TryStmt;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResourceLeakAnalyzer implements Analyzer {

    private static final Set<String> CLOSEABLE_TYPES = Set.of(
            "FileInputStream", "FileOutputStream", "FileReader", "FileWriter",
            "BufferedReader", "BufferedWriter", "Scanner", "Connection",
            "Statement", "ResultSet", "InputStream", "OutputStream"
    );

    @Override
    public List<Issue> analyze(CompilationUnit cu, String filePath) {
        List<Issue> issues = new ArrayList<>();

        cu.findAll(ObjectCreationExpr.class).forEach(creation -> {
            String typeName = creation.getTypeAsString();

            if (CLOSEABLE_TYPES.contains(typeName)) {
                // Objenin bir try-with-resources içinde tanımlanıp tanımlanmadığını kontrol et
                boolean isInTryWithResources = creation.findAncestor(TryStmt.class)
                        .map(tryStmt -> tryStmt.getResources().stream()
                                .anyMatch(res -> res.toString().contains(creation.toString())))
                        .orElse(false);

                if (!isInTryWithResources) {
                    int lineNumber = creation.getBegin().map(pos -> pos.line).orElse(0);

                    issues.add(new Issue(
                            "RESOURCE_LEAK",
                            String.format("Potential resource leak: '%s' created without try-with-resources.", typeName),
                            lineNumber,
                            Severity.HIGH,
                            filePath
                    ));
                }
            }
        });

        return issues;
    }
}