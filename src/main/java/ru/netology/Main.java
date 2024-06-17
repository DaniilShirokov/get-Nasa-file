package ru.netology;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws ParseException {
        CloseableHttpClient httpClient = connect();
        HttpGet request = new HttpGet("https://api.nasa.gov/planetary/apod?api_key=73ydee006z64kvDENOqzLtlIwtUtDApexj5DNPjQ");
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseEssence = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println(responseEssence);
            ObjectResponseNASA objectResponseNASA = createObjectResonseNASA(responseEssence);
            String testUrl = "https://808.media/wp-content/uploads/2021/07/pole-windows-xp-1-scaled.jpg";
            getPicture(objectResponseNASA.getUrl());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static CloseableHttpClient connect() {
        CloseableHttpClient newhttpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        return newhttpClient;
    }

    public static ObjectResponseNASA createObjectResonseNASA(String responseEssence) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(responseEssence.toString());
        ObjectResponseNASA newOjectResponseNASAinput = new ObjectResponseNASA(
                (String) jsonObject.get("copyright"),
                (String) jsonObject.get("url"),
                (String) jsonObject.get("title"),
                (String) jsonObject.get("service_version"),
                (String) jsonObject.get("media_type"),
                (String) jsonObject.get("hdurl"),
                (String) jsonObject.get("explanation"),
                (String) jsonObject.get("date")
        );
        return newOjectResponseNASAinput;
    }

    public static void getPicture(String url) {
        try {
            URL newUrl = new URL(url);
            String[] parts = newUrl.getFile().split("/");
            String destinationFile = parts[parts.length - 1];
            if (destinationFile.indexOf(".jpg") != -1) {
                HttpURLConnection httpConn = (HttpURLConnection) newUrl.openConnection();
                int responseCode = httpConn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpConn.getInputStream();
                    BufferedImage image = ImageIO.read(inputStream);
                    File outputFile = new File(destinationFile);
                    ImageIO.write(image, "jpg", outputFile);

                    System.out.println("Image downloaded and saved to " + destinationFile);
                } else {
                    System.out.println("No image found at specified URL");
                }
            } else{
                System.out.println("Something went wrong, please check the correctness of the URL link " + newUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}