package cn.filepicker.common;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import org.greenrobot.eventbus.EventBus;

import cn.filepicker.R;
import cn.filepicker.adapter.BaseFileAdapter;
import cn.filepicker.adapter.CommonFileAdapter;
import cn.filepicker.base.FilePickerBaseActivity;
import cn.filepicker.base.FilePickerBaseFragment;
import cn.filepicker.event.FileRefreshEvent;
import cn.filepicker.model.FileItem;

/**
 * Created by cloudist on 2017/7/3.
 */

public class FilePickerCommonFragment extends FilePickerBaseFragment {

    public static FilePickerCommonFragment newInstance() {
        Bundle arguments = new Bundle();
        FilePickerCommonFragment fragment = new FilePickerCommonFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public BaseFileAdapter.OnClickListener initClickListener() {
        return new CommonFileAdapter.OnClickListener() {
            @Override
            public void onClick(View view, final FileItem fileItem) {
                switch (fileItem.getItemType()) {
                    case CommonFileAdapter.TYPE_DOC:
                        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_choose);
                        checkBox.setChecked(!checkBox.isChecked());
                        ((FilePickerBaseActivity) getActivity()).selectedFilesChange(checkBox.isChecked(), fileItem);
                        btnPreview.setText(String.format(getString(R.string.preview), ((FilePickerBaseActivity) getActivity()).selectedFiles.size()));
                        if (mType == TYPE_SELECTED) {
                            EventBus.getDefault().post(new FileRefreshEvent());
                        } else if (mType == TYPE_ROOT || mType == TYPE_DIRECTORY) {
                        } else {
                        }
                        break;
                    case CommonFileAdapter.TYPE_FOLDER:
                        goIntoDirectory(fileItem);
                        break;
                }
            }
        };
    }
}
