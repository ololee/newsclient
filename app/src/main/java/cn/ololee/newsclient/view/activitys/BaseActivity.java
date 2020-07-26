package cn.ololee.newsclient.view.activitys;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.ololee.newsclient.utils.ActivityCollector;
import cn.ololee.newsclient.utils.ColorUtils;

import static cn.ololee.newsclient.utils.Constants.COLOR_GREEN;
import static cn.ololee.newsclient.utils.Constants.PREFREENCES_NAME;
import static cn.ololee.newsclient.utils.Constants.PRIMARY_COLOR;

public class BaseActivity extends AppCompatActivity {
    protected  int backgroundColor;
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;
    protected int savedColor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView=getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initData();
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }


    protected void initData(){
        sharedPreferences=getSharedPreferences(PREFREENCES_NAME,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        savedColor = sharedPreferences.getInt(PRIMARY_COLOR, COLOR_GREEN);
        backgroundColor= ColorUtils.getPrimaryColor(savedColor);
    }


    protected void finishAll()
    {
        ActivityCollector.finishAll();
    }
}
