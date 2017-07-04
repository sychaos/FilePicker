package cn.filepicker.common;

import android.os.Bundle;

import cn.filepicker.base.FilePickerBaseFragment;

/**
 * Created by cloudist on 2017/7/3.
 */

public class FilePickerFragment extends FilePickerBaseFragment {

    public static FilePickerFragment newInstance() {
        Bundle arguments = new Bundle();
        FilePickerFragment fragment = new FilePickerFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

}
