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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.filepicker.R;
import cn.filepicker.adapter.BaseFileAdapter;
import cn.filepicker.adapter.CommonFileAdapter;
import cn.filepicker.model.FileItem;
import cn.filepicker.view.RecyclerViewDivider;

/**
 * Created by cloudist on 2017/7/4.
 */

public class FilePickerBaseFragment extends Fragment {

    FilePickerBaseActivity.OnResultListener onResultListener;

    private String mTitle;
    public BaseFileAdapter mAdapter;

    LinearLayoutManager mLayoutManager;

    RecyclerView mRecyclerView;
    ImageView btnBack;

    private int mToolbarColorResId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_file_selector, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!checkSDState()) {
            Toast.makeText(getActivity(), R.string.no_sd_card, Toast.LENGTH_SHORT).show();
            return;
        }

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        btnBack = (ImageView) view.findViewById(R.id.btn_nav_back);

        toolbar.setBackgroundResource(mToolbarColorResId);
        toolbarTitle.setText(mTitle);

        mAdapter.setDefaultData(((FilePickerBaseActivity) getActivity()).selectedFiles);
        mAdapter.setOnClickListener(new CommonFileAdapter.OnClickListener() {
            @Override
            public void onClick(View view, final FileItem fileItem) {
                switch (fileItem.getItemType()) {
                    case CommonFileAdapter.TYPE_DOC:
                        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_choose);
                        checkBox.setChecked(!checkBox.isChecked());
                        ((FilePickerBaseActivity) getActivity()).selectedFilesChange(checkBox.isChecked(), fileItem);
                        break;
                    case CommonFileAdapter.TYPE_FOLDER:
                        goIntoDirectory(fileItem);
                        break;
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), RecyclerViewDivider.VERTICAL_LIST));
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
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
    public void goIntoDirectory(FileItem fileItem) {
        ((FilePickerBaseActivity) getActivity()).newDirectoryFragment(fileItem);
    }

    public void setOnResultListener(FilePickerBaseActivity.OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    public void setmToolbarColorResId(int mToolbarColorResId) {
        this.mToolbarColorResId = mToolbarColorResId;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setAdapter(BaseFileAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

}
