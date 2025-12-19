package com.jinspector.test;

public class TestClass {

    public void crazyMethod() {
        if (true) {
            if (true) {
                if (true) {
                    if (true) {
                        if (true) {
                            if (true) {
                                if (true) {
                                    if (true) {
                                        if (true) {
                                            if (true) {
                                                System.out.println("boom");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void maintenanceNightmare() {
        // Hem çok uzun, hem karmaşık hem de riskli bir metod örneği
        int x = 0;

        // Karmaşıklık Testi: Çok fazla bağımsız karar noktası
        if (x == 0) x++;
        if (x == 1) x++;
        if (x == 2) x++;
        if (x == 3) x++;

        // Risk Testi: Tehlikeli işlemler ve sessiz hata yakalama
        try {
            for (int i = 0; i < 100; i++) {
                if (i % 10 == 0) {
                    // İç içe geçmiş karmaşıklık
                    while (x < i) {
                        x += 2;
                    }
                }
            }
        } catch (RuntimeException e) {
            // J-Inspector burayı da yakalamalı!
        } finally {
            // Boş finally bloklarını da takip ediyorsan burası iyi bir test olur
        }

        // Uzunluk Testi: Gereksiz satırlar ekleyerek limiti zorla
        System.out.println("Gereksiz satır 1");
        System.out.println("Gereksiz satır 2");
        System.out.println("Gereksiz satır 3");
        System.out.println("Gereksiz satır 4");
        System.out.println("Gereksiz satır 5");
    }
    public void dangerousMethod() {
        try {
            int x = 10 / 0; // Hata verecek
        } catch (Exception e) {
            // İçi boş, J-Inspector bunu yakalamalı!
        }
    }
}



