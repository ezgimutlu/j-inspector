package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.*;

public class DuplicateCodeAnalyzer implements Analyzer {

    @Override
    public List<Issue> analyze(CompilationUnit cu, String filePath) {
        List<Issue> issues = new ArrayList<>();
        Map<String, Integer> blockHashes = new HashMap<>();

        cu.findAll(BlockStmt.class).forEach(block -> {
            if (block.getStatements().size() >= 3) {
                String blockText = block.toString().replaceAll("\\s+", "");
                int lineNumber = block.getBegin().map(pos -> pos.line).orElse(0);

                if (blockHashes.containsKey(blockText)) {
                    issues.add(new Issue(
                            "DUPLICATE_CODE",
                            String.format("Duplicate code block detected (first seen around line %d). Consider refactoring into a reusable method.", blockHashes.get(blockText)),
                            lineNumber,
                            Severity.MEDIUM,
                            filePath
                    ));
                } else {
                    blockHashes.put(blockText, lineNumber);
                }
            }
        });

        return issues;
    }
}