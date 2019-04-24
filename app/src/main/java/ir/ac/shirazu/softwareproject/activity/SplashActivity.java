package ir.ac.shirazu.softwareproject.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.ac.shirazu.softwareproject.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new CountDownTimer(2000, 100) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);

                startActivity(intent);

            }

        }.start();

    }
}
