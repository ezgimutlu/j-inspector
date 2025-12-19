package com.jinspector;

import com.jinspector.parser.JavaSourceParser;

public class Main {

    public static void main(String[] args) {

        // Analiz etmek istediğin klasörün yolunu buraya yaz.
        // Kendi projeni taratmak istersen "." (nokta) koyabilirsin.
        String projectPath = ".";

        System.out.println("🚀 J-Inspector Analizi Başlıyor...");
        System.out.println("------------------------------------");

        JavaSourceParser parser = new JavaSourceParser();
        parser.parse(projectPath);

        System.out.println("------------------------------------");
        System.out.println("✅ Analiz Tamamlandı!");
    }
}
