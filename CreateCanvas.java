package complexity;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class CreateCanvas extends Canvas{
	static final int unitSize = 20; // С����߳� 
	int gridNum; // �Թ��߳�����С����Ϊ��λ
	int temp;
	int x, y; // ͨ·����
	int i, j;
	boolean win; // ��Ϸ�ٲñ��
	int paintFlag[][] = new int[25][25]; // λ�����Ա�� 
	int startx = 240, starty = 240; // ��è�ĳ�ʼλ��
	int nextxl, nextxr, nextxu, nextxd; // �ĸ�������ܵ���Ծλ��
	int nextyl, nextyr, nextyu, nextyd;
	int tempx, tempy; // �����������м����

	public CreateCanvas() {
		gridNum = 25; 
		paintFlag = newMaze(); // ���캯�����������λ�ñ��
	}

	public int[][] newMaze(){ // ��������Թ�λ��	
		int paintFlag[][] = new int[25][25];
		for(i = 0; i < gridNum; i++) {
			for(j = 0; j < gridNum; j++) {
				paintFlag[i][j] = 0; // ��Ĭ���Թ�ȫ��ǽ
			} 
		}
		x = startx; // Ϊͨ·����ʼλ��
		y = starty; 
		paintFlag[12][12] = 1; // ��ʼλ�ñ��Ϊͨ·
		while (true) { // ѭ���ж�����ҳ�һ��ͨ·
			temp = (int)(Math.random()*4); // �������0��3֮������
			if (temp == 0)	x += unitSize; // �жϼ�¼Ϊͨ·
			if (temp == 1)	y += unitSize;				
			if (temp == 2)	x -= unitSize;
			if (temp == 3)	y -= unitSize;
			paintFlag[x/unitSize][y/unitSize] = 1; // ���ͨ·
			if (x == 0 || y == 0 || x == 480 || y == 480) break; // ���ҵ�ͨ·����ʱֹͣ
		}
		for (i = 1; i < gridNum - 1; i++) // ��ͨ·���⣬Χǽ���ڵ�λ�����������
			for (j = 1; j < gridNum - 1; j++) {				
				if (paintFlag[i][j] == 1) continue; // �������ͨ·������
				else {
					temp = (int)(Math.random()*3); // �������0��3֮���������
					if (temp == 0) { // �Զ���֮һ�ĸ��ʱ��Ϊͨ·	
						paintFlag[i][j] = 1;						
						temp = (int)(Math.random()*20); // ��������0��40֮���������
						if (temp == 6)	paintFlag[i][j] = 2; // ����ʮ��֮һ�ĸ��ʱ��Ϊ����
					}	
				}
			}
		return paintFlag; // ����λ�ñ��
	}

	public void paint(Graphics g){ // ���ƺ���������λ�ñ�ǣ����費ͬ��ɫ������ͨ·��ǽ����è������
		for(i = 0; i < gridNum; i++) // �����Թ�
			for(j = 0; j < gridNum; j++) {
				if (paintFlag[i][j] == 0) { // ������Ϊǽ�����������ɫ
					g.setColor(Color.red);
					g.fill3DRect(i*unitSize, j*unitSize, unitSize, unitSize, true);
				}	
				if (paintFlag[i][j] == 1) { // ������Ϊͨ·��������ɫ				
					g.setColor(Color.ORANGE);
					g.fill3DRect(i*unitSize, j*unitSize, unitSize, unitSize, true);
				}
				if (paintFlag[i][j] == 2) { // ������Ϊ����
					Image food = new ImageIcon("src/food.jpg").getImage(); // ��ȡͼƬ
					g.drawImage(food, i*unitSize, j*unitSize, this); // �ø�ͼƬ���

				}
			}
		Image people = new ImageIcon("src/newpanda.jpg").getImage();
		g.drawImage(people, startx, starty, this);
		paintFlag[startx/unitSize][starty/unitSize] = 1; // ���ı���è��ǰ��λ�õı��λΪͨ·
		if (startx > 0 && startx < 480 && starty > 0 && starty < 480) { // �����è��λ�����Թ�Χǽ��
			g.setColor(Color.orange); // �ı䵱ǰ��ɫΪ�ٻ�ɫ
			if (paintFlag[startx/unitSize - 1][starty/unitSize] == 0) { // �����è���󷽵�һ������Ϊǽ
				nextxl = -1; // ��Ĭ�Ͽ��ܵ���һ��������Ծλ����Ч
				nextyl = -1;
				for (i = startx/unitSize - 2; i > 0; i--) { // ���󷽵ڶ������鿪ʼ��
					if (paintFlag[i][starty/unitSize] == 1 || paintFlag[i][starty/unitSize] == 2) { // ����з�����Ϊͨ·���߱���
						nextxl = i*unitSize; // ����λ�ø�����ܵ���һ��������Ծλ��
						nextyl = starty;
						g.drawLine(startx, starty + 5, nextxl + 10, nextyl + 5); // ������Ծ·��
						g.drawLine(startx, starty + 15, nextxl + 10, nextyl + 15); // ������Ծ·��
						tempx = startx - 10;
						while (tempx> nextxl + 10 ) { // ������Ծ���뻭�����ӵĺᵲ
							g.drawLine(tempx, starty + 5, tempx, starty + 15);
							tempx -= 10;
						}					
						break; // ֻҪ�ҵ���һ������ֹͣѭ��
					}
				}
			}
			if (paintFlag[startx/unitSize + 1][starty/unitSize] == 0) { // ����˵��ҷ���һ������Ϊǽ
				nextxr = -1; // ��Ĭ�Ͽ��ܵ���һ��������Ծλ����Ч
				nextyr = -1;
				for (i = startx/unitSize + 2; i < 24; i++) { // ���ҷ��ڶ������鿪ʼ��
					if (paintFlag[i][starty/unitSize] == 1 || paintFlag[i][starty/unitSize] == 2) { // ����з�����Ϊͨ·���߱���
						nextxr = i*unitSize; // ����λ�ø�����ܵ���һ��������Ծλ��
						nextyr = starty;
						g.drawLine(startx + 20, starty + 5, nextxr + 10, nextyr + 5); // ������Ծ·��
						g.drawLine(startx + 20, starty + 15, nextxr + 10, nextyr + 15); // ������Ծ·��
						tempx = startx + 30;
						while (tempx <= nextxr) { // ������Ծ���뻭�����ӵĺᵲ
							g.drawLine(tempx, starty + 5, tempx, starty + 15);
							tempx += 10;
						}	
						break; // ֻҪ�ҵ���һ������ֹͣѭ��
					}	
				}
			}
			if (paintFlag[startx/unitSize][starty/unitSize - 1] == 0) { // ����˵��Ϸ���һ������Ϊǽ
				nextxu = -1; // ��Ĭ�Ͽ��ܵ���һ��������Ծλ����Ч
				nextyu = -1;
				for (i = starty/unitSize - 2; i > 0; i--) { // ���Ϸ��ڶ������鿪ʼ��
					if (paintFlag[startx/unitSize][i] == 1 || paintFlag[startx/unitSize][i] == 2) { // ����з�����Ϊͨ·���߱���
						nextxu = startx; // ����λ�ø�����ܵ���һ��������Ծλ��
						nextyu = i*unitSize;
						g.drawLine(startx + 5, starty, nextxu + 5, nextyu + 10); // ������Ծ·��
						g.drawLine(startx + 15, starty, nextxu + 15, nextyu + 10); // ������Ծ·��
						tempy = starty - 10;
						while (tempy > nextyu + 10) { // ������Ծ���뻭�����ӵĺᵲ
							g.drawLine(startx + 5, tempy, startx + 15, tempy);
							tempy -= 10;
						}
						break; // ֻҪ�ҵ���һ������ֹͣѭ��
					}
				}
			}
			if (paintFlag[startx/unitSize][starty/unitSize + 1] == 0) { // ����˵��·���һ������Ϊǽ
				nextxd = -1; // ��Ĭ�Ͽ��ܵ���һ��������Ծλ����Ч
				nextyd = -1;
				for (i = starty/unitSize + 2; i < 24; i++) { // ���·��ڶ������鿪ʼ��
					if (paintFlag[startx/unitSize][i] == 1 || paintFlag[startx/unitSize][i] == 2) { // ����з�����Ϊͨ·���߱���
						nextxd = startx; // ����λ�ø�����ܵ���һ��������Ծλ��
						nextyd = i*unitSize;
						g.drawLine(startx + 5, starty + 20, nextxd + 5, nextyd + 10); // ������Ծ·��
						g.drawLine(startx + 15, starty + 20, nextxd + 15, nextyd + 10); // ������Ծ·��
						tempy = starty + 30;
						while (tempy <= nextyd) { // ������Ծ���뻭�����ӵĺᵲ
							g.drawLine(startx + 5, tempy, startx + 15, tempy);
							tempy += 10;
						}
						break; // ֻҪ�ҵ���һ������ֹͣѭ��
					}
				}		
			}
		}
	}

	public void update(Graphics g){ // ����˫����   
		Image t = createImage(getWidth(), getHeight());
		Graphics GraImage = t.getGraphics();
		paint(GraImage);
		GraImage.dispose();
		g.drawImage(t, 0, 0, null);
	}

	public void moveLeft(){ // ���������� 
		startx = startx - unitSize; // ��è��������ֵ
		if (startx > 480 || starty > 480 || startx < 0 || starty < 0){ // �����λ�����Թ�����
			startx = startx + unitSize;
			win = true;
			for (i = 0; i < gridNum; i++) // �����Թ�
				for (j = 0; j < gridNum; j++) {	
					if (paintFlag[i][j] == 2) // �������û�Ե�����
						win = false; // ���Ϊδ�ɹ�
				}
			repaint(); // �ػ沢������
			if(win)	JOptionPane.showMessageDialog(this,"�㿴�ҿɰ���||����~~","Win!",JOptionPane.INFORMATION_MESSAGE);		
			else	JOptionPane.showMessageDialog(this,"����㲻��������һ�������ס�������","Wrong!",JOptionPane.INFORMATION_MESSAGE);
		}
		else { // �����λ�����Թ�����
			if (paintFlag[startx/unitSize][starty/unitSize] == 1  || paintFlag[startx/unitSize][starty/unitSize] == 2)	repaint(); // �����λ��Ϊͨ·����ػ�
			else { // �����λ��Ϊǽ
				if (nextxl == -1) startx = startx + unitSize; // ������ܵ���һ��������Ծλ����Ч����ԭλ��
				else { // ����������Ծ����λ��
					startx = nextxl;
					starty = nextyl;
					repaint(); // �ػ�
				}	
			}
		}
	}

	public void moveRight(){ // ���Ҷ�������
		startx = startx + unitSize; // ��è��������ֵ
		if (startx > 480 || starty > 480 || startx < 0 || starty < 0){ // �����λ�����Թ�����
			startx = startx - unitSize;
			win = true;
			for (i = 0; i < gridNum; i++) // �����Թ�
				for (j = 0; j < gridNum; j++) {	
					if (paintFlag[i][j] == 2) // �������û�Ե�����
						win = false; // ���Ϊδ�ɹ�
				}
			repaint(); // �ػ沢������
			if(win)	JOptionPane.showMessageDialog(this,"�㿴�ҿɰ���||����~~","Win!",JOptionPane.INFORMATION_MESSAGE);		
			else	JOptionPane.showMessageDialog(this,"����㲻��������һ�������ס�������","Wrong!",JOptionPane.INFORMATION_MESSAGE);
		}
		else { // �����λ�����Թ�����
			if (paintFlag[startx/unitSize][starty/unitSize] == 1 || paintFlag[startx/unitSize][starty/unitSize] == 2)	repaint(); // �����λ��Ϊͨ·����ػ�
			else { // �����λ��Ϊǽ
				if (nextxr == -1) startx = startx - unitSize; // ������ܵ���һ��������Ծλ����Ч����ԭλ��
				else { // ����������Ծ����λ��
					startx = nextxr;
					starty = nextyr;
					repaint(); // �ػ�
				}	
			}
		}
	}

	public void moveUp(){ // ���϶�������
		starty = starty - unitSize; // ��è��������ֵ
		if (startx > 480 || starty > 480 || startx < 0 || starty < 0){ // �����λ�����Թ�����	
			starty = starty + unitSize;
			win = true;
			for (i = 0; i < gridNum; i++) // �����Թ�
				for (j = 0; j < gridNum; j++) {	
					if (paintFlag[i][j] == 2) // �������û�Ե�����
						win = false; // ���Ϊδ�ɹ�
				}
			repaint(); // �ػ沢������	
			if(win)	JOptionPane.showMessageDialog(this,"�㿴�ҿɰ���||����~~","Win!",JOptionPane.INFORMATION_MESSAGE);		
			else	JOptionPane.showMessageDialog(this,"����㲻��������һ�������ס�������","Wrong!",JOptionPane.INFORMATION_MESSAGE);
		}
		else { // �����λ�����Թ�����
			if (paintFlag[startx/unitSize][starty/unitSize] == 1 || paintFlag[startx/unitSize][starty/unitSize] == 2)	repaint(); // �����λ��Ϊͨ·����ػ�
			else { // �����λ��Ϊǽ
				if (nextxu == -1) starty = starty + unitSize; // ������ܵ���һ��������Ծλ����Ч����ԭλ��
				else { // ����������Ծ����λ��
					startx = nextxu;
					starty = nextyu;
					repaint(); // �ػ�
				}		
			}
		}
	}

	public void moveDown(){ // ���¶�������
		starty = starty + unitSize; // ��è��������ֵ
		if (startx > 480 || starty > 480 || startx < 0 || starty < 0){ // �����λ�����Թ�����
			starty = starty - unitSize;
			win = true;
			for (i = 0; i < gridNum; i++) // ��ͨ·���⣬Χǽ���ڵ�λ�����������
				for (j = 0; j < gridNum; j++) {	
					if (paintFlag[i][j] == 2) // �������û�Ե�����
						win = false; // ���Ϊδ�ɹ�
				}
			repaint(); // �ػ沢������	
			if(win)	JOptionPane.showMessageDialog(this,"�㿴�ҿɰ���||����~~","Win!",JOptionPane.INFORMATION_MESSAGE);		
			else	JOptionPane.showMessageDialog(this,"����㲻��������һ�������ס�������","Wrong!",JOptionPane.INFORMATION_MESSAGE);
		}
		else { // �����λ�����Թ�����
			if (paintFlag[startx/unitSize][starty/unitSize] == 1 || paintFlag[startx/unitSize][starty/unitSize] == 2)	repaint(); // �����λ��Ϊͨ·����ػ�
			else { // �����λ��Ϊǽ				
				if (nextxd == -1) starty = starty - unitSize; // ������ܵ���һ��������Ծλ����Ч����ԭλ��
				else { // ����������Ծ����λ��
					startx = nextxd;
					starty = nextyd;
					repaint(); // �ػ�
				}
			}
		}
	}

}
