import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import org.json.JSONObject;

public class ConversorDeMonedas {

    private static final String API_KEY = "471e3cc9bc17d343ac040a32"; // Reemplazar con tu API key
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 7) {
            System.out.println("\n=== Conversor de Monedas - Challenge Alura ===");
            System.out.println("1) USD → ARS");
            System.out.println("2) ARS → USD");
            System.out.println("3) USD → BRL");
            System.out.println("4) BRL → USD");
            System.out.println("5) USD → COP");
            System.out.println("6) COP → USD");
            System.out.println("7) Salir");
            System.out.print("Elige una opción: ");

            opcion = scanner.nextInt();

            if (opcion == 7) {
                System.out.println("Gracias por usar el conversor. ¡Hasta luego!");
                break;
            }

            System.out.print("Ingresa la cantidad: ");
            double cantidad = scanner.nextDouble();

            String base = "";
            String destino = "";

            switch (opcion) {
                case 1 -> { base = "USD"; destino = "ARS"; }
                case 2 -> { base = "ARS"; destino = "USD"; }
                case 3 -> { base = "USD"; destino = "BRL"; }
                case 4 -> { base = "BRL"; destino = "USD"; }
                case 5 -> { base = "USD"; destino = "COP"; }
                case 6 -> { base = "COP"; destino = "USD"; }
            }

            double resultado = convertir(base, destino, cantidad);

            if (resultado != -1) {
                System.out.printf("Resultado: %.2f %s = %.2f %s\n",
                        cantidad, base, resultado, destino);
            } else {
                System.out.println("Error en la conversión.");
            }
        }
    }

    public static double convertir(String base, String destino, double cantidad) {
        try {
            String url = API_URL + API_KEY + "/latest/" + base;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());

            double tasa = json.getJSONObject("conversion_rates").getDouble(destino);

            return cantidad * tasa;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return -1;
        }
    }
}
