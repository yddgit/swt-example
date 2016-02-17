package com.my.explorer.filter;

import java.io.File;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * 过滤文件树只显示目录
 * 
 * @author yang.dongdong
 *
 */
public class AllowOnlyFoldersFilter extends ViewerFilter {

    @Override
    public boolean select( Viewer viewer, Object parentElement, Object element ) {
        return ((File) element).isDirectory();
    }

}
