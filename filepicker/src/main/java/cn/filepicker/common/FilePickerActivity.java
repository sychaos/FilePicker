package cn.filepicker.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

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

    public static Intent getStartIntent(Context context, List<FileItem> selectedFiles, int colorResId) {
        Intent intent = new Intent(context, FilePickerActivity.class);
        intent.putExtra(EXTRA_SELECTED_FILES, (Serializable) selectedFiles);
        intent.putExtra(EXTRA_TITLE_COLOR, colorResId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        toolbarColorResId = getIntent().getIntExtra(EXTRA_TITLE_COLOR, R.color.filepicker_toolbar);
        super.onCreate(savedInstanceState);
    }

    @Override
    public BaseFileAdapter initAdapter() {
        return new CommonFileAdapter(FilePickerActivity.this);
    }

}
