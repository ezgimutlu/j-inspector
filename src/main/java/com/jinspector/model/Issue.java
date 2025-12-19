package com.jinspector.model;

public class Issue {

    private final String type;
    private final String file;
    private final int line;
    private final Severity severity;
    private final String message;

    public Issue(String type, String file, int line, Severity severity, String message) {
        this.type = type;
        this.file = file;
        this.line = line;
        this.severity = severity;
        this.message = message;
    }

    @Override
    public String toString() {
        return "[" + severity + "] " + type +
                " | " + file +
                " : line " + line +
                " | " + message;
    }
}

