package com.jinspector.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jinspector.model.Issue; // Başka paketteki Issue'yu kullanabilmek için


import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReportExporter {
    private final ObjectMapper objectMapper;

    public ReportExporter() {
        // Jackson nesnesini oluşturuyoruz
        this.objectMapper = new ObjectMapper();

        // JSON çıktısının alt alta ve okunaklı olması için bu ayarı yapıyoruz
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * Verilen sorun listesini JSON dosyası olarak kaydeder.
     * @param issues Bulunan hataların listesi
     * @param fileName Kaydedilecek dosya adı (örneğin: report.json)
     */
    public void exportToJson(List<Issue> issues, String fileName) {
        try {
            // Dosyayı oluşturup içine listeyi yazıyoruz
            objectMapper.writeValue(new File(fileName), issues);
            System.out.println("\n✅ Rapor başarıyla oluşturuldu: " + fileName);
        } catch (IOException e) {
            System.err.println("❌ Rapor yazılırken bir hata oluştu: " + e.getMessage());
        }
    }
}
