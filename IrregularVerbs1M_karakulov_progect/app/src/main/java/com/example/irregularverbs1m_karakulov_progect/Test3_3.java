//класс страницы теста по написанию одной из ТРЁХ форм неправильного глагола
package com.example.irregularverbs1m_karakulov_progect;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

public class Test3_3 extends Test3 {
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    String answer = "";
    //    массивы для форм глагола
    String v1 [] = new String[100];
    String v2 [] = new String[100];
    String v3 [] = new String[100];
    Test3 t3 = new Test3();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);
        ImageButton home = findViewById(R.id.home_3);
        Button bt = findViewById(R.id.var1);
        EditText et = findViewById(R.id.enterAnswer);
        TextView task = (TextView) findViewById(R.id.task);
        TextView pg = (TextView) findViewById(R.id.page);
//        переход на главеый экран
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Test3_3.this,MainActivity.class);
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
        int n = 0;
        for (cursor_v1.moveToFirst(),cursor_v2.moveToFirst(),cursor_v3.moveToFirst();
             !cursor_v1.isAfterLast();
             cursor_v1.moveToNext(),cursor_v2.moveToNext(),cursor_v3.moveToNext(),n++){
            v1[n] = cursor_v1.getString(0);
            v2[n] = cursor_v2.getString(0);
            v3[n] = cursor_v2.getString(0);
        }
        cursor_v1.close();
        cursor_v2.close();
        cursor_v3.close();
//        выбор глагола для задания
        Random random;
        random = new Random();
        int r = random.nextInt(93);
//        выбор пропущенной формы глагола
        int r1;
        r1 = random.nextInt(3);
//        выведение задания теста с одной пропущенной формой
        if (r1 == 0) {
            task.setText("? - " + v2[r] + " - " + v3[r]);
            answer = v1[r];
        } else if (r1 == 1){
            task.setText(v1[r] + " - ? - " + v3[r]);
            answer = v2[r];
        } else {
            task.setText(v1[r] + " - " +v2[r] + " - ?");
            answer = v3[r];
        }
//        переход к следующему вопросу теста
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                сравнение ответа пользователя с правильным ответом
                String an = et.getText().toString();
//                проверка наличия ответа в поле для ввода ответа
                if(an.length()>1) {
                    if (an.equals(answer)) {
                        t3.addRight(1);
                    } else {
                        addMiss(an, answer);
                        addNum(1);
                    }
//                переход на следующую страницу теста
                    Intent intent1 = new Intent(Test3_3.this, Test3_3.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    t3.turnPage(1);
                }else{
//                    вывод просьбы ввести ответ хотя бы из 2-х букв
                    Toast.makeText(Test3_3.this, R.string.toast,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
//        установка номера страницы теста
        pg.setText(page + " / 10");
//        условие перехода на экран результата
        if(page>10){
            Intent intent1 = new Intent(Test3_3.this, ShowResultTest3.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
        }
    }
}
