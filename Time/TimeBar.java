package Time;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;

public class TimeBar {
	
	private ServerPos.Server ser;
	JStatusBar statusBar = new JStatusBar();
	TimerThread timerThread;
    JLabel leftLabel = new JLabel("BLACKSTONE");
    final JLabel dateLabel = new JLabel();
    final JLabel timeLabel = new JLabel();
    
	public TimeBar(ServerPos.Server ser){
		this.ser = ser;
	
		statusBar.setLeftComponent(leftLabel);
		dateLabel.setHorizontalAlignment(JLabel.CENTER);
        statusBar.addRightComponent(dateLabel);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        statusBar.addRightComponent(timeLabel);
        
		ser.add(statusBar);
		statusBar.setBounds(0, 658, 1191, 30);
		
		timerThread = new TimerThread(dateLabel, timeLabel);
		timerThread.start();
	}
}
