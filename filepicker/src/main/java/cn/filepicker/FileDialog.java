package cn.filepicker;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.filepicker.adapter.CommonFileAdapter;
import cn.filepicker.model.PickerFile;
import cn.filepicker.utils.FileUtils;

/**
 * Created by cloudist on 2017/6/30.
 */

public class FileDialog extends DialogFragment {

    List<PickerFile> mDefaultFiles = new ArrayList<>();

    private String mPath;

    CommonFileAdapter mAdapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;

    public static FileDialog newInstance() {
        Bundle args = new Bundle();
        FileDialog fragment = new FileDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeBottomDialog);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置入出场动画
        View rootView = inflater.inflate(R.layout.dialog_file_selector, container);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = (Button) view.findViewById(R.id.btn_select);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        if (!checkSDState()) {
            Toast.makeText(getActivity(), R.string.no_sd_card, Toast.LENGTH_SHORT).show();
            return;
        }

        mPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        mAdapter = new CommonFileAdapter(getActivity(), getFileList(mPath));
        mAdapter.setDefaultData(mDefaultFiles);
        mAdapter.setOnClickListener(new CommonFileAdapter.OnClickListener() {
            @Override
            public void onClick(View view, PickerFile pickerFile) {
                switch (pickerFile.getItemType()) {
                    case CommonFileAdapter.TYPE_DOC:
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_choose);
                        checkBox.setChecked(!checkBox.isChecked());
                        break;
                    case CommonFileAdapter.TYPE_FOLDER:
                        goIntoDirectory(pickerFile);
                        break;
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public void onResume() {
        final Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        hideStatusNavigationBar(window);
        // Call super onResume after sizing
        super.onResume();
    }

    private void hideStatusNavigationBar(Window window) {
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;//hide statusBar
            window.getDecorView().setSystemUiVisibility(uiFlags);

        }
    }

    /**
     * 点击进入目录
     */
    private void goIntoDirectory(PickerFile pickerFile) {
        mPath = pickerFile.getLocation();
        //更新数据源
        mAdapter.setmData(getFileList(mPath));
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }

    /**
     * 检测SD卡是否可用
     */
    private boolean checkSDState() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 根据地址获取当前地址下的所有目录和文件，并且排序
     *
     * @param path
     * @return List<File>
     */
    private List<PickerFile> getFileList(String path) {
        File file = new File(path);
        List<PickerFile> list = FileUtils.getFileListByDirPath(path);
        return list;
    }


    public List<PickerFile> getDefaultFiles() {
        return mDefaultFiles;
    }

    public FileDialog setDefaultFiles(List<PickerFile> defaultFiles) {
        this.mDefaultFiles = defaultFiles;
        return FileDialog.this;
    }

}
