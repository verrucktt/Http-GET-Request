import java.util.HashMap;

/**
 * An example of the http GET request class
 * @author Verkutt
 * @since 01/05/2022
 * @version 1.1
 */
public class Main {
    public static void main(String[] args) {
        GETRequest getRequest = new GETRequest("https://api.coingecko.com/api/v3/ping");

        HashMap<String,String> params = new HashMap<String,String>();
        params.put("vs_currency","usd");
        params.put("from","1392577232");
        params.put("to","1422577232");
        GETRequest getRequest2 = new GETRequest("https://api.coingecko.com/api/v3/coins/bitcoin/market_chart/range",params);

        try{
            System.out.println(getRequest.sendGETRequest());
            System.out.println(getRequest.getStatusCode());

            System.out.println(getRequest2.sendGETRequest());
            System.out.println(getRequest2.getStatusCode());
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }
}
