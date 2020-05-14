package Morris_Mano;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dobler
 */
public class Table extends JFrame {
    private JTable InvTable = new JTable() ; 
    private JPanel InvitePanel = new JPanel();
    
    Table () {
     super("Update");
     setSize(400, 300);
     setResizable(true);
     setVisible(true);
        setDefaultCloseOperation(Table.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
       setLocationRelativeTo(null);
       InvitePanel.add(new JScrollPane(InvTable),BorderLayout.CENTER);
                 Vector data= new Vector();
                 for (int i = 0 ; i <30 ; i++ ) {
                     Vector row = new Vector ();
                     for (int j = 0 ; j <=15 ; j++) {  row.add(Integer.toHexString(j));   }
                     data.add(row);
                 }
                 
                 
                 Vector column= new Vector();
                 for(int i= 0 ; i <= 15 ; i++) {
                     column.add("Number"+i);
                 }
                 
                 System.out.print(column.toString());
                 System.out.print(data.toString());
                 DefaultTableModel InvModel = new DefaultTableModel(data,column);
                 InvTable.setModel(InvModel);
                 InvTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                 InvTable.setRowSelectionAllowed(true);
                 InvTable.setBackground(Color.WHITE);
                 InvTable.setOpaque(true);
                 InvTable.setDefaultEditor(Object.class, null); // to endable editing
                 InvTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                 //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
           
                 //add(InvitePanel);
    
    }
     public void ValidatePanel () {
                InvitePanel.invalidate();
                InvitePanel.validate();
                InvitePanel.repaint(); 
                invalidate();
                validate();
                repaint(); 
    
    }
}
