# Transcription Service Project

## Übersicht

Dieses Projekt implementiert einen Transkriptionsservice mit einer grafischen Benutzeroberfläche, der es Benutzern ermöglicht, Audiodateien hochzuladen und eine Transkription der hochgeladenen Dateien zu erhalten.

## Voraussetzungen

- Java 11 oder höher
- Maven
- OpenAI API Key
- Visual Studio Code (oder eine andere IDE mit Java-Unterstützung)

## Abhängigkeiten

Das Projekt verwendet folgende externe Bibliotheken, die in der `pom.xml` definiert sind:

- `org.apache.httpcomponents:httpclient:4.5.13`
- `com.google.code.gson:gson:2.8.6`
- `org.openjfx:javafx-controls:17`
- `org.openjfx:javafx-fxml:17`

Diese werden automatisch von Maven verwaltet.

## Projekt einrichten

1. **Repository klonen**:
    ```sh
    git clone https://github.com/username/repository.git
    cd repository
    ```

2. **Launch-Konfiguration anlegen**:

    Um das Projekt in deiner Entwicklungsumgebung (z.B. Visual Studio Code) ausführen zu können, musst du eine `launch.json` Datei manuell erstellen. Diese Datei sollte sich im `.vscode` Verzeichnis deines Projektordners befinden.

    Erstelle die Datei `launch.json` mit folgendem Inhalt:

    ```json
    {
        "version": "0.2.0",
        "configurations": [
            {
                "type": "java",
                "name": "Current File",
                "request": "launch",
                "mainClass": "${file}",
                "args": [
                    "{Your OpenAI API KEY}"
                ]
            },
            {
                "type": "java",
                "name": "Main",
                "request": "launch",
                "mainClass": "com.example.Main",
                "args": [
                    "{Your OpenAI API KEY}"
                ],
                "projectName": "transcriptionfx"
            }
        ]
    }
    ```

    **Hinweis**: Ersetze `{Your OpenAI API KEY}` durch deinen tatsächlichen OpenAI API Key.

## Anwendung starten

Um die Anwendung zu starten, benutze die Launch-Konfigurationen in deiner Entwicklungsumgebung.

## Projektstruktur

- `src/main/java/com/example/`: Enthält die Java-Quellcode-Dateien
- `src/main/resources/`: Enthält die FXML-Datei und andere Ressourcen
