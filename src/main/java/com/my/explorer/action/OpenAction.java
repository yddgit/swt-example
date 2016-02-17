package com.my.explorer.action;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.program.Program;

import com.my.explorer.Explorer;
import com.my.util.Utils;

/**
 * 打开文件操作（启动与文件关联的程序）
 * 
 * @author yang.dongdong
 *
 */
public class OpenAction extends Action implements ISelectionChangedListener {

    Explorer window;

    public OpenAction( Explorer window ) {
        this.window = window;
        setEnabled( false );
        setText( "Run" );
        setToolTipText( "Run the associated program on a file" );
        setImageDescriptor( ImageDescriptor.createFromURL( Utils.newURL( "file:icons/run.gif" ) ) );
    }

    @Override
    public void run() {
        IStructuredSelection selection = window.getTableSelection();
        if ( selection.size() != 1 ) {
            return;
        }
        File selectedFile = (File) selection.getFirstElement();
        if ( selectedFile.isFile() ) {
            Program.launch( selectedFile.getAbsolutePath() );
        }
    }

    @Override
    public void selectionChanged( SelectionChangedEvent event ) {
        // 根据选中状态改变Run菜单的状态、文本和提示
        setText( "Run" );
        setToolTipText( "Run the associated program on a file" );
        IStructuredSelection selection = window.getTableSelection();
        if ( selection.size() != 1 ) {
            setEnabled( false );
            setToolTipText( getToolTipText() + "(Only enabled when exactly one item is selected" );
            return;
        }

        File file = (File) selection.getFirstElement();
        if ( file.isFile() ) {
            setEnabled( true );
            setText( "Run the assciated program on " + file.getName() );
            setToolTipText( "Run the program associated with " + file.getName() + " with this file as the argument" );
        } else {
            setEnabled( false );
        }
    }
}
