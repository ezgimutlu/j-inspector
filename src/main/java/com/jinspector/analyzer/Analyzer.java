package com.jinspector.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.jinspector.model.Issue;

import java.util.List;

public interface Analyzer {

    List<Issue> analyze(CompilationUnit cu, String fileName);
}

