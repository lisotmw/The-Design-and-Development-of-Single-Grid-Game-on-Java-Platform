package complexity;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class CreateCanvas extends Canvas{
	static final int unitSize = 20; // 小方块边长 
	int gridNum; // 迷宫边长，以小方块为单位
	int temp;
	int x, y; // 通路坐标
	int i, j;
	boolean win; // 游戏仲裁标记
	int paintFlag[][] = new int[25][25]; // 位置属性标记 
	int startx = 240, starty = 240; // 熊猫的初始位置
	int nextxl, nextxr, nextxu, nextxd; // 四个方向可能的跳跃位置
	int nextyl, nextyr, nextyu, nextyd;
	int tempx, tempy; // 画梯子所用中间变量

	public CreateCanvas() {
		gridNum = 25; 
		paintFlag = newMaze(); // 构造函数，生成随机位置标记
	}

	public int[][] newMaze(){ // 生成随机迷宫位置	
		int paintFlag[][] = new int[25][25];
		for(i = 0; i < gridNum; i++) {
			for(j = 0; j < gridNum; j++) {
				paintFlag[i][j] = 0; // 先默认迷宫全是墙
			} 
		}
		x = startx; // 为通路赋初始位置
		y = starty; 
		paintFlag[12][12] = 1; // 初始位置标记为通路
		while (true) { // 循环判断随机找出一条通路
			temp = (int)(Math.random()*4); // 随机生成0－3之间整数
			if (temp == 0)	x += unitSize; // 判断记录为通路
			if (temp == 1)	y += unitSize;				
			if (temp == 2)	x -= unitSize;
			if (temp == 3)	y -= unitSize;
			paintFlag[x/unitSize][y/unitSize] = 1; // 标记通路
			if (x == 0 || y == 0 || x == 480 || y == 480) break; // 当找到通路出口时停止
		}
		for (i = 1; i < gridNum - 1; i++) // 对通路以外，围墙以内的位置做随机处理
			for (j = 1; j < gridNum - 1; j++) {				
				if (paintFlag[i][j] == 1) continue; // 如果已是通路，跳过
				else {
					temp = (int)(Math.random()*3); // 否则产生0－3之间随机整数
					if (temp == 0) { // 以二分之一的概率标记为通路	
						paintFlag[i][j] = 1;						
						temp = (int)(Math.random()*20); // 继续产生0－40之间随机整数
						if (temp == 6)	paintFlag[i][j] = 2; // 以四十分之一的概率标记为宝物
					}	
				}
			}
		return paintFlag; // 返回位置标记
	}

	public void paint(Graphics g){ // 绘制函数，根据位置标记，赋予不同颜色，区分通路，墙，熊猫与竹子
		for(i = 0; i < gridNum; i++) // 遍历迷宫
			for(j = 0; j < gridNum; j++) {
				if (paintFlag[i][j] == 0) { // 如果标记为墙，则填充亮灰色
					g.setColor(Color.red);
					g.fill3DRect(i*unitSize, j*unitSize, unitSize, unitSize, true);
				}	
				if (paintFlag[i][j] == 1) { // 如果标记为通路，则填充黑色				
					g.setColor(Color.ORANGE);
					g.fill3DRect(i*unitSize, j*unitSize, unitSize, unitSize, true);
				}
				if (paintFlag[i][j] == 2) { // 如果标记为竹子
					Image food = new ImageIcon("src/food.jpg").getImage(); // 获取图片
					g.drawImage(food, i*unitSize, j*unitSize, this); // 用该图片填充

				}
			}
		Image people = new ImageIcon("src/newpanda.jpg").getImage();
		g.drawImage(people, startx, starty, this);
		paintFlag[startx/unitSize][starty/unitSize] = 1; // 并改变熊猫当前的位置的标记位为通路
		if (startx > 0 && startx < 480 && starty > 0 && starty < 480) { // 如果熊猫的位置在迷宫围墙里
			g.setColor(Color.orange); // 改变当前颜色为橘黄色
			if (paintFlag[startx/unitSize - 1][starty/unitSize] == 0) { // 如果熊猫的左方第一个方块为墙
				nextxl = -1; // 先默认可能的下一个向左跳跃位置无效
				nextyl = -1;
				for (i = startx/unitSize - 2; i > 0; i--) { // 从左方第二个方块开始找
					if (paintFlag[i][starty/unitSize] == 1 || paintFlag[i][starty/unitSize] == 2) { // 如果有方块标记为通路或者宝物
						nextxl = i*unitSize; // 将该位置赋予可能的下一个向左跳跃位置
						nextyl = starty;
						g.drawLine(startx, starty + 5, nextxl + 10, nextyl + 5); // 画出跳跃路径
						g.drawLine(startx, starty + 15, nextxl + 10, nextyl + 15); // 画出跳跃路径
						tempx = startx - 10;
						while (tempx> nextxl + 10 ) { // 根据跳跃距离画出梯子的横挡
							g.drawLine(tempx, starty + 5, tempx, starty + 15);
							tempx -= 10;
						}					
						break; // 只要找到有一个，即停止循环
					}
				}
			}
			if (paintFlag[startx/unitSize + 1][starty/unitSize] == 0) { // 如果人的右方第一个方块为墙
				nextxr = -1; // 先默认可能的下一个向右跳跃位置无效
				nextyr = -1;
				for (i = startx/unitSize + 2; i < 24; i++) { // 从右方第二个方块开始找
					if (paintFlag[i][starty/unitSize] == 1 || paintFlag[i][starty/unitSize] == 2) { // 如果有方块标记为通路或者宝物
						nextxr = i*unitSize; // 将该位置赋予可能的下一个向右跳跃位置
						nextyr = starty;
						g.drawLine(startx + 20, starty + 5, nextxr + 10, nextyr + 5); // 画出跳跃路径
						g.drawLine(startx + 20, starty + 15, nextxr + 10, nextyr + 15); // 画出跳跃路径
						tempx = startx + 30;
						while (tempx <= nextxr) { // 根据跳跃距离画出梯子的横挡
							g.drawLine(tempx, starty + 5, tempx, starty + 15);
							tempx += 10;
						}	
						break; // 只要找到有一个，即停止循环
					}	
				}
			}
			if (paintFlag[startx/unitSize][starty/unitSize - 1] == 0) { // 如果人的上方第一个方块为墙
				nextxu = -1; // 先默认可能的下一个向上跳跃位置无效
				nextyu = -1;
				for (i = starty/unitSize - 2; i > 0; i--) { // 从上方第二个方块开始找
					if (paintFlag[startx/unitSize][i] == 1 || paintFlag[startx/unitSize][i] == 2) { // 如果有方块标记为通路或者宝物
						nextxu = startx; // 将该位置赋予可能的下一个向上跳跃位置
						nextyu = i*unitSize;
						g.drawLine(startx + 5, starty, nextxu + 5, nextyu + 10); // 画出跳跃路径
						g.drawLine(startx + 15, starty, nextxu + 15, nextyu + 10); // 画出跳跃路径
						tempy = starty - 10;
						while (tempy > nextyu + 10) { // 根据跳跃距离画出梯子的横挡
							g.drawLine(startx + 5, tempy, startx + 15, tempy);
							tempy -= 10;
						}
						break; // 只要找到有一个，即停止循环
					}
				}
			}
			if (paintFlag[startx/unitSize][starty/unitSize + 1] == 0) { // 如果人的下方第一个方块为墙
				nextxd = -1; // 先默认可能的下一个向下跳跃位置无效
				nextyd = -1;
				for (i = starty/unitSize + 2; i < 24; i++) { // 从下方第二个方块开始找
					if (paintFlag[startx/unitSize][i] == 1 || paintFlag[startx/unitSize][i] == 2) { // 如果有方块标记为通路或者宝物
						nextxd = startx; // 将该位置赋予可能的下一个向下跳跃位置
						nextyd = i*unitSize;
						g.drawLine(startx + 5, starty + 20, nextxd + 5, nextyd + 10); // 画出跳跃路径
						g.drawLine(startx + 15, starty + 20, nextxd + 15, nextyd + 10); // 画出跳跃路径
						tempy = starty + 30;
						while (tempy <= nextyd) { // 根据跳跃距离画出梯子的横挡
							g.drawLine(startx + 5, tempy, startx + 15, tempy);
							tempy += 10;
						}
						break; // 只要找到有一个，即停止循环
					}
				}		
			}
		}
	}

	public void update(Graphics g){ // 引入双缓存   
		Image t = createImage(getWidth(), getHeight());
		Graphics GraImage = t.getGraphics();
		paint(GraImage);
		GraImage.dispose();
		g.drawImage(t, 0, 0, null);
	}

	public void moveLeft(){ // 向左动作函数 
		startx = startx - unitSize; // 熊猫的新坐标值
		if (startx > 480 || starty > 480 || startx < 0 || starty < 0){ // 如果新位置在迷宫以外
			startx = startx + unitSize;
			win = true;
			for (i = 0; i < gridNum; i++) // 遍历迷宫
				for (j = 0; j < gridNum; j++) {	
					if (paintFlag[i][j] == 2) // 如果还有没吃的竹子
						win = false; // 标记为未成功
				}
			repaint(); // 重绘并输出结果
			if(win)	JOptionPane.showMessageDialog(this,"你看我可爱吗？||？喵~~","Win!",JOptionPane.INFORMATION_MESSAGE);		
			else	JOptionPane.showMessageDialog(this,"如果你不介意再来一碟花生米。。。。","Wrong!",JOptionPane.INFORMATION_MESSAGE);
		}
		else { // 如果新位置在迷宫以内
			if (paintFlag[startx/unitSize][starty/unitSize] == 1  || paintFlag[startx/unitSize][starty/unitSize] == 2)	repaint(); // 如果新位置为通路或宝物，重绘
			else { // 如果新位置为墙
				if (nextxl == -1) startx = startx + unitSize; // 如果可能的下一个向左跳跃位置无效，还原位置
				else { // 否则，向左跳跃到新位置
					startx = nextxl;
					starty = nextyl;
					repaint(); // 重绘
				}	
			}
		}
	}

	public void moveRight(){ // 向右动作函数
		startx = startx + unitSize; // 熊猫的新坐标值
		if (startx > 480 || starty > 480 || startx < 0 || starty < 0){ // 如果新位置在迷宫以外
			startx = startx - unitSize;
			win = true;
			for (i = 0; i < gridNum; i++) // 遍历迷宫
				for (j = 0; j < gridNum; j++) {	
					if (paintFlag[i][j] == 2) // 如果还有没吃的竹子
						win = false; // 标记为未成功
				}
			repaint(); // 重绘并输出结果
			if(win)	JOptionPane.showMessageDialog(this,"你看我可爱吗？||？喵~~","Win!",JOptionPane.INFORMATION_MESSAGE);		
			else	JOptionPane.showMessageDialog(this,"如果你不介意再来一碟花生米。。。。","Wrong!",JOptionPane.INFORMATION_MESSAGE);
		}
		else { // 如果新位置在迷宫以内
			if (paintFlag[startx/unitSize][starty/unitSize] == 1 || paintFlag[startx/unitSize][starty/unitSize] == 2)	repaint(); // 如果新位置为通路或宝物，重绘
			else { // 如果新位置为墙
				if (nextxr == -1) startx = startx - unitSize; // 如果可能的下一个向左跳跃位置无效，还原位置
				else { // 否则，向右跳跃到新位置
					startx = nextxr;
					starty = nextyr;
					repaint(); // 重绘
				}	
			}
		}
	}

	public void moveUp(){ // 向上动作函数
		starty = starty - unitSize; // 熊猫的新坐标值
		if (startx > 480 || starty > 480 || startx < 0 || starty < 0){ // 如果新位置在迷宫以外	
			starty = starty + unitSize;
			win = true;
			for (i = 0; i < gridNum; i++) // 遍历迷宫
				for (j = 0; j < gridNum; j++) {	
					if (paintFlag[i][j] == 2) // 如果还有没吃的竹子
						win = false; // 标记为未成功
				}
			repaint(); // 重绘并输出结果	
			if(win)	JOptionPane.showMessageDialog(this,"你看我可爱吗？||？喵~~","Win!",JOptionPane.INFORMATION_MESSAGE);		
			else	JOptionPane.showMessageDialog(this,"如果你不介意再来一碟花生米。。。。","Wrong!",JOptionPane.INFORMATION_MESSAGE);
		}
		else { // 如果新位置在迷宫以内
			if (paintFlag[startx/unitSize][starty/unitSize] == 1 || paintFlag[startx/unitSize][starty/unitSize] == 2)	repaint(); // 如果新位置为通路或宝物，重绘
			else { // 如果新位置为墙
				if (nextxu == -1) starty = starty + unitSize; // 如果可能的下一个向左跳跃位置无效，还原位置
				else { // 否则，向上跳跃到新位置
					startx = nextxu;
					starty = nextyu;
					repaint(); // 重绘
				}		
			}
		}
	}

	public void moveDown(){ // 向下动作函数
		starty = starty + unitSize; // 熊猫的新坐标值
		if (startx > 480 || starty > 480 || startx < 0 || starty < 0){ // 如果新位置在迷宫以外
			starty = starty - unitSize;
			win = true;
			for (i = 0; i < gridNum; i++) // 对通路以外，围墙以内的位置做随机处理
				for (j = 0; j < gridNum; j++) {	
					if (paintFlag[i][j] == 2) // 如果还有没吃的竹子
						win = false; // 标记为未成功
				}
			repaint(); // 重绘并输出结果	
			if(win)	JOptionPane.showMessageDialog(this,"你看我可爱吗？||？喵~~","Win!",JOptionPane.INFORMATION_MESSAGE);		
			else	JOptionPane.showMessageDialog(this,"如果你不介意再来一碟花生米。。。。","Wrong!",JOptionPane.INFORMATION_MESSAGE);
		}
		else { // 如果新位置在迷宫以内
			if (paintFlag[startx/unitSize][starty/unitSize] == 1 || paintFlag[startx/unitSize][starty/unitSize] == 2)	repaint(); // 如果新位置为通路或宝物，重绘
			else { // 如果新位置为墙				
				if (nextxd == -1) starty = starty - unitSize; // 如果可能的下一个向左跳跃位置无效，还原位置
				else { // 否则，向下跳跃到新位置
					startx = nextxd;
					starty = nextyd;
					repaint(); // 重绘
				}
			}
		}
	}

}
