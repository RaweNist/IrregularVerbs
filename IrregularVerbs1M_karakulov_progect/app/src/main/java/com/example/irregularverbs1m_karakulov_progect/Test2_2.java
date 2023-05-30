//класс страницы теста по выбору одной из ТРЁХ форм неправильного глагола
package com.example.irregularverbs1m_karakulov_progect;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

public class Test2_2 extends Test2 {
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    String answer = "";
    //    массивы для форм глагола
    String v1 [] = new String[100];
    String v2 [] = new String[100];
    String v3 [] = new String[100];
    //    массивы для неправильных вариантов ответов
    String v1_1 [] = new String[100];
    String v1_2 [] = new String[100];
    String v2_1 [] = new String[100];
    String v2_2 [] = new String[100];
    String v3_1 [] = new String[100];
    String v3_2 [] = new String[100];
    Test2 t2 = new Test2();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);
        TextView task = (TextView) findViewById(R.id.task);
        TextView pg = (TextView) findViewById(R.id.page);
        ImageButton home = findViewById(R.id.home_2);
        Button bt1 = findViewById(R.id.var1);
        Button bt2 = findViewById(R.id.var2);
        Button bt3 = findViewById(R.id.var3);
//        переход на главеый экран
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Test2_2.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
//        проверка исключительных ситуаций с базой данных
        mDBHelper = new DataBaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
//        добавление глаголов и неправильных ответов в соответственные списки
        Cursor cursor_v1 = mDb.rawQuery("SELECT v1 FROM verbs", null);
        Cursor cursor_v2 = mDb.rawQuery("SELECT v2 FROM verbs", null);
        Cursor cursor_v3 = mDb.rawQuery("SELECT v3 FROM verbs", null);
        Cursor cursor_v1_1 = mDb.rawQuery("SELECT v1_1 FROM verbs", null);
        Cursor cursor_v1_2 = mDb.rawQuery("SELECT v1_2 FROM verbs", null);
        Cursor cursor_v2_1 = mDb.rawQuery("SELECT v2_1 FROM verbs", null);
        Cursor cursor_v2_2 = mDb.rawQuery("SELECT v2_2 FROM verbs", null);
        Cursor cursor_v3_1 = mDb.rawQuery("SELECT v3_1 FROM verbs", null);
        Cursor cursor_v3_2 = mDb.rawQuery("SELECT v3_2 FROM verbs", null);
        int n = 0;
        for (cursor_v1.moveToFirst(),cursor_v2.moveToFirst(),cursor_v3.moveToFirst()
                     , cursor_v1_1.moveToFirst(), cursor_v1_2.moveToFirst(),
                     cursor_v2_1.moveToFirst(), cursor_v2_2.moveToFirst(),
                     cursor_v3_1.moveToFirst(), cursor_v3_2.moveToFirst();
             !cursor_v1.isAfterLast();
             cursor_v1.moveToNext(),cursor_v2.moveToNext(),cursor_v3.moveToNext()
                     ,cursor_v1_1.moveToNext(), cursor_v1_2.moveToNext(),
                     cursor_v2_1.moveToNext(), cursor_v2_2.moveToNext(),
                     cursor_v3_1.moveToNext(), cursor_v3_2.moveToNext()
                     ,n++){
            v1[n] = cursor_v1.getString(0);
            v2[n] = cursor_v2.getString(0);
            v3[n] = cursor_v3.getString(0);
            v1_1[n] = cursor_v1_1.getString(0);
            v1_2[n] = cursor_v1_2.getString(0);
            v2_1[n] = cursor_v2_1.getString(0);
            v2_2[n] = cursor_v2_2.getString(0);
            v3_1[n] = cursor_v3_1.getString(0);
            v3_2[n] = cursor_v3_2.getString(0);
        }
        cursor_v1.close();
        cursor_v2.close();
        cursor_v3.close();
        cursor_v1_1.close();
        cursor_v1_2.close();
        cursor_v2_1.close();
        cursor_v2_2.close();
        cursor_v3_1.close();
        cursor_v3_2.close();
//        выбор глагола для задания
        Random random;
        random = new Random();
        int r = random.nextInt(92);
//        выбор пропущенной формы глагола
        int r1 = random.nextInt(3);
//        выбор ячейки, в которой будет находиться правильный ответ
        int r2 = random.nextInt(3);
//        выведение задания теста с одной пропущенной формой
        if (r1 == 0) {
            task.setText("? - " + v2[r] + " - " + v3[r]);
            answer = v1[r];
        } else if (r1 == 1){
            task.setText(v1[r] + " - ? - " + v3[r]);
            answer = v2[r];
        } else{
            task.setText(v1[r] + " - " + v2[r] + " - ?");
            answer = v3[r];
        }
//        заполнение ячеек с ответами в зависимости от нахождения правилього ответа
        if (r2==0){
            bt1.setText(answer);
            if(r1==0){
                bt2.setText(v1_1[r]);
                bt3.setText(v1_2[r]);
            }else if (r1 == 1){
                bt2.setText(v2_2[r]);
                bt3.setText(v2_1[r]);
            } else{
                bt2.setText(v3_1[r]);
                bt3.setText(v3_2[r]);
            }
        } else if (r2==1){
            bt2.setText(answer);
            if(r1==0){
                bt1.setText(v1_1[r]);
                bt3.setText(v1_2[r]);
            } else if (r1 == 1){
                bt1.setText(v2_2[r]);
                bt3.setText(v2_1[r]);
            } else{
                bt1.setText(v3_2[r]);
                bt3.setText(v3_1[r]);
            }
        } else{
            bt3.setText(answer);
            if(r1==0){
                bt1.setText(v1_1[r]);
                bt2.setText(v1_2[r]);
            }else if (r1 == 1){
                bt1.setText(v2_2[r]);
                bt2.setText(v2_1[r]);
            } else{
                bt1.setText(v3_1[r]);
                bt2.setText(v3_2[r]);
            }
        }
//        обработка нажатия на первый вариант ответа
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (r2==0){
                    t2.addRight(1);
                } else{
                    t2.addMiss(answer, bt1.getText().toString());
                    t2.addNum(1);
                }
//                переход к следующему вопросу теста
                Intent intent1 = new Intent(Test2_2.this, Test2_2.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                t2.turnPage(1);
            }
        });
//        обработка нажатия на второй вариант ответа
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (r2==1){
                    t2.addRight(1);
                } else{
                    t2.addMiss(answer, bt2.getText().toString());
                    t2.addNum(1);
                }
//                переход к следующему вопросу теста
                Intent intent1 = new Intent(Test2_2.this, Test2_2.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                t2.turnPage(1);
            }
        });
//        обработка нажатия на третий вариант ответа
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (r2==2){
                    t2.addRight(1);
                } else{
                    t2.addMiss(bt3.getText().toString(), answer);
                    t2.addNum(1);
                }
//                переход к следующему вопросу теста
                Intent intent1 = new Intent(Test2_2.this, Test2_2.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                t2.turnPage(1);
            }
        });
//        установка номера страницы теста
        pg.setText(page + " / 10");
//        условие перехода на экран результата
        if(page>10){
            Intent intent1 = new Intent(Test2_2.this, ShowResultTest2.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
        }
    }
}