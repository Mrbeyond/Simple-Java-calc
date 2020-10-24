import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gridbagger.Eval;
import gridbagger.GridBagger;

public class Calc {
  public String currentValue;
  public String lastValue = "0";
  public String currentSign, newValue = "";
  public String answer = "0";
  public boolean signUp, done = false, isNum, isOff, isOn;
  public List<String> recent = new ArrayList<String>();
  public static GridBagConstraints grid;
  public GridBagConstraints form;
  
  public String timerHolder;
  JTextField timeBox;
  JTextField showBox;
  JTextField ansBox;

  public static String plusMinus = "\u00B1";
  public static String inverse  = "X\u207B\u00B9";
  public static String sqr = "X\u00B2";
  public static String cbrt = "\u221B";
  public static String root = "\u221A";
  public static String xPowY = "X\u02B8";


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
    showBox = new JTextField();
    Font font;
    
    
    frame.setTitle("Calculator");
    frame.setLayout( new GridBagLayout());
    font = new Font("SansSerif", Font.BOLD, 15);
    timeBox.setFont(font);
    timeBox.setEditable(false);
    grid = GridBagger.pos(0,0,2,1,2);
    grid.insets = new Insets( 4,1,8,1);     
    frame.add(timeBox, grid);
    JButton off = new JButton("OFF");
    off.setBackground( Color.RED);
    off.addActionListener( new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(isOn){
          isOn = !isOn;
          showBox.setText("");
          ansBox.setText("");
        }
      }
    });
    frame.add(off, GridBagger.pos(4, 0));

    font = new Font("SansSerif", Font.BOLD, 20);
    showBox.setFont(font);
    showBox.setEditable(false);
    showBox.setBorder(BorderFactory.createMatteBorder(4,4,0,4,Color.gray));
    grid = GridBagger.emptyLay();
    grid = GridBagger.pos(0,1,5,2,2);
    grid.insets = new Insets( 0,2,0,2); 
    frame.add(showBox, grid);
    ansBox = new JTextField();
    font = new Font("SansSerif", Font.BOLD, 17);
    ansBox.setFont(font);
    ansBox.setEditable(false);
    ansBox.setBorder(BorderFactory.createMatteBorder(0,4,4,4,Color.gray));
    grid = GridBagger.emptyLay();
    grid = GridBagger.pos(0,3,5,1,2);
    grid.insets = new Insets( 0,2,10,2); 
    frame.add(ansBox, grid);


    JButton[] brackets = new JButton[10];
    String[] bracks = {"log", "alog", "sin", "cos", "tan", "\u221B", "\u221A", "asin", "acos", "atan"};
    
    JButton[] raisers = new JButton[5]; 
    String[] raises = {"X\u207B\u00B9", "X\u00B2", "X\u02B8", "ln", "n!" };

    JButton[] permuters = new JButton[10];
    String[] permutes = {"nPr", "nCr", "rand", "\u00B1", "DEL","(", ")", "E", "\u00B1", "DEL"};

    JButton[] numPrators = new JButton[20];
    String[] numeprators = {"7", "8", "9","AC", "ON", "4","5","6","*","/","1","2","3","+","-","0",".","%","ANS","=" };


    int bx=0, by=4;
    for(int i=0; i<brackets.length; i++){
      brackets[i] = new JButton(bracks[i]);
      brackets[i].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          if(!isOn) return;
          String val = e.getActionCommand();
          currentValue = done? "": showBox.getText();
          showBox.setText( currentValue + val+"(");
          recent.add(val+"(");
          done= false;
        }
      });
      frame.add(brackets[i], GridBagger.pos(bx,by,1,1,2));
      if(bx == 4){
        bx = 0;
        by++;
      }else{
        bx++;
      }
    }

    int rx=0, ry=6;
    for(int i=0; i<raisers.length; i++){
      raisers[i] = new JButton(raises[i]);
      raisers[i].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          if(!isOn) return;
          String val = e.getActionCommand();
          currentValue = done? "": showBox.getText();
          
          if(val.equals(inverse)){
            val = "^-1";
          }
          if(val.equals(sqr)){
            val = "^2";
          }
          if(val.equals(xPowY)){
            val = "^(";
          }
          if(val.equals("n!")){
            val = "!";
          }
          showBox.setText( currentValue + val);
          recent.add(val);
          done= false;
        }
      });
      frame.add(raisers[i], GridBagger.pos(rx,ry,1,1,2));
      rx++;
    }

    int px=0, py=7;
    for(int i=0; i<permuters.length; i++){
      permuters[i] = new JButton(permutes[i]);
      permuters[i].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          if(!isOn) return;
          String val = e.getActionCommand();
          currentValue = done? "": showBox.getText();

          if(val.equals("DEL")){
            if(currentValue.length() == 0) return;
            showBox.setText(currentValue.substring(0, currentValue.length() - recent.get(recent.size()-1).length()));
            recent.remove(recent.size()-1);
            return;
          }
          if(val.equals("E")){
            val = "(10^";
          }
          if(val.equals("rand")){
            val = String.valueOf(Math.random());
          }
          if(val.equals("nPr")){
            val = "P";
          }
          if(val.equals("nCr")){
            val = "C";
          }
          showBox.setText( currentValue + val);
          recent.add(val);
          done= false;
        }
      });
      frame.add(permuters[i], GridBagger.pos(px,py,1,1,2));
      if(px == 4){
        px = 0;
        py++;
      }else{
        px++;
      }
    }

    int nx=0, ny=9;
    for(int i=0; i<numPrators.length; i++){
      numPrators[i] = new JButton(numeprators[i]);
      numPrators[i].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          
          String val = e.getActionCommand();
          if(val.equals("ON")){
            isOn = true;
            showBox.setText("");
            ansBox.setText("");
            return;
          }
          if(!isOn) return;
          System.out.println(done + "  uu");
          currentValue = showBox.getText();
          if(val.equals("AC")){
            showBox.setText("");
            ansBox.setText("");
            return;
          }
          if(val.equals("=")){
            System.out.println("from calc val is " +currentValue);
            if(String.valueOf(currentValue.charAt(0)).equals("*") || String.valueOf(currentValue.charAt(0)).equals("/") ){
              ansBox.setText("Syntax Error");
              return;
            }
            answer = Eval.factorial(currentValue);
            if(answer.equals("Syntax Error")){
              ansBox.setText("Syntax Error");
              return;
            }
            if(answer.equals("Math Eroor")){
              ansBox.setText("Math Error");
              return;
            }
            answer = Eval.bracketEval(answer);
            if(answer.equals("Math Error")){
              ansBox.setText(answer);
            }else{
              ansBox.setText(answer);
              String exponent = new String(answer);
              Matcher match = Eval.rigex("\\d8*\\.+\\d*E\\d*", exponent);
              if(match.find()){
                 String g = match.group();
                //  int s = match.start();
                //  int l = match.end();
                 exponent = exponent.replace(g, g.replace("E", "*10^"));
              }
              System.out.println(exponent);
              Eval.setAnswer(exponent);

              done = true;
            }
            // System.out.println(done + "  mm");
            return;
          }
          // System.out.println(done + "  dd");
          if(done){
            if(val.equals("+") || val.equals("-") || val.equals("*") || val.equals("/")){
              currentValue = "ANS";
            }else{
              currentValue = "";
            }
          }
          showBox.setText( currentValue + val);
          recent.add(val);
          done= false;
        }
      });
      if(i == 3){
        numPrators[i].setBackground(Color.ORANGE);
      }
      if(i == 4){
        numPrators[i].setBackground(Color.GREEN);
      }
      frame.add(numPrators[i], GridBagger.pos(nx,ny,1,1,2));
      if(nx == 4){
        nx = 0;
        ny++;
      }else{
        nx++;
      }
    }







  //   JButton[] nums = new JButton[10];
  //   char[] numeric = {'7','8','9','4','5','6',
  //   '1','2','3','0'};
  //   int x = 0, y = 8;
  //   for(int i=0; i < nums.length ; i++){
  //     nums[i] = new JButton(String.valueOf(numeric[i]));
  //     nums[i].addActionListener(new ActionListener(){
  //       public void actionPerformed(ActionEvent e){
          
  //         String val = e.getActionCommand();
  //         currentValue = showBox.getText();
  //         if(currentValue.equals("0")) currentValue = "";
  //         if(signUp){
  //           newValue = val;
  //           isNum = false;
  //         }else{
  //           isNum = true;
  //           if(done){
  //             currentValue = "";
  //             newValue = val;
  //           }else{
  //             newValue = newValue+val;
  //           }            
  //         }
  //         showBox.setText(currentValue+val);
  //         signUp = false;
  //         done = false;
         
  //         System.out.println(signUp+" up "+done +" done");
  //         System.out.println(lastValue+" last "+newValue +" new");
  //       }
  //     });
  //     frame.add(nums[i], GridBagger.pos(x,y,1,1,2));
  //     // System.out.println(x+" and "+y);
  //     if((i+1) % 3 == 0){
  //       x = 0;
  //       y++;
  //     }else{
  //       x++;
  //     }      
  //   }
    
  //   JButton[] dotsButton = new JButton[3];
  //   char[] dots = {'.','%'};
  //   int dx = 1;
  //   for(int i=0; i<dots.length; i++){
  //     dotsButton[i] = new JButton(String.valueOf(dots[i]));
  //     dotsButton[i].addActionListener(new ActionListener(){
  //       public void actionPerformed(ActionEvent e){
  //         // System.out.println(e.getActionCommand());
  //         String val = e.getActionCommand();
  //       currentValue = showBox.getText();
  //         if(val.equals(".")){
  //           if(newValue.contains(".")) return;
  //           if(signUp){
  //             val = "0"+val;
  //           }else{
  //             // isNum = true;
  //             // if(done){
  //             //   currentValue = "";
  //             //   newValue = val;
  //             // }else{
  //             //   newValue = newValue+val;
  //             // }            
  //           }
  //           showBox.setText(currentValue+val);
  //           newValue = newValue + val;
  //         }else{

  //         }
  //         signUp= false;
  //       }
  //     });
  //     frame.add(dotsButton[i], GridBagger.pos(dx,11,1,1,2));
  //     dx++;
  //   }

  //   JButton[] aritPart = new JButton[8];
  //   String sqrt = String.valueOf("\u221A");
  //   String[] aritText  = {sqrt, "DEL","*", "/","+","-","ANS","="};

  //   int px=4, py=8;
  //   for(int i=0; i<aritPart.length; i++){
  //     aritPart[i] = new JButton(String.valueOf(aritText[i]));
  //     aritPart[i].addActionListener(new ActionListener(){
  //       public void actionPerformed(ActionEvent e){
  //         System.out.println(e.getActionCommand());
  //         String val = e.getActionCommand();
  //         currentValue = showBox.getText();
  //         if(val.equals(sqrt)){
  //           System.out.println(sqrt);
  //         }
  //         else if(val.equals("+") || val.equals("-") || val.equals("*") || val.equals("/")){
  //           String lCh = String.valueOf(currentValue.charAt(currentValue.length()-1));
  //           if(lCh.equals("+") || lCh.equals("-") || lCh.equals("*") || lCh.equals("/")){
  //             currentValue = currentValue.substring(0,currentValue.length()-1);
  //           }
  //           if(currentValue.equals("0")) newValue = "0";
  //           currentSign = val;
  //           if(!signUp && !done) lastValue = newValue;
  //           signUp = true;
  //           done = false;
  //           isNum = false;
  //           System.out.println(lastValue+" "+" last");
  //           showBox.setText(currentValue+val);
  //         } 
  //         else if(val.equals("DEL")){
  //           if(currentValue.length() == 1){
  //             showBox.setText("0");
  //             newValue = "";
  //             return;
  //           }
  //           if(newValue.equals("")){
  //             newValue = "";
  //           }
  //           showBox.setText(currentValue.substring(0, currentValue.length()-1));
  //           currentValue = showBox.getText();
  //           newValue = newValue.substring(0, newValue.length()-1);

  //           System.out.println(signUp+" up "+done +" done");
  //           System.out.println(lastValue+" last "+newValue +" new");
  //         }
  //         else if(val.equals("ANS")){
  //           showBox.setText(lastValue);
  //         }
  //         else if(val.equals("=")){
  //          compute();            
  //         }
  //       }
  //     });
  //     frame.add(aritPart[i], GridBagger.pos(px, py,1,1,2));
  //     if((i+1) % 2 == 0){
  //       px = 4;
  //       py++;
  //     }else{
  //       px++;
  //     } 
  //   }

    /** Cancel the thread here when the frame closes */
    frame.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
        timer.cancel();
      }
    });

    frame.setVisible(true);
    frame.pack();
  }


  // public void compute(){

  //   if(currentSign == null) return;
  //   if(signUp) return;    
  //   Double last = Double.valueOf(lastValue);
  //   Double curr = Double.valueOf(newValue);
  //   if(!isNum){
  //     if(currentSign.equals("+")) answer = String.valueOf(last+curr);
  //     if(currentSign.equals("/")) answer = String.valueOf(last/curr);
  //     if(currentSign.equals("*")) answer = String.valueOf(last*curr);
  //     if(currentSign.equals("-")) answer = String.valueOf(last-curr); 
  //   } else{
  //     answer = newValue;
  //   }
  //   ansBox.setText(answer);
  //   done = true;
  //   lastValue = answer;
  //   System.out.println(lastValue+" last "+newValue +" new");
  // }


  // /** Check if the string can parsed to double */
  // public static boolean isNumeric(String strNum) {
  //   if (strNum == null) {
  //       return false;
  //   }
  //   try {
  //     Double.parseDouble(strNum);
  //   } catch (NumberFormatException nfe) {
  //       return false;
  //   }
  //   return true;
  // }

  /** Converts the string to double  */
  public static double toDouble(String str){
    double d = Double.parseDouble(str);
    return d;
  }

  /** The regex pattern matcher created and parameterized */
  public static Matcher rigex(String regx, String str ){
    Pattern pat = Pattern.compile(regx);
    Matcher m = pat.matcher(str);
    return m;
  }

  /** The main exciter block begins here */
  public static void main(String[] args) {
    new Calc();
    // System.out.println(toDouble("E2"));
    // Math.min(50, 30);
  }  
}
