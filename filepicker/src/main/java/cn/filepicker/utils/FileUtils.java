package cn.filepicker.utils;

import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.filepicker.adapter.CommonFileAdapter;
import cn.filepicker.filter.FilePickerFilter;
import cn.filepicker.model.PickerFile;

/**
 * Created by Dimorinny on 24.10.15.
 */
public class FileUtils {
    public static List<PickerFile> getFileListByDirPath(String path) {
        File directory = new File(path);
        File[] files = directory.listFiles();

        List<PickerFile> pickerFiles = new ArrayList<>();

        if (files == null) {
            return pickerFiles;
        }

        List<File> result = Arrays.asList(files);
        Collections.sort(result, new FileComparator());

        for (File file : result) {
            PickerFile pickerFile = null;
            if (file.isDirectory()) {
                pickerFile = new PickerFile(CommonFileAdapter.TYPE_FOLDER);
            } else {
                pickerFile = new PickerFile(CommonFileAdapter.TYPE_DOC);
            }

            pickerFile.setName(file.getName());
            pickerFile.setLocation(file.getAbsolutePath());
            pickerFile.setSize(file.length());

            pickerFiles.add(pickerFile);
        }
        return pickerFiles;
    }

    public static String cutLastSegmentOfPath(String path) {
        return path.substring(0, path.lastIndexOf("/"));
    }

    public static String getReadableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
