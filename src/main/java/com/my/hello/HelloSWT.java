package com.my.hello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CBanner;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.PopupList;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tracker;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.my.util.Utils;

/**
 * 1. Display是一个顶层容器组件，类似于Container或Component的功能,<br>
 * 主要负责与底层的窗口系统之间的连接。具体的代表“屏幕”。一个Display<br>
 * 可以包含多个Shell，通过一个应用程序只含一个Display（即一个单例组件）。<br>
 * <br>
 * 
 * 2. Shell表示位于“屏幕”上面的窗口，是Composite组件和Control组件构成<br>
 * 的组件树的根。Composite是可以包含其它Composite和Control的容器。<br>
 * Control是一个重量级系统对象，按钮、标签、表格、工具栏和树都是Control<br>
 * 的子类，Composite和Shell也是。<br>
 * <br>
 * 
 * 3. 资源释放原则：<br>
 * - 如果你创建了某个资源，那么你就有责任释放它<br>
 * - 释放父组件资源的同时也释放了其子组件的资源<br>
 * <br>
 * 
 * 4. 窗口组件被创建的时候必须伴随一个他上层组件：<br>
 * Button button = new Button(shell, SWT.PUSH);<br>
 * - Button的父组件Shell是必不可少的，这样就限定了我们生成组件的顺序<br>
 * - 第二个参数称为“Style Bit”, 表示了这个组件的显示特性，每个特性占一位<br>
 * Text test = new Text(group, SWT.SINGLE|SWT.BORDER);<br>
 * - 以上代码生成了一个单一的、有边框的文本框<br>
 * <br>
 * 
 * 5. Error&Exception<br>
 * Throwable --> Error SWTError<br>
 * Error --> SWTError<br>
 * Exception --> RuntimeException<br>
 * RuntimeException --> IllegalArgumentException SWTException<br>
 * <br>
 * SWTError指的是不能修复的错误，以及一些操作系统错误<br>
 * SWTException指的是一些可恢复的错误以及无效的线程访问之类的错误<br>
 * IllegalArgumentException指可修复的错误或参数为null之类的错误<br>
 * <br>
 * 
 * 6. Item类是一个轻量级的系统对象，总的作为基本的单位元素与其他类配合使用。<br>
 * 如Tree中的元素即为TreeItem，Table的单位元素则为TableItem，而MenuItem<br>
 * 就是Menu的基本单位元素。<br>
 * <br>
 * 
 * 7. SWT的类结构<br>
 * 以{@link org.eclipse.swt.widgets.Widget}为父类的类体系结构<br>
 * 以{@link org.eclipse.swt.widgets.Dialog}为父类的类体系结构<br>
 * <br>
 * 
 * 8. SWT的布局管理<br>
 * Composite.setLayout()方法<br>
 * FillLayout-->让所有子组件等大小填满整个面板空间<br>
 * RowLayout -->让所有组件按行排列，一行排不下就放到下一行，可指定RowData<br>
 * GridLayout-->将所有组件放置在一个网格中，可指定GridData<br>
 * FormLayout-->组件之间使用相对定位<br>
 * StackLayout-->几乎完全等同于CardLyout的功能<br>
 * 
 * 在SWT中，位置和大小的变化并非自动发生，应用程序即可以在Composite子类的构造<br>
 * 函数中指定初始位置和大小，也可以在一个改变窗口大小的监听器中用布局类来定位<br>
 * 和惊变Composite子类的大小<br>
 * 
 * 9. 用SWT绘制2D图形<br>
 * 一是借助Graphics Context，另一种是利用Draw2D。Draw2D是一个基于SWT Composite<br>
 * 的轻量级组件，在效率上，无法体现出Native Code的速度优势，仅适用于绘图工作不是<br>
 * 系统瓶颈的应用程序。<br>
 * <br>
 * Graphics Context<br>
 * 在任何实现了org.eclipse.swt.graphics.Drawable接口的类上绘制图形，这包括一个<br>
 * 控件、一幅图像、一个显示设备或一个打印设备。类org.eclipse.swt.graphics.GC是一<br>
 * 个封装了所有可执行的绘图操作的图形上下文（Graphics Context）<br>
 * 
 * @author yang.dongdong
 *
 */
public class HelloSWT {

    public static void main( String[] args ) {
        new HelloSWT().runApp();
    }

    /**
     * Run the application
     */
    public void runApp() {
        Display display = new Display();

        Shell shell = new Shell( display );
        shell.setSize( 1160, 630 );
        shell.setText( "Hello SWT" );

        // Shell Layout
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        shell.setLayout( layout );

        createContents( shell );

        shell.addShellListener( new MyShellAdapter( shell ) );
        shell.open();

        // 反复读取和分派事件，并在没有事件的时候把控制权还给CPU
        while ( !shell.isDisposed() ) {
            if ( !display.readAndDispatch() )
                display.sleep();
        }

        display.dispose();

    }

    /**
     * 创建窗口内容
     * 
     * @param shell
     */
    private void createContents( Shell shell ) {
        // Label
        createLabel( shell );

        // MenuBar
        createMenu( shell );

        // Text
        createText( shell );

        // 复合控件，在SWT中，控件组合是通过Composite类实现的
        createComposite( shell );

        // Combo
        createCombo( shell );

        // List
        createList( shell );

        // Tree
        createTree( shell );

        // Table
        createTable( shell );

        // Tab
        createTab( shell );

        // Dialog
        createDialog( shell );

        // ToolBar
        createToolBar( shell );
    }

    /**
     * Create Label
     * 
     * @param shell
     */
    private void createLabel( Shell shell ) {
        Label label = new Label( shell, SWT.NONE );
        label.setText( "Label1: Label text" );

        CLabel cLabel = new CLabel( shell, SWT.NONE );
        cLabel.setText( "Label2: Imaged label text" );
        cLabel.setImage( Utils.getImageRegistry().get( "run" ) );

    }

    /**
     * Create Menu
     * 
     * @param shell
     */
    private void createMenu( Shell shell ) {
        // Create the menu bar system
        Menu main = new Menu( shell, SWT.BAR | SWT.LEFT_TO_RIGHT );
        shell.setMenuBar( main );

        // File --> Exit
        MenuItem fileMenuItem = new MenuItem( main, SWT.CASCADE );
        fileMenuItem.setText( "&File" );
        fileMenuItem.setEnabled( true );

        Menu fileMenu = new Menu( shell, SWT.DROP_DOWN );
        fileMenu.setEnabled( true );
        fileMenuItem.setMenu( fileMenu );

        MenuItem exitMenuItem = new MenuItem( fileMenu, SWT.PUSH );
        exitMenuItem.setText( "E&xit\tCtrl+X" );
        exitMenuItem.setAccelerator( SWT.CTRL + 'X' );
        exitMenuItem.setEnabled( true );
        exitMenuItem.addSelectionListener( new MySelectionAdapter( 1 ) );

        // Help --> About
        MenuItem helpMenuItem = new MenuItem( main, SWT.CASCADE );
        helpMenuItem.setText( "&Help" );
        helpMenuItem.setEnabled( true );

        Menu helpMenu = new Menu( shell, SWT.DROP_DOWN );
        helpMenu.setEnabled( true );
        helpMenuItem.setMenu( helpMenu );

        MenuItem aboutMenuItem = new MenuItem( helpMenu, SWT.PUSH );
        aboutMenuItem.setText( "&About\tCtrl+H" );
        aboutMenuItem.setAccelerator( SWT.CTRL + 'H' );
        aboutMenuItem.setEnabled( true );
        aboutMenuItem.addSelectionListener( new MySelectionAdapter( 2 ) );

        // Add pop up menu
        Menu popup = new Menu( shell, SWT.POP_UP );
        shell.setMenu( popup );

        MenuItem popupMenuItem1 = new MenuItem( popup, SWT.PUSH );
        popupMenuItem1.setText( "&About" );
        popupMenuItem1.setEnabled( true );
        popupMenuItem1.addSelectionListener( new MySelectionAdapter( 2 ) );

        MenuItem popupMenuItem2 = new MenuItem( popup, SWT.PUSH );
        popupMenuItem2.setText( "&Noop" );
        popupMenuItem2.setEnabled( true );
        popupMenuItem2.addSelectionListener( new MySelectionAdapter( 3 ) );

    }

    /**
     * Create Text
     * 
     * @param shell
     */
    private void createText( Shell shell ) {

        Composite container = new Composite( shell, SWT.NONE );
        GridLayout layout = new GridLayout( 2, false );
        container.setLayout( layout );

        Label label = new Label( container, SWT.LEFT );
        label.setText( "Cursor: " );
        label.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, true, true ) );

        Text text = new Text( container, SWT.SINGLE | SWT.BORDER );
        text.setText( "--<none>--" );
        text.setTextLimit( 50 );
        text.setToolTipText( "Enter you name -- Last, First" );
        text.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, true, true ) );
        text.selectAll(); // enable fast erase

        // 在这个Text里显示当前鼠标坐标
        shell.addMouseMoveListener( new MousePositionListener( text ) );

    }

    /**
     * 复合控件，在SWT中，控件组合是通过Composite类实现的
     * 
     * @param shell
     */
    private void createComposite( Shell shell ) {
        Group buttons = new Group( shell, SWT.SHADOW_OUT );
        GridLayout bLayout = new GridLayout( 3, false );
        bLayout.marginHeight = 10;
        bLayout.marginWidth = 10;
        buttons.setLayout( bLayout );
        buttons.setText( "SWT Buttons" );

        // Button
        // ARROW:显示为一个指向上、下、左、右方向的箭头
        // CHECK:已标记的复选标记
        // FLAT：没有凸起外观的按钮
        // PUSH：瞬时按钮（最常见的事件源）
        // RADIO：具有排他性的粘性标记（sticky mark），其他所有单选按钮都在相同的组中
        // TOGGLE：一个粘性按钮
        Button clear = new Button( buttons, SWT.PUSH );
        clear.setText( "Clea&r" );
        clear.addSelectionListener( new ButtonSelectionListener( shell, 1 ) );// 事件监听
        Button arrowRight = new Button( buttons, SWT.ARROW | SWT.RIGHT );
        arrowRight.setText( "ArrowRight" );
        Button check = new Button( buttons, SWT.CHECK );
        check.setText( "Check" );
        Button flat = new Button( buttons, SWT.FLAT );
        flat.setText( "Flat" );
        flat.addSelectionListener( new ButtonSelectionListener( shell, 2 ) );
        Button radio = new Button( buttons, SWT.RADIO );
        radio.setText( "Radio" );
        Button toggle = new Button( buttons, SWT.TOGGLE );
        toggle.setText( "Toggle" );

    }

    /**
     * Create Combo
     * 
     * @param shell
     */
    private void createCombo( Shell shell ) {
        String[] data = { "ComboItem1", "ComboItem2", "ComboItem3", "ComboItem4", "ComboItem5", "ComboItem6",
                "ComboItem7", "ComboItem8", "ComboItem9", "ComboItem10", };

        Composite container = new Composite( shell, SWT.NONE );
        GridLayout layout = new GridLayout( 2, false );
        container.setLayout( layout );

        Label label = new Label( container, SWT.LEFT );
        label.setText( "Combo: " );
        label.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, true, true ) );

        Combo combo = new Combo( container, SWT.DROP_DOWN | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL );
        combo.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, true, true ) );
        // combo.addSelectionListener( new SelectionListener() { ... } );

        combo.removeAll();
        for ( int i = 0; i < data.length; i++ ) {
            combo.add( data[i] );
        }

    }

    /**
     * Create List
     * 
     * @param shell
     */
    private void createList( Shell shell ) {
        String[] data = { "ListItem1", "ListItem2", "ListItem3", "ListItem4", "ListItem5", "ListItem6", "ListItem7",
                "ListItem8", "ListItem9", "ListItem10", };

        Composite container = new Composite( shell, SWT.NONE );
        GridLayout layout = new GridLayout( 2, false );
        container.setLayout( layout );

        Label label = new Label( container, SWT.LEFT );
        label.setText( "List: " );
        label.setLayoutData( new GridData( SWT.LEFT, SWT.TOP, true, true ) );

        List list = new List( container, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL );
        list.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, true, true ) );
        // list.addSelectionListener( new SelectionListener() { ... } );

        list.removeAll();
        for ( int i = 0; i < data.length; i++ ) {
            list.add( data[i] );
        }
    }

    /**
     * Create Tree
     * 
     * @param shell
     */
    private void createTree( Shell shell ) {
        TreeNode root = new TreeNode( "<root>" );
        addChildren( root, 3, 3, "Child" );

        Tree tree = new Tree( shell, SWT.CHECK | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL );
        // tree.addSelectionListener( new SelectionListener() { ... } );

        tree.removeAll();
        TreeItem ti = new TreeItem( tree, SWT.NONE );
        setTreeItemContents( ti, root );
        tree.setLayoutData( new RowData( 200, 250 ) );

    }

    /**
     * 设置树结点内容
     * 
     * @param ti
     * @param root
     */
    private void setTreeItemContents( TreeItem ti, TreeNode root ) {
        ti.setText( root.getName() );
        java.util.List < TreeNode > children = root.getChildren();
        if ( children != null && children.size() > 0 ) {
            for ( Iterator < TreeNode > i = children.iterator(); i.hasNext(); ) {
                TreeNode n = (TreeNode) i.next();
                TreeItem tix = new TreeItem( ti, SWT.NONE );
                setTreeItemContents( tix, n );
            }
        }
    }

    /**
     * 向树添加子结点
     * 
     * @param n
     * @param count
     * @param depth
     * @param prefix
     */
    private void addChildren( TreeNode n, int count, int depth, String prefix ) {
        if ( depth > 0 ) {
            for ( int i = 0; i < count; i++ ) {
                String name = prefix + '.' + i;
                TreeNode child = new TreeNode( name );
                n.addChild( child );
                addChildren( child, count, depth - 1, name );
            }
        }
    }

    /**
     * Create Table
     * 
     * @param shell
     */
    private void createTable( Shell shell ) {
        Object[] items = { new String[] { "A", "a", "0" }, new String[] { "B", "b", "1" },
                new String[] { "C", "c", "2" }, new String[] { "D", "d", "3" }, new String[] { "E", "e", "4" },
                new String[] { "F", "f", "5" }, new String[] { "G", "g", "6" }, new String[] { "H", "h", "7" },
                new String[] { "I", "i", "8" }, new String[] { "J", "j", "9" } };

        Table table = new Table( shell, SWT.CHECK | SWT.SINGLE | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL );
        table.setHeaderVisible( true );
        table.setLinesVisible( true );

        TableColumn tc1 = new TableColumn( table, SWT.LEFT );
        tc1.setText( "Column 1" );
        tc1.setResizable( true );
        tc1.setWidth( 100 );
        TableColumn tc2 = new TableColumn( table, SWT.CENTER );
        tc2.setText( "Column 2" );
        tc2.setResizable( true );
        tc2.setWidth( 100 );
        TableColumn tc3 = new TableColumn( table, SWT.RIGHT );
        tc3.setText( "Column 3" );
        tc3.setResizable( true );
        tc3.setWidth( 100 );

        for ( int i = 0; i < items.length; i++ ) {
            String[] item = (String[]) items[i];
            TableItem ti = new TableItem( table, SWT.NONE );
            ti.setText( item );
        }

    }

    /**
     * Create Tabs
     * 
     * @param shell
     */
    private void createTab( Shell shell ) {
        TabFolder tabs = new TabFolder( shell, SWT.TOP );

        TabItem canvas = new TabItem( tabs, SWT.NONE, 0 );
        canvas.setText( "Canvas" );

        TabItem progressBar = new TabItem( tabs, SWT.NONE, 1 );
        progressBar.setText( "ProgressBar" );

        TabItem styledText = new TabItem( tabs, SWT.NONE, 2 );
        styledText.setText( "StyledText" );

        TabItem stack = new TabItem( tabs, SWT.NONE, 3 );
        stack.setText( "StackLayout" );

        createCanvas( shell, tabs, canvas );
        createProgressBar( shell, tabs, progressBar );
        createStyledText( shell, tabs, styledText );
        createStackLayout( shell, tabs, stack );
    }

    /**
     * 创建画布，随机绘制一些图形
     */
    private void createCanvas( Shell shell, TabFolder tabs, TabItem tab ) {
        Composite container = new Composite( tabs, SWT.NONE );
        Canvas canvas = new Canvas( container, SWT.NONE );
        Composite buttons = new Composite( container, SWT.NONE );

        RowLayout layout = new RowLayout( SWT.VERTICAL );
        container.setLayout( layout );
        RowLayout buttonLayout = new RowLayout( SWT.HORIZONTAL );
        buttons.setLayout( buttonLayout );

        java.util.List < PaintItem > gcObjects = new ArrayList < PaintItem >();
        canvas.addPaintListener( new MyPaintListener( gcObjects ) );
        canvas.setLayoutData( new RowData( 320, 180 ) );

        Button draw = new Button( buttons, SWT.PUSH );
        draw.setText( "Draw" );
        draw.addSelectionListener( new CanvasButtonListener( canvas, gcObjects, 1 ) );

        Button clear = new Button( buttons, SWT.PUSH );
        clear.setText( "Clear" );
        clear.addSelectionListener( new CanvasButtonListener( canvas, gcObjects, 2 ) );

        // 将创建好的控件绑定到选项卡
        tab.setControl( container );

    }

    /**
     * 创建滚动条、滑块选择器、进度条等控件
     * 
     * @param tabs
     * @param tab
     */
    private void createProgressBar( Shell shell, TabFolder tabs, TabItem tab ) {
        // Scale允许在（通过很小）整数范围内选择一个值
        // Slider允许使用类似滚动条的方法在（可能很大）整数范围内选择一个值
        // Spinner允许选择或键入一个（可能为小数的）数字，since V3.1
        // ProgressBar类似于一个只输出的Slider

        Composite container = new Composite( tabs, SWT.NONE );
        Group group = new Group( container, SWT.SHADOW_OUT );
        // 显示进度的文本标签
        Label valueLabel = new Label( container, SWT.CENTER );
        // 进度条
        ProgressBar bar = new ProgressBar( container, SWT.SMOOTH | SWT.HORIZONTAL );

        GridLayout layout = new GridLayout( 1, true );
        container.setLayout( layout );

        GridLayout groupLayout = new GridLayout( 2, false );
        group.setText( "Control the ProgressBar" );
        group.setLayout( groupLayout );
        group.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, true ) );

        // AutoUpdate按钮、滚动条、滑块选择器等
        Button modeButton = new Button( group, SWT.CHECK );
        Label sliderLabel = new Label( group, SWT.NONE );
        Slider slider = new Slider( group, SWT.HORIZONTAL );
        Label spinnerLabel = new Label( group, SWT.NONE );
        Spinner spinner = new Spinner( group, SWT.HORIZONTAL | SWT.READ_ONLY );
        Label scaleLabel = new Label( group, SWT.NONE );
        Scale scale = new Scale( group, SWT.HORIZONTAL );

        modeButton.setText( "Automatic Update" );
        modeButton.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, true, 2, 1 ) );

        sliderLabel.setText( "Bar Value:" );
        slider.setMinimum( 0 );
        slider.setMaximum( 100 );
        slider.setSelection( 0 );
        slider.setIncrement( 1 );
        slider.setPageIncrement( 10 );
        slider.setThumb( 1 );
        slider.addSelectionListener( new ProgressSelectionListener( 1, slider, spinner, bar, valueLabel ) );
        slider.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, true ) );

        spinnerLabel.setText( "Bar Value:" );
        spinner.setMinimum( 0 );
        spinner.setMaximum( 100 );
        spinner.setSelection( 0 );
        spinner.setIncrement( 1 );
        spinner.setPageIncrement( 10 );
        spinner.addSelectionListener( new ProgressSelectionListener( 2, spinner, slider, bar, valueLabel ) );
        spinner.addMouseWheelListener( new SpinnerMouseWheelListener( slider, bar, valueLabel ) );
        spinner.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, true ) );

        scaleLabel.setText( "Update Rate:" );
        scale.setMinimum( 0 );
        scale.setMaximum( 500 );
        scale.setSelection( 0 );
        scale.setIncrement( 10 );
        scale.setPageIncrement( 100 );
        scale.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, true ) );

        valueLabel.setText( "0%" );
        valueLabel.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, true ) );

        bar.setMinimum( 0 );
        bar.setMaximum( 100 );
        bar.setSelection( 0 );
        bar.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, true ) );

        // 开启自动更新
        BarUpdater barUpdater = new BarUpdater( tabs.getDisplay(), modeButton, bar, spinner, slider, scale, valueLabel );
        barUpdater.setDaemon( true );
        barUpdater.start();

        // 将创建好的控件绑定到选项卡
        tab.setControl( container );

    }

    /**
     * 创建带格式的文本
     * 
     * @param tabs
     * @param tab
     */
    private void createStyledText( Shell shell, TabFolder tabs, TabItem tab ) {
        Display display = tabs.getDisplay();
        StyledText styledText = new StyledText( tabs, SWT.MULTI | SWT.WRAP | SWT.FULL_SELECTION );
        styledText.addMouseListener( new StyledTextMouseListener( shell, styledText ) );
        styledText.setText( "0123456789 ABCDEFGHIJKLM NOPQRSTUVWXYZ" );

        // make 0123456789 appear bold
        StyleRange style1 = new StyleRange();
        style1.start = 0;
        style1.length = 10;
        style1.fontStyle = SWT.BOLD;
        styledText.setStyleRange( style1 );
        // make ABCDEFGHIJKLM have a red font
        StyleRange style2 = new StyleRange();
        style2.start = 11;
        style2.length = 13;
        style2.foreground = display.getSystemColor( SWT.COLOR_RED );
        styledText.setStyleRange( style2 );
        // make NOPQRSTUVWXYZ have a blue background
        StyleRange style3 = new StyleRange();
        style3.start = 25;
        style3.length = 13;
        style3.background = display.getSystemColor( SWT.COLOR_GRAY );
        styledText.setStyleRange( style3 );

        // 将创建好的控件绑定到选项卡
        tab.setControl( styledText );

    }

    /**
     * 创建Popup列表菜单，即右键弹出的类似菜单的列表
     * 
     * @param shell
     * @param styledText
     */
    private void createPopupList( Shell shell, StyledText styledText ) {
        String[] popupItems = { "Select All", "Cut", "Copy", "Paste" };

        PopupList popup = new PopupList( shell, SWT.NONE );
        popup.setMinimumWidth( 50 );
        popup.setItems( popupItems );
        // popup.select( popupItems[0] );
        Point p = styledText.getLocation();

        p = shell.getDisplay().map( styledText, null, p.x, p.y );

        String choice = popup.open( new Rectangle( p.x + 100, p.y - 200, 100, 200 ) );
        if ( choice != null ) {
            if ( popupItems[0].equals( choice ) ) {
                styledText.selectAll();
            }
            if ( popupItems[1].equals( choice ) ) {
                styledText.cut();
            }
            if ( popupItems[2].equals( choice ) ) {
                styledText.copy();
            }
            if ( popupItems[3].equals( choice ) ) {
                styledText.paste();
            }
        }
    }

    /**
     * 创建StackLayout，适合创建Wizard，第一步、第二步...
     * 
     * @param shell
     * @param tabs
     * @param tab
     */
    private void createStackLayout( Shell shell, TabFolder tabs, TabItem tab ) {
        Composite container = new Composite( tabs, SWT.NONE );

        GridLayout layout = new GridLayout( 2, false );
        container.setLayout( layout );

        StackLayout stackLayout = new StackLayout();
        Composite clabels = new Composite( container, SWT.BORDER );
        clabels.setLayout( stackLayout );
        clabels.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );

        Label[] labels = new Label[5];

        for ( int i = 0; i < labels.length; i++ ) {
            Label xlabel = new Label( clabels, SWT.CENTER );
            xlabel.setText( "Step " + i );
            xlabel.setFont( new Font( shell.getDisplay(), "Arial", 70, SWT.NORMAL ) );
            labels[i] = xlabel;
        }

        stackLayout.topControl = labels[0];

        Button previous = new Button( container, SWT.PUSH );
        previous.setText( "<<" );
        previous.addSelectionListener( new StackLayoutPreNextListener( 1, stackLayout, clabels, labels ) );
        Button next = new Button( container, SWT.PUSH );
        next.setText( ">>" );
        next.addSelectionListener( new StackLayoutPreNextListener( 2, stackLayout, clabels, labels ) );

        // 将创建好的控件绑定到选项卡
        tab.setControl( container );
    }

    /**
     * 文件选择、目录选择、颜色选择、字体选择对话框
     * 
     * @param shell
     */
    private void createDialog( Shell shell ) {

        Composite container = new Composite( shell, SWT.NONE );
        RowLayout layout = new RowLayout( SWT.HORIZONTAL );
        container.setLayout( layout );

        Button fileButton = new Button( container, SWT.PUSH );
        Button dirButton = new Button( container, SWT.PUSH );
        Button colorButton = new Button( container, SWT.PUSH );
        Button fontButton = new Button( container, SWT.PUSH );
        Text value = new Text( container, SWT.BORDER | SWT.SINGLE );
        value.setLayoutData( new RowData( 400, 20 ) );

        fileButton.setText( "File" );
        fileButton.addSelectionListener( new DialogSelectionListener( shell, value, 1 ) );

        dirButton.setText( "Directory" );
        dirButton.addSelectionListener( new DialogSelectionListener( shell, value, 2 ) );

        colorButton.setText( "Color" );
        colorButton.addSelectionListener( new DialogSelectionListener( shell, value, 3 ) );

        fontButton.setText( "Font" );
        fontButton.addSelectionListener( new DialogSelectionListener( shell, value, 4 ) );

    }

    private void createToolBar( Shell shell ) {
        SashForm container = new SashForm( shell, SWT.HORIZONTAL );
        container.setBackground( shell.getDisplay().getSystemColor( SWT.COLOR_LIST_SELECTION ) );
        container.setLayoutData( new RowData( 600, 260 ) );

        // 左边的SashForm，左、中、右三块

        Composite left = new Composite( container, SWT.BORDER );
        left.setLayout( new FillLayout() );
        SashForm leftSash = new SashForm( left, SWT.HORIZONTAL );

        Composite leftPanel = new Composite( leftSash, SWT.BORDER );
        leftPanel.setLayout( new FillLayout() );
        Label leftLabel = new Label( leftPanel, SWT.LEFT );
        leftLabel.setText( "L" );
        leftLabel.setBackground( shell.getDisplay().getSystemColor( SWT.COLOR_CYAN ) );

        Composite centerPanel = new Composite( leftSash, SWT.BORDER );
        centerPanel.setLayout( new FillLayout() );
        Label centerLabel = new Label( centerPanel, SWT.CENTER );
        centerLabel.setText( "M" );
        centerLabel.setBackground( shell.getDisplay().getSystemColor( SWT.COLOR_YELLOW ) );

        Composite rightPanel = new Composite( leftSash, SWT.BORDER );
        rightPanel.setLayout( new FillLayout() );
        Label rightLabel = new Label( rightPanel, SWT.RIGHT );
        rightLabel.setText( "R" );
        rightLabel.setBackground( shell.getDisplay().getSystemColor( SWT.COLOR_GREEN ) );

        leftSash.setWeights( new int[] { 33, 33, 33 } );

        Composite right = new Composite( container, SWT.BORDER );
        right.setLayout( new GridLayout( 2, false ) );

        container.setWeights( new int[] { 20, 80 } );

        // 右边的工具栏按钮，普通水平的工具栏

        ToolBar toolBar = new ToolBar( right, SWT.VERTICAL );
        toolBar.setLayoutData( new GridData( SWT.CENTER, SWT.CENTER, false, false, 1, 7 ) );

        ToolItem tracker = new ToolItem( toolBar, SWT.PUSH );
        tracker.setText( "Tracker" );
        tracker.setToolTipText( "Open a Tracker" );
        tracker.addSelectionListener( new OpenTrackerListener( right ) );
        ToolItem check = new ToolItem( toolBar, SWT.CHECK );
        check.setText( "Check" );
        check.setToolTipText( "Example Check Item" );
        ToolItem drop = new ToolItem( toolBar, SWT.DROP_DOWN );
        drop.setText( "Drop" );
        drop.setToolTipText( "Example Drop Down Item" );
        drop.addSelectionListener( new ToolBarSelectionListener( toolBar ) );

        ToolItem separator = new ToolItem( toolBar, SWT.SEPARATOR );
        separator.setText( "Separator" );
        separator.setToolTipText( "Example Separator Item" );
        separator.setControl( new Label( toolBar, SWT.SEPARATOR | SWT.HORIZONTAL ) );

        ToolItem radio1 = new ToolItem( toolBar, SWT.RADIO );
        radio1.setText( "Radio 1" );
        radio1.setToolTipText( "Example Radio Item" );
        ToolItem radio2 = new ToolItem( toolBar, SWT.RADIO );
        radio2.setText( "Radio 2" );
        radio2.setToolTipText( "Example Radio Item" );
        ToolItem radio3 = new ToolItem( toolBar, SWT.RADIO );
        radio3.setText( "Radio 3" );
        radio3.setToolTipText( "Example Radio Item" );

        // 水平工具栏，分隔栏圆角的工具栏

        CBanner cBanner = new CBanner( right, SWT.NONE );
        cBanner.setSimple( true );
        cBanner.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false, 1, 1 ) );

        ToolBar toolBar1 = new ToolBar( cBanner, SWT.HORIZONTAL );
        ToolBar toolBar2 = new ToolBar( cBanner, SWT.HORIZONTAL );

        cBanner.setLeft( toolBar1 );
        cBanner.setRight( toolBar2 );

        ToolItem leftPress = new ToolItem( toolBar1, SWT.PUSH );
        leftPress.setText( "Press" );
        leftPress.setToolTipText( "Example Button Item" );
        ToolItem leftCheck = new ToolItem( toolBar1, SWT.CHECK );
        leftCheck.setText( "Check" );
        leftCheck.setToolTipText( "Example Check Item" );
        ToolItem leftDrop = new ToolItem( toolBar1, SWT.DROP_DOWN );
        leftDrop.setText( "Drop" );
        leftDrop.setToolTipText( "Example Drop Down Item" );
        leftDrop.addSelectionListener( new ToolBarSelectionListener( toolBar1 ) );

        ToolItem leftSeparator = new ToolItem( toolBar1, SWT.SEPARATOR );
        leftSeparator.setText( "Separator" );
        leftSeparator.setToolTipText( "Example Separator Item" );
        leftSeparator.setControl( new Label( toolBar1, SWT.SEPARATOR | SWT.VERTICAL ) );

        ToolItem leftRadio1 = new ToolItem( toolBar1, SWT.RADIO );
        leftRadio1.setText( "Radio 1" );
        leftRadio1.setToolTipText( "Example Radio Item" );
        ToolItem leftRadio2 = new ToolItem( toolBar1, SWT.RADIO );
        leftRadio2.setText( "Radio 2" );
        leftRadio2.setToolTipText( "Example Radio Item" );
        ToolItem leftRadio3 = new ToolItem( toolBar1, SWT.RADIO );
        leftRadio3.setText( "Radio 3" );
        leftRadio3.setToolTipText( "Example Radio Item" );

        ToolItem rightPress = new ToolItem( toolBar2, SWT.PUSH );
        rightPress.setText( "Press" );
        rightPress.setToolTipText( "Example Button Item" );
        ToolItem rightCheck = new ToolItem( toolBar2, SWT.CHECK );
        rightCheck.setText( "Check" );
        rightCheck.setToolTipText( "Example Check Item" );
        ToolItem rightDrop = new ToolItem( toolBar2, SWT.DROP_DOWN );
        rightDrop.setText( "Drop" );
        rightDrop.setToolTipText( "Example Drop Down Item" );
        rightDrop.addSelectionListener( new ToolBarSelectionListener( toolBar2 ) );

        ToolItem rightSeparator = new ToolItem( toolBar2, SWT.SEPARATOR );
        rightSeparator.setText( "Separator" );
        rightSeparator.setToolTipText( "Example Separator Item" );
        rightSeparator.setControl( new Label( toolBar2, SWT.SEPARATOR | SWT.VERTICAL ) );

        ToolItem rightRadio1 = new ToolItem( toolBar2, SWT.RADIO );
        rightRadio1.setText( "Radio 1" );
        rightRadio1.setToolTipText( "Example Radio Item" );
        ToolItem rightRadio2 = new ToolItem( toolBar2, SWT.RADIO );
        rightRadio2.setText( "Radio 2" );
        rightRadio2.setToolTipText( "Example Radio Item" );
        ToolItem rightRadio3 = new ToolItem( toolBar2, SWT.RADIO );
        rightRadio3.setText( "Radio 3" );
        rightRadio3.setToolTipText( "Example Radio Item" );

        // 水平图标工具栏，工具栏显示图标

        CBanner cImageBanner = new CBanner( right, SWT.NONE );
        cImageBanner.setSimple( false );
        cImageBanner.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false, 1, 1 ) );

        ToolBar toolBarImage1 = new ToolBar( cImageBanner, SWT.HORIZONTAL );
        ToolBar toolBarImage2 = new ToolBar( cImageBanner, SWT.HORIZONTAL );

        cImageBanner.setLeft( toolBarImage1 );
        cImageBanner.setRight( toolBarImage2 );

        ToolItem leftImagePress = new ToolItem( toolBarImage1, SWT.PUSH );
        // leftImagePress.setText( "Press" );
        leftImagePress.setImage( Utils.getImageRegistry().get( "app1" ) );
        leftImagePress.setToolTipText( "Example Button Item" );
        ToolItem leftImageCheck = new ToolItem( toolBarImage1, SWT.CHECK );
        // leftImageCheck.setText( "Check" );
        leftImageCheck.setImage( Utils.getImageRegistry().get( "app2" ) );
        leftImageCheck.setToolTipText( "Example Check Item" );
        ToolItem leftImageDrop = new ToolItem( toolBarImage1, SWT.DROP_DOWN );
        // leftImageDrop.setText( "Drop" );
        leftImageDrop.setImage( Utils.getImageRegistry().get( "app3" ) );
        leftImageDrop.setToolTipText( "Example Drop Down Item" );
        leftImageDrop.addSelectionListener( new ToolBarSelectionListener( toolBarImage1 ) );

        ToolItem leftImageSeparator = new ToolItem( toolBarImage1, SWT.SEPARATOR );
        leftImageSeparator.setText( "Separator" );
        leftImageSeparator.setToolTipText( "Example Separator Item" );
        leftImageSeparator.setControl( new Label( toolBarImage1, SWT.SEPARATOR | SWT.VERTICAL ) );

        ToolItem leftImageRadio1 = new ToolItem( toolBarImage1, SWT.RADIO );
        // leftImageRadio1.setText( "Radio 1" );
        leftImageRadio1.setImage( Utils.getImageRegistry().get( "app4" ) );
        leftImageRadio1.setToolTipText( "Example Radio Item" );
        ToolItem leftImageRadio2 = new ToolItem( toolBarImage1, SWT.RADIO );
        // leftImageRadio2.setText( "Radio 2" );
        leftImageRadio2.setImage( Utils.getImageRegistry().get( "app5" ) );
        leftImageRadio2.setToolTipText( "Example Radio Item" );
        ToolItem leftImageRadio3 = new ToolItem( toolBarImage1, SWT.RADIO );
        // leftImageRadio3.setText( "Radio 3" );
        leftImageRadio3.setImage( Utils.getImageRegistry().get( "app6" ) );
        leftImageRadio3.setToolTipText( "Example Radio Item" );

        ToolItem rightImagePress = new ToolItem( toolBarImage2, SWT.PUSH );
        // rightImagePress.setText( "Press" );
        rightImagePress.setImage( Utils.getImageRegistry().get( "app1" ) );
        rightImagePress.setToolTipText( "Example Button Item" );
        ToolItem rightImageCheck = new ToolItem( toolBarImage2, SWT.CHECK );
        // rightImageCheck.setText( "Check" );
        rightImageCheck.setImage( Utils.getImageRegistry().get( "app2" ) );
        rightImageCheck.setToolTipText( "Example Check Item" );
        ToolItem rightImageDrop = new ToolItem( toolBarImage2, SWT.DROP_DOWN );
        // rightImageDrop.setText( "Drop" );
        rightImageDrop.setImage( Utils.getImageRegistry().get( "app3" ) );
        rightImageDrop.setToolTipText( "Example Drop Down Item" );
        rightImageDrop.addSelectionListener( new ToolBarSelectionListener( toolBarImage2 ) );

        ToolItem rightImageSeparator = new ToolItem( toolBarImage2, SWT.SEPARATOR );
        rightImageSeparator.setText( "Separator" );
        rightImageSeparator.setToolTipText( "Example Separator Item" );
        rightImageSeparator.setControl( new Label( toolBarImage2, SWT.SEPARATOR | SWT.VERTICAL ) );

        ToolItem rightImageRadio1 = new ToolItem( toolBarImage2, SWT.RADIO );
        // rightImageRadio1.setText( "Radio 1" );
        rightImageRadio1.setImage( Utils.getImageRegistry().get( "app4" ) );
        rightImageRadio1.setToolTipText( "Example Radio Item" );
        ToolItem rightImageRadio2 = new ToolItem( toolBarImage2, SWT.RADIO );
        // rightImageRadio2.setText( "Radio 2" );
        rightImageRadio2.setImage( Utils.getImageRegistry().get( "app5" ) );
        rightImageRadio2.setToolTipText( "Example Radio Item" );
        ToolItem rightImageRadio3 = new ToolItem( toolBarImage2, SWT.RADIO );
        // rightImageRadio3.setText( "Radio 3" );
        rightImageRadio3.setImage( Utils.getImageRegistry().get( "app6" ) );
        rightImageRadio3.setToolTipText( "Example Radio Item" );

        // 可拖动的工具栏按钮，可调整位置的工具栏按钮

        CoolBar coolBar = new CoolBar( right, SWT.FLAT );
        coolBar.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false, 1, 1 ) );

        CoolItem coolItem1 = new CoolItem( coolBar, SWT.FLAT );
        coolItem1.setText( "Drop" );
        CoolItem coolItem2 = new CoolItem( coolBar, SWT.FLAT );
        coolItem2.setText( "Drop" );
        CoolItem coolItem3 = new CoolItem( coolBar, SWT.FLAT );
        coolItem3.setText( "Drop" );
        CoolItem coolItem4 = new CoolItem( coolBar, SWT.FLAT );
        coolItem4.setText( "Drop" );
        CoolItem coolItem5 = new CoolItem( coolBar, SWT.FLAT );
        coolItem5.setText( "Drop" );

        Button press1 = new Button( coolBar, SWT.PUSH );
        press1.setText( "Press1" );
        press1.addSelectionListener( new ToolBarSelectionListener( coolBar ) );
        coolItem1.setControl( press1 );

        Button press2 = new Button( coolBar, SWT.PUSH );
        press2.setText( "Press2" );
        press2.addSelectionListener( new ToolBarSelectionListener( coolBar ) );
        coolItem2.setControl( press2 );

        Button radioCoolBar1 = new Button( coolBar, SWT.RADIO );
        radioCoolBar1.setText( "Radio 1" );
        coolItem3.setControl( radioCoolBar1 );
        Button radioCoolBar2 = new Button( coolBar, SWT.RADIO );
        radioCoolBar2.setText( "Radio 2" );
        coolItem4.setControl( radioCoolBar2 );
        Button radioCoolBar3 = new Button( coolBar, SWT.RADIO );
        radioCoolBar3.setText( "Radio 3" );
        coolItem5.setControl( radioCoolBar3 );

        coolBar.setItemLayout( new int[] { 2, 3, 4, 0, 1 }, null, new Point[] { new Point( 75, 25 ),
                new Point( 75, 25 ), new Point( 75, 25 ), new Point( 80, 25 ), new Point( 80, 25 ) } );

        // 链接样式的按钮
        Link link = new Link( right, SWT.NONE );
        link.setText( "<a href=\"http://www.baidu.com\">This is a link!</a>" );
        link.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false, 1, 1 ) );
    }

    /**
     * 按钮点击事件监听
     * 
     * @author yang.dongdong
     *
     */
    class ButtonSelectionListener implements SelectionListener {

        private Shell shell;
        private int   type;

        public ButtonSelectionListener( Shell shell, int type ) {
            this.shell = shell;
            this.type = type;
        }

        @Override
        public void widgetSelected( SelectionEvent e ) {
            switch ( this.type ) {
                case 1:
                    // 弹出一个message窗口
                    MessageBox m = new MessageBox( shell, SWT.ICON_INFORMATION | SWT.OK );
                    m.setText( "Info" );
                    m.setMessage( "Clear pressed" );
                    m.open();
                    break;
                case 2:
                    // 打开一个子窗口
                    openSubFrame();
                    break;
                case 3:
                    // Shell可以有子shell，这些子shell是与父shell相关的，如果父shell关闭，那么其子shell也关闭
                    if ( !shell.isDisposed() ) {
                        Shell dialog = new Shell( shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );// 打开一个模态窗口
                        dialog.setText( "Dialog" );
                        dialog.setLayout( new FillLayout() );
                        Label dialogLabel = new Label( dialog, SWT.NONE | SWT.CENTER );
                        dialogLabel.setText( "A dialog shell" );
                        dialog.pack();
                        dialog.open();
                    }
                    break;
            }
        }

        @Override
        public void widgetDefaultSelected( SelectionEvent e ) {
            widgetSelected( e );
        }

    }

    /**
     * 打开一个子窗口
     */
    private void openSubFrame() {
        // Shell是一种可能没有父复合控件的复合控件，此外，它有一个Display做为父控件，通过是默认的。
        // Shell常见的样式是SWT.SHELL_TRIM或SWT.DIALOG_TRIM。shell可以是模态或非模态的。模态通
        // 常用于对话框。
        Shell frame = new Shell( SWT.SHELL_TRIM );

        // 设置窗口布局
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        frame.setLayout( layout );
        frame.setText( "Shell Example" );

        // 设置表单项布局
        Group input = new Group( frame, SWT.SHADOW_IN );
        GridLayout inputLayout = new GridLayout( 2, true );
        inputLayout.marginHeight = 10;
        inputLayout.marginWidth = 10;
        input.setLayout( inputLayout );
        input.setText( "Input Values" );

        // Input name
        Label nameLabel = new Label( input, SWT.NONE | SWT.LEFT );
        nameLabel.setText( "Name:" );
        Text name = new Text( input, SWT.SINGLE | SWT.BORDER );
        name.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
        name.setToolTipText( "Input Name" );

        // Input address
        Label addrLabel = new Label( input, SWT.NONE | SWT.LEFT );
        addrLabel.setText( "Address:" );
        Text addr = new Text( input, SWT.SINGLE | SWT.BORDER );
        addr.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
        addr.setToolTipText( "Input Address" );

        // Input phone number
        Label phoneLabel = new Label( input, SWT.NONE | SWT.LEFT );
        phoneLabel.setText( "Phone Number:" );
        Text phone = new Text( input, SWT.SINGLE | SWT.BORDER );
        phone.setToolTipText( "Input Phone Number" );
        phone.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );

        // OK/Clear Button
        Composite buttons = new Composite( frame, SWT.NONE );
        RowLayout dialogLayout = new RowLayout( SWT.HORIZONTAL );
        dialogLayout.marginTop = 10;
        buttons.setLayout( dialogLayout );

        Button ok = new Button( buttons, SWT.PUSH );
        ok.setText( "&Ok" );
        ok.setLayoutData( new RowData( 100, 25 ) );
        Button cls = new Button( buttons, SWT.PUSH );
        cls.setText( "&Clear" );
        cls.setLayoutData( new RowData( 100, 25 ) );
        cls.addSelectionListener( new ButtonSelectionListener( frame, 3 ) );

        // 打开窗口
        frame.pack();
        frame.open();
    }

    /**
     * Shell窗口事件监听<br>
     * - 关闭窗口时弹出提示
     * 
     * @author yang.dongdong
     *
     */
    class MyShellAdapter extends ShellAdapter {

        private Shell shell;

        public MyShellAdapter( Shell shell ) {
            super();
            this.shell = shell;
        }

        @Override
        public void shellClosed( ShellEvent e ) {
            MessageBox m = new MessageBox( shell, SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL );
            m.setText( "Confirm Exit" );
            m.setMessage( "Are you sure you want to exit?" );
            int rc = m.open();
            e.doit = rc == SWT.OK;
        }

    }

    /**
     * 菜单选中事件监听
     * 
     * @author yang.dongdong
     *
     */
    class MySelectionAdapter extends SelectionAdapter {

        private int type;

        public MySelectionAdapter( int type ) {
            super();
            this.type = type;
        }

        @Override
        public void widgetSelected( SelectionEvent e ) {
            widgetDefaultSelected( e );
        }

        @Override
        public void widgetDefaultSelected( SelectionEvent e ) {
            switch ( this.type ) {
                case 1:
                    System.out.println( "Do Exit..." );
                    break;
                case 2:
                    System.out.println( "Do About..." );
                    break;
                case 3:
                    System.out.println( "Do Nothing..." );
                    break;
            }
        }
    }

    /**
     * 树结点
     * 
     * @author yang.dongdong
     *
     */
    class TreeNode {

        protected java.util.List < TreeNode > children;

        public java.util.List < TreeNode > getChildren() {
            return children;
        }

        public void setChildren( java.util.List < TreeNode > children ) {
            this.children = children;
        }

        public void addChild( TreeNode node ) {
            children.add( node );
        }

        protected String name;

        public String getName() {
            return name;
        }

        public void setName( String name ) {
            this.name = name;
        }

        public TreeNode( String name ) {
            this( name, new ArrayList < TreeNode >() );
        }

        public TreeNode( String name, java.util.List < TreeNode > children ) {
            setName( name );
            setChildren( children );
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * 绘画监听类
     * 
     * @author yang.dongdong
     *
     */
    class MyPaintListener implements PaintListener {

        private java.util.List < PaintItem > gcObjects;

        public MyPaintListener( java.util.List < PaintItem > gcObjects ) {
            this.gcObjects = gcObjects;
        }

        @Override
        public void paintControl( PaintEvent e ) {
            GC gc = e.gc;
            Canvas canvas = (Canvas) e.getSource();
            gc.setBackground( canvas.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
            Point cext = canvas.getSize();
            gc.fillRectangle( 0, 0, cext.x, cext.y );
            for ( Iterator < PaintItem > it = gcObjects.iterator(); it.hasNext(); ) {
                PaintItem paintItem = it.next();
                paintItem.paint( gc );
            }
        }

    }

    /**
     * Canvas选项卡上按钮的监听类
     * 
     * @author yang.dongdong
     *
     */
    class CanvasButtonListener implements SelectionListener {

        private Canvas                       canvas;
        private java.util.List < PaintItem > gcObjects;
        private int                          type;
        private Random                       random   = new Random();
        private int[]                        colorIds = { SWT.COLOR_WHITE, SWT.COLOR_BLUE, SWT.COLOR_CYAN,
                                                              SWT.COLOR_GRAY, SWT.COLOR_GREEN, SWT.COLOR_MAGENTA,
                                                              SWT.COLOR_RED, SWT.COLOR_YELLOW, SWT.COLOR_BLACK };

        public CanvasButtonListener( Canvas canvas, java.util.List < PaintItem > gcObjects, int type ) {
            this.canvas = canvas;
            this.gcObjects = gcObjects;
            this.type = type;
        }

        @Override
        public void widgetSelected( SelectionEvent e ) {
            widgetDefaultSelected( e );
        }

        @Override
        public void widgetDefaultSelected( SelectionEvent e ) {
            switch ( type ) {
                case 1:
                    gcObjects.clear();
                    Display display = canvas.getDisplay();
                    for ( int i = 0; i < 50; i++ ) {
                        if ( i % 2 == 0 ) {
                            RectangleItem ri = new RectangleItem();
                            ri.extent = new Rectangle( random.nextInt( 200 ), random.nextInt( 100 ),
                                    random.nextInt( 200 ), random.nextInt( 100 ) );
                            ri.color = display.getSystemColor( colorIds[random.nextInt( colorIds.length )] );
                            ri.fill = i % 3 == 0;
                            gcObjects.add( ri );
                        } else {
                            ElipseItem ei = new ElipseItem();
                            ei.extent = new Rectangle( random.nextInt( 200 ), random.nextInt( 100 ),
                                    random.nextInt( 200 ), random.nextInt( 100 ) );
                            ei.color = display.getSystemColor( colorIds[random.nextInt( colorIds.length )] );
                            ei.fill = i % 5 == 0;
                            gcObjects.add( ei );
                        }
                    }
                    canvas.redraw();
                    break;
                case 2:
                    gcObjects.clear();
                    canvas.redraw();
                    break;
            }
        }

    }

    /**
     * 绘画对象
     * 
     * @author yang.dongdong
     *
     */
    class PaintItem {

        public Color     color;
        public boolean   fill;
        public Rectangle extent;

        public void paint( GC gc ) {
            gc.setForeground( color );
            gc.setBackground( color );
        }
    }

    /**
     * 椭圆对象
     * 
     * @author yang.dongdong
     *
     */
    class ElipseItem extends PaintItem {

        public void paint( GC gc ) {
            super.paint( gc );
            if ( fill ) {
                gc.fillOval( extent.x, extent.y, extent.width, extent.height );
            } else {
                gc.drawOval( extent.x, extent.y, extent.width, extent.height );
            }
        }
    }

    /**
     * 矩形对象
     * 
     * @author yang.dongdong
     *
     */
    class RectangleItem extends PaintItem {

        public void paint( GC gc ) {
            super.paint( gc );
            if ( fill ) {
                gc.fillRectangle( extent.x, extent.y, extent.width, extent.height );
            } else {
                gc.drawRectangle( extent.x, extent.y, extent.width, extent.height );
            }
        }
    }

    /**
     * ProgressBar选项卡上的按钮选择事件监听
     * 
     * @author yang.dongdong
     *
     */
    class ProgressSelectionListener implements SelectionListener {

        private int         type;
        private Control     control;
        private Control     another;
        private ProgressBar bar;
        private Label       valueLabel;

        public ProgressSelectionListener( int type, Control control, Control another, ProgressBar bar, Label valueLabel ) {
            this.type = type;
            this.control = control;
            this.another = another;
            this.bar = bar;
            this.valueLabel = valueLabel;
        }

        @Override
        public void widgetSelected( SelectionEvent e ) {
            widgetDefaultSelected( e );
        }

        @Override
        public void widgetDefaultSelected( SelectionEvent e ) {
            switch ( type ) {
                case 1:
                    Slider slider = (Slider) control;
                    int v1 = slider.getSelection();
                    ((Spinner) another).setSelection( v1 );
                    bar.setSelection( v1 );
                    valueLabel.setText( Integer.toString( v1 ) + "%" );
                    break;
                case 2:
                    Spinner spinner = (Spinner) control;
                    int v2 = spinner.getSelection();
                    ((Slider) another).setSelection( v2 );
                    bar.setSelection( v2 );
                    valueLabel.setText( Integer.toString( v2 ) + "%" );
                    break;
            }
        }
    }

    /**
     * Spinner的鼠标滚轮事件监听
     * 
     * @author yang.dongdong
     *
     */
    class SpinnerMouseWheelListener implements MouseWheelListener {

        private Slider      slider;
        private ProgressBar bar;
        private Label       valueLabel;

        public SpinnerMouseWheelListener( Slider slider, ProgressBar bar, Label valueLabel ) {
            super();
            this.slider = slider;
            this.bar = bar;
            this.valueLabel = valueLabel;
        }

        @Override
        public void mouseScrolled( MouseEvent e ) {
            Spinner spinner = (Spinner) e.getSource();
            int value = spinner.getSelection() + (spinner.getIncrement() * e.count);
            if ( value > spinner.getMaximum() ) {
                value = spinner.getMaximum();
            }
            if ( value < spinner.getMinimum() ) {
                value = spinner.getMinimum();
            }
            spinner.setSelection( value );
            slider.setSelection( value );
            bar.setSelection( value );
            valueLabel.setText( Integer.toString( value ) + "%" );
        }
    }

    /**
     * 监听鼠标位置
     * 
     * @author yang.dongdong
     *
     */
    class MousePositionListener implements MouseMoveListener {

        private Text showPoint;

        public MousePositionListener( Text showPoint ) {
            this.showPoint = showPoint;
        }

        @Override
        public void mouseMove( MouseEvent e ) {
            showPoint.setText( e.x + "," + e.y );
        }

    }

    /**
     * 在其他线程中更新进度条
     * 
     * 这里所有方法逻辑都在syncExec中执行，并且对要操作的每个控件都要做可用性检查
     * 
     * @author yang.dongdong
     *
     */
    class BarUpdater extends Thread {

        private int         delay;
        private Display     display;

        private Button      modeButton;
        private ProgressBar bar;
        private Spinner     spinner;
        private Slider      slider;
        private Scale       scale;
        private Label       valueLabel;

        public BarUpdater( Display display, Button modeButton, ProgressBar bar, Spinner spinner, Slider slider,
                Scale scale, Label valueLabel ) {
            this.display = display;
            this.modeButton = modeButton;
            this.bar = bar;
            this.spinner = spinner;
            this.slider = slider;
            this.scale = scale;
            this.valueLabel = valueLabel;
        }

        @Override
        public void run() {
            try {
                while ( true ) {
                    try {
                        if ( !display.isDisposed() ) {
                            display.syncExec( new Runnable() {

                                @Override
                                public void run() {
                                    // 根据自动更新按钮是否选中及滑块选择器的值为确定定时更新的时间间隔
                                    if ( !modeButton.isDisposed() && !scale.isDisposed() ) {
                                        delay = modeButton.getSelection() ? scale.getSelection() : -1;
                                    }
                                }
                            } );
                            if ( delay >= 0 ) {
                                Thread.sleep( delay );
                                if ( !display.isDisposed() ) {
                                    display.syncExec( new Runnable() {

                                        @Override
                                        public void run() {
                                            // 每次更新进度+1
                                            if ( !bar.isDisposed() ) {
                                                int v = bar.getSelection() + 1;
                                                if ( v > bar.getMaximum() ) {
                                                    v = bar.getMinimum();
                                                }
                                                bar.setSelection( v );
                                                if ( !slider.isDisposed() ) {
                                                    slider.setSelection( v );
                                                }
                                                if ( !spinner.isDisposed() ) {
                                                    spinner.setSelection( v );
                                                }
                                                if ( !valueLabel.isDisposed() ) {
                                                    valueLabel.setText( Integer.toString( v ) + "%" );
                                                }
                                            }
                                        }
                                    } );
                                }
                            }
                        }
                        Thread.sleep( 100 );
                    } catch ( InterruptedException e ) {
                    }
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }

    /**
     * StyledText右键PopupList鼠标事件监听
     * 
     * @author yang.dongdong
     *
     */
    class StyledTextMouseListener extends MouseAdapter {

        private Shell      shell;
        private StyledText styledText;

        public StyledTextMouseListener( Shell shell, StyledText styledText ) {
            super();
            this.shell = shell;
            this.styledText = styledText;
        }

        @Override
        public void mouseDown( MouseEvent e ) {
            if ( e.button == 3 ) {
                createPopupList( shell, styledText );
            }
        }
    }

    /**
     * StackLayout上一步下一步按钮事件监听
     * 
     * @author yang.dongdong
     *
     */
    class StackLayoutPreNextListener implements SelectionListener {

        private int         type;
        private StackLayout stackLayout;
        private Composite   clabels;
        private Label[]     labels;

        public StackLayoutPreNextListener( int type, StackLayout stackLayout, Composite clabels, Label[] labels ) {
            this.type = type;
            this.stackLayout = stackLayout;
            this.clabels = clabels;
            this.labels = labels;
        }

        @Override
        public void widgetSelected( SelectionEvent e ) {
            widgetDefaultSelected( e );
        }

        @Override
        public void widgetDefaultSelected( SelectionEvent e ) {
            switch ( type ) {
                case 1:
                    Label current1 = (Label) stackLayout.topControl;
                    int index1 = Arrays.asList( labels ).indexOf( current1 );
                    int preIndex = (5 + index1 - 1) % 5;
                    stackLayout.topControl = labels[preIndex];
                    clabels.layout();
                    break;
                case 2:
                    Label current2 = (Label) stackLayout.topControl;
                    int index2 = Arrays.asList( labels ).indexOf( current2 );
                    int nextIndex = (index2 + 1) % 5;
                    stackLayout.topControl = labels[nextIndex];
                    clabels.layout();
                    break;
            }
        }

    }

    /**
     * 文件选择、目录选择、颜色选择、字体选择按钮事件监听
     * 
     * @author yang.dongdong
     *
     */
    class DialogSelectionListener implements SelectionListener {

        private Shell shell;
        private Text  value;
        private int   type;

        public DialogSelectionListener( Shell shell, Text value, int type ) {
            this.shell = shell;
            this.value = value;
            this.type = type;
        }

        @Override
        public void widgetSelected( SelectionEvent e ) {
            widgetDefaultSelected( e );
        }

        @Override
        public void widgetDefaultSelected( SelectionEvent e ) {
            switch ( type ) {
                case 1:
                    FileDialog file = new FileDialog( shell, SWT.OPEN );
                    file.setFilterExtensions( new String[] { "*.jpg", "*.png", "*.*" } );
                    file.setFilterPath( System.getProperty( "user.home" ) );
                    file.setText( "Sample File Dialog" );
                    String filePath = file.open();
                    value.setText( filePath == null ? "<Not Select>" : filePath );
                    break;
                case 2:
                    DirectoryDialog dir = new DirectoryDialog( shell, SWT.NONE );
                    dir.setText( "Sample Directory Dialog" );
                    String dirPath = dir.open();
                    value.setText( dirPath == null ? "<Not Select>" : dirPath );
                    break;
                case 3:
                    ColorDialog color = new ColorDialog( shell, SWT.NONE );
                    color.setText( "Sample Color Dialog" );
                    RGB rgb = color.open();
                    value.setText( rgb == null ? "<Not Select>" : rgb + "" );
                    break;
                case 4:
                    FontDialog font = new FontDialog( shell, SWT.NONE );
                    font.setText( "Sample Font Dialog" );
                    FontData fontData = font.open();
                    value.setText( fontData == null ? "<Not Select>" : fontData + "" );
                    break;
            }
        }

    }

    /**
     * 工具栏按钮事件监听：弹出菜单
     * 
     * @author yang.dongdong
     *
     */
    class ToolBarSelectionListener implements SelectionListener {

        private Control parent;

        public ToolBarSelectionListener( Control parent ) {
            this.parent = parent;
        }

        @Override
        public void widgetSelected( SelectionEvent e ) {
            Menu menu = new Menu( parent );
            MenuItem menuItem1 = new MenuItem( menu, SWT.PUSH, 0 );
            menuItem1.setText( "Item 1" );
            menuItem1.setAccelerator( 1 );
            menuItem1.setEnabled( true );
            MenuItem menuItem2 = new MenuItem( menu, SWT.PUSH, 1 );
            menuItem2.setText( "Item 2" );
            menuItem2.setAccelerator( 2 );
            menuItem2.setEnabled( true );
            MenuItem menuItem3 = new MenuItem( menu, SWT.PUSH, 2 );
            menuItem3.setText( "Item 3" );
            menuItem3.setAccelerator( 3 );
            menuItem3.setEnabled( true );
            menu.setVisible( true );
        }

        @Override
        public void widgetDefaultSelected( SelectionEvent e ) {
            widgetSelected( e );
        }
    }

    class OpenTrackerListener extends SelectionAdapter {

        private Composite right;

        public OpenTrackerListener( Composite right ) {
            this.right = right;
        }

        @Override
        public void widgetSelected( SelectionEvent e ) {
            Tracker tracker = new Tracker( right, SWT.RESIZE );
            // tracker.addControlListener( ... );
            // tracker.addKeyListener( ... );
            Rectangle rectangle = ((ToolItem) e.widget).getParent().getBounds();
            tracker.setRectangles( new Rectangle[] { rectangle } );
            tracker.setStippled( true );
            tracker.open();
        }
    }
}
