package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    // Metoda odczytująca zawartość pliku
    public static String readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        return Files.readString(path);
    }

    // Metoda zapisująca zawartość do pliku
    public static void writeFile(String fileName, String content) throws IOException {
        Path path = Paths.get(fileName);
        Files.writeString(path, content);
    }

    // Metoda tworząca plik, jeśli nie istnieje
    public static void createFileIfNotExists(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
    }
}
