package com.example.touchmvp;


public class MainContract {

    /** 뷰 인터페이스 **/
    public interface View{
        //뷰에 x,y값 적용
        void setXY(float x, float y);
    }

    /** 프레젠터 인터페이스 **/
    public interface Presenter{

        void setView(MainContract.View view);

        //뷰 터치포인트 x,y변위 계산
        void cal_displacement(float press_x, float press_y, float move_x, float move_y, float view_x, float view_y);

        //조이스틱 모델 생성
        void createModel(int p_width, int p_height, int v_width, int v_height);

        //조이스틱 버튼의 인덱스와 동일한 인덱스를 가진 모델 호출
        void callModel(int index);
    }
}
