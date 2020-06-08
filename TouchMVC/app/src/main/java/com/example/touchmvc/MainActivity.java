package com.example.touchmvc;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private View touch_view;

    private Button leftTop_btn;
    private Button top_btn;
    private Button rightTop_btn;
    private Button left_btn;
    private Button center_btn;
    private Button right_btn;
    private Button leftBottom_btn;
    private Button bottom_btn;
    private Button rightBottom_btn;

    private float f_x =0;
    private float f_y =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touch_view = findViewById(R.id.touch_view);
        touch_view.setOnTouchListener(this);

        //버튼 초기화
        init_btn();

        //버튼 setOnClickListener;
        init_listener();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
     /*   int a = findViewById(R.id.touchZone).getLeft();
        int b = findViewById(R.id.touchZone).getRight();
        int c = findViewById(R.id.touchZone).getTop();
        int d = findViewById(R.id.touchZone).getBottom();*/

      /* BtnOnClickListeners btnOnClickListeners = new BtnOnClickListeners(this);*/
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
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
        leftTop_btn=findViewById(R.id.leftTop_btn);
        top_btn=findViewById(R.id.top_btn);
        rightTop_btn=findViewById(R.id.rightTop_btn);
        left_btn = findViewById(R.id.left_btn);
        center_btn = findViewById(R.id.center_btn);
        right_btn = findViewById(R.id.right_btn);
        leftBottom_btn = findViewById(R.id.leftBottom_btn);
        bottom_btn = findViewById(R.id.bottom_btn);
        rightBottom_btn = findViewById(R.id.rightBottom_btn);
    }

    public void init_listener(){
/*        leftTop_btn
        top_btn
        rightTop_btn
        left_btn
        center_btn
        right_btn
        leftBottom_btn
        bottom_btn
        rightBottom_btn*/
    }
}
