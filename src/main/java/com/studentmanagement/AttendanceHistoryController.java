package com.studentmanagement;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AttendanceHistoryController {

    @FXML
    private TableView<String[]> historyTable;

    @FXML
    private TableColumn<String[], String> nameColumn;

    @FXML
    private TableColumn<String[], String> rollColumn;

    @FXML
    private TableColumn<String[], String> dateColumn;

    @FXML
    private TableColumn<String[], String> statusColumn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label messageLabel;


    @FXML
    public void initialize() {

        nameColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue()[0]
                        )
        );

        rollColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue()[1]
                        )
        );

        dateColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue()[2]
                        )
        );

        statusColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue()[3]
                        )
        );

        loadAllHistory();

        messageLabel.setText(
                "Showing all attendance records"
        );
    }


    // ==============================
    // LOAD ALL HISTORY
    // ==============================

    private void loadAllHistory() {

        historyTable.setItems(
                FXCollections.observableArrayList(
                        StudentDAO.getAttendanceHistory()
                )
        );
    }


    // ==============================
    // SEARCH BY DATE
    // ==============================

    @FXML
    private void searchByDate() {

        if (datePicker.getValue() == null) {

            messageLabel.setText(
                    "Please select a date."
            );

            loadAllHistory();

            return;
        }

        String selectedDate =
                datePicker.getValue().toString();

        historyTable.setItems(
                FXCollections.observableArrayList(
                        StudentDAO
                                .getAttendanceHistoryByDate(
                                        selectedDate
                                )
                )
        );

        if (historyTable.getItems().isEmpty()) {

            messageLabel.setText(
                    "No attendance found for "
                            + selectedDate
            );

        } else {

            messageLabel.setText(
                    "Attendance for "
                            + selectedDate
            );
        }
    }


    // ==============================
    // SHOW ALL
    // ==============================

    @FXML
    private void showAllHistory() {

        datePicker.setValue(null);

        loadAllHistory();

        messageLabel.setText(
                "Showing all attendance records"
        );
    }


    // ==============================
    // CLOSE
    // ==============================

    @FXML
    private void closeWindow() {

        Stage stage =
                (Stage) historyTable
                        .getScene()
                        .getWindow();

        stage.close();
    }
}