package service;

import java.util.HashMap;
import java.util.Map;

public class FrequencyAnalysisService {

    // Tablica częstotliwości liter w języku angielskim
    private static final double[] ENGLISH_LETTER_FREQUENCIES = {
            0.082, 0.015, 0.028, 0.043, 0.127, 0.022, 0.020, 0.061,
            0.070, 0.002, 0.008, 0.040, 0.024, 0.067, 0.075, 0.019,
            0.001, 0.060, 0.063, 0.091, 0.028, 0.010, 0.023, 0.001,
            0.020, 0.001
    };

    // Metoda zgadująca długość klucza
    public int guessKeyLength(String cipherText) {
        cipherText = cipherText.replaceAll("[^A-Z]", "");  // Usuwamy znaki niealfabetyczne
        int length = 1;
        double highestIC = 0.0;

        // Sprawdzamy różne długości klucza od 1 do 20
        for (int i = 1; i <= 20; i++) {
            double ic = calculateIC(cipherText, i); // Obliczamy indeks koincydencji
            System.out.println("Długość klucza: " + i + " ma IC: " + ic);
            if (ic > highestIC) {
                highestIC = ic;
                length = i; // Ustawiamy długość klucza z najwyższym IC
            }
            if (highestIC > 0.065) {
                break;
            }
        }
        return length;
    }

    // Metoda obliczająca indeks koincydencji dla danej długości klucza
    private double calculateIC(String text, int keyLength) {
        double totalIC = 0.0;

        // Dzielimy tekst na sekwencje dla każdej pozycji klucza
        for (int i = 0; i < keyLength; i++) {
            StringBuilder sequence = new StringBuilder();
            for (int j = i; j < text.length(); j += keyLength) {
                char c = text.charAt(j);
                if (c >= 'A' && c <= 'Z') {
                    sequence.append(c);
                }
            }
            totalIC += calculateSequenceIC(sequence.toString()); // Obliczamy IC dla sekwencji
        }

        return totalIC / keyLength; // Zwracamy średni IC
    }

    // Metoda obliczająca IC dla danej sekwencji
    private double calculateSequenceIC(String sequence) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : sequence.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        double ic = 0.0;
        int length = sequence.length();
        for (int count : frequencyMap.values()) {
            ic += (count * (count - 1));
        }

        return ic / (length * (length - 1));
    }

    // Metoda zgadująca klucz na podstawie zaszyfrowanego tekstu i długości klucza
    public String guessKey(String cipherText, int keyLength) {
        cipherText = cipherText.replaceAll("[^A-Z]", "");  // Usuwamy znaki niealfabetyczne
        StringBuilder guessedKey = new StringBuilder();

        // Dzielimy tekst na sekwencje dla każdej pozycji klucza
        for (int i = 0; i < keyLength; i++) {
            StringBuilder sequence = new StringBuilder();
            for (int j = i; j < cipherText.length(); j += keyLength) {
                char c = cipherText.charAt(j);
                if (c >= 'A' && c <= 'Z') {
                    sequence.append(c);
                }
            }
            char guessedChar = guessKeyChar(sequence.toString()); // Zgadujemy literę klucza
            guessedKey.append(guessedChar);
            System.out.println("Dla pozycji " + i + " odgadnięto literę klucza: " + guessedChar);
        }

        return guessedKey.toString();
    }

    // Metoda zgadująca pojedynczą literę klucza
    private char guessKeyChar(String sequence) {
        int[] frequency = new int[26];

        // Liczymy częstotliwości liter w sekwencji
        for (char c : sequence.toCharArray()) {
            frequency[c - 'A']++;
        }

        double maxCorrelation = Double.NEGATIVE_INFINITY;
        int bestShift = 0;

        // Szukamy najlepszego przesunięcia na podstawie korelacji z częstotliwościami liter w języku angielskim
        for (int shift = 0; shift < 26; shift++) {
            double correlation = 0.0;
            for (int i = 0; i < 26; i++) {
                correlation += ENGLISH_LETTER_FREQUENCIES[i] * frequency[(i + shift) % 26];
            }
            if (correlation > maxCorrelation) {
                maxCorrelation = correlation;
                bestShift = shift;
            }
        }

        return (char) ('A' + bestShift); // Zwracamy odgadniętą literę klucza
    }
}
