package cn.filepicker;

import android.os.Bundle;

import cn.filepicker.adapter.BaseFileAdapter;
import cn.filepicker.adapter.CommonFileAdapter;
import cn.filepicker.base.FilePickerBaseFragment;

/**
 * Created by cloudist on 2017/7/3.
 */

public class FilePickerFragment extends FilePickerBaseFragment {

    public static FilePickerFragment newInstance(String path) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENTS_PATH, path);
        FilePickerFragment fragment = new FilePickerFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public BaseFileAdapter initAdapter() {
        return new CommonFileAdapter(getActivity(), getFileList(mPath));
    }

    public interface OnResultListener {
        void onResult();
    }

}
