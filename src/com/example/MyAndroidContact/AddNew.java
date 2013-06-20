package com.example.MyAndroidContact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * User: hijack
 * Date: 13-6-20
 * Time: 下午9:34
 * To change this template use File | Settings | File Templates.
 */
public class AddNew extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.addNew);

        Intent intent = getIntent();

    }
}
