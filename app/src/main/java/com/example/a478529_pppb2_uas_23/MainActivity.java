package com.example.a478529_pppb2_uas_23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Bitmap mBitmap;
    Canvas mCanvas;
    ImageView mImgView;
    private int mColorBackground;
    Paint mCirclePaint = new Paint();
    Paint mHeadPaint = new Paint();
    private ConstraintLayout mLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgView = findViewById(R.id.img_view);
        mCirclePaint.setColor(getResources().getColor(R.color.black));
        mHeadPaint.setColor(getResources().getColor(R.color.white));
        mLayout = findViewById(R.id.layout);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int vWidth = mImgView.getWidth();
        int vHeight = mImgView.getHeight();
        float centerX = vWidth / 2f;
        float centerY = vHeight / 2f;
        float radiusX = vWidth / 3f;
        float radiusY = vHeight / 4f;

        mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
        mImgView.setImageBitmap(mBitmap);
        mImgView.setVisibility(View.VISIBLE);
        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.white, null);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(mColorBackground);
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mColorBackground = ResourcesCompat.getColor(getResources(), R.color.yellow, null);
                mCanvas = new Canvas(mBitmap);
                mCanvas.drawColor(mColorBackground);

                drawHead(centerX, centerY, radiusX, radiusY);
                drawRightEye(vWidth, vHeight);
                drawLeftEye(vWidth, vHeight);
                drawEyeConnector(vWidth, vHeight);

                mCanvas.rotate(360, centerX, centerY);
                //mImgView.invalidate();

                animateCharacter(view);
                mImgView.setRotationY(0);
            }
        });
    }

    public void drawHead(float centerX, float centerY, float radiusX, float radiusY) {
        mCanvas.drawOval(centerX - radiusY, centerY - radiusX, centerX + radiusY,
                centerY + radiusX, mHeadPaint);
    }
    public void drawRightEye (float vWidth, float vHeight) {
        mCanvas.drawCircle(vWidth / 2 - 200,vHeight / 2, 80, mCirclePaint);
    }
    public void drawLeftEye (float vWidth, float vHeight) {
        mCanvas.drawCircle(vWidth / 2 + 200,vHeight / 2, 80, mCirclePaint);
    }
    public void drawEyeConnector (float vWidth, float vHeight) {
        mCanvas.drawRect(vWidth / 2 - 175, vHeight / 2 + 20,
                vWidth / 2 + 175, vHeight / 2 -20, mCirclePaint);
    }

    public void animateCharacter(View view){
        ObjectAnimator alphaStartAnimator = ObjectAnimator.ofFloat(mImgView, "alpha", 0f, 1f);
        alphaStartAnimator.setDuration(1000);

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(mImgView, "rotationY", 180);
        rotationAnimator.setDuration(1000);

        ObjectAnimator alphaEndAnimator = ObjectAnimator.ofFloat(mImgView, "alpha", 1f, 0f);
        alphaEndAnimator.setDuration(1000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(alphaStartAnimator, rotationAnimator, alphaEndAnimator);
        animatorSet.start();

        view.invalidate();
    }
}