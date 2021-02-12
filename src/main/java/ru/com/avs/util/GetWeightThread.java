package ru.com.avs.util;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.time.Duration;

public class GetWeightThread extends Thread {
    public String url;
    private String weight = "0.0";

    @Override
    public void run() {
        weight = getWeightfromURL(url);
    }

    public GetWeightThread(String url) {
        this.url = url;
    }


    public String getWeightfromURL(String url) {
        int timeout = 5;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build();
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            return rd.readLine();
        } catch (ConnectException e) {
            System.out.println("connection refused : " + url);
            return "ConErr";
        } catch (ConnectTimeoutException e) {
            System.out.println("connection timeout : " + url);
            return "TimeOut";
        } catch (ClientProtocolException e) {
            System.out.println("protocol exception : " + url);
            return "PErr";
        } catch (IOException e) {
            System.out.println("IOException : " + url);
            return "IOErr";
        }
    }

    public String getWeight() {
        return weight;
    }
}
