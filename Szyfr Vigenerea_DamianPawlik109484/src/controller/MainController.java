package controller;

import service.FrequencyAnalysisService;
import service.VigenereService;
import utils.FileManager;
import view.UserView;

import java.io.IOException;

public class MainController {

    private final UserView view;
    private final VigenereService vigenereService;
    private final FrequencyAnalysisService frequencyAnalysisService;

    // Konstruktor klasy MainController, inicjalizuje widok oraz usługi
    public MainController(UserView view, VigenereService vigenereService, FrequencyAnalysisService frequencyAnalysisService) {
        this.view = view;
        this.vigenereService = vigenereService;
        this.frequencyAnalysisService = frequencyAnalysisService;
    }

    // Metoda uruchamiająca główną logikę programu
    public void start() {
        try {
            initializeFiles(); // Inicjalizacja plików
        } catch (IOException e) {
            System.out.println("Błąd inicjalizacji plików: " + e.getMessage());
            return; // Zakończ program, jeśli wystąpi błąd
        }

        boolean running = true;
        while (running) { // Pętla główna programu
            view.showAutor();
            view.showMainMenu(); // Wyświetlenie menu głównego
            int choice = view.getUserChoice(); // Pobranie wyboru użytkownika
            switch (choice) {
                case 1 -> showEncryptMenu(); // Przejdź do menu szyfrowania
                case 2 -> showDecryptMenu(); // Przejdź do menu deszyfrowania
                case 3 -> view.showProgramInfo(); // Wyświetl informacje o programie
                case 4 -> running = false; // Zakończ program
                default -> view.showMenuError(); // Wyświetl błąd, jeśli wybór jest niepoprawny
            }
        }
    }

    // Metoda inicjalizująca wymagane pliki
    private void initializeFiles() throws IOException {
        FileManager.createFileIfNotExists("klucz.txt");
        FileManager.createFileIfNotExists("tekstdozaszyfrowania.txt");
        FileManager.createFileIfNotExists("zaszyfrowanytekst.txt");
        FileManager.createFileIfNotExists("tekstdoodszyfrowania.txt");
        FileManager.createFileIfNotExists("odszyfrowanytekst.txt");
        FileManager.createFileIfNotExists("kluczodszyfrowany.txt");
    }

    // Metoda wyświetlająca menu szyfrowania
    private void showEncryptMenu() {
        view.showAutor();
        view.showCryptMenu();
        int choice = view.getUserChoice();
        switch (choice) {
            case 1 -> encryptFromFile(); // Wykonaj szyfrowanie z pliku
            case 2 -> {} // Powrót do poprzedniego menu
            default -> view.showMenuError(); // Wyświetl błąd, jeśli wybór jest niepoprawny
        }
    }

    // Metoda wyświetlająca menu deszyfrowania
    private void showDecryptMenu() {
        view.showAutor();
        view.showDecryptMenu();
        int choice = view.getUserChoice();
        switch (choice) {
            case 1 -> decryptFromFile(); // Wykonaj deszyfrowanie z pliku
            case 2 -> guessKeyFromFile(); // Wykonaj zgadywanie klucza z pliku
            default -> view.showMenuError(); // Wyświetl błąd, jeśli wybór jest niepoprawny
        }
    }

    // Metoda szyfrująca tekst z pliku
    private void encryptFromFile() {
        try {
            String key = FileManager.readFile("klucz.txt").trim(); // Odczyt klucza z pliku
            String text = FileManager.readFile("tekstdozaszyfrowania.txt").trim(); // Odczyt tekstu do zaszyfrowania
            String encrypted = vigenereService.encrypt(text, key); // Szyfrowanie tekstu
            FileManager.writeFile("zaszyfrowanytekst.txt", encrypted); // Zapis zaszyfrowanego tekstu do pliku
            System.out.println("Tekst został zaszyfrowany i zapisany do pliku zaszyfrowanytekst.txt");
        } catch (IOException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    // Metoda deszyfrująca tekst z pliku
    private void decryptFromFile() {
        try {
            String key = FileManager.readFile("klucz.txt").trim(); // Odczyt klucza z pliku
            String text = FileManager.readFile("tekstdoodszyfrowania.txt").trim(); // Odczyt zaszyfrowanego tekstu
            String decrypted = vigenereService.decrypt(text, key); // Deszyfrowanie tekstu
            FileManager.writeFile("odszyfrowanytekst.txt", decrypted); // Zapis odszyfrowanego tekstu do pliku
            System.out.println("Tekst został odszyfrowany i zapisany do pliku odszyfrowanytekst.txt");
        } catch (IOException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    // Metoda zgadująca klucz z zaszyfrowanego tekstu
    private void guessKeyFromFile() {
        try {
            String cipherText = FileManager.readFile("tekstdoodszyfrowania.txt").trim(); // Odczyt zaszyfrowanego tekstu
            int keyLength = frequencyAnalysisService.guessKeyLength(cipherText); // Oszacowanie długości klucza
            System.out.println("Oszacowana długość klucza: " + keyLength);

            String guessedKey = frequencyAnalysisService.guessKey(cipherText, keyLength); // Zgadywanie klucza
            FileManager.writeFile("kluczodszyfrowany.txt", guessedKey); // Zapis odgadniętego klucza do pliku
            System.out.println("Odgadnięty klucz: " + guessedKey);
            System.out.println("Klucz został zapisany do pliku kluczodszyfrowany.txt");
        } catch (IOException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }
}
