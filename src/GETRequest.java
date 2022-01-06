import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

/**
 * Constructs the api GET request and their response in string format
 * @author Veruktt
 * @since 01/05/2022
 * @version 1.0
 */
public class GETRequest {
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse response;

    /**
     * Build the GET request without query params
     * @param apiEndPoint the endpoint you want to interact with
     */
    public GETRequest(String apiEndPoint){
       // build request
        client = HttpClient.newHttpClient();  // make the http client
        request = HttpRequest.newBuilder()   // build http request
                .GET()                        // request of GET type
                .header("Accept","application/json") // headers
                .uri(URI.create(apiEndPoint)) // api endpoint
                .build();                     // finish building the request
    }

    /**
     * Build the GET request with query params
     * @param apiEndPoint the api you want to interact with
     * @param params the query params needed
     */
    public GETRequest(String apiEndPoint, HashMap<String, String> params){
        apiEndPoint += "?"; // start prepping the string for the query parameters

        // format the string url to have the query params in them
        for(String key : params.keySet()){
            apiEndPoint += key + "=" + params.get(key) + "&";
        }

        // build the request
        client = HttpClient.newHttpClient();  // make the http client
        request = HttpRequest.newBuilder()   // build http request
                .GET()                        // request of GET type
                .header("Accept","application/json") // headers
                .uri(URI.create(apiEndPoint)) // api endpoint
                .build();                     // finish building the request
    }

    /**
     * Send api request to api and return its response in string format
     * @return api response in a string format
     * @throws NullPointerException if the GET request was never built
     */
    public String sendGETRequest() throws NullPointerException{
        if(client == null || request == null)
            throw new NullPointerException("Cannot recieve response without having a request");

        //send the request to the api and store its response
        response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .join();

        return (String) response.body();
    }

    /**
     * @return the status code of the last api call
     * @throws NullPointerException If no response(api has never been called)
     */
    public int getStatusCode() throws NullPointerException{
        if(response == null)
            throw new NullPointerException("Api must atleast be called once prior");

        return response.statusCode();
    }
}
