package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;

public class MethodLengthAnalyzer implements Analyzer {

    private static final int MAX_LINES = 30;

    @Override
    public List<Issue> analyze(CompilationUnit cu, String fileName) {

        List<Issue> issues = new ArrayList<>();

        for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {

            if (method.getRange().isPresent()) {
                int lines = method.getRange().get().getLineCount();

                if (lines > MAX_LINES) {
                    issues.add(
                            new Issue(
                                    "LONG_METHOD",
                                    fileName,
                                    method.getBegin().get().line,
                                    Severity.HIGH,
                                    method.getNameAsString() + " has " + lines + " lines"
                            )
                    );
                }
            }
        }

        return issues;
    }
}


