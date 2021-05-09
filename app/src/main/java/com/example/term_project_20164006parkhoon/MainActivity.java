package com.example.term_project_20164006parkhoon;

import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

    boolean sw=false;

    LinkedList_Queue queue;//연결리스트 큐

    Button clean, rst;//계산 초기화 버튼

    TextView expression, textResult;
    String exp;
    String result;

    ///////////버튼 변수 설정
    Button[] numButtons = new Button[10];
    Integer[] numBtnIDs = { R.id.BtnNum0, R.id.BtnNum1, R.id.BtnNum2,
            R.id.BtnNum3, R.id.BtnNum4, R.id.BtnNum5, R.id.BtnNum6,
            R.id.BtnNum7, R.id.BtnNum8, R.id.BtnNum9 };

    ///연산 변수 설정
    Button[] expButtons = new Button[13];
    Integer[] expBtnIDs = { R.id.add, R.id.sub, R.id.div, R.id.mul, R.id.and, R.id.or, R.id.not,R.id.xor, R.id.re, R.id.left, R.id.right };
    int i;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///////////////////////////////////////////////
        queue = new LinkedList_Queue();

        //////////////////////////////////////////////

        ArrayList<String> items = new ArrayList<String>() ;


        try {
            FileInputStream inFs = openFileInput("file.txt");
            byte[] txt = new byte[1000];
            inFs.read(txt);
            String str2 = new String(txt);
            str2 = str2 +",";
            while(str2.length()!=0)
            {
                int idx = str2.indexOf(",");
                String temp1 = str2.substring(0,idx);
                items.add(temp1);
                str2 = str2.substring(idx+1);
            }

            inFs.close();
        } catch (IOException e) {

        }

        /////리스트뷰
        ListView list = (ListView) findViewById(R.id.listView1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                                long arg3) {
                                            Toast.makeText(getApplicationContext(), "파일",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
        ///////////////////////////////////


        /////////////////////////////////
        TabHost tabHost = getTabHost();

        TabSpec tabSpecSong = tabHost.newTabSpec("Calculator").setIndicator("Calculator");
        tabSpecSong.setContent(R.id.Calculator);
        tabHost.addTab(tabSpecSong);

        TabSpec tabSpecArtist = tabHost.newTabSpec("history")
                .setIndicator("history");
        tabSpecArtist.setContent(R.id.history);
        tabHost.addTab(tabSpecArtist);

        TabSpec tabSpecAlbum = tabHost.newTabSpec("Quiz").setIndicator("Quiz");
        tabSpecAlbum.setContent(R.id.Quiz);
        tabHost.addTab(tabSpecAlbum);

        tabHost.setCurrentTab(0);

        expression = (TextView) findViewById(R.id.txt_expression);
        textResult = (TextView) findViewById(R.id.txt_result);


        //계산 버튼 이벤트 처리
        for (i = 0; i < numBtnIDs.length; i++) {
            numButtons[i] = (Button) findViewById(numBtnIDs[i]);
        }
        for (i = 0; i < numBtnIDs.length; i++) {

            final int index;
            index = i;

            numButtons[index].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    exp="";
                    if(!sw)
                        exp=exp+" ";

                    //queue.enqueue(numButtons[index].getText().toString());//큐에 숫자 삽입
                    exp = expression.getText().toString()
                            +exp+ numButtons[index].getText().toString();
                    expression.setText(exp);

                    sw=true;
                }
            });
        }

        ///연산식 버튼 이벤트 처리
        for (i = 0; i < expBtnIDs.length; i++) {
            expButtons[i] = (Button) findViewById(expBtnIDs[i]);
        }
        for (i = 0; i < expBtnIDs.length; i++) {

            final int index;
            index = i;

            expButtons[index].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    sw=false;

                    String temp = expButtons[index].getText().toString();

                    if(temp.equals("And"))
                    {
                        temp = "&";
                    }
                    else if(temp.equals("Or"))
                    {
                        temp = "|";
                    }
                    else if(temp.equals("Not"))
                    {
                        temp = "~";
                    }
                    else if(temp.equals("Xor"))
                    {
                        temp = "^";
                    }
                    //queue.enqueue(expButtons[index].getText().toString());//큐에 계산식 삽입
                    exp = expression.getText().toString()
                            + " "+temp;
                    expression.setText(exp);


                }
            });
        }


        //계산식 초기화
        clean = (Button) findViewById(R.id.clean);
        clean.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.clear();
                expression.setText("");
                textResult.setText("");
                sw=false;
            }
        });

        rst = (Button) findViewById(R.id.BtnRst);
        rst.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String temp = expression.getText().toString()+" ";
                System.out.println(temp);
                while(temp.length()!=0)
                {
                    int idx = temp.indexOf(" ");
                    String temp1 = temp.substring(0,idx);
                    queue.enqueue(temp1);
                    System.out.println("token "+temp1);
                    temp = temp.substring(idx+1);
                }
                queue.dequeue();

                queue.printListQueue();
                calc cal = new calc();
                String str="";
                try {
                    cal.infix_to_postfix(queue);
                    result = cal.eval();
                    textResult.setText(result);


                    try {
                        FileInputStream inFs = openFileInput("file.txt");
                        byte[] txt = new byte[1000];
                        inFs.read(txt);
                        str = new String(txt);
                        inFs.close();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "파일 없음" , Toast.LENGTH_SHORT).show();
                    }

                    try {
                        FileOutputStream outFs = openFileOutput("file.txt",
                                Context.MODE_PRIVATE);
                        str = exp+" = "+result + ","+str;
                        outFs.write(str.getBytes());
                        outFs.close();
                        Toast.makeText(getApplicationContext(), "history 저장", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "history 저장에러", Toast.LENGTH_SHORT).show();
                    }



                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"계산 오류",Toast.LENGTH_SHORT).show();
                }



                items.clear();
                try {
                    FileInputStream inFs = openFileInput("file.txt");
                    byte[] txt = new byte[1000];
                    inFs.read(txt);
                    String str2 = new String(txt);
                    str2 = str2 +",";
                    while(str2.length()!=0)
                    {
                        int idx = str2.indexOf(",");
                        String temp1 = str2.substring(0,idx);
                        items.add(temp1);
                        str2 = str2.substring(idx+1);
                    }

                    inFs.close();
                } catch (IOException e) {

                }
                adapter.notifyDataSetChanged();

            }
        });



    }
}