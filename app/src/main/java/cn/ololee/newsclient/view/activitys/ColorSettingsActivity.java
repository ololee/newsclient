package cn.ololee.newsclient.view.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ololee.newsclient.R;
import cn.ololee.newsclient.utils.ColorUtils;
import cn.ololee.newsclient.view.adapter.ColorChooseAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static cn.ololee.newsclient.utils.Constants.CHOOSED_COLOR;
import static cn.ololee.newsclient.utils.Constants.COLORS;
import static cn.ololee.newsclient.utils.Constants.PRIMARY_COLOR;

public class ColorSettingsActivity extends BaseActivity {
    @BindView(R.id.colorToolbar)
    Toolbar colorToolbar;
    @BindView(R.id.colorChooseGrid)
    RecyclerView colorChooseGrid;
    @BindView(R.id.colorChooseSave)
    TextView save;
    private int selection = -1;
    private List<Integer> gridData;
    private ColorChooseAdapter colorChooseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_settings);
        ButterKnife.bind(this);
        init();
    }

    /*
     * 初始化
     * */
    private void init() {
        initGridData();
        initActionBar();
        initRecyclerView();
    }

    /*
     * 初始化actionbar
     * */
    private void initActionBar() {
        setSupportActionBar(colorToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            colorToolbar.setBackgroundColor(getColor(ColorUtils.getPrimaryColor(selection)));
        }
    }

    /*
     * 初始化GridLayoutData
     * */
    private void initGridData() {
        selection=getIntent().getIntExtra(CHOOSED_COLOR,0);
        gridData = new ArrayList<>();
        for (int color : COLORS) {
            gridData.add(color);
        }
        colorChooseAdapter = new ColorChooseAdapter(this, gridData, selection);
        colorChooseAdapter.setCallback(new ColorChooseAdapter.Callback() {
            @Override
            public void callback(int position) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    backgroundColor= getColor(ColorUtils.getPrimaryColor(gridData.get(position)));
                    colorToolbar.setBackgroundColor(backgroundColor);
                }
            }
        });
    }

    private void initRecyclerView() {
        colorChooseGrid.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        colorChooseGrid.setLayoutManager(gridLayoutManager);
        colorChooseGrid.setAdapter(colorChooseAdapter);
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

    @OnClick(R.id.colorChooseSave)
    public void save() {
        selection = colorChooseAdapter.getChoosePosition();
        if (selection != -1) {
            editor.putInt(PRIMARY_COLOR, selection);
            editor.commit();
            setResult(Activity.RESULT_OK,new Intent().putExtra(CHOOSED_COLOR,selection));
            finish();
        } else {
            Toast.makeText(this, "请先选择颜色", Toast.LENGTH_SHORT).show();
        }

    }
}