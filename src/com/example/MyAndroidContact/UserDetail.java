package com.example.MyAndroidContact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created with IntelliJ IDEA.
 * User: hijack
 * Date: 13-6-25
 * Time: 下午12:20
 * To change this template use File | Settings | File Templates.
 */
public class UserDetail extends Activity{
    EditText name;
    EditText mobile;
    EditText email;

    Button change;
    Button ok;
    Button back;

    User user;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        setContentView(R.layout.userdetail);

        Intent intent = getIntent();
        id = (Integer)intent.getSerializableExtra("id");
        user = DBHelper.getUser(id);
        loadUserData();
        setEditTextDisable();

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextEnable();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modify();
                setEditTextDisable();
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(3);
                finish();
            }
        });
    }

    private void modify() {

        user.setName(name.getText().toString());
        user.setMobile(mobile.getText().toString());
        user.setEmail(email.getText().toString());

        boolean flag = DBHelper.update(id, user);
        if (flag) {
            setTitle("change success!");
        }
    }

    private void setTextEnable() {

        name.setEnabled(true);
        mobile.setEnabled(true);
        email.setEnabled(true);
    }

    private void loadUserData() {
        name = (EditText)findViewById(R.id.UserDetailName);
        mobile = (EditText)findViewById(R.id.UserDetailMobile);
        email = (EditText)findViewById(R.id.UserDetailEmail);

        change = (Button)findViewById(R.id.UserDetailChange);
        ok = (Button)findViewById(R.id.UserDetailOK);
        back = (Button)findViewById(R.id.UserDetailReturn);

        name.setText(user.getName());
        mobile.setText(user.getMobile());
        email.setText(user.getEmail());

    }

    private void setEditTextDisable() {
        name.setEnabled(false);
        mobile.setEnabled(false);
        email.setEnabled(false);

        //To change body of created methods use File | Settings | File Templates.

    }
}
