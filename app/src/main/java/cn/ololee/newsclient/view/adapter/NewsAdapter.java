package cn.ololee.newsclient.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ololee.newsclient.R;
import cn.ololee.newsclient.bean.NewsItem;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG=NewsAdapter.class.getSimpleName();
    private static final int NEWS_ITEM=1;
    private Context context;
    private CopyOnWriteArrayList<NewsItem> data;
    private OnItemClickListener itemClickListener;
    private LoadMoreListener loadMoreListener;
    private HashSet<Integer> loadedPosition=new HashSet<>();


    public NewsAdapter(Context context, CopyOnWriteArrayList<NewsItem> data) {
        this.context = context;
        this.data = data;
    }

     class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.newsSimpleListItemTime)
        TextView time;
        @BindView(R.id.newsSimpleListItemParent)
        LinearLayout item;
        @BindView(R.id.newsSimpleListItemPicture)
        ImageView picture;
        @BindView(R.id.newsSimpleListItemSource)
        Button source;
        @BindView(R.id.newsSimpleListItemTitle)
        TextView title;
        @BindView(R.id.newsSimpleListItemContent)
        TextView content;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=null;
        RecyclerView.ViewHolder holder=null;
       switch (viewType)
       {
           case NEWS_ITEM:
               itemView= LayoutInflater.from(context).inflate(R.layout.simple_news_item,parent,false);
               holder=new NewsViewHolder(itemView);
               break;
       }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NewsViewHolder)
        {
            if(position==getItemCount()-2&&!loadedPosition.contains(position))
            {
               if(loadMoreListener!=null) {
                   loadedPosition.add(position);
                   loadMoreListener.loadmore();
               }
            }
            NewsViewHolder newsViewHolder= (NewsViewHolder) holder;
            NewsItem newsItem=data.get(position);
            newsViewHolder.time.setText(newsItem.getTime());
            newsViewHolder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener!=null)
                        itemClickListener.onClick(newsItem);
                }
            });
            Glide.with(context).load(newsItem.getPicture()).into(newsViewHolder.picture);
            newsViewHolder.source.setText(newsItem.getSource());
            newsViewHolder.title.setText(newsItem.getTitle());
            String content=newsItem.getContent().replaceAll("&nbsp;"," ");
            newsViewHolder.content.setText(content.substring(0,Math.min(content.length()-1,50)).trim());
            newsViewHolder.content.append("...");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return NEWS_ITEM;
    }

    public interface OnItemClickListener{
        void onClick(NewsItem newsItem);
    }

    public interface LoadMoreListener{
        void loadmore();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void removeAllSet() {
        loadedPosition.removeAll(loadedPosition);
        Log.i(TAG, "removeAllSet: "+loadedPosition);
    }
}
