package RecupDonnee;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import org.json.*;


public class GetSet {


    static void testGET()
    {
        String line;
        StringBuilder result = new StringBuilder();
        int id = 3582212;
        try
        {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject currentObject = itemsArray.getJSONObject(i);
                Iterator<String> keys = currentObject.keys();

                while (keys.hasNext()) {
                    String key = keys.next();
                    System.out.println("          " + key + " : " + currentObject.get(key));
                }

                System.out.println();
            }

            /*URL url = new URL("http://192.168.226.144:8080/ords/projetfinale/ProjetSGBD/"+id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();


            //On utilise le verbe GET pour une recherche par ID
            con.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((line = rd.readLine()) != null)
            {
                result.append(line);
                System.out.println(line);
            }
            rd.close();

            // Maintenant, vous pouvez extraire les valeurs associées aux clés
            double accx = con.getDouble("accx");
            double accy = con.getDouble("accy");
            double accz = con.getDouble("accz");
            double gyrox = con.getDouble("gyrox");
            double gyroy = con.getDouble("gyroy");
            double gyroz = con.getDouble("gyroz");
            String classe = con.getString("classe");
            long timestamps = con.getLong("timestamps");

            // Faites ce que vous voulez avec les données extraites
            System.out.println("accx: " + accx);
            System.out.println("accy: " + accy);
            System.out.println("accz: " + accz);
            System.out.println("gyrox: " + gyrox);
            System.out.println("gyroy: " + gyroy);
            System.out.println("gyroz: " + gyroz);
            System.out.println("classe: " + classe);
            System.out.println("timestamps: " + timestamps);

            System.out.println("Le JSON des détails:" + result);
        }catch (Exception e)
        {
            System.out.println("Error in  getting details : " + e);
        }

    }
   public  static String testPOST() {
        String line;
       // StringBuilder result = new StringBuilder();
        //TEST AVEC DES PARAMETRES DANS LE BODY
        StringBuilder result2 = new StringBuilder();
        try {
            JSONObject obj = new JSONObject();
            String urlParameters = obj.toString();
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            //System.out.println(postData);
            int postDataLength = postData.length;

            URL url = new URL("http://192.168.226.144:8080/ords/projetfinale/ProjetSGBD");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST"); //Verbe POST
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = rd.readLine()) != null) {
                result2.append(line);
                System.out.println(line);
            }
            rd.close();

            System.out.println("Le JSON des détails:" + result2);

            //Parsing dans le JSON reçu
            try {
                JSONArray received = new JSONArray(result2.toString());

                for (int i = 0; i < received.length(); i++) {
                    JSONObject currentObject = received.getJSONObject(i);
                    // Récupère les valeurs spécifiques que vous souhaitez analyser
                    double accx = currentObject.getDouble("accx");
                    double accy = currentObject.getDouble("accy");
                    double accz = currentObject.getDouble("accz");
                    double gyrox = currentObject.getDouble("gyrox");
                    double gyroy = currentObject.getDouble("gyroy");
                    double gyroz = currentObject.getDouble("gyroz");
                    String classe = currentObject.getString("classe");
                    long timestamps = currentObject.getLong("timestamps");

                    // Faites ce que vous voulez avec les données extraites
                    System.out.println("accx: " + accx);
                    System.out.println("accy: " + accy);
                    System.out.println("accz: " + accz);
                    System.out.println("gyrox: " + gyrox);
                    System.out.println("gyroy: " + gyroy);
                    System.out.println("gyroz: " + gyroz);
                    System.out.println("classe: " + classe);
                    System.out.println("timestamps: " + timestamps);
                }
            } catch (Exception e) {
                System.out.println("Erreur lors de l'analyse JSON : " + e);
            }

            /*for(int i = 0; i < received.length(); i++)
            {
                JSONObject currentObject = received.getJSONObject(i);
                Iterator it = currentObject.keys();
                while(it.hasNext())
                {
                    String key = (String)it.next();
                    System.out.println(key + " : " + currentObject.get(key));
                }
                System.out.println("");
            }

        catch (Exception e) {
            System.out.println("Error in  getting details : " + e);
        }

        return result2.toString();
    }



    public static void main(String[] args) {
        //Envoi d'une requête à la DB par REST
        //testGET();
        testPOST();

    }

}*/

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Iterator;

public class GetSet {
    public static void main(String[] args) {
        int id = 3582129;
        String result = EnvoieGET.sendGETRequest(id);
        System.out.println(result);

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject currentObject = itemsArray.getJSONObject(i);
                Iterator<String> keys = currentObject.keys();

                while (keys.hasNext()) {
                    String key = keys.next();
                    //System.out.println("          " + key + " : " + currentObject.get(key));
                    if (key.equals("accx")) {
                        double accx = currentObject.getDouble(key);
                        System.out.println("accx : " + accx);
                    } else if (key.equals("accy")) {
                        double accy = currentObject.getDouble(key);
                        System.out.println("accy : " + accy);
                    } else if (key.equals("accz")) {
                        double accz = currentObject.getDouble(key);
                        System.out.println("accz : " + accz);
                    } else if (key.equals("gyrox")) {
                        double gyrox = currentObject.getDouble(key);
                        System.out.println("gyrox : " + gyrox);
                    }else if (key.equals("gyroy")) {
                        double gyroy = currentObject.getDouble(key);
                        System.out.println("gyroy : " + gyroy);
                    }else if (key.equals("gyroz")) {
                        double gyroz = currentObject.getDouble(key);
                        System.out.println("gyroz : " + gyroz);
                    }else if (key.equals("classe")) {
                        String classe = currentObject.getString(key);
                        System.out.println("classe :"+ classe);
                    }else if (key.equals("timestamps")) {
                        int timestamps = currentObject.getInt(key);
                        System.out.println("timestamps :"+ timestamps);
                    }
                }

                //System.out.println();

            }
        } catch (Exception e) {
            System.out.println("Error in JSON parsing: " + e);
        }
    }
}