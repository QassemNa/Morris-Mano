/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Morris_Mano;

import java.util.Hashtable;

/**
 *
 * @author Dobler
 */

public class Translator {
        private String Instruction ;
        Translator () {}
        
        
        public String Tranlate (String Instruction) {
            String Inst=Instruction.toLowerCase();
            boolean I = false;
            //_____________Memory Refernce________________
            Hashtable<String, String> 
            mr = new Hashtable<String, String>(); 
            mr.put("and","0"); mr.put("and[","8");
            mr.put("add","1"); mr.put("add[","9");
            mr.put("lda","2"); mr.put("lda[","A" );
            mr.put("sta","3"); mr.put("sta[","B");
            mr.put("bun","4"); mr.put("bun[","C");
            mr.put("bsa","5"); mr.put("bsa[","D");
            mr.put("isz","6"); mr.put("isz[","E");

            Hashtable<String, String> 
            ior = new Hashtable<String, String>(); 
            ior.put("cla", "7800");
            ior.put("cle", "7400");
            ior.put("cma", "7200");
            ior.put("cme", "7100");
            ior.put("cir", "7080");
            ior.put("cil", "7040");
            ior.put("inc", "7020");
            ior.put("spa", "7010");
            ior.put("sna", "7008");
            ior.put("sza", "7004");
            ior.put("sze", "7002");
            ior.put("hlt", "7001");
            
            ior.put("inp", "F800");
            ior.put("out", "F400");
            ior.put("ski", "F200");
            ior.put("sko", "F100");
            ior.put("ion", "F080");
            ior.put("iof", "F040");
            if(Inst.length()==6) { // direct
                 Inst=mr.get(Inst.substring(0, 3))+Inst.substring(3);
             I = true;
            }
            else if (Inst.length()==8) { // indirect
            Inst=mr.get(Inst.substring(0, 4))+Inst.substring(4, 7);
             I = true; 
            }
            //_________Register Refrence / IO ______
            else if(Inst.length()==3) {
             Inst=ior.get(Inst);
             I = true;
            }
            
            if (I) return Inst;
            else return null;
        }
        
        public static void main(String[] args) { 
            Translator a = new Translator ();
            String s = a.Tranlate("and[132]");
            System.out.println(s);
        
        }
}

