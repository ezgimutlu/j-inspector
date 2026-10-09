package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;

public class NullPointerRiskAnalyzer implements Analyzer {

    @Override
    public List<Issue> analyze(CompilationUnit cu, String filePath) {
        List<Issue> issues = new ArrayList<>();

        cu.findAll(MethodCallExpr.class).forEach(call -> {
            call.getScope().ifPresent(scope -> {
                if (scope.toString().equals("null")) {
                    int lineNumber = call.getBegin().map(pos -> pos.line).orElse(0);
                    issues.add(new Issue(
                            "NULL_POINTER_RISK",
                            String.format("Direct method call on null target: '%s'", call.toString()),
                            lineNumber,
                            Severity.HIGH,
                            filePath
                    ));
                }
            });
        });

        return issues;
    }
}