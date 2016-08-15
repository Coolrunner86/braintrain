package com.github.coolrunner86.braintrain;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class DifficultyActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_difficulty);

        Button temp = (Button) findViewById(R.id.easyBtn);
        temp.setOnClickListener(this);

        temp = (Button) findViewById(R.id.normalBtn);
        temp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.easyBtn:
                Intent intent = new Intent(this, VariantsActivity.class);
                startActivity(intent);

                break;
            case R.id.normalBtn:
                break;
        }
    }
}
