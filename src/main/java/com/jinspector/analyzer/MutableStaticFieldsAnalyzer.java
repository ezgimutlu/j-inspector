package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;

public class MutableStaticFieldsAnalyzer implements Analyzer {

    @Override
    public List<Issue> analyze(CompilationUnit cu, String filePath) {
        List<Issue> issues = new ArrayList<>();

        cu.findAll(FieldDeclaration.class).forEach(field -> {
            if (field.isPublic() && field.isStatic() && !field.isFinal()) {
                int lineNumber = field.getBegin().map(pos -> pos.line).orElse(0);
                field.getVariables().forEach(var -> {
                    issues.add(new Issue(
                            "MUTABLE_STATIC_FIELD",
                            String.format("Public static mutable field detected: '%s'. Make it private or final to prevent thread safety issues.", var.getNameAsString()),
                            lineNumber,
                            Severity.HIGH,
                            filePath
                    ));
                });
            }
        });

        return issues;
    }
}