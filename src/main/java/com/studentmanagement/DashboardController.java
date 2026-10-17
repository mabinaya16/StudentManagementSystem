package com.studentmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardController {

    private static DashboardController instance;

    @FXML
    private Label totalStudentsLabel;

    @FXML
    private Label presentStudentsLabel;

    @FXML
    private Label absentStudentsLabel;


    // ==============================
    // CONSTRUCTOR
    // ==============================

    public DashboardController() {

        instance = this;
    }


    // ==============================
    // INITIALIZE
    // ==============================

    @FXML
    public void initialize() {

        refreshDashboard();
    }


    // ==============================
    // REFRESH DASHBOARD
    // ==============================

    public static void refreshDashboard() {

        if (instance != null) {

            int total =
                    StudentDAO.getStudentCount();

            int present =
                    StudentDAO.getPresentCount();

            int absent =
                    StudentDAO.getAbsentCount();


            instance.totalStudentsLabel.setText(
                    String.valueOf(total)
            );

            instance.presentStudentsLabel.setText(
                    String.valueOf(present)
            );

            instance.absentStudentsLabel.setText(
                    String.valueOf(absent)
            );
        }
    }


    // ==============================
    // ADD STUDENT
    // ==============================

    @FXML
    public void addStudent(ActionEvent event) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/add-student.fxml"
                            )
                    );

            Parent root =
                    loader.load();

            Stage stage =
                    new Stage();

            stage.setTitle(
                    "Add Student"
            );

            stage.setScene(
                    new Scene(
                            root,
                            600,
                            500
                    )
            );

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    // ==============================
    // VIEW STUDENTS
    // ==============================

    @FXML
    public void viewStudents(ActionEvent event) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/view-students.fxml"
                            )
                    );

            Parent root =
                    loader.load();

            Stage stage =
                    new Stage();

            stage.setTitle(
                    "Student List"
            );

            stage.setScene(
                    new Scene(
                            root,
                            900,
                            600
                    )
            );

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    // ==============================
    // ATTENDANCE
    // ==============================

    @FXML
    public void attendance(ActionEvent event) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/attendance.fxml"
                            )
                    );

            Parent root =
                    loader.load();

            Stage stage =
                    new Stage();

            stage.setTitle(
                    "Student Attendance"
            );

            stage.setScene(
                    new Scene(
                            root,
                            700,
                            500
                    )
            );

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    // ==============================
    // ATTENDANCE HISTORY
    // ==============================

    @FXML
    public void attendanceHistory(
            ActionEvent event) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/attendance-history.fxml"
                            )
                    );

            Parent root =
                    loader.load();

            Stage stage =
                    new Stage();

            stage.setTitle(
                    "Attendance History"
            );

            stage.setScene(
                    new Scene(
                            root,
                            800,
                            550
                    )
            );

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    // ==============================
    // EXIT APPLICATION
    // ==============================

    @FXML
    public void exitApplication(
            ActionEvent event) {

        Stage stage =
                (Stage) totalStudentsLabel
                        .getScene()
                        .getWindow();

        stage.close();
    }
}