package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class AppView extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        String CSSFile = getClass().getResource("/resources/styles/AppView.css").toExternalForm();

        URL FXMLfile = getClass().getResource("/resources/fxml/AppView.fxml");
        BorderPane root = FXMLLoader.load(FXMLfile);

        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().add(CSSFile);

        stage.setTitle("Lista de Tarefas");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
