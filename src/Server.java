import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Server {

    void main() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(1234), 0);

            server.createContext("/hallo", pesan -> {
                pesan.sendResponseHeaders(200, 0);
                pesan.getResponseBody().write("Hallo!".getBytes());
                pesan.close();
            });

            server.createContext("/jam", pesan -> {
                var time = LocalDateTime.now();
                pesan.sendResponseHeaders(200, 0);
                pesan.getResponseBody().write(time.toString().getBytes());
                pesan.close();
            });

            server.createContext("/console", pesan -> {
                pesan.sendResponseHeaders(200, 0);
                pesan.getResponseBody().write("""
                                <script>
                                \s
                                 const url = 'http://localhost:1234/post-pesan';
                                \s
                                 const data = `
                                   nama: "Budi",
                                   umur: 21,
                                   pesan: "Halo, ini testing API!"`;
                                \s
                                 fetch(url, {
                                   method: "POST",
                                   headers: {
                                     "Content-Type": "application/json"
                                   },
                                   body: data
                                 })
                                   .then(response => {
                                     if (!response.ok) {
                                       throw new Error(`HTTP Error: ${response.status}`);
                                     }
                                     return response.json();
                                   })
                                   .then(result => {
                                     console.log("Berhasil mengirim data!");
                                     console.log("Response API:", result);
                                   })
                                   .catch(error => {
                                     console.error("Gagal:", error.message);
                                   });
                                  \s
                                  </script>
                        \s""".getBytes());
                pesan.close();
            });

            server.createContext("/post-pesan", pesan -> {
                var method = pesan.getRequestMethod();

                if (!method.equals("POST")) {
                    pesan.sendResponseHeaders(400, 0);
                    pesan.getResponseBody().write("GET request method not supported!".getBytes());
                    pesan.close();
                    System.out.println(method);
                    return;
                }

                pesan.sendResponseHeaders(200, 0);

                try (
                        BufferedReader reader = new BufferedReader(new InputStreamReader(pesan.getRequestBody()));
                ) {
                    var kalimat = "";

                    while (kalimat != null) {

                        kalimat = reader.readLine();

                        if (kalimat != null) {
                            System.out.println(kalimat);
                            continue;
                        }
                        break;
                    }
                }
            });

            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    ;


}
