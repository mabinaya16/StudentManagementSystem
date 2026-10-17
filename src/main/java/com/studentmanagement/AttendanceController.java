package com.studentmanagement;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AttendanceController {

    @FXML
    private TableView<Student> attendanceTable;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, String> rollColumn;

    @FXML
    private TableColumn<Student, Boolean> presentColumn;


    // ==========================================
    // LOAD STUDENTS
    // ==========================================

    @FXML
    public void initialize() {

        // Student Name column
        nameColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getName()
                        )
        );


        // Roll Number column
        rollColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getRollNumber()
                        )
        );


        // Present column
        presentColumn.setCellValueFactory(
                data ->
                        data.getValue().presentProperty()
        );


        // Checkbox
        presentColumn.setCellFactory(
                column ->
                        new TableCell<Student, Boolean>() {

                            private final CheckBox checkBox =
                                    new CheckBox();

                            {
                                checkBox.setOnAction(event -> {

                                    Student student =
                                            getTableView()
                                                    .getItems()
                                                    .get(getIndex());

                                    student.setPresent(
                                            checkBox.isSelected()
                                    );
                                });
                            }


                            @Override
                            protected void updateItem(
                                    Boolean item,
                                    boolean empty) {

                                super.updateItem(
                                        item,
                                        empty
                                );

                                if (empty) {

                                    setGraphic(null);

                                } else {

                                    checkBox.setSelected(
                                            item != null && item
                                    );

                                    setGraphic(checkBox);
                                }
                            }
                        }
        );


        // Load students from database
        attendanceTable.setItems(
                FXCollections.observableArrayList(
                        StudentDAO.getAllStudents()
                )
        );
    }


    // ==========================================
    // SAVE ATTENDANCE
    // ==========================================

    @FXML
    private void saveAttendance() {

        boolean allSaved = true;


        for (Student student :
                attendanceTable.getItems()) {

            boolean saved =
                    StudentDAO.saveAttendance(
                            student.getId(),
                            student.isPresent()
                    );

            if (!saved) {

                allSaved = false;
            }
        }


        // Show result
        Alert alert;


        if (allSaved) {

            alert =
                    new Alert(
                            Alert.AlertType.INFORMATION
                    );

            alert.setTitle("Attendance");

            alert.setHeaderText(
                    "Attendance Saved Successfully"
            );

            alert.setContentText(
                    "Today's attendance has been saved."
            );

        } else {

            alert =
                    new Alert(
                            Alert.AlertType.ERROR
                    );

            alert.setTitle("Database Error");

            alert.setHeaderText(
                    "Unable to save attendance"
            );

            alert.setContentText(
                    "Please check the database connection."
            );
        }


        alert.showAndWait();


        // Refresh dashboard and close window
        if (allSaved) {

            DashboardController.refreshDashboard();

            Stage stage =
                    (Stage) attendanceTable
                            .getScene()
                            .getWindow();

            stage.close();
        }
    }
}