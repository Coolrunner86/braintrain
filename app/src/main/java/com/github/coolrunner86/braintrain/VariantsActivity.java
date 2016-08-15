package com.github.coolrunner86.braintrain;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class VariantsActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_variants);

        Button temp = (Button) findViewById(R.id.normalVariantBtn);
        temp.setOnClickListener(this);

        temp = (Button) findViewById(R.id.reverseVariantBtn);
        temp.setOnClickListener(this);

        temp = (Button) findViewById(R.id.reverseFlipVariantBtn);
        temp.setOnClickListener(this);

        temp = (Button) findViewById(R.id.randomVariantBtn);
        temp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
