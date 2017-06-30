package cn.filepicker.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import cn.filepicker.R;
import cn.filepicker.model.File;

public class FileSelector extends Fragment {

    List<File> defaultFiles = new ArrayList<>();
    OnResultListener onResultListener;

    public static FileSelector newInstance() {
        Bundle args = new Bundle();
        FileSelector fragment = new FileSelector();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_file_selector, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = (Button) view.findViewById(R.id.btn_apply_company);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onResultListener != null) {
                    onResultListener.onResult(null);
                }
            }
        });
    }

    public List<File> getDefaultFiles() {
        return defaultFiles;
    }

    public void setDefaultFiles(List<File> defaultFiles) {
        this.defaultFiles = defaultFiles;
    }

    public OnResultListener getOnResultListener() {
        return onResultListener;
    }

    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    public interface OnResultListener {
        void onResult(List<File> list);
    }
}
