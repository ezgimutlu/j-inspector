package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;

public class TooManyParametersAnalyzer implements Analyzer {

    private static final int MAX_PARAMETERS = 4;

    @Override
    public List<Issue> analyze(CompilationUnit cu, String fileName) {
        List<Issue> issues = new ArrayList<>();

        cu.findAll(MethodDeclaration.class).forEach(method -> {
            int paramCount = method.getParameters().size();
            if (paramCount > MAX_PARAMETERS) {
                int line = method.getBegin().map(p -> p.line).orElse(0);
                issues.add(new Issue(
                        "TOO_MANY_PARAMETERS",
                        fileName,
                        line,
                        Severity.HIGH,
                        method.getNameAsString() + " metodu " + paramCount + " parametre alıyor! (Üst sınır: " + MAX_PARAMETERS + "). DTO veya Builder kalıbı tercih ediniz."
                ));
            }
        });

        return issues;
    }
}