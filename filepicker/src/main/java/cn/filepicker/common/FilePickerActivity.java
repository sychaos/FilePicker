package cn.filepicker.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

import cn.filepicker.R;
import cn.filepicker.adapter.BaseFileAdapter;
import cn.filepicker.adapter.CommonFileAdapter;
import cn.filepicker.base.FilePickerBaseActivity;
import cn.filepicker.model.PickerFile;
import cn.filepicker.utils.FragmentUtils;

/**
 * Created by cloudist on 2017/7/3.
 */

public class FilePickerActivity extends FilePickerBaseActivity {

    public static Intent getStartIntent(Context context, List<PickerFile> selectedFiles) {
        Intent intent = new Intent(context, FilePickerActivity.class);
        intent.putExtra(EXTRA_SELECTED_FILES, (Serializable) selectedFiles);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newRootFragment(R.color.filepicker_colorPrimary, new CommonFileAdapter(FilePickerActivity.this));
    }

    @Override
    public void newRootFragment(int toolbarColorResId, BaseFileAdapter baseFileAdapter) {
        this.toolbarColorResId = toolbarColorResId;
        FilePickerFragment filePickerFragment = FilePickerFragment.newInstance();
        filePickerFragment.setOnResultListener(onResultListener);
        filePickerFragment.setmToolbarColorResId(toolbarColorResId);
        baseFileAdapter.setData(getFileList(Environment.getExternalStorageDirectory().getAbsolutePath()));
        filePickerFragment.setAdapter(baseFileAdapter);
        filePickerFragment.setTitle("文件选择器");
        FragmentUtils.replaceFragmentToActivity(getSupportFragmentManager(),
                filePickerFragment, R.id.fragment_container);
    }

    @Override
    public void newDirectoryFragment(PickerFile pickerFile) {
        CommonFileAdapter commonFileAdapter = new CommonFileAdapter(FilePickerActivity.this);
        FilePickerFragment filePickerFragment = FilePickerFragment.newInstance();
        filePickerFragment.setOnResultListener(onResultListener);
        filePickerFragment.setmToolbarColorResId(toolbarColorResId);
        commonFileAdapter.setData(getFileList(pickerFile.getLocation()));
        filePickerFragment.setAdapter(commonFileAdapter);
        filePickerFragment.setTitle(pickerFile.getName());
        FragmentUtils.addFragmentToActivity(getSupportFragmentManager(),
                filePickerFragment, R.id.fragment_container);
    }

}
