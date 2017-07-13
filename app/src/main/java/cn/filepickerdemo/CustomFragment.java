package cn.filepickerdemo;

import android.view.View;
import android.widget.Toast;


import cn.filepicker.adapter.BaseFileAdapter;
import cn.filepicker.adapter.CommonFileAdapter;
import cn.filepicker.base.FilePickerBaseFragment;
import cn.filepicker.model.FileItem;

/**
 * Created by cloudist on 2017/7/14.
 */

public class CustomFragment extends FilePickerBaseFragment {
    @Override
    public BaseFileAdapter.OnClickListener initClickListener() {
        return new CommonFileAdapter.OnClickListener() {
            @Override
            public void onClick(View view, final FileItem fileItem) {
                switch (fileItem.getItemType()) {
                    case CommonFileAdapter.TYPE_DOC:
                        Toast.makeText(getActivity(), "TYPE_DOC", Toast.LENGTH_SHORT).show();
                        break;
                    case CommonFileAdapter.TYPE_FOLDER:
                        Toast.makeText(getActivity(), "TYPE_FOLDER", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }
}
