package com.example.touchmvp;

import android.graphics.Point;

import java.util.ArrayList;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private ArrayList<JoyStick> joyStick_list = new ArrayList();

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    //뷰 이동 거리 계산
    // x,y 이동거리 = (이동한 x,y지점) - (최초누른 x,y지점)
    @Override
    public void cal_distance(float press_x, float press_y, float move_x, float move_y, float view_x, float view_y) {
        float distance_x = move_x - press_x;
        float distance_y = move_y - press_y;
        float new_x = view_x + distance_x;
        float new_y = view_y + distance_y;

        view.setXY(new_x, new_y);
    }

    // 0 ~ 8까지의 인덱스로 모델 생성
    @Override
    public void createModel(int p_width, int p_height, int v_width, int v_height) {

        for(int i =0; i<9; i++){
            Point joystick_xy = cal_XY(p_width, p_height, v_width, v_height, i);
            joyStick_list.add(new JoyStick(joystick_xy.x, joystick_xy.y));
        }
    }

    //해당 버튼의 인덱스와 같은 인덱스를 지닌 모델 x,y값 호출
    @Override
    public void callModel(int index) {
        float x = joyStick_list.get(index).getX();
        float y = joyStick_list.get(index).getY();
        view.setXY(x,y);
    }

    //버튼
    private Point cal_XY(int p_width, int p_height, int v_width, int v_height, int index){
        Point point = new Point();
        if(index == 0) //좌상단
        {
            point.x = 0;
            point.y = 0;
        }
        else if(index == 1) // 상단
        {
            point.x = (p_width/2)-(v_width/2);
            point.y = 0;
        }
        else if(index == 2) // 우상단
        {
            point.x = p_width-v_width;
            point.y = 0;
        }
        else if(index == 3) // 좌
        {
            point.x = 0;
            point.y = (p_height/2)-(v_height/2);
        }
        else if(index ==4) // 중앙
        {
            point.x = (p_width/2)-(v_width/2);
            point.y = (p_height/2)-(v_height/2);
        }
        else if(index == 5) // 우
        {
            point.x = p_width-v_width;
            point.y = (p_height/2)-(v_height/2);
        }
        else if(index == 6) // 좌하단
        {
            point.x = 0;
            point.y = p_height-v_height;
        }
        else if(index == 7) // 하단
        {
            point.x = (p_width/2)-(v_width/2);
            point.y = p_height-v_height;
        }
        else if(index == 8) // 우하단
        {
            point.x = p_width-v_width;
            point.y = p_height-v_height;
        }

        return point;
    }
}
