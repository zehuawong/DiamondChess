package 演示走法;

import 回溯法找到一个解.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class DC extends JFrame implements ActionListener {
	// -----move时候用的变量们
	public static int oldx = 0;
	public static int oldy = 0;
	public static int newx = 0;
	public static int newy = 0;
	public static boolean movingflag = false; // 可以移动的棋子标识

	private GridLayout optionlayer = new GridLayout(8, 1);
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
	JButton startButton = new JButton("开始");
	JButton stopButton = new JButton("暂停");
	JButton continueButton = new JButton("继续");
	JButton backButton = new JButton("退出");
	displayOneAnswer display;// 演示的线程的目标对象
	Thread displayThread;
	Answer answer = new Answer();
	int sleepTime = 1000;
	private final JButton upButton = new JButton("加速+");
	private final JButton downButton = new JButton("减速-");

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
		ImageIcon image = new ImageIcon(this.getClass().getResource("钻石.png"));
		this.setIconImage(image.getImage());// 窗口识别图标
		this.setVisible(true);
		// 游戏背景图片
		
		ImageIcon bg = new ImageIcon(this.getClass().getResource("背景1.jpg"));
		JLabel backLabel = new JLabel(bg);
		backLabel.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
		this.getLayeredPane().add(backLabel, new Integer(Integer.MIN_VALUE));

		
		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.setOpaque(false);// 内容面板设置透明
		this.setBounds(400, 100, 1080, 800);
		this.setTitle("独立钻石跳棋走法演示");
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
		jl1.setBackground(Color.blue);
		this.leftpanel.add(jl1);
		jl2.setForeground(Color.YELLOW);
		jl2.setBounds(189, 5, 179, 35);
		jl2.setVerticalAlignment(SwingConstants.TOP);
		jl2.setFont(new Font("宋体", Font.BOLD, 30));
		jl2.setBackground(Color.BLUE);
		this.leftpanel.add(jl2);

		contentPane.add(this.rightpanel, BorderLayout.CENTER);// 右边面板
		rightpanel.setOpaque(false);// 右边面板设置透明
		this.rightpanel.setLayout(chessboardlayer);// 右边布局9*9
		initChessBoard();
		initChessDot();
		initChessArmy();
		startButton.setForeground(Color.YELLOW);
		startButton.setBackground(Color.BLACK);
		startButton.setFont(new Font("楷体", Font.BOLD, 30));
		// 开始按钮
		startButton.addActionListener(this);
		this.leftpanel.add(startButton);

		stopButton.setForeground(Color.YELLOW);
		stopButton.setBackground(Color.BLACK);
		stopButton.setFont(new Font("楷体", Font.BOLD, 30));
		stopButton.setOpaque(false);// 按钮设置透明
		stopButton.addActionListener(this);
		this.leftpanel.add(stopButton);

		continueButton.setForeground(Color.YELLOW);
		continueButton.setBackground(Color.BLACK);
		continueButton.setFont(new Font("楷体", Font.BOLD, 30));
		continueButton.addActionListener(this);
		this.leftpanel.add(continueButton);

		backButton.setBackground(Color.BLACK);
		backButton.setForeground(Color.YELLOW);
		backButton.addActionListener(this);
		backButton.setFont(new Font("楷体", Font.BOLD, 30));

		// 加速按钮
		upButton.setForeground(Color.YELLOW);
		upButton.setBackground(Color.BLACK);
		upButton.setFont(new Font("楷体", Font.BOLD, 30));
		upButton.setOpaque(false);// 按钮设置透明
		upButton.addActionListener(this);
		leftpanel.add(upButton);
		// 减速按钮
		downButton.setForeground(Color.YELLOW);
		downButton.setBackground(Color.BLACK);
		downButton.setFont(new Font("楷体", Font.BOLD, 30));
		downButton.setOpaque(false);// 按钮设置透明
		downButton.addActionListener(this);
		leftpanel.add(downButton);
		this.leftpanel.add(backButton);

		answer.findOneAnswer();// 回溯法找到一个解
		display = new displayOneAnswer();// 演示的线程 的目标对象
		displayThread = new Thread(display);// 演示的线程

		this.validate(); // 注意要调用此方法，否则组件无法显示
	}

	// 内部类的方式执行演示线程
	public class displayOneAnswer implements Runnable {
		boolean stopFlag = false;// 设置线程结束标志
		int i = 0;

		public void setStopFlag(boolean flag) {
			stopFlag = flag;
		}

		public void run() {
			for (; i < Answer.top && stopFlag == false; i++) {
				// System.out.println("i=" + i);
				Step step = new Step();
				step = answer.mystack[i];
				chessArmy[step.sx + 1][step.sy + 1].setMoving();
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e1) {
				}
				chessArmy[step.sx + 1][step.sy + 1].setLife(false);
				chessArmy[(step.sx + step.tx) / 2 + 1][(step.sy + step.ty) / 2 + 1].setLife(false);
				jl1.setText("剩余子数:" + (31 - i));
				jl2.setText("已走步数:" + (i + 1));
				chessArmy[step.tx + 1][step.ty + 1].setMoving();
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e2) {
				}
				chessArmy[step.tx + 1][step.ty + 1].setLife(true);
			}

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			startButton.setEnabled(false);// 设置按钮失效，防止再次点击开始按钮引发的异常
			displayThread.start();

		} else if (e.getSource() == stopButton) {
			displayThread.suspend(); // 不建议用这个，但是这里用的话不会发生线程安全问题
		} else if (e.getSource() == continueButton) {
			displayThread.resume();//
		} else if (e.getSource() == upButton) {
			sleepTime *= 0.5;
		} else if (e.getSource() == downButton) {
			sleepTime *= 2;
		} else if (e.getSource() == backButton) {// 返回按扭
			display.setStopFlag(true);
			// displayThread.stop();
			this.dispose();
			// StartFrame frame=new StartFrame();
		}
	}

	public static void main(String[] _s) {
		DC dc = new DC();
	}

}
