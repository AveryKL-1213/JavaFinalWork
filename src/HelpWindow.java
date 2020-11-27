package JavaFinalWork.src;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//帮助菜单功能的事项类
public class HelpWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private DrawMainWindow drawpad = null;

    HelpWindow(DrawMainWindow dp) {
        drawpad = dp;
    }

    public void MainHelp() {
        JOptionPane.showMessageDialog(this, "DrawAnyway-Help Doc", "DrawPad", JOptionPane.WARNING_MESSAGE);
    }

    public void AboutBook() {
        JOptionPane.showMessageDialog(drawpad, "DrawAnyway" + "\n" + "Version: 1.1" + "\n" + "Authors:    " + "\n"
                + "      程睿 " + "\n" + "      王有为" + "\n" + "      董修良", "DrawAnyway", JOptionPane.WARNING_MESSAGE);
    }
}
