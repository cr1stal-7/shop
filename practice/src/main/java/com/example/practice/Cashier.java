package com.example.practice;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Cashier {
    static int quantity;
    static final int x = 930;
    static final int r = 15;
    private int queue;
    private Circle circle;

    public Cashier() {
        queue = 0;
        circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(335 + quantity * 170);
        circle.setRadius(r);
        circle.setFill(Color.WHITE);
        circle.setStrokeWidth(2);
        circle.setStroke(Color.BLACK);
        quantity++;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public int getQueue() {
        return queue;
    }

    public void sale(Customer customer) {
        long start = System.currentTimeMillis();
        long time = customer.getQuantity() * Simulation.speedScan * 1000;
        while (System.currentTimeMillis() - start < time) {
        }
        queue = queue - 1;
        customer.setStatus("Exit");
    }
}