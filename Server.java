import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Calendar;
import java.util.*;
import java.text.*;
import javax.swing.border.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.*;
import java.io.*;

//server class
public class Server implements ActionListener {
    JTextField text;// declaring globally
    JPanel a1;
    static Box vertical = Box.createVerticalBox();// one after the other
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    Server() {
        // constructor
        f.setLayout(null);// for layouts
        JPanel p1 = new JPanel(); // chatting header(contact name)
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1); // above the frame
        // back icon

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("1.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        back.addMouseListener(new MouseAdapter() {
            // for action
            public void mouseClicked(MouseEvent ae) {
                System.exit(0); // to close the project we can also use setvisible
            }
        });
        // profile picture
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png "));
        Image i8 = i7.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);

        // phone
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("mobile.png "));
        Image i11 = i10.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 35, 30);
        p1.add(phone);

        // more revert
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png "));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);
        p1.add(morevert);

        // for writing name
        JLabel name = new JLabel("Chatbot");
        name.setBounds(110, 10, 100, 30);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1);

        // textbox
        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN_SARIF", Font.PLAIN, 16));

        f.add(text);

        // send buttton
        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);// event
        send.setFont(new Font("SAN_SARIF", Font.PLAIN, 16));
        f.add(send);

        f.setSize(450, 700); // for sizing
        f.setLocation(800, 50);// always the frame opens from top left side now it will open from certain
                               // position.
        f.setUndecorated(true); // used to remove close,maxi,mini button.
        f.getContentPane().setBackground(Color.white);

        f.setVisible(true);// for visiblity
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText();

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15)); // space between the message
            a1.add(vertical, BorderLayout.PAGE_START);
            dout.writeUTF(out);
            // to emptyy text after sending
            text.setText("");
            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel(out);
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));// color background
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50)); // border of chat
        panel.add(output);
        Calendar cal = Calendar.getInstance();// for date
        SimpleDateFormat sdf = new SimpleDateFormat("HH : mm");
        JLabel time = new JLabel();// to set the value dynamically we use settext function
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;

    }

    public static void main(String[] args) {
        new Server();// object
        try {
            ServerSocket skt = new ServerSocket(6001);
            while (true) {
                Socket s = skt.accept();// to get i/p o
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true) {
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg); // display
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}