package ServerGUI;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ServerStartPanel extends JPanel{
    
    //private class attributes
    JTextArea clientInfoTextArea;
    JButton closeServerBtn;
    JLabel clientInfoLabel;
    final int WIDHT = 400;
    final int HEIGHT = 600;
    //____________________________________

    public ServerStartPanel(MainFrame mainFrame){
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        setBackground(Color.black);

        clientInfoTextArea = new JTextArea();
        clientInfoTextArea.setBackground(Color.black);
        clientInfoTextArea.setFont(MainFrame.GLOBAL_FONT);
        clientInfoTextArea.setForeground(Color.white);
        clientInfoTextArea.append("<PLACEHOLDER FOR FIRST CLIENT>");

        closeServerBtn = new JButton("CLOSE THE SERVER");
        closeServerBtn.setFocusable(false);
        closeServerBtn.setFont(MainFrame.GLOBAL_FONT);
        closeServerBtn.setBackground(Color.gray);
        closeServerBtn.setForeground(Color.black);
        closeServerBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainFrame.closeServer();
            }
        });

        clientInfoLabel = new JLabel("CLIENTS INFORMATION", JLabel.CENTER);
        clientInfoLabel.setForeground(Color.white);
        clientInfoLabel.setBackground(Color.black);
        clientInfoLabel.setFont(MainFrame.GLOBAL_FONT);

        add(clientInfoLabel, BorderLayout.NORTH);
        add(clientInfoTextArea, BorderLayout.CENTER);
        add(closeServerBtn, BorderLayout.SOUTH);
    }

}
