package com.example.fx.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.fx.DBConn;
import com.example.fx.MainApp;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private VBox linkList;

    @FXML
    private TextField longLink;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField shortLink;

    DBConn dbConn = new DBConn();

    private void addLink() {

        longLink.setStyle("-fx-border-color: #ffffff");
        shortLink.setStyle("-fx-border-color: #ffffff");
        messageLabel.setText("");

        String longL = longLink.getText();
        String shortL = shortLink.getText();

        if (longL.equals("") || shortL.equals("")){

            messageLabel.setText("Одно из полей пусто!");
            longLink.setStyle("-fx-border-color: #ff0000");
            shortLink.setStyle("-fx-border-color: #ff0000");

        }
        else if (dbConn.isExistLink(shortL)) {

            messageLabel.setText("Короткая ссылка уже есть!");
            shortLink.setStyle("-fx-border-color: #ff0000");

        }
        else{

            dbConn.addLink(longL,shortL);
            longLink.setText("");
            shortLink.setText("");
            messageLabel.setText("Ссылка была добавлена!");

        }

    }

    private void showLinks()  throws SQLException, IOException {

        linkList.getChildren().clear();

        ResultSet resultSet = dbConn.getLinks();

        while (resultSet.next()){

            Node node = FXMLLoader.load(Objects.requireNonNull(MainApp.class.getResource("link-view.fxml")));

            Label link = (Label) node.lookup("#linkLabel");
            link.setText(resultSet.getString("shortL"));

            node.setOnMouseEntered(event->{
                node.setStyle("-fx-background-color: #434343");
            });

            node.setOnMouseExited(event->{
                node.setStyle("-fx-background-color: #343434");
            });

            linkList.getChildren().add(node);
            linkList.setSpacing(5);

        }

    }

    @FXML
    void initialize() throws SQLException, IOException {

        showLinks();

        addButton.setOnAction(event->{
            addLink();
            try {
                showLinks();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

}
