package com.gvs.follow;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gvs on 2017/4/6.
 */

public class Follow {

    //判断当前移动的控件是否会和其他的控件重叠
    public View testCollection(View from, View root) {
        if (root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) root;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View to = group.getChildAt(i);
                if (to == from) continue;
                //判断到右重叠的时候就返回这个重叠的控件
                if (!realCollection(from, to)) {
                    return to;
                }
            }

        }
        return null;
    }

    //判断两个控件是否重叠
    public boolean realCollection(View f, View t) {
        double fx = f.getLeft();
        double fy = f.getTop();
        double tx = t.getLeft();
        double ty = t.getTop();
        //保证了横坐标不重叠,纵坐标不重叠
        return fx < tx - f.getWidth() || fx > tx + t.getWidth() ||
                fy < ty - f.getHeight() || fy > ty + t.getHeight();
    }

}
