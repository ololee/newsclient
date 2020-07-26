package cn.ololee.newsclient.view.activitys;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

/*启动页*/
public class SplashActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}