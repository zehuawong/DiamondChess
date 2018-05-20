package 游戏;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.SwingConstants;

class Step // 记录移动棋子的信息
{
	public int sx, sy; // 记录移动棋子前棋子的位置
	public int tx, ty; // 记录移动棋子后棋子的位置
	public int dir; // dir值代表移动棋子的方向
}

public class DC extends JFrame {
	// -----move时候用的变量们
	public static int oldx = 0;
	public static int oldy = 0;
	public static int newx = 0;
	public static int newy = 0;
	public static boolean movingflag = false; // 可以移动的棋子标识

	private GridLayout optionlayer = new GridLayout(5, 1);
	private GridLayout chessboardlayer = new GridLayout(9, 9);
	// -----左右布局的jpanel
	private JPanel leftpanel = new JPanel();
	private JPanel rightpanel = new JPanel();
	// -----
	public static int restcount = 32;
	public static int steps = 0;
	public static JLabel jl1 = new JLabel("剩余子数:" + restcount);
	public static JLabel jl2 = new JLabel("当前步数:0" + steps);

	public static boolean[][] chessdot;// -----chess点阵
	public static boolean[][] chessboard;// -----board点阵//区分边界
	// 上面这两个二维数组主要是为了初始化棋盘

	// -----chess引用点阵 //含button和颜色信息的
	public static OneChess[][] chessArmy = new OneChess[9][9];
	private static Stack<Step> stack=new Stack<Step>();
	private JButton lastStep=new JButton("悔棋");
	private JButton backButton=new JButton("退出");
	// -----初始化
	public static void initChessDot() {
		chessdot = new boolean[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (((i < 3 || i > 5) && (j < 3 || j > 5)) || i == 0 || i == 8 || j == 0 || j == 8)
					chessdot[i][j] = false;
				else
					chessdot[i][j] = true;
			}
		}
		chessdot[4][4] = false;
	}

	public static void initChessBoard() {
		chessboard = new boolean[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (((i < 3 || i > 5) && (j < 3 || j > 5)) || i == 0 || i == 8 || j == 0 || j == 8)
					chessboard[i][j] = false;
				else
					chessboard[i][j] = true;
			}
		}

	}
	void initChessArmy() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {

				boolean flag = chessdot[col][row];
				OneChess oc = new OneChess(flag, col, row);// 一颗棋子
				if (!chessboard[col][row]) { // 如果不是棋子，即是边界的话
					oc.setBoard(); // 设置边界为白色
					JLabel borderLabel = new JLabel(); // 标签加入到右边的面板中
					borderLabel.setBackground(Color.DARK_GRAY);
					borderLabel.setOpaque(false);
					this.rightpanel.add(borderLabel);
				} else {
					this.rightpanel.add(oc); // 棋子button加入到右边面板中
				}
				this.chessArmy[col][row] = oc; // 设置含有button和颜色信息的棋子阵

			}
		}
	}
	public DC() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon image = new ImageIcon(this.getClass().getResource("钻石图标.png"));
		this.setIconImage(image.getImage());// 窗口识别图标

		// 游戏背景图片
		ImageIcon bg = new ImageIcon(this.getClass().getResource("背景1.jpg"));
		JLabel backLabel = new JLabel(bg);
		backLabel.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
		this.getLayeredPane().add(backLabel, new Integer(Integer.MIN_VALUE));

		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.setOpaque(false);// 内容面板设置透明
		this.setBounds(400, 100, 1080, 800);
		this.setTitle("独立钻石跳棋");
		// this.setResizable(false);//设置窗口不可调整大小
		// contentPane.setLayout(gamepanellayer);
		contentPane.setLayout(new BorderLayout());
		contentPane.add(this.leftpanel, BorderLayout.WEST); // 左边面板

		leftpanel.setBackground(Color.gray);// new Color(100,100,100)
		leftpanel.setOpaque(false);
		optionlayer.setVgap(20);
		leftpanel.setLayout(this.optionlayer);
		jl1.setForeground(Color.YELLOW);
		jl1.setBounds(5, 5, 179, 35);
		jl1.setVerticalAlignment(SwingConstants.BOTTOM);
		jl1.setFont(new Font("宋体", Font.BOLD, 30)); // 剩余步数和已走步数
		jl1.setBackground(Color.BLACK);
		this.leftpanel.add(jl1);
		jl2.setForeground(Color.YELLOW);
		jl2.setBounds(189, 5, 179, 35);
		jl2.setVerticalAlignment(SwingConstants.TOP);
		jl2.setFont(new Font("宋体", Font.BOLD, 30));
		jl2.setBackground(Color.BLACK);
		this.leftpanel.add(jl2);
		//悔棋按钮
		lastStep.setForeground(Color.YELLOW);
		lastStep.setForeground(Color.YELLOW);
		lastStep.setBackground(Color.BLACK);
		lastStep.setFont(new Font("楷体", Font.BOLD, 30));
		lastStep.setOpaque(false);// 按钮设置透明
		lastStep.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				do_lastStepButton_click( e);
			}	
		});
		this.leftpanel.add(lastStep);
		
		 
		backButton.setBackground(Color.BLACK);
		backButton.setForeground(Color.YELLOW);
		backButton.setOpaque(false);// 按钮设置透明
		backButton.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				do_backButton_click( e);
			}	
		});
		backButton.setFont(new Font("楷体", Font.BOLD, 30));
		this.leftpanel.add(backButton);
		
		contentPane.add(this.rightpanel, BorderLayout.CENTER);// 右边面板
		rightpanel.setOpaque(false);// 右边面板设置透明
		this.rightpanel.setLayout(chessboardlayer);// 右边布局9*9
		initChessBoard();
		initChessDot();
		initChessArmy();
		this.setVisible(true);

	}

	

	public static boolean canMove() { // 静态方法
		boolean f = false;
		if (oldx == newx && (oldy == newy + 2 || newy == oldy + 2)) { // x水平方向移动棋子
			if (chessArmy[oldx][(newy + oldy) / 2].getLife()) { // 如果中间的是棋子
				f = true;
			} else {
				f = false;
			}
		}
		if (oldy == newy && (oldx == newx + 2 || newx == oldx + 2)) { // y方向移动棋子
			if (chessArmy[(newx + oldx) / 2][oldy].getLife()) { // 如果中间的是棋子
				f = true;
			} else {
				f = false;
			}
		}
		return f;
	}

	public static void doMove() { // 静态方法
		if (oldx == newx) {
			chessArmy[oldx][(newy + oldy) / 2].setLife(false);
		}
		if (oldy == newy) {
			chessArmy[(newx + oldx) / 2][oldy].setLife(false);
		}
		chessArmy[oldx][oldy].setLife(false);
		chessArmy[newx][newy].setLife(true);
		Step step=new Step();
		step.sx=oldx;step.sy=oldy;
		step.tx=newx;step.ty=newy;
		stack.push(step);//走法记录入栈		
	}
	//悔棋操作
	private void do_lastStepButton_click(MouseEvent e){
		if(!stack.isEmpty()){
			Step step=new Step();			
			step=stack.peek();
			stack.pop();
			//chessArmy[step.tx][step.ty].setMoving();			
			chessArmy[step.tx][step.ty].setLife(false);
			chessArmy[(step.sx+step.tx)/2][(step.sy+step.ty)/2].setLife(true);
			chessArmy[step.sx][step.sy].setLife(true);	
			//修改当前步数和剩余子数
			steps--;
			restcount++;
			jl1.setText("剩余子数:" + restcount);
			jl2.setText("当前步数:" + steps);
		}
	}
	//退出操作
	private void  do_backButton_click(MouseEvent e){
		this.dispose();
	}
	public static void main(String[] args) {
		DC dc = new DC();
	}

}
