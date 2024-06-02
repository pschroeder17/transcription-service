package com.example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for handling the transcription functionality.
 */
public class TranscriptionController {

    @FXML
    private ImageView headsetIcon;

    @FXML
    private Button uploadButton;

    @FXML
    private Button sendButton;

    @FXML
    private Label selectedFileLabel;

    @FXML
    private TextArea transcriptionTextArea;

    @FXML
    private ProgressBar progressBar;

    private File selectedFile;
    private TranscriptionService transcriptionService;

    /**
     * Initializes the controller class.
     * Sets up the transcription service and loads the headset icon.
     */
    @FXML
    public void initialize() {
        transcriptionService = new TranscriptionService();
        headsetIcon.setImage(new Image(getClass().getResourceAsStream("/headset.png")));
    }

    /**
     * Handles the file upload action.
     * Opens a file chooser to select an audio file and enables the send button if a valid file is selected.
     */
    @FXML
    private void handleUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.aac"));
        selectedFile = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
        if (selectedFile != null && isAudioFile(selectedFile)) {
            sendButton.setDisable(false);
            selectedFileLabel.setText("Selected File: " + selectedFile.getName());
        } else {
            showErrorMessage("Invalid file type. Please select a valid audio file.");
        }
    }

    /**
     * Handles the send action.
     * Disables the send button, shows the progress bar, and starts the transcription process in a new thread.
     */
    @FXML
    private void handleSend() {
        if (selectedFile != null) {
            sendButton.setDisable(true);
            progressBar.setVisible(true);
            transcriptionTextArea.clear();

            new Thread(() -> {
                // Create metadata map with file information
                Map<String, Object> metadata = new HashMap<>();
                metadata.put("filename", selectedFile.getName());
                metadata.put("size", selectedFile.length());

                // Transcribe the file and update the UI with the result
                String result = transcriptionService.transcribeFile(selectedFile, metadata);
                Platform.runLater(() -> {
                    transcriptionTextArea.setText(result);
                    progressBar.setVisible(false);
                    sendButton.setDisable(false);
                });
            }).start();
        }
    }

    /**
     * Checks if the selected file is an audio file.
     * @param file The file to check.
     * @return true if the file is an audio file, false otherwise.
     */
    private boolean isAudioFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".mp3") || fileName.endsWith(".wav") || fileName.endsWith(".aac");
    }

    /**
     * Shows an error message on the UI.
     * @param message The error message to display.
     */
    private void showErrorMessage(String message) {
        Platform.runLater(() -> {
            // Display error message (you can use an alert dialog, status bar, etc.)
            System.err.println(message);
        });
    }
}
