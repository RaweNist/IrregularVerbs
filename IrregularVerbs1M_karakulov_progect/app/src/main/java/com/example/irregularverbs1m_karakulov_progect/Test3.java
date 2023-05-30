//класс начала теста по написанию формы глагола
package com.example.irregularverbs1m_karakulov_progect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Test3 extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    //    номер страницы
    static int page;
    //    кол-во правильных ответов
    static int right;
    //    номер ошибки
    static int num;
    //    список ошибок с правильными ответами
    static String [][] mistakes = new String[10][2];
    //    переменная состояния Switch
    boolean isChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1_start);
        Button test3_start = findViewById(R.id.var1);
        Switch sw = findViewById(R.id.sw1);
//        проверка состояния Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(this);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        try {
            setSupportActionBar(toolbar);
        } catch (Exception e){
            getSupportActionBar().hide();
        }
//        добавление кнопки возврата на главный экран
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Test3.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
//        переход на вариант теста в зависимости от значения isChecked
        test3_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked == true) {
//                    переход на тест с 3-мя формами глаголов
                    turnPage(0);
                    addRight(0);
                    addNum(0);
                    Intent intent1 = new Intent(Test3.this, Test3_3f.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                }
                else {
//                    переход на тест с 2-мя формами глаголов
                    turnPage(0);
                    addRight(0);
                    addNum(0);
                    Intent intent1 = new Intent(Test3.this, Test3_3.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                }
            }
        });
    }
//    установка значения isChecked в зависимости от состояния Switch
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean a) {
        isChecked = a;
    }
//    изменение номера страницы
    public void turnPage(int a){
        if (a==1)
            page++;
        else if(a==0)
            page=1;}
//    изменение кол-ва правильных ответов
    public void addRight(int a){
        if (a==1)
            right++;
        else if(a==0)
            right=0;}
//    изменение номера ошибки в списке ошибок
    public void addNum(int a){
        if (a==1)
            num++;
        else if(a==0)
            num=0;}
//    добавление ошибки и правильного варианта ответа в список ошибок
    public void addMiss(String right, String miss){
        mistakes[num][0] = miss;
        mistakes[num][1] = right;
    }
}
