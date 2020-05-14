/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Morris_Mano;

/**
 *
 * @author Dobler
 */
public class Block {
    public String Address; //address in hex
    public String Content; //content in hex
    Block(String Address , String Content) {
     this.Address=Address;
     this.Content=Content;   
    }
    
}
