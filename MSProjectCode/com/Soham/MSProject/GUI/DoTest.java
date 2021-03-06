package com.Soham.MSProject.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class DoTest {

  protected Shell shell;

  /**
   * Launch the application.
   * @param args
   */
  public static void main(String[] args) {
    try {
      DoTest window = new DoTest();
      window.open();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Open the window.
   */
  public void open() {
    Display display = Display.getDefault();
    createContents();
    shell.open();
    shell.layout();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  /**
   * Create contents of the window.
   */
  protected void createContents() {
    shell = new Shell();
    shell.setSize(423, 319);
    shell.setText("SWT Application");
    
    Label lblCV = new Label(shell, SWT.NONE);
    lblCV.setBounds(9, 0, 128, 17);
    lblCV.setText("Chaining Value Size");    
    final Combo comboCV = new Combo(shell, SWT.NONE);
    comboCV.setToolTipText("Choose the number of bits for chaining value.");
    comboCV.setItems(new String[]{"32", "64", "128", "256", "512"});
    comboCV.setBounds(185, 0, 201, 27);
    
    Label lblMethodToFind = new Label(shell, SWT.NONE);
    lblMethodToFind.setBounds(9, 39, 170, 17);
    lblMethodToFind.setText("Method to find collision ");
    final Combo combo_find_collision = new Combo(shell, SWT.NONE);
    combo_find_collision.setToolTipText("Choose the algorithm to find collisions.");
    combo_find_collision.setItems(new String[]{"Hill Climbing", "Simulated Annealing",
        "Taboo Search", "Random Search"});
    combo_find_collision.setBounds(185, 39, 201, 27);
    
    Label lblDigestLength = new Label(shell, SWT.NONE);
    lblDigestLength.setBounds(10, 81, 101, 17);
    lblDigestLength.setText("Digest Length");
    final Combo combo_digest_length = new Combo(shell, SWT.NONE);
    combo_digest_length.setToolTipText("Select the digest length for the hash.");
    combo_digest_length.setItems(new String[]{"224", "256", "384", "512"});
    combo_digest_length.setBounds(185, 81, 201, 27);
    
    Label lblHashAlgorithm = new Label(shell, SWT.NONE);
    lblHashAlgorithm.setBounds(9, 125, 116, 17);
    lblHashAlgorithm.setText("Hash Algorithm");
    final Combo combo_sha3 = new Combo(shell, SWT.NONE);
    combo_sha3.setToolTipText("Select the SHA3 finalist algorithm for hashing.");
    combo_sha3.setItems(new String[]{"BLAKE", "Groestl", "Keccak"});
    combo_sha3.setBounds(185, 125, 201, 27);
    
    Label lblNumberOfRounds = new Label(shell, SWT.NONE);
    lblNumberOfRounds.setBounds(9, 164, 128, 17);
    lblNumberOfRounds.setText("Number of rounds");
    final Combo combo_num_rounds = new Combo(shell, SWT.NONE);
    combo_num_rounds.setToolTipText("Select the number of rounds");
    combo_num_rounds.setItems(new String[]{"1", "2", "3"});
    combo_num_rounds.setBounds(185, 164, 201, 27);
    
    Label lblFlippedEnd = new Label(shell, SWT.NONE);
    lblFlippedEnd.setBounds(9, 204, 86, 17);
    lblFlippedEnd.setText("Flipped End");
    final Combo combo_flip_end = new Combo(shell, SWT.NONE);
    combo_flip_end.setToolTipText("Select the flipped end of text pairs for this run.");
    combo_flip_end.setItems(new String[]{"Start", "Middle", "End"});
    combo_flip_end.setBounds(185, 204, 201, 27);
    
    Button btnStartEvaluation = new Button(shell, SWT.NONE);
    btnStartEvaluation.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseDoubleClick(MouseEvent arg0) {
        String cv          = comboCV.getText();
        String m_collision = combo_find_collision.getText();
        String diglen      = combo_digest_length.getText();
        String sha3        = combo_sha3.getText();
        String rounds      = combo_num_rounds.getText();
        String flipend     = combo_flip_end.getText();
        com.Soham.MSProject.SimulationAlgorithm.Experiment exp = 
            new com.Soham.MSProject.SimulationAlgorithm.Experiment();
        exp.startExperiment(cv, m_collision, diglen, sha3, rounds, flipend);
      }
    });
    btnStartEvaluation.setBounds(9, 252, 128, 27);
    btnStartEvaluation.setText("Start evaluation");
  }
}