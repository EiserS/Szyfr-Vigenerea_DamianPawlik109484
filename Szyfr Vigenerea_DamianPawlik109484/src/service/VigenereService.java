package service;

public class VigenereService {

    // Metoda szyfrująca tekst za pomocą szyfru Vigenère'a
    public String encrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();
        key = key.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') {
                result.append(c); // Dodajemy niealfabetyczne znaki bez zmian
            } else {
                result.append((char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A')); // Szyfrowanie znaku
                j = ++j % key.length();
            }
        }
        return result.toString();
    }

    // Metoda deszyfrująca tekst za pomocą szyfru Vigenère'a
    public String decrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();
        key = key.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') {
                result.append(c); // Dodajemy niealfabetyczne znaki bez zmian
            } else {
                result.append((char) ((c - key.charAt(j) + 26) % 26 + 'A')); // Deszyfrowanie znaku
                j = ++j % key.length();
            }
        }
        return result.toString();
    }
}
