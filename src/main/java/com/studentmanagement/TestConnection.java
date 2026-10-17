package com.studentmanagement;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        try {

            Connection connection =
                    DatabaseConnection.getConnection();

            System.out.println("MySQL Connected Successfully!");

            connection.close();

        } catch (Exception e) {

            System.out.println("MySQL Connection Failed!");

            e.printStackTrace();
        }
    }
}