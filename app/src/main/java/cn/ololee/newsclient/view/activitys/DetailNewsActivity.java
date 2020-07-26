package cn.ololee.newsclient.view.activitys;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ololee.newsclient.R;
import cn.ololee.newsclient.bean.NewsItem;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import static cn.ololee.newsclient.utils.Constants.INTENT_NEWS_ITEM;

/*
* 详细新闻展示页
* */
public class DetailNewsActivity extends BaseActivity {
    @BindView(R.id.newsDetailCollapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.newsDetailImageView)
    ImageView picture;
    @BindView(R.id.newsDetailToolbar)
    Toolbar toolbar;
    @BindView(R.id.newsDetailTime)
    TextView time;
    @BindView(R.id.newsDetailContent)
    TextView content;
    @BindView(R.id.newsDetailUrl)
    TextView newsUrlLink;
    private NewsItem newsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        ButterKnife.bind(this);
        init();
    }

    private void init()
    {
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(true);
         newsItem= getIntent().getParcelableExtra(INTENT_NEWS_ITEM);
         initColor();
         initView();
    }

    private void initView()
    {
        if(newsItem==null)
            return;
        collapsingToolbar.setTitle(newsItem.getTitle());
        Glide.with(this).load(newsItem.getPicture()).into(picture);
        time.setText("时间: "+newsItem.getTime().substring(0,newsItem.getTime().length()-3));
        content.setText(newsItem.getContent().replaceAll("&nbsp;"," "));
        SpannableString spannableString=new SpannableString(newsItem.getTitle());
        spannableString.setSpan(new URLSpan(newsItem.getUrl()),0,newsItem.getTitle().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        newsUrlLink.setMovementMethod(LinkMovementMethod.getInstance());
        newsUrlLink.setText(spannableString);
        newsUrlLink.setLinkTextColor(Color.BLUE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*颜色初始化*/
    private void initColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            collapsingToolbar.setContentScrimColor(getColor(backgroundColor));
            collapsingToolbar.setStatusBarScrimColor(getColor(backgroundColor));
        }
    }
}