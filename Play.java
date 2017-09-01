package complexity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Play extends javax.swing.JFrame implements ActionListener,KeyListener{
	CreateCanvas canvas = new CreateCanvas();
	public Play() {
		this.setTitle("���ң���ֻ��һ������ĳԻ�");
		addKeyListener(this); // ���������¼�
		add(canvas); // ��������Թ��������ϵ����GUI���������
		setVisible(true); // ���ڿɼ�
		setSize(500,525); // ���ô��ڴ�С
		setLocation(400, 250); // ���ô���λ��
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE); // ���ùرմ����˳�����
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
		switch(e.getKeyCode()){ // ͨ�����̶��������ö���������������Ϸ 
		case KeyEvent.VK_DOWN:canvas.moveDown();break;
		case KeyEvent.VK_LEFT:canvas.moveLeft();break; 
		case KeyEvent.VK_RIGHT:canvas.moveRight();break; 
		case KeyEvent.VK_UP:canvas.moveUp();break; 
		} 
	}
}
 
