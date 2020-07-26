package cn.ololee.newsclient.view.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ololee.newsclient.R;
import cn.ololee.newsclient.bean.NewsBean;
import cn.ololee.newsclient.bean.NewsItem;
import cn.ololee.newsclient.network.GetNewsService;
import cn.ololee.newsclient.utils.ColorUtils;
import cn.ololee.newsclient.utils.NetworkUtils;
import cn.ololee.newsclient.view.adapter.NewsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static cn.ololee.newsclient.utils.Constants.CHANNEL_ID;
import static cn.ololee.newsclient.utils.Constants.CHOOSED_COLOR;
import static cn.ololee.newsclient.utils.Constants.COLOR_ACCENT;
import static cn.ololee.newsclient.utils.Constants.COLOR_BLUE;
import static cn.ololee.newsclient.utils.Constants.COLOR_GREEN;
import static cn.ololee.newsclient.utils.Constants.COLOR_PURPLE;
import static cn.ololee.newsclient.utils.Constants.COLOR_RED;
import static cn.ololee.newsclient.utils.Constants.COLOR_YELLOW;
import static cn.ololee.newsclient.utils.Constants.INTENT_NEWS_ITEM;
import static cn.ololee.newsclient.utils.Constants.LIMIT;
import static cn.ololee.newsclient.utils.Constants.PRIMARY_COLOR;
import static cn.ololee.newsclient.utils.Constants.TIME_CHOOSE;
import static cn.ololee.newsclient.utils.TimeConstant.CURRENTDAY;

public class MainActivity extends BaseActivity {
    public static final String TAG=MainActivity.class.getSimpleName();
    private static final int COLOR_ACTIVITY_ID=1;
    private static final int SETTING_ACTIVITY_ID=2;
    @BindView(R.id.newsSimpleList)
    RecyclerView recyclerView;
    @BindView(R.id.homeToolbar)
    Toolbar toolbar;
    @BindView(R.id.homeDrawLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.homeTopBarLayout)
    RelativeLayout homeTopBarLayout;
    @BindView(R.id.homeIcon)
    ImageView homeIcon;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    private CopyOnWriteArrayList<NewsItem> newsItems=new CopyOnWriteArrayList<>();
    private static int channelId=0;
    private static int time=3;
    private static int limit=10;
    private NewsAdapter newsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    /*
    * 初始化
    * */
    private void init() {
        initActionBar();
        initDrawerLayout();
        initColor();
        initAdapter();
        initRecyclerView();
    }


    /*
    * 初始化标题栏
    * */
    private void initActionBar(){
        setSupportActionBar(toolbar);
    }

    /*初始化侧拉栏*/
    private void initDrawerLayout() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                /*
                * 侧拉栏交互处理
                * */
                switch (item.getItemId())
                {
                    case R.id.skinSetting:
                        Intent intent=new Intent(MainActivity.this,ColorSettingsActivity.class);
                        intent.putExtra(CHOOSED_COLOR,savedColor);
                        startActivityForResult(intent,COLOR_ACTIVITY_ID);
                        break;
                    case R.id.setting:
                        Intent settingIntent=new Intent(MainActivity.this,SettingsActivity.class);
                        settingIntent.putExtra(TIME_CHOOSE,time);
                        settingIntent.putExtra(CHANNEL_ID,channelId);
                        settingIntent.putExtra(LIMIT,limit);
                        startActivityForResult(settingIntent,SETTING_ACTIVITY_ID);
                        break;
                }
                return true;
            }
        });
    }

    /*颜色初始化*/
    private void initColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setBackgroundColor(getColor(backgroundColor));
            homeTopBarLayout.setBackgroundColor(getColor(backgroundColor));
        }
    }

    /*
    * 初始化数据
    * */
    @Override
    protected void initData(){
        super.initData();
        time=sharedPreferences.getInt(TIME_CHOOSE,CURRENTDAY);
        channelId=sharedPreferences.getInt(CHANNEL_ID,0);
        limit=sharedPreferences.getInt(LIMIT,10);
        loadMoreData(channelId,time,0,limit);
    }

    /*
    * 初始化适配器
    * */
    private void initAdapter(){
        newsAdapter=new NewsAdapter(this,newsItems);
        newsAdapter.setLoadMoreListener(new NewsAdapter.LoadMoreListener() {
            @Override
            public void loadmore() {
               loadMoreData(channelId,time,newsAdapter.getItemCount(),limit);
            }
        });
        newsAdapter.setItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onClick(NewsItem newsItem) {
                Intent intent=new Intent(MainActivity.this,DetailNewsActivity.class);
                intent.putExtra(INTENT_NEWS_ITEM,newsItem);
                startActivity(intent);
            }
        });
    }

    /*
    * 初始化RecyclerView
    * */
    private void initRecyclerView(){
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(newsAdapter);
    }

    /*
     * 加载更多数据
     * */
    private void loadMoreData(int channelId,int time,int skip,int limit){
        GetNewsService getNewsService=NetworkUtils.getRetrofit().create(GetNewsService.class);
        Call<NewsBean> call=getNewsService.getNews(channelId,time,skip,limit);
        call.enqueue(new Callback<NewsBean>() {
            @Override
            public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
                newsItems.addAll(response.body().getNewsItemList());
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsBean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }



    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            finishAll();
        }
    }

    @OnClick(R.id.homeIcon)
    public void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case COLOR_ACTIVITY_ID:
                if(resultCode == Activity.RESULT_OK)
                {
                    savedColor=data.getIntExtra(CHOOSED_COLOR,0);
                    backgroundColor=ColorUtils.getPrimaryColor(savedColor);
                    initColor();
                }
                break;
            case SETTING_ACTIVITY_ID:
                if(resultCode==RESULT_OK)
                {
                    time=data.getIntExtra(TIME_CHOOSE,CURRENTDAY);
                    channelId=data.getIntExtra(CHANNEL_ID,0);
                    limit=data.getIntExtra(LIMIT,10);
                    if(!newsItems.isEmpty())
                    {
                        for (int i = newsItems.size()-1; i >=0 ; i--) {
                            newsItems.remove(i);
                        }
                        newsAdapter.removeAllSet();
                        newsAdapter.notifyDataSetChanged();
                    }
                    loadMoreData(channelId,time,0,limit);
                }
                break;
            default:
                break;
        }
    }
}
