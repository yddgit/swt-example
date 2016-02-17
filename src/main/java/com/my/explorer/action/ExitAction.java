package com.my.explorer.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;

import com.my.util.Utils;

/**
 * 菜单栏退出操作
 * 
 * @author yang.dongdong
 *
 */
public class ExitAction extends Action {

    ApplicationWindow window;

    public ExitAction( ApplicationWindow window ) {
        this.window = window;
        // &表示键盘导航键，不同于快捷键
        // @Ctrl+W是设置快捷键
        setText( "E&xit@Ctrl+W" );
        // 工具条提示
        setToolTipText( "Exit the application" );
        // 图标，不使用ImageRegistry的原因是这句代码是在createContents之前被调用的
        setImageDescriptor( ImageDescriptor.createFromURL( Utils.newURL( "file:icons/close.gif" ) ) );
    }

    @Override
    public void run() {
        window.close();
    }

}
