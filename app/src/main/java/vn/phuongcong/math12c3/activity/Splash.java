package vn.phuongcong.math12c3.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import vn.phuongcong.math12c3.R;
import vn.phuongcong.math12c3.data.MuPdfData;

public class Splash extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mTextView;
    private Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        mImageView = (ImageView) findViewById(R.id.image);
        mTextView = (TextView) findViewById(R.id.text);
        startAnimation();
    }

    private void startAnimation() {
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotate.reset();
//        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress);
//        DoubleBounce doubleBounce = new DoubleBounce();
//        progressBar.setIndeterminateDrawable(doubleBounce);
        mImageView.setAnimation(rotate);

        mThread = new Thread() {
            @Override
            public void run() {
                super.run();
                int waited = 0;
                MuPdfData.getUriFromFileCopy(Splash.this);
                while (waited < 3500) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waited += 100;
                }

                Intent intent = new Intent(Splash.this, PhanActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }

        };
        mThread.start();
//        asyncTask.execute();
    }


}