import org.apache.http.HttpEntity;

import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Guillaume Gingembre on 03/11/2017.
 */
public class HttpMethods {

    private static final String URL1 = "https://www.quandl.com/api/v3/datasets/WIKI/";
    private static final String URL2 = "http://petstore.swagger.io/v2";

    private void sendHttpRequest(HttpRequestBase httpMethod) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault();
             CloseableHttpResponse httpResponse = httpclient.execute(httpMethod)){

            HttpEntity httpEntity = httpResponse.getEntity();
            System.out.println("Answer header: " + httpResponse.toString());
            System.out.println("Answer content: " + new BufferedReader(new InputStreamReader(httpEntity.getContent())).readLine());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void StockQuote(){
        printSeparatorLine();
        System.out.println("Exercise 1: Simple http GET \r\n");

        String symbol = "AAPL";
        String url = URL1 + symbol + ".json?start_date=2017-09-01&end_date=2017-11-02";
        HttpRequestBase httpGet = new HttpGet(url);

        System.out.println("Executing request: " + httpGet.getRequestLine());

        sendHttpRequest(httpGet);

    }

    public void Search (String sentence) {
        printSeparatorLine();
        System.out.println("Exercise 2 and 3: Search \r\n");

        HttpGet httpGet = new HttpGet("http://google.com");

        try {
            URI uri = new URIBuilder(httpGet.getURI()).addParameter("q",
                    sentence).build();
            (httpGet).setURI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.println("Executing request: " + httpGet.getRequestLine());
        sendHttpRequest(httpGet);

    }

    public void Post () {
        printSeparatorLine();
        System.out.println("Simple POST method\r\n");

        String url = URL2 + "/pet";
        HttpPost httpPost = new HttpPost(url);
        String message = "{\n" +
                "  \"id\": 200,\n" +
                "  \"category\": {\n" +
                "    \"id\": 200,\n" +
                "    \"name\": \"Dog\"\n" +
                "  },\n" +
                "  \"name\": \"Dog\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 200,\n" +
                "      \"name\": \"Dog\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        StringEntity httpEntity = new StringEntity(message, ContentType.APPLICATION_JSON);
        httpPost.setEntity(httpEntity);
        System.out.println("Executing request: " + httpPost.toString());
        System.out.println("Executing request headers: " + httpEntity.toString());
        System.out.println("Executing request body: \r\n" + message);

        sendHttpRequest(httpPost);

    }

    public void Delete () {
        printSeparatorLine();
        System.out.println("Simple DELETE http method\r\n");

        HttpDelete httpDelete = new HttpDelete(URL2 +"/pet/200");

        System.out.println("DELETE request :" + httpDelete.toString());
        sendHttpRequest(httpDelete);
    }

    public void httpResponses () {
        printSeparatorLine();
        System.out.println("Different answers\r\n");
        System.out.println("HTTP/1.1 200 OK\n" +
                "\n" +
                "HTTP/1.1 404 Not Found\n" +
                "\n" +
                "HTTP/1.1 301 Moved Permanently\n" +
                "Location: http://www.new-location.org\n" +
                "\n" +
                "\n" +
                "HTTP/1.1 501 Not Implemented\n" +
                "\n" +
                "HTTP/1.1 500 Internal Server Error");
    }

    public void CloseSession () {
        printSeparatorLine();
        String url = URL2 + "/user/logout";
        HttpRequestBase httpGet = new HttpGet(url);

        System.out.println("Executing request: " + httpGet.getRequestLine());

        sendHttpRequest(httpGet);
    }


    public void GetWithSession () {
        printSeparatorLine();
        HttpGet httpGet = new HttpGet(URL2 + "/pet/1");
        httpGet.setHeader("Cookie", "IDd=1");
        System.out.println(httpGet);
        sendHttpRequest(httpGet);
    }


    public void PostWithLogin () {
        printSeparatorLine();
        System.out.println("User login with get and parameters\r\n");
        HttpGet httpGet = new HttpGet(URL2 + "/user/login");

        httpGet.setHeader("username", "user1");
        httpGet.setHeader("password", "password");
        System.out.println(httpGet );
        sendHttpRequest(httpGet);
    }

    public void uploadFile(){

        printSeparatorLine();
        HttpPost httpPost = new HttpPost("https://ps.uci.edu/~franklin/doc/file_upload.html");
        File file = new File("C:\\Users\\Guillaume Gingembre\\IdeaProjects\\homework4_http\\src\\main\\resources\\file.jpg");
        String message = "This is a multipart post";
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("upfile", file, ContentType.DEFAULT_BINARY, "C:\\Users\\Guillaume Gingembre\\IdeaProjects\\homework4_http\\src\\main\\resources\\file.jpg");
        builder.addTextBody("text", message, ContentType.DEFAULT_BINARY);
//
        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);
        sendHttpRequest(httpPost);
    }

    private void printSeparatorLine() {
        System.out.println("\r\n*************************************\r\n");
    }

}
