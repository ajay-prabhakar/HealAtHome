package com.example.firebase_auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadURL {

    public String readUrl(String myUrl) throws IOException {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(myUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            //read the data from the url

            inputStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            //read each line one by one

            String line = ""; //stores each line
            //append it to the String buffer

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            //convert string buffer to string and store it in data

            data = sb.toString();

            br.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //something you want to execute even if there is some exception comes in finally block

            //always gets executed

            inputStream.close();
            urlConnection.disconnect();
        }

        return data;

        //data returned from web will be in json format ;
        //get this data using http url connection
    }
}
