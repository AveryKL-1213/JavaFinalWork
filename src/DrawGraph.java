package JavaFinalWork.src;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.Serializable;

// 绘图父类
public class DrawGraph implements Serializable {

    private static final long serialVersionUID = 1L;

    int toolFlag; // 字体
    String txtContant; // 文本框内容
    String fontStyle; // 字体加粗/斜体
    int x1, x2, y1, y2; // 坐标
    int R, G, B; // 颜色RGB
    float stroke; // 线条粗细
    int Graphtype; // 图形类型

    // 返回图形类型
    int getGraph() {
        return Graphtype;
    }

    // 绘图函数
    void Draw(Graphics2D Graph2d) {
    }

    // 判断鼠标是否在图形内
    boolean isInGraph(int x, int y) {
        return false;
    }
}

// 铅笔
class Pencil extends DrawGraph {

    private static final long serialVersionUID = 1L;

    int getGraph() {
        Graphtype = 3;
        return Graphtype;
    }

    void Draw(Graphics2D Graph2d) {
        Graph2d.setPaint(new Color(R, G, B)); // 设置颜色
        Graph2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL)); // 设置画笔
        Graph2d.drawLine(x1, y1, x2, y2); // 绘制，铅笔通过短直线实现
    }
    // 不重写覆盖isInGraph，不可被选中，主要是太麻烦了
}

// 文本框
class Txt extends DrawGraph {

    private static final long serialVersionUID = 1L;

    int getGraph() {
        Graphtype = 13;
        return Graphtype;
    }

    void Draw(Graphics2D Graph2d) {
        Graph2d.setPaint(new Color(R, G, B));
        Graph2d.setFont(new Font(fontStyle, toolFlag, ((int) stroke) * 25)); // 设置字体
        if (txtContant != null) {
            Graph2d.drawString(txtContant, x1, y1 + (int) stroke * 25);
        }
    }

    boolean isInGraph(int x, int y) {
        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
            return true;
        } else {
            return false;
        }
    }
}

// 直线
class Line extends DrawGraph {

    private static final long serialVersionUID = 1L;

    int getGraph() {
        Graphtype = 4;
        return Graphtype;
    }

    void Draw(Graphics2D Graph2d) {
        Graph2d.setPaint(new Color(R, G, B));
        Graph2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        Graph2d.drawLine(x1, y1, x2, y2);
    }

    boolean isInGraph(int x, int y) {
        // 判断当前点是否靠近直线
        if (Math.abs(x2 - x1) <= 5) {
            if (((x >= (x1 - 5)) && (x <= (x1 + 5))) && (y >= Math.min(y1, y2) && y <= Math.max(y1, y2))) {
                return true;
            }
        }
        if (Math.abs(y2 - y1) <= 5) {
            if (((y >= (y1 - 5)) && (y <= (y1 + 5))) && (x >= Math.min(x1, x2) && x <= Math.max(x1, x2))) {
                return true;
            }
        }
        if (Math.abs((x2 - x1) * (y - y1) - (y2 - y1) * (x - x1)) < Math.abs(5 * (x2 - x1)) && ((x >= Math.min(x1, x2))
                && (x <= Math.max(x1, x2)) && ((y >= Math.min(y1, y2)) && (y <= Math.max(y1, y2))))) {
            return true;
        }
        return false;
    }
}

// 矩形
class Rectangle extends DrawGraph {

    private static final long serialVersionUID = 1L;

    int getGraph() {
        Graphtype = 5;
        return Graphtype;
    }

    void Draw(Graphics2D Graph2d) {
        Graph2d.setPaint(new Color(R, G, B));
        Graph2d.setStroke(new BasicStroke(stroke));
        Graph2d.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    boolean isInGraph(int x, int y) {
        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
            return true;
        } else {
            return false;
        }
    }
}

// 实心矩形类
class filledRectangle extends DrawGraph {

    private static final long serialVersionUID = 1L;

    int getGraph() {
        Graphtype = 6;
        return Graphtype;
    }

    void Draw(Graphics2D Graph2d) {
        Graph2d.setPaint(new Color(R, G, B));
        Graph2d.setStroke(new BasicStroke(stroke));
        Graph2d.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    boolean isInGraph(int x, int y) {
        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
            return true;
        } else {
            return false;
        }
    }
}

// 椭圆类
class Ellipse extends DrawGraph {

    private static final long serialVersionUID = 1L;

    int getGraph() {
        Graphtype = 7;
        return Graphtype;
    }

    void Draw(Graphics2D Graph2d) {
        Graph2d.setPaint(new Color(R, G, B));
        Graph2d.setStroke(new BasicStroke(stroke));
        Graph2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    // 判断圆是否被选中
    boolean isInGraph(int x, int y) {
        double x0 = ((double) (x2 + x1) / 2);
        double y0 = ((double) (y2 + y1) / 2);
        double xi = Math.pow((x2 - x1), 2);
        double yi = Math.pow((y2 - y1), 2);
        // 由drawOval函数及椭圆数学方程推导
        if (4 * Math.pow((x - x0), 2) * yi + 4 * Math.pow((y - y0), 2) * xi <= (xi * yi)) {
            return true;
        }
        return false;
    }
}

// 实心椭圆类
class filledEllipse extends DrawGraph {

    private static final long serialVersionUID = 1L;

    int getGraph() {
        Graphtype = 8;
        return Graphtype;
    }

    void Draw(Graphics2D Graph2d) {
        Graph2d.setPaint(new Color(R, G, B));
        Graph2d.setStroke(new BasicStroke(stroke));
        Graph2d.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    boolean isInGraph(int x, int y) {
        double x0 = ((double) (x2 + x1) / 2);
        double y0 = ((double) (y2 + y1) / 2);
        double xi = Math.pow((x2 - x1), 2);
        double yi = Math.pow((y2 - y1), 2);
        if (4 * Math.pow((x - x0), 2) * yi + 4 * Math.pow((y - y0), 2) * xi <= (xi * yi)) {
            return true;
        }
        return false;
    }
}

// 圆形类
class Circle extends DrawGraph {

    private static final long serialVersionUID = 1L;

    int getGraph() {
        Graphtype = 9;
        return Graphtype;
    }

    void Draw(Graphics2D Graph2d) {
        Graph2d.setPaint(new Color(R, G, B));
        Graph2d.setStroke(new BasicStroke(stroke));
        Graph2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)),
                Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)));
    }

    boolean isInGraph(int x, int y) {
        double a = Math.min(x1, x2);
        double b = Math.min(y1, y2);
        double d = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
        double x0 = (a + d / 2);
        double y0 = (b + d / 2);
        if ((Math.pow(x - x0, 2) + Math.pow(y - y0, 2)) <= Math.pow(d / 2, 2)) {
            return true;
        }
        return false;
    }
}

// 实心圆类
class filledCircle extends DrawGraph {

    private static final long serialVersionUID = 1L;

    int getGraph() {
        Graphtype = 10;
        return Graphtype;
    }

    void Draw(Graphics2D Graph2d) {
        Graph2d.setPaint(new Color(R, G, B));
        Graph2d.setStroke(new BasicStroke(stroke));
        Graph2d.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)),
                Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)));
    }

    boolean isInGraph(int x, int y) {
        double a = Math.min(x1, x2);
        double b = Math.min(y1, y2);
        double d = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
        double x0 = (a + d / 2);
        double y0 = (b + d / 2);
        if ((Math.pow(x - x0, 2) + Math.pow(y - y0, 2)) <= Math.pow(d / 2, 2)) {
            return true;
        }
        return false;
    }
}

// 圆角矩形类
class RoundRectangle extends DrawGraph {

    private static final long serialVersionUID = 1L;

    int getGraph() {
        Graphtype = 11;
        return Graphtype;
    }

    void Draw(Graphics2D Graph2d) {
        Graph2d.setPaint(new Color(R, G, B));
        Graph2d.setStroke(new BasicStroke(stroke));
        Graph2d.drawRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2), 50, 35);
    }

    boolean isInGraph(int x, int y) {
        // 当作近似矩形处理
        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
            return true;
        } else {
            return false;
        }
    }
}

// 实心圆角矩形类
class filledRoundRectangle extends DrawGraph {

    private static final long serialVersionUID = 1L;

    int getGraph() {
        Graphtype = 12;
        return Graphtype;
    }

    void Draw(Graphics2D Graph2d) {
        Graph2d.setPaint(new Color(R, G, B));
        Graph2d.setStroke(new BasicStroke(stroke));
        Graph2d.fillRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2), 50, 35);
    }

    boolean isInGraph(int x, int y) {
        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
            return true;
        } else {
            return false;
        }
    }
}