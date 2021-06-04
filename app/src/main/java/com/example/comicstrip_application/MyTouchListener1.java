package com.example.comicstrip_application;

import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

// class MyTouchListener allows user to drag and drop object anywhere within the layout
class MyTouchListener1 implements View.OnTouchListener {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float xDown =0, yDown=0;
        switch(motionEvent.getActionMasked()){
            //user pressed down on object
            case MotionEvent.ACTION_DOWN:
                xDown = motionEvent.getX();
                yDown = motionEvent.getY();
                break;
            //user moves object
            case MotionEvent.ACTION_MOVE:
                float movedX, movedY;
                movedX = motionEvent.getX();
                movedY = motionEvent.getY();

                //calculates distance from down to move
                float distanceX = movedX - xDown;
                float distanceY = movedY - yDown;

                //move view to position
                view.setX(view.getX()+distanceX);
                view.setY(view.getY()+distanceY);

                //set values for next move
                xDown=movedX;
                yDown=movedY;

                break;
        }
        return false;
    }
}

