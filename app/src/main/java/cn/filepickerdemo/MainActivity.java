package cn.filepickerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;

import cn.filepicker.FilePicker;
import cn.filepicker.base.FilePickerBaseActivity;
import cn.filepicker.model.FileItem;

public class MainActivity extends AppCompatActivity {

    List<FileItem> files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCommon = (Button) findViewById(R.id.btn_common);
        Button btnCustom = (Button) findViewById(R.id.btn_custom);

        btnCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePicker.builder()
                        .withActivity(MainActivity.this)
                        .withRequestCode(1004)
                        .withSelectedFiles(files)
                        .withTitleColor(R.color.filepicker_colorPrimary)
                        .build();
            }
        });

        btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        CustomActivity.getStartIntent(MainActivity.this, files), 1005);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1004 || requestCode == 1005) {
                files = (List<FileItem>) data.getSerializableExtra(FilePickerBaseActivity.EXTRA_DATA);
            }
        }

    }

}
