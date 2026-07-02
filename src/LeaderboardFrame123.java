/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Kiara
 */
import javax.swing.JFrame;
import javax.swing.JTextArea;
public class LeaderboardFrame123 extends JFrame {
    

     public LeaderboardFrame123(String[] names, Integer[] stages) {
    super("Names and Leaderboard");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Closes the window instead of exiting
    // Create a JTextArea to display the names
    JTextArea leaderboardText = new JTextArea();
    leaderboardText.setEditable(false); // Prevent editing the text

    // Build the leaderboard text
    StringBuilder messageBuilder = new StringBuilder("Names in the leaderboard:\n");
    int i=0;
    for (String name : names) {
        
      messageBuilder.append(name).append("---------").append(stages[i]).append("\n");
        i++;
    }

    leaderboardText.setText(messageBuilder.toString());

    // Add the JTextArea to the frame
    add(leaderboardText);

    // Pack the frame to fit its contents
    pack();
  }
}




