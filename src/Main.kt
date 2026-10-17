import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpClient.newHttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

import java.nio.file.Files
import java.nio.file.Path

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.io.path.exists

fun main() {

    val path = Path.of("src/hasil.json")

    if (!path.exists()) {

        val client = newHttpClient();

        val request =
            HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/users")).GET().build()

        val hasil = client.send(request, HttpResponse.BodyHandlers.ofString())


        val waktu = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
        val data = """{
        "Waktu Diambil": "$waktu",
        "Data": ${hasil.body()}
    }""".trimIndent()


        if (hasil.statusCode() == 200) {
            Files.writeString(path, data, Charsets.UTF_8)
            println("Yey berhasil")
        } else {
            println("error bro")
        }

    }

    val verifikasi = Files.readAllLines(path)

    println("verifikasi: ${verifikasi.size}")

    println("baris ke 52: ${verifikasi[44]}")

}