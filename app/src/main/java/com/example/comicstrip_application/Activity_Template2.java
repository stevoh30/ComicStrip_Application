package com.example.comicstrip_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Activity_Template2 extends AppCompatActivity {


    private ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(50,50);


    //private ViewGroup.LayoutParams layoutParams;
    //private android.widget.RelativeLayout.LayoutParams layoutParams;
    //ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.myLayout);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__template2);

       // Button btn = findViewById(R.id.btnPractice);

        //findViewById(R.id.btnPractice).setOnTouchListener(new MyTouchListener1());
        //findViewById(R.id.btnPractice).setOnDragListener(new MyDragListener1());
//        btn.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                float xDown =0, yDown=0;
//                switch(motionEvent.getActionMasked()){
//                    //user pressed down on object
//                    case MotionEvent.ACTION_DOWN:
//                        xDown = motionEvent.getX();
//                        yDown = motionEvent.getY();
//                        break;
//                    //user moves object
//                    case MotionEvent.ACTION_MOVE:
//                        float movedX, movedY;
//                        movedX = motionEvent.getX();
//                        movedY = motionEvent.getY();
//
//                        //calculates distance from down to move
//                        float distanceX = movedX - xDown;
//                        float distanceY = movedY - yDown;
//
//                        //move view to position
//                        btn.setX(btn.getX()+distanceX);
//                        btn.setY(btn.getY()+distanceY);
//
//                        //set values for next move
//                        xDown=movedX;
//                        yDown=movedY;
//
//                        break;
//
//                }
//                return false;
//            }
//        });

    }
}
//class MyTouchListener1 implements View.OnTouchListener {
//    //creates shadowbox when dragging object
//    @Override
//    public boolean onTouch(View v, MotionEvent e) {
//
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
//                v.startDrag(null, shadowBuilder, v, 0);
//                v.setVisibility((v.INVISIBLE));
//                return true;
//        }
//        return false;
//    }
//}
//creates coordinates for targeted drop for object upon release click
class MyDragListener1 implements View.OnDragListener {
    private static final String msg = "Works";
    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(50,50);
    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch(event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                layoutParams = (ConstraintLayout.LayoutParams)v.getLayoutParams();
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_STARTED");
                // Do nothing
                break;

            case DragEvent.ACTION_DRAG_ENTERED:
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENTERED");
                int x_cord = (int) event.getX();
                int y_cord = (int) event.getY();
                break;

            case DragEvent.ACTION_DRAG_EXITED :
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_EXITED");
                x_cord = (int) event.getX();
                y_cord = (int) event.getY();
                layoutParams.leftMargin = x_cord;
                layoutParams.topMargin = y_cord;
                v.setLayoutParams(layoutParams);
                break;

            case DragEvent.ACTION_DRAG_LOCATION  :
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_LOCATION");
                x_cord = (int) event.getX();
                y_cord = (int) event.getY();
                break;

            case DragEvent.ACTION_DRAG_ENDED   :
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENDED");
                v.setVisibility(View.VISIBLE);
                // Do nothing
                break;

            case DragEvent.ACTION_DROP:
                Log.d(msg, "ACTION_DROP event");

                // Do nothing
                break;
            default: break;
        }
        return true;
    }


//    @Override
//    public boolean onDrag(View mainView, DragEvent e) {
//        View view = (View) e.getLocalState();
//        switch (e.getAction()) {
//            case DragEvent.ACTION_DROP:
//                Log.d(TAG, "onDrag: ACTION_DRAG_STARTED ");
//                view.setX(e.getX() - (view.getWidth() / 2));
//                view.setY(e.getY() - (view.getHeight() / 2));
//                view.invalidate();
//                mainView.invalidate();
//                view.setVisibility(view.VISIBLE);
//                return true;
//            case DragEvent.ACTION_DRAG_STARTED:
//                return true;
//
//            case DragEvent.ACTION_DRAG_EXITED:
//                break;
//
//            case DragEvent.ACTION_DRAG_ENDED:
//                mainView.invalidate();
//                return true;
//
//            default:
//
//
//                break;
//        }
//
//        return true;
//    }
//    @Override
//    public boolean onDrag(View v, DragEvent event) {
//        //int action = event.getAction();
//        switch (event.getAction()) {
//            case DragEvent.ACTION_DRAG_STARTED:
//                //Log.d(TAG, "onDrag: ACTION_DRAG_STARTED ");
//                break;
//            case DragEvent.ACTION_DRAG_ENTERED:
//                //Log.d(TAG, "onDrag: ACTION_DRAG_ENTERED ");
//                break;
//            case DragEvent.ACTION_DRAG_EXITED:
//                //Log.d(TAG, "onDrag: ACTION_DRAG_EXITED ");
//                break;
//            case DragEvent.ACTION_DROP:
//                View view = (View) event.getLocalState();
//                ViewGroup owner = (ViewGroup) view.getParent();
//                owner.removeView(view);
//                ConstraintLayout container = (ConstraintLayout) v;
//                container.addView(view);
//                view.setVisibility(View.VISIBLE);
//                break;
//            case DragEvent.ACTION_DRAG_ENDED:
//                //Log.d(TAG, "onDrag: ACTION_DRAG_ENDED ");
//            default:
//                break;
//        }
//        return true;
//    }
    //class MyTouchListener implements View.OnTouchListener {
//    @Override
//    public boolean onTouch(View view, MotionEvent event) {
//        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
//                view);
//        view.startDrag(null, shadowBuilder, view, 0);
//        view.setVisibility(View.INVISIBLE);
//        return true;
//    }
    //creates shadowbox when dragging object
//    public boolean onTouch(View v, MotionEvent e) {
//
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
//                v.startDrag(null, shadowBuilder, v, 0);
//                v.setVisibility((v.INVISIBLE));
//                return true;
//        }
//        return false;
//    }
//}
////creates coordinates for targeted drop for object upon release click
//class MyDragListener implements View.OnDragListener {
//    @Override
//    public boolean onDrag(View mainView, DragEvent e) {
//        View view = (View) e.getLocalState();
//        switch (e.getAction()) {
//            case DragEvent.ACTION_DROP:
//                view.setX(e.getX() - (view.getWidth() / 2));
//                view.setY(e.getY() - (view.getHeight() / 2));
//                view.invalidate();
//                mainView.invalidate();
//                //view.setVisibility(view.VISIBLE);
//                return true;
//            case DragEvent.ACTION_DRAG_STARTED:
//
//                return true;
//
//            case DragEvent.ACTION_DRAG_EXITED:
//                break;
//
//            case DragEvent.ACTION_DRAG_ENDED:
//                mainView.invalidate();
//                return true;
//
//            default:
//
//                break;
//        }
//
//        return true;
//    }
//    public boolean onDrag(View v, DragEvent event) {
//        int action = event.getAction();
//        switch (event.getAction()) {
//            case DragEvent.ACTION_DRAG_STARTED:
//                break;
//            case DragEvent.ACTION_DRAG_ENTERED:
//                //Log.d(TAG, "onDrag: ACTION_DRAG_ENTERED ");
//                break;
//            case DragEvent.ACTION_DRAG_EXITED:
//                //Log.d(TAG, "onDrag: ACTION_DRAG_EXITED ");
//                break;
//            case DragEvent.ACTION_DROP:
//                View view = (View) event.getLocalState();
//                ViewGroup owner = (ViewGroup) view.getParent();
//                owner.removeView(view);
//                ConstraintLayout container = (ConstraintLayout) v;
//                container.addView(view);
//                view.setVisibility(View.VISIBLE);
//                break;
//            case DragEvent.ACTION_DRAG_ENDED:
//                //Log.d(TAG, "onDrag: ACTION_DRAG_ENDED ");
//            default:
//                break;
//        }
//        return true;
//    }
//}

}
