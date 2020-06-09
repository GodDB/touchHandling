package com.example.touchmvp;

import android.view.MotionEvent;

public class MainContract {

    public interface View{
        //뷰에 x,y값 적용
        void setXY(float x, float y);
    }

    public interface Presenter{

        void setView(MainContract.View view);

        //뷰 이동 거리 계산
        void cal_distance(float press_x, float press_y, float move_x, float move_y, float view_x, float view_y);

        //조이스틱 모델 생성
        void createModel(int p_width, int p_height, int v_width, int v_height);

        //조이스틱 버튼과 대응되는 모델 호출
        void callModel(int index);
    }
}
