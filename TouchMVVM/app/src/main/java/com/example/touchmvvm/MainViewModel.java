package com.example.touchmvvm;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableFloat;
import java.util.ArrayList;

/** 뷰 모델 **/
public class MainViewModel extends BaseObservable {
    // 터치 뷰와 바인딩 되어 있음
    public ObservableFloat x = new ObservableFloat();
    public ObservableFloat y = new ObservableFloat();

    //모델 리스트
    private ArrayList<JoyStick> joyStick_list = new ArrayList();

    /** 모델 생성 (0 ~ 8) **/
    public void createModel(float p_width, float p_height, float v_width, float v_height){

        for(int i=0; i<9; i++){

            //각 버튼에 맞는 모델 데이터 계산
            float[] joystick_xy = cal_XY(p_width, p_height, v_width, v_height, i);

            //모델 생성
            joyStick_list.add(new JoyStick(joystick_xy[0], joystick_xy[1]));
        }
    }


    /** 터치 포인트 x,y변위 계산 **/
    public void cal_displacement(float press_x, float press_y, float move_x, float move_y) {

        // x,y 변위 = (터치포인트 move x,y지점) - (터치포인트 press x,y지점)
        float dis_x = move_x - press_x;
        float dis_y = move_y - press_y;

        // 뷰의 x,y지점 + x,y 변위
        float new_x = x.get() + dis_x;
        float new_y = y.get() + dis_y;

        // 터치 뷰와 바인딩 되어 있는 x,y변수에 set -> notification
        x.set(new_x);
        y.set(new_y);
    }

    /** 조이스틱 버튼 클릭 시 해당 버튼의 인덱스값 전달 **/
    public void onClick(int index){

        //joystick_list에서 인덱스로 값을 가져옴
        float model_x = joyStick_list.get(index).getX();
        float model_y = joyStick_list.get(index).getY();

        // 터치 뷰와 바인딩 되어 있는 x,y변수에 set -> notification
        x.set(model_x);
        y.set(model_y);
    }


    /** 모델 데이터 계산
     - 터치 뷰의 좌측 상단(0,0)을 기준으로 뷰를 그리므로 그걸 고려하여 계산함
     **/
    private float[] cal_XY(float p_width, float p_height, float v_width, float v_height, float index){
        float[] xy = new float[2]; // [0] == x , [1] == y

        if(index == 0) //좌상단
        {
            xy[0] = -((p_width/2) - (v_width/2));
            xy[1] = -((p_height/2) - (v_height/2));
        }
        else if(index == 1) // 상단
        {
            xy[0] = 0;
            xy[1] = -((p_height/2) - (v_height/2));
        }
        else if(index == 2) // 우상단
        {
            xy[0] = (p_width/2)+(v_width/2)-v_width;
            xy[1] = -((p_height/2) - (v_height/2));
        }
        else if(index == 3) // 좌
        {
            xy[0] = -((p_width/2) - (v_width/2));
            xy[1] = 0;
        }
        else if(index ==4) // 중앙
        {
            xy[0] = 0;
            xy[1] = 0;
        }
        else if(index == 5) // 우
        {
            xy[0] = (p_width/2)+(v_width/2)-v_width;
            xy[1] = 0;
        }
        else if(index == 6) // 좌하단
        {
            xy[0] = -((p_width/2) - (v_width/2));
            xy[1] = (p_height/2) - (v_height/2);
        }
        else if(index == 7) // 하단
        {
            xy[0] = 0;
            xy[1] = (p_height/2) - (v_height/2);
        }
        else if(index == 8) // 우하단
        {
            xy[0] = (p_width/2)+(v_width/2)-v_width;
            xy[1] = (p_height/2) - (v_height/2);
        }

        return xy;
    }

}
