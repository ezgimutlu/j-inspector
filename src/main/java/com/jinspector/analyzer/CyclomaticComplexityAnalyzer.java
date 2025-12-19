package com.jinspector.analyzer;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;

public class CyclomaticComplexityAnalyzer implements Analyzer {

    private static final int MAX_COMPLEXITY = 5;

    @Override
    public List<Issue> analyze(CompilationUnit cu, String fileName) {

        List<Issue> issues = new ArrayList<>();

        for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {

            int complexity = calculateComplexity(method);

            if (complexity > MAX_COMPLEXITY && method.getBegin().isPresent()) {
                issues.add(
                        new Issue(
                                "CYCLOMATIC_COMPLEXITY",
                                fileName,
                                method.getBegin().get().line,
                                Severity.MEDIUM,
                                method.getNameAsString() + " complexity = " + complexity
                        )
                );
            }
        }

        return issues;
    }

    private int calculateComplexity(MethodDeclaration method) {
        int complexity = 1;

        complexity += method.findAll(IfStmt.class).size();
        complexity += method.findAll(ForStmt.class).size();
        complexity += method.findAll(ForEachStmt.class).size();
        complexity += method.findAll(WhileStmt.class).size();
        complexity += method.findAll(DoStmt.class).size();
        complexity += method.findAll(SwitchEntry.class).size();
        complexity += method.findAll(CatchClause.class).size();

        long operatorCount = method.findAll(BinaryExpr.class).stream()
                .filter(be -> be.getOperator() == Operator.AND || be.getOperator() == Operator.OR)
                .count();

        return complexity+(int) operatorCount;
    }
}

