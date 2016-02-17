package com.my.explorer;

import java.io.File;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;

import com.my.explorer.action.CopyFileNameToClipboardAction;
import com.my.explorer.action.ExitAction;
import com.my.explorer.action.OpenAction;
import com.my.explorer.filter.AllowOnlyFoldersFilter;
import com.my.explorer.provider.FileTableContentProvider;
import com.my.explorer.provider.FileTableLabelProvider;
import com.my.explorer.provider.FileTreeContentProvider;
import com.my.explorer.provider.FileTreeLabelProvider;
import com.my.explorer.sorter.FileSorter;
import com.my.util.Utils;

/**
 * 文件管理器<br>
 * 
 * Explorer --> TreeViewer TableViewer MenuManager<br>
 * TreeViewer --> FileTreeContentProvider FileTreeLabelProvider AllowOnlyFoldersFilter<br>
 * TableViewer --> FileTableContentProvider FileTableLabelProvider FileSorter<br>
 * MenuManager --> ExitAction OpenAction CopyFileNamesToClipboardAction<br>
 * <br>
 * 
 * 简单的文件管理器： <br>
 * 1. 左侧文件树<br>
 * - 单击显示文件和文件夹列表，双击展开当前结点<br>
 * 2. 右侧文件列表<br>
 * - 单击选中行，双击打开，右键弹出菜单(Exit/CopyFileName/Run)<br>
 * 
 * @author yang.dongdong
 *
 */
public class Explorer extends ApplicationWindow {

    private SashForm                      sashForm;
    private TreeViewer                    treeViewer;
    private TableViewer                   tableViewer;
    private OpenAction                    openAction;
    private ExitAction                    exitAction;
    private CopyFileNameToClipboardAction copyAction;

    public Explorer() {
        super( null );
        exitAction = new ExitAction( this );
        copyAction = new CopyFileNameToClipboardAction( this );
        openAction = new OpenAction( this );
        // 添加状态栏
        addStatusLine();
        // 添加菜单栏
        addMenuBar();
        // 添加工具栏
        addToolBar( SWT.FLAT | SWT.WRAP );
    }

    public static void main( String[] args ) {
        Explorer window = new Explorer();
        window.setBlockOnOpen( true );
        window.open();
        Display.getCurrent().dispose();
        Utils.getClipboard().dispose();
    }

    @Override
    protected Control createContents( Composite parent ) {

        // 设置窗口标题
        getShell().setText( "File Explorer" );

        sashForm = new SashForm( parent, SWT.HORIZONTAL | SWT.NULL );

        // 左侧文件树
        initTreeViewer();

        // 右侧文件列表
        initTableViewer();

        // 设置SashForm里的控件显示比例
        sashForm.setWeights( new int[] { 3, 7 } );

        return sashForm;
    }

    @Override
    protected MenuManager createMenuManager() {
        // 创建菜单栏

        MenuManager barMenu = new MenuManager( "" );
        MenuManager fileMenu = new MenuManager( "&File" );
        MenuManager editMenu = new MenuManager( "&Edit" );
        MenuManager viewMenu = new MenuManager( "&View" );

        barMenu.add( fileMenu );
        barMenu.add( editMenu );
        barMenu.add( viewMenu );

        fileMenu.add( exitAction ); // File > Exit
        editMenu.add( copyAction ); // Edit > Copy File Names
        editMenu.add( openAction ); // Edit > Run

        return barMenu;
    }

    @Override
    protected ToolBarManager createToolBarManager( int style ) {
        // 创建工具栏

        ToolBarManager toolBarManager = new ToolBarManager( style );

        toolBarManager.add( exitAction );
        toolBarManager.add( copyAction );
        toolBarManager.add( openAction );

        return toolBarManager;
    }

    /**
     * 左侧文件树
     */
    private void initTreeViewer() {
        treeViewer = new TreeViewer( sashForm );

        treeViewer.setContentProvider( new FileTreeContentProvider() );
        treeViewer.setLabelProvider( new FileTreeLabelProvider() );
        treeViewer.setInput( new File( "D:\\" ) ); // setInput方法告诉控件要显示哪些项目
        treeViewer.addFilter( new AllowOnlyFoldersFilter() ); // 过滤文件树只显示目录

        // 树结点选择事件：在右侧显示文件列表
        treeViewer.addSelectionChangedListener( new ISelectionChangedListener() {

            @Override
            public void selectionChanged( SelectionChangedEvent event ) {
                // 获得当前选择的结点，将其作为TableViewer的输入
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                Object selectedFile = selection.getFirstElement();
                tableViewer.setInput( selectedFile ); // setInput方法告诉控件要显示哪些项目
            }
        } );

        // 树结点双击事件：展开树结点
        treeViewer.addDoubleClickListener( new IDoubleClickListener() {

            @Override
            public void doubleClick( DoubleClickEvent event ) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                Object selectedFile = selection.getFirstElement();
                openFolder( (File) selectedFile );
            }
        } );
    }

    /**
     * 右侧文件列表
     */
    private void initTableViewer() {
        // SWT.FULL_SELECTION 选中某行后，整行都将被突出显示
        // SWT.MULTI 允许多选
        tableViewer = new TableViewer( sashForm, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI );

        tableViewer.setContentProvider( new FileTableContentProvider() );
        tableViewer.setLabelProvider( new FileTableLabelProvider() );
        tableViewer.setSorter( new FileSorter() ); // 修改排序规则

        // 右侧文件列表显示列
        addColumnToTable();

        // 表项选择事件：修改状态栏信息
        tableViewer.addSelectionChangedListener( new ISelectionChangedListener() {

            @Override
            public void selectionChanged( SelectionChangedEvent event ) {
                // 当前选中结点时，在状态栏显示选中的项目数
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                setStatus( "Number of items selected is " + selection.size() );
            }
        } );

        // 表项选择事件：根据选择项目调整Run菜单的状态、文本和提示
        tableViewer.addSelectionChangedListener( openAction );

        // 表项双击事件：打开文件或展开文件夹
        tableViewer.addDoubleClickListener( new IDoubleClickListener() {

            @Override
            public void doubleClick( DoubleClickEvent event ) {
                // 双击打开文件或展开文件夹
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();

                if ( selection.size() != 1 ) {
                    return;
                }
                File selectedFile = (File) selection.getFirstElement();

                if ( selectedFile.isFile() ) {
                    Program.launch( selectedFile.getAbsolutePath() );
                }

                if ( selectedFile.isDirectory() ) {
                    tableViewer.setInput( selectedFile );
                    openFolder( selectedFile );
                }
            }
        } );

        // 添加右键菜单
        MenuManager menuManager = new MenuManager();
        tableViewer.getTable().setMenu( menuManager.createContextMenu( tableViewer.getTable() ) );
        menuManager.add( exitAction );
        menuManager.add( copyAction );
        menuManager.add( openAction );

    }

    /**
     * 添加右侧表格显示列
     */
    private void addColumnToTable() {
        TableColumn name = new TableColumn( tableViewer.getTable(), SWT.LEFT );
        name.setText( "Name" );
        name.setWidth( 200 );

        TableColumn updateDate = new TableColumn( tableViewer.getTable(), SWT.LEFT );
        updateDate.setText( "Update Date" );
        updateDate.setWidth( 180 );

        TableColumn type = new TableColumn( tableViewer.getTable(), SWT.LEFT );
        type.setText( "Type" );
        type.setWidth( 100 );

        TableColumn size = new TableColumn( tableViewer.getTable(), SWT.LEFT );
        size.setText( "Size" );
        size.setWidth( 100 );

        tableViewer.getTable().setHeaderVisible( true );
    }

    /**
     * 取右侧表格选中的行
     * 
     * @return
     */
    public IStructuredSelection getTableSelection() {
        return (IStructuredSelection) (tableViewer.getSelection());
    }

    /**
     * 展开文件树结点
     * 
     * @param folder
     */
    public void openFolder( File folder ) {
        treeViewer.setExpandedState( folder, true );
        treeViewer.setSelection( new StructuredSelection( folder ), false );
    }
}
