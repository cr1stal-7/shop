package com.example.practice;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Shelf {
    static final String[] type = new String[]{"Хлебобулочные изделия", "Хозяйственные товары", "Молочные продукты", "Колбасные изделия", "Заморожненные продукты"};
    private String name;
    private int number;
    private int price;
    static int quantity;
    static final int x = 300;
    private Rectangle rectangle;

    public Shelf() {
        name = type[quantity];
        number = 15;
        price = (50 * quantity + 100);
        rectangle = new Rectangle();
        rectangle.setX(x);
        rectangle.setY(quantity * 120 + 50);
        rectangle.setWidth(300);
        rectangle.setHeight(60);
        rectangle.setFill(Color.AQUA);
        rectangle.setStrokeWidth(1);
        rectangle.setStroke(Color.BLACK);
        quantity++;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}

