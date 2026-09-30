package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;

public class MagicNumberAnalyzer implements Analyzer {

    // Kod içinde kabul edilebilir standart sayılar
    private static final List<String> ALLOWED_NUMBERS = List.of("0", "1", "-1", "2");

    @Override
    public List<Issue> analyze(CompilationUnit cu, String fileName) {
        List<Issue> issues = new ArrayList<>();

        cu.findAll(IntegerLiteralExpr.class).forEach(literal -> {
            String value = literal.getValue();
            if (!ALLOWED_NUMBERS.contains(value)) {
                int line = literal.getBegin().map(p -> p.line).orElse(0);
                issues.add(new Issue(
                        "MAGIC_NUMBER",
                        fileName,
                        line,
                        Severity.LOW,
                        "Büyülü sayı (Magic Number) tespit edildi: " + value + ". Lütfen sabit (constant/final) olarak tanımlayın."
                ));
            }
        });

        return issues;
    }
}
