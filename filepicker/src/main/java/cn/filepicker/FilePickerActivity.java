package cn.filepicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

import cn.filepicker.base.FilePickerBaseActivity;
import cn.filepicker.model.PickerFile;

/**
 * Created by cloudist on 2017/7/3.
 */

public class FilePickerActivity extends FilePickerBaseActivity {

    public static Intent getStartIntent(Context context, List<PickerFile> selectedFiles) {
        Intent intent = new Intent(context, FilePickerActivity.class);
        intent.putExtra(EXTRA_SELECTED_FILES, (Serializable) selectedFiles);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        //TODO 解耦
        newFilePickerFragment(R.color.filepicker_colorPrimary);
    }

}
