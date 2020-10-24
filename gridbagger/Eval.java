package gridbagger;
import java.util.regex.*;  

public class Eval {
  public static double ans;
  public static int y,z;
  public static Matcher mm;
  public static String mode = "deg";
  private static String answer = "0";
  public static String plusMinus = "\u00B1";
  public static String inverse  = "X\u207B\u00B9";
  public static String sqr = "X\u00B2";
  public static String cbrt = "\u221B";
  public static String root = "\u221A";
  public static String xPowY = "X\u02B8";

  public static String getAnswer() {
    return answer;
  }

  public static void setAnswer(String answer) {
    Eval.answer = answer;
  }
      
  public static Matcher rigex(String regx, String str ){
    Pattern pat = Pattern.compile(regx);
    Matcher m = pat.matcher(str);
    return m;
  }
      
  public static double deg(double val){
    if(mode.equals("rad")) return val;
    return val*(Math.PI/180);
  }
  
  public static int factorialize(int n){
    if(n > 1){
      return n*factorialize(n-1);
    } else{
      return 1;
    }
  }
  
  public static String factorial(String value){
     String str = new String(value);
     Matcher mm;
     String fact;
     while(str.lastIndexOf("!") > -1){
      int locate= str.lastIndexOf("!");
      String lSide = str.substring(0, locate+1);
      String rSide = str.substring(locate+1);       
      mm = rigex("!$", lSide);
      if(mm.find()){
        mm = rigex("\\d+\\.+\\d*!$", lSide);
        if(mm.find()){
          str = "Math Error";
          break;
        }else{
          mm = rigex("\\d+!$", lSide);
          if(mm.find()){
            String part = mm.group();
            int exact = Integer.valueOf(part.substring(0,part.length()-1));            
            if(exact == 0){
              fact = "0.0";
            }else{
              fact = String.valueOf(Double.valueOf(factorialize(exact)));
            }
            if(lSide.length() == part.length()){
              str = fact+rSide;
            }else{
              if(lSide.charAt((lSide.length()-part.length()-1)) == ')'){
                lSide = lSide.replace("\\d+!$", fact);
                str = lSide+"*"+rSide;
              }else{
                lSide = lSide.replace("\\d+!$", fact);
                str = lSide+rSide;
                // System.out.println("top is"+ lSide +" ryt "+rSide+ " loc "+locate);
              }
            }
          }else{
            str = "Syntax Error";
            break;
          }

        }
      }
     }
     System.out.println(str);
     return str;
  }
      
  public static String bracketEval(String evals){
    String br = new String(evals);
    System.out.println("The bracket is :"+ br);
    // String br = "4-9+(-5)(-5)";
    // String br = "4+3((2*2^2)(2+4)^2)+9^(-2)";
    // String br = "4+3+sin(90)sin(30)sin(60)cos(60)+2+log(100)";
    double ANS;
    while(br.lastIndexOf("(") > -1){
      for(int i= 0; i<br.length(); i++ ){
        if(!String.valueOf(br.charAt(i)).equals("(")) continue;
        int fp = br.lastIndexOf("(");
        int lp = br.indexOf(")", fp);
        String focus = br.substring(fp+1,lp);
        ANS = mathEval(focus);
        String left = br.substring(0,fp);
        String right = br.substring(lp+1);
        mm = rigex("\\d+\\.?\\d*$", left);
              
        if(mm.find()){
          br = left+"*"+ANS+right;
        }else{
          mm = rigex("[)]$", left);
          if(mm.find()){
            br = left+"*"+ANS+right;
          }else{
            br = left+ANS+right;
          }
        }

        mm = rigex("√$", left);
        if(mm.find()){
          ANS = Math.sqrt(ANS);
          mm = rigex("\\d+\\.?\\d*√$", left);
          if(mm.find()){
            left = left.substring(0, left.length()-1);
            br = left+"*"+ANS+right;            
            System.out.println(2000);
          }else{
            mm = rigex("\\)√$", left);
            if(mm.find()){
              left = left.substring(0, left.length()-1);
              br = left+"*"+ANS+right;            
              System.out.println(2001);
            }else{
              left = left.substring(0,left.length()-1);
              br = left+ANS+right;
              System.out.println(20002);
            }
          }
        }
              
        mm = rigex("atan$", left);
        if(mm.find()){
          if(-1 > ANS || ANS >1){
            br = "Math Error";
            break;
          }
          if(mode.equals("deg")){
            ANS = Math.atan(ANS)*(180/Math.PI);
          }else{

            ANS = Math.atan(ANS);
          }
          mm = rigex("\\d+\\.?\\d*atan$", left);
          if(mm.find()){
            left = left.substring(0,left.length()-4);
            br = left + "*" +ANS+right;
            System.out.println(2000);
          }else{
            mm = rigex("\\)atan$", left);
            if(mm.find()){
              left = left.substring(0,left.length()-4);
              br = left+"*"+ANS+right;
              System.out.println(20001);
            }else{
              left = left.substring(0,left.length()-4);
              br = left+ANS+right;
              System.out.println(20002);
            }
          }
        }
        
        mm = rigex("asin$", left);
        if(mm.find()){
          if( -1 > ANS || ANS >1){
            br = "Math Error";
            System.out.println("here");
            break;
          }
          if(mode.equals("deg")){
            ANS = Math.asin(ANS)*(180/Math.PI);
          }else{

            ANS = Math.asin(ANS);
          }
          mm = rigex("\\d+\\.?\\d*asin$", left);
          if(mm.find()){
            left = left.substring(0,left.length()-4);
            br = left + "*" +ANS+right;
            System.out.println(2000);
          }else{
            mm = rigex("\\)asin$", left);
            if(mm.find()){
              left = left.substring(0,left.length()-4);
              br = left+"*"+ANS+right;
              System.out.println(20001);
            }else{
              left = left.substring(0,left.length()-4);
              br = left+ANS+right;
              System.out.println(20002 + "  ==asss");
            }
          }
        }
              
        mm = rigex("acos$", left);
        if(mm.find()){
          if( -1 > ANS || ANS >1){
            br = "Math Error";
            break;
          }
          if(mode.equals("deg")){
            ANS = Math.acos(ANS)*(180/Math.PI);
          }else{

            ANS = Math.acos(ANS);
          }
          mm = rigex("\\d+\\.?\\d*acos$", left);
          if(mm.find()){
            left = left.substring(0,left.length()-4);
            br = left + "*" +ANS+right;
            System.out.println(2000);
            }else{
              mm = rigex("\\)acos$", left);
              if(mm.find()){
                left = left.substring(0,left.length()-4);
                br = left+"*"+ANS+right;
                System.out.println(20001);
              }else{
                left = left.substring(0,left.length()-4);
                br = left+ANS+right;
                System.out.println(20002);
              }
            }
          }

          mm = rigex("sin$", left);
        if(mm.find()){
          ANS = Math.sin(deg(ANS));
          mm = rigex("\\d+\\.?\\d*sin$", left);
          if(mm.find()){
            left = left.substring(0,left.length()-3);
            br = left + "*" +ANS+right;
            System.out.println(2000);
          }else{
            mm = rigex("\\)sin$", left);
            if(mm.find()){
              left = left.substring(0,left.length()-3);
              br = left+"*"+ANS+right;
              System.out.println(20001);
            }else{
              left = left.substring(0,left.length()-3);
              br = left+ANS+right;
              System.out.println(20002);
            }
          }
        }
              
        mm = rigex("cos$", left);
        if(mm.find()){
          ANS = Math.cos(deg(ANS));
          mm = rigex("\\d+\\.?\\d*cos$", left);
          if(mm.find()){
            left = left.substring(0,left.length()-3);
            br = left + "*" +ANS+right;
            System.out.println(2000);
          }else{
            mm = rigex("\\)cos$", left);
            if(mm.find()){
              left = left.substring(0,left.length()-3);
              br = left+"*"+ANS+right;
              System.out.println(20001);
            }else{
              left = left.substring(0,left.length()-3);
              br = left+ANS+right;
              System.out.println(20002);
            }
          }
        }
              
        mm = rigex("tan$", left);
        if(mm.find()){
          ANS = Math.tan(deg(ANS));
          mm = rigex("\\d+\\.?\\d*tan$", left);
          if(mm.find()){
            left = left.substring(0,left.length()-3);
            br = left + "*" +ANS+right;
            System.out.println(2000);
          }else{
            mm = rigex("\\)tan$", left);
            if(mm.find()){
              left = left.substring(0,left.length()-3);
              br = left+"*"+ANS+right;
              System.out.println(20001);
            }else{
              left = left.substring(0,left.length()-3);
              br = left+ANS+right;
              System.out.println(20002);
            }
          }
        }
              
          mm = rigex("log$", left);
          if(mm.find()){
            ANS = Math.log10(ANS);
            mm = rigex("\\d+\\.?\\d*log$", left);
            if(mm.find()){
              left = left.substring(0,left.length()-3);
              br = left + "*" +ANS+right;
              System.out.println(2000);
            }else{
              mm = rigex("\\)log$", left);
              if(mm.find()){
                left = left.substring(0,left.length()-3);
                br = left+"*"+ANS+right;
                System.out.println(20001);
              }else{
                left = left.substring(0,left.length()-3);
                br = left+ANS+right;
                System.out.println(20002);
              }
            }
          }
              
        mm = rigex("ln$", left);
        if(mm.find()){
          ANS = Math.log(ANS);
          mm = rigex("\\d+\\.?\\d*ln$", left);
          if(mm.find()){
            left = left.substring(0,left.length()-3);
            br = left + "*" +ANS+right;
            System.out.println(2000);
          }else{
            mm = rigex("\\)ln$", left);
            if(mm.find()){
              left = left.substring(0,left.length()-3);
              br = left+"*"+ANS+right;
              System.out.println(20001);
            }else{
              left = left.substring(0,left.length()-3);
              br = left+ANS+right;
              System.out.println(20002);
            }
          }
        }              
      }
      System.out.println(br);
    }    
    if(br.equals("Math Error")){
      return br;
    }
    
    System.out.println(mathEval(br));
    // double eval = mathEval(br);
    // return br;
    return String.valueOf(mathEval(br));
  }
      
  public static double mathEval(String str){
    Character[] s = {'^','/','*','+','-'}; 
    String ca = new String(str.replace("+-", "-"));
    ca = ca.replace("ANS", getAnswer());
    System.out.println("the string is : " + ca);
    String left,right="";
    for(int i = 0; i<s.length; i++){
      for(int v =0; v<ca.length(); v++){
        int p = ca.lastIndexOf(s[i]);
        if(p < 0) continue;
        String r="", l ="";
        boolean minus = false;
        left = ca.substring(0,p);
        right = ca.substring(p+1);
        
        mm= rigex("\\-\\d+\\.?\\d*$", left);
        if(mm.find()){
          minus = true;
          l = mm.group();
          y = mm.start();
          System.out.println(l+ "   === minus");
        } else{
          mm = rigex("\\d+\\.?\\d*$",left);
          if(mm.find()){
            l = mm.group();
            y = mm.start();
          }
        }        
                  
        if(s[i].equals('^')){
          mm = rigex("\\-?\\d+\\.?\\d*",right);
          if(mm.find()){
            r = mm.group();
            z = p+r.length();
          }
        }else{
          mm = rigex("\\d+\\.?\\d*",right);
          if(mm.find()){
            r = mm.group();
            z = p+r.length();
          }
        }
                
        Double dL = Double.valueOf(l) , dR = Double.valueOf(r);  
        if(r.equals("") || l.equals("")) continue;
        System.out.println(l + "  ----  "+r);
        if(s[i].equals('+')){                      
          ans = dL+ dR;
          // System.out.println(ans);
        }
          
        if(s[i].equals('*')){
          ans = dL * dR;
          // System.out.println(ans);
        }
        if(s[i].equals('/')){
          ans = dL/ dR;
          // System.out.println(ans);
        }
        if(s[i].equals('-')){
          ans = dL-dR;
          // System.out.println(ans);
        }
          
        if(s[i].equals('^')){
          ans = Math.pow(dL,dR);
          //  System.out.println(ans);
        }
            
        String xx = ca.substring(0,p-l.length());
        String yy = ca.substring(p+r.length()+1);
        // String ansToStr = String.valueOf(ans);
        // Matcher match = rigex("\\d8*\\.+\\d*E\\d*", ansToStr);
        // if(match.find()){
        //     String g = match.group();
        //   //  int s = match.start();
        //   //  int l = match.end();
        //     ansToStr = g.replace(g, g.replace("E", "*10^"));
        // }
        if(minus){
          ca =  xx + "+"+ans+yy;
          ca= ca.replace("+-","-");
        }else{
          ca = xx + ans + yy;
          ca= ca.replace("+-","-");                    
        }
        System.out.println(ca);
        
      }
    }
    
    System.out.println(ca);
    return Double.valueOf(ca);
  }
  
  public static void main(String args[]) {
    
    // mathEval("2*2");
  //   System.out.println(mathEval("4"));
  // bracketEval("asin(5)");
  factorial("5+5!");
        
    
  }
}
