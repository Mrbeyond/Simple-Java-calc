package gridbagger;
import java.util.regex.*;  

public class Eval {
  public static double ans;
  public static int y,z;
  public static Matcher mm;
  public static String mode = "deg";
      
  public static Matcher rigex(String regx, String str ){
    Pattern pat = Pattern.compile(regx);
    Matcher m = pat.matcher(str);
    return m;
  }
      
  public static double deg(double val){
    if(mode.equals("rad")) return val;
    return val*(Math.PI/180);
  }      
      
  public static String bracketEval(){
    // String br = "4-9+(-5)(-5)";
    String br = "4+3((2*2^2)(2+4)^2)+9^(-2)";
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
              
        mm = rigex("atan$", left);
        if(mm.find()){
          ANS = Math.atan(deg(ANS));
          mm = rigex("\\d+\\.?\\d*atan$", left);
          if(mm.find()){
            left = left.substring(0,left.length()-3);
            br = left + "*" +ANS+right;
            System.out.println(2000);
          }else{
            mm = rigex("\\)atan$", left);
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
        
        mm = rigex("asin$", left);
        if(mm.find()){
          ANS = Math.asin(deg(ANS));
          mm = rigex("\\d+\\.?\\d*asin$", left);
          if(mm.find()){
            left = left.substring(0,left.length()-3);
            br = left + "*" +ANS+right;
            System.out.println(2000);
          }else{
            mm = rigex("\\)asin$", left);
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
              
        mm = rigex("acos$", left);
        if(mm.find()){
          ANS = Math.acos(deg(ANS));
          mm = rigex("\\d+\\.?\\d*acos$", left);
          if(mm.find()){
            left = left.substring(0,left.length()-3);
            br = left + "*" +ANS+right;
            System.out.println(2000);
            }else{
              mm = rigex("\\)acos$", left);
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
    System.out.println(mathEval(br));    
    return br;
  }
      
  public static double mathEval(String str){
    Character[] s = {'^','/','*','+','-'}; 
    String ca = new String(str.replace("+-", "-"));
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
                
                  
        if(r.equals("") || l.equals("")) continue;
        System.out.println(l + "  ----  "+r);
        if(s[i].equals('+')){                      
          ans = Double.valueOf(l)+ Double.valueOf(r);
          // System.out.println(ans);
        }
          
        if(s[i].equals('*')){
          ans = Double.valueOf(l)* Double.valueOf(r);
          // System.out.println(ans);
        }
        if(s[i].equals('/')){
          ans = Double.valueOf(l)/ Double.valueOf(r);
          // System.out.println(ans);
        }
        if(s[i].equals('-')){
          ans = Double.valueOf(l)-Double.valueOf(r);
          // System.out.println(ans);
        }
          
        if(s[i].equals('^')){
          ans = Math.pow(Double.valueOf(l),Double.valueOf(r));
          //  System.out.println(ans);
        }
            
        String xx = ca.substring(0,p-l.length());
        String yy = ca.substring(p+r.length()+1);
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
    
    mathEval("2^3");
  //   System.out.println(mathEval("4"));
  // bracketEval();
        
    
  }
}
