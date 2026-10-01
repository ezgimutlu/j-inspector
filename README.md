# 🔍 J-Inspector v3.0

![Build Status](https://github.com/ezgimutlu/j-inspector/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)
![Maven](https://img.shields.io/badge/Build-Maven-red.svg)
![License](https://img.shields.io/badge/License-MIT-blue.svg)

> **Java projeleri için AST tabanlı, AI Auto-Fix destekli ve CI/CD entegrasyonlu statik kod analiz motoru.**

---

## ⚡ Neden J-Inspector?
J-Inspector, Java kaynak kodlarını derlemeye ihtiyaç duymadan **Soyut Sözdizim Ağacı (AST)** seviyesinde analiz eder. Kod kalitesini artırır, güvenlik zafiyetlerini tespit eder ve geliştiriciye anında **yapay zeka destekli çözüm önerileri (AI Refactoring)** sunar.

### ✨ Öne Çıkan Özellikler
- **Core AST Engine:** JavaParser ile tam sözdizimsel kod taraması.
- **AI Auto-Fix Engine:** İhlaller için otomatik çözüm ve refactoring önerileri.
- **Security Analysis:** Kaynak sızıntıları (`UNCLOSED_RESOURCE`) ve gömülü şifre (`HARDCODED_CREDENTIALS`) tespiti.
- **Automated Reporting:** Profesyonel **HTML Dashboard** ve otomasyon dostu **JSON** çıktıları.
- **Production CI/CD:** GitHub Actions ile her `push` işleminde otomatik derleme ve self-inspection.

---

## 🛡️ Analiz Kuralları Matrisi

| Kural | Şiddet | Açıklama |
| :--- | :--- | :--- |
| `UNCLOSED_RESOURCE` | 🔴 **CRITICAL** | Kapatılmayan veritabanı/dosya akışlarını tespit eder. |
| `HARDCODED_CREDENTIALS` | 🔴 **CRITICAL** | Kod içine gömülmüş API Key, Token ve Şifreleri yakalar. |
| `EMPTY_CATCH_BLOCK` | 🟠 **HIGH** | Sessizce yutulan boş catch bloklarını raporlar. |
| `CYCLOMATIC_COMPLEXITY` | 🟡 **MEDIUM** | Karmaşıklığı yüksek metotları tespit eder. |
| `MAGIC_NUMBER` | 🟢 **LOW** | Kod içerisindeki sabit sayısal değerleri bulur. |

---

## 🤖 AI Auto-Fix Engine Örneği

J-Inspector bir ihlal bulduğunda HTML rapora geliştiriciye rehberlik eden otomatik öneriler basar:

> 💡 **AI Fix Suggestion:** Kaynak sızıntısını önlemek için try-with-resources bloğu kullanın -> `try (FileReader fr = new FileReader(...))`

---

## 🛠️ Hızlı Başlangıç

### 1. Projeyi Derleyin
```bash
mvn clean package -DskipTests