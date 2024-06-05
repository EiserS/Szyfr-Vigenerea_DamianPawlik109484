import controller.MainController;
import service.FrequencyAnalysisService;
import service.VigenereService;
import view.UserView;

public class Main {
    public static void main(String[] args) {
        UserView view = new UserView(); // Inicjalizacja widoku
        VigenereService vigenereService = new VigenereService(); // Inicjalizacja usługi Vigenère'a
        FrequencyAnalysisService frequencyAnalysisService = new FrequencyAnalysisService(); // Inicjalizacja usługi analizy częstotliwości
        MainController controller = new MainController(view, vigenereService, frequencyAnalysisService); // Inicjalizacja kontrolera
        controller.start(); // Uruchomienie aplikacji
    }
}
