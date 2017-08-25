package Discount;

import java.awt.JobAttributes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class DiscountEvent {
	
	
	public DiscountEvent(ServerPos.Server ser){
		ser.btn3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					int price = Integer.parseInt(ser.tf1.getText()); // text안에 있는 String을 a라는 변수에 integer로 담아준다.
					new Discount.DiscountDialog(ser, price).setVisible(true);
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(ser, "할인할 목록이 없습니다");
				}
			


			}
		}); // 할인 버튼에 대한 이벤트
	}
}
