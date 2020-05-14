/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Morris_Mano;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Dobler
 */

public class CO {

    /**
     * @param args the command line arguments
     */
    protected static Memory m;
    public static void main(String[] args) {
      m = new Memory();
      m.Automate();
      m.Automate();
      m.PaseAddress("000");
      Scanner input = new Scanner(System.in);
    try {
			Scanner scanner = new Scanner(new File("C:\\Users\\ME\\Documents\\NetBeansProjects\\CO\\src\\co\\Input.txt"));
			while (scanner.hasNextLine()) {
				m.AddMemoryBlock(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
       
      // m.print();
   
      
    
      System.out.println();
      System.out.println();
      System.out.println();
      
      
      Algorithims Algo = new Algorithims(m);
      Algo.setPC("001");
      m.print();
      System.out.println("PC"+"\t"+"IR"+"\t"+"IRN"+"\tAC\tDR\tAR\tM[AR]\tInstruction Category\tADMode");
              
     /* while("1".equals(Algo.S)){
         Algo.init();
          Algo.print();
      } */
       System.out.println();
      System.out.println();
      System.out.println();
    //  m.print();
      //m.Automate();
     // Vector a = new Vector ();
     // m.FillData(a);
  
     
    // System.out.println(m.getContent("00f"));
    }
    
}
