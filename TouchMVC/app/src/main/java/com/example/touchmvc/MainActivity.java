package com.example.touchmvc;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    //터치 뷰
    private View touch_view;

    //부모 뷰(터치영역)
    private ConstraintLayout parentView;

    //뷰 위치값
    private float f_x =0;
    private float f_y =0;

    //JoyStick list
    ArrayList<JoyStick> joyStick_list = new ArrayList();

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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        int p_width = parentView.getWidth();
        int p_height = parentView.getHeight();
        int v_width = touch_view.getWidth();
        int v_height = touch_view.getHeight();

        for(int i=0; i<9; i++){
            int[] joystick_xy = cal_XY(p_width, p_height, v_width, v_height, i);
            //조이스틱 버튼과 대응되는 모델 리스트
            joyStick_list.add(new JoyStick(joystick_xy[0], joystick_xy[1]));
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        // x,y 이동거리 = (이동한 x,y지점) - (최초누른 x,y지점)
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
            f_x = event.getX();
            f_y = event.getY();
        }
        if(event.getActionMasked() == MotionEvent.ACTION_MOVE){
            float n_x = (event.getX()-f_x);
            float n_y = (event.getY()-f_y);
            v.setX(v.getX()+n_x);
            v.setY(v.getY()+n_y);
        }
        return true;
    }

    public void init_btn(){
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


    public int[] cal_XY(int p_width, int p_height, int v_width, int v_height, int index){
        int[] joystick_xy = new int[2];
        if(index == 0) //좌상단
        {
            joystick_xy[0] = 0;
            joystick_xy[1] = 0;
        }
        else if(index == 1) // 상단
        {
            joystick_xy[0] = (p_width/2)-(v_width/2);
            joystick_xy[1] = 0;
        }
        else if(index == 2) // 우상단
        {
            joystick_xy[0] = p_width-v_width;
            joystick_xy[1] = 0;
        }
        else if(index == 3) // 좌
        {
            joystick_xy[0] = 0;
            joystick_xy[1] = (p_height/2)-(v_height/2);
        }
        else if(index ==4) // 중앙
        {
            joystick_xy[0] = (p_width/2)-(v_width/2);
            joystick_xy[1] = (p_height/2)-(v_height/2);
        }
        else if(index == 5) // 우
        {
            joystick_xy[0] = p_width-v_width;
            joystick_xy[1] = (p_height/2)-(v_height/2);
        }
        else if(index == 6) // 좌하단
        {
            joystick_xy[0] = 0;
            joystick_xy[1] = p_height-v_height;
        }
        else if(index == 7) // 하단
        {
            joystick_xy[0] = (p_width/2)-(v_width/2);
            joystick_xy[1] = p_height-v_height;
        }
        else if(index == 8) // 우하단
        {
            joystick_xy[0] = p_width-v_width;
            joystick_xy[1] = p_height-v_height;
        }

        return joystick_xy;
    }


    @Override
    public void onClick(View v) {
        //joystick 버튼의 부모
        GridLayout gridLayout = (GridLayout)v.getParent();

        //누른 joystick 버튼의 인덱스(0~8)
        int index = gridLayout.indexOfChild(v);

        //그 버튼과 대응되는 joystick 모델 호출
        int x = joyStick_list.get(index).getX();
        int y = joyStick_list.get(index).getY();

        touch_view.setX(x);
        touch_view.setY(y);
    }
}
