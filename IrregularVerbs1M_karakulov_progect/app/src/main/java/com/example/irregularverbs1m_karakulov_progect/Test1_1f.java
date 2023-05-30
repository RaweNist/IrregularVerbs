//класс страницы теста по составлению одной из ДВУХ форм неправильного глагола
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Test1_1f extends Test1 {
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    String answer = "";
    //    массивы для форм глагола
    String v1 [] = new String[100];
    String v2 [] = new String[100];
    Test1 t1 = new Test1();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test3_2f);
        Button bt = findViewById(R.id.enter);
        ImageButton home = findViewById(R.id.home_1f);
        EditText et = findViewById(R.id.enterAnswer2);
        TextView task = findViewById(R.id.task);
        TextView pg = findViewById(R.id.page);
        TextView letters = findViewById(R.id.letters2);
//        переход на главеый экран
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Test1_1f.this,MainActivity.class);
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
//        добавление форм глаголов в соответственные списки
        Cursor cursor_v1 = mDb.rawQuery("SELECT v1 FROM verbs", null);
        Cursor cursor_v2 = mDb.rawQuery("SELECT v2 FROM verbs", null);

        int n = 0;

        for (cursor_v1.moveToFirst(),cursor_v2.moveToFirst();!cursor_v1.isAfterLast();
             cursor_v1.moveToNext(),cursor_v2.moveToNext(),n++){
            v1[n] = cursor_v1.getString(0);
            v2[n] = cursor_v2.getString(0);
        }
        cursor_v1.close();
        cursor_v2.close();
//        выбор глагола для задания
        Random random;
        random = new Random();
        int r = random.nextInt(93);
        int r1;
//        выбор пропущенной формы глагола
        r1 = random.nextInt(2);
//        выведение задания теста с одной пропущенной формой
        if (r1 == 0) {
            task.setText("? - " + v2[r]);
            answer = v1[r];
        } else {
            task.setText(v1[r] + " - ?");
            answer = v2[r];
        }
//        массив букв пропущенного солова
        String word [] = answer.split("");
//        список для букв пропущенного слова
        ArrayList<String> word_sep= new ArrayList<>();
//        побуквенное разбиение и добавление в массив пропущенной формы глагола
        for (int i = 0; i<word.length; i++) {
            word_sep.add(word[i]);
        }
//        перемешивание букв слова
        Collections.shuffle(word_sep);
//        выведение на экран букв, состовляющих пропущенную форму, в случайном порядке
        for (int i = 0; i<word.length; i++) {
            letters.append(word_sep.get(i) + "   ");
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
                        t1.addRight(1);
                    } else {
                        addMiss(an, answer);
                        addNum(1);
                    }
//                переход на следующую страницу теста
                    Intent intent1 = new Intent(Test1_1f.this, Test1_1f.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    t1.turnPage(1);
                }else{
//                    вывод просьбы ввести ответ хотя бы из 2-х букв
                    Toast.makeText(Test1_1f.this, R.string.toast,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
//        установка номера страницы теста
        pg.setText(page + " / 10");
//        условие перехода на экран результата
        if(page>10){
            Intent intent1 = new Intent(Test1_1f.this, ShowResultTest1.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
        }
    }
}
