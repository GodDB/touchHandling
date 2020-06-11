package com.example.touchmvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.touchmvvm.databinding.ActivityMainBinding;

/** 뷰 **/
public class MainActivity extends AppCompatActivity {
    private static float press_x;
    private static float press_y;

    private static MainViewModel mainViewModel;
    private ActivityMainBinding activityMainBinding;


    /** 뷰모델과 바인딩 **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel();
        activityMainBinding.setViewModel(mainViewModel);
    }

    /** 뷰모델에 모델 생성 요청 **/
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //터치 뷰의 부모 뷰 가로길이
        float p_width = activityMainBinding.touchZone.getWidth();
        //터치 뷰의 부모 뷰 세로길이
        float p_height = activityMainBinding.touchZone.getHeight();
        //터치 뷰의 가로 길이
        float v_width = activityMainBinding.touchView.getWidth();
        //터치 뷰의 세로 길이
        float v_height = activityMainBinding.touchView.getHeight();

        //모델 생성 요청 -> viewmodel
        mainViewModel.createModel(p_width, p_height, v_width, v_height);
    }

    /** 터치뷰에 onTouchListener 장착 **/
    @BindingAdapter("onTouchListener")
    public static void setOnTouchListener(View view, boolean value){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    press_x = event.getX();
                    press_y = event.getY();
                }
                if(event.getActionMasked() == MotionEvent.ACTION_MOVE){
                    float move_x = event.getX();
                    float move_y = event.getY();

                    //터치포인트 x,y 변위 계산 요청 to viewmodel
                    mainViewModel.cal_displacement(press_x, press_y, move_x, move_y);
                }
                return true;
            }
        });
    }




}
