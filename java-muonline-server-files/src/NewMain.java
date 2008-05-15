/*
 * NewMain.java
 *
 * Created on 6 grudzie 2007, 13:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Miki
 */
public class NewMain {
    
    /** Creates a new instance of NewMain */
    public NewMain() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       String t= "STRING nazwa (10,20)";
       String[] tt=t.split(" ");
       System.out.println(tt.length);
       for (int c=0; c<tt.length;c++)
           System.out.println(tt[c]);
    }
   
}
