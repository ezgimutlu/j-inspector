package com.jinspector.analyzer;

import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class CognitiveComplexityVisitor extends VoidVisitorAdapter<Integer> {

    private int complexity = 0;

    @Override
    public void visit(IfStmt n, Integer nesting) {
        complexity += 1 + nesting;
        super.visit(n, nesting + 1);
    }

    @Override
    public void visit(ForStmt n, Integer nesting) {
        complexity += 1 + nesting;
        super.visit(n, nesting + 1);
    }

    @Override
    public void visit(ForEachStmt n, Integer nesting) {
        complexity += 1 + nesting;
        super.visit(n, nesting + 1);
    }

    @Override
    public void visit(WhileStmt n, Integer nesting) {
        complexity += 1 + nesting;
        super.visit(n, nesting + 1);
    }

    @Override
    public void visit(DoStmt n, Integer nesting) {
        complexity += 1 + nesting;
        super.visit(n, nesting + 1);
    }

    @Override
    public void visit(SwitchStmt n, Integer nesting) {
        complexity += 1 + nesting;
        super.visit(n, nesting + 1);
    }

    @Override
    public void visit(CatchClause n, Integer nesting) {
        complexity += 1 + nesting;
        super.visit(n, nesting + 1);
    }

    public int getComplexity() {
        return complexity;
    }
}