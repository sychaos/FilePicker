package cn.filepicker.common;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.util.List;

import cn.filepicker.R;
import cn.filepicker.adapter.BaseFileAdapter;
import cn.filepicker.adapter.CommonFileAdapter;
import cn.filepicker.base.FilePickerBaseActivity;
import cn.filepicker.model.FileItem;

/**
 * Created by cloudist on 2017/7/3.
 */

public class FilePickerActivity extends FilePickerBaseActivity {

    public static Intent getStartIntent(Context context, List<FileItem> selectedFiles) {
        Intent intent = new Intent(context, FilePickerActivity.class);
        intent.putExtra(EXTRA_SELECTED_FILES, (Serializable) selectedFiles);
        return intent;
    }

    @Override
    public BaseFileAdapter initFilePicker() {
        toolbarColorResId = R.color.filepicker_colorPrimary;
        return new CommonFileAdapter(FilePickerActivity.this);
    }

}