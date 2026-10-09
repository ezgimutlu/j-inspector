package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class NamingConventionAnalyzer implements Analyzer {

    private static final Pattern PASCAL_CASE = Pattern.compile("^[A-Z][a-zA-Z0-9]*$");
    private static final Pattern CAMEL_CASE = Pattern.compile("^[a-z][a-zA-Z0-9]*$");
    private static final Pattern UPPER_SNAKE_CASE = Pattern.compile("^[A-Z][A-Z0-9_]*$");

    @Override
    public List<Issue> analyze(CompilationUnit cu, String filePath) {
        List<Issue> issues = new ArrayList<>();

        // 1. Sınıf İsimleri (PascalCase)
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(clazz -> {
            String name = clazz.getNameAsString();
            if (!PASCAL_CASE.matcher(name).matches()) {
                int lineNumber = clazz.getBegin().map(pos -> pos.line).orElse(0);
                issues.add(new Issue(
                        "NAMING_CONVENTION",
                        String.format("Class name '%s' should follow PascalCase convention.", name),
                        lineNumber,
                        Severity.LOW,
                        filePath
                ));
            }
        });

        // 2. Metod İsimleri (camelCase)
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            String name = method.getNameAsString();
            if (!CAMEL_CASE.matcher(name).matches()) {
                int lineNumber = method.getBegin().map(pos -> pos.line).orElse(0);
                issues.add(new Issue(
                        "NAMING_CONVENTION",
                        String.format("Method name '%s' should follow camelCase convention.", name),
                        lineNumber,
                        Severity.LOW,
                        filePath
                ));
            }
        });

        // 3. Değişken ve Sabit İsimleri
        cu.findAll(FieldDeclaration.class).forEach(field -> {
            boolean isStaticFinal = field.isStatic() && field.isFinal();
            field.getVariables().forEach(var -> {
                String name = var.getNameAsString();
                if (isStaticFinal) {
                    if (!UPPER_SNAKE_CASE.matcher(name).matches()) {
                        int lineNumber = field.getBegin().map(pos -> pos.line).orElse(0);
                        issues.add(new Issue(
                                "NAMING_CONVENTION",
                                String.format("Constant '%s' should follow UPPER_SNAKE_CASE convention.", name),
                                lineNumber,
                                Severity.LOW,
                                filePath
                        ));
                    }
                } else {
                    if (!CAMEL_CASE.matcher(name).matches()) {
                        int lineNumber = field.getBegin().map(pos -> pos.line).orElse(0);
                        issues.add(new Issue(
                                "NAMING_CONVENTION",
                                String.format("Field name '%s' should follow camelCase convention.", name),
                                lineNumber,
                                Severity.LOW,
                                filePath
                        ));
                    }
                }
            });
        });

        return issues;
    }
}