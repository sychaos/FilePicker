package cn.filepicker;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import cn.filepicker.adapter.CommonFileAdapter;
import cn.filepicker.model.PickerFile;
import cn.filepicker.utils.FileUtils;

/**
 * Created by cloudist on 2017/7/3.
 */

public class FilePickFragment extends Fragment {

    public final static String ARGUMENTS_PATH = "arguments_path";

    public static FilePickFragment newInstance(String path) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENTS_PATH, path);
        FilePickFragment fragment = new FilePickFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    OnResultListener onResultListener;

    private String mPath;

    CommonFileAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    RecyclerView mRecyclerView;
    ImageView btnBack;
    Button btnSelect;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_file_selector, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSelect = (Button) view.findViewById(R.id.btn_select);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        btnBack = (ImageView) view.findViewById(R.id.btn_nav_back);

        if (!checkSDState()) {
            Toast.makeText(getActivity(), R.string.no_sd_card, Toast.LENGTH_SHORT).show();
            return;
        }

        mPath = getArguments().getString(ARGUMENTS_PATH);

        mAdapter = new CommonFileAdapter(getActivity(), getFileList(mPath));
        mAdapter.setDefaultData(((FilePickActivity) getActivity()).selectedFiles);
        mAdapter.setOnClickListener(new CommonFileAdapter.OnClickListener() {
            @Override
            public void onClick(View view, final PickerFile pickerFile) {
                switch (pickerFile.getItemType()) {
                    case CommonFileAdapter.TYPE_DOC:
                        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_choose);
                        checkBox.setChecked(!checkBox.isChecked());
                        if (checkBox.isChecked()) {
                            ((FilePickActivity) getActivity()).selectedFiles.add(pickerFile);
                        } else {
                            ((FilePickActivity) getActivity()).selectedFiles.remove(pickerFile);
                        }
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
                if (onResultListener != null) {
                    onResultListener.onResult();
                }
            }
        });

    }

    /**
     * 点击进入目录
     */
    private void goIntoDirectory(PickerFile pickerFile) {
        mPath = pickerFile.getLocation();
        ((FilePickActivity) getActivity()).goIntoDirectory(mPath);
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


    public OnResultListener getOnResultListener() {
        return onResultListener;
    }

    public FilePickFragment setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
        return FilePickFragment.this;
    }

    public interface OnResultListener {
        void onResult();
    }

}
