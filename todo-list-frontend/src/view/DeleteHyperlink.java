package view;

import controller.AppController;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DeleteHyperlink<TaskModel> extends TableCell<TaskModel, String> {

    private final Hyperlink hyperlink;
    private final ImageView imageView;

    public DeleteHyperlink() {
        hyperlink = new Hyperlink();
        imageView = new ImageView();

        imageView.setFitWidth(16);
        imageView.setFitHeight(16);

        hyperlink.setOnAction(event -> AppController.appControllerInstance.deleteTask());

        Tooltip.install(hyperlink, new Tooltip("Excluir"));

        setGraphic(hyperlink);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            imageView.setImage(new Image(getClass().getResource("/resources/img/trash.png").toString()));
            hyperlink.setGraphic(imageView);
        }
    }
}
