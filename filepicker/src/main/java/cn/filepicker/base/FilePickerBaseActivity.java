package cn.filepicker.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.filepicker.adapter.BaseFileAdapter;
import cn.filepicker.R;
import cn.filepicker.common.FilePickerCommonFragment;
import cn.filepicker.model.FileItem;
import cn.filepicker.utils.FileUtils;
import cn.filepicker.utils.FragmentUtils;

/**
 * Created by cloudist on 2017/7/4.
 */

public abstract class FilePickerBaseActivity extends AppCompatActivity {

    protected static final String EXTRA_SELECTED_FILES = "extra_selected_files";

    public static final String EXTRA_DATA = "extra_data";

    public List<FileItem> selectedFiles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        selectedFiles = (List<FileItem>) getIntent().getSerializableExtra(EXTRA_SELECTED_FILES);
        if (selectedFiles == null) {
            selectedFiles = new ArrayList<>();
        }

        newRootFragment();

    }

    /**
     * 根据地址获取当前地址下的所有目录和文件，并且排序
     *
     * @param path
     * @return List<File>
     */
    public List<FileItem> getFileList(String path) {
        File file = new File(path);
        List<FileItem> list = FileUtils.getFileListByDirPath(path);
        return list;
    }

    public void selectedFilesChange(boolean ischecked, FileItem fileItem) {
        if (ischecked) {
            selectedFiles.add(fileItem);
        } else {
            selectedFiles.remove(fileItem);
        }
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

    public void newRootFragment() {
        BaseFileAdapter baseFileAdapter = initAdapter();
        baseFileAdapter.setData(getFileList(Environment.getExternalStorageDirectory().getAbsolutePath()));

        FilePickerCommonFragment filePickerCommonFragment = FilePickerCommonFragment.newInstance();
        filePickerCommonFragment.setOnResultListener(onResultListener);
        filePickerCommonFragment.setAdapter(baseFileAdapter);
        filePickerCommonFragment.setTitle("文件选择器");
        filePickerCommonFragment.setType(FilePickerBaseFragment.TYPE_ROOT);
        FragmentUtils.replaceFragmentToActivity(getSupportFragmentManager(),
                filePickerCommonFragment, R.id.fragment_container);
    }

    public void newDirectoryFragment(FileItem fileItem) {
        BaseFileAdapter baseFileAdapter = initAdapter();
        baseFileAdapter.setData(getFileList(fileItem.getLocation()));

        FilePickerCommonFragment filePickerCommonFragment = FilePickerCommonFragment.newInstance();
        filePickerCommonFragment.setOnResultListener(onResultListener);
        filePickerCommonFragment.setAdapter(baseFileAdapter);
        filePickerCommonFragment.setTitle(fileItem.getName());
        filePickerCommonFragment.setType(FilePickerBaseFragment.TYPE_DIRECTORY);
        FragmentUtils.addFragmentToActivity(getSupportFragmentManager(),
                filePickerCommonFragment, R.id.fragment_container);
    }

    public void newSelectedFragment() {
        if (selectedFiles.isEmpty()) {
            return;
        }
        BaseFileAdapter baseFileAdapter = initAdapter();
        baseFileAdapter.setData(selectedFiles);

        FilePickerCommonFragment filePickerCommonFragment = FilePickerCommonFragment.newInstance();
        filePickerCommonFragment.setOnResultListener(onResultListener);
        filePickerCommonFragment.setAdapter(baseFileAdapter);
        filePickerCommonFragment.setTitle("已选文件");
        filePickerCommonFragment.setType(FilePickerBaseFragment.TYPE_SELECTED);
        FragmentUtils.addFragmentToActivity(getSupportFragmentManager(),
                filePickerCommonFragment, R.id.fragment_container);
    }

    public abstract BaseFileAdapter initAdapter();

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
