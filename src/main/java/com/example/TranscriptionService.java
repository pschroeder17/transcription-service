package com.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Service for handling audio file transcription via the OpenAI API.
 */
public class TranscriptionService {

    private static final String API_URL = "https://api.openai.com/v1/audio/transcriptions";

    /**
     * Transcribes the given audio file using the OpenAI transcription API.
     *
     * @param file     the audio file to be transcribed
     * @param metadata a map containing metadata about the file
     * @return the transcription result as a string
     */
    public String transcribeFile(File file, Map<String, Object> metadata) {
        // Configure request timeouts
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(2 * 60 * 1000)
                .setConnectTimeout(2 * 60 * 1000)
                .setConnectionRequestTimeout(2 * 60 * 1000)
                .build();

        // Create an HTTP client with the specified request configuration
        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {
            HttpPost uploadFile = new HttpPost(API_URL);

            // Check for the API key in the environment variables
            if (Main.OPENAI_API_KEY == null) {
                throw new IllegalStateException("Environment variable 'API_KEY' is not set.");
            }

            // Set the authorization header with the API key
            uploadFile.setHeader("Authorization", "Bearer " + Main.OPENAI_API_KEY);

            // Build the multipart entity for the file and metadata
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", file, ContentType.APPLICATION_OCTET_STREAM, file.getName());
            builder.addTextBody("model", "whisper-1", ContentType.TEXT_PLAIN);

            // Convert metadata map to JSON string and add it to the multipart entity
            Gson gson = new Gson();
            String metadataJson = gson.toJson(metadata);
            builder.addTextBody("metadata", metadataJson, ContentType.APPLICATION_JSON);

            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);

            // Execute the HTTP request and handle the response
            try (CloseableHttpResponse response = httpClient.execute(uploadFile)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    HttpEntity responseEntity = response.getEntity();
                    String jsonResponse = EntityUtils.toString(responseEntity);

                    JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
                    if (jsonObject.has("text")) {
                        return jsonObject.get("text").getAsString();
                    } else {
                        return "Error: Transcription key not found in response.";
                    }
                } else {
                    return "Error: HTTP request failed with status code " + statusCode;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();          
        }
    }
}
