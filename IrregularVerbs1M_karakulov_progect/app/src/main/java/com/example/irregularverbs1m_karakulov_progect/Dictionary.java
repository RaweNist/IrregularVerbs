//класс словаря неправильных глаголов
package com.example.irregularverbs1m_karakulov_progect;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.util.ArrayList;

public class Dictionary extends MainActivity{
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary);
        GridView gv = findViewById(R.id.gv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_d);
        try {
            setSupportActionBar(toolbar);
        } catch (Exception e){
            getSupportActionBar().hide();
        }
//        переход на главеый экран
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dictionary.this, MainActivity.class);
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
//        список для неправильных глаголов
        ArrayList <String> list = new ArrayList<>(100);
//        добавление заголовков столбцов словаря
        list.add("Infinitive");
        list.add("Past Simple");
        list.add("Past Participle");
        list.add("Перевод");
//        добавление глаголов в список list
        Cursor cursor_v1 = mDb.rawQuery("SELECT v1 FROM verbs", null);
        Cursor cursor_v2 = mDb.rawQuery("SELECT v2 FROM verbs", null);
        Cursor cursor_v3 = mDb.rawQuery("SELECT v3 FROM verbs", null);
        Cursor cursor_tr = mDb.rawQuery("SELECT translate FROM verbs", null);

        for (cursor_v1.moveToFirst(),cursor_v2.moveToFirst(),cursor_v3.moveToFirst(),cursor_tr.moveToFirst();!cursor_v1.isAfterLast();
             cursor_v1.moveToNext(),cursor_v2.moveToNext(),cursor_v3.moveToNext(),cursor_tr.moveToNext()) {
            list.add(cursor_v1.getString(0));
            list.add(cursor_v2.getString(0));
            list.add(cursor_v3.getString(0));
            list.add(cursor_tr.getString(0));
        }
        cursor_v1.close();
        cursor_v2.close();
        cursor_v3.close();
        cursor_tr.close();
//        выведение списка в GridView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simle_textview, list);

        gv.setAdapter(adapter);
    }
}
