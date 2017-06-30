package cn.filepickerdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.filepicker.FileDialog;
import cn.filepicker.model.File;
import cn.filepicker.view.FileSelector;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.text);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FileSelector fragment = FileSelector.newInstance();
//                fragment.setOnResultListener(new FileSelector.OnResultListener() {
//                    @Override
//                    public void onResult(List<File> list) {
//                        Toast.makeText(MainActivity.this, "sddbudsusdudsuds", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_container, fragment);
//                transaction.commit();

                FileDialog.newInstance().show(getSupportFragmentManager(),"");
            }
        });
    }
}
