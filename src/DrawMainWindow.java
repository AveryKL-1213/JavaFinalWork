package JavaFinalWork.src;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.swing.*;

// 主界面类
public class DrawMainWindow extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private String logonames[] = { "newfile", "openfile", "savefile", "pen", "line", "rect", "frect", "oval", "foval",
            "circle", "fcircle", "roundrect", "froundrect", "brush", "txt", "stroke", "eraser", "delete", "move",
            "fill", "clear", "color" };// 图标文件名
    private String tooltips[] = { "New Canvas", "Open Canvas", "Save Canvas", "Pencil", "Line", "Rectangle",
            "Filled Rectangle", "Ellipse", "Filled Ellipse", "Circle", "Filled Circle", "Round Rectangle",
            "Filled Round Rectangle", "Brush", "Text", "Stroke Weight", "Eraser", "Delete Graph", "Move Graph",
            "Fill Graph", "Clear Canvas", "Set Color" };// 工具功能提示
    private Icon icons[];// 存储图标

    private Canvas canvas; // 画布
    private HelpWindow helpWindow; // 帮助
    private FileManage fileManage; // 文件

    private JMenuBar menuBar; // 菜单栏
    private JMenu file, edit, help; // 菜单
    private JMenuItem newFile, openFile, saveFile, exit, editGraph, resetColor, editStroke, editTxt, helpInfo, helpDoc,
            chooseColor, setStroke; // 菜单项
    private JToolBar toolPanel; // 工具栏
    JButton toolButtons[]; // 工具栏按钮
    private JLabel statusLabel; // 状态栏
    private boolean flag = false; // 擦除工具标记
    String[] fontName; // 字体
    private JButton bold, italic, plain; // 字体设置
    private JComboBox<String> fontStyles; // 下拉框

    // 主界面
    DrawMainWindow(String string) {
        // 设置窗口标题、图标
        super(string);
        Image icon = Toolkit.getDefaultToolkit().getImage("../images/logo.png");
        this.setIconImage(icon);
        // 菜单初始化
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(help);
        setJMenuBar(menuBar); // 显示菜单栏
        // 快捷键
        file.setMnemonic('F'); // ALT+F
        edit.setMnemonic('E'); // ALT+E
        help.setMnemonic('H'); // ALT+H

        // File菜单
        newFile = new JMenuItem("New File");
        openFile = new JMenuItem("Open File");
        saveFile = new JMenuItem("Save");
        exit = new JMenuItem("Exit");
        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);
        file.add(exit);
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        exit.addActionListener(this);

        // Edit菜单
        editGraph = new JMenuItem("Edit Graph");
        resetColor = new JMenuItem("Change Color");
        editStroke = new JMenuItem("Change Stroke Size");
        editTxt = new JMenuItem("Edit Text");
        chooseColor = new JMenuItem("Choose Color");
        setStroke = new JMenuItem("Set Stroke Size");
        edit.add(editGraph);
        edit.add(resetColor);
        edit.add(editStroke);
        edit.add(editTxt);
        edit.addSeparator(); // 分割线
        edit.add(chooseColor);
        edit.add(setStroke);
        editGraph.addActionListener(this);
        resetColor.addActionListener(this);
        editStroke.addActionListener(this);
        editTxt.addActionListener(this);
        chooseColor.addActionListener(this);
        setStroke.addActionListener(this);
        chooseColor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        setStroke.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));

        // Help菜单
        helpDoc = new JMenuItem("Help");
        helpInfo = new JMenuItem("About DrawAnyway");
        help.add(helpDoc);
        help.addSeparator(); // 分割线
        help.add(helpInfo);
        helpInfo.addActionListener(this);
        helpDoc.addActionListener(this);

        // 工具栏
        toolPanel = new JToolBar(JToolBar.VERTICAL);
        toolPanel.setLayout(new GridLayout(15, 2, 0, 0));
        toolPanel.setFloatable(false); // 不可浮动
        icons = new ImageIcon[logonames.length];
        toolButtons = new JButton[logonames.length];
        for (int i = 0; i < logonames.length; i++) {
            icons[i] = new ImageIcon("../images/" + logonames[i] + ".png");// 加载图片
            toolButtons[i] = new JButton("", icons[i]);// 创建工具栏中的按钮
            toolButtons[i].setToolTipText(tooltips[i]);// 设置按钮对应提示
            toolPanel.add(toolButtons[i]);
            toolButtons[i].setBackground(Color.white);
            if (i < 3)
                toolButtons[i].addActionListener(this);
            else if (i < logonames.length)
                toolButtons[i].addActionListener(this);
        }
        toolButtons[21].setIcon(new ImageIcon("../images/color-white.png"));
        toolButtons[21].setBackground(Color.BLACK);
        // 字体设置
        bold = new JButton("B");
        bold.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        bold.addActionListener(this);
        italic = new JButton("I");
        italic.setFont(new Font(Font.DIALOG, Font.ITALIC, 15));
        italic.addActionListener(this);
        plain = new JButton("A");
        plain.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        plain.addActionListener(this);
        GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        fontName = g.getAvailableFontFamilyNames(); // 获取字体列表
        fontStyles = new JComboBox<String>(fontName);
        fontStyles.addActionListener(this);
        fontStyles.setMaximumSize(new Dimension(80, 50));// 下拉最大尺
        JToolBar fontPanel = new JToolBar("FontStyle", JToolBar.HORIZONTAL);
        fontPanel.setLayout(new GridLayout(1, 4, 0, 0));
        fontPanel.setFloatable(false);// 不可浮动
        fontPanel.add(bold);
        fontPanel.add(italic);
        fontPanel.add(plain);
        fontPanel.add(fontStyles);

        JPanel strokePanel = new JPanel();
        JSlider slider = new JSlider(0, 50, (int) Canvas.getStrokeWeight());
        // 设置主刻度间隔
        slider.setMajorTickSpacing(5);
        // 设置次刻度间隔
        slider.setMinorTickSpacing(1);
        // 绘制 刻度 和 标签
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        strokePanel.add(slider);

        // 状态栏的初始化
        statusLabel = new JLabel("DrawAnyway");
        toolButtons[21].addActionListener(e -> canvas.chooseColor());

        // 绘画区的初始化
        canvas = new Canvas(this);
        helpWindow = new HelpWindow(this);
        fileManage = new FileManage(this, canvas);

        Container mainBoard = getContentPane();// 得到内容面板
        Toolkit tempTool = getToolkit();
        Dimension screenSize = tempTool.getScreenSize();// 获得屏幕大小
        mainBoard.setLayout(null);
        toolPanel.setBounds(0, 0, 130, 1000);// 给各工具栏安排位置
        statusLabel.setBounds(screenSize.width - 100, screenSize.height - 150, 300, 150);
        canvas.setBounds(130, 0, screenSize.width - 175, screenSize.height - 200);
        fontPanel.setBounds(130, screenSize.height - 180, 300, 30);
        strokePanel.setBounds(130, screenSize.height - 140, 300, 30);
        mainBoard.add(toolPanel);
        mainBoard.add(canvas);
        mainBoard.add(statusLabel);
        mainBoard.add(fontPanel);
        mainBoard.add(strokePanel);
        mainBoard.add(canvas.textArea);
        setBounds(0, 0, screenSize.width, screenSize.height);
        setVisible(true);
        setResizable(false); // 设置为不可更改大小
        validate();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                canvas.setStroke(slider.getValue());
            }
        });
    }

    // 设置状态栏显示的字符
    void setStatusBarText(String s) {
        statusLabel.setText(s);
    }

    // 按钮事件
    public void actionPerformed(ActionEvent e) {
        if (flag) {
            canvas.setRGB(0, 0, 0);
            canvas.setStroke(1);
            flag = false;
        }
        // 根据当前画笔颜色设置Color的背景色
        if (canvas.getPenColor() != Color.BLACK) {
            toolButtons[21].setIcon(new ImageIcon("../images/color.png"));
            toolButtons[21].setBackground(canvas.getPenColor());
        } else { // 如果为黑色，更改白色的图标，方便查看
            toolButtons[21].setIcon(new ImageIcon("../images/color-white.png"));
            toolButtons[21].setBackground(canvas.getPenColor());
        }
        // 绘图
        for (int i = 3; i <= 14; i++) {
            if (e.getSource() == toolButtons[i]) {
                if (i == 13)
                    flag = true;
                canvas.setToolStatus(i);
                canvas.drawItem();
                canvas.repaint();
            }
        }
        if (e.getSource() == toolButtons[15] || e.getSource() == setStroke) { // 画笔粗细
            canvas.setStroke();
        } else if (e.getSource() == toolButtons[16]) { // 橡皮擦
            flag = true;
            canvas.setToolStatus(22);
            canvas.drawItem();
            canvas.repaint();
        } else if (e.getSource() == toolButtons[17]) { // 删除图形
            canvas.setToolStatus(15);
        } else if (e.getSource() == toolButtons[18]) { // 拖动图形
            canvas.setToolStatus(16);
        } else if (e.getSource() == editGraph) { // 改变图形大小
            canvas.setToolStatus(17);
        } else if (e.getSource() == resetColor) { // 改变图形颜色
            canvas.setToolStatus(18);
        } else if (e.getSource() == editStroke) { // 改变图形线型
            canvas.setToolStatus(19);
        } else if (e.getSource() == toolButtons[19]) { // 填充图片
            canvas.setToolStatus(20);
        } else if (e.getSource() == editTxt) { // 编辑文字
            canvas.setToolStatus(21);
        } else if (e.getSource() == toolButtons[20]) { // 清空
            fileManage.NewFile();
        } else if (e.getSource() == newFile || e.getSource() == toolButtons[0]) {
            fileManage.SaveFile(); // 保存
            fileManage.NewFile(); // 新建
        } else if (e.getSource() == openFile || e.getSource() == toolButtons[1]) {
            fileManage.OpenFile(); // 打开
        } else if (e.getSource() == saveFile || e.getSource() == toolButtons[2]) {
            fileManage.SaveFile(); // 保存
        } else if (e.getSource() == exit) { // 退出
            System.exit(0);
        } else if (e.getSource() == chooseColor) { // 选择颜色
            canvas.chooseColor();
        } else if (e.getSource() == helpInfo) { // 关于
            helpWindow.AboutDialog();
        } else if (e.getSource() == helpDoc) { // 帮助文档
            helpWindow.HelpDocDialog();
        }
        if (e.getSource() == bold) {
            canvas.setFont(1, Font.BOLD);
            canvas.drawItem();
        }
        if (e.getSource() == italic) {
            canvas.setFont(2, Font.ITALIC);
            canvas.drawItem();
        }
        if (e.getSource() == plain) {
            canvas.setFont(3, Font.PLAIN);
            canvas.drawItem();
        }
        if (e.getSource() == fontStyles) {
            canvas.style = fontName[fontStyles.getSelectedIndex()];
        }
    }
}
