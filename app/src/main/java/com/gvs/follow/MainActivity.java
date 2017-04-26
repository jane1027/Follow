package com.gvs.follow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;

import android.widget.RelativeLayout;

import java.util.Collections;

public class MainActivity extends Activity {

    private ImageView moveImage1;
    private ImageView moveImage2;
    private ImageView moveImage3;
    private ImageView moveImage4;
    private ImageView moveImage5;
    private ImageView moveImage6;
    private ImageView moveImage7;
    private ImageView moveImage8;
    private ImageView moveImage9;
    private ImageView moveImage10;
    private RelativeLayout mRelativeLayout;
    private Follow mFollow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        moveImage1 = (ImageView) findViewById(R.id.move_image1);
        moveImage2 = (ImageView) findViewById(R.id.move_image2);
        moveImage3 = (ImageView) findViewById(R.id.move_image3);
        moveImage4 = (ImageView) findViewById(R.id.move_image4);
        moveImage5 = (ImageView) findViewById(R.id.move_image5);
        moveImage6 = (ImageView) findViewById(R.id.move_image6);
        moveImage7 = (ImageView) findViewById(R.id.move_image7);
        moveImage8 = (ImageView) findViewById(R.id.move_image8);
        moveImage9 = (ImageView) findViewById(R.id.move_image9);

        moveImage1.setOnTouchListener(onTouchListener);
        moveImage2.setOnTouchListener(onTouchListener);
        moveImage3.setOnTouchListener(onTouchListener);
        moveImage4.setOnTouchListener(onTouchListener);
        moveImage5.setOnTouchListener(onTouchListener);
        moveImage6.setOnTouchListener(onTouchListener);
        moveImage7.setOnTouchListener(onTouchListener);
        moveImage8.setOnTouchListener(onTouchListener);
        moveImage9.setOnTouchListener(onTouchListener);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        mFollow = new Follow();
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        int lastX;
        int lastY;
        int startleft;
        int startTop;
        int startRight;
        int startBotom;
        int left;
        int top;
        int right;
        int bottom;
        int screenWidth;
        int screenHeight;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                //按下
                case MotionEvent.ACTION_DOWN:

                    left = v.getLeft();
                    right = v.getRight();
                    top = v.getTop();
                    bottom = v.getBottom();

                    startleft = v.getLeft();
                    startRight = v.getRight();
                    startTop = v.getTop();
                    startBotom = v.getBottom();

                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();

                    //根据按下的X,Y坐标获取所点击item的position
                    break;
                //移动
                case MotionEvent.ACTION_MOVE:
                    int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;
                    screenWidth = mRelativeLayout.getWidth();
                    screenHeight = mRelativeLayout.getHeight();

                    left = v.getLeft() + dx;
                    top = v.getTop() + dy;
                    right = v.getRight() + dx;
                    bottom = v.getBottom() + dy;
                    if (left < 0) {
                        left = 0;
                        right = left + v.getWidth();
                    }
                    if (right > screenWidth) {
                        right = screenWidth;
                        left = right - v.getWidth();
                    }
                    if (top < 0) {
                        top = 0;
                        bottom = top + v.getHeight();
                    }
                    if (bottom > screenHeight) {
                        bottom = screenHeight;
                        top = bottom - v.getHeight();
                    }

                    v.layout(left, top, right, bottom);
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    break;
                //抬起
                case MotionEvent.ACTION_UP:
                    //判断有没有重叠.以及对重叠的一些处理
                    View to = mFollow.testCollection(v, mRelativeLayout);
                    //如果to不为空的话就是检测到有重叠,并且to为重叠的对象
                    if (null!=to){
                        int leftTo = to.getLeft();
                        int rightTo = to.getRight();
                        int topTo = to.getTop();
                        int bottomTo = to.getBottom();
                        v.layout(leftTo, topTo, rightTo, bottomTo);
                        to.layout(startleft, startTop, startRight, startBotom);
                    }

                    break;
            }
            return true;
        }

    };

}