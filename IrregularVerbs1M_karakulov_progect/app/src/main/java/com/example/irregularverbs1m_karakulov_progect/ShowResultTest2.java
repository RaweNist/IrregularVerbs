//класс показа результата прохождения теста по выбору формы глагола
package com.example.irregularverbs1m_karakulov_progect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class ShowResultTest2 extends Test2 {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        Toolbar toolbar = findViewById(R.id.toolbar_r);
        TextView result = findViewById(R.id.score);
//        список ошибок
        ArrayList<String> list = new ArrayList<>(10-right);
        ListView ml = findViewById(R.id.mislist);
        Test2 t2 = new Test2();
//        добавление кнопки возврата на главный экран
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowResultTest2.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
//        вывод количества правильных ответов пользоваетля
        result.setText(right + "/10");
//        добавление ошибок в список для неверных ответов с правильным вариантом ответа
        if(right!=10) {
            for(int j = 0; j < (10 - right); j++){
            list.add("Ваш ответ: " + mistakes[j][1] + "\n" + "Правильный ответ: " + mistakes[j][0]);
            }
        }
//        добавление и вывод данных об ошибках в ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        ml.setAdapter(adapter);
//        обнуление значений правильных ответов, номера в списке и номера страницы
        t2.addRight(0);
        t2.addNum(0);
        t2.turnPage(0);
    }
}
