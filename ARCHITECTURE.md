cat << 'EOF' > ARCHITECTURE.md
# 🏗️ J-Inspector Mimari ve Teknik Kararlar

Bu doküman, **J-Inspector** projesinin arkasındaki mimari desenleri, teknoloji tercihlerinin gerekçelerini ve mühendislik kararlarını özetlemektedir.

---

## 🎯 1. Temel Mühendislik Odağı: Önce Backend (Backend First)
**J-Inspector** projesinin temel amacı; yüksek performanslı, kodun derlenmesine ihtiyaç duymayan bir Java statik kod analiz motoru sunmaktır.

- **Ana Sorumluluk ve Eser Sahibi:** AST analizi (JavaParser), özel kural motorları, zafiyet tespit algoritmaları ve CI/CD süreçleri projenin ana mühendislik emeğini oluşturur.
- **Raporlama ve Sunum Katmanı:** Backend mimarisine, güvenlik kurallarına ve algoritma performansına odaklanmayı sürdürebilmek amacıyla; HTML/CSS rapor paneli ve sunum slaytları (`docs/presentation.html`) yapay zeka destekli hızlı prototipleme (AI-assisted rapid prototyping) araçlarıyla geliştirilmiştir.
- **Sektörel Yaklaşım:** Görsel ön yüz prototiplemesinde AI araçlarından faydalanmak, bir Backend / Yazılım Mühendisinin ana sistem mantığından sapmadan uçtan uca ürün görünürlüğü sağlamasına olanak tanır.

---

## 🛠️ 2. Mimari Bileşenler

### A. Statik Kod Analiz Motoru (Java & JavaParser)
- **Soyut Sözdizim Ağacı (AST):** Kaynak kodlar derleme zorunluluğu olmadan AST düğümlerine ayrıştırılır.
- **Visitor Pattern:** Özel tarayıcılar sınıfları, catch bloklarını ve kaynak kullanımlarını milisaniyeler içinde gezer.

### B. Kural Motoru ve AI Otomatik Düzeltme
- **Güvenlik ve Kalite Kuralları:** OWASP zafiyetlerine (örn. gömülü şifreler/API anahtarları) ve performans sorunlarına odaklanır.
- **Bağlama Duyarlı Öneriler:** Geliştiricinin anında müdahale edebilmesi için koda uygun çözüm önerileri üretir.

### C. CI/CD ve Dağıtım
- **Otomatik İş Akışları:** GitHub Actions, `main` dalına yapılan her push işleminde projenin derleme bütünlüğünü doğrular ve kendi kendini tarar.
  EOF