package cn.filepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.filepicker.model.FileItem;

/**
 * Created by cloudist on 2017/7/4.
 */

public abstract class BaseFileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<FileItem> mSelectedData = new ArrayList<>();
    public List<FileItem> mData = new ArrayList<>();
    public Context mContext;
    public OnClickListener onClickListener;

    public BaseFileAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initData(holder, mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getItemType();
    }

    public List<FileItem> getSelectedData() {
        return mSelectedData;
    }

    public void setDefaultData(List<FileItem> defaultData) {
        if (defaultData == null) {
            defaultData = new ArrayList<>();
        }
        this.mSelectedData = defaultData;
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setData(List<FileItem> mData) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        this.mData = mData;
    }

    public interface OnClickListener {
        void onClick(View view, FileItem fileItem);
    }

    public abstract void initData(RecyclerView.ViewHolder holder, FileItem fileItem, int position);
}
