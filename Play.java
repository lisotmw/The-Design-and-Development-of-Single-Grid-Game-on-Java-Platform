package complexity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Play extends javax.swing.JFrame implements ActionListener,KeyListener{
	CreateCanvas canvas = new CreateCanvas();
	public Play() {
		this.setTitle("别看我，我只是一个肉肉的吃货");
		addKeyListener(this); // 监听键盘事件
		add(canvas); // 将这个画迷宫对象整合到这个GUI界面程序当中
		setVisible(true); // 窗口可见
		setSize(500,525); // 设置窗口大小
		setLocation(400, 250); // 设置窗口位置
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE); // 设置关闭窗口退出程序
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Play().setVisible(true);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){ // 通过键盘动作，调用动作函数，进行游戏 
		case KeyEvent.VK_DOWN:canvas.moveDown();break;
		case KeyEvent.VK_LEFT:canvas.moveLeft();break; 
		case KeyEvent.VK_RIGHT:canvas.moveRight();break; 
		case KeyEvent.VK_UP:canvas.moveUp();break; 
		} 
	}
}
 
