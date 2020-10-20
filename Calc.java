import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gridbagger.GridBagger;

public class Calc {
  public String currentValue;
  public String lastValue = "0";
  public String currentSign;
  public String newValue = "";
  public String answer;
  public boolean signUp, done, isNum, isOff, isOn;

  public GridBagConstraints form;
  
  public String timerHolder;
  JTextField timeBox;
  JTextField showBox;
  JTextField ansBox;

  /** A timer task class for putting the time on a thread */
  public class Interval extends TimerTask{
    public Calendar lend;
    public void run(){
      lend =  Calendar.getInstance();
      timerHolder = lend.getTime().toString();
      timerHolder = timerHolder.substring(0, timerHolder.indexOf("WAT")); 
      timeBox.setText(timerHolder);
      // System.out.println(timerHolder);
    }
  }

  /** The constructor block starts here */
  public Calc(){
    timeBox = new JTextField(timerHolder);

    /** The interval timer for the time display schedule */
    Timer timer = new Timer();
    /** Instance of Timer task */
    TimerTask intval = new Interval();
    timer.schedule(intval, 0,1000);
    
    JFrame frame = new JFrame();
    showBox = new JTextField("0");
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
    showBox.setBorder(BorderFactory.createMatteBorder(4,4,0,4,Color.gray));
    frame.add(showBox, GridBagger.pos(0,1,5,2,2));
    ansBox = new JTextField("hghgh");
    font = new Font("SansSerif", Font.BOLD, 17);
    ansBox.setFont(font);
    ansBox.setEditable(false);
    ansBox.setBorder(BorderFactory.createMatteBorder(0,4,4,4,Color.gray));
    frame.add(ansBox, GridBagger.pos(0,3,5,1,2));
    
    JButton[] nums = new JButton[10];
    char[] numeric = {'7','8','9','4','5','6','1','2','3','0'};
    int x = 0, y = 8;
    for(int i=0; i < nums.length ; i++){
      nums[i] = new JButton(String.valueOf(numeric[i]));
      nums[i].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          
          String val = e.getActionCommand();
          currentValue = showBox.getText();
          if(currentValue.equals("0")) currentValue = "";
          if(signUp){
            newValue = val;
            isNum = false;
          }else{
            isNum = true;
            if(done){
              currentValue = "";
              newValue = val;
            }else{
              newValue = newValue+val;
            }            
          }
          showBox.setText(currentValue+val);
          signUp = false;
          done = false;
         
          System.out.println(signUp+" up "+done +" done");
          System.out.println(lastValue+" last "+newValue +" new");
        }
      });
      frame.add(nums[i], GridBagger.pos(x,y,1,1,2));
      // System.out.println(x+" and "+y);
      if((i+1) % 3 == 0){
        x = 0;
        y++;
      }else{
        x++;
      }      
    }
    
    JButton[] dotsButton = new JButton[3];
    char[] dots = {'.','%'};
    int dx = 1;
    for(int i=0; i<dots.length; i++){
      dotsButton[i] = new JButton(String.valueOf(dots[i]));
      dotsButton[i].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          // System.out.println(e.getActionCommand());
          String val = e.getActionCommand();
        currentValue = showBox.getText();
          if(val.equals(".")){
            if(newValue.contains(".")) return;
            if(signUp){
              val = "0"+val;
            }else{
              // isNum = true;
              // if(done){
              //   currentValue = "";
              //   newValue = val;
              // }else{
              //   newValue = newValue+val;
              // }            
            }
            showBox.setText(currentValue+val);
            newValue = newValue + val;
          }else{

          }
          signUp= false;
        }
      });
      frame.add(dotsButton[i], GridBagger.pos(dx,11,1,1,2));
      dx++;
    }

    JButton[] aritPart = new JButton[8];
    String sqrt = String.valueOf("\u221A");
    String[] aritText  = {sqrt, "DEL","*", "/","+","-","ANS","="};

    int px=4, py=8;
    for(int i=0; i<aritPart.length; i++){
      aritPart[i] = new JButton(String.valueOf(aritText[i]));
      aritPart[i].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          System.out.println(e.getActionCommand());
          String val = e.getActionCommand();
          currentValue = showBox.getText();
          if(val.equals(sqrt)){
            System.out.println(sqrt);
          }
          else if(val.equals("+") || val.equals("-") || val.equals("*") || val.equals("/")){
            String lCh = String.valueOf(currentValue.charAt(currentValue.length()-1));
            if(lCh.equals("+") || lCh.equals("-") || lCh.equals("*") || lCh.equals("/")){
              currentValue = currentValue.substring(0,currentValue.length()-1);
            }
            if(currentValue.equals("0")) newValue = "0";
            currentSign = val;
            if(!signUp && !done) lastValue = newValue;
            signUp = true;
            done = false;
            isNum = false;
            System.out.println(lastValue+" "+" last");
            showBox.setText(currentValue+val);
          } 
          else if(val.equals("DEL")){
            if(currentValue.length() == 1){
              showBox.setText("0");
              newValue = "";
              return;
            }
            if(newValue.equals("")){
              newValue = "";
            }
            showBox.setText(currentValue.substring(0, currentValue.length()-1));
            currentValue = showBox.getText();
            newValue = newValue.substring(0, newValue.length()-1);

            System.out.println(signUp+" up "+done +" done");
            System.out.println(lastValue+" last "+newValue +" new");
          }
          else if(val.equals("ANS")){
            showBox.setText(lastValue);
          }
          else if(val.equals("=")){
           compute();            
          }
        }
      });
      frame.add(aritPart[i], GridBagger.pos(px, py,1,1,2));
      if((i+1) % 2 == 0){
        px = 4;
        py++;
      }else{
        px++;
      } 
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


  public void compute(){

    if(currentSign == null) return;
    if(signUp) return;    
    Double last = Double.valueOf(lastValue);
    Double curr = Double.valueOf(newValue);
    if(!isNum){
      if(currentSign.equals("+")) answer = String.valueOf(last+curr);
      if(currentSign.equals("/")) answer = String.valueOf(last/curr);
      if(currentSign.equals("*")) answer = String.valueOf(last*curr);
      if(currentSign.equals("-")) answer = String.valueOf(last-curr); 
    } else{
      answer = newValue;
    }
    ansBox.setText(answer);
    done = true;
    lastValue = answer;
    System.out.println(lastValue+" last "+newValue +" new");
  }


  /** Check if the string can parsed to double */
  public static boolean isNumeric(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {
      Double.parseDouble(strNum);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
  }

  /** Converts the string to double  */
  public static double toDouble(String str){
    double d = Double.parseDouble(str);
    return d;
  }

  public static Matcher rigex(String regx, String str ){
    Pattern pat = Pattern.compile(regx);
    Matcher m = pat.matcher(str);
    return m;
  }

  /** The main exciter block begins here */
  public static void main(String[] args) {
    new Calc();
    System.out.println(toDouble("-4+8"));
    // Math.min(50, 30);
  }  
}
