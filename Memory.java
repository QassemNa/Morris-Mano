/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Morris_Mano;
import java.util.ArrayList;
import java.util.Vector;
/**
 *
 * @author Dobler
 */
public class Memory {
    private ArrayList<Block> Data ;
    private int PaseAddress;
    
    Memory () {}
    
    public void Automate() {
       Data = new ArrayList();
    this.PaseAddress("0");
    for(int i=0; i< 4096 ; i++ ) {
        Block a = new Block(Integer.toHexString( i), Integer.toHexString(i));
        Data.add(a);
    }
    }
    public void FillData (Vector data) {
        Vector row = new Vector();
        int ctr = 0;
        for ( Block a : Data ) {
            String hex = a.Content;
                     if(hex.length()==0) hex=hex.toUpperCase();
                     else if(hex.length()==1) hex="00"+hex.toUpperCase();
                     else if(hex.length()==2) hex="0"+hex.toUpperCase();
                     else hex=hex.toUpperCase();
            row.add(hex);
            ctr++;
            if(ctr==256) { 
                ctr=0;
                data.add(row);
                row=new Vector();
                    }
        }
        System.out.println(data.toString());
    
    }
    public void PaseAddress(String hex) {
        this.PaseAddress=Integer.parseInt(hex, 16); // converting string hex into integer
    }
    public void AddMemoryBlock (String content) {
        String address = Integer.toHexString(this.PaseAddress);
        Block a = new Block(address,content);
        this.Update(address, content);
        PaseAddress++;
    }
    public String getContent(String Address) {
        String st="";
        int AddressInt =  Integer.parseInt(Address, 16);
        int AddressInt2 ;
        for ( Block a : Data ) {
            AddressInt2=Integer.parseInt(a.Address,16);
            if(AddressInt2==AddressInt) st= a.Content;
        }
    
        return st;
        
    }
    public void print() { int c =0;
        System.out.println("No\tAddress\tContent");
        for (Block a : Data) { System.out.println((++c)+"\t"+a.Address+"\t"+a.Content); }
    }
    public void Update(String Address,String Content) {
        int AddressInt =  Integer.parseInt(Address, 16);
        int AddressInt2 ;
        
        for (Block a : Data) { 
            AddressInt2=Integer.parseInt(a.Address,16);
            if(AddressInt2==AddressInt)
        {                       a.Content=Content;
                                break;
                                    } 
        }
    }
    
}
