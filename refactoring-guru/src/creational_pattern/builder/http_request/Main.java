package creational_pattern.builder.http_request;

public class Main {
    public static void main(String[] args) {
        HttpRequest httpRequest = new HttpRequest.Builder()
                .url("https://google.com")
                .methods("GET")
                .retryCount(3)
                .timeout(5000)
                .bearerToken("gfdhklkjfbhjkfdvsbjhksfbvdhvnkbdjzkzfbvankzdv")
                .build();

        System.out.println(httpRequest);
    }
}
