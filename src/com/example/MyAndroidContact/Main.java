package com.example.MyAndroidContact;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Activity {

    ListView lv;
    GridView bottomMenuGrid;
    LinearLayout mainSearchLayout;
    EditText searchText;
    SimpleAdapter adapter;
    ArrayList list = new ArrayList(10);
    ArrayList<Integer> deleteId;

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

        if (list.size() == 0) {
            setTitle("no data!");
        }
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main.this, UserDetail.class);
                intent.putExtra("id", position);
                startActivityForResult(intent, position);
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (deleteId == null) {
                    deleteId = new ArrayList<Integer>(10);
                }

                LinearLayout L = (LinearLayout)view;
                ImageView markedview = (ImageView)L.getChildAt(2);

                if (markedview.getVisibility() == View.VISIBLE) {
                    markedview.setVisibility(View.GONE);
                    deleteId.remove(position);
                }
                else {
                    markedview.setVisibility(View.VISIBLE);
                    deleteId.add(position);
                }
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);    //To change body of overridden methods use File | Settings | File Templates.

        if (resultCode == 3) {
            list = DBHelper.getAllUsers();
            adapter = new SimpleAdapter(
                    this, list, R.layout.listitem,
                    new String[]{"imageid", "name", "mobile"},
                    new int[]{R.id.ListViewImage, R.id.ListViewName, R.id.ListViewMobile}
            );

        }
        lv.setAdapter(adapter);

        if (resultCode == 3)
            lv.setSelection(list.size());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            loadBottomMenu();

            if (bottomMenuGrid.getVisibility() == View.VISIBLE) {
                if (mainSearchLayout != null && mainSearchLayout.getVisibility() == View.VISIBLE)
                    mainSearchLayout.setVisibility(View.GONE);
                bottomMenuGrid.setVisibility(View.GONE);
            }
            else {
                bottomMenuGrid.setVisibility(View.VISIBLE);
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
            bottomMenuGrid.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            if (bottomMenuGrid.getVisibility() == View.VISIBLE) {
                                bottomMenuGrid.setVisibility(View.GONE);
                            }

                            Intent intent = new Intent(Main.this, AddNew.class);
                            startActivityForResult(intent, 3);
                            break;
                        case 1:
                            loadSearchLayout();

                            /**
                             * 下面将隐藏的搜索框显示出来...
                             */
                            if (mainSearchLayout.getVisibility() == View.GONE) {
                                mainSearchLayout.setVisibility(View.VISIBLE);
                            }
                            else {
                                mainSearchLayout.setVisibility(View.GONE);
                            }
                            break;
                        case 2:
                            if (bottomMenuGrid.getVisibility() == View.VISIBLE) {
                                bottomMenuGrid.setVisibility(View.GONE);
                            }
                            if (deleteId == null) {
                                Toast.makeText(Main.this, "deleteId is empty", Toast.LENGTH_LONG).show();
                                break;
                            }
                            new AlertDialog.Builder(Main.this)
                                    .setTitle("delete " + deleteId.size() + " data(s)?")
                                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            boolean flag = DBHelper.deleteMarked(deleteId);
                                            if (flag) setTitle("delete success!");
                                            /**
                                             * 删除成功，下面显示删除后的list..
                                             */
                                            list = DBHelper.getAllUsers();
                                            adapter = new SimpleAdapter(
                                                    Main.this, list, R.layout.listitem,
                                                    new String[]{"imageid", "name", "mobile"},
                                                    new int[]{R.id.ListViewImage, R.id.ListViewName, R.id.ListViewMobile}
                                            );
                                            lv.setAdapter(adapter);
                                            deleteId.clear();
                                        }
                                    })
                                    .setNegativeButton("no", null)
                                    .create().show();
                            break;
                        case 3:finish();break;
                    }
                }
            });
        }
        //To change body of created methods use File | Settings | File Templates.
    }

    private void loadSearchLayout() {
        mainSearchLayout = (LinearLayout)findViewById(R.id.main_LinearLayoutForSearch);
        searchText = (EditText)findViewById(R.id.main_SearchText);
        /**
         * 下面添加监听器，搜索框输入关键字，实时去DB中查找...
         */
        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String needSearch = searchText.getText().toString();

                list = DBHelper.getUsers(needSearch);

                adapter = new SimpleAdapter(
                        Main.this, list, R.layout.listitem,
                        new String[]{"imageid", "name", "mobile"},
                        new int[]{R.id.ListViewImage, R.id.ListViewName, R.id.ListViewMobile}
                );

                lv.setAdapter(adapter);

                if (list.size() == 0) {
                    setTitle("no data found");
                }
                else {
                    setTitle("total: " + list.size());
                }
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
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
                this, datalist, R.layout.menuitem,
                new String[] {"ItemImage", "ItemText"}, new int[] {R.id.ItemImage, R.id.ItemText}
        );
        return adapter;
    }
}
