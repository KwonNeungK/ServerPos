package ServerPos;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Membership.PointDialog;

public class Server extends JFrame{
	
	public int money = 100000;
	
	//매출 현황
	String num = null;
	String tabelend;
	String listdel;
	int tableColor;

	final PopupMenu pMenu = new PopupMenu("Edit"); //final로 해야 이벤트 핸들러 실행가능
	MenuItem menu = new MenuItem("메뉴보기");

	int total = 0;
	private MultiThread mt;

	public Server self = this;
	HashMap<String, ArrayList<Order>> foodlist = new HashMap<String, ArrayList<Order>>();

	DefaultTableModel model;
	JTable list;
	JScrollPane js;

	private String[] menulist = new String[] {"음식","수량","가격"};

	private JLabel log = new JLabel();
	
	JButton[] btn = new JButton[15];
	JPanel pan = new JPanel(new GridLayout(3, 5));

	private JLabel la1 = new JLabel("총   합 : ");
	private JLabel la2 = new JLabel("받은돈 : ");
	private JLabel la3 = new JLabel("잔   돈 : ");

	public JTextField tf1 = new JTextField();   //총합금
	JTextField tf2 = new JTextField();	 //받은돈	
	JTextField tf3 = new JTextField();   //잔돈

	private JButton complete = new JButton("완료");
	private JButton cancel = new JButton("취소");
	private JPanel pan2 = new JPanel(new GridLayout(1, 2));
	
	public JButton btn1 = new JButton(new ImageIcon("1.jpg"));    //정산
	private JButton btn2 = new JButton(new ImageIcon("2.jpg"));  //매출 현황
	public JButton btn3 = new JButton(new ImageIcon("33.jpg"));  //할인
	private JButton btn4 = new JButton(new ImageIcon("4.jpg"));  //포인트
	private JButton btn5 = new JButton("영수증");  //영수증
	private JButton btn6 = new JButton("종료");   //종료
	private JPanel pan3 = new JPanel(new GridLayout(1, 6));
	private JPanel pan4 = new JPanel(new GridLayout(3, 1));
	private JPanel pan5 = new JPanel(new GridLayout(3, 1));



	private void table(){  //테이블배치
		new Table(self);
	}

	public void arrlist(String a, ArrayList<Order> b){
		foodlist.put(a, b);
	}
	public ArrayList<Order> menulist(String num){
		return foodlist.get(num);  // 주문한 음식 리스트
	}

	public HashMap<String, ArrayList<Order>> getFooldList(){
		return foodlist;  //추가 주문 확인 메서드
	}


	private void allEvent(){
		new BtnEvent(self);  //테이블 번호
		new PopupEvent(self);  // 판업창 
		new Discount.DiscountEvent(self); //할인
		new Calculate.CalculateEvent(self);
		
		
		btn6.addActionListener(new ActionListener() {//종료
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	private void time(){
		new Time.TimeBar(self);
	}


	private void textEvent(){   //계산 및 테이블 초기화
		tf2.addActionListener(new ActionListener() {  //계산
			public void actionPerformed(ActionEvent e) {
				int num1 = Integer.parseInt(tf1.getText()); //총합금
				int num2 = Integer.parseInt(tf2.getText()); //받은돈
				int num3 = num2 - num1;  //잔돈
				tf3.setText(""+num3);
			}
		});
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PointDialog(self).setVisible(true);
			}
		});
		complete.addActionListener(new ActionListener() { //완료 버튼
			public void actionPerformed(ActionEvent e) {
				int del = model.getRowCount();
				for(int i = 0;i<del;i++){
					model.removeRow(0);
				}
				total = Integer.parseInt(tf1.getText());
				money += total;
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");

				btn[self.tableColor].setBackground(null);
				foodlist.remove(listdel);
			}
		});

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int del = model.getRowCount();
				for(int i = 0;i<del;i++){
					model.removeRow(0);
				}
				tf1.setText("");
			}
		});

	}

	private void comInit(){
		this.setLayout(null);
		model = new DefaultTableModel(menulist,0);
		list = new JTable(model);
		js = new JScrollPane(list);

		this.add(js);
		this.js.setBounds(851, 101, 339, 300);
		//계산 테이블 넓이 조절
		this.list.getColumnModel().getColumn(0).setPreferredWidth(250);
		this.list.getColumnModel().getColumn(1).setPreferredWidth(40);

		//계산 테이블 센터 정렬
		DefaultTableCellRenderer tableRender = new DefaultTableCellRenderer();
		tableRender.setHorizontalAlignment(JLabel.CENTER);
		list.getColumnModel().getColumn(0).setCellRenderer(tableRender);
		list.getColumnModel().getColumn(1).setCellRenderer(tableRender);
		list.getColumnModel().getColumn(2).setCellRenderer(tableRender);


		//---------총합 받은돈 잔돈
		la1.setHorizontalAlignment(JLabel.LEFT);
		la2.setHorizontalAlignment(JLabel.LEFT);
		la3.setHorizontalAlignment(JLabel.LEFT);

		this.pan4.add(la1);
		this.la1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		this.pan4.add(la2);
		this.la2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		this.pan4.add(la3);
		this.la3.setFont(new Font("맑은 고딕", Font.BOLD, 20));

		this.pan5.add(tf1);
		this.pan5.add(tf2);
		this.pan5.add(tf3);

		this.add(pan4);
		this.add(pan5);
		this.pan4.setBounds(855, 401, 80, 150);  //총합 받은돈 잔돈 묶음 
		this.pan5.setBounds(940, 403, 250, 150);	 //총합 받은돈 잔돈 필드 묶음
		//------------완료 취소
		this.pan2.add(complete);
		this.pan2.add(cancel);
		this.add(pan2);  
		this.pan2.setBounds(853, 555, 337, 60);  //완료 취소
		//-----------정산 영수증 종료 할인
		this.pan3.add(btn1); //정산
		this.pan3.add(btn2); //매출현황
		this.pan3.add(btn3); //할인
		this.pan3.add(btn4); //포인트
		this.pan3.add(btn5); //영수증
		this.pan3.add(btn6); //종료
		this.add(pan3);
		this.pan3.setBounds(0, 616, 1191, 40); //정산 영수증 결산 할인 묶음
		//---로고
		this.add(log);
		this.log.setBounds(0, 0, 1190, 100);
		this.log.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.log.setOpaque(true); //Opaque값을 true로 미리 설정해 주어야 배경색이 적용된다
		this.log.setBackground(Color.white);


		

	}



	public Server() throws Exception{
		this.setTitle("BLACKSTONE");
		this.setSize(1201,715);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.time();
		this.table();
		this.allEvent();
		this.textEvent();
		this.comInit();
		this.setVisible(true);

		ServerSocket server = new ServerSocket(20000);

		while (true) {
			System.out.println("서버가 연결을 대기하는 중 입니다.");
			Socket sock = server.accept(); // 네트워크에서 20000포트로 들어오는 데이터 듣기
			System.out.println(sock.getInetAddress() + "님이 접속 했습니다.");
			mt = new MultiThread(self,sock);
			mt.start();
		}
	}
	public static void main(String[] args) throws Exception{
//		try {
//			// select Look and Feel
//			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
//			// start application
			new Server();
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//		}
	} // end main

}