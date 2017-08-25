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
import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class UsePoint extends JDialog{
	PointDialog parent;
	public PointDBManager manager = new PointDBManager();
	
	private JLabel  contactLabel= new JLabel("연락처");
	private JTextField contactText = new JTextField();
	private JLabel  nameLabel= new JLabel("이름");
	private JLabel  pointLabel= new JLabel("포인트");
	private JLabel willSpend = new JLabel("사용하실 포인트 : ");
	private JTextField pointText = new JTextField();
	private JButton btnInquiry = new JButton("조회");
	private JButton btnUse = new JButton("사용");
	private JPanel panelUse = new JPanel(new GridLayout(4, 2));
	private JPanel usePoint = new JPanel(new GridLayout(2, 1));
	private int returnpoint;
	public int pointPrice;
	public void compInit(){
		this.panelUse.add(contactLabel);
		this.panelUse.add(contactText);
		this.panelUse.add(nameLabel);
		this.panelUse.add(pointLabel);
		this.usePoint.add(willSpend);
		this.usePoint.add(pointText);
		this.panelUse.add(usePoint);
		this.panelUse.add(btnInquiry);
		this.panelUse.add(btnUse);
		this.add(panelUse);
	}

	public void eventInit(){
		this.btnInquiry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int contact = Integer.parseInt(contactText.getText());
				try {
					ArrayList<MembershipPoint> result = manager.selectPoint(contact); 
					// MembershipPoint형을 가진 Arraylist형 result는 dbmanager에게 contact를 넘겨주어서 
					for( MembershipPoint tmp : result){
						nameLabel.setText(tmp.getName());
						pointLabel.setText(Integer.toString(tmp.getPoint()));

					}
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
			}
		});
		this.btnUse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					int point = Integer.parseInt(pointText.getText());
					int contact = Integer.parseInt(contactText.getText()); // 텍스트필드에 적힌 포인트와 연락처를 가져온다.

					ArrayList<MembershipPoint> result = manager.selectPoint(contact);
					for( MembershipPoint tmp : result){
						if( tmp.getPoint() > Integer.parseInt(pointText.getText())) {
							returnpoint = tmp.getPoint() - Integer.parseInt(pointText.getText()); // Integer.부분을 나중에 Server의 결제 금액 중 일부로 받아오는 식을 만들어야됨.
							pointPrice = Integer.parseInt(parent.parent.self.tf1.getText()) // dialog의 parent인 server에 있는 tf 내의 값을 불러오는 것
									- Integer.parseInt(pointText.getText());
							parent.parent.self.tf1.setText(""+pointPrice); // 새로 계산한 값을 부여하는 것.
							
							int insertResult = manager.updatePoint(returnpoint,contact);
							if(insertResult > 0) {
								JOptionPane.showMessageDialog(parent,"포인트가 사용되었습니다.");
								dispose();					//현재 프레임 종료 
							}else {
								JOptionPane.showMessageDialog(parent,"에러가 발생했습니다.");
							}
						} else {
							JOptionPane.showMessageDialog(parent, "보유한 포인트가 부족합니다.");
						}
					}// 밖에서 선언해준 
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(parent,"오류 발생! 관리자에게 연락하세요.");
				} 

			}
		});
	}
	public UsePoint(PointDialog parent) {
		this.parent = parent;
		this.setSize(500,500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(parent);
		this.compInit();
		this.eventInit();
		this.setVisible(true);
	}

}
