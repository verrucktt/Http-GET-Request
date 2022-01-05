import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

/**
 This program is an example on how to use GET http request in java
 @author Veruktt
 @since 12/04/2022
 @version 1.0
 testing github

 */
public class HttpGetRequest {

    public static void main(String[] args) {
        String apiEndpoint = "api endpoint"; // url of api end point
        String apiEndPointOasis = "api endpoint"; // api endpoint for oasismonitor
        HashMap<String,String> params = new HashMap<String, String>(); // query params
        params.put("key","value"); // add params

        try {
            String apiReturn = GETRequest(apiEndpoint);
            String valueReturned = GETRequest(apiEndPointOasis, params); // store the value returned from the api
            System.out.println(valueReturned); // print the value returned from the api
            System.out.println(apiReturn);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Sends a GET request to api end point, do not use if you need query params only path params
     * @param apiEndPoint the url of the api end point, if you need path params it should be included here
     * @return the body of the api as a java string
     */
    public static String GETRequest(String apiEndPoint) throws Exception{
        final int goodResponseCode = 200;                // response code that determines a "good" api call

        HttpClient client = HttpClient.newHttpClient();  // make the http client
        HttpRequest request = HttpRequest.newBuilder()   // build http request
                .GET()                        // request of GET type
                .header("Accept","application/json") // headers
                .uri(URI.create(apiEndPoint)) // api endpoint
                .build();                     // finish building the request

        // send asynchronous http request then return to the caller what the api returned
        HttpResponse response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .join();

        // throw an error if status code isn't the "good response code"
        // api Docs don't specify status codes value
        if(response.statusCode() != goodResponseCode) {
            System.out.println(response.statusCode());
            throw new Exception("Error");
        }
        // add error codes if status codes available

        return (String) response.body(); // return the content of the api response
    }

    /**
     * Sends a GET request to api end point, do not use if you need query params only path params
     * @param apiEndPoint the url of the api end point, if you need path params it should be included here
     * @param queryParams the query parameters required for the api endpoint
     * @return the body of the api as a java string
     */

    public static String GETRequest(String apiEndPoint, HashMap<String,String> queryParams)throws Exception{
        apiEndPoint += "?"; // start prepping the string for the query parameters

        // format the string url to have the query params in them
        for(String key : queryParams.keySet()){
            apiEndPoint += key + "=" + queryParams.get(key) + "&";
        }

        HttpClient client = HttpClient.newHttpClient();  // make the http client
        HttpRequest request = HttpRequest.newBuilder()   // build http request
                .GET()                        // request of GET type
                .header("Accept","application/json") // headers
                .uri(URI.create(apiEndPoint)) // api endpoint
                .build();                     // finish building the request

        // send asynchronous http request then return to the caller what the api returned
        HttpResponse response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .join();

        final int statusCode = response.statusCode();
        // check to see if there is any errors
        if(statusCode == 400){
            System.out.println(statusCode);
            throw new Exception("Bad Request");
        }
        else if(statusCode == 404){
            System.out.println(statusCode);
            throw new Exception("Not found");
        }

        return (String) response.body(); // return the content of the api response
    }
}
