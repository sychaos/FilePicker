package cn.filepickerdemo;

import cn.filepicker.adapter.BaseFileAdapter;
import cn.filepicker.adapter.CommonFileAdapter;
import cn.filepicker.base.FilePickerBaseActivity;
import cn.filepicker.base.FilePickerBaseFragment;

/**
 * Created by cloudist on 2017/7/14.
 */

public class CustomActivity extends FilePickerBaseActivity {

    @Override
    public BaseFileAdapter initAdapter() {
        return new CommonFileAdapter(CustomActivity.this);
    }

    @Override
    public FilePickerBaseFragment initFragment() {
        return new CustomFragment();
    }
}
