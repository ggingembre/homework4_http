
/**
 * Created by Guillaume Gingembre on 03/11/2017.
 */
public class Main {

    public static void main(String[] args) {

        HttpMethods httpMethods = new HttpMethods();
        httpMethods.StockQuote();
        httpMethods.Search("I want to buy a flat");
        httpMethods.Post();
        httpMethods.Delete();
        httpMethods.httpResponses();
        httpMethods.CloseSession();
        httpMethods.GetWithSession();
        httpMethods.PostWithLogin();
        httpMethods.uploadFile();

    }

}
