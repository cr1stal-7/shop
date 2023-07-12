package com.example.practice;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Simulation {
    private Stage stage;
    static int speedScan;
    static int allCustomer;
    static int numCashier;
    static int numShelf;
    private Manager manager;
    private ArrayList<Cashier> cashierList;
    private ArrayList<Shelf> shelfList;
    private ArrayList<Customer> customerList;
    private boolean stop = false;

    public Simulation(int numCashier, int numShelf, int speedScan) {
        this.numCashier = numCashier;
        this.numShelf = numShelf;
        this.speedScan = speedScan;
        cashierList = new ArrayList<Cashier>();
        shelfList = new ArrayList<Shelf>();
        customerList = new ArrayList<Customer>();
        stage = new Stage();
        stage.setTitle("Симуляция");
        stage.getIcons().add(new Image("C:\\Users\\5221\\Desktop\\Новая папка (2)\\icon.png"));
        stage.setWidth(1000);
        stage.setHeight(700);
        stage.setResizable(false);
    }

    public void startSimulation() {
        Result result = new Result();
        Pane root = new Pane();
        Canvas canvas = new Canvas(1000, 700);
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        Button comleteButton = new Button("Завершить");
        comleteButton.setLayoutX(850);
        comleteButton.setLayoutY(10);
        comleteButton.setPrefSize(100, 25);
        root.getChildren().add(comleteButton);
        Button stopButton = new Button("Остановить");
        stopButton.setLayoutX(720);
        stopButton.setLayoutY(10);
        stopButton.setPrefSize(80, 25);
        root.getChildren().add(stopButton);
        root.setOnMouseClicked(e -> {
            if (e.getX() >= 720 && e.getX() <= 800 && e.getY() >= 10 && e.getY() <= 35) {
                if (!stop) stop = true;
                else stop = false;
            }
            if (e.getX() >= 850 && e.getX() <= 950 && e.getY() >= 10 && e.getY() <= 35) {
                customerList.clear();
                stage.hide();
                result.res();
            }
        });
        manager = new Manager();
        for (int i = 0; i != numCashier; i++) {
            cashierList.add(new Cashier());
            root.getChildren().add(cashierList.get(i).getCircle());
        }
        for (int i = 0; i != numShelf; i++) {
            shelfList.add(new Shelf());
            root.getChildren().add(shelfList.get(i).getRectangle());
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (!stop) {
                    customerList.add(new Customer());
                    allCustomer++;
                    doCustomer(customerList.get(customerList.size() - 1));
                }
            }
        }, 0, 4000);
        doManager(manager);
        canvas.setOnMousePressed(e -> {
            if (stop) {
                boolean object = false;
                double mouseX = e.getX();
                double mouseY = e.getY();
                for (int i = 0; i != customerList.size(); i++) {
                    if (customerList.get(i).getCircle().contains(mouseX, mouseY)) {
                        object = true;
                        String text = new String("Количество денег: " + customerList.get(i).getMoney() + "\nКорзина:");
                        for (int j = 0; j != customerList.get(i).getSizeProductList(); j++) {
                            text = text + "\n" + customerList.get(i).getProductList(j);
                        }
                        graphics.setFill(Color.BLACK);
                        graphics.fillText(text, customerList.get(i).getCircle().getCenterX() + 20, customerList.get(i).getCircle().getCenterY() - 20);
                    }
                }
                for (int i = 0; i != shelfList.size(); i++) {
                    if (shelfList.get(i).getRectangle().contains(mouseX, mouseY)) {
                        object = true;
                        String text = new String(shelfList.get(i).getName() + "\nКол-во товаров: " + shelfList.get(i).getNumber() + "\nЦена товара: " + shelfList.get(i).getPrice());
                        graphics.setFill(Color.BLACK);
                        graphics.fillText(text, shelfList.get(i).getRectangle().getX() + 60, shelfList.get(i).getRectangle().getY() + 15);
                    }
                }
                if (!object) Draw(graphics);
            }
        });
        root.getChildren().add(canvas);
        wall(root);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Timer time = new Timer();
                time.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (customerList.size() == 0) time.cancel();
                        if (!stop) Draw(graphics);
                    }
                }, 0, 30);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public void doManager(Manager manager) {
        Timer timerConsultant = new Timer();
        TimerTask taskConsultant = new TimerTask() {
            @Override
            public void run() {
                if (!stop) {
                    int j = 0;
                    while (j != numShelf && manager.getStatus() == "stock") {
                        if (shelfList.get(j).getNumber() < 5) manager.setStatus("shelf");
                        j++;
                    }
                    switch (manager.getStatus()) {
                        case "shelf": {
                            int i = 0;
                            while (i != numShelf && shelfList.get(i).getNumber() >= 5) i++;
                            if (shelfList.get(i).getNumber() < 5) {
                                manager.goShelf(shelfList.get(i));
                            } else manager.setStatus("complete");
                            break;
                        }
                        case "complete": {
                            manager.goStock();
                            break;
                        }
                    }
                }
            }
        };
        timerConsultant.schedule(taskConsultant, 0, manager.getSpeed());
    }

    public void doCustomer(Customer customer) {
        Timer timerCustomer = new Timer();
        TimerTask taskCustomer = new TimerTask() {
            boolean move = false;

            @Override
            public void run() {
                if (!stop) {
                    switch (customer.getStatus()) {
                        case " ": {
                            if (customer.getCircle().getCenterX() != 700) customer.moveX(700);
                            if (customer.getCircle().getCenterX() == 700) customer.setStatus("s");
                            break;
                        }
                        case "s": {
                            customer.Product(shelfList);
                        }
                        break;
                        case "buy": {
                            int min_ch = cashierList.get(0).getQueue();
                            int num = 0;
                            for (int i = 1; i < numCashier; i++) {
                                if (cashierList.get(i).getQueue() < min_ch) {
                                    num = i;
                                    min_ch = cashierList.get(i).getQueue();
                                }
                            }
                            customer.buy(cashierList.get(num));
                            customer.setNumCheckout(num);
                            break;
                        }
                        case "q": {
                            if (customer.getNumQueue() == 0) {
                                cashierList.get(customer.getNumCheckout()).sale(customer);
                                if (cashierList.get(customer.getNumCheckout()).getQueue() != 0) move = true;
                            }
                            break;
                        }
                        case "exit": {
                            if (customer.getCircle().getCenterY() != Customer.y && customer.getCircle().getCenterX() != Customer.x)
                                customer.moveX(Shelf.x + 365);
                            if (customer.getCircle().getCenterX() == Shelf.x + 365 && customer.getCircle().getCenterY() != Customer.y)
                                customer.moveY(Customer.y);
                            if (customer.getCircle().getCenterY() == Customer.y && customer.getCircle().getCenterX() != Customer.x)
                                customer.moveX(Customer.x);
                            if (customer.getCircle().getCenterX() == Customer.x) {
                                customerList.remove(customer);
                            }
                            break;
                        }
                        case "Exit": {
                            if (customer.getCircle().getCenterY() != Customer.y && customer.getCircle().getCenterX() != Customer.x)
                                customer.moveY(Customer.y);
                            if (customer.getCircle().getCenterY() == Customer.y && customer.getCircle().getCenterX() != Customer.x)
                                customer.moveX(Customer.x);
                            if (customer.getCircle().getCenterX() == Customer.x) {
                                customerList.remove(customer);
                            }
                            break;
                        }
                    }
                    if (move) {
                        for (int i = 0; i != customerList.size(); i++) {
                            if (customerList.get(i).getStatus() == "q" && customerList.get(i).getCircle().getCenterY() == cashierList.get(customer.getNumCheckout()).getCircle().getCenterY()
                                    && customerList.get(i).getCircle().getCenterX() != Cashier.x - 90 - 40 * (customerList.get(i).getNumQueue() - 1))
                                if (customerList.get(i).getCircle().getCenterX() <= Cashier.x - 90)
                                    customerList.get(i).moveX(Cashier.x - 90 - 40 * (customerList.get(i).getNumQueue() - 1));
                            if (customerList.get(i).getStatus() == "q" && customerList.get(i).getCircle().getCenterY() == cashierList.get(customer.getNumCheckout()).getCircle().getCenterY()
                                    && customerList.get(i).getCircle().getCenterX() == Cashier.x - 90 - 40 * (customerList.get(i).getNumQueue() - 1)) {
                                customerList.get(i).setNumQueue(customerList.get(i).getNumQueue() - 1);
                                if (customerList.get(i).getNumQueue() == 0) move = false;
                            }
                        }
                    }
                    if (customerList.size() == 0) timerCustomer.cancel();
                }
            }
        };
        timerCustomer.schedule(taskCustomer, 0, customer.getSpeed());
    }

    public void wall(Pane root) {
        Line wallUp = new Line(10.0, 50.0, 975.0, 50.0);
        Line wallDown = new Line(10.0, 650.0, 975.0, 650.0);
        Line wallLeft = new Line(10.0, 50.0, 10.0, 650.0);
        Line wallRight_1 = new Line(975.0, 50.0, 975.0, 150.0);
        Line wallRight_2 = new Line(975.0, 250.0, 975.0, 650.0);
        Line wallStock_1 = new Line(100.0, 50.0, 100.0, 200.0);
        Line wallStock_2 = new Line(100.0, 300.0, 100.0, 650.0);
        Rectangle cash_1 = new Rectangle(860, 280, 40, 115);
        if (Cashier.quantity == 2) {
            Rectangle cash_2 = new Rectangle(860, 450, 40, 115);
            cash_2.setFill(Color.AQUA);
            cash_2.setStrokeWidth(1);
            cash_2.setStroke(Color.BLACK);
            root.getChildren().add(cash_2);
        }
        wallUp.setStrokeWidth(3);
        wallDown.setStrokeWidth(3);
        wallLeft.setStrokeWidth(3);
        wallRight_1.setStrokeWidth(3);
        wallRight_2.setStrokeWidth(3);
        wallStock_1.setStrokeWidth(3);
        wallStock_2.setStrokeWidth(3);
        cash_1.setFill(Color.AQUA);
        cash_1.setStrokeWidth(1);
        cash_1.setStroke(Color.BLACK);
        root.getChildren().addAll(wallUp, wallDown, wallLeft, wallRight_1, wallRight_2, wallStock_1, wallStock_2, cash_1);
    }

    public void Draw(GraphicsContext graphics) {
        int i;
        graphics.clearRect(0, 0, graphics.getCanvas().getWidth(), graphics.getCanvas().getHeight());
        manager.Draw(graphics);
        for (i = 0; i != customerList.size(); i++) customerList.get(i).Draw(graphics);
    }
}