package Calculate;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Calculate extends JDialog{
	
	ServerPos.Server ser;
	private JLabel lb1 = new JLabel("���� �ݾ�");
	private JLabel lb2 = new JLabel("���� �ݾ�");
	private JTextField money = new JTextField();
	private JTextField calculateMoney = new JTextField();
	private JButton btn1 = new JButton("���� �Ϸ�");
	private JButton btn2 = new JButton("�ݱ�");
	
	private void comInit(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints(); 
		c.insets = new Insets(10,10,0,0);
		
		money.setText(ser.money+"");
		c.gridy = 1;
		this.add(lb1, c);
		this.add(money, c);
		this.money.setPreferredSize(new Dimension(100, 25));
		
		int mon = ser.money - 100000;
		calculateMoney.setText(mon+"");
		c.gridy = 2;
		this.add(lb2, c);
		this.add(calculateMoney, c);
		this.calculateMoney.setPreferredSize(new Dimension(100, 25));
		
		c.gridy = 3;
		this.add(btn1, c);
		this.add(btn2, c);
	}
	
	private void event(){
		btn1.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				if(ser.money>100000){
					ser.money = 100000;
					money.setText(ser.money+"");
					calculateMoney.setText("0");
				}else{
					JOptionPane.showMessageDialog(ser, "10���� �����Դϴ�");
				}
				
			}
		});
		
		btn2.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	public Calculate(ServerPos.Server ser){
		this.ser = ser;
		this.setSize(300,200);
		this.setLocationRelativeTo(ser);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.comInit();
		this.event();
		this.setVisible(true);
	}
}
