package com.example.touchmvp;

import android.graphics.Point;

import java.util.ArrayList;

/** 프레젠터 **/
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    //모델 리스트
    private ArrayList<JoyStick> joyStick_list = new ArrayList();

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }


    /** 터치 포인트 x,y변위 계산 **/
    @Override
    public void cal_displacement(float press_x, float press_y, float move_x, float move_y, float view_x, float view_y) {

        // x,y 변위 = (터치포인트 move x,y지점) - (터치포인트 press x,y지점)
        float disp_x = move_x - press_x;
        float disp_y = move_y - press_y;

        // 뷰의 x,y지점 + x,y 변위
        float new_x = view_x + disp_x;
        float new_y = view_y + disp_y;

        //뷰에 x,y값 전달
        view.setXY(new_x, new_y);
    }


    /** 모델 생성 (0 ~ 8) **/
    @Override
    public void createModel(int p_width, int p_height, int v_width, int v_height) {

        for(int i =0; i<9; i++){
            //모델 데이터 계산
            Point joystick_xy = cal_XY(p_width, p_height, v_width, v_height, i);

            //조이스틱 버튼과 대응되는 모델 리스트 ( 0번째 버튼 - 0번째 모델, 1번째 버튼 - 1번째 모델 ... )
            joyStick_list.add(new JoyStick(joystick_xy.x, joystick_xy.y));
        }
    }


    /** 전달 받은 인덱스로 모델리스트 조회 후
     *  view에 데이터 전달 **/
    @Override
    public void callModel(int index) {

        // 전달 받은 인덱스의 모델 x,y값
        float x = joyStick_list.get(index).getX();
        float y = joyStick_list.get(index).getY();

        //뷰에 x,y값 전달
        view.setXY(x,y);
    }


    /** 모델 데이터 계산
     - 부모 뷰의 좌측 상단(0,0)을 기준으로 뷰를 그리므로 그걸 고려하여 계산함
     **/
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
