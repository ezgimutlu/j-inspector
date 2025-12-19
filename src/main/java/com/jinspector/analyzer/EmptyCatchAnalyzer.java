package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.CatchClause;
import com.jinspector.model.Issue;
import com.jinspector.model.Severity;

import java.util.ArrayList;
import java.util.List;

public class EmptyCatchAnalyzer implements Analyzer {

    @Override
    public List<Issue> analyze(CompilationUnit cu, String fileName) {
        List<Issue> issues = new ArrayList<>();

        // Tüm catch bloklarını bul
        cu.findAll(CatchClause.class).forEach(catchBlock -> {
            // Eğer catch bloğunun içi boşsa veya sadece yorum satırı varsa
            if (catchBlock.getBody().getStatements().isEmpty()) {
                issues.add(new Issue(
                        "EMPTY_CATCH_BLOCK",
                        fileName,
                        catchBlock.getBegin().map(p -> p.line).orElse(0),
                        Severity.CRITICAL, // Bu çok tehlikeli bir durumdur!
                        "Boş catch bloğu tespit edildi. Hatalar sessizce yutulmamalı!"
                ));
            }
        });

        return issues;
    }
}
