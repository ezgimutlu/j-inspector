package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;

public class MaintainabilityIndexAnalyzer implements Analyzer {

    private static final double MIN_MAINTAINABILITY_INDEX = 65.0;

    @Override
    public List<Issue> analyze(CompilationUnit cu, String filePath) {
        List<Issue> issues = new ArrayList<>();

        cu.findAll(MethodDeclaration.class).forEach(method -> {
            // 1. Cyclomatic Complexity
            ComplexityVisitor visitor = new ComplexityVisitor();
            method.accept(visitor, null);
            int complexity = visitor.getComplexity();

            // 2. Lines of Code (LOC)
            int loc = method.getEnd().flatMap(end ->
                    method.getBegin().map(begin -> end.line - begin.line + 1)
            ).orElse(1);

            // 3. Simplified Maintainability Index Calculation
            double mi = 171.0 - (5.2 * Math.log(loc)) - (0.23 * complexity);
            mi = Math.max(0.0, (mi * 100.0) / 171.0); // Normalize 0 - 100

            if (mi < MIN_MAINTAINABILITY_INDEX) {
                int lineNumber = method.getBegin().map(pos -> pos.line).orElse(0);
                String methodName = method.getNameAsString();

                issues.add(new Issue(
                        "LOW_MAINTAINABILITY_INDEX",
                        String.format("Method '%s' has low Maintainability Index of %.1f (Threshold is %.1f). Consider simplifying.",
                                methodName, mi, MIN_MAINTAINABILITY_INDEX),
                        lineNumber,
                        Severity.HIGH,
                        filePath
                ));
            }
        });

        return issues;
    }
}