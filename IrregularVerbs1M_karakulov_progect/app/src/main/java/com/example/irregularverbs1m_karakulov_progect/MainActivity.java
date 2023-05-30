//класс начального экрана приложения
package com.example.irregularverbs1m_karakulov_progect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout test_comb = findViewById(R.id.combinate);
        RelativeLayout test_ch = findViewById(R.id.choice);
        RelativeLayout test_wr = findViewById(R.id.write);
        RelativeLayout dict = findViewById(R.id.dictionary);
        Button exit = findViewById(R.id.exit);
//        переход на тест по составлению формы глагола
        test_comb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_1 = new Intent(MainActivity.this, Test1.class);
                startActivity(intent_1);
            }
        });
//        переход на тест по выбору формы глагола
        test_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_2 = new Intent(MainActivity.this, Test2.class);
                startActivity(intent_2);
            }
        });
//        переход на тест по написанию формы глагола
        test_wr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_3 = new Intent(MainActivity.this, Test3.class);
                startActivity(intent_3);
            }
        });
//        переход на экран словаря
        dict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_4 = new Intent(MainActivity.this, Dictionary.class);
                startActivity(intent_4);

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
        });
    }

}