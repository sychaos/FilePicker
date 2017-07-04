package cn.filepicker.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.filepicker.FilePickerFragment;
import cn.filepicker.R;
import cn.filepicker.model.PickerFile;
import cn.filepicker.utils.FragmentUtils;

/**
 * Created by cloudist on 2017/7/4.
 */

public class FilePickerBaseActivity extends AppCompatActivity {

    protected static final String EXTRA_SELECTED_FILES = "extra_selected_files";

    public static final String EXTRA_DATA = "extra_data";

    public List<PickerFile> selectedFiles;
    public int toolbarColorResId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedFiles = (List<PickerFile>) getIntent().getSerializableExtra(EXTRA_SELECTED_FILES);
        if (selectedFiles == null) {
            selectedFiles = new ArrayList<>();
        }
    }

    protected void newFilePickerFragment(int toolbarColorResId) {
        this.toolbarColorResId = toolbarColorResId;
        FilePickerFragment filePickerFragment = FilePickerFragment.newInstance(Environment.getExternalStorageDirectory().getAbsolutePath());
        filePickerFragment.setOnResultListener(onResultListener);
        filePickerFragment.setmToolbarColorResId(toolbarColorResId);
        filePickerFragment.setTitle("文件选择器");
        FragmentUtils.replaceFragmentToActivity(getSupportFragmentManager(),
                filePickerFragment, R.id.fragment_container);
    }

    public void goIntoDirectory(PickerFile pickerFile) {
        FilePickerFragment filePickerFragment = FilePickerFragment.newInstance(pickerFile.getLocation());
        filePickerFragment.setOnResultListener(onResultListener);
        filePickerFragment.setmToolbarColorResId(toolbarColorResId);
        filePickerFragment.setTitle(pickerFile.getName());
        FragmentUtils.addFragmentToActivity(getSupportFragmentManager(),
                filePickerFragment, R.id.fragment_container);
    }

    FilePickerFragment.OnResultListener onResultListener = new FilePickerFragment.OnResultListener() {
        @Override
        public void onResult() {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DATA, (Serializable) selectedFiles);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

}
