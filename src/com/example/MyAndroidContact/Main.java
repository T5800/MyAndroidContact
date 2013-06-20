package com.example.MyAndroidContact;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Activity {

    ListView lv;
    GridView bottomMenuGrid;

    String[] bottom_menu_name = {"add", "search", "delete", "back"};
    int[] bottom_menu_src = {
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher};
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lv = (ListView)findViewById(R.id.main_ListView);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            loadBottomMenu();

            if (bottomMenuGrid.getVisibility() == View.GONE) {
                bottomMenuGrid.setVisibility(View.VISIBLE);
            }
            else {
                bottomMenuGrid.setVisibility(View.GONE);
            }
        }
        return super.onKeyDown(keyCode, event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void loadBottomMenu() {
        if (bottomMenuGrid == null) {
            bottomMenuGrid = (GridView)findViewById(R.id.main_GridView);
            /**
             * add, search, delete, back
             */
            bottomMenuGrid.setNumColumns(4);
            bottomMenuGrid.setBackgroundResource(R.drawable.channelgallery_bg);
            bottomMenuGrid.setGravity(Gravity.CENTER);
            bottomMenuGrid.setAdapter(getAdapter(bottom_menu_name, bottom_menu_src));
            /**
             * 此时，bottomMenuGrid建立完毕...下面安装监听器..
             */
            bottomMenuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            if (bottomMenuGrid.getVisibility() == View.VISIBLE) {
                                bottomMenuGrid.setVisibility(View.GONE);
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
                            builder.setTitle("add is being clicked!");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            break;
                        case 1:


                        case 2:
                            if (bottomMenuGrid.getVisibility() == View.VISIBLE) {
                                bottomMenuGrid.setVisibility(View.GONE);
                            }

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(Main.this);
                            builder1.setIcon(R.drawable.ic_launcher);
                            builder1.setTitle("delete is clicked!");
                            AlertDialog dialog1 = builder1.create();
                            dialog1.show();
                            break;
                        case 3:finish();break;
                    }
                }
            });


        }
        //To change body of created methods use File | Settings | File Templates.
    }
    private SimpleAdapter getAdapter(String[] bottom_menu_name, int[] bottom_menu_src) {
        ArrayList<HashMap<String, Object>> datalist = new ArrayList<HashMap<String, Object>>();
        for (int i = 0 ; i < bottom_menu_name.length; i ++) {
            HashMap<String, Object> map = new HashMap<String, Object>(4);
            map.put("ItemImage", bottom_menu_src[i]);
            map.put("ItemText", bottom_menu_name[i]);
            datalist.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this, datalist, R.layout.menuItem,
                new String[] {"ItemImage", "ItemText"}, new int[] {R.id.ItemImage, R.id.ItemText}
        );
        return adapter;
    }
}
