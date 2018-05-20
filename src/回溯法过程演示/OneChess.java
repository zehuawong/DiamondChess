package 回溯法过程演示;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class OneChess extends JButton {
	private boolean life;//方格按钮上是否为棋子的标识
	public int xp;//xp和yp标识棋子的位置信息
	public int yp;
	public OneChess(boolean sl, int x, int y) {
		setLife(sl);
		xp = x;
		yp = y;
	}
	public void setLife(boolean l) {// 设置棋子是不是已走
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
	public void setMoving() {// 正在移动的棋子，即鼠标点中的棋
		ImageIcon image = new ImageIcon(this.getClass().getResource("蓝色钻石.png"));
		this.setIcon(image);
	}
	public boolean getLife() {
		return life;
	}

}