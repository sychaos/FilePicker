package cn.filepicker.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.filepicker.adapter.BaseFileAdapter;
import cn.filepicker.R;
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

    public static Intent getStartIntent(Context context, List<FileItem> selectedFiles, Class<? extends FilePickerBaseActivity> activity) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(EXTRA_SELECTED_FILES, (Serializable) selectedFiles);
        return intent;
    }

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

        FilePickerBaseFragment baseFragment = getFragment(baseFileAdapter,
                onResultListener, "文件选择器", FilePickerBaseFragment.TYPE_ROOT);
        FragmentUtils.replaceFragmentToActivity(getSupportFragmentManager(),
                baseFragment, R.id.fragment_container);
    }

    public void newDirectoryFragment(FileItem fileItem) {
        BaseFileAdapter baseFileAdapter = initAdapter();
        baseFileAdapter.setData(getFileList(fileItem.getLocation()));

        FilePickerBaseFragment baseFragment = getFragment(baseFileAdapter,
                onResultListener, fileItem.getName(), FilePickerBaseFragment.TYPE_DIRECTORY);
        FragmentUtils.addFragmentToActivity(getSupportFragmentManager(),
                baseFragment, R.id.fragment_container);
    }

    public void newSelectedFragment() {
        if (selectedFiles.isEmpty()) {
            return;
        }
        BaseFileAdapter baseFileAdapter = initAdapter();
        baseFileAdapter.setData(selectedFiles);

        FilePickerBaseFragment baseFragment = getFragment(baseFileAdapter,
                onResultListener, "已选文件", FilePickerBaseFragment.TYPE_SELECTED);
        FragmentUtils.addFragmentToActivity(getSupportFragmentManager(),
                baseFragment, R.id.fragment_container);
    }

    @NonNull
    private FilePickerBaseFragment getFragment(BaseFileAdapter baseFileAdapter,
                                               OnResultListener onResultListener, String title, int typeRoot) {
        FilePickerBaseFragment baseFragment = initFragment();
        baseFragment.setOnResultListener(onResultListener);
        baseFragment.setAdapter(baseFileAdapter);
        baseFragment.setTitle(title);
        baseFragment.setType(typeRoot);
        return baseFragment;
    }

    public abstract BaseFileAdapter initAdapter();

    public abstract FilePickerBaseFragment initFragment();

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
