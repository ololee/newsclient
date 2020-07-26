package cn.ololee.newsclient.utils;

import java.util.ArrayList;
import java.util.List;

import cn.ololee.newsclient.view.activitys.BaseActivity;

public class ActivityCollector {
    private static class Single{
        private static final List<BaseActivity> activityLiveList=new ArrayList<>();
    }

    public static void addActivity(BaseActivity activity){
        Single.activityLiveList.add(activity);
    }

    public static void removeActivity(BaseActivity activity)
    {
        Single.activityLiveList.remove(activity);
    }



    public static void finishAll(){
        for (BaseActivity activity : Single.activityLiveList) {
            if(!activity.isFinishing())
                activity.finish();
        }
    }

}
