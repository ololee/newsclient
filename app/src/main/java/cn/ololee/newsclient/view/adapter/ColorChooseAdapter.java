package cn.ololee.newsclient.view.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ololee.newsclient.R;
import cn.ololee.newsclient.utils.ColorUtils;

public class ColorChooseAdapter extends RecyclerView.Adapter<ColorChooseAdapter.ColorChooseVH> {
    private Context context;
    private List<Integer> data;
    private List<Boolean> choosenPositions;
    private int selction;
    private Callback callback;

    public ColorChooseAdapter(Context context, List<Integer> data,int selection) {
        this.context = context;
        this.data = data;
        choosenPositions=new ArrayList<>();
        refreshStatus(selection);
    }

    @NonNull
    @Override
    public ColorChooseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.color_choose_item,parent,false);
        return new ColorChooseVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorChooseVH holder, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.parent.setBackgroundColor(context.getColor(ColorUtils.getPrimaryColor(data.get(position))));
            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshStatus(position);
                    int pos=holder.getLayoutPosition();
                    if(callback!=null)
                        callback.callback(pos);
                }
            });
            Log.d("posTAG", "onBindViewHolder: "+position);
            holder.chosenView.setVisibility(choosenPositions.get(position)?View.VISIBLE:View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ColorChooseVH extends RecyclerView.ViewHolder{
        @BindView(R.id.colorChooseArea)
        RelativeLayout parent;
        @BindView(R.id.isColorChoosed)
        ImageView chosenView;
        public ColorChooseVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private void refreshStatus(int position)
    {
        if(choosenPositions.isEmpty())
        {
            for (int i = 0; i < data.size(); i++) {
                choosenPositions.add(false);
            }
        }
        else {
            for (int i = 0; i < choosenPositions.size(); i++) {
                choosenPositions.set(i,false);
            }
        }
        choosenPositions.set(position,true);
        notifyDataSetChanged();
        selction=position;
    }

    public int getChoosePosition(){
        return selction;
    }

    public interface Callback{
        void callback(int position);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
