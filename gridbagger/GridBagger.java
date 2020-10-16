package gridbagger;
import java.awt.*;
public class GridBagger {
  public static GridBagConstraints plan;

  /** Simple x and y */
  public static GridBagConstraints pos(int x, int y){
    plan  = new GridBagConstraints();
    plan.gridx = x;
    plan.gridy = y;
    return plan;
  }
  /** Simple coord and xy span with fill property */
  public static GridBagConstraints pos(int x, int y,  int w, int h, int f){
    plan  = new GridBagConstraints();
    plan.gridx = x;
    plan.gridy = y;
    plan.gridwidth = w;
    plan.gridheight = h;
    plan.fill = f;
    return plan;
  }

  /** Simple coord with weightX */
  public static GridBagConstraints pos(int x, int y, int W){
    plan  = new GridBagConstraints();
    plan.gridx = x;
    plan.gridy = y;
    plan.weightx = W;
    return plan;
  }

  /** Coords with insect */
  public static GridBagConstraints pos(int x, int y, Insets i){
    plan  = new GridBagConstraints();
    plan.gridx = x;
    plan.gridy = y;
    plan.insets = i;
    return plan;
  }

  /** Coord and inner padding */
  public static GridBagConstraints pos(int x, int y, int px, int py){
    plan  = new GridBagConstraints();
    plan.gridx = x;
    plan.gridy = y;
    plan.ipadx = px;
    plan.ipady = py;
    return plan;
  }

  public static void main(String[] args) {
    //  new GridBagger();
    System.out.println("bag");

  }
}
