# J-Inspector 🔍

**J-Inspector**, Java kaynak kodlarını derlemeye gerek duymadan analiz eden, kod kalitesini ve karmaşıklığını ölçen hafif sıklet bir **Statik Kod Analiz** aracıdır.

---

## 🚀 Öne Çıkan Özellikler

* **Döngüsel Karmaşıklık (Cyclomatic Complexity):** Metotların karar yollarını analiz eder ve riskli yapıları raporlar.
* **Metot Uzunluğu Tespiti (Long Method):** Okunması zor, aşırı uzun metotları işaretler.
* **Boş Catch Bloğu Denetimi (Empty Catch Block):** Hataların sessizce yutulduğu tehlikeli kod bloklarını yakalar.
* **Hibrit Tarama Desteği:** İster tüm projeyi, ister tek bir `.java` dosyasını analiz edebilirsiniz.
* **JSON Raporlama:** Analiz sonuçlarını `jinspector_report.json` dosyasına aktarır.

---

## 🛠 Kullanılan Teknolojiler

* **Java 17+**
* **JavaParser:** Kodları AST (Abstract Syntax Tree) yapısına dönüştürmek için.
* **Jackson Databind:** Analiz sonuçlarını JSON formatına çevirmek için.
* **Maven:** Bağımlılık yönetimi için.

---

## 💻 Kurulum ve Çalıştırma

1.  Projeyi bilgisayarınıza klonlayın:
    ```bash
    git clone [https://github.com/ezgimutlu/j-inspector.git](https://github.com/ezgimutlu/j-inspector.git)
    ```
2.  `Main.java` dosyası içerisindeki `projectPath` değişkenine analiz etmek istediğiniz yolu yazın.
3.  Projeyi çalıştırın.

---

## 📊 Örnek Rapor Çıktısı (JSON)

```json
{
  "type": "CYCLOMATIC_COMPLEXITY",
  "fileName": "TestClass.java",
  "line": 15,
  "severity": "CRITICAL",
  "message": "Complexity is 11 (Max allowed: 5)"
}
