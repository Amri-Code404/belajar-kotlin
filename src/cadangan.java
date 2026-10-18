import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class cadangan {
    static void main() {

        try (
                ExecutorService executor = Executors.newFixedThreadPool(2);
        ) {
            Future<String> p = executor.submit(cadangan::ambilUser);
            executor.submit(cadangan::ambilComments);

            System.out.println(p.get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }


    public static String ambilUser() {

        try (
                HttpClient client = HttpClient.newHttpClient();
        ) {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/users")).GET().build();

            try {

                HttpResponse<String> hasil = client.send(request, HttpResponse.BodyHandlers.ofString());

                // System.out.println(hasil.body());

                if (hasil.statusCode() == 200) {

                    var waktu = LocalDateTime.now();

                    String data = """
                                {
                                "Waktu Diambil": "%s",
                                "Data": %s
                            }
                            """.formatted(waktu, hasil.body());


                    Path writer = Files.writeString(Path.of("src/hasil1.json"), data);

                }

            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        return "Berhasil mengambil user";
    }

    public static String ambilComments() {

        try (
                HttpClient client = HttpClient.newHttpClient();
        ) {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/comments")).GET().build();

            try {

                HttpResponse<String> hasil = client.send(request, HttpResponse.BodyHandlers.ofString());

                // System.out.println(hasil.body());

                if (hasil.statusCode() == 200) {

                    var waktu = LocalDateTime.now();

                    String data = """
                                {
                                "Waktu Diambil": "%s",
                                "Data": %s
                            }
                            """.formatted(waktu, hasil.body());


                    Path writer = Files.writeString(Path.of("src/hasil2.json"), data, StandardOpenOption.TRUNCATE_EXISTING);

                }

            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        return "Berhasil mengambil komentar";
    }
}
