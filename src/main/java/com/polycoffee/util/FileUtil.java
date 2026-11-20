package com.polycoffee.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

/**
 * Lop tien ich ho tro upload va xoa file anh trong thu muc /uploads/ (Lab 4).
 *
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
public class FileUtil {
    private static final String FOLDER = "/uploads/";
    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024;
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png", ".webp", ".gif");

    public static String upload(HttpServletRequest request, String name) throws IOException {
        try {
            Part part = request.getPart(name);
            if (part == null || part.getSize() == 0 || part.getSize() > MAX_FILE_SIZE) {
                return null;
            }
            String submitted = part.getSubmittedFileName();
            if (submitted == null || submitted.isBlank()) {
                return null;
            }
            String cleanName = Path.of(submitted).getFileName().toString();
            int dotIdx = cleanName.lastIndexOf('.');
            if (dotIdx < 0) {
                return null;
            }
            String ext = cleanName.substring(dotIdx).toLowerCase();
            if (!ALLOWED_EXTENSIONS.contains(ext)) {
                return null;
            }

            String uniqueName = System.currentTimeMillis() + ext;
            String realFolder = request.getServletContext().getRealPath(FOLDER);
            if (realFolder == null) {
                return null;
            }
            Path uploadDir = Path.of(realFolder);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            part.write(uploadDir.resolve(uniqueName).toString());
            return FOLDER + uniqueName;
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean delete(HttpServletRequest request, String fileNameOrPath) {
        try {
            if (fileNameOrPath == null || fileNameOrPath.isBlank()) {
                return false;
            }
            String fileName = Path.of(fileNameOrPath).getFileName().toString();
            String realFolder = request.getServletContext().getRealPath(FOLDER);
            if (realFolder == null) {
                return false;
            }
            File file = new File(realFolder, fileName);
            return file.exists() && file.isFile() && file.delete();
        } catch (Exception e) {
            return false;
        }
    }
}
