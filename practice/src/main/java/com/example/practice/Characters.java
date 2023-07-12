package com.example.practice;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

abstract class Characters {
    protected Circle circle;
    protected int speed;
    protected String status;
    static final int r = 15;

    public Characters(int Speed, int x, int y, Color color) {
        speed = Speed;
        circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(r);
        circle.setFill(color);
    }

    public Circle getCircle() {
        return circle;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public int getSpeed() {
        return speed;
    }

    public void moveX(int x) {
        if (x < circle.getCenterX()) {
            circle.setCenterX(circle.getCenterX() - 1);
        }
        if (x > circle.getCenterX()) {
            circle.setCenterX(circle.getCenterX() + 1);
        }
    }

    public void moveY(int y) {
        if (y < circle.getCenterY()) {
            circle.setCenterY(circle.getCenterY() - 1);
        }
        if (y > circle.getCenterY()) {
            circle.setCenterY(circle.getCenterY() + 1);
        }
    }
}
