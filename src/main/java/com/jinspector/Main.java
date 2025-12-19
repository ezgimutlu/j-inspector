package com.jinspector;

import com.jinspector.parser.JavaSourceParser;

public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: java -jar j-inspector.jar <source-folder>");
            return;
        }
        String sourcePath=args[0];

        JavaSourceParser parser = new JavaSourceParser();
        parser.parse(sourcePath);
    }
}
