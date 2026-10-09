package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.*;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;

public class DeadCodeAnalyzer implements Analyzer {

    @Override
    public List<Issue> analyze(CompilationUnit cu, String filePath) {
        List<Issue> issues = new ArrayList<>();

        // 1. Durum: if (false) veya while (false) kontrolü
        cu.findAll(IfStmt.class).forEach(ifStmt -> {
            if (ifStmt.getCondition().toString().equals("false")) {
                int lineNumber = ifStmt.getBegin().map(pos -> pos.line).orElse(0);
                issues.add(new Issue(
                        "DEAD_CODE",
                        "Unreachable code detected: 'if (false)' condition will never execute.",
                        lineNumber,
                        Severity.HIGH,
                        filePath
                ));
            }
        });

        // 2. Durum: return/throw ifadelerinden sonra gelen erişilemez kodlar
        cu.findAll(BlockStmt.class).forEach(block -> {
            List<Statement> stmts = block.getStatements();
            for (int i = 0; i < stmts.size() - 1; i++) {
                Statement current = stmts.get(i);
                if (current.isReturnStmt() || current.isThrowStmt() || current.isBreakStmt() || current.isContinueStmt()) {
                    Statement unreachable = stmts.get(i + 1);
                    int lineNumber = unreachable.getBegin().map(pos -> pos.line).orElse(0);
                    issues.add(new Issue(
                            "DEAD_CODE",
                            "Unreachable code detected: Code following return/throw/break/continue statement will never execute.",
                            lineNumber,
                            Severity.HIGH,
                            filePath
                    ));
                    break;
                }
            }
        });

        return issues;
    }
}