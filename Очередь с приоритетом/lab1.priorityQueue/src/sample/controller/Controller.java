package sample.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.exceptions.NotFoundItemException;
import sample.pojo.PriorityQueue;
import sample.pojo.QueueItem;

public class Controller {
    PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfValue;

    @FXML
    private TextField tfPriority;

    @FXML
    private Button btAdd;

    @FXML
    private Button btExtract;

    @FXML
    private Button btFind;

    @FXML
    private TextArea tvElems;

    @FXML
    private TextField lblRes;


    @FXML
    void initialize() {

        btAdd.setOnAction(event -> {
            try {
                priorityQueue.insert(new QueueItem<Integer>(Integer.valueOf(tfPriority.getText()), Integer.valueOf(tfValue.getText())));
                tfPriority.setText("");
                tfValue.setText("");
                fill();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Неверные данные!");
                alert.showAndWait();
            }
        });

        btExtract.setOnAction(event -> {
            try {
                QueueItem<Integer> item = priorityQueue.extractMin();
                lblRes.setText(String.format("Извлеченный: (%d, %d)", item.getValue(), item.getPriority()));
                fill();
            } catch (NotFoundItemException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });

        btFind.setOnAction(event -> {
            try {
                QueueItem<Integer> item = priorityQueue.findMin();
                lblRes.setText(String.format("Найденный: (%d, %d)", item.getValue(), item.getPriority()));
            } catch (NotFoundItemException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
    }

    private void fill() {
        QueueItem[] arr = priorityQueue.getArray();
        tvElems.clear();
        for (QueueItem integerQueueItem : arr) {
            if (integerQueueItem != null) {
                tvElems.setText(String.format("%s(%d, %d); ", tvElems.getText(), integerQueueItem.getValue(), integerQueueItem.getPriority()));
            }
        }
    }
}
