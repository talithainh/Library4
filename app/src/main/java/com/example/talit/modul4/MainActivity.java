package com.example.talit.modul4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView myImageView = (ImageView) findViewById(R.id.imgview);

                new Emojifier();
                Emojifier emoji = new Emojifier();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                Bitmap mBitmap = BitmapFactory.decodeResource(
                        getApplicationContext().getResources(),
                        R.drawable.hehe,
                        options
                );

                Paint myRectPaint = new Paint();
                myRectPaint.setStrokeWidth(5);
                myRectPaint.setColor(Color.YELLOW);
                myRectPaint.setStyle(Paint.Style.STROKE);

                FaceDetector fd =
                        new FaceDetector.Builder(getApplicationContext())
                                .setTrackingEnabled(false)
                                .build();
                if (!fd.isOperational()) {
                    new AlertDialog.Builder(v.getContext())
                            .setMessage("gabisa" + "terdeteksi!")
                            .show();
                    return;

                }

                Frame frame = new Frame.Builder().setBitmap(mBitmap).build();

                SparseArray<Face> faces = fd.detect(frame);

                Bitmap tempBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.RGB_565);
                Canvas tempCanvas = new Canvas(tempBitmap);
                tempCanvas.drawBitmap(mBitmap, 0, 0, null);

                for (int i = 0; i < faces.size(); i++) {
                    Face thisFace = faces.valueAt(i);
                    float x1 = thisFace.getPosition().x;
                    float y1 = thisFace.getPosition().y;
                    float x2 = x1 + thisFace.getWidth();
                    float y2 = y1 + thisFace.getHeight();
                    tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
                }
                myImageView.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
                myImageView.setImageBitmap(emoji.detectFaces(getApplicationContext(), mBitmap));


            }

        });


    }
}


