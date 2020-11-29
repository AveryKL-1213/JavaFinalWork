package JavaFinalWork.src;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

// 帮助页面
public class HelpWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private DrawMainWindow drawanyway = null;

    HelpWindow(DrawMainWindow dp) {
        drawanyway = dp;
    }

    public void HelpDocDialog() {
        JOptionPane.showMessageDialog(this, "DrawAnyway-Help Doc\nVisit URL : https://blog.averykl.art", "DrawAnyway",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void AboutDialog() {
        JOptionPane.showMessageDialog(drawanyway, "DrawAnyway\n" + "Version: 1.1\n" + "Authors:    " + "程睿 \n"
                + "                  王有为\n" + "                  董修良", "DrawAnyway", JOptionPane.INFORMATION_MESSAGE);
    }
}
