package uml.hotel.utils;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CalendarFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PCalendar model;
	private JLabel yearAndMonth;
	private CalendarFrameDelegate delegate;
	
	private JButton[] days = new JButton[42];
	
	public CalendarFrame(PCalendar model) {
		// TODO Auto-generated constructor stub
		this.model = model;
		model.setView(this);
		
		setTitle("万年历");
		setSize(400, 400);
		init();
		
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	private void init() {
		getContentPane().setLayout(null);
		
		JLabel[] weeks = {
				new JLabel("周日"),
				new JLabel("周一"),
				new JLabel("周二"),
				new JLabel("周三"),
				new JLabel("周四"),
				new JLabel("周五"),
				new JLabel("周六")
		};
		
		for (int i = 0; i < weeks.length; i++) {
			weeks[i].setBounds(40 + i * 50, 50, 50, 30);
			getContentPane().add(weeks[i]);
		}
		
		JButton lastMonth = new JButton("<<");
		lastMonth.setBounds(125, 20, 30, 20);
		lastMonth.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				model.lastMonth();
			}
		});
		getContentPane().add(lastMonth);
		
		JButton nextMonth = new JButton(">>");
		nextMonth.setBounds(245, 20, 30, 20);
		getContentPane().add(nextMonth);
		nextMonth.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				model.nextMonth();
			}
		});
		
		yearAndMonth = new JLabel("");
		yearAndMonth.setBounds(165, 20, 80, 20);
		getContentPane().add(yearAndMonth);
		
		//设置天数
		for (int i = 0; i < days.length; i++) {
			days[i]= new JButton("");
//			days[i].setBorder(null);
			days[i].setBounds(40 + (i % 7) * 50, 90 + (i / 7) * 50, 20, 20);
			getContentPane().add(days[i]);
			
			days[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String day = ((JButton)arg0.getSource()).getText();
					if (delegate != null) {
						int dayNum = Integer.parseInt(day);
						String formattedDay = new DecimalFormat("00").format(dayNum);
						String dateString = yearAndMonth.getText() + "-" + formattedDay;
						delegate.calendarDidSelectWithDayName(dateString);
					}
				}
			});
		}
	}
	
	public void showCalendar() {
		for (int i = 0; i < days.length; i++) {
			days[i].setText("");
			days[i].setVisible(false);
			days[i].setForeground(Color.BLACK);
		}
		
		String str = model.getYearAndMonth();
		int firstDayOfWeek = model.getFirstDayOfWeek();
		int maxDay = model.getMaxDay();
		
		yearAndMonth.setText(str);
		Calendar c = Calendar.getInstance();
		int currentDay = c.get(Calendar.DAY_OF_MONTH);
		int currentMonth = c.get(Calendar.MONTH) + 1;
		
		int month = model.getShowingMonth(yearAndMonth.getText());
		
		for (int i = 0; i < maxDay; i++) {
			int day = i + 1;
			days[i + firstDayOfWeek - 1].setText("" + day);
			days[i + firstDayOfWeek - 1].setVisible(true);
			
			if (day == currentDay && month == currentMonth) {
				days[i + firstDayOfWeek - 1].setForeground(Color.red);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PCalendar c = new PCalendar();
		CalendarFrame frame = new CalendarFrame(c);
		frame.setVisible(true);
		frame.showCalendar();
	}

	public CalendarFrameDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(CalendarFrameDelegate delegate) {
		this.delegate = delegate;
	}

}
