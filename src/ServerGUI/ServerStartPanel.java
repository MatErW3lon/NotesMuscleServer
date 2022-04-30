package ServerGUI;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import ServerBackEnd.BackendGUI_Interface;

public class ServerStartPanel extends JPanel implements Runnable{
    
    //private class attributes
    JTextArea clientInfoTextArea;
    JButton closeServerBtn;
    JLabel clientInfoLabel;
    final int WIDHT = 400;
    final int HEIGHT = 600;
    Thread updateClientTextAreaThread;
    boolean exit;
    MainFrame mainFrame;
    //____________________________________

    public ServerStartPanel(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        setBackground(Color.black);

        clientInfoTextArea = new JTextArea();
        clientInfoTextArea.setBackground(Color.black);
        clientInfoTextArea.setFont(MainFrame.GLOBAL_FONT);
        clientInfoTextArea.setForeground(Color.white);
        clientInfoTextArea.append("CLIENT USERNAMES");

        closeServerBtn = new JButton("CLOSE THE SERVER");
        closeServerBtn.setFocusable(false);
        closeServerBtn.setFont(MainFrame.GLOBAL_FONT);
        closeServerBtn.setBackground(Color.gray);
        closeServerBtn.setForeground(Color.black);
        closeServerBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                serverPanelDestructor();
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

        exit = false;
        updateClientTextAreaThread = new Thread(this);
        updateClientTextAreaThread.start();
    }

    @Override
    public void run() {
        //only update if the change is detected
        //what is the possible change. the change in the size of the client_info arraylist!
        ArrayList<String> clients_info = BackendGUI_Interface.ClientInformationHandler(null, true);
        while(!exitServerPanel(false)){
            ArrayList<String> temp = BackendGUI_Interface.ClientInformationHandler(null, true);
            if(clients_info.size() != temp.size()){
                clients_info = temp;
                clientInfoTextArea.setText(null);
                clientInfoTextArea.append("CLIENT USERNAMES");
                for(String client_username : clients_info){
                    clientInfoTextArea.append('\n' + client_username);
                }
            }    
        }
    }

    private synchronized boolean exitServerPanel(boolean endPanel){
        if(endPanel)
            exit = endPanel;
        return exit;  
    }

    private void serverPanelDestructor(){
        exitServerPanel(true);
        BackendGUI_Interface.resetClientInformation();
    }
}
