package com.my.explorer.provider;

import java.io.File;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.my.util.Utils;

/**
 * 树控件的标签显示类
 * 
 * @author yang.dongdong
 *
 */
public class FileTreeLabelProvider extends LabelProvider {

    @Override
    public String getText( Object element ) {
        return ((File) element).getName();
    }

    @Override
    public Image getImage( Object element ) {
        // 给树结点加图标
        if ( ((File) element).isDirectory() ) {
            return Utils.getImageRegistry().get( "folder" );
        } else {
            return Utils.getImageRegistry().get( "file" );
        }
    }

}
