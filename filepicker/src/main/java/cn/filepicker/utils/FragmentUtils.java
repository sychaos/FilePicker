package cn.filepicker.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import cn.filepicker.R;

public class FragmentUtils {

    public static void replaceFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        // 开启事务
        FragmentTransaction transaction = fragmentManager
                .beginTransaction();
        transaction.setCustomAnimations(R.anim.push_left_in_no_alpha,
                R.anim.push_left_out_no_alpha,
                R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha
        );
        // 执行事务,添加Fragment
        transaction.add(frameId, fragment, "addFragmentToActivity");
        // 添加到回退栈,并定义标记
        transaction.addToBackStack("addFragmentToActivity");
        // 提交事务
        transaction.commit();
    }
}
