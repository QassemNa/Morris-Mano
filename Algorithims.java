/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Morris_Mano;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Dobler
 */
public class Algorithims {
    protected String PC ; // PC counter in hex
    protected String IR ; // Instruction Register in hex
    protected String Instruction ; // The instruction itself ( in monics ) for example LDA or STA
    protected String AC ="0000" ; // Accomulator register
    protected String DR ="0000"; // Data Rigister in hex
    protected String AR ; // ar in hex
    protected Memory M = new Memory() ; // the memory to be accessed M[AR]
    protected String InstructionCategory ; 
    protected String AddressingMode ;
    protected boolean I;
    protected String E="1";
    protected String S="1";
    protected String INPR;
    protected String OUTR;
    protected String IEN="0";//interrupt enable
    protected String FGI="0";//input flag
    protected String FGO="1";//output flag

    Algorithims(Memory m ) {this.M=m;}
    
    public void init () { // _____ to Initialize the algorithms___________  
       AR=PC;
       IR=M.getContent(AR);
       PC=this.INCPC_AR(PC);
       AR=IR.substring(1);
       if(IR.length()==4){
       
           if(IR.charAt(0)=='7'){
               Register();
           }
           else if(IR.charAt(0)=='F'||IR.charAt(0)=='f'){
               InputOutput();
           }
           else
               Memory_reference(); 
       }
       else
           Memory_reference();
       
    }
    public void print () {
    //    System.out.println("PC\tIR\tAC\tDR\tAR\tM[AR]\tCatagory\tAdMode");
        System.out.println(PC+"\t"+IR+"\t"+Instruction+"\t"+AC+"\t"+DR+"\t"+AR+"\t"+M.getContent(AR)+"\t"+InstructionCategory+"\t"+AddressingMode);
    }
    public void FillData (Vector row ) {
        row.add(PC); row.add(IR); row.add(Instruction); row.add(AC); row.add(DR); row.add(AR); row.add(M.getContent(AR)); row.add(InstructionCategory); row.add(AddressingMode);
    }
    public void setPC(String PC) {
        this.PC=PC;
    }
    
    public void Memory_reference () {
      this.InstructionCategory="Memory Reference";
     // System.out.println(AR);
     // System.out.println(IR);
       if(IR.charAt(0)=='0'||IR.charAt(0)=='1'||IR.charAt(0)=='2'||IR.charAt(0)=='3'||IR.charAt(0)=='4'||IR.charAt(0)=='5'||IR.charAt(0)=='6') I=false;
       else { I=true;  }
       if(I) { //idirect
            AR=M.getContent(AR);
            if(AR.length()>=4) AR=AR.substring(1);
            this.AddressingMode="Indirect";
       }
       else this.AddressingMode="Direct";
      
      
       //____________________BUN___________
       if(IR.charAt(0)=='4'||IR.charAt(0)=='c'||IR.charAt(0)=='C') { // branch unconditionally
           PC=AR;
           this.Instruction="BUN";
           }
       //__________________BSA_________________
       else if(IR.charAt(0)=='5'||IR.charAt(0)=='d'||IR.charAt(0)=='D') {
       M.Update(AR, PC);
       AR=this.INCPC_AR(AR);
       PC=AR; 
       this.Instruction="BSA";
       }
       //________________ISZ_____________________
       else if(IR.charAt(0)=='6'||IR.charAt(0)=='e'||IR.charAt(0)=='E') {
               DR=M.getContent(AR);
               DR=this.INCDR(DR);
               M.Update(AR, DR);
               if(DR.equals("0"))  PC=this.INCPC_AR(PC);
               this.Instruction="ISZ";
       }
       //______________AND_______________________________
       
       else if(IR.charAt(0)=='0'||IR.charAt(0)=='8') {
           DR = M.getContent(AR);
           AC=this.AND(AC, DR);
           this.Instruction="AND";
       }
       //________________ADD_________________________________
       else if( IR.charAt(0)=='1'||IR.charAt(0)=='9' ) {
           DR = M.getContent(AR);
           String r [] = this.ADD(AC,DR);
           AC= r[0];// Result 
           E= r[1]; // Carryout
           this.Instruction="ADD";
       }
       //________________LDA_____________
       else if( IR.charAt(0)=='2'||IR.charAt(0)=='A'||IR.charAt(0)=='a' ) {
           DR=M.getContent(AR);
           AC=DR;
           this.Instruction="LDA";
       }
       //_________________STA___________________
       else if( IR.charAt(0)=='3'||IR.charAt(0)=='B'||IR.charAt(0)=='b' ) {
       M.Update(AR, AC);
       this.Instruction="STA";
       }
       
    }
    
public void InputOutput () {

        this.AddressingMode="x";
        this.InstructionCategory="Input/Output";
       // this.IR=ir;
        
    if( "F800".equals(IR)||"f800".equals(IR)){
    
        this.Instruction="INP";
        String in;
        Scanner input = new Scanner(System.in);
        in=input.next();
        int dec=(int)in.charAt(in.length()-1);
        this.INPR=Integer.toHexString(dec);
        this.AC="0000";
        this.AC=this.INPR;
    
    }
    else if("F400".equals(IR)||"f400".equals(IR)){
    
        this.Instruction="OUT";
        String tmp;
        char[] hexch = new char[8];
        tmp=ConvertToBinary(this.AC);
        for(int i=0;i<8;i++){
            hexch[i]=tmp.charAt(i+8);
        }
        
        int hexint= Integer.parseInt(String.valueOf(hexch), 2);
        this.OUTR=Integer.toHexString(hexint);
        System.out.print((char)hexint);
    }
    else if("F200".equals(IR)||"f200".equals(IR)){
        this.Instruction="SKI";
        if("1".equals(this.FGI)){
            PC=this.INCPC_AR(PC);
        }
    }
    else if("F100".equals(IR)||"f100".equals(IR)){
        this.Instruction="SKO";
        if("1".equals(this.FGO)){
            PC=this.INCPC_AR(PC);
        }
    }
    else if("F080".equals(IR)||"f080".equals(IR)){
        this.Instruction="ION";
        this.IEN="1";
    }
    else if("F040".equals(IR)||"f040".equals(IR)){
        this.Instruction="IOF";
        this.IEN="0";
    }
        System.out.println("AC:"+this.AC);
    
    
    }
    
public void Register () {
        this.AddressingMode="x";
        this.InstructionCategory="Register-Reference";
       // IR=ir;  
    if( "7800".equals(IR)){ //CLA Clear AC
        this.Instruction="CLA";
        this.AC="0000";
    }
    else if("7400".equals(IR)){//CLE Clear E
        this.Instruction="CLE";
        this.E="0";
    }
    else if("7200".equals(IR)){//CMA Complement AC
        this.AC=Compliment(AC);
        this.Instruction="CMA";
    }
    else if("7100".equals(IR)){//CME Complement E
        this.Instruction="CME";
        if("0".equals(this.E)){
            this.E="1";
        }
        else
            this.E="0";
    }
    else if("7080".equals(IR)){//CIR circulate right ac wth E
        this.Instruction="CIR";
        this.AC=CIR(this.AC);
    }
    else if("7040".equals(IR)){////CIL circulate left ac wth E
        this.Instruction="CIL";
        this.AC=CIL(this.AC);
    }
    else if("7020".equals(IR)){//INC increment AC
        this.Instruction="INC";
        this.AC=this.INCDR(AC);
        
    }
    else if("7010".equals(IR)){//SPA
        this.Instruction="SPA";
        int hexint=Integer.parseInt(this.AC, 16);
        if(hexint>0){
            PC=this.INCPC_AR(PC);
        }
        
    }
    else if("7008".equals(IR)){//SNA
        this.Instruction="SNA";
        String hex=ConvertToBinary(this.AC);
        if(hex.charAt(15)==1){
            PC=this.INCPC_AR(PC);
        }
    }
    else if("7004".equals(IR)){//SZA
        this.Instruction="SZA";
        int hexint=Integer.parseInt(this.AC, 16);
        if(hexint==0){
               PC=this.INCPC_AR(PC);
        }
    }
    else if("7002".equals(IR)){//SZE
        this.Instruction="SZE";
        if("0".equals(this.E)){
            PC=this.INCPC_AR(PC);
        }
    }
    else if("7001".equals(IR)){//HLT
        this.Instruction="HLT";
        this.S="0";
    }
        //to sout rigestors
       // System.out.println("AC: "+this.AC);
       // System.out.println("E: "+this.E);
       // System.out.println("PC: "+this.PC);
    }
    
    
    public String Compliment(String hex){
        hex=ConvertToBinary(hex,15);
        char[] hexChars = hex.toCharArray();
        
        for(int i=0; i<hex.length();i++){    
            if(hex.charAt(i)=='0'){
                hexChars[i] = '1';
            }
            else {
                hexChars[i] = '0';
            }
        }
        
        int hexint= Integer.parseInt(String.valueOf(hexChars), 2);
        hex=Integer.toHexString(hexint);
        return hex;
    }
    
    public String CIR(String hex){
        hex=ConvertToBinary(hex,15);
        char[] hexch1 = hex.toCharArray();
        char[] hexch2 = new char[16];
        for(int i=0;i<16;i++){
            hexch2[i]='0';
        }
        String a=this.E;
        this.E=String.valueOf(hexch1[15]);
        for(int i=1;i<16;i++){
            hexch2[i]=hexch1[i-1];
        }
        hexch2[0]=a.charAt(0);
        
        int hexint= Integer.parseInt(String.valueOf(hexch2), 2);
        hex=Integer.toHexString(hexint);
        
        return hex;
    }
    
    public String CIL(String hex){
        hex=ConvertToBinary(hex,15);
        char[] hexch1 = hex.toCharArray();
        char[] hexch2 = new char[16];
        for(int i=0;i<16;i++){
            hexch2[i]='0';
        }
        String a=this.E;
        this.E=String.valueOf(hexch1[0]);
        for(int i=0;i<15;i++){
            hexch2[i]=hexch1[i+1];
        }
        hexch2[15]=a.charAt(0);
        
        int hexint= Integer.parseInt(String.valueOf(hexch2), 2);
        hex=Integer.toHexString(hexint);
        return hex;
    }
    
    public String INCPC_AR (String PC) {
        int intPC = Integer.parseInt(PC,16);
        intPC++;
        if(intPC>4095) intPC=0;
        return Integer.toHexString(intPC);
    }
    public String INCDR (String DR) {
        int intDR = Integer.parseInt(DR,16);
        intDR++;
        if(intDR>65535) intDR=0;
        return Integer.toHexString(intDR);
    }
    
    
    public String AND (String A , String B) {
       String ABin=this.ConvertToBinary(A);
       String BBin=this.ConvertToBinary(B);
       String result = "";
       for(int i = 0 ; i<= ABin.length()-1 ; i++) {
           if( ABin.charAt(i)==BBin.charAt(i) ) result=result+""+ABin.charAt(i);
           else result=result+"0";
       }
       return Integer.toHexString(Integer.parseInt(result,2));
       
       
    }
    public String[] ADD (String A,String B){
        int intA = Integer.parseInt(A,16);
        int intB = Integer.parseInt(B,16);
        int C= intA+intB;
        String CarryOut ;
        CarryOut="0";
        String result = this.ConvertToBinary(Integer.toHexString(C));
        CarryOut=result.charAt(0)+"";
        
        char tmp[]=result.toCharArray();
        tmp[0]='0';
        result=String.valueOf(tmp);
        String r[]={Integer.toHexString(Integer.parseInt(result,2)),CarryOut};
        return r;
        
    }
    public String ConvertToBinary ( String Hex ) {
        Hex = Integer.toBinaryString(Integer.parseInt(Hex,16));
        char str[]= {'0', '0','0','0','0', '0','0','0','0', '0','0','0','0', '0','0','0','0' };
       int i = 16 ; 
       int len = Hex.length()-1;
       while(i>=0 && len >=0 ) {
       str[i]=Hex.charAt(len);
       len--;
       i--;
       }
       String c =""; 
       for (i=0; i<=16; i++) { c=c+""+str[i]; }
       return c;
    }
    public String ConvertToBinary ( String Hex, int bit ) {
        Hex = Integer.toBinaryString(Integer.parseInt(Hex,16));
        char str[]= {'0', '0','0','0','0', '0','0','0','0', '0','0','0','0', '0','0','0','0' };
       int i = bit ; 
       int len = Hex.length()-1;
       while(i>=0 && len >=0 ) {
       str[i]=Hex.charAt(len);
       len--;
       i--;
       }
       String c =""; 
       for (i=0; i<=bit; i++) { c=c+""+str[i]; }
       return c;
    }
    
}
