package com.example.practice;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.Scene;

public class Main extends Application {
    private Button start;
    private ChoiceBox<Integer> choiceBoxCashier;
    private ChoiceBox<Integer> choiceBoxShelf;
    private ChoiceBox<Integer> choiceBoxspeedScan;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        StackPane holder = new StackPane();
        Canvas canvas = new Canvas(1000,  700);
        holder.getChildren().add(canvas);
        root.getChildren().add(holder);
        holder.setStyle("-fx-background-color: grey");
        Scene scene = new Scene(root,1000,700);
        stage.setResizable(false);
        createText(root);
        createChoiceElement(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image("C:\\Users\\5221\\Desktop\\Новая папка (2)\\icon.png"));
        stage.setTitle("Магазин");
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        start.setOnAction(e -> {
            stage.hide();
            Simulation simulation = new Simulation(choiceBoxCashier.getValue(), choiceBoxShelf.getValue(), choiceBoxspeedScan.getValue());
            simulation.startSimulation();
        });
        stage.show();
    }

    public void createText(Pane root) {
        Text title = new Text("Симуляция работы магазина");
        Text numberCashier = new Text("Выберите количество касс");
        Text numberShelf = new Text("Выберите количество стеллажей");
        Text spaceCustomer = new Text("Выберите время сканирования товаров");
        title.setFont(Font.font("montserrat", FontWeight.BOLD, FontPosture.REGULAR, 40));
        title.setLayoutX(240);
        title.setLayoutY(200);
        numberCashier.setFont(Font.font("montserrat", FontWeight.BOLD, FontPosture.REGULAR, 22));
        numberCashier.setLayoutX(170);
        numberCashier.setLayoutY(300);
        numberShelf.setFont(Font.font("montserrat", FontWeight.BOLD, FontPosture.REGULAR, 22));
        numberShelf.setLayoutX(170);
        numberShelf.setLayoutY(350);
        spaceCustomer.setFont(Font.font("montserrat", FontWeight.BOLD, FontPosture.REGULAR, 22));
        spaceCustomer.setLayoutX(170);
        spaceCustomer.setLayoutY(400);
        root.getChildren().addAll(title, numberShelf, numberCashier, spaceCustomer);
    }
    public void createChoiceElement(Pane root) {
        start = new Button("Начать симуляцию");
        start.setFont(Font.font("montserrat", FontWeight.BOLD, FontPosture.REGULAR, 22));
        start.setLayoutX(370);
        start.setLayoutY(450);
        start.setPrefSize(300, 50);
        ObservableList<Integer> choiceCashier = FXCollections.observableArrayList(1, 2);
        ObservableList<Integer> choiceShelf = FXCollections.observableArrayList(2, 3, 4, 5);
        ObservableList<Integer> choicespeedScan = FXCollections.observableArrayList(1, 2, 3, 4);
        choiceBoxCashier = new ChoiceBox<Integer>(choiceCashier);
        choiceBoxCashier.setLayoutX(670);
        choiceBoxCashier.setLayoutY(280);
        choiceBoxCashier.setValue(1);
        choiceBoxShelf = new ChoiceBox<Integer>(choiceShelf);
        choiceBoxShelf.setLayoutX(670);
        choiceBoxShelf.setLayoutY(330);
        choiceBoxShelf.setValue(2);
        choiceBoxspeedScan = new ChoiceBox<Integer>(choicespeedScan);
        choiceBoxspeedScan.setLayoutX(670);
        choiceBoxspeedScan.setLayoutY(380);
        choiceBoxspeedScan.setValue(1);
        root.getChildren().addAll(start, choiceBoxCashier, choiceBoxShelf, choiceBoxspeedScan);
    }
}