package JavaFinalWork.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JColorChooser;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 画布
public class Canvas extends JPanel {

    private static final long serialVersionUID = 1L;

    DrawMainWindow drawanyway = null;
    DrawGraph[] canvasList = new DrawGraph[3000]; // 储存画布

    int selectPID = 0; // 选中图形下标
    int x0, y0; // 记录移动图形鼠标起始位置
    private int toolStatus = 3; // 设置默认基本图形状态为随笔画
    int index = 0; // 当前已经绘制的图形数目
    private Color color = Color.black; // 当前画笔颜色
    int R, G, B; // 颜色RGB值
    int fontBold, fontItalic; // 当前字体加粗/斜体
    String style; // 当前字体
    static float penStroke = 1.0f; // 画笔粗细
    JTextArea textArea = new JTextArea("");
    int textX, textY;

    Canvas(DrawMainWindow mainWindow) {
        drawanyway = mainWindow;
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)); // 设置鼠标为十字
        setBackground(Color.white); // 设置画布背景为白色
        // 添加鼠标事件
        addMouseListener(new MouseA());
        addMouseMotionListener(new MouseB());
        drawItem();
    }

    // 返回当前画笔颜色
    public Color getPenColor() {
        return color;
    }

    public void paintComponent(Graphics G) {
        super.paintComponent(G);
        Graphics2D Graph2d = (Graphics2D) G;
        int j = 0;
        while (j <= index) {
            setPen(Graph2d, canvasList[j]);
            j++;
        }
    }

    void setPen(Graphics2D Graph2d, DrawGraph i) {
        i.Draw(Graph2d);
    }

    // 绘制图形的基本单元对象
    void drawItem() {
        // 根据当前功能，设置光标样式
        if (toolStatus == 14)
            setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        else
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        // 根据当前选择的功能，调用对应绘制函数
        switch (toolStatus) {
            case 3: // 铅笔
                canvasList[index] = new Pencil();
                break;
            case 4: // 直线
                canvasList[index] = new Line();
                break;
            case 5: // 矩形
                canvasList[index] = new Rectangle();
                break;
            case 6: // 实心矩形
                canvasList[index] = new filledRectangle();
                break;
            case 7: // 椭圆
                canvasList[index] = new Ellipse();
                break;
            case 8: // 实心椭圆
                canvasList[index] = new filledEllipse();
                break;
            case 9: // 圆
                canvasList[index] = new Circle();
                break;
            case 10: // 实心圆
                canvasList[index] = new filledCircle();
                break;
            case 11: // 圆角矩形
                canvasList[index] = new RoundRectangle();
                break;
            case 12: // 实心圆角矩形
                canvasList[index] = new filledRoundRectangle();
                break;
            case 13: // 画刷
                penStroke = (float) 50;
                canvasList[index] = new Pencil();
                break;
            case 14: // 文字输入
                canvasList[index] = new Word();
                break;
            case 22: // 橡皮擦
                R = 255;
                G = 255;
                B = 255;
                penStroke = (float) 50;
                canvasList[index] = new Pencil();
                break;
        }
        // 存储工具以及画笔属性信息到canvasList
        if (toolStatus >= 3 && toolStatus <= 14 || toolStatus == 22) {
            canvasList[index].toolFlag = toolStatus;
            canvasList[index].R = R;
            canvasList[index].G = G;
            canvasList[index].B = B;
            canvasList[index].stroke = penStroke;
        }
    }

    // 设置index
    public void setIndex(int x) {
        index = x;
    }

    // 返回index
    public int getIndex() {
        return index;
    }

    // 设置颜色
    public void setColor(Color color) {
        this.color = color;
    }

    // 选择颜色
    public void chooseColor() {
        color = JColorChooser.showDialog(drawanyway, "Choose Color", color);
        try {
            R = color.getRed();
            G = color.getGreen();
            B = color.getBlue();
        } catch (Exception e) {
            R = 0;
            G = 0;
            B = 0;
        }
        canvasList[index].R = R;
        canvasList[index].G = G;
        canvasList[index].B = B;
    }

    // 直接设置颜色RGB
    public void setRGB(int cR, int cG, int cB) {
        R = cR;
        G = cG;
        B = cB;
        canvasList[index].R = R;
        canvasList[index].G = G;
        canvasList[index].B = B;
    }

    // 改变所选择的图形的颜色
    public void changeColor() {
        color = JColorChooser.showDialog(drawanyway, "Choose Color", color);
        try {
            R = color.getRed();
            G = color.getGreen();
            B = color.getBlue();
        } catch (Exception e) {
            R = 0;
            G = 0;
            B = 0;
        }
        canvasList[selectPID].R = R;
        canvasList[selectPID].G = G;
        canvasList[selectPID].B = B;
    }

    // 设置画笔粗细
    public void setStroke() {
        new StrokeSlider();
        penStroke = StrokeSlider.getWeight();
        canvasList[index].stroke = penStroke;
    }

    public void loadStroke() {
        canvasList[index].stroke = penStroke;
    }

    public static void setStroke(float f) {
        penStroke = f;
    }

    public static float getStrokeWeight() {
        return penStroke;
    }

    public void changeDrawStroke() {
        String input;
        input = JOptionPane.showInputDialog("Input Stroke Weight(a float number)");
        try {
            penStroke = Float.parseFloat(input);
        } catch (Exception e) {
            penStroke = 1.0f;
        }
        canvasList[selectPID].stroke = penStroke;
    }

    // 设置当前工具
    public void setToolStatus(int i) {
        toolStatus = i;
    }

    // 修改文字
    public void changeText() {
        String input;
        input = JOptionPane.showInputDialog("Input words to change to");
        // 重构文本框
        canvasList[selectPID].txtContant = input;
        canvasList[selectPID].toolFlag = fontBold + fontItalic;
        canvasList[selectPID].fontStyle = style;
        canvasList[selectPID].stroke = penStroke;
        canvasList[selectPID].R = R;
        canvasList[selectPID].G = G;
        canvasList[selectPID].B = B;
    }

    // 设置字体
    public void setFont(int i, int font) {
        if (i == 1) {
            fontBold = font;
        } else
            fontItalic = font;
    }

    // 填充图形
    public void fillGraph(DrawGraph cur) {
        int curType = cur.getGraph();// 判断图形类型
        if (curType == 5) {
            canvasList[selectPID] = new filledRectangle();
        } else if (curType == 7) {
            canvasList[selectPID] = new filledEllipse();
        } else if (curType == 9) {
            canvasList[selectPID] = new filledCircle();
        } else if (curType == 11) {
            canvasList[selectPID] = new filledRoundRectangle();
        }
        canvasList[selectPID].x1 = cur.x1;
        canvasList[selectPID].x2 = cur.x2;
        canvasList[selectPID].y1 = cur.y1;
        canvasList[selectPID].y2 = cur.y2;
        canvasList[selectPID].R = R;
        canvasList[selectPID].G = G;
        canvasList[selectPID].B = B;
    }

    // 删除所选图形
    public void deletePaint(DrawGraph cur) {
        int curType = cur.getGraph();
        if (curType >= 3 && curType <= 14 || toolStatus == 22) {
            canvasList[selectPID] = new Line();
        }
    }

    // 鼠标事件
    class MouseA extends MouseAdapter {
        // 鼠标进入
        public void mouseEntered(MouseEvent me) {
            drawanyway.setStatusBarText("(" + me.getX() + " ," + me.getY() + ")");
        }

        // 鼠标退出
        public void mouseExited(MouseEvent me) {
            drawanyway.setStatusBarText("(" + me.getX() + " ," + me.getY() + ")");
        }

        // 鼠标按下
        public void mousePressed(MouseEvent me) {
            canvasList[index].stroke = penStroke;
            drawanyway.setStatusBarText("(" + me.getX() + " ," + me.getY() + ")");
            if (toolStatus >= 15 && toolStatus <= 21) {
                for (selectPID = index - 1; selectPID >= 0; selectPID--) {
                    // 从后到前寻找当前鼠标是否在某个图形内部
                    if (canvasList[selectPID].isInGraph(me.getX(), me.getY())) {
                        if (toolStatus == 16)// 移动图形需要记录press时的坐标
                        {
                            x0 = me.getX();
                            y0 = me.getY();
                        }
                        break;// 其它操作只需找到currenti即可
                    }
                }
                if (selectPID >= 0) {// 有图形被选中
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));// 更改鼠标样式为手形
                    if (toolStatus == 20) {// 触发填充
                        fillGraph(canvasList[selectPID]);
                        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));// 鼠标样式变回十字花
                        repaint();
                    } else if (toolStatus == 15) {// 触发删除
                        deletePaint(canvasList[selectPID]);
                        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                        repaint();
                    } else if (toolStatus == 18) {// 改变已有图形的颜色
                        changeColor();
                        repaint();
                    } else if (toolStatus == 19) {// 改变已有图形的线型
                        changeDrawStroke();
                        repaint();
                    } else if (toolStatus == 21) {// 改变已有文字
                        changeText();
                        repaint();
                    }
                }
            } else {
                canvasList[index].x1 = canvasList[index].x2 = me.getX();
                canvasList[index].y1 = canvasList[index].y2 = me.getY();// x1,x2,y1,y2初始化
                // 铅笔
                if (toolStatus == 3 || toolStatus == 13 || toolStatus == 22) {
                    canvasList[index].x1 = canvasList[index].x2 = me.getX();
                    canvasList[index].y1 = canvasList[index].y2 = me.getY();
                    index++;
                    drawItem();// 创建新的图形的基本单元对象
                }
                // 如果选择图形的文字输入，则进行下面的操作
                if (toolStatus == 14) {
                    textX = me.getX();
                    textY = me.getY();
                    textArea.setBounds(textX, textY, 0, 0);
                    textArea.setBorder(new LineBorder(new java.awt.Color(127, 157, 185), 1, false));
                }
            }
        }

        public void mouseReleased(MouseEvent me) {
            // 鼠标松开
            drawanyway.setStatusBarText("(" + me.getX() + " ," + me.getY() + ")");
            if (toolStatus == 16) {// 移动结束
                if (selectPID >= 0) {// 鼠标成功选择了某个图形
                    canvasList[selectPID].x1 = canvasList[selectPID].x1 + me.getX() - x0;
                    canvasList[selectPID].y1 = canvasList[selectPID].y1 + me.getY() - y0;
                    canvasList[selectPID].x2 = canvasList[selectPID].x2 + me.getX() - x0;
                    canvasList[selectPID].y2 = canvasList[selectPID].y2 + me.getY() - y0;
                    repaint();
                    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                }
            } else if (toolStatus == 17) {// 放大缩小结束
                if (selectPID >= 0) {// 鼠标成功选择了某个图形
                    canvasList[selectPID].x2 = me.getX();
                    canvasList[selectPID].y2 = me.getY();
                    repaint();
                    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                }
            } else {
                if (toolStatus == 3 || toolStatus == 13 || toolStatus == 22) {// 随笔画绘制结束
                    canvasList[index].x1 = me.getX();
                    canvasList[index].y1 = me.getY();
                } else if (toolStatus == 14) {// 文本框绘制结束
                    textArea.setBounds(Math.min(textX, me.getX()) + 130, Math.min(textY, me.getY()),
                            Math.abs(textX - me.getX()), Math.abs(textY - me.getY()));// 绘制文本框
                    String input;
                    input = JOptionPane.showInputDialog("Input words");
                    textArea.setText(input);
                    canvasList[index].txtContant = input;
                    canvasList[index].toolFlag = fontBold + fontItalic;// 设置粗体、斜体
                    canvasList[index].x2 = me.getX();
                    canvasList[index].y2 = me.getY();
                    canvasList[index].fontStyle = style;// 设置字体

                    index++;
                    toolStatus = 14;
                    drawItem();// 创建新的图形的基本单元对象
                    repaint();
                    textArea.setText("");// 重设文本框，为下一次使用做准备
                    textArea.setBounds(textX, textY, 0, 0);
                }
                if (toolStatus >= 3 && toolStatus <= 13 || toolStatus == 22) {
                    canvasList[index].x2 = me.getX();
                    canvasList[index].y2 = me.getY();
                    repaint();
                    index++;
                    drawItem();// 创建新的图形的基本单元对象
                }
            }
        }
    }

    // 鼠标事件MouseB继承了MouseMotionAdapter，用来处理鼠标的滚动与拖动
    class MouseB extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent me)// 鼠标的拖动
        {
            drawanyway.setStatusBarText("(" + me.getX() + " ," + me.getY() + ")");
            if (toolStatus == 3 || toolStatus == 13 || toolStatus == 22) {// 任意线的画法
                canvasList[index - 1].x1 = canvasList[index].x2 = canvasList[index].x1 = me.getX();
                canvasList[index - 1].y1 = canvasList[index].y2 = canvasList[index].y1 = me.getY();
                index++;
                drawItem();// 创建新的图形的基本单元对象
                repaint();
            } else if (toolStatus == 17) {
                if (selectPID >= 0) {// 移动的过程
                    canvasList[selectPID].x1 = canvasList[selectPID].x1 + me.getX() - x0;
                    canvasList[selectPID].y1 = canvasList[selectPID].y1 + me.getY() - y0;
                    canvasList[selectPID].x2 = canvasList[selectPID].x2 + me.getX() - x0;
                    canvasList[selectPID].y2 = canvasList[selectPID].y2 + me.getY() - y0;
                    x0 = me.getX();
                    y0 = me.getY();
                    repaint();
                }
            } else if (toolStatus == 18) {// 放大缩小的过程
                if (selectPID >= 0) {
                    canvasList[selectPID].x2 = me.getX();
                    canvasList[selectPID].y2 = me.getY();
                    repaint();
                }
            } else if (toolStatus >= 3 && toolStatus <= 14 || toolStatus == 22) {// 绘制图形的过程
                canvasList[index].x2 = me.getX();
                canvasList[index].y2 = me.getY();
                repaint();
            }
        }

        public void mouseMoved(MouseEvent me)// 鼠标的移动
        {
            drawanyway.setStatusBarText("(" + me.getX() + " ," + me.getY() + ")");
            for (selectPID = index - 1; selectPID >= 0; selectPID--) {
                // 从后到前寻找当前鼠标是否在某个图形内部
                if (canvasList[selectPID].isInGraph(me.getX(), me.getY())) {
                    break;// 其它操作只需找到currenti即可
                }
            }
            if (selectPID >= 0) {// 有图形被选中
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));// 更改鼠标样式为箭头
            } else {
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            }
        }
    }

}

class StrokeSlider extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JFrame jf;
    JButton confirmButton;
    static private int weight;

    public StrokeSlider() {
        jf = new JFrame("Set Stroke Weight");
        jf.setSize(300, 130);
        jf.setLocationRelativeTo(null);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        // 创建一个滑块，最小值、最大值、初始值 分别为 0、20、10
        final JSlider slider = new JSlider(0, 100, (int) Canvas.getStrokeWeight());
        // 设置主刻度间隔
        slider.setMajorTickSpacing(10);
        // 设置次刻度间隔
        slider.setMinorTickSpacing(1);
        // 绘制 刻度 和 标签
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        // 添加刻度改变监听器
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                weight = slider.getValue();
            }
        });

        confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        confirmButton.addActionListener(this);
        panel.add(slider);
        panel.add(confirmButton);
        jf.setContentPane(panel);
        jf.setVisible(true);

    }

    public static int getWeight() {
        return weight;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            Canvas.setStroke(weight);
            jf.dispose();
        }
    }
}