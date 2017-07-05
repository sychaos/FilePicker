package cn.filepicker.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.filepicker.adapter.BaseFileAdapter;
import cn.filepicker.R;
import cn.filepicker.common.FilePickerFragment;
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
    public int toolbarColorResId;

    Button btnSelect;
    Button btnPreview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        selectedFiles = (List<FileItem>) getIntent().getSerializableExtra(EXTRA_SELECTED_FILES);
        if (selectedFiles == null) {
            selectedFiles = new ArrayList<>();
        }

        btnSelect = (Button) findViewById(R.id.btn_select);
        btnPreview = (Button) findViewById(R.id.btn_preview);

        btnPreview.setText(String.format(getString(R.string.has_selected), selectedFiles.size()));

        newRootFragment();

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newSelectedFragment();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFiles.isEmpty()) {
                    Toast.makeText(FilePickerBaseActivity.this, "请至少选择一个文件", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (onResultListener != null) {
                    onResultListener.onResult();
                }
            }
        });

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
        btnPreview.setText(String.format(getString(R.string.has_selected), selectedFiles.size()));
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
        BaseFileAdapter baseFileAdapter = initFilePicker();
        FilePickerFragment filePickerFragment = FilePickerFragment.newInstance();
        filePickerFragment.setOnResultListener(onResultListener);
        filePickerFragment.setmToolbarColorResId(toolbarColorResId);
        baseFileAdapter.setData(getFileList(Environment.getExternalStorageDirectory().getAbsolutePath()));
        filePickerFragment.setAdapter(baseFileAdapter);
        filePickerFragment.setTitle("文件选择器");
        FragmentUtils.replaceFragmentToActivity(getSupportFragmentManager(),
                filePickerFragment, R.id.fragment_container);
    }

    public void newDirectoryFragment(FileItem fileItem) {
        BaseFileAdapter baseFileAdapter = initFilePicker();
        FilePickerFragment filePickerFragment = FilePickerFragment.newInstance();
        filePickerFragment.setOnResultListener(onResultListener);
        filePickerFragment.setmToolbarColorResId(toolbarColorResId);
        baseFileAdapter.setData(getFileList(fileItem.getLocation()));
        filePickerFragment.setAdapter(baseFileAdapter);
        filePickerFragment.setTitle(fileItem.getName());
        FragmentUtils.addFragmentToActivity(getSupportFragmentManager(),
                filePickerFragment, R.id.fragment_container);
    }

    public void newSelectedFragment() {
        if (selectedFiles.isEmpty()) {
            return;
        }
        BaseFileAdapter baseFileAdapter = initFilePicker();
        FilePickerFragment filePickerFragment = FilePickerFragment.newInstance();
        filePickerFragment.setOnResultListener(onResultListener);
        filePickerFragment.setmToolbarColorResId(toolbarColorResId);
        baseFileAdapter.setData(selectedFiles);
        filePickerFragment.setAdapter(baseFileAdapter);
        filePickerFragment.setTitle("已选文件");
        FragmentUtils.addFragmentToActivity(getSupportFragmentManager(),
                filePickerFragment, R.id.fragment_container);
    }

    public abstract BaseFileAdapter initFilePicker();

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
