package com.github.coolrunner86.braintrain;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        Button temp = (Button) findViewById(R.id.newGameBtn);
        temp.setOnClickListener(this);

        temp = (Button) findViewById(R.id.resumeGameBtn);
        temp.setOnClickListener(this);

        temp = (Button) findViewById(R.id.loadGameBtn);
        temp.setOnClickListener(this);

        temp = (Button) findViewById(R.id.saveGameBtn);
        temp.setOnClickListener(this);

        temp = (Button) findViewById(R.id.highscoresBtn);
        temp.setOnClickListener(this);

        temp = (Button) findViewById(R.id.aboutBtn);
        temp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.newGameBtn:
                Intent intent = new Intent(this, DifficultyActivity.class);
                startActivity(intent);
                break;

            case R.id.resumeGameBtn:
                break;
            case R.id.loadGameBtn:
                break;
            case R.id.saveGameBtn:
                break;

            case R.id.aboutBtn:
                break;
            case R.id.highscoresBtn:
                break;
        }
    }
}
