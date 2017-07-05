package cn.filepicker.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.filepicker.adapter.CommonFileAdapter;
import cn.filepicker.model.FileItem;

public class FileUtils {
    public static List<FileItem> getFileListByDirPath(String path) {
        File directory = new File(path);
        File[] files = directory.listFiles();

        List<FileItem> fileItems = new ArrayList<>();

        if (files == null) {
            return fileItems;
        }

        List<File> result = Arrays.asList(files);
        Collections.sort(result, new FileComparator());

        for (File file : result) {
            FileItem fileItem = null;
            if (file.isDirectory()) {
                fileItem = new FileItem(CommonFileAdapter.TYPE_FOLDER);
            } else {
                fileItem = new FileItem(CommonFileAdapter.TYPE_DOC);
            }

            fileItem.setName(file.getName());
            fileItem.setLocation(file.getAbsolutePath());
            fileItem.setSize(file.length());

            fileItems.add(fileItem);
        }
        return fileItems;
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
