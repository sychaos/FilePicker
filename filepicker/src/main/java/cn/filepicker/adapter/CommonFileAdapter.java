package cn.filepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.filepicker.R;
import cn.filepicker.model.FileItem;

/**
 * Created by cloudist on 2017/6/30.
 */

public class CommonFileAdapter extends BaseFileAdapter {

    public static final int TYPE_DOC = 1001;
    public static final int TYPE_FOLDER = 1002;
    public static final int TYPE_DIVIDER = 1003;
    public static final int TYPE_EMPTYE = 1004;

    public CommonFileAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public ViewGroup initViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        ViewGroup viewGroup = null;
        switch (viewType) {
            case TYPE_DOC:
                viewGroup = (ViewGroup) mInflater.inflate(R.layout.item_doc, parent, false);
                break;
            case TYPE_FOLDER:
                viewGroup = (ViewGroup) mInflater.inflate(R.layout.item_folder, parent, false);
                break;
            case TYPE_DIVIDER:
                viewGroup = (ViewGroup) mInflater.inflate(R.layout.common_divider, parent, false);
                break;
            case TYPE_EMPTYE:
                viewGroup = (ViewGroup) mInflater.inflate(R.layout.empty_view, parent, false);
                break;
        }
        return viewGroup;
    }

    @Override
    public void initData(RecyclerView.ViewHolder holder, final FileItem fileItem, int position) {
        final View view = holder.itemView;
        switch (holder.getItemViewType()) {
            case TYPE_DOC:
                final CheckBox docCbChoose = (CheckBox) view.findViewById(R.id.cb_choose);
                TextView docName = (TextView) view.findViewById(R.id.tv_name);
                docCbChoose.setChecked(mSelectedData.contains(fileItem));
                docName.setText(fileItem.getName());
                break;
            case TYPE_FOLDER:
                TextView folderName = (TextView) view.findViewById(R.id.tv_name);
                folderName.setText(fileItem.getName());
                break;
            case TYPE_DIVIDER:
                break;
            case TYPE_EMPTYE:
                break;
        }
        if (onClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(view, fileItem);
                }
            });
        }
    }

    @Override
    public void setData(List<FileItem> mData) {
        if (mData == null || mData.isEmpty()) {
            mData = new ArrayList<>();
            FileItem fileItem = new FileItem(TYPE_EMPTYE);
            mData.add(fileItem);
        }
        super.setData(mData);
    }

}
