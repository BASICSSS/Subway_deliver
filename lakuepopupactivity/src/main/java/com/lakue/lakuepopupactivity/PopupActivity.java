package com.lakue.lakuepopupactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;




import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.os.FileUtils.copy;

public class PopupActivity extends Activity {


    TextView tv_title;
    TextView tv_content; //원래 이거
    TextView tv_user;
    TextView tv_obj;
    TextView tv_pickupAdd;
    TextView tv_receiver;
    TextView tv_receiverPhone;
    TextView tv_receiverAdd;
    TextView tv_objType;


    Button btn_ok;
    Button btn_left;
    Button btn_right;
    ImageView iv_popup_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //데이터 가져오기
        Intent intent = getIntent();
        PopupType type = (PopupType) intent.getSerializableExtra("type");

        String title = "";
        String content = "";
        String user = ""; //셀럭트에서 발송인으로
        String obj = "";
        String objType = "";
        String pickupAdd = "";
        String receiver = "";
        String receiverPhone = "";
        String receiverAdd = "";
        String buttonCenter = "";
        String buttonLeft = "";
        String buttonRight = "";
        PopupGravity gravity;

        if (type == PopupType.NORMAL) {
            setContentView(R.layout.activity_popup);
            title = intent.getStringExtra("title");
            content = intent.getStringExtra("content");
            buttonCenter = intent.getStringExtra("buttonCenter");
            gravity = (PopupGravity) intent.getSerializableExtra("gravity");

            //UI 객체생성
            tv_title = (TextView) findViewById(R.id.tv_title);
            tv_content = (TextView) findViewById(R.id.tv_content);
            btn_ok = (Button) findViewById(R.id.btn_ok);

            if(gravity == PopupGravity.CENTER){
                tv_title.setGravity(Gravity.CENTER);
                tv_content.setGravity(Gravity.CENTER);
            } else if(gravity == PopupGravity.LEFT){
                tv_title.setGravity(Gravity.LEFT);
                tv_content.setGravity(Gravity.LEFT);
            } else if(gravity == PopupGravity.RIGHT){
                tv_title.setGravity(Gravity.RIGHT);
                tv_content.setGravity(Gravity.RIGHT);
            }


            tv_title.setText(title);
            tv_content.setText(content);
            btn_ok.setText(buttonCenter);

            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //데이터 전달하기

                    Intent intent = new Intent();
                    intent.putExtra("result", PopupResult.CENTER);
                    setResult(RESULT_OK, intent);

                    //액티비티(팝업) 닫기
                    finish();
                }
            });
        } else if (type == PopupType.SELECT) {
            setContentView(R.layout.activity_popup_select);
            title = intent.getStringExtra("title");
//            content = intent.getStringExtra("content");
            user = intent.getStringExtra("user");
            obj = intent.getStringExtra("obj");
            pickupAdd = intent.getStringExtra("pickupAdd");
            receiver = intent.getStringExtra("receiver");
            receiverPhone = intent.getStringExtra("receiverPhone");
            receiverAdd = intent.getStringExtra("receiverAdd");
            objType = intent.getStringExtra("objType");
            buttonLeft = intent.getStringExtra("buttonLeft");
            buttonRight = intent.getStringExtra("buttonRight");
            gravity = (PopupGravity) intent.getSerializableExtra("gravity");

            //UI 객체생성
            tv_title = (TextView) findViewById(R.id.tv_title);
            tv_user = (TextView) findViewById(R.id.tv_user);
            tv_obj = (TextView) findViewById(R.id.tv_obj);
            tv_pickupAdd = (TextView) findViewById(R.id.tv_pickupAdd);
            tv_receiver = (TextView) findViewById(R.id.tv_receiver);
            tv_receiverPhone = (TextView) findViewById(R.id.tv_receiverPhone);
            tv_receiverAdd = (TextView) findViewById(R.id.tv_receiverAdd);
            tv_objType = (TextView) findViewById(R.id.tv_objType);

            btn_left = (Button) findViewById(R.id.btn_left);
            btn_right = (Button) findViewById(R.id.btn_right);

            if(gravity == PopupGravity.CENTER){
                tv_title.setGravity(Gravity.CENTER);
                tv_user.setGravity(Gravity.CENTER);
                tv_obj.setGravity(Gravity.CENTER);
                tv_pickupAdd.setGravity(Gravity.CENTER);
                tv_receiver.setGravity(Gravity.CENTER);
                tv_receiverPhone.setGravity(Gravity.CENTER);
                tv_receiverAdd.setGravity(Gravity.CENTER);
                tv_objType.setGravity(Gravity.CENTER);

            } else if(gravity == PopupGravity.LEFT){
                tv_title.setGravity(Gravity.LEFT);
                tv_user.setGravity(Gravity.LEFT);
                tv_obj.setGravity(Gravity.LEFT);
                tv_pickupAdd.setGravity(Gravity.LEFT);
                tv_receiver.setGravity(Gravity.LEFT);
                tv_receiverPhone.setGravity(Gravity.LEFT);
                tv_receiverAdd.setGravity(Gravity.LEFT);
                tv_objType.setGravity(Gravity.LEFT);

            } else if(gravity == PopupGravity.RIGHT){
                tv_title.setGravity(Gravity.RIGHT);
                tv_user.setGravity(Gravity.RIGHT);
                tv_obj.setGravity(Gravity.RIGHT);
                tv_pickupAdd.setGravity(Gravity.RIGHT);
                tv_receiver.setGravity(Gravity.RIGHT);
                tv_receiverPhone.setGravity(Gravity.RIGHT);
                tv_receiverAdd.setGravity(Gravity.RIGHT);
                tv_objType.setGravity(Gravity.RIGHT);

            }

            tv_title.setText(title);
//            tv_user.setText(content);
            tv_user.setText(user);
            tv_obj.setText(obj);
            tv_pickupAdd.setText(pickupAdd);
            tv_receiver.setText(receiver);
            tv_receiverPhone.setText(receiverPhone);
            tv_receiverAdd.setText(receiverAdd);
            tv_objType.setText(objType);

            btn_left.setText(buttonLeft);
            btn_right.setText(buttonRight);

            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //데이터 전달하기
                    Intent intent = new Intent();
                    intent.putExtra("result", PopupResult.RIGHT);
                    setResult(RESULT_OK, intent);

                    //액티비티(팝업) 닫기
                    finish();
                }
            });

            btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //데이터 전달하기
                    Intent intent = new Intent();
                    intent.putExtra("result", PopupResult.LEFT);
                    setResult(RESULT_OK, intent);

                    //액티비티(팝업) 닫기
                    finish();
                }
            });
        } else if (type == PopupType.ERROR) {
            setContentView(R.layout.activity_popup_error);
            title = intent.getStringExtra("title");
            content = intent.getStringExtra("content");
            buttonRight = intent.getStringExtra("buttonRight");
            gravity = (PopupGravity) intent.getSerializableExtra("gravity");

            //UI 객체생성
            tv_title = (TextView) findViewById(R.id.tv_title);
            tv_content = (TextView) findViewById(R.id.tv_content);
            btn_ok = (Button) findViewById(R.id.btn_ok);

            tv_title.setText(title);
            tv_content.setText(content);
            btn_ok.setText(buttonRight);

            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //데이터 전달하기
                    Intent intent = new Intent();
                    intent.putExtra("result", PopupResult.CENTER);
                    setResult(RESULT_OK, intent);

                    //액티비티(팝업) 닫기
                    finish();
                }
            });
        } else if (type == PopupType.IMAGE) {
            setContentView(R.layout.activity_popup_image);
            title = intent.getStringExtra("title");
            buttonLeft = intent.getStringExtra("buttonLeft");
            buttonRight = intent.getStringExtra("buttonRight");

            //UI 객체생성
            btn_left = (Button) findViewById(R.id.btn_left);
            btn_right = (Button) findViewById(R.id.btn_right);
            iv_popup_image = (ImageView) findViewById(R.id.iv_popup_image);

            btn_left.setText(buttonLeft);
            btn_right.setText(buttonRight);
            new DownloadFilesTask().execute(title);
            //DownloadFullFromUrl(title, iv_popup_image);
            //Glide.with(this).load(title).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(iv_popup_image);

            iv_popup_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //데이터 전달하기
                    Intent intent = new Intent();
                    intent.putExtra("result", PopupResult.IMAGE);
                    setResult(RESULT_OK, intent);

                    //액티비티(팝업) 닫기
                    finish();
                }
            });


            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //데이터 전달하기
                    Intent intent = new Intent();
                    intent.putExtra("result", PopupResult.RIGHT);
                    setResult(RESULT_OK, intent);

                    //액티비티(팝업) 닫기
                    finish();
                }
            });

            btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //데이터 전달하기
                    Intent intent = new Intent();
                    intent.putExtra("result", PopupResult.LEFT);
                    setResult(RESULT_OK, intent);

                    //액티비티(팝업) 닫기
                    finish();
                }
            });

        } else if( type == PopupType.EMPTY_SELECT ){


            setContentView(R.layout.activity_popup_empty_select);
            title = intent.getStringExtra("title");
            content = intent.getStringExtra("content");
            buttonLeft = intent.getStringExtra("buttonLeft");
            buttonRight = intent.getStringExtra("buttonRight");
            gravity = (PopupGravity) intent.getSerializableExtra("gravity");

            //UI 객체생성
            tv_title = (TextView) findViewById(R.id.tv_title);
            tv_content = (TextView) findViewById(R.id.tv_content);
            btn_left = (Button) findViewById(R.id.btn_left);
            btn_right = (Button) findViewById(R.id.btn_right);

            if(gravity == PopupGravity.CENTER){
                tv_title.setGravity(Gravity.CENTER);
                tv_content.setGravity(Gravity.CENTER);
            } else if(gravity == PopupGravity.LEFT){
                tv_title.setGravity(Gravity.LEFT);
                tv_content.setGravity(Gravity.LEFT);
            } else if(gravity == PopupGravity.RIGHT){
                tv_title.setGravity(Gravity.RIGHT);
                tv_content.setGravity(Gravity.RIGHT);
            }

            tv_title.setText(title);
            tv_content.setText(content);
            btn_left.setText(buttonLeft);
            btn_right.setText(buttonRight);

            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //데이터 전달하기
                    Intent intent = new Intent();
                    intent.putExtra("result", PopupResult.RIGHT);
                    setResult(RESULT_OK, intent);

                    //액티비티(팝업) 닫기
                    finish();
                }
            });

            btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //데이터 전달하기
                    String order = intent.getStringExtra("order");
                    Intent intent = new Intent();
                    intent.putExtra("result", PopupResult.LEFT);
                    intent.putExtra("idx", order);
                    setResult(RESULT_OK, intent);

                    //액티비티(팝업) 닫기
                    finish();
                }
            });


        }

//
    }


    private class DownloadFilesTask extends AsyncTask<String,Void,Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bmp = null;
            try {
                String img_url = strings[0]; //url of the image
                URL url = new URL(img_url);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            // doInBackground 에서 받아온 total 값 사용 장소
            iv_popup_image.setImageBitmap(result);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
