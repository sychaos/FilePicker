package cn.filepicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.filepicker.model.PickerFile;
import cn.filepicker.utils.FragmentUtils;

/**
 * Created by cloudist on 2017/7/3.
 */

public class FilePickActivity extends AppCompatActivity {

    private static final String EXTRA_SELECTED_FILES = "extra_selected_files";

    public List<PickerFile> selectedFiles;
    public int toolbarColorResId;

    public static Intent getStartIntent(Context context, List<PickerFile> selectedFiles) {
        Intent intent = new Intent(context, FilePickActivity.class);
        intent.putExtra(EXTRA_SELECTED_FILES, (Serializable) selectedFiles);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        selectedFiles = (List<PickerFile>) getIntent().getSerializableExtra(EXTRA_SELECTED_FILES);
        if (selectedFiles == null) {
            selectedFiles = new ArrayList<>();
        }
        toolbarColorResId = R.color.filepicker_colorPrimary;

        FilePickFragment filePickFragment = FilePickFragment.newInstance(Environment.getExternalStorageDirectory().getAbsolutePath());
        filePickFragment.setOnResultListener(onResultListener);
        filePickFragment.setmToolbarColorResId(toolbarColorResId);
        filePickFragment.setTitle("文件选择器");
        FragmentUtils.replaceFragmentToActivity(getSupportFragmentManager(),
                filePickFragment, R.id.fragment_container);
    }

    public void goIntoDirectory(PickerFile pickerFile) {
        FilePickFragment filePickFragment = FilePickFragment.newInstance(pickerFile.getLocation());
        filePickFragment.setOnResultListener(onResultListener);
        filePickFragment.setmToolbarColorResId(toolbarColorResId);
        filePickFragment.setTitle(pickerFile.getName());
        FragmentUtils.addFragmentToActivity(getSupportFragmentManager(),
                filePickFragment, R.id.fragment_container);
    }

    FilePickFragment.OnResultListener onResultListener = new FilePickFragment.OnResultListener() {
        @Override
        public void onResult() {
            Intent intent = new Intent();
            intent.putExtra("data", (Serializable) selectedFiles);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    public interface OnResultListener {
        void onResult(List<PickerFile> list);
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
