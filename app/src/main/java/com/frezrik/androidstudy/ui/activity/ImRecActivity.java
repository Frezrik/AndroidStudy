package com.frezrik.androidstudy.ui.activity;

import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;
import com.frezrik.androidstudy.R;
import com.frezrk.support.utils.Aes;
import com.frezrk.support.utils.Des;

public class ImRecActivity
        extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_rec);

        initView();
    }

    //https://blog.csdn.net/zmx729618/article/details/78125548
    //https://blog.csdn.net/z240336124/article/details/81385195
    private void initView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("zmzm", "getPinYinFirstLetter");
                    String letter = Aes.getPinYinFirstLetter("周");
                    // String encrypt = Des.encrypt("123", "123456");
                    //Toast.makeText(this, letter, Toast.LENGTH_SHORT).show();
                    Log.d("zmzm", letter);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("zmzm", e.toString());
                    //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        }).start();


    }

}
