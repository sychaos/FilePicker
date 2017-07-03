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
import cn.filepicker.model.PickerFile;

/**
 * Created by cloudist on 2017/6/30.
 */

public class CommonFileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_DOC = 1001;
    public static final int TYPE_FOLDER = 1002;
    public static final int TYPE_DIVIDER = 1003;

    private List<PickerFile> mSelectedData = new ArrayList<>();
    private List<PickerFile> mData = new ArrayList<>();
    private Context mContext;
    private OnClickListener onClickListener;

    public CommonFileAdapter(Context mContext, List<PickerFile> mData) {
        this.mContext = mContext;
        if (mData == null) {
            mData = new ArrayList<>();
        }
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        }
        return new ViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        intData(holder, mData.get(position), position);
    }

    private void intData(RecyclerView.ViewHolder holder, final PickerFile file, final int position) {
        final View view = holder.itemView;
        switch (holder.getItemViewType()) {
            case TYPE_DOC:
                final CheckBox docCbChoose = (CheckBox) view.findViewById(R.id.cb_choose);
                TextView docName = (TextView) view.findViewById(R.id.tv_name);
                docCbChoose.setChecked(mSelectedData.contains(file));
                docName.setText(file.getName());
                break;
            case TYPE_FOLDER:
                TextView folderName = (TextView) view.findViewById(R.id.tv_name);
                folderName.setText(file.getName());
                break;
            case TYPE_DIVIDER:
                break;
        }
        if (onClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(view, file);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getItemType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public List<PickerFile> getmData() {
        return mData;
    }

    public void setmData(List<PickerFile> mData) {
        this.mData = mData;
    }

    public List<PickerFile> getSelectedData() {
        return mSelectedData;
    }

    public void setDefaultData(List<PickerFile> defaultData) {
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

    public interface OnClickListener {
        void onClick(View view, PickerFile pickerFile);
    }

}
