package cn.filepickerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cn.filepicker.FilePickActivity;
import cn.filepicker.model.PickerFile;

public class MainActivity extends AppCompatActivity {

    List<PickerFile> files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.text);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        FilePickActivity.getStartIntent(MainActivity.this, files), 1004);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1004) {
                files = (List<PickerFile>) data.getSerializableExtra("data");
            }
        }

    }

}
