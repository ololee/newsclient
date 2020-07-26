package cn.ololee.newsclient.view.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ololee.newsclient.R;
import cn.ololee.newsclient.utils.ColorUtils;
import cn.ololee.newsclient.view.adapter.ColorChooseAdapter;

import static cn.ololee.newsclient.utils.Constants.CHANNEL_ID;
import static cn.ololee.newsclient.utils.Constants.CHOOSED_COLOR;
import static cn.ololee.newsclient.utils.Constants.COLORS;
import static cn.ololee.newsclient.utils.Constants.LIMIT;
import static cn.ololee.newsclient.utils.Constants.LIMITS;
import static cn.ololee.newsclient.utils.Constants.PRIMARY_COLOR;
import static cn.ololee.newsclient.utils.Constants.TIME_CHOOSE;
import static cn.ololee.newsclient.utils.TimeConstant.CURRENTDAY;

public class SettingsActivity extends BaseActivity {
    @BindView(R.id.settingToolbar)
    Toolbar settingToolbar;
    @BindView(R.id.settingSave)
    TextView save;
    @BindView(R.id.date_chooser)
    Spinner timeChooser;
    @BindView(R.id.channel_chooser)
    Spinner channelChooser;
    @BindView(R.id.limit_chooser)
    Spinner limitChooser;
    private int time;
    private int channelId;
    private int limit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        init();
    }

    /*
     * 初始化
     * */
    private void init() {
        initSettingData();
        initActionBar();
        initListener();
    }

    /*
    * 监听事件
    * */
    private void initListener() {
        timeChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time=position+1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        channelChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                channelId=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        limitChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                limit=LIMITS[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /*
     * 初始化actionbar
     * */
    private void initActionBar() {
        setSupportActionBar(settingToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            settingToolbar.setBackgroundColor(getColor(backgroundColor));
        }
    }

    /*
     * 初始化GridLayoutData
     * */
    private void initSettingData() {
        Intent intent=getIntent();
        time=intent.getIntExtra(TIME_CHOOSE,CURRENTDAY);
        timeChooser.setSelection(time-1,false);
        channelId=intent.getIntExtra(CHANNEL_ID,0);
        channelChooser.setSelection(channelId,false);
        limit=intent.getIntExtra(LIMIT,10);
        for (int i = 0; i < LIMITS.length; i++) {
            if(LIMITS[i]==limit)
            {
                limitChooser.setSelection(i,false);
                break;
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.settingSave)
    public void save() {
        editor.putInt(TIME_CHOOSE,time);
        editor.putInt(CHANNEL_ID,channelId);
        editor.putInt(LIMIT,limit);
        editor.commit();
        setResult(RESULT_OK,new Intent().
                putExtra(TIME_CHOOSE,time).
                putExtra(CHANNEL_ID,channelId).
                putExtra(LIMIT,limit));
        finish();
    }


}