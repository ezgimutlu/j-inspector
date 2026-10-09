package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.*;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;

public class DeepNestingAnalyzer implements Analyzer {

    private static final int MAX_NESTING_DEPTH = 3;

    @Override
    public List<Issue> analyze(CompilationUnit cu, String filePath) {
        List<Issue> issues = new ArrayList<>();

        cu.findAll(Statement.class).forEach(stmt -> {
            if (isNestingStatement(stmt)) {
                int depth = calculateDepth(stmt);
                if (depth > MAX_NESTING_DEPTH) {
                    int lineNumber = stmt.getBegin().map(pos -> pos.line).orElse(0);

                    issues.add(new Issue(
                            "DEEP_NESTING",
                            String.format("Code block is nested too deep (%d levels). Max recommended depth is %d.",
                                    depth, MAX_NESTING_DEPTH),
                            lineNumber,
                            Severity.MEDIUM,
                            filePath
                    ));
                }
            }
        });

        return issues;
    }

    private boolean isNestingStatement(Statement stmt) {
        return stmt.isIfStmt() || stmt.isForStmt() || stmt.isForEachStmt() ||
                stmt.isWhileStmt() || stmt.isDoStmt() || stmt.isSwitchStmt();
    }

    private int calculateDepth(Statement stmt) {
        int depth = 0;
        Statement parent = stmt.getParentNode()
                .filter(p -> p instanceof Statement)
                .map(p -> (Statement) p)
                .orElse(null);

        while (parent != null) {
            if (isNestingStatement(parent)) {
                depth++;
            }
            Statement nextParent = parent;
            parent = parent.getParentNode()
                    .filter(p -> p instanceof Statement)
                    .map(p -> (Statement) p)
                    .orElse(null);
        }
        return depth;
    }
}