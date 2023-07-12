package com.example.practice;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Manager extends Characters {
    static final int y = 250;
    static final int x = 55;

    public Manager() {
        super(10, x, y, Color.BLUE);
        status = "stock";
    }

    public void goShelf(Shelf shelf) {
        if (circle.getCenterX() != Shelf.x - 35 && circle.getCenterY() != shelf.getRectangle().getY() + 80)
            moveX(Shelf.x - 35);
        if (circle.getCenterX() == Shelf.x - 35) moveY((int) shelf.getRectangle().getY() + 80);
        if (circle.getCenterY() == shelf.getRectangle().getY() + 80) moveX((int) shelf.getRectangle().getX() + 90);
        if (circle.getCenterY() == shelf.getRectangle().getY() + 80 && circle.getCenterX() == shelf.getRectangle().getX() + 90) {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 1000) {
            }
            shelf.setNumber(15);
            status = "complete";
        }
    }

    public void goStock() {
        if (circle.getCenterY() != y && circle.getCenterX() != Shelf.x - 90) moveX(Shelf.x - 90);
        if (circle.getCenterX() == Shelf.x - 90) moveY(y);
        if (circle.getCenterY() == y && circle.getCenterX() != x) moveX(x);
        if (circle.getCenterY() == y && circle.getCenterX() == x) status = "stock";
    }
    public void Draw(GraphicsContext graphics) {
        graphics.setStroke(circle.getStroke());
        graphics.setLineWidth(circle.getStrokeWidth());
        graphics.setFill(circle.getFill());
        graphics.strokeOval(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(), circle.getRadius() * 2, circle.getRadius() * 2);
        graphics.fillOval(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(), circle.getRadius() * 2, circle.getRadius() * 2);
    }
}
