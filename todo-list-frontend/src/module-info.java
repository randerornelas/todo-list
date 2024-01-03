module todoListFrontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;

    opens model;
    opens view;
    opens controller;
}
