package view;

import java.util.Scanner;

public class UserView {
    private final Scanner scanner = new Scanner(System.in);

    // Metoda pobierająca wybór użytkownika
    public int getUserChoice() {
        while (true) {
            System.out.print("Wybierz opcję: ");
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Błędny format, proszę wprowadzić numer.");
            }
        }
    }

    // Metoda wyświetlająca menu główne
    public void showMainMenu() {
        System.out.println("""
                ------------* MENU APLIKACJI *------------
                |1. Menu szyfrowania                      |
                |2. Menu deszyfrowania                    |
                |3. Informacje o programie                |
                |4. Wyjdź z programu                      |
                ------------------------------------------""");
    }

    // Metoda wyświetlająca menu szyfrowania
    public void showCryptMenu() {
        System.out.println("""
                -------------|* MENU SZYFROWANIA *--------------
                |1. Zaszyfruj z pliku                           |
                |2. Powrót                                      |
                ------------------------------------------------""");
    }

    // Metoda wyświetlająca menu deszyfrowania
    public void showDecryptMenu() {
        System.out.println("""
                -------------* MENU DESZYFROWANIA *-------------
                |1. Odszyfruj tekst z pliku                      |
                |2. Zgadnij klucz z pliku                        |
                |3. Powrót                                       |
                ------------------------------------------------""");
    }

    // Metoda wyświetlająca błąd menu
    public void showMenuError() {
        System.out.println("Error: Wybrano niepoprawną opcję, spróbuj ponownie.");
    }

    // Metoda wyświetlająca informacje o programie
    public void showProgramInfo() {
        System.out.println("""
                Program służy do szyfrowania i deszyfrowania tekstu za pomocą szyfru Vigenère'a.
                Aby zaszyfrować tekst, wybierz opcję "Menu szyfrowania" i następnie "Zaszyfruj z pliku".
                Aby odszyfrować zaszyfrowany tekst, wybierz opcję "Menu deszyfrowania" i następnie "Odszyfruj tekst z pliku".
                Klucz szyfrowania/odszyfrowywania należy umieścić w pliku "klucz.txt".
                Tekst do zaszyfrowania należy umieścić w pliku "tekstdozaszyfrowania.txt".
                Zaszyfrowany tekst zostanie zapisany w pliku "zaszyfrowanytekst.txt".
                Zaszyfrowany tekst do odszyfrowania należy umieścić w pliku "tekstdoodszyfrowania.txt".
                Odszyfrowany tekst zostanie zapisany w pliku "odszyfrowanytekst.txt".
                Klucz odgadnięty zostanie zapisany w pliku "kluczodszyfrowany.txt".
                """);
    }

    public void showAutor(){
        System.out.println("Autor: Damian Pawlik 109484");
    }
}
