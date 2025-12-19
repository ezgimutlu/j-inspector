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
    public void dangerousMethod() {
        try {
            int x = 10 / 0; // Hata verecek
        } catch (Exception e) {
            // İçi boş, J-Inspector bunu yakalamalı!
        }
    }
}



