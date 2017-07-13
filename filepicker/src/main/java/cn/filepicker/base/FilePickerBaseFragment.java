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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.filepicker.R;
import cn.filepicker.adapter.BaseFileAdapter;
import cn.filepicker.adapter.CommonFileAdapter;
import cn.filepicker.event.FileRefreshEvent;
import cn.filepicker.model.FileItem;
import cn.filepicker.view.RecyclerViewDivider;

/**
 * Created by cloudist on 2017/7/4.
 */

public abstract class FilePickerBaseFragment extends Fragment {

    public static final int TYPE_ROOT = 1010;
    public static final int TYPE_DIRECTORY = 1011;
    public static final int TYPE_SELECTED = 1012;

    FilePickerBaseActivity.OnResultListener onResultListener;

    private String mTitle;
    public BaseFileAdapter mAdapter;

    LinearLayoutManager mLayoutManager;

    RecyclerView mRecyclerView;
    ImageView btnBack;
    Button btnSelect;

    public Button btnPreview;
    public int mType;

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
        btnSelect = (Button) view.findViewById(R.id.btn_select);
        btnPreview = (Button) view.findViewById(R.id.btn_preview);

        toolbarTitle.setText(mTitle);

        btnPreview.setText(String.format(getString(R.string.preview), ((FilePickerBaseActivity) getActivity()).selectedFiles.size()));

        mAdapter.setDefaultData(((FilePickerBaseActivity) getActivity()).selectedFiles);
        mAdapter.setOnClickListener(initClickListener());

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

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType == TYPE_SELECTED) {
                    return;
                }
                ((FilePickerBaseActivity) getActivity()).newSelectedFragment();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((FilePickerBaseActivity) getActivity()).selectedFiles.isEmpty()) {
                    Toast.makeText(getActivity(), "请至少选择一个文件", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (onResultListener != null) {
                    onResultListener.onResult();
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFileRefreshEvent(FileRefreshEvent event) {
        if (mType == TYPE_SELECTED) {
        } else if (mType == TYPE_ROOT || mType == TYPE_DIRECTORY) {
            btnPreview.setText(String.format(getString(R.string.preview), ((FilePickerBaseActivity) getActivity()).selectedFiles.size()));
            mAdapter.notifyDataSetChanged();
        } else {
        }
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

    public abstract BaseFileAdapter.OnClickListener initClickListener();

    public void setOnResultListener(FilePickerBaseActivity.OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setAdapter(BaseFileAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public void setType(int mType) {
        this.mType = mType;
    }
}
