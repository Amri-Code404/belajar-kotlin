import com.sun.net.httpserver.Request;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class cadangan {
    static void main() {

        try(
                HttpClient client = HttpClient.newHttpClient();
        ){

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/users")).GET().build();

            try {

                HttpResponse<String> hasil = client.send(request,HttpResponse.BodyHandlers.ofString());

                // System.out.println(hasil.body());

                Path writer = Files.writeString(Path.of("src/hasil.json"), hasil.body());

            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
