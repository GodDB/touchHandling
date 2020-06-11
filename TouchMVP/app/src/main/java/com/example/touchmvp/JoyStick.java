package com.example.touchmvp;

/** 모델 **/
public class JoyStick {
    private float x;
    private float y;

    JoyStick(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
