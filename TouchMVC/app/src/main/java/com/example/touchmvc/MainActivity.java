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

        int p_width = parentView.getWidth();
        int p_height = parentView.getHeight();
        int v_width = touch_view.getWidth();
        int v_height = touch_view.getHeight();

        for(int i=0; i<9; i++){

            //모델 데이터 계산
            Point joystick_xy = cal_XY(p_width, p_height, v_width, v_height, i);

            //조이스틱 버튼과 대응되는 모델 리스트 ( 0번째 버튼 - 0번째 모델, 1번째 버튼 - 1번째 모델 ... )
            joyStick_list.add(new JoyStick(joystick_xy.x, joystick_xy.y));
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
        int x = joyStick_list.get(index).getX();
        int y = joyStick_list.get(index).getY();

        touch_view.setX(x);
        touch_view.setY(y);
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
