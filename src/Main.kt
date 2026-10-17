import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpClient.newHttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun main() {

    val client = newHttpClient();

    val request = HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/users")).GET().build()

    val hasil = client.send(request, HttpResponse.BodyHandlers.ofString())

    println(hasil.statusCode())
    println(hasil.body())

}