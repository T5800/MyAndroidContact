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
 * Date: 13-6-25
 * Time: 下午12:20
 * To change this template use File | Settings | File Templates.
 */
public class UserDetail extends Activity{
    EditText name;
    EditText mobile;
    EditText email;

    ImageButton imageButton;
    Button change;
    Button ok;
    Button back;

    User user;
    int id;

    AlertDialog imageChooseAlertDialog;
    boolean imageChanged = false;
    int currentImagePosition;
    int previousImagePosition;
    View imageChoosView;
    Gallery gallery;
    ImageSwitcher imageSwitcher;

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
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(3);
                finish();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImage();
                initImageChooseAlertDialog();
                imageChooseAlertDialog.show();
            }

            private void initImageChooseAlertDialog() {

                if (imageChooseAlertDialog == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserDetail.this);
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
    }

    public void loadImage() {
        if (imageChoosView == null) {
            LayoutInflater LI = LayoutInflater.from(UserDetail.this);
            imageChoosView = LI.inflate(R.layout.imageswitch, null);

            gallery = (Gallery)imageChoosView.findViewById(R.id.gallery);
            gallery.setAdapter(new MyImageAdapter(this));
            gallery.setSelection(user.getImageid());//图片选项从当前开始...

            imageSwitcher = (ImageSwitcher)imageChoosView.findViewById(R.id.imageswitch);
            imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    ImageView view = new ImageView(UserDetail.this);
                    view.setBackgroundColor(0x00000000);
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

    private void modify() {

        if (imageChanged) {
            user.setImageid(imageids[currentImagePosition%imageids.length]);
        }
        user.setName(name.getText().toString());
        user.setMobile(mobile.getText().toString());
        user.setEmail(email.getText().toString());

        boolean flag = DBHelper.update(id, user);
        if (flag) {
            setTitle("change success!");
        }
    }

    private void setTextEnable() {
        imageButton.setEnabled(true);
        name.setEnabled(true);
        mobile.setEnabled(true);
        email.setEnabled(true);
    }

    private void loadUserData() {
        name = (EditText)findViewById(R.id.UserDetailName);
        mobile = (EditText)findViewById(R.id.UserDetailMobile);
        email = (EditText)findViewById(R.id.UserDetailEmail);

        imageButton = (ImageButton)findViewById(R.id.UserDetailImage);
        change = (Button)findViewById(R.id.UserDetailChange);
        ok = (Button)findViewById(R.id.UserDetailOK);
        back = (Button)findViewById(R.id.UserDetailReturn);

        imageButton.setImageResource(user.getImageid());
        name.setText(user.getName());
        mobile.setText(user.getMobile());
        email.setText(user.getEmail());

    }

    private void setEditTextDisable() {
        imageButton.setEnabled(false);
        name.setEnabled(false);
        mobile.setEnabled(false);
        email.setEnabled(false);

        //To change body of created methods use File | Settings | File Templates.

    }
}
