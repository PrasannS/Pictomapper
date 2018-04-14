package com.apps.android.prasannsinghal.pictomapper;

import android.util.Log;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Prasann Singhal on 4/7/2018.
 */

public class WikiDataExtractionSvc {

    public static String getHttpsContent(String urlName) {

        String line;
        String result="";

        //String s1 = IPSTicket;
        //String s3 = IPSUserName;
        //String b1 = new String(s1.getBytes(Charset.defaultCharset()));
        //String b3 = new String(s3.getBytes(Charset.defaultCharset()));

        try {

            StringBuilder httpsContent = new StringBuilder();
            URL url = new URL(urlName);
            HttpsURLConnection httpsConnection = (HttpsURLConnection) url.openConnection();
            httpsConnection.setConnectTimeout(5000);
            //httpsConnection.setRequestProperty("IPSWSTicket", b1);
            //httpsConnection.addRequestProperty("IPSUserName", b3);

            httpsConnection.setHostnameVerifier(new AllowAllHostnameVerifier());

            int responseCode = httpsConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                InputStream inputStream = httpsConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    httpsContent.append(line);
                    Log.d("HTTPS", line);
                }
                httpsConnection.disconnect();
            }
            result = httpsContent.toString();
        }
        catch(Exception ex){
            Log.d("HTTPS", ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }

    public static String getHttpContent(String urlName) {

        String line;
        String result="";
        //String s1 = IPSTicket;
        //String s3 = IPSUserName;
        //String b1 = new String(s1.getBytes(Charset.defaultCharset()));
        //String b3 = new String(s3.getBytes(Charset.defaultCharset()));
        try {
            StringBuilder httpsContent = new StringBuilder();
            URL url = new URL(urlName);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            //urlConnection.setRequestProperty("IPSWSTicket", b1);
            //urlConnection.addRequestProperty("IPSUserName", b3);
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    httpsContent.append(line);
                    Log.d("HTTP", line);
                }
                urlConnection.disconnect();
            }
            result = httpsContent.toString();
        }
        catch(Exception ex){
            Log.d("HTTP", ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }

    public static MonumentModel getMonumentSummary(SearchResultModel srm){

        String url01 = "https://en.wikipedia.org/api/rest_v1/page/summary/"+WikiSearchTermToFullUrlSvc.getURLPartFromPageId(srm);
        URL url = null;
        String jSONResponse ="";
        MonumentModel m = null;

        try {
            url = new URL(url01);

            Long now_before = System.currentTimeMillis();
            Log.d("getMonumentSummary", now_before.toString());
            String s2 = getHttpsContent(url01);

            Long now_after = System.currentTimeMillis();
            Log.d("getMonumentSummary", now_after.toString());

            jSONResponse = s2;
            if (jSONResponse!=null && jSONResponse.length()>0){
                m = new MonumentModel(jSONResponse);
            }


        } catch (Exception e) {
            Log.d("getMonumentSummary",e.getMessage());
        }
        finally {

        }

        return m;
    }
}
