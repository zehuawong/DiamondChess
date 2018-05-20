package 回溯法找解;

import 回溯法过程演示.*;

public class Answer {
	public static Step mystack[]=new Step[100];//这个栈要改？
	public static Step last_step=new Step();//上一步
	public static char diamond[][]=new char [7][7];
	public static int Left_diamond=32;
	public static int x,y,nx,ny,ndir;// ndir值代表方向, 0代表向右, 1代表向下, 2代表向左, 3代表向上
	public static int top;
	static int flag=1;// 是否成功找到解的标志
	public static DiamondChess frame;
	
	 
	public static void Init_diamond(){
		for(int i=0;i<7;i++){
			for(int j=0;j<7;j++){
				if((i<2||i>4)&&(j<2||j>4));
				else {
					diamond[i][j]='*';
				}
			}
		}
		diamond[3][3]='.';
	}
	static int Move_diamond(int y, int x, int dir)	
	{
		if(diamond[y][x] != '*')
		{
			return 0;
		}
		Step temp=new Step();
		switch(dir)  //四个方向的走法
		{
		case 0:
			if(x+2>6 || diamond[y][x+1]!='*' || diamond[y][x+2]!='.')
			{
				return 0;
			}
			diamond[y][x] = diamond[y][x+1] = '.';//原来的位置和跨过的棋子记录为点号
			diamond[y][x+2] = '*';	 
			temp.sx = x;   //记录走法位置
			temp.sy = y;
			temp.tx = x+2;
			temp.ty = y;
			temp.dir = dir;
			frame.MoveOneStep(temp);//窗口的棋盘更新走一步
			mystack[top++] = temp; //走法入栈 top++
			return 1;
			 
		case 1:
			if(y+2>6 || diamond[y+1][x]!='*' || diamond[y+2][x]!='.')
			{
				return 0;
			}
			diamond[y][x] = diamond[y+1][x] = '.';
			diamond[y+2][x] = '*';		
			temp.sx = x;  
			temp.sy = y;
			temp.tx = x;
			temp.ty = y+2;
			temp.dir = dir;
			frame.MoveOneStep(temp);//窗口的棋盘更新走一步
			mystack[top++] = temp;
			return 1;
			 
		case 2:
			if(x-2<0 || diamond[y][x-1]!='*' || diamond[y][x-2]!='.')
			{
				return 0;
			}
			diamond[y][x] = diamond[y][x-1] = '.';
			diamond[y][x-2] = '*';
			temp.sx = x;
			temp.sy = y;
			temp.tx = x-2;
			temp.ty = y;
			temp.dir = dir;
			frame.MoveOneStep(temp);//窗口的棋盘更新走一步
			mystack[top++] = temp;
			return 1;
		case 3:
			if(y-2<0 || diamond[y-1][x]!='*' || diamond[y-2][x]!='.')
			{
				return 0;
			}
			diamond[y][x] = diamond[y-1][x] = '.';
			diamond[y-2][x] = '*';
			temp.sx = x;
			temp.sy = y;
			temp.tx = x;
			temp.ty = y-2;
			temp.dir = dir;
			frame.MoveOneStep(temp);//窗口的棋盘更新走一步
			mystack[top++] = temp;
			return 1;
		default:
			break;
		}
		return 0;
	}
	
	 
	/*******************************主函数********************************/
	public void findOneAnswer(DiamondChess Frame)	 
	{	
		this.frame=Frame;
		//frame.chessArmy[0][0].setLife(true);
		Init_diamond(); //初始化
		top = nx = ny = ndir = 0;
		System.out.println("回溯遍历，直到找到一个解 ");
		// 回溯遍历，直到找到一个解
		while(true){
			boolean backFlag=false;
		//	System.out.println("下一次循环");
			if(Left_diamond == 1 && diamond[3][3] == '*')
			{
				break;
			} 
			for(y=ny; y<7; y++,nx=0)  //nx,ny代表下一次要移动的棋子的起始位置？？
			{
				for(x=nx; x<7; x++,ndir=0)
				{
					for(int dir=ndir; dir<4; dir++)
					{
						if(Move_diamond(y, x, dir)==1)//如果此步可行
						{	
							//此处更新窗口棋盘：
							Left_diamond--;
							nx = ny = ndir = 0;
							backFlag=true;
							//goto nextstep;	//如何开始下一次大循环	 						
						}
						if(backFlag)
							break;
					}
					if(backFlag)
						break;
				}
				if(backFlag)
					break;
			} 
		//nextstep 下一步
			 if(y == 7)
				{	
				 	System.out.println("top="+top);
					top--;
					// 回到上一步， 并改变方向（注意要改变方向，关键的一步）
					if(top >= 0)
					{
						last_step = mystack[top];
						//还原窗口棋盘的一步
						frame.BackOneStep(last_step);
						diamond[(last_step.sy + last_step.ty)/2][(last_step.sx + last_step.tx)/2] = '*';
						diamond[last_step.sy][last_step.sx] = '*';
						diamond[last_step.ty][last_step.tx] = '.';
						nx = last_step.sx;//重要，下一次开始的位置
						ny = last_step.sy;//重要
						ndir = last_step.dir + 1;//改变方向，重要！！
						Left_diamond++;
					}
					else  //如果栈空
					{
						System.out.println("找不到任何一个解!");
						flag=0;
						break;
					}
				}
		
		}//大循环while（1）结束
		System.out.println("成功找到了一个解！");
		//printAnswer();
	}
	public void printAnswer(){
		int i;
		for(i=0;i<top;i++){
			Step step=new Step();
			step=mystack[i];
			System.out.println("Move"+"("+(step.sy+1)+","+(step.sx+1)+")"+"to"
							+"("+(step.ty+1)+","+(step.tx+1)+")");
		}
	}
}
