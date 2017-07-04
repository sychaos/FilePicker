package cn.filepicker.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.filepicker.adapter.BaseFileAdapter;
import cn.filepicker.R;
import cn.filepicker.model.PickerFile;
import cn.filepicker.utils.FileUtils;

/**
 * Created by cloudist on 2017/7/4.
 */

public abstract class FilePickerBaseActivity extends AppCompatActivity {

    protected static final String EXTRA_SELECTED_FILES = "extra_selected_files";

    public static final String EXTRA_DATA = "extra_data";

    public List<PickerFile> selectedFiles;
    public int toolbarColorResId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        selectedFiles = (List<PickerFile>) getIntent().getSerializableExtra(EXTRA_SELECTED_FILES);
        if (selectedFiles == null) {
            selectedFiles = new ArrayList<>();
        }

    }

    /**
     * 根据地址获取当前地址下的所有目录和文件，并且排序
     *
     * @param path
     * @return List<File>
     */
    public List<PickerFile> getFileList(String path) {
        File file = new File(path);
        List<PickerFile> list = FileUtils.getFileListByDirPath(path);
        return list;
    }

    public OnResultListener onResultListener = new OnResultListener() {
        @Override
        public void onResult() {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DATA, (Serializable) selectedFiles);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    public abstract void newRootFragment(int toolbarColorResId, BaseFileAdapter baseFileAdapter);

    public abstract void newDirectoryFragment(PickerFile pickerFile);

    public interface OnResultListener {
        void onResult();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

}
