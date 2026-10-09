package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SecurityThreatAnalyzer implements Analyzer {

    private static final Set<String> SENSITIVE_KEYWORDS = Set.of(
            "password", "passwd", "secret", "apikey", "token", "authkey"
    );

    @Override
    public List<Issue> analyze(CompilationUnit cu, String filePath) {
        List<Issue> issues = new ArrayList<>();

        // 1. Sabit Parola veya API Key Kullanımı
        cu.findAll(VariableDeclarator.class).forEach(var -> {
            String varName = var.getNameAsString().toLowerCase();
            if (SENSITIVE_KEYWORDS.stream().anyMatch(varName::contains)) {
                if (var.getInitializer().isPresent() && var.getInitializer().get().isStringLiteralExpr()) {
                    int lineNumber = var.getBegin().map(pos -> pos.line).orElse(0);
                    issues.add(new Issue(
                            "HARDCODED_SECRET",
                            String.format("Potential hardcoded secret/password in variable '%s'. Store secrets securely.", var.getNameAsString()),
                            lineNumber,
                            Severity.HIGH,
                            filePath
                    ));
                }
            }
        });

        // 2. Potansiyel SQL Injection (Dinamik SQL Birleştirme)
        cu.findAll(MethodCallExpr.class).forEach(call -> {
            String methodName = call.getNameAsString().toLowerCase();
            if (methodName.contains("executequery") || methodName.contains("executeupdate") || methodName.contains("execute")) {
                call.getArguments().forEach(arg -> {
                    if (arg instanceof BinaryExpr && arg.toString().contains("+")) {
                        int lineNumber = call.getBegin().map(pos -> pos.line).orElse(0);
                        issues.add(new Issue(
                                "SQL_INJECTION_RISK",
                                "Potential SQL Injection: Concatenating strings in SQL queries. Use PreparedStatement instead.",
                                lineNumber,
                                Severity.HIGH,
                                filePath
                        ));
                    }
                });
            }
        });

        return issues;
    }
}