package com.Soham.MSProject.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import com.Soham.MSProject.Input.CreateInputPairs;
import com.Soham.MSProject.Input.CreateInputPairsImpl;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Experiment {

  private JFrame frame;
  private JTextField seedipbox;
  private JLabel lblNumberOfPairs;
  private JTextField opfilename;
  
  private enum GroupOfFlippedBits {    
    Starting( "Starting" ),
    Middle( "Middle" ),
    Trailing( "Trailing" );
    
    private String bitposition;
    
    private GroupOfFlippedBits( String bitpos ) {
      this.bitposition = bitpos;
    }
    
    @Override
    public String toString() {
      return bitposition;
    }
  }

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Experiment window = new Experiment();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public Experiment() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 822, 500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    
    JLabel seediptxtlbl = new JLabel("Seed Input String");
    seediptxtlbl.setBounds(0, 0, 133, 22);
    frame.getContentPane().add(seediptxtlbl);
    
    seedipbox = new JTextField();
    seedipbox.setToolTipText("Input the seed string that will have bits flipped for experiment.");
    seediptxtlbl.setLabelFor(seedipbox);
    seedipbox.setText("Input String");
    seedipbox.setBounds(149, 1, 471, 22);
    frame.getContentPane().add(seedipbox);
    seedipbox.setColumns(10);
    
    lblNumberOfPairs = new JLabel("Number of Pairs");
    lblNumberOfPairs.setBounds(0, 34, 123, 16);
    frame.getContentPane().add(lblNumberOfPairs);
    
    final JComboBox<Integer> numberofpairs = new JComboBox<Integer>();
    numberofpairs.setToolTipText("This selects the number of strings with flips to produce.");
    lblNumberOfPairs.setLabelFor(numberofpairs);
    final List< DefaultComboBoxModel<Integer> > num_pair_model = 
        new ArrayList< DefaultComboBoxModel<Integer> >( 2 );
    num_pair_model.add( new DefaultComboBoxModel<Integer>( new Integer[]
        {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20} ));
    num_pair_model.add( new DefaultComboBoxModel<Integer>( new Integer[] {2, 4, 6, 8, 10, 12, 14, 16, 18, 20} ));
    numberofpairs.setModel( num_pair_model.get(0) );
    numberofpairs.setMaximumRowCount(20);
    numberofpairs.setBounds(151, 34, 58, 22);
    frame.getContentPane().add(numberofpairs);
    
    final JComboBox<GroupOfFlippedBits> flipBitValue = new JComboBox<GroupOfFlippedBits>();
    flipBitValue.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String selected = flipBitValue.getSelectedItem().toString();
        if(selected.equals("Trailing")) {
          numberofpairs.setModel( num_pair_model.get(1) );
        } else {
          numberofpairs.setModel( num_pair_model.get(0) );
        }
      }
    });
    flipBitValue.setModel(new DefaultComboBoxModel<GroupOfFlippedBits>(GroupOfFlippedBits.values()));
    flipBitValue.setToolTipText("This will select from which end the flipping to start.");
    flipBitValue.setBounds(632, 0, 92, 22);
    frame.getContentPane().add(flipBitValue);
    
    JLabel lblOutputFile = new JLabel("Output File");
    lblOutputFile.setBounds(227, 34, 79, 16);
    frame.getContentPane().add(lblOutputFile);
    
    opfilename = new JTextField();
    opfilename.setToolTipText("You can enter the file directory to better organize the results.");
    lblOutputFile.setLabelFor(opfilename);
    opfilename.setText("Output File");
    opfilename.setBounds(308, 32, 315, 22);
    frame.getContentPane().add(opfilename);
    opfilename.setColumns(10);
    
    JButton createipbtn = new JButton("Create input file.");
    createipbtn.setToolTipText("Click the button to create input file with given parameters.");
    createipbtn.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        CreateInputPairs c = new CreateInputPairsImpl();
        Object [] success = c.createFile( seedipbox.getText(), 
            flipBitValue.getSelectedItem().toString(),
            ( Integer )numberofpairs.getSelectedItem(), opfilename.getText() );
        if( success.length != 2 ) {
          JOptionPane.showMessageDialog(null, "Something bad happened");
        } else {
          JOptionPane.showMessageDialog(null, success[1]);
        }
      }
    });
    createipbtn.setBounds(632, 26, 175, 32);
    frame.getContentPane().add(createipbtn);
  }
}
