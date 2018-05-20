package 回溯法找到一个解;

import 回溯法过程演示.*;

public class Answer {
	public static Step mystack[] = new Step[32];//记录走法的栈
	public static int top;//栈顶指针
	public static Step last_step = new Step();// 上一步
	public static char diamond[][] = new char[7][7];//棋盘，二维数组表示
	public static int Left_diamond = 32;//剩余子数
	public static int x, y, nx, ny, ndir;// ndir值代表方向, 0代表向右, 1代表向下, 2代表向左, 3代表向上
	static int flag = 1;// 是否成功找到解的标志
	public static DiamondChess frame;
	int count = 0;//进入最内层循环的次数
	int backCount = 0;//回溯的次数

	public static void Init_diamond() {//初始化棋盘
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if ((i < 2 || i > 4) && (j < 2 || j > 4))
					;
				else {
					diamond[i][j] = '*';//棋子用*标记
				}
			}
		}
		diamond[3][3] = '.';
	}

	static int Move_diamond(int y, int x, int dir) {//判断棋子在dir方向上是否可走，如果可走则走法入栈
		if (diamond[y][x] != '*') {
			return 0;
		}
		Step temp = new Step();
		switch (dir) // 四个方向的走法
		{
		case 0:
			if (x + 2 > 6 || diamond[y][x + 1] != '*' || diamond[y][x + 2] != '.') {
				return 0;
			}
			diamond[y][x] = diamond[y][x + 1] = '.';//原来的位置和跨过的棋子记录为点号
			diamond[y][x + 2] = '*';
			temp.sx = x; //记录走法位置
			temp.sy = y;
			temp.tx = x + 2;
			temp.ty = y;
			temp.dir = dir;
			mystack[top++] = temp; // 走法入栈 top++
			return 1;
		case 1:
			if (y + 2 > 6 || diamond[y + 1][x] != '*' || diamond[y + 2][x] != '.') {
				return 0;
			}
			diamond[y][x] = diamond[y + 1][x] = '.';
			diamond[y + 2][x] = '*';
			temp.sx = x;
			temp.sy = y;
			temp.tx = x;
			temp.ty = y + 2;
			temp.dir = dir;

			mystack[top++] = temp;
			return 1;

		case 2:
			if (x - 2 < 0 || diamond[y][x - 1] != '*' || diamond[y][x - 2] != '.') {
				return 0;
			}
			diamond[y][x] = diamond[y][x - 1] = '.';
			diamond[y][x - 2] = '*';
			temp.sx = x;
			temp.sy = y;
			temp.tx = x - 2;
			temp.ty = y;
			temp.dir = dir;

			mystack[top++] = temp;
			return 1;
		case 3:
			if (y - 2 < 0 || diamond[y - 1][x] != '*' || diamond[y - 2][x] != '.') {
				return 0;
			}
			diamond[y][x] = diamond[y - 1][x] = '.';
			diamond[y - 2][x] = '*';
			temp.sx = x;
			temp.sy = y;
			temp.tx = x;
			temp.ty = y - 2;
			temp.dir = dir;

			mystack[top++] = temp;
			return 1;
		default:
			break;
		}
		return 0;
	}
	public void findOneAnswer() {//找到跳棋一种走法的方法
		Init_diamond(); // 初始化棋盘
		top = nx = ny = ndir = 0;
		System.out.println("回溯遍历，直到找到一个解 ");
		while (true) {
			boolean backFlag = false;
			if (Left_diamond == 1 && diamond[3][3] == '*') {//如果棋盘只剩下一颗棋子在正中央，则成功找到一个解，跳出循环
				break;
			}
			for (y = ny; y < 7; y++, nx = 0) //nx,ny代表下一次要移动的棋子的起始位置
			{
				for (x = nx; x < 7; x++, ndir = 0) {
					for (int dir = ndir; dir < 4; dir++) {
						count++;
						if (Move_diamond(y, x, dir) == 1)// 如果此步可行
						{					 
							Left_diamond--;
							nx = ny = ndir = 0;
							backFlag = true;//跳出for循环，判断是否需要回溯，重新遍历棋盘					
						}
						if (backFlag)
							break;
					}
					if (backFlag)
						break;
				}
				if (backFlag)
					break;
			}
			if (y == 7) {//回溯的条件是y==7
				backCount++;
				top--;
				if (top >= 0) {	// 回到上一步， 并改变方向
					last_step = mystack[top];
					//还原棋盘
					diamond[(last_step.sy + last_step.ty) / 2][(last_step.sx + last_step.tx) / 2] = '*';
					diamond[last_step.sy][last_step.sx] = '*';
					diamond[last_step.ty][last_step.tx] = '.';
					nx = last_step.sx;
					ny = last_step.sy;
					ndir = last_step.dir + 1;
					Left_diamond++;
				} else // 如果栈空
				{
					System.out.println("找不到任何一个解!");
					flag = 0;
					break;
				}
			}

		} // 大循环while（1）结束
		System.out.println("成功找到了一个解！");
	}

	public void printAnswer() {//打印走法
		System.out.println("回溯的次数" + backCount);
		System.out.println("进入最内层循环的次数" + count);
		int i;
		for (i = 0; i < top; i++) {
			Step step = new Step();
			step = mystack[i];
			System.out.println("Move" + "(" + (step.sy + 1) + "," + (step.sx + 1) + ")" + "to" + "(" + (step.ty + 1)
					+ "," + (step.tx + 1) + ")");
		}
	}
}
