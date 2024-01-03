package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.TaskModel;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import view.DeleteHyperlink;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class AppController implements Initializable {

    @FXML
    private TableView<TaskModel> table;

    @FXML
    private TableColumn<TaskModel, String> taskCol;

    @FXML
    private TableColumn<TaskModel, LocalDate> startDateCol;

    @FXML
    private TableColumn<TaskModel, String> timeCol;

    @FXML
    private TableColumn<TaskModel, LocalDate> deadlineCol;

    @FXML
    private TableColumn<TaskModel, String> actionsCol;

    public static AppController appControllerInstance;

    private final String URL = "http://localhost:8080/tasks";

    public AppController() {
        appControllerInstance = this;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();
    }

    public void updateTable() {
        taskCol.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        deadlineCol.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        actionsCol.setCellValueFactory(new PropertyValueFactory<>("deleteLink"));
        actionsCol.setCellFactory(column -> new DeleteHyperlink<>());

        try {
            table.setItems(getTaskList());
        } catch (IOException | ParseException | java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<TaskModel> getTaskList() throws IOException, ParseException, java.text.ParseException {
        List<TaskModel> tasks = new ArrayList<>();

        HttpGet request = new HttpGet(URL);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();

            if(entity != null) {
                String result = EntityUtils.toString(entity);

                Gson gson = new Gson();

                JsonArray array = gson.fromJson(result, JsonArray.class);

                for(JsonElement element: array) {
                    TaskModel taskModel = gson.fromJson(element, TaskModel.class);

                    tasks.add(new TaskModel(taskModel.getId(), taskModel.getTaskName(),
                            formatDate(taskModel.getStartDate()), taskModel.getStartTime(),
                            formatDate(taskModel.getDeadline()), "Excluir"));
                }
            }
        }

        return FXCollections.observableArrayList(tasks);
    }

    public void deleteTask() {
        TaskModel data = table.getSelectionModel().getSelectedItem();

        if(data == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setHeaderText(null);
            alert.setContentText("Nenhuma linha selecionada.");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();
        } else {
            String URLGetById = String.format("%s/%d", URL, data.getId());

            HttpGet httpGet = new HttpGet(URLGetById);

            try(CloseableHttpClient httpClient = HttpClients.createDefault()) {

                CloseableHttpResponse response = httpClient.execute(httpGet);

                HttpEntity entity = response.getEntity();

                if(entity != null) {
                    String result = EntityUtils.toString(entity);

                    Gson gson = new Gson();

                    TaskModel taskModel = gson.fromJson(result, TaskModel.class);

                    HttpDelete httpDelete = new HttpDelete(URL);

                    URI uriDelete = new URIBuilder(httpDelete.getUri())
                            .addParameter("id",  Integer.toString(taskModel.getId()))
                            .build();

                    httpDelete.setUri(uriDelete);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                    alert.setTitle("Excluir tarefa");
                    alert.setHeaderText(null);
                    alert.setContentText("Deseja excluir a linha selecionada: '" + taskModel.getTaskName() + "'?");
                    alert.initStyle(StageStyle.UTILITY);

                    Optional<ButtonType> resp = alert.showAndWait();

                    if(resp.isPresent() && resp.get() == ButtonType.OK) {
                        httpClient.execute(httpDelete, new BasicHttpClientResponseHandler());

                        updateTable();
                    }
                }
            } catch(IOException | URISyntaxException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String formatDate(String strDate) throws java.text.ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);

        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public void openNewTask() throws Exception {
        Stage stage = new Stage();

        String CSSFile = getClass().getResource("/resources/styles/NewTaskView.css").toExternalForm();

        URL FXMLFile = getClass().getResource("/resources/fxml/NewTaskView.fxml");

        GridPane root = FXMLLoader.load(FXMLFile);

        Scene scene = new Scene(root, 400, 400);
        scene.getStylesheets().add(CSSFile);

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setOnCloseRequest(windowEvent -> updateTable());

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
