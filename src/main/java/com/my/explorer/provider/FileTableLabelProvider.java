package com.my.explorer.provider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.my.util.Utils;

/**
 * 表控件的标签显示类
 * 
 * @author yang.dongdong
 *
 */
public class FileTableLabelProvider implements ITableLabelProvider {

    @Override
    public void addListener( ILabelProviderListener listener ) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean isLabelProperty( Object element, String property ) {
        return false;
    }

    @Override
    public void removeListener( ILabelProviderListener listener ) {
    }

    @Override
    public Image getColumnImage( Object element, int columnIndex ) {
        // 给列表项加图标
        if ( columnIndex != 0 ) {
            return null;
        }
        if ( ((File) element).isDirectory() ) {
            return Utils.getImageRegistry().get( "folder" );
        } else {
            return Utils.getImageRegistry().get( "file" );
        }
    }

    @Override
    public String getColumnText( Object element, int columnIndex ) {
        String text = "";
        switch ( columnIndex ) {
            case 0:
                text = ((File) element).getName();
                break;
            case 1:
                text = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" )
                        .format( new Date( ((File) element).lastModified() ) );
                break;
            case 2:
                text = ((File) element).isFile() ? "file" : "directory";
                break;
            case 3:
                text = Utils.byteCountToDisplaySize( ((File) element).length() );
                break;
        }
        return text;
    }

}
