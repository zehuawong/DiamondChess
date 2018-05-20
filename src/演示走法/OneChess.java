package 演示走法;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class OneChess extends JButton {
	private boolean life;
	public int xp;
	public int yp;

	public OneChess(boolean sl, int x, int y) {
		setLife(sl);
		xp = x;
		yp = y;
		// this.addMouseListener(this); // 当前对象添加鼠标事件监视器
	}

	public void setLife(boolean l) { // 设置棋子是不是已走
		life = l;
		if (l) {
			setBackground(Color.black);
			ImageIcon image = new ImageIcon(this.getClass().getResource("红色钻石.png"));
			this.setIcon(image);

		} else {
			setBackground(Color.black);
			this.setIcon(null);
		}
	}

	public void setMoving() { // 正在移动的棋子，即鼠标点中的棋
		ImageIcon image = new ImageIcon(this.getClass().getResource("蓝色钻石.png"));
		this.setIcon(image);
	}

	public void setBoard() { // 设置边界
		setBackground(Color.WHITE);
	}

	public boolean getLife() {
		return life;
	}

}