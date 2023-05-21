package com.example.firstsemester_midterm_file;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    DatePicker dp;
    Button btnWrite;
    EditText edtDiary;
    String filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dp = findViewById(R.id.datePicker);
        btnWrite = findViewById(R.id.btnWrite);
        edtDiary = findViewById(R.id.edtDiary);

        //현재의 날짜를 담음
        Calendar cal =Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month =cal.get(Calendar.MONTH);
        int day =cal.get(Calendar.DAY_OF_MONTH);

        //DatePicker에 초기화화
       dp.init(year, month, day,
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view,int year,
                                              int month, int dayOfMonth) {
                        filename = Integer.toString(year)+"_"+
                                Integer.toString(month)+"_"+
                                Integer.toString(dayOfMonth)+".txt";
                        String str =  readDiary(filename);
                        edtDiary.setText(str);
                        btnWrite.setEnabled(true);
                    }
                });

        //쓰기버튼누르면
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //파일을 쓰기모드로 열기
                    FileOutputStream oufs =openFileOutput(filename,
                            Context.MODE_PRIVATE);
                    String str = edtDiary.getText().toString();
                    oufs.write(str.getBytes());
                    oufs.close();
                }catch (IOException e){}
            }
        });
    }//onCreate
    String readDiary(String fname){
        String diaryStr = null;
        FileInputStream infs;
        try{
            infs = openFileInput(fname);
            byte[] txt = new byte[50];
            infs.read(txt);
            infs.close();
            diaryStr = new String(txt).trim();
            btnWrite.setText("수정하기");
        }catch (IOException e){
            edtDiary.setText("일기없음");
            btnWrite.setText("새로저장하기");
        }
        return diaryStr;
    }
}//class