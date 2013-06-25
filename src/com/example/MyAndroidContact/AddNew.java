package com.example.MyAndroidContact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: hijack
 * Date: 13-6-20
 * Time: 下午9:34
 * To change this template use File | Settings | File Templates.
 */
public class AddNew extends Activity{

    EditText name ;
    EditText email;
    EditText mobile;

    Button save;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.addnew);
        System.out.println("到达addnew");
        Intent intent = getIntent();

        name = (EditText)findViewById(R.id.nameForAddNew);
        mobile = (EditText)findViewById(R.id.MobileForAddNew);
        email = (EditText)findViewById(R.id.EmailForAddNew);
        save = (Button)findViewById(R.id.SaveForAddNew);
        back = (Button)findViewById(R.id.ReturnForAddNew);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString();
                if (nameText.equals("")) {
                    Toast.makeText(AddNew.this, "name can not be empty!", Toast.LENGTH_LONG).show();
                    return;
                }

                User user = new User(
                        nameText,
                        mobile.getText().toString(),
                        email.getText().toString()
                );


                boolean flag = DBHelper.save(user);
                if (flag) {
                    Toast.makeText(AddNew.this, "add success!", Toast.LENGTH_LONG).show();
                }
                /**
                 * 这里addnew只是将user放到db中，addnew结束，会返回到main中onreceive方法，在那里显示listview到屏幕。
                 */
                setTitle("add success!");
                setResult(3);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
