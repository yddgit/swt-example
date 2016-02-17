package com.my.explorer.sorter;

import java.io.File;

import org.eclipse.jface.viewers.ViewerSorter;

/**
 * 文件排序规则
 * 
 * @author yang.dongdong
 *
 */
public class FileSorter extends ViewerSorter {

    @Override
    public int category( Object element ) {
        return ((File) element).isDirectory() ? 0 : 1;
    }

}
