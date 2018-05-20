package ��Ϸ;

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
		this.addMouseListener(this); // ��ǰ�����������¼�������
	}

	public void setLife(boolean l) { // ���������ǲ�������
		life = l;
		if (l) {
			setBackground(Color.black);
			ImageIcon image = new ImageIcon(this.getClass().getResource("��ɫ��ʯ.png"));
			this.setIcon(image);

		} else {
			setBackground(Color.black);
			this.setIcon(null);
		}
	}

	public void setMoving() { // �����ƶ������ӣ��������е���
		ImageIcon image = new ImageIcon(this.getClass().getResource("��ɫ��ʯ.png"));
		this.setIcon(image);
	}

	@SuppressWarnings("static-access")	 
	public void setBoard() { // ���ñ߽�Ϊ��ɫ
		isout = true;
		setBackground(Color.WHITE);
	}

	public boolean getLife() {
		return life;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (isout) { // ����Ǳ߽�ģ��������ӵĻ���ֱ�ӷ���
			return;
		}
		// -----movingflag�ж�moving����������
		if (!DC.movingflag) { // ѡ�е�����
			if (DC.chessboard[xp][yp] && getLife()) {
				DC.oldx = xp;
				DC.oldy = yp;
				DC.movingflag = true;
				setMoving();
			}
		} else { // �ɹ��ƶ�1����
			if (xp == DC.oldx && yp == DC.oldy) { // ����1���ӣ�����1��
				setLife(true);
				DC.movingflag = false;

			} else if (DC.chessboard[xp][yp] && getLife()) { // �㵽���������ϣ��л�ѡ�е���
				DC.chessArmy[DC.oldx][DC.oldy].setLife(true);

				DC.oldx = xp;
				DC.oldy = yp;

				setMoving();
			}
			if (DC.chessboard[xp][yp] && !getLife()) { // �����˻�ɫ�㣬�����Է��µ����ӵ�λ��
				DC.newx = xp;
				DC.newy = yp;
				if (DC.canMove()) { // ������Է�����Ϸ���򣬿����ƶ�����
					DC.doMove(); // �ƶ�����
					DC.restcount--; // ʣ��������
					DC.jl1.setText("ʣ������:" + DC.restcount);
					DC.steps++; // ��������1
					DC.jl2.setText("��ǰ����:" + DC.steps);
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