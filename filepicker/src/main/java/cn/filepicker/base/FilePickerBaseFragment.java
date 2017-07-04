package cn.filepicker.base;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import cn.filepicker.FilePickerActivity;
import cn.filepicker.FilePickerFragment;
import cn.filepicker.R;
import cn.filepicker.adapter.BaseFileAdapter;
import cn.filepicker.adapter.CommonFileAdapter;
import cn.filepicker.model.PickerFile;
import cn.filepicker.utils.FileUtils;

/**
 * Created by cloudist on 2017/7/4.
 */

public abstract class FilePickerBaseFragment extends Fragment {

    public final static String ARGUMENTS_PATH = "arguments_path";

    FilePickerFragment.OnResultListener onResultListener;

    private String mTitle;
    public String mPath;

    LinearLayoutManager mLayoutManager;
    BaseFileAdapter mAdapter;

    RecyclerView mRecyclerView;
    ImageView btnBack;
    Button btnSelect;

    private int mToolbarColorResId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_file_selector, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPath = getArguments().getString(ARGUMENTS_PATH);


        if (!checkSDState()) {
            Toast.makeText(getActivity(), R.string.no_sd_card, Toast.LENGTH_SHORT).show();
            return;
        }

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        btnSelect = (Button) view.findViewById(R.id.btn_select);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        btnBack = (ImageView) view.findViewById(R.id.btn_nav_back);

        mPath = getArguments().getString(ARGUMENTS_PATH);

        toolbar.setBackgroundResource(mToolbarColorResId);
        toolbarTitle.setText(mTitle);

        btnSelect.setText(String.format(getString(R.string.has_selected), ((FilePickerActivity) getActivity()).selectedFiles.size()));

        mAdapter = initAdapter();
        mAdapter.setDefaultData(((FilePickerActivity) getActivity()).selectedFiles);
        mAdapter.setOnClickListener(new CommonFileAdapter.OnClickListener() {
            @Override
            public void onClick(View view, final PickerFile pickerFile) {
                switch (pickerFile.getItemType()) {
                    case CommonFileAdapter.TYPE_DOC:
                        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_choose);
                        checkBox.setChecked(!checkBox.isChecked());
                        if (checkBox.isChecked()) {
                            ((FilePickerActivity) getActivity()).selectedFiles.add(pickerFile);
                        } else {
                            ((FilePickerActivity) getActivity()).selectedFiles.remove(pickerFile);
                        }
                        btnSelect.setText(String.format(getString(R.string.has_selected), ((FilePickerActivity) getActivity()).selectedFiles.size()));
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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((FilePickerActivity) getActivity()).selectedFiles.isEmpty()) {
                    Toast.makeText(getActivity(), "请至少选择一个文件", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (onResultListener != null) {
                    onResultListener.onResult();
                }
            }
        });

    }

    /**
     * 检测SD卡是否可用
     */
    private boolean checkSDState() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 点击进入目录
     */
    public void goIntoDirectory(PickerFile pickerFile) {
        ((FilePickerActivity) getActivity()).goIntoDirectory(pickerFile);
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

    public void setOnResultListener(FilePickerFragment.OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    public void setmToolbarColorResId(int mToolbarColorResId) {
        this.mToolbarColorResId = mToolbarColorResId;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public abstract BaseFileAdapter initAdapter();

}
