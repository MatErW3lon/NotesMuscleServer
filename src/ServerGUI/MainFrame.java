package ServerGUI;

import ServerBackEnd.MainServer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Container;
import java.awt.Color;
import java.awt.Font;

public class MainFrame extends JFrame{

    public static final Font GLOBAL_FONT = new Font("Mistral", Font.PLAIN, 20);

    //the private instance attributes
    final int WIDTH = 500;
    final int HEIGHT = 600;
    MainServer mainServer;
    JLabel serverSetupLabel, serverLogoLabel;
    JButton startServerBtn;
    Container contentPane;
    CardLayout crd;
    JPanel mainFramePanel;
    ServerStartPanel serverStartPanel;
    //______________________________

    private static MainFrame mainFrame = null;

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        //System.out.println("THREAD: " + Thread.currentThread());
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setVisible(true);
    }

    public static MainFrame getInstance(){
        if(MainFrame.mainFrame == null){
            MainFrame.mainFrame = new MainFrame();
        }
        return MainFrame.mainFrame;
    }

    private MainFrame(){
        setMainFrame();
        createComponenets();
        contentPane.add("mainFramePanel", this.mainFramePanel);
        this.serverStartPanel = new ServerStartPanel(this);
        contentPane.add("serverStartPanel", this.serverStartPanel);
    }

    private void setMainFrame(){
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("NOTES MUSCLE SERVER");
        crd = new CardLayout();
        contentPane = getContentPane();
        contentPane.setLayout(crd);
        contentPane.setBackground(Color.black);
    }

    private void createComponenets(){
        createLogo();
        mainFramePanel = new JPanel();
        serverSetupLabel = new JLabel("CLICK TO START THE SERVER", JLabel.CENTER);
        serverSetupLabel.setForeground(Color.white);
        serverSetupLabel.setFont(new Font("Mistral", Font.PLAIN, 20));

        startServerBtn = new JButton("START SERVER");
        startServerBtn.setBackground(Color.gray);
        startServerBtn.setForeground(Color.black);
        startServerBtn.setFont(new Font("Mistral", Font.PLAIN, 20));
        startServerBtn.setFocusable(false);
        startServerBtn.setSize(50, 100);
        startServerBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(mainServer == null){
                    try{
                        mainServer = MainServer.getInstance();
                        serverStartPanel = new ServerStartPanel(MainFrame.mainFrame);
                        JOptionPane.showMessageDialog(null, "SERVER STARTED AT PORT localhost:4444", "SERVER START", JOptionPane.INFORMATION_MESSAGE);
                        mainServer.start();
                        crd.next(contentPane);
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
                        serverSetupLabel.setText("SERVER FAILED TO START! TRY AGAIN");
                        try {
                            mainServer.closeEveryThing();
                        } catch (IOException IOex) {
                            IOex.printStackTrace();
                        }
                        mainServer = null;
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "SERVER IS ALREADY RUNNING", "ERROR!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mainFramePanel.setLayout(new BorderLayout());
        mainFramePanel.add(serverSetupLabel, BorderLayout.NORTH);
        mainFramePanel.add(serverLogoLabel, BorderLayout.CENTER);
        mainFramePanel.add(startServerBtn, BorderLayout.SOUTH);
        mainFramePanel.setBackground(Color.black);
    }


    private void createLogo(){
        BufferedImage logoImage;
        try{
            logoImage = ImageIO.read(new File(System.getProperty("user.dir") + "\\assets\\Logo.png"));
            serverLogoLabel = new JLabel(new ImageIcon(logoImage));
        }catch(IOException IOex){
            IOex.printStackTrace();
            System.exit(0);
        }
    }

    public void closeServer(){
        mainServer.setServerState(false);
        try{
            mainServer.closeEveryThing();
        }catch(IOException IOex){
            IOex.printStackTrace();
        }
        mainServer = null;
        MainServer.resetInstance();
        crd.previous(contentPane);
    }
}