import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import gridbagger.GridBagger;

public class Calc {

  public String lastValue;
  public String currentSign;
  public String newValue;

  public GridBagConstraints form;
  
  public String timerHolder;

  /** A timer task class for putting the time on a thread */
  public class Interval extends TimerTask{
    public Calendar lend;
    public void run(){
      lend =  Calendar.getInstance();
      timerHolder = lend.getTime().toString();
      timerHolder = timerHolder.substring(0, timerHolder.indexOf("WAT")); 
      System.out.println(timerHolder);
    }
  }

  /** The constructor block starts here */
  public Calc(){
    Timer timer = new Timer();
    TimerTask intval = new Interval();
    timer.schedule(intval, 0,1000);
    
    JFrame frame = new JFrame();
    JTextField timeBox = new JTextField(timerHolder);
    JTextField showBox = new JTextField("0");
    Font font;
    
    
    frame.setTitle("Calculator");
    frame.setLayout( new GridBagLayout());
    font = new Font("SansSerif", Font.BOLD, 12);
    timeBox.setFont(font);
    timeBox.setEditable(false);
    frame.add(timeBox, GridBagger.pos(0,0,2,1,2));
    font = new Font("SansSerif", Font.BOLD, 20);
    showBox.setFont(font);
    showBox.setEditable(false);
    frame.add(showBox, GridBagger.pos(0,1,3,3,2));
    
    JButton[] nums = new JButton[9];
    char[] numeric = {'7','8','9','4','5','6','1','2','3'};
    int x = 0, y = 4;
    for(int i=0; i < nums.length ; i++){
      nums[i] = new JButton(String.valueOf(numeric[i]));
      nums[i].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            System.out.println(e.getActionCommand());
            String val = e.getActionCommand();
            String currentValue = showBox.getText();
            if (currentValue.equals("0")){
              currentValue = "";
            }
            showBox.setText(currentValue+val);
        }
      });
      frame.add(nums[i], GridBagger.pos(x,y));
      System.out.println(x+" and "+y);
      if((i+1) % 3 == 0){
        x = 0;
        y++;
      }else{
        x++;
      }      
    }
    
    JButton[] dotsButton = new JButton[3];
    char[] dots = {'0','.','%'};
    int dx = 0;
    for(int i=0; i<dots.length; i++){
      dotsButton[i] = new JButton(String.valueOf(dots[i]));
      dotsButton[i].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          System.out.println(e.getActionCommand());
          String val = e.getActionCommand();
          String currentValue = showBox.getText();
          if(val.equals(".")){
            if(currentValue.contains(".")) return;
            showBox.setText(currentValue+val);
          }else if(val.equals("0")){
            if(currentValue.equals("0")) return;
            showBox.setText(currentValue+val);
          }
        }
      });
      frame.add(dotsButton[i], GridBagger.pos(dx,7));
      dx++;
    }
    /** Cancel the thread here when the frame closes */
    frame.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
        timer.cancel();
      }
    });

    frame.setVisible(true);
    frame.pack();
  }

  /** The main exciter block begins here */
  public static void main(String[] args) {
    new Calc();
  }

  
}