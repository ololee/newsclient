package cn.ololee.newsclient.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import cn.ololee.newsclient.R;

import static cn.ololee.newsclient.utils.Constants.COLOR_ACCENT;
import static cn.ololee.newsclient.utils.Constants.COLOR_BLUE;
import static cn.ololee.newsclient.utils.Constants.COLOR_GREEN;
import static cn.ololee.newsclient.utils.Constants.COLOR_PURPLE;
import static cn.ololee.newsclient.utils.Constants.COLOR_RED;
import static cn.ololee.newsclient.utils.Constants.COLOR_YELLOW;

public class ColorUtils {

    public static int getPrimaryColor(int choosedColor)
    {
        int color;
        switch (choosedColor)
        {
            case COLOR_RED:
                color=R.color.red;
                break;
            case COLOR_BLUE:
               color=R.color.colorPrimary;
                break;
            case COLOR_YELLOW:
                color=R.color.yellow;
                break;
            case COLOR_PURPLE:
                color=R.color.purple;
                break;
            case COLOR_ACCENT:
                color=R.color.colorAccent;
                break;
            case COLOR_GREEN:
            default:
                color=R.color.green;
                break;
        }
        return color;
    }
}
