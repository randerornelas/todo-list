package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewTaskController {

    @FXML
    private TextField taskField;

    @FXML
    private DatePicker startDateField;

    @FXML
    private TextField timeField;

    @FXML
    private DatePicker deadlineField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    public void saveNewTask() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if(taskField.toString().isEmpty() || startDateField.getValue() == null ||
                timeField.toString().isEmpty() || deadlineField.getValue() == null) {

            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();

        } else if(deadlineField.getValue().isBefore(startDateField.getValue())) {
            alert.setHeaderText(null);
            alert.setContentText("A data final não deve ser anterior à data inicial.");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();

        } else {
            String URL = "http://localhost:8080/tasks";

            HttpPost httpPost = new HttpPost(URL);

            List<NameValuePair> taskList = new ArrayList<>();
            taskList.add(new BasicNameValuePair("taskName", taskField.getText()));
            taskList.add(new BasicNameValuePair("startDate", startDateField.getValue().toString()));
            taskList.add(new BasicNameValuePair("startTime", timeField.getText()));
            taskList.add(new BasicNameValuePair("deadline", deadlineField.getValue().toString()));

            httpPost.setEntity(new UrlEncodedFormEntity(taskList));

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                httpClient.execute(httpPost, new BasicHttpClientResponseHandler());
            }

            Stage stage = (Stage) saveButton.getScene().getWindow();

            stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

            stage.close();
        }
    }

    public void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();

        stage.close();
    }
}
