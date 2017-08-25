package Membership;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;

public class AddPoint extends JDialog{
	PointDialog parent;
	ServerPos.Server server;
	private PointDBManager manager = new PointDBManager();


	private JLabel nameLabel = new JLabel("이름");
	private JTextField nameText = new JTextField();
	private JLabel  contactLabel= new JLabel("연락처");
	private JTextField contactText = new JTextField();
	private JLabel  pointLabel= new JLabel("포인트");
	private JTextField pointText = new JTextField();
	private JButton btnAdd = new JButton("적립");
	private JPanel panelAdd = new JPanel(new GridLayout(3, 2));
	private int returnpoint;
	private final JLabel empty = new JLabel();

	public void compInit(){
		//		this.panelAdd.add(nameLabel);
		//		this.panelAdd.add(nameText);
		this.panelAdd.add(contactLabel);
		this.panelAdd.add(contactText);
		this.panelAdd.add(pointLabel);
		this.panelAdd.add(pointText);
		this.pointText.setText("" +Integer.parseInt(parent.parent.self.tf1.getText())/ 100 *2);
		
		panelAdd.add(empty);
		this.panelAdd.add(btnAdd);
		getContentPane().add(panelAdd);
	}

	public void eventInit(){
		this.btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int point = Integer.parseInt(pointText.getText());
					int contact = Integer.parseInt(contactText.getText()); // 텍스트필드에 적힌 포인트와 연락처를 가져온다.

					ArrayList<MembershipPoint> result = manager.selectPoint(contact);
					for( MembershipPoint tmp : result){
						int estimatedPoint = Integer.parseInt(parent.parent.self.tf1.getText())/ 100 *2;
						returnpoint = tmp.getPoint() + estimatedPoint; // Integer.부분을 나중에 Server의 결제 금액 중 일부로 받아오는 식을 만들어야됨. 
					}// 밖에서 선언해준 
					
					
					int insertResult = manager.updatePoint(returnpoint,contact);
					if(insertResult > 0) {
						JOptionPane.showMessageDialog(parent,"포인트가 적립되었습니다.");
						dispose();					//현재 프레임 종료 
					}else {
						JOptionPane.showMessageDialog(parent,"없는 번호입니다.");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(parent,"오류 발생! 관리자에게 연락하세요.");
				} 
			}
		});
	}
	public AddPoint(PointDialog parent) {
		this.parent = parent;
		this.setSize(300,150);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(parent);
		this.compInit();
		this.eventInit();
		this.setVisible(true);
	}

}
