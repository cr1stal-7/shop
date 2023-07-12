package com.example.practice;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Customer extends Characters {
    static final int x = 980;
    static final int y = 200;
    private int money;
    private int numCheckout;
    private int numQueue;
    private int quantity;
    private ArrayList<String> productList;

    public Customer() {
        super(10, x, y, Color.RED);
        status = " ";
        Random random = new Random();
        money = random.nextInt(500, 1500);
        quantity = 0;
        int sizeProductList = random.nextInt(1, 5);
        productList = new ArrayList<String>();
        for (int i = 0; i != sizeProductList; i++) productList.add(Shelf.type[random.nextInt(5)]);
    }

    public int getMoney() {
        return money;
    }

    public int getSizeProductList() {
        return productList.size();
    }

    public String getProductList(int i) {
        return productList.get(i);
    }

    public void setNumQueue(int numQueue) {
        this.numQueue = numQueue;
    }

    public int getNumQueue() {
        return numQueue;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setNumCheckout(int numCheckout) {
        this.numCheckout = numCheckout;
    }

    public int getNumCheckout() {
        return numCheckout;
    }

    public void goShelf(Shelf shelf) {
        if (circle.getCenterX() != Shelf.x + 325 && circle.getCenterY() != shelf.getRectangle().getY() + 80)
            moveX(Shelf.x + 325);
        if (circle.getCenterX() == Shelf.x + 325)
            moveY((int) shelf.getRectangle().getY() + 80);
        if (circle.getCenterY() == shelf.getRectangle().getY() + 80) moveX((int) shelf.getRectangle().getX() + 210);
        if (circle.getCenterY() == shelf.getRectangle().getY() + 80 && circle.getCenterX() == shelf.getRectangle().getX() + 210)
            if (shelf.getNumber() > 0)
                if (shelf.getPrice() <= money) {
                    long start = System.currentTimeMillis();
                    while (System.currentTimeMillis() - start < 1000) {
                    }
                    shelf.setNumber(shelf.getNumber() - 1);
                    Result.sum = Result.sum + shelf.getPrice();
                    money = money - shelf.getPrice();
                    quantity = quantity + 1;
                    if (productList.size() != 0 && productList.get(0).equals(shelf.getName())) productList.remove(0);
                } else {
                    long start = System.currentTimeMillis();
                    while (System.currentTimeMillis() - start < 1000) {
                    }
                    if (productList.size() != 0 && productList.get(0).equals(shelf.getName())) productList.remove(0);
                }
    }

    public void buy(Cashier cashier) {
        if (circle.getCenterX() != Shelf.x + 435 && circle.getCenterY() != cashier.getCircle().getCenterY())
            moveX(Shelf.x + 435);
        if (circle.getCenterX() == Shelf.x + 435 && circle.getCenterY() != cashier.getCircle().getCenterY())
            moveY((int) cashier.getCircle().getCenterY());
        if (circle.getCenterY() == cashier.getCircle().getCenterY())
            moveX((int) cashier.getCircle().getCenterX() - 90 - 40 * (cashier.getQueue()));
        if (circle.getCenterY() == cashier.getCircle().getCenterY() && circle.getCenterX() == cashier.getCircle().getCenterX() - 90 - 40 * (cashier.getQueue())) {
            numQueue = cashier.getQueue();
            cashier.setQueue(cashier.getQueue() + 1);
            status = "q";
        }
    }

    public void Product(ArrayList<Shelf> shelf) {
        if (productList.size() == 0) {
            if (quantity == 0) status = "exit";
            else status = "buy";
        } else {
            String product = productList.get(0);
            int i = 0;
            while (i != shelf.size() && !shelf.get(i).getName().equals(product)) i++;
            if (i != shelf.size() && shelf.get(i).getName().equals(product)) {
                goShelf(shelf.get(i));
            }
            if (i == shelf.size()) productList.remove(0);
        }
    }

    public void Draw(GraphicsContext graphics) {
        graphics.setStroke(circle.getStroke());
        graphics.setLineWidth(circle.getStrokeWidth());
        graphics.setFill(circle.getFill());
        graphics.strokeOval(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(), circle.getRadius() * 2, circle.getRadius() * 2);
        graphics.fillOval(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(), circle.getRadius() * 2, circle.getRadius() * 2);
    }
}