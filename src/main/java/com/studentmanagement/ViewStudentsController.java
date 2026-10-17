package com.studentmanagement;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Optional;

public class ViewStudentsController {

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, String> rollColumn;

    @FXML
    private TableColumn<Student, String> departmentColumn;

    @FXML
    private TableColumn<Student, String> emailColumn;

    @FXML
    private TableColumn<Student, Void> actionColumn;

    @FXML
    private TextField searchField;


    // ==========================================
    // INITIALIZE
    // ==========================================

    @FXML
    public void initialize() {

        nameColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getName()
                        )
        );

        rollColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getRollNumber()
                        )
        );

        departmentColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getDepartment()
                        )
        );

        emailColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getEmail()
                        )
        );


        // Edit + Delete buttons
        actionColumn.setCellFactory(
                column ->
                        new TableCell<Student, Void>() {

                            private final Button editButton =
                                    new Button("Edit");

                            private final Button deleteButton =
                                    new Button("Delete");

                            {
                                editButton.setOnAction(event -> {

                                    Student student =
                                            getTableView()
                                                    .getItems()
                                                    .get(getIndex());

                                    editStudent(student);
                                });


                                deleteButton.setOnAction(event -> {

                                    Student student =
                                            getTableView()
                                                    .getItems()
                                                    .get(getIndex());

                                    deleteStudent(student);
                                });
                            }


                            @Override
                            protected void updateItem(
                                    Void item,
                                    boolean empty) {

                                super.updateItem(
                                        item,
                                        empty
                                );

                                if (empty) {

                                    setGraphic(null);

                                } else {

                                    javafx.scene.layout.HBox box =
                                            new javafx.scene.layout.HBox(8);

                                    box.getChildren().addAll(
                                            editButton,
                                            deleteButton
                                    );

                                    setGraphic(box);
                                }
                            }
                        }
        );


        loadStudents();
    }


    // ==========================================
    // LOAD ALL STUDENTS
    // ==========================================

    private void loadStudents() {

        studentTable.setItems(
                FXCollections.observableArrayList(
                        StudentDAO.getAllStudents()
                )
        );
    }


    // ==========================================
    // SEARCH STUDENT
    // ==========================================

    @FXML
    private void searchStudent() {

        String keyword =
                searchField.getText().trim();

        if (keyword.isEmpty()) {

            loadStudents();

            return;
        }

        studentTable.setItems(
                FXCollections.observableArrayList(
                        StudentDAO.searchStudents(keyword)
                )
        );
    }


    // ==========================================
    // SHOW ALL STUDENTS
    // ==========================================

    @FXML
    private void showAllStudents() {

        searchField.clear();

        loadStudents();
    }


    // ==========================================
    // EDIT STUDENT
    // ==========================================

    private void editStudent(Student student) {

        TextInputDialog nameDialog =
                new TextInputDialog(
                        student.getName()
                );

        nameDialog.setTitle("Edit Student");
        nameDialog.setHeaderText(
                "Enter new student name"
        );

        Optional<String> nameResult =
                nameDialog.showAndWait();

        if (nameResult.isEmpty()) {
            return;
        }


        TextInputDialog rollDialog =
                new TextInputDialog(
                        student.getRollNumber()
                );

        rollDialog.setTitle("Edit Student");
        rollDialog.setHeaderText(
                "Enter new roll number"
        );

        Optional<String> rollResult =
                rollDialog.showAndWait();

        if (rollResult.isEmpty()) {
            return;
        }


        TextInputDialog departmentDialog =
                new TextInputDialog(
                        student.getDepartment()
                );

        departmentDialog.setTitle("Edit Student");
        departmentDialog.setHeaderText(
                "Enter new department"
        );

        Optional<String> departmentResult =
                departmentDialog.showAndWait();

        if (departmentResult.isEmpty()) {
            return;
        }


        TextInputDialog emailDialog =
                new TextInputDialog(
                        student.getEmail()
                );

        emailDialog.setTitle("Edit Student");
        emailDialog.setHeaderText(
                "Enter new email"
        );

        Optional<String> emailResult =
                emailDialog.showAndWait();

        if (emailResult.isEmpty()) {
            return;
        }


        boolean updated =
                StudentDAO.updateStudent(
                        student.getId(),
                        nameResult.get().trim(),
                        rollResult.get().trim(),
                        departmentResult.get().trim(),
                        emailResult.get().trim()
                );


        if (updated) {

            Alert alert =
                    new Alert(
                            Alert.AlertType.INFORMATION
                    );

            alert.setTitle("Success");

            alert.setHeaderText(
                    "Student Updated Successfully"
            );

            alert.showAndWait();

            loadStudents();

            DashboardController.refreshDashboard();

        } else {

            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR
                    );

            alert.setTitle("Error");

            alert.setHeaderText(
                    "Unable to update student"
            );

            alert.setContentText(
                    "Roll number may already exist."
            );

            alert.showAndWait();
        }
    }


    // ==========================================
    // DELETE STUDENT
    // ==========================================

    private void deleteStudent(Student student) {

        Alert confirm =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirm.setTitle("Delete Student");

        confirm.setHeaderText(
                "Delete this student?"
        );

        confirm.setContentText(
                "Student: "
                        + student.getName()
                        + "\nRoll Number: "
                        + student.getRollNumber()
        );


        Optional<ButtonType> result =
                confirm.showAndWait();


        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            boolean deleted =
                    StudentDAO.deleteStudent(
                            student.getId()
                    );


            if (deleted) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.INFORMATION
                        );

                alert.setTitle("Success");

                alert.setHeaderText(
                        "Student Deleted Successfully"
                );

                alert.showAndWait();

                loadStudents();

                DashboardController.refreshDashboard();

            } else {

                Alert alert =
                        new Alert(
                                Alert.AlertType.ERROR
                        );

                alert.setTitle("Error");

                alert.setHeaderText(
                        "Unable to delete student"
                );

                alert.setContentText(
                        "This student may have attendance records."
                );

                alert.showAndWait();
            }
        }
    }


    // ==========================================
    // CLOSE WINDOW
    // ==========================================

    @FXML
    private void closeWindow() {

        Stage stage =
                (Stage) studentTable
                        .getScene()
                        .getWindow();

        stage.close();
    }
}