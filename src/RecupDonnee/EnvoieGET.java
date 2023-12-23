package RecupDonnee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EnvoieGET {
    public static String sendGETRequest(int id) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL("http://192.168.226.150:8080/ords/hr/getdonne/EntreeTime?timestamptime=" + id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            rd.close();

        } catch (Exception e) {
            System.out.println("Error in getting details : " + e);
        }

        return result.toString();
    }



}

