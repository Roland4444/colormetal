package ru.com.avs.thread;

import static ru.com.avs.util.UserUtils.waiting;

import javafx.scene.control.Label;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.com.avs.model.Scale;
import ru.com.avs.scalereader.Reader;
import ru.com.avs.scalereader.ReaderFactory;
import ru.com.avs.util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;

@Component("ScaleThread")
@Scope("prototype")
public class ScaleThread extends Thread {

    String weight = "0.0";
    private Label lblScaleWeight;
    private boolean isActive;
    private final Scale scale;

    /**
     * Constructor.
     *
     * @param lblScaleWeight Label
     * @param scale          Scale
     */
    public ScaleThread(Label lblScaleWeight, Scale scale) {
        this.lblScaleWeight = lblScaleWeight;
        this.scale = scale;
        this.isActive = true;
    }

    @Override
    public void run() {
        boolean isConnected = false;
        Reader weightReader = null;
        boolean emptyURL = true;
        try {
            weightReader = ReaderFactory.createReader(this.scale);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Add read weight from rest server*/
        while (!scale.getEthCmd().isEmpty()) {
            emptyURL = false;
            try {
                weight = getWeightFromURL(scale.getEthCmd());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Utils.onFxThread(lblScaleWeight, weight);
            waiting(1000);
        }
        while (!isConnected && isActive && weightReader != null && emptyURL) {
            try {
                weightReader.connect();
                isConnected = true;
                while (isActive) {
                    weight = weightReader.readWeight();
                    Utils.onFxThread(lblScaleWeight, weight);
                    waiting(1000);
                }
                weightReader.disconnect();
            } catch (Exception e) {
                waiting(1000);
                continue;
            }
            waiting(1000);
        }

    }

    /**
     * Getting weight.
     */
    public String getWeight() throws IOException {
        //   return new Mock().mockWeigth;
        return weight;
    }

    /**
     * Stopping thread.
     */
    public void stopThread() {
        this.isActive = false;
        do {
            if (!this.isAlive()) {
                this.interrupt();
            }
        } while (this.isAlive());
    }


    public static String getWeightFromURL(String url) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        String line = "";
        try {
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while ((line = rd.readLine()) != null) {
                return line;
            }
        } catch (ConnectException e) {
            System.out.println("connection refused : " + url);
            line = "ConErr";
        }

        return line;
    }
}
