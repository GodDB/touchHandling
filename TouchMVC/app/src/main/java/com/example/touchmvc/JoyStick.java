package com.example.touchmvc;

public class JoyStick {
    private int x;
    private int y;

    JoyStick(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
