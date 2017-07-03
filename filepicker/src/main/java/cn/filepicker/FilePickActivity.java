package cn.filepicker;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.filepicker.model.PickerFile;
import cn.filepicker.utils.FragmentUtils;

/**
 * Created by cloudist on 2017/7/3.
 */

public class FilePickActivity extends AppCompatActivity {

    public List<PickerFile> selectedFiles = new ArrayList<>();
    public List<PickerFile> mDefaultFiles = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        selectedFiles = mDefaultFiles;

        FilePickFragment filePickFragment = FilePickFragment.newInstance(Environment.getExternalStorageDirectory().getAbsolutePath());
        filePickFragment.setOnResultListener(onResultListener);
        FragmentUtils.replaceFragmentToActivity(getSupportFragmentManager(),
                filePickFragment, R.id.fragment_container);
    }

    public void goIntoDirectory(String mPath) {
        FilePickFragment filePickFragment = FilePickFragment.newInstance(mPath);
        filePickFragment.setOnResultListener(onResultListener);
        FragmentUtils.addFragmentToActivity(getSupportFragmentManager(),
                filePickFragment, R.id.fragment_container);
    }

    FilePickFragment.OnResultListener onResultListener = new FilePickFragment.OnResultListener() {
        @Override
        public void onResult() {
            Toast.makeText(FilePickActivity.this, selectedFiles.size() + "", Toast.LENGTH_SHORT).show();
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
