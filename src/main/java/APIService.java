import io.netty.util.Timeout;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.ConnectException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

public class APIService
{
    private WebClient webClient;

    private String completePartNumber, storeNumber, urlRequest;

    public APIService(String storeNumber, String partialPartNumber)
    {
        urlRequest = "https://www.apple.com/shop/fulfillment-messages?store="+storeNumber+"&parts.0="+partialPartNumber+"%2FA";
    }

    public AppleStore startService()
    {
        sendRequest();
    }


    public Mono<String> sendRequest()
    {
        System.out.println("Contacting Server...");
        return webClient.get()
                .uri(this.urlRequest)
                .retrieve()
                .onStatus(status -> status.isError(), response -> {
                    return Mono.error(new RuntimeException("Error Response Received: "+response.statusCode()));
                })
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(15))
                .retryWhen(Retry.backoff(25, Duration.ofSeconds(1)))
                .onErrorResume(e -> {
                    if (e instanceof ConnectException)
                    {
                        System.out.println("Connection lost. Retrying...");
                    }
                    else if (e instanceof TimeoutException)
                    {
                        System.out.println("Connection timed out. Retrying...");
                    }
                    return Mono.error(e);
                });
    }


}
