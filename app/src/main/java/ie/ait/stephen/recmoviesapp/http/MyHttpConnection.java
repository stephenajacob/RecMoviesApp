package ie.ait.stephen.recmoviesapp.http;

/**
 * Created by stephen on 3/25/2016.
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MyHttpConnection {
    public String postToUrl(String jsonURL, String jsonPostData) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpsURLConnection urlConnection = null;
        try {
            System.out.println("INSIDE TRY");
            URL url = new URL(jsonURL);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            System.out.println("AFTER CONNECT");

            // Send post request
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(jsonPostData);
            wr.flush();
            wr.close();

            System.out.println("AFTER CLOSE");
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            System.out.println("DATA: " + data);
                    br.close();
        } catch (Exception e) {
            System.out.println("Exception" + e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
