package com.example.bubblepop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.content.*;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView vw = new MyView(this);
        setContentView(vw);
    }

    protected class MyView extends View {
        Bitmap bubble, pop;
        int[] X = new int[20];
        int[] Y = new int[40];
        int x_l, y_l;

        boolean[][] checkPop = new boolean[20][40];  //each state of position (pop or bubble)
        Canvas c;

        private void init() {
            bubble = BitmapFactory.decodeResource(getResources(), R.drawable.trues);  //get bubble image
            pop = BitmapFactory.decodeResource(getResources(), R.drawable.falses); //get pop image
            for (int i = 0; i < 20; i += 1) {
                for (int j = 0; j < 40; j += 1) {
                    checkPop[i][j] = false;  // false : bubble  true : pop
                }
            }
            int l = 0;  //get number of fragment width and height
            for (int i = 50; i < 1500; i += 100) {
                X[l] = i;
                l++;
            }
            x_l = l;
            l = 0;
            for (int i = 50; i < 4000; i += 100) {
                Y[l] = i;
                l++;
            }
            y_l = l;
        }

        MyView(Context c) {
            super(c);
            init();
        }

        public void onDraw(Canvas canvas) {
            Paint p = new Paint();

            for (int i = 0; i < x_l; i += 1) {
                for (int j = 0; j < y_l; j += 1) {
                    if(!checkPop[i][j])    //if this position was picked then show pop image nor show bubble image
                         canvas.drawBitmap(bubble, X[i] - 50, Y[j] - 50, null);
                    else
                        canvas.drawBitmap(pop, X[i] - 50, Y[j] - 50, null);
                }
            }


        }

        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            int minX = -1;
            int minY = -1;
            boolean finish = false;
            float cal;
            float min = -1;
            for (int i = 0; i < x_l; i++) {
                for (int j = 0; j < y_l; j++) {
                    cal = (x - X[i]) * (x - X[i]) + (y - Y[j]) * (y - Y[j]);
                    if(cal <= 50*50){   //when user push screen, computing what position user put then change state
                        Toast.makeText(getApplicationContext(),String.valueOf(cal),Toast.LENGTH_SHORT).show();
                        checkPop[i][j] = true;
                        finish = true;
                        break;
                    }
                }
                if(finish) break;
            }
            invalidate();
            return true;
        }
        private boolean checkIn(int x, int y, float checkX, float checkY){
            float length = ((float)x - checkX)*((float)x - checkX) + ((float)y-checkY)*((float)y-checkY);
            if(50*50 >= length){
                return true;
            }
            else
                return false;
        }
    }


}
