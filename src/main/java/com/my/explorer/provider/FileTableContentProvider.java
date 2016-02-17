package com.my.explorer.provider;

import java.io.File;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 表控件的数据源
 * 
 * @author yang.dongdong
 *
 */
public class FileTableContentProvider implements IStructuredContentProvider {

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
    }

    @Override
    public Object[] getElements( Object inputElement ) {
        Object[] kids = null;
        kids = ((File) inputElement).listFiles();
        return kids == null ? new Object[0] : kids;
    }

}
