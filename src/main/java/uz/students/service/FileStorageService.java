package uz.students.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    @Value("${file.upload}")
    private String uploadDirPdf;

    public Resource loadFileAsResourcePDF(String fileName) {
        try {
            Path filePath = Paths.get(uploadDirPdf).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Fayl topilmadi: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Faylni yuklash uchun malumotni qayta yaratishda xato yuz berdi", ex);
        }
    }

    public Resource loadFileAsResourceXlsx(String fileName) {
        try {
            Path filePath = Paths.get("").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Fayl topilmadi: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Faylni yuklash uchun malumotni qayta yaratishda xato yuz berdi", ex);
        }
    }

}
