package ServerPos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class BtnEvent {
	Server ser;

	public BtnEvent(Server ser) {
		this.ser = ser;
		
		ser.btn[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					for (Order tmp : ser.foodlist.get("1")) {
						ser.model.addRow(new Object[] {tmp.getMenu(), tmp.getQuantity(), tmp.getPrice() * tmp.getQuantity()});
						ser.total += tmp.getPrice() * tmp.getQuantity();
					}
					ser.tf1.setText("" + ser.total);
					ser.tableColor = 0;
					ser.listdel = "1";
					ser.total = 0;
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(ser, "주문이없습니다");
				}

			}
		});

		ser.btn[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					for (Order tmp : ser.foodlist.get("2")) {
						ser.model.addRow(new Object[] { tmp.getMenu(), tmp.getQuantity(), tmp.getPrice() * tmp.getQuantity() });
						ser.total += tmp.getPrice() * tmp.getQuantity();
					}
					ser.tf1.setText("" + ser.total);
					ser.tableColor = 1;
					ser.listdel = "2";
					ser.total = 0;
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(ser, "주문이없습니다");
				}
			}
		});
	}

}