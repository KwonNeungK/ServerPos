package ServerPos;
import javax.swing.JButton;

public class Table {
	Server ser;
	
	public Table(Server ser){
		this.ser = ser;
		
		for(int i = 0; i<ser.btn.length;i++){
			ser.btn[i] = new JButton((i+1)+"번테이블");
		}
		
		for(int i = 0;i<ser.btn.length;i++){
			ser.pan.add(ser.btn[i]);
		}
		ser.add(ser.pan);
		ser.pan.setBounds(1, 101, 850, 515);
		
	}
}
