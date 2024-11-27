import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
// import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Color;

class Main implements ActionListener, KeyListener {
    JPanel notify = null;
    JPanel chat = null;
    JPanel text = null;
    JFrame frame = null;
    JTextArea conlist = null; // this will have the list of people connected to us
    JTextArea chatdisplay = null; // this will display the chats between the users
    JTextField chatti = null; // this is the area where you will type the text or command
    JButton send = null; // it is the send button
    JLabel texcom = null; // this contain both send button and chatti JTextField
    // private Boolean command = false;
    JScrollPane scroll;
    Thread t1=null;
    // Thread t2=null;
    Server S=null;
    Client C=null;

    public static void main(String[] args) throws IOException
    {

        Main object = new Main();
        object.Frontend();
        File er=new File("./chat/error");
        er.createNewFile();
        data.error=new FileWriter("./chat/error");
        // if(data.refresh)
        // {
        //     object.screen(data.filename);
        // }
        while(true)
        {
            if(data.refresh)
            {
                object.screen(data.filename);
            }
        }

    }

    public void Frontend() // gui parts of the code
    {
        notify = new JPanel();
        notify.setBackground(Color.green);
        notify.setBounds(0, 0, 200, 600);
        notify.setLayout(null);

        chat = new JPanel();
        chat.setBackground(Color.green);
        chat.setBounds(200, 0, 400, 400);
        chat.setLayout(null);

        text = new JPanel();
        text.setLayout(null);
        text.setBounds(200, 400, 400, 200);
        text.setBackground(Color.green);

        conlist = new JTextArea();
        conlist.setBounds(5, 5, 190, 560);
        conlist.setEditable(false);
        conlist.setBackground(Color.black);

        chatdisplay = new JTextArea();
        chatdisplay.setBounds(5, 5, 390, 390);
        chatdisplay.setEditable(false);
        chatdisplay.setBackground(Color.black);
        chatdisplay.setForeground(Color.white);

        scroll = new JScrollPane(chatdisplay);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(5, 5, 390, 390);

        chatti = new JTextField();
        chatti.setBounds(5, 5, 380, 50);
        chatti.addKeyListener(this);

        send = new JButton();
        send.setBounds(150, 80, 100, 40);
        send.setText("SEND");
        send.addActionListener(this);

        texcom = new JLabel();
        texcom.setBounds(5, 5, 390, 160);
        texcom.setBackground(Color.black);
        texcom.setOpaque(true);
        texcom.add(chatti);
        texcom.add(send);

        frame = new JFrame();
        frame.setLayout(null);
        notify.add(conlist);
        // chat.add(chatdisplay);
        chat.add(scroll);
        text.add(texcom);
        frame.add(notify);
        frame.add(chat);
        frame.add(text);
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) // this will send the text to send function when the button is pressed
    {
        String str = chatti.getText();
        if (str != "") {
            try{
                textsend(str);
            }

            catch(Exception ea)
            {
                System.out.print(ea);
            }
            chatti.setText("");
            chatti.setBackground(Color.white);
        }
    }

    public void textsend(String str) throws IOException, InterruptedException 
    {   // it sends the texts to command or the the other user
        if (data.command) {
            data.command = false;
            comand(str);
        }

        else {
            S.text(str);
        }
    }
    //

    @Override
    public void keyPressed(KeyEvent e) // check when esc and enter is pressed and do work accordingly
    {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            chatti.setBackground(Color.green);
            data.command = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String str = chatti.getText();
            if (str != null) {
                try{
                    textsend(str);
                }

                catch(Exception es)
                {
                    System.out.print(es);
                }
                chatti.setText("");
                chatti.setBackground(Color.white);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    public void comand(String str) throws IOException, InterruptedException 
    {   // this function will act the commands which user will pass
        if(str.substring(0,6).equalsIgnoreCase("Server")) //Server start
        {
            if(str.substring(7,12).equalsIgnoreCase("start"))
            {
                S=new Server();
                t1=new Thread(S);
                t1.start();
                chatdisplay.setText("server start");
                
            }

            else if(str.substring(7,11).equalsIgnoreCase("stop")){
                S.exit();
            }
        }

        else if(str.substring(0,7).equalsIgnoreCase("client"))
        {
            //todo command: client 127.0.0.1
        }

        if(str.substring(0,4).equalsIgnoreCase("chat"))
        {
            
            int aa=Integer.parseInt(String.valueOf(str.charAt(5)));
            String connectionnumber=data.connection.get(aa);
            
            if(connectionnumber.substring(2,8).equalsIgnoreCase("server"))
            {
                System.out.println("haaai");
                S.start(Integer.parseInt(String.valueOf(connectionnumber.charAt(0))));
                System.out.println("haaai");
            }

            else if(connectionnumber.substring(2,8).equalsIgnoreCase("client"))
            {
                //todo c.start(Integer.parseInt(String.valueOf(connectionnumber.charAt(0))));

            }
        }
    }

    public void screen(String a) throws FileNotFoundException
    {
        conlist.append(""+data.connection.size()+"\n");
        File file=new File("./chat/"+a);
        Scanner sc=new Scanner(file);
        chatdisplay.setText("");
        while(sc.hasNextLine())
        {
            String dda=sc.nextLine();
            chatdisplay.append(dda);
            data.refresh=false;
            
        }
        sc.close();
    }
}