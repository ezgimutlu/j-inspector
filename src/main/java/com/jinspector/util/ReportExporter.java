package com.jinspector.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jinspector.model.Issue;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReportExporter {
    private final ObjectMapper objectMapper;

    public ReportExporter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void exportToJson(List<Issue> issues, String fileName) {
        try {
            File safeFile = new File(fileName).getCanonicalFile();
            objectMapper.writeValue(safeFile, issues);
            System.out.println("\n✅ Rapor başarıyla oluşturuldu: " + safeFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("❌ Rapor yazılırken bir hata oluştu: " + e.getMessage());
        }
    }
}
