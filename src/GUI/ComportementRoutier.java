package GUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class ComportementRoutier {
    private JButton simulerButton;
    private JButton recupererButton;
    private JButton snapshotButton;
    private JComboBox CB_Type;
    private JComboBox CB_Filtre;
    private JPanel PannelAff;
    private JPanel panel1;
    private JTextField tftimestamps;
    private TimeSeriesCollection dataset;



    public ComportementRoutier() {


        String [] filteName = {"Accéléromètre","Gyroscope"};
        DefaultComboBoxModel comboBoxModel2 = new DefaultComboBoxModel(filteName);
        CB_Filtre.setModel(comboBoxModel2);

        PannelAff();

        recupererButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selectedFiltre=(String) CB_Filtre.getSelectedItem();
                int timestamps = Integer.parseInt(tftimestamps.getText());

                if(timestamps > 3583378)
                {
                    JOptionPane.showMessageDialog(null,"Veuillez entré un timestamp correct ","Erreur",JOptionPane.ERROR_MESSAGE);

                }

                if (selectedFiltre == null) {
                    JOptionPane.showMessageDialog(null,"Veuillez selectionner un filtre  ","Erreur",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(selectedFiltre.equals("Accéléromètre"))
                {
                    //Construction de url
                    String url = "http://192.168.226.153:8080/ords/hr/getdonnee/Param/"+timestamps; // mettre suite

                    try {
                        //Envoi requete
                        URL object = new URL(url);
                        HttpURLConnection connection = (HttpURLConnection) object.openConnection();
                        connection.setRequestMethod("GET");
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            System.out.println(inputLine);
                            response.append(inputLine);

                        }
                        in.close();

                        //Traitement donner
                        JSONObject json = new JSONObject(response.toString());
                        dataset.removeAllSeries();
                        //Utilisation des donnees pour le graph
                        TimeSeries series = new TimeSeries("X");
                        TimeSeries series2 = new TimeSeries("Y");
                        TimeSeries series3 = new TimeSeries("Z");

                        JSONArray jsonArray = json.getJSONArray("items");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            series.addOrUpdate(new Millisecond(i*20, timestamps % 60, 0, 0, 1, 1, 2023), js.getDouble("accx"));
                            series2.addOrUpdate(new Millisecond(i*20, timestamps % 60, 0, 0, 1, 1, 2023), js.getDouble("accy"));
                            series3.addOrUpdate(new Millisecond(i*20, timestamps % 60, 0, 0, 1, 1, 2023), js.getDouble("accz"));
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            System.out.println("Objet JSON " + i + ": " + js.toString());

                        }


                        dataset.addSeries(series);
                        dataset.addSeries(series2);
                        dataset.addSeries(series3);

                        PannelAff.repaint();

                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ProtocolException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
                else if(selectedFiltre.equals("Gyroscope"))
                {
                    //Construction de url
                    String url = "http://192.168.226.153:8080/ords/hr/getdonnee/Param/"+timestamps;
                    try
                    {
                        //Envoi requete
                        URL object = new URL(url);
                        HttpURLConnection connection = (HttpURLConnection) object.openConnection();
                        connection.setRequestMethod("GET");
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine())!= null)
                        {
                            System.out.println(inputLine);
                            response.append(inputLine);

                        }
                        in.close();

                        //Traitement donner
                        JSONObject json = new JSONObject(response.toString());
                        dataset.removeAllSeries();
                        //Utilisation des donnees pour le graph
                        TimeSeries series = new TimeSeries("X");
                        TimeSeries series2 = new TimeSeries("Y");
                        TimeSeries series3 = new TimeSeries("Z");

                        JSONArray jsonArray = json.getJSONArray("items");

                        for(int i = 0;i<20;i++)
                        {
                            JSONObject js = jsonArray.getJSONObject(i);
                            series.addOrUpdate(new Millisecond(i*20,timestamps%60,0,0,1,1,2023),js.getDouble("gyrox"));
                            series2.addOrUpdate(new Millisecond(i*20,timestamps%60,0,0,1,1,2023),js.getDouble("gyroy"));
                            series3.addOrUpdate(new Millisecond(i*20,timestamps%60,0,0,1,1,2023),js.getDouble("gyroz"));
                        }
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            System.out.println("Objet JSON " + i + ": " + js.toString());

                        }


                        dataset.addSeries(series);
                        dataset.addSeries(series2);
                        dataset.addSeries(series3);

                        PannelAff.repaint();

                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
        simulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Thread thread = new Thread() {

                    public void run()
                    {
                        String selectedFiltre=(String) CB_Filtre.getSelectedItem();
                        int timestamps = Integer.parseInt(tftimestamps.getText());

                        if(timestamps > 3583378)
                        {
                            JOptionPane.showMessageDialog(null,"Veuillez entré un timestamp correct ","Erreur",JOptionPane.ERROR_MESSAGE);

                        }

                        if (selectedFiltre == null) {
                            JOptionPane.showMessageDialog(null,"Veuillez selectionner un filtre et un type ","Erreur",JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if(selectedFiltre.equals("Accéléromètre"))
                        {
                            //Construction de url
                            String url = "http://192.168.226.153:8080/ords/hr/getdonnee/Param/"+timestamps;

                            try {
                                URL object = new URL(url);
                                HttpURLConnection connection = (HttpURLConnection) object.openConnection();
                                connection.setRequestMethod("GET");

                                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                String inputLine;
                                StringBuffer response = new StringBuffer();

                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();

                                JSONObject json = new JSONObject(response.toString());
                                dataset.removeAllSeries();

                                JSONArray jsonArray = json.getJSONArray("items");
                                ArrayList<reveler> revelerList = new ArrayList<>();

                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject js = jsonArray.getJSONObject(i);
                                    reveler data = new reveler(
                                            js.getDouble("accx"),
                                            js.getDouble("accy"),
                                            js.getDouble("accz"),
                                            js.getString("classname"),
                                            js.getInt("timestamptime"),
                                            js.getDouble("gyrox"),
                                            js.getDouble("gyroy"),
                                            js.getDouble("gyroz")
                                    );
                                    revelerList.add(data);
                                    System.out.println("Objet JSON " + i + ": " + js.toString());


                                }

                                    TimeSeries nseries = new TimeSeries("X");
                                    TimeSeries nseries2 = new TimeSeries("Y");
                                    TimeSeries nseries3 = new TimeSeries("Z");

                                    // Affiche les prochaines données en fonction de la position actuelle
                                    for (int i =0;i<20 ; i++) {
                                        reveler reveler = revelerList.get(i);
                                        nseries.addOrUpdate(new Millisecond(500* (i%2), reveler.getTimestampd() % 60, 0, 0, 1, 1, 2023), reveler.getAccx());
                                        nseries2.addOrUpdate(new Millisecond(500* (i%2), reveler.getTimestampd() % 60, 0, 0, 1, 1, 2023), reveler.getAccy());
                                        nseries3.addOrUpdate(new Millisecond(500* (i%2), reveler.getTimestampd() % 60, 0, 0, 1, 1, 2023), reveler.getAccz());
                                    }

                                    // Ajouter les nouvelles séries au dataset
                                    dataset.addSeries(nseries);
                                    dataset.addSeries(nseries2);
                                    dataset.addSeries(nseries3);

                                    // Repaint pour afficher les nouvelles données
                                    PannelAff.repaint();

                                    int demi;
                                    int last= 0;

                                    for (int i=20;i<revelerList.size();i++)
                                    {
                                        nseries.delete(0,0);
                                        nseries2.delete(0,0);
                                        nseries3.delete(0,0);


                                        reveler reveler = revelerList.get(i);

                                        demi = (reveler.getTimestampd()== last) ? 1 : 0;
                                        last = reveler.getTimestampd();

                                        nseries.addOrUpdate(new Millisecond(500* demi, reveler.getTimestampd() % 60, 0, 0, 1, 1, 2023), reveler.getAccx());
                                        nseries2.addOrUpdate(new Millisecond(500* demi, reveler.getTimestampd() % 60, 0, 0, 1, 1, 2023), reveler.getAccy());
                                        nseries3.addOrUpdate(new Millisecond(500* demi, reveler.getTimestampd() % 60, 0, 0, 1, 1, 2023), reveler.getAccz());


                                        // Repaint pour afficher les nouvelles données
                                        PannelAff.repaint();
                                        try {
                                            Thread.sleep(500);
                                        } catch (InterruptedException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }




                            } catch (MalformedURLException ex) {
                                throw new RuntimeException(ex);
                            } catch (ProtocolException ex) {
                                throw new RuntimeException(ex);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }

                        }else if(selectedFiltre.equals("Gyroscope"))
                        {
                            //Construction de url
                            String url = "http://192.168.226.153:8080/ords/hr/getdonnee/Param/"+timestamps;

                            try {
                                URL object = new URL(url);
                                HttpURLConnection connection = (HttpURLConnection) object.openConnection();
                                connection.setRequestMethod("GET");

                                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                String inputLine;
                                StringBuffer response = new StringBuffer();

                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();

                                JSONObject json = new JSONObject(response.toString());
                                dataset.removeAllSeries();

                                JSONArray jsonArray = json.getJSONArray("items");
                                ArrayList<reveler> revelerList = new ArrayList<>();

                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject js = jsonArray.getJSONObject(i);
                                    reveler data = new reveler(
                                            js.getDouble("accx"),
                                            js.getDouble("accy"),
                                            js.getDouble("accz"),
                                            js.getString("classname"),
                                            js.getInt("timestamptime"),
                                            js.getDouble("gyrox"),
                                            js.getDouble("gyroy"),
                                            js.getDouble("gyroz")
                                    );
                                    revelerList.add(data);
                                    System.out.println("Objet JSON " + i + ": " + js.toString());

                                }

                                TimeSeries nseries = new TimeSeries("X");
                                TimeSeries nseries2 = new TimeSeries("Y");
                                TimeSeries nseries3 = new TimeSeries("Z");

                                // Affiche les prochaines données en fonction de la position actuelle
                                for (int i =0;i<20; i++) {
                                    reveler reveler = revelerList.get(i);
                                    nseries.addOrUpdate(new Millisecond(500* (i%2), reveler.getTimestampd() % 60, 0, 0, 1, 1, 2023), reveler.getGyrox());
                                    nseries2.addOrUpdate(new Millisecond(500* (i%2), reveler.getTimestampd() % 60, 0, 0, 1, 1, 2023), reveler.getGyroy());
                                    nseries3.addOrUpdate(new Millisecond(500* (i%2), reveler.getTimestampd() % 60, 0, 0, 1, 1, 2023), reveler.getGyroz());
                                }

                                // Ajouter les nouvelles séries au dataset
                                dataset.addSeries(nseries);
                                dataset.addSeries(nseries2);
                                dataset.addSeries(nseries3);

                                // Repaint pour afficher les nouvelles données
                                PannelAff.repaint();

                                int demi;
                                int last= 0;

                                for (int i=20;i<revelerList.size();i++)
                                {
                                    nseries.delete(0,0);
                                    nseries2.delete(0,0);
                                    nseries3.delete(0,0);


                                    reveler reveler = revelerList.get(i);

                                    demi = (reveler.getTimestampd()== last) ? 1 : 0;
                                    last = reveler.getTimestampd();

                                    nseries.addOrUpdate(new Millisecond(500* demi, reveler.getTimestampd() % 60, 0, 0, 1, 1, 2023), reveler.getGyrox());
                                    nseries2.addOrUpdate(new Millisecond(500* demi, reveler.getTimestampd() % 60, 0, 0, 1, 1, 2023), reveler.getGyroy());
                                    nseries3.addOrUpdate(new Millisecond(500* demi, reveler.getTimestampd() % 60, 0, 0, 1, 1, 2023), reveler.getGyroz());


                                    // Repaint pour afficher les nouvelles données
                                    PannelAff.repaint();
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }

                            } catch (MalformedURLException ex) {
                                throw new RuntimeException(ex);
                            } catch (ProtocolException ex) {
                                throw new RuntimeException(ex);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }

                        }
                    }
                };
                thread.start();
            }
        });

        snapshotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void PannelAff()
    {
        String nom = (String) CB_Filtre.getSelectedItem();
        TimeSeries series = new TimeSeries(nom);
        dataset = new TimeSeriesCollection(series);
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Données du capteur","Temps","Valeur",dataset);
        ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.validate();
        PannelAff.setLayout(new BorderLayout());
        PannelAff.removeAll();
        PannelAff.add(chartPanel,BorderLayout.CENTER);
        PannelAff.validate();
        PannelAff.setVisible(true);
    }


    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Comportement routier");
        frame.setSize(800,550);
        ComportementRoutier app = new ComportementRoutier();
        frame.setContentPane(app.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        //frame.pack();
        frame.setVisible(true);
    }



}
