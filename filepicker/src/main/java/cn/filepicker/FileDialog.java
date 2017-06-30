package cn.filepicker;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import cn.filepicker.model.File;
import cn.filepicker.view.FileSelector;

/**
 * Created by cloudist on 2017/6/30.
 */

public class FileDialog extends DialogFragment {

    List<File> defaultFiles = new ArrayList<>();
    FileSelector.OnResultListener onResultListener;

    public static FileDialog newInstance() {
        Bundle args = new Bundle();
        FileDialog fragment = new FileDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeBottomDialog);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置入出场动画
        View rootView = inflater.inflate(R.layout.dialog_file_selector, container);
        return rootView;
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

    public void onResume() {
        final Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        hideStatusNavigationBar(window);
        // Call super onResume after sizing
        super.onResume();
    }

    private void hideStatusNavigationBar(Window window) {
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN //hide statusBar
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //hide navigationBar
            window.getDecorView().setSystemUiVisibility(uiFlags);

        }
    }


    public List<File> getDefaultFiles() {
        return defaultFiles;
    }

    public void setDefaultFiles(List<File> defaultFiles) {
        this.defaultFiles = defaultFiles;
    }

    public FileSelector.OnResultListener getOnResultListener() {
        return onResultListener;
    }

    public void setOnResultListener(FileSelector.OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    public interface OnResultListener {
        void onResult(List<File> list);
    }

}
