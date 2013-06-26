package com.example.MyAndroidContact;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.*;

/**
 * Created with IntelliJ IDEA.
 * User: hijack
 * Date: 13-6-20
 * Time: 下午9:34
 * To change this template use File | Settings | File Templates.
 */
public class AddNew extends Activity {

    EditText name ;
    EditText email;
    EditText mobile;

    ImageButton imageButton;
    Button save;
    Button back;

    View imageChoosView;
    AlertDialog imageChooseAlertDialog;
    Gallery gallery;
    ImageSwitcher imageSwitcher;

    int currentImagePosition;
    int previousImagePosition;
    boolean imageChanged = false;

    int[] imageids = new int[]{
      R.drawable.ic_launcher,
      R.drawable.image27,
      R.drawable.ic_launcher,
      R.drawable.image27,
      R.drawable.ic_launcher,
      R.drawable.image27,
      R.drawable.ic_launcher,
      R.drawable.image27
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.addnew);
        Intent intent = getIntent();

        name = (EditText)findViewById(R.id.nameForAddNew);
        mobile = (EditText)findViewById(R.id.MobileForAddNew);
        email = (EditText)findViewById(R.id.EmailForAddNew);

        imageButton = (ImageButton)findViewById(R.id.TouXiangForAddNew);
        save = (Button)findViewById(R.id.SaveForAddNew);
        back = (Button)findViewById(R.id.ReturnForAddNew);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImage();
                initImageChooseAlertDialog();
                imageChooseAlertDialog.show();
            }

            private void initImageChooseAlertDialog() {

                if (imageChooseAlertDialog == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddNew.this);
                    builder = builder.setTitle("choose Image")
                            .setView(imageChoosView)
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    imageChanged = true;
                                    previousImagePosition = currentImagePosition;
                                    imageButton.setImageResource(imageids[currentImagePosition%imageids.length]);
                                }
                            })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currentImagePosition = previousImagePosition;
                        }
                    });
                    imageChooseAlertDialog = builder.create();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString();
                if (nameText.equals("")) {
                    Toast.makeText(AddNew.this, "name can not be empty!", Toast.LENGTH_LONG).show();
                    return;
                }

                User user = new User(
                        imageids[currentImagePosition%imageids.length],
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

    public void loadImage() {
        if (imageChoosView == null) {
            LayoutInflater LI = LayoutInflater.from(AddNew.this);
            imageChoosView = LI.inflate(R.layout.imageswitch, null);

            gallery = (Gallery)imageChoosView.findViewById(R.id.gallery);
            gallery.setAdapter(new MyImageAdapter(this));
            gallery.setSelection(0);//图片选项从第一个开始...

            imageSwitcher = (ImageSwitcher)imageChoosView.findViewById(R.id.imageswitch);
            imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    ImageView view = new ImageView(AddNew.this);
                    view.setBackgroundColor(0xff00ff0f);
                    view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    view.setLayoutParams(new ImageSwitcher.LayoutParams(90,90));
                    return view;
                }
            });
            imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
            //卸载图片的动画效果
            imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
            gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    currentImagePosition = position;
                    imageSwitcher.setImageResource(imageids[position%imageids.length]);
                }

            });

        }
    }

    class MyImageAdapter extends BaseAdapter {

        private Context context;

        MyImageAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getItem(int position) {
            return position;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public long getItemId(int position) {
            return position;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView iv = new ImageView(context);
            iv.setImageResource(imageids[position%imageids.length]);
            iv.setAdjustViewBounds(true);
            iv.setLayoutParams(new Gallery.LayoutParams(80,80));
            iv.setPadding(15, 10, 15, 10);
            return iv;
        }
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }


}
