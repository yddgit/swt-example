package com.my.explorer.action;

import java.io.File;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;

import com.my.explorer.Explorer;
import com.my.util.Utils;

/**
 * 将当前选中的文件全路径复制到剪贴板
 * 
 * @author yang.dongdong
 *
 */
public class CopyFileNameToClipboardAction extends Action {

    Explorer window;

    public CopyFileNameToClipboardAction( Explorer window ) {
        this.window = window;
        setToolTipText( "Copy absolute file names of selected files to clipboard" );
        setText( "Copy File &Names@Ctrl+Shift+C" );
        setImageDescriptor( ImageDescriptor.createFromURL( Utils.newURL( "file:icons/copy.gif" ) ) );
    }

    @Override
    public void run() {
        Clipboard clipboard = Utils.getClipboard();
        TextTransfer textTransfer = TextTransfer.getInstance();

        IStructuredSelection selection = window.getTableSelection();
        if ( selection.isEmpty() ) {
            return;
        }
        StringBuffer buffer = new StringBuffer();
        for ( Iterator < ? > it = selection.iterator(); it.hasNext(); ) {
            File file = (File) it.next();
            buffer.append( file.getAbsolutePath() );
            buffer.append( "\n" );
        }
        clipboard.setContents( new Object[] { buffer.substring( 0, buffer.lastIndexOf( "\n" ) ) },
                new Transfer[] { textTransfer } );
    }
}
