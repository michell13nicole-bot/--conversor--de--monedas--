import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import java.util.Scanner;

public class ConversorMonedasAPI {

    private static final String API_KEY = "471e3cc9bc17d343ac040a32";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Conversor de Monedas - Alura Latam ===\n");

        System.out.print("Ingresa la moneda base (Ejemplo: USD): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Ingresa la moneda objetivo (Ejemplo: COP): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Ingresa la cantidad a convertir: ");
        double amount = scanner.nextDouble();

        try {
            String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + baseCurrency;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());
            double rate = json.getJSONObject("conversion_rates").getDouble(targetCurrency);

            double result = amount * rate;

            System.out.println("\nResultado:");
            System.out.println(amount + " " + baseCurrency + " = " + result + " " + targetCurrency);

        } catch (Exception e) {
            System.out.println("Error al obtener los datos. Verifica las monedas ingresadas.");
        }

        scanner.close();
    }
}

