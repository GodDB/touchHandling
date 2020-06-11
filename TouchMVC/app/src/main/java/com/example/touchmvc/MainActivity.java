package com.example.touchmvc;

import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;

/** 컨트롤러 **/
public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    //터치 뷰
    private View touch_view;

    //터치 뷰의 부모
    private ConstraintLayout parentView;

    //터치 포인트 press 지점
    private float press_x;
    private float press_y;

    //JoyStick list
    ArrayList<JoyStick> joyStick_list = new ArrayList();

    /** 뷰 초기화 **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentView = findViewById(R.id.touchZone);

        touch_view = findViewById(R.id.touch_view);
        touch_view.setOnTouchListener(this);

        //버튼 초기화
        init_btn();

    }

    /** 모델 데이터 계산 및 모델 리스트에 add **/
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //터치뷰의 부모의 크기 높이, 터치뷰의 크기 높이
        float p_width = parentView.getWidth();
        float p_height = parentView.getHeight();
        float v_width = touch_view.getWidth();
        float v_height = touch_view.getHeight();

        for(int i=0; i<9; i++){

            //모델 데이터 계산
            float[] joystick_xy = cal_XY(p_width, p_height, v_width, v_height, i);

            //조이스틱 버튼과 대응되는 모델 리스트 ( 0번째 버튼 - 0번째 모델, 1번째 버튼 - 1번째 모델 ... )
            joyStick_list.add(new JoyStick(joystick_xy[0], joystick_xy[1]));
        }
    }

    /** 터치 이벤트 핸들러
       - 이벤트 발생 시 x,y변위를 계산하여 뷰의 위치 갱신 **/
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        // x,y 변위 = (터치포인트 move x,y지점) - (터치포인트 press x,y지점)
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
            press_x = event.getX();
            press_y = event.getY();
        }
        if(event.getActionMasked() == MotionEvent.ACTION_MOVE){
            //x,y변위
            float dis_x = (event.getX()-press_x);
            float dis_y = (event.getY()-press_y);

            //터치 뷰의 x,y + x,y변위
            v.setX(v.getX()+dis_x);
            v.setY(v.getY()+dis_y);
        }
        return true;
    }

    /** 클릭 이벤트 핸들러
     - 이벤트 발생 시 해당 버튼의 인덱스로 모델 값 호출 **/
    @Override
    public void onClick(View v) {
        //joystick 버튼의 부모
        GridLayout gridLayout = (GridLayout)v.getParent();

        //누른 joystick 버튼의 인덱스(0~8)
        int index = gridLayout.indexOfChild(v);

        //버튼 인덱스와 같은 인덱스인 모델 호출
        float x = joyStick_list.get(index).getX();
        float y = joyStick_list.get(index).getY();

        touch_view.setX(x);
        touch_view.setY(y);
    }

    /** 모델 데이터 계산
     -  터치 뷰는 부모 뷰의 좌측 상단(0,0)을 기준으로 뷰를 그리므로 그걸 고려하여 계산함
     **/
    private float[] cal_XY(float p_width, float p_height, float v_width, float v_height, int index){

        // [0] == x , [1] == y
        float[] xy = new float[2];

        switch (index){
            case 0 :
                xy[0] = 0;
                xy[1] = 0;
                break;
            case 1 :
                xy[0] = (p_width/2)-(v_width/2);
                xy[1] = 0;
                break;
            case 2 :
                xy[0] = p_width-v_width;
                xy[1] = 0;
                break;
            case 3 :
                xy[0] = 0;
                xy[1] = (p_height/2)-(v_height/2);
                break;
            case 4 :
                xy[0] = (p_width/2)-(v_width/2);
                xy[1] = (p_height/2)-(v_height/2);
                break;
            case 5 :
                xy[0] = p_width-v_width;
                xy[1] = (p_height/2)-(v_height/2);
                break;
            case 6 :
                xy[0] = 0;
                xy[1] = p_height-v_height;
                break;
            case 7 :
                xy[0] = (p_width/2)-(v_width/2);
                xy[1] = p_height-v_height;
                break;
            case 8 :
                xy[0] = p_width-v_width;
                xy[1] = p_height-v_height;
                break;
        }
        return xy;
    }

    /** 버튼에 리스너 장착 **/
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
