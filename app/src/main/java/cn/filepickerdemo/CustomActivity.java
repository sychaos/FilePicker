package cn.filepickerdemo;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.util.List;

import cn.filepicker.adapter.BaseFileAdapter;
import cn.filepicker.adapter.CommonFileAdapter;
import cn.filepicker.base.FilePickerBaseActivity;
import cn.filepicker.model.FileItem;

/**
 * Created by cloudist on 2017/7/5.
 */

public class CustomActivity extends FilePickerBaseActivity {

    public static Intent getStartIntent(Context context, List<FileItem> selectedFiles) {
        Intent intent = new Intent(context, CustomActivity.class);
        intent.putExtra(EXTRA_SELECTED_FILES, (Serializable) selectedFiles);
        return intent;
    }

    @Override
    public BaseFileAdapter initFilePicker() {
        toolbarColorResId = R.color.colorAccent;
        return new CommonFileAdapter(CustomActivity.this);
    }
}
