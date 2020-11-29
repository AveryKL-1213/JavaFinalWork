package JavaFinalWork.src;

import java.awt.Color;
import java.io.*;
import javax.swing.*;

//文件类
public class FileManage {
    private DrawMainWindow drawanyway;
    Canvas canvas = null;

    FileManage(DrawMainWindow mainWindow, Canvas ca) {
        drawanyway = mainWindow;
        canvas = ca;
    }

    // 新建
    public void NewFile() {
        canvas.setIndex(0); // 清空画板
        canvas.setToolStatus(3); // 默认为铅笔
        canvas.setColor(Color.black); // 默认黑色
        canvas.setStroke(1.0f);
        canvas.drawItem();
        canvas.repaint();
    }

    // 打开
    public void OpenFile() {
        JFileChooser filechooser = new JFileChooser();
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = filechooser.showOpenDialog(drawanyway);

        if (returnVal == JFileChooser.CANCEL_OPTION) {// 如果窗口关闭或取消，结束进程
            return;
        }
        File fileName = filechooser.getSelectedFile();// 获得文件路径
        fileName.canRead();
        if (fileName == null || fileName.getName().equals(""))// 文件名不存在
        {
            JOptionPane.showMessageDialog(filechooser, "", "请输入文件名！", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                FileInputStream ifs = new FileInputStream(fileName);
                ObjectInputStream input = new ObjectInputStream(ifs);

                int countNumber = 0;
                DrawGraph inputRecord;
                countNumber = input.readInt();
                for (int i = 0; i < countNumber; i++) {
                    canvas.setIndex(i);
                    inputRecord = (DrawGraph) input.readObject();
                    canvas.canvasList[i] = inputRecord;
                }
                canvas.drawItem();
                input.close();
                canvas.repaint();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(drawanyway, "没有找到源文件！", "没有找到源文件", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(drawanyway, "读文件是发生错误！", "读取错误", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(drawanyway, "不能创建对象！", "已到文件末尾", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    // 保存
    public void SaveFile() {
        JFileChooser filechooser = new JFileChooser();
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // 仅选择文件
        int result = filechooser.showSaveDialog(drawanyway);
        if (result == JFileChooser.CANCEL_OPTION) {
            return;
        }
        File fileName = filechooser.getSelectedFile();// 获得文件路径
        fileName.canWrite();
        if (fileName == null || fileName.getName().equals(""))// 文件名不存在
        {
            JOptionPane.showMessageDialog(filechooser, "文件名", "请输入文件名！", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                fileName.delete();// 删除路径位置的文件
                FileOutputStream fos = new FileOutputStream(fileName + ".cr"); // 流输出
                ObjectOutputStream output = new ObjectOutputStream(fos);
                output.writeInt(canvas.getIndex());
                for (int i = 0; i < canvas.getIndex(); i++) {
                    DrawGraph p = canvas.canvasList[i];
                    output.writeObject(p);
                    output.flush();// 刷新流缓冲
                }
                output.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
