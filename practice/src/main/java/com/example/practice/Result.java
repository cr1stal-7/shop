package com.example.practice;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Result {
    private Stage stage;
    static int sum;

    public Result() {
        stage = new Stage();
        stage.setTitle("Магазин");
        stage.getIcons().add(new Image("C:\\Users\\5221\\Desktop\\Новая папка (2)\\icon.png"));
        stage.setResizable(false);
    }

    public void res() {
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        Pane root = new Pane();
        StackPane holder = new StackPane();
        Canvas canvas = new Canvas(1000, 700);
        holder.getChildren().add(canvas);
        root.getChildren().add(holder);
        holder.setStyle("-fx-background-color: grey");
        Scene scene = new Scene(root, 1000, 700);
        Text title = new Text("Результаты");
        title.setLayoutX(380);
        title.setLayoutY(130);
        title.setFont(Font.font("montserrat", FontWeight.BOLD, FontPosture.REGULAR, 40));
        Text money = new Text("Общий доход: " + sum);
        Text customer = new Text("Всего покупателей: " + Simulation.allCustomer);
        Text shelf = new Text("Число стеллажей: " + Simulation.numShelf);
        Text cash = new Text("Число касс: " + Simulation.numCashier);
        Text scan = new Text("Время сканирования товаров : " + Simulation.speedScan);
        money.setFont(Font.font("montserrat", FontWeight.BOLD, FontPosture.REGULAR, 22));
        money.setLayoutX(170);
        money.setLayoutY(200);
        customer.setFont(Font.font("montserrat", FontWeight.BOLD, FontPosture.REGULAR, 22));
        customer.setLayoutX(170);
        customer.setLayoutY(250);
        shelf.setFont(Font.font("montserrat", FontWeight.BOLD, FontPosture.REGULAR, 22));
        shelf.setLayoutX(170);
        shelf.setLayoutY(300);
        cash.setFont(Font.font("montserrat", FontWeight.BOLD, FontPosture.REGULAR, 22));
        cash.setLayoutX(170);
        cash.setLayoutY(350);
        scan.setFont(Font.font("montserrat", FontWeight.BOLD, FontPosture.REGULAR, 22));
        scan.setLayoutX(170);
        scan.setLayoutY(400);
        root.getChildren().addAll(title, money, customer, shelf, cash, scan);
        stage.setScene(scene);
        stage.show();
    }
}

