package JavaFinalWork.src;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

// 主界面类
public class DrawMainWindow extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JToolBar toolPanel;// 工具面板
    private JMenuBar bar;// 菜单栏
    private JMenu file, color, stroke, help, edit;// 菜单
    private JMenuItem newfile, openfile, savefile, exit;// file菜单
    private JMenuItem editgraph, editcolor, editstroke, edittext;// edit菜单
    private JMenuItem helpin, helpmain, colorchoice, strokeitem;// help菜单
    // private Icon nf, sf, of;// 文件菜单项的图标对象
    private JLabel startbar;// 状态栏
    private Canvas canvas;// 画布类的定义
    private HelpWindow helpwindow; // 定义一个帮助类对象
    private FileManage filemanage;// 文件对象
    String[] fontName;// 字体名称

    private String names[] = { "newfile", "openfile", "savefile", "pen", "line", "rect", "frect", "oval", "foval",
            "circle", "fcircle", "roundrect", "froundrect", "brush", "txt", "stroke", "eraser", "delete", "move",
            "fill", "clear", "color" };// 图标文件名
    private String tiptext[] = { "新建画布", "打开画布", "保存画布", "铅笔", "直线", "矩形", "填充矩形", "椭圆", "填充椭圆", "圆", "填充圆", "圆角矩形",
            "填充圆角矩形", "画刷", "文字的输入", "线条的粗细", "橡皮擦", "删除图形", "移动图形", "填充图形", "清空", "颜色" };// 工具功能提示提示
    private Icon icons[];// 定义按钮图标数组

    JButton button[];// 定义工具条中的按钮组
    private JCheckBox bold, italic;// 工具条字体的风格（复选框）
    private JComboBox<String> styles;// 工具条中的字体的样式（下拉列表）

    DrawMainWindow(String string) {
        // 主界面构造函数
        super(string);
        // 菜单初始化
        file = new JMenu("文件");
        edit = new JMenu("编辑");
        color = new JMenu("颜色");
        stroke = new JMenu("画笔");
        help = new JMenu("帮助");

        // 菜单栏初始化
        bar = new JMenuBar();
        bar.add(file);// 菜单条添加菜单
        bar.add(edit);
        bar.add(color);
        bar.add(stroke);
        bar.add(help);

        // 显示菜单栏
        setJMenuBar(bar);

        // 快捷键
        file.setMnemonic('F'); // ALT+F
        edit.setMnemonic('E'); // ALT+E
        color.setMnemonic('C'); // ALT+C
        stroke.setMnemonic('S'); // ALT+S
        help.setMnemonic('H'); // ALT+H

        // File
        // nf = new ImageIcon("../images/newfile.png");// 创建图标
        // sf = new ImageIcon("../images/savefile.png");
        // of = new ImageIcon("../images/openfile.png");
        newfile = new JMenuItem("新建");
        openfile = new JMenuItem("打开");
        savefile = new JMenuItem("保存");
        exit = new JMenuItem("退出");
        // File下拉栏
        file.add(newfile);
        file.add(openfile);
        file.add(savefile);
        file.add(exit);
        // File中快捷键
        newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        openfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        savefile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        newfile.addActionListener(this);
        openfile.addActionListener(this);
        savefile.addActionListener(this);
        exit.addActionListener(this);

        // Color
        colorchoice = new JMenuItem("调色板");
        colorchoice.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        colorchoice.addActionListener(this);
        color.add(colorchoice);

        // Help
        helpmain = new JMenuItem("帮助");
        helpin = new JMenuItem("关于DrawAnyway");
        // Help下拉栏
        help.add(helpmain);
        help.addSeparator(); // 分割线
        help.add(helpin);
        helpin.addActionListener(this);
        helpmain.addActionListener(this);

        // Stroke
        strokeitem = new JMenuItem("设置画笔");
        strokeitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        stroke.add(strokeitem);
        strokeitem.addActionListener(this);

        // Edit
        editgraph = new JMenuItem("编辑图形");
        editcolor = new JMenuItem("更改颜色");
        editstroke = new JMenuItem("更改线型");
        edittext = new JMenuItem("编辑文字");
        // Edit下拉栏
        edit.add(editgraph);
        edit.add(editcolor);
        edit.add(editstroke);
        edit.add(edittext);
        editgraph.addActionListener(this);
        editcolor.addActionListener(this);
        editstroke.addActionListener(this);
        edittext.addActionListener(this);

        // 工具栏
        toolPanel = new JToolBar(JToolBar.VERTICAL);
        toolPanel.setLayout(new GridLayout(15, 2, 0, 0));
        toolPanel.setFloatable(false); // 不可浮动
        icons = new ImageIcon[names.length];
        button = new JButton[names.length];
        for (int i = 0; i < names.length; i++) {
            icons[i] = new ImageIcon("../images/" + names[i] + ".png");// 加载图片
            button[i] = new JButton("", icons[i]);// 创建工具条中的按钮
            button[i].setToolTipText(tiptext[i]);// 鼠标移到相应的按钮上给出相应的提示
            toolPanel.add(button[i]);
            button[i].setBackground(Color.white);
            if (i < 3)
                button[i].addActionListener(this);
            else if (i < names.length)
                button[i].addActionListener(this);
        }

        CheckBoxHandler CHandler = new CheckBoxHandler();// 字体样式处理类
        bold = new JCheckBox("B");
        bold.setFont(new Font(Font.DIALOG, Font.BOLD, 15));// 设置字体
        bold.addItemListener(CHandler);
        italic = new JCheckBox("I");
        italic.addItemListener(CHandler);// italic注册监听
        italic.setFont(new Font(Font.DIALOG, Font.ITALIC, 15));// 设置字体
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();// 计算机上字体可用的名称
        fontName = ge.getAvailableFontFamilyNames();
        styles = new JComboBox<String>(fontName);// 下拉列表的初始化
        styles.addItemListener(CHandler);// styles注册监听
        styles.setMaximumSize(new Dimension(200, 50));// 设置下拉列表的最大尺寸
        styles.setMinimumSize(new Dimension(100, 40));
        styles.setFont(new Font(Font.DIALOG, Font.BOLD, 15));// 设置字体

        // 添加字体式样栏
        JToolBar fontpanel = new JToolBar("字体选项", JToolBar.HORIZONTAL);// 水平方向
        fontpanel.setLayout(new GridLayout(1, 3, 0, 0));
        fontpanel.setFloatable(false);// 不可浮动
        fontpanel.add(bold);
        fontpanel.add(italic);
        fontpanel.add(styles);

        // 状态栏的初始化
        startbar = new JLabel("DrawAnyway");
        button[21].addActionListener(e -> canvas.chooseColor());

        // 绘画区的初始化
        canvas = new Canvas(this);
        helpwindow = new HelpWindow(this);
        filemanage = new FileManage(this, canvas);

        Container con = getContentPane();// 得到内容面板
        Toolkit tool = getToolkit();// 得到一个Toolkit类的对象（主要用于得到屏幕的大小）
        Dimension dim = tool.getScreenSize();// 得到屏幕的大小 （返回Dimension对象）
        con.setLayout(null);
        toolPanel.setBounds(0, 0, 130, 1000);// 给各工具栏安排位置
        startbar.setBounds(dim.width - 300, dim.height - 150, 300, 100);
        canvas.setBounds(130, 0, dim.width - 10, dim.height - 200);
        fontpanel.setBounds(130, dim.height - 190, 200, 50);
        con.add(toolPanel);// 将各工具栏添加到主面板内
        con.add(canvas);
        con.add(startbar);
        con.add(fontpanel);
        con.add(canvas.tarea);
        setBounds(0, 0, dim.width, dim.height);
        setVisible(true);
        validate();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    // 设置状态栏显示的字符
    void setStratBar(String s) {
        startbar.setText(s);
    }

    // 事件的处理
    public void actionPerformed(ActionEvent e) {
        for (int i = 3; i <= 14; i++) {// 图形工具
            if (e.getSource() == button[i]) {
                canvas.setChosenStatus(i);
                canvas.createNewitem();
                canvas.repaint();
            }
        }
        if (e.getSource() == button[15] || e.getSource() == strokeitem) { // 画笔粗细的调整
            canvas.setStroke();
        } else if (e.getSource() == button[16]) { // 橡皮擦
            canvas.setChosenStatus(22);
            canvas.createNewitem();
            canvas.repaint();
        } else if (e.getSource() == button[17]) { // 删除一个图形
            canvas.setChosenStatus(15);
        } else if (e.getSource() == button[18]) { // 拖动图形
            canvas.setChosenStatus(16);
        } else if (e.getSource() == editgraph) { // 改变已有图形大小
            canvas.setChosenStatus(17);
        } else if (e.getSource() == editcolor) { // 改变已有图形颜色
            canvas.setChosenStatus(18);
        } else if (e.getSource() == editstroke) { // 改变已有图形线型
            canvas.setChosenStatus(19);
        } else if (e.getSource() == button[19]) { // 填充图片
            canvas.setChosenStatus(20);
        } else if (e.getSource() == edittext) { // 编辑已输入的文字
            canvas.setChosenStatus(21);
        } else if (e.getSource() == button[20]) { // 清空
            filemanage.newFile();
        } else if (e.getSource() == newfile || e.getSource() == button[0]) {
            filemanage.saveFile(); // 保存
            filemanage.newFile(); // 新建
        } else if (e.getSource() == openfile || e.getSource() == button[1]) {
            filemanage.openFile(); // 打开
        } else if (e.getSource() == savefile || e.getSource() == button[2]) {
            filemanage.saveFile(); // 保存
        } else if (e.getSource() == exit) { // 退出
            System.exit(0);
        } else if (e.getSource() == colorchoice) { // 选择颜色
            canvas.chooseColor();
        } else if (e.getSource() == helpin) { // 帮助
            helpwindow.AboutBook();
        } else if (e.getSource() == helpmain) {
            helpwindow.MainHelp();
        }
    }

    // 字体样式处理类（粗体、斜体、字体名称）
    public class CheckBoxHandler implements ItemListener {

        public void itemStateChanged(ItemEvent ie) {
            if (ie.getSource() == bold)// 字体粗体
            {
                if (ie.getStateChange() == ItemEvent.SELECTED)
                    canvas.setFont(1, Font.BOLD);
                else
                    canvas.setFont(1, Font.PLAIN);
            } else if (ie.getSource() == italic)// 字体斜体
            {
                if (ie.getStateChange() == ItemEvent.SELECTED)
                    canvas.setFont(2, Font.ITALIC);
                else
                    canvas.setFont(2, Font.PLAIN);

            } else if (ie.getSource() == styles)// 字体的名称
            {
                canvas.style = fontName[styles.getSelectedIndex()];
            }
        }

    }
}
