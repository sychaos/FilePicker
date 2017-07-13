package cn.filepicker;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

import cn.filepicker.common.FilePickerCommonActivity;
import cn.filepicker.model.FileItem;

/**
 * Created by cloudist on 2017/7/5.
 */

public class FilePicker {

    private Activity mActivity;
    private int mRequestCode;
    private List<FileItem> mSelectedFiles;

    private FilePicker() {
    }

    public static FilePicker builder() {
        FilePicker filePicker = new FilePicker();
        return filePicker;
    }

    public FilePicker withActivity(Activity activity) {
        this.mActivity = activity;
        return this;
    }

    public FilePicker withRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    public FilePicker withSelectedFiles(List<FileItem> selectedFiles) {
        this.mSelectedFiles = selectedFiles;
        return this;
    }

    public void build() {
        if (mActivity == null) {
            throw new RuntimeException("You must pass Activity or Adapter by withActivity or withAdapter method");
        }
        Intent intent = FilePickerCommonActivity.getStartIntent(mActivity, mSelectedFiles);
        mActivity.startActivityForResult(intent, mRequestCode);
    }

}
