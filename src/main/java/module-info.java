module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpmime;
    requires com.google.gson;

    opens com.example to javafx.fxml;
    exports com.example;
}
