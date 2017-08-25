package ServerPos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class PopupEvent {
	Server ser;

	public PopupEvent(Server ser) {
		this.ser = ser;
		
		ser.btn[0].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.getModifiers() == me.BUTTON3_MASK) {// BUTTON3_MASK는 마우스 오른쪽 버튼 눌린 것
					ser.pMenu.show(ser.btn[0], me.getX(), me.getY());
					ser.num = "1";
				}
			}
		});

		ser.btn[1].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.getModifiers() == me.BUTTON3_MASK) {// BUTTON3_MASK는 마우스 오른쪽 버튼 눌린 것
					ser.pMenu.show(ser.btn[1], me.getX(), me.getY());
					ser.num = "2";
				}
			}
		});

		ser.menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FoodList(ser, ser.num).setVisible(true);
			}
		});

		ser.pMenu.add(ser.menu);
		ser.add(ser.pMenu);
	}
}
