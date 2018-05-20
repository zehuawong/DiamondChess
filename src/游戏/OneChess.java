package 游戏;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class OneChess extends JButton implements MouseListener {
	private boolean life;
	private boolean isout;
	public int xp;
	public int yp;

	public OneChess(boolean sl, int x, int y) {
		setLife(sl);
		xp = x;
		yp = y;
		this.addMouseListener(this); // 当前对象添加鼠标事件监视器
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

	@SuppressWarnings("static-access")	 
	public void setBoard() { // 设置边界为白色
		isout = true;
		setBackground(Color.WHITE);
	}

	public boolean getLife() {
		return life;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (isout) { // 如果是边界的，不是棋子的话就直接返回
			return;
		}
		// -----movingflag判断moving的两个边沿
		if (!DC.movingflag) { // 选中单个子
			if (DC.chessboard[xp][yp] && getLife()) {
				DC.oldx = xp;
				DC.oldy = yp;
				DC.movingflag = true;
				setMoving();
			}
		} else { // 成功移动1个子
			if (xp == DC.oldx && yp == DC.oldy) { // 放弃1个子，计数1步
				setLife(true);
				DC.movingflag = false;

			} else if (DC.chessboard[xp][yp] && getLife()) { // 点到其他子身上，切换选中的子
				DC.chessArmy[DC.oldx][DC.oldy].setLife(true);

				DC.oldx = xp;
				DC.oldy = yp;

				setMoving();
			}
			if (DC.chessboard[xp][yp] && !getLife()) { // 点中了灰色点，即可以放新的棋子的位置
				DC.newx = xp;
				DC.newy = yp;
				if (DC.canMove()) { // 如果可以符合游戏规则，可以移动棋子
					DC.doMove(); // 移动棋子
					DC.restcount--; // 剩余棋子数
					DC.jl1.setText("剩余子数:" + DC.restcount);
					DC.steps++; // 步数增加1
					DC.jl2.setText("当前步数:" + DC.steps);
					DC.oldx = xp;
					DC.oldy = yp;
					setMoving();

				}

			}
		}
		return;
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

}