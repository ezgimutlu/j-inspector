package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;

public class CognitiveComplexityAnalyzer implements Analyzer {

    private static final int MAX_COGNITIVE_THRESHOLD = 15;

    @Override
    public List<Issue> analyze(CompilationUnit cu, String filePath) {
        List<Issue> issues = new ArrayList<>();

        cu.findAll(MethodDeclaration.class).forEach(method -> {
            CognitiveComplexityVisitor visitor = new CognitiveComplexityVisitor();
            method.accept(visitor, 0);
            int complexity = visitor.getComplexity();

            if (complexity > MAX_COGNITIVE_THRESHOLD) {
                int lineNumber = method.getBegin().map(pos -> pos.line).orElse(0);
                String methodName = method.getNameAsString();

                issues.add(new Issue(
                        "HIGH_COGNITIVE_COMPLEXITY",
                        String.format("Method '%s' has high cognitive complexity of %d (Threshold is %d). Refactor nested blocks.",
                                methodName, complexity, MAX_COGNITIVE_THRESHOLD),
                        lineNumber,
                        Severity.HIGH,
                        filePath
                ));
            }
        });

        return issues;
    }
}