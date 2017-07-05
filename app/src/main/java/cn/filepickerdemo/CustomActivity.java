package cn.filepickerdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

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

    public static Intent getStartIntent(Context context, List<FileItem> selectedFiles, int colorResId) {
        Intent intent = new Intent(context, CustomActivity.class);
        intent.putExtra(EXTRA_SELECTED_FILES, (Serializable) selectedFiles);
        intent.putExtra(EXTRA_TITLE_COLOR, colorResId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbarColorResId = getIntent().getIntExtra(EXTRA_TITLE_COLOR, R.color.colorAccent);
    }

    @Override
    public BaseFileAdapter initAdapter() {
        return new CommonFileAdapter(CustomActivity.this);
    }
}
