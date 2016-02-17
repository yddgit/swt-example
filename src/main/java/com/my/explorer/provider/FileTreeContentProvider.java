package com.my.explorer.provider;

import java.io.File;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 树控件的数据源
 * 
 * @author yang.dongdong
 *
 */
public class FileTreeContentProvider implements ITreeContentProvider {

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
    }

    @Override
    public Object[] getChildren( Object element ) {
        Object[] kids = ((File) element).listFiles();
        return kids == null ? new Object[0] : kids;
    }

    @Override
    public Object[] getElements( Object element ) {
        return getChildren( element );
    }

    @Override
    public Object getParent( Object element ) {
        return ((File) element).getParentFile();
    }

    @Override
    public boolean hasChildren( Object element ) {
        return getChildren( element ).length > 0;
    }

}
