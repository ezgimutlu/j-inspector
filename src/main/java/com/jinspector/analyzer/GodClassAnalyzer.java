package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;

public class GodClassAnalyzer implements Analyzer {

    private static final int MAX_METHODS_THRESHOLD = 15;
    private static final int MAX_FIELDS_THRESHOLD = 10;

    @Override
    public List<Issue> analyze(CompilationUnit cu, String filePath) {
        List<Issue> issues = new ArrayList<>();

        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(clazz -> {
            if (!clazz.isInterface()) {
                int methodCount = clazz.getMethods().size();
                int fieldCount = clazz.getFields().size();

                if (methodCount > MAX_METHODS_THRESHOLD || fieldCount > MAX_FIELDS_THRESHOLD) {
                    int lineNumber = clazz.getBegin().map(pos -> pos.line).orElse(0);
                    String className = clazz.getNameAsString();

                    issues.add(new Issue(
                            "GOD_CLASS",
                            String.format("Class '%s' might be a God Class (%d methods, %d fields). Consider splitting responsibilities.",
                                    className, methodCount, fieldCount),
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