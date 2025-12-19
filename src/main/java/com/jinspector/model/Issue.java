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
    // JSON çıktısı için Getter'lar şarttır
    public String getType() { return type; }
    public String getFile() { return file; }
    public int getLine() { return line; }
    public Severity getSeverity() { return severity; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return String.format("[%s] %-15s | %s : line %-4d | %s",severity,type,file,line,message);
    }
}

