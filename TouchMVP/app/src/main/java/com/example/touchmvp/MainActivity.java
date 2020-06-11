package com.example.touchmvp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

/** 뷰 **/
public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, MainContract.View {

    //프레젠터
    private MainContract.Presenter presenter;

    //터치 뷰
    private View touch_view;

    //터치 뷰의 부모
    private ConstraintLayout parentView;

    //터치 포인트 press 지점
    private float press_x = 0;
    private float press_y = 0;

    /** 뷰 초기화 **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter();
        presenter.setView(this);

        //touch_View의 부모 뷰 초기화
        parentView = findViewById(R.id.touchZone);

        //touch view 초기화
        touch_view = findViewById(R.id.touch_view);
        touch_view.setOnTouchListener(this);

        //버튼 초기화
        init_btn();
    }

    /** 프레젠터에 모델 생성 요청 **/
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //터치뷰의 부모의 크기 높이, 터치뷰의 크기 높이
        float p_width = parentView.getWidth();
        float p_height = parentView.getHeight();
        float v_width = touch_view.getWidth();
        float v_height = touch_view.getHeight();

        //모델 생성 요청 to presenter
        presenter.createModel(p_width, p_height, v_width, v_height);
    }

    /** 터치 이벤트 핸들러
     - 이벤트 발생 시 프레젠터에 x,y변위 계산 요청 **/
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
            press_x = event.getX();
            press_y = event.getY();
        }
        if(event.getActionMasked() == MotionEvent.ACTION_MOVE){
            float move_x = event.getX();
            float move_y = event.getY();

            //프레젠터에 x,y변위 계산 요청
            presenter.cal_displacement(press_x, press_y, move_x, move_y, v.getX(), v.getY());
        }
        return true;
    }

    /** 클릭 이벤트 핸들러
     - 이벤트 발생 시 프레젠터에 버튼의 인덱스값 전달 **/
    @Override
    public void onClick(View v) {
        //joystick 버튼의 부모
        GridLayout gridLayout = (GridLayout)v.getParent();

        //누른 joystick 버튼의 인덱스(0~8)
        int index = gridLayout.indexOfChild(v);

        //동일한 인덱스의 모델 호출
        presenter.callModel(index);
    }

    /** 터치 뷰에 x,y값 적용 **/
    @Override
    public void setXY(float x, float y) {
        touch_view.setX(x);
        touch_view.setY(y);
    }

    /** 버튼에 listener 장착 **/
    private void init_btn(){
        findViewById(R.id.leftTop_btn).setOnClickListener(this);
        findViewById(R.id.top_btn).setOnClickListener(this);
        findViewById(R.id.rightTop_btn).setOnClickListener(this);
        findViewById(R.id.left_btn).setOnClickListener(this);
        findViewById(R.id.center_btn).setOnClickListener(this);
        findViewById(R.id.right_btn).setOnClickListener(this);
        findViewById(R.id.leftBottom_btn).setOnClickListener(this);
        findViewById(R.id.bottom_btn).setOnClickListener(this);
        findViewById(R.id.rightBottom_btn).setOnClickListener(this);
    }
}
