import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
public class ATMPROJECT {
    //Main Fuction FRame 1
    public static void main(String[] args) {
        Frame f=new Frame("ATM INTERFACE");
        final TextField tf1=new TextField("");
        final JPasswordField tf2=new JPasswordField("");
        Label l1=new Label("WELCOME TO OUR ATM ");
        l1.setBounds(400,150,800,50);
        Label l2=new Label("ACCOUNT NO ");
        l2.setBounds(500,270,100,40);
        Label l3=new Label("PASSWORD ");
        l3.setBounds(750,270,80,40);

        f.setBackground(Color.magenta);
        tf1.setBounds(500,300,200,40);
        tf2.setBounds(750,300,200,40);
        Font fnt = new Font("Serif", Font.BOLD, 44);
        Font fnt1= new Font("Serif", Font.BOLD, 60);
        l1.setFont(fnt1);
        Button b=new Button("PRESS TO ENTER");
        b.setFont(fnt);
        b.setBounds(480,400,500,80);

        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ATMPROJECT mn=new ATMPROJECT();
                String un=tf1.getText();
                String pw=tf2.getText();
                mn.connect(un,pw);
                tf1.setText("");
                tf2.setText("");
            }
        });
        f.add(b);
        f.add(l1);
        f.add(tf1);
        f.add(tf2);
        f.add(l2);
        f.add(l3);
        f.setSize(1400,750);
        f.setLayout(null);
        f.setVisible(true);
    }
    // frame 2 main page
    public void mainpage(Long s){
        Frame f2 = new Frame("Main Page");
        f2.setBackground(Color.CYAN);
        Label l1=new Label("CHOOSE AN ACTION");
        l1.setBounds(350,120,800,50);
        Font fnt2= new Font("Serif", Font.BOLD, 60);
        f2.setFont(fnt2);
        Font fnt1 = new Font("Elephant", Font.BOLD, 40);
        Button b1 = new Button("WITHDRAW");
        Button b2 = new Button("DEPOSIT");
        Button b3 = new Button("DETAILS");
        Button b4 = new Button("BALANCE");
        Button b5 = new Button("EXIT");
        b1.setFont(fnt1);
        b2.setFont(fnt1);
        b3.setFont(fnt1);
        b4.setFont(fnt1);
        b5.setFont(fnt1);
        int [] btnarr=new int[1];
        b1.setBounds(200, 250, 300, 100);
        b2.setBounds(700, 250, 300, 100);
        b3.setBounds(200, 400, 300, 100);
        b4.setBounds(700, 400, 300, 100);
        b5.setBounds(470, 550, 300, 100);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                withdraw(s,"Withdraw");
                btnarr[0]=1;
            }
        });
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                withdraw(s,"Deposite");
            }
        });
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                details(s);
            }
        });

        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                balance(s);
            }
        });
        b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f2.dispose();
            }
        });

        f2.add(b1);
        f2.add(b2);
        f2.add(b3);
        f2.add(b4);
        f2.add(b5);
        f2.add(l1);
        f2.setSize(1400, 750);
        f2.setLayout(null);
        f2.add(tf);
        f2.setVisible(true);
    }

    final TextField tf=new TextField();
    int gamt=0;
    public void withdraw(Long a, String op){
        Frame f3 = new Frame("Withdraw");
        f3.setBackground(Color.yellow);
        Label l3=new Label("Enter Amount");
        l3.setBounds(200,100,100,80);
        TextField tf3=new TextField();
        tf3.setBounds(200,200,200,80);
        Button wb = new Button(op);
        wb.setBounds(200,450,200,80);
        wb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               gamt=Integer.parseInt(tf3.getText());
               if (op=="Withdraw"){
               deduct(a);
               f3.dispose();
               }
               else{
                   addm(a);
                   f3.dispose();
               }
            }});
        f3.add(wb);
        f3.add(l3);
        f3.setSize(600,600);
        f3.add(tf3);
        f3.setLayout(null);
        f3.setVisible(true);
    }

    public void connect(String a,String b) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/atmdb", "root", "");
            Statement stmt = con.createStatement();
            String qry="select * from users where Accont_No = "+a+" ";
            ResultSet rs = stmt.executeQuery(qry);
            if(rs.next()){
                String ckp=rs.getString("Password");
                String cku=rs.getString("Accont_no");
                if (ckp.equals(b) && cku.equals(a)){
                    JOptionPane.showMessageDialog(null,"Login Succcessful");
                    ATMPROJECT c1=new ATMPROJECT();
                    c1.mainpage(Long.parseLong(a));
                }
                else{
                    JOptionPane.showMessageDialog(null, "Wrong Username or Password");
                } }
            else{
                JOptionPane.showMessageDialog(null, "Wrong Username or Password");
            }
            con.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Wrong Username or Password");
        } }
        public void deduct(Long a){
        int checkbal=0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/atmdb", "root", "");
            Statement stmtn = conn.createStatement();
            String qury="select * from users where Accont_No = "+a+" ";
            ResultSet rst = stmtn.executeQuery(qury);
            while(rst.next()) {
                checkbal = rst.getInt("Balance");
            }
            String quryy="Update users set Balance ="+String.valueOf(checkbal-gamt)+"  where Accont_no ="+a+" ";
            if (checkbal>=gamt) {
                stmtn.executeUpdate(quryy);
                JOptionPane.showMessageDialog(null, "Balance Deducted");
            }
            else{
                JOptionPane.showMessageDialog(null, "LOW Balance Unable to withdraw");
            }}
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        } }
        public void addm(Long a){
            int checkbal=0;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/atmdb", "root", "");
                Statement stmtn = conn.createStatement();
                String qury="select * from users where Accont_No = "+a+" ";
                ResultSet rst = stmtn.executeQuery(qury);
                while(rst.next()) {
                    checkbal = rst.getInt("Balance");
                }
                String quryy="Update users set Balance ="+String.valueOf(checkbal+gamt)+"  where Accont_no ="+a+" ";

                    stmtn.executeUpdate(quryy);
                    JOptionPane.showMessageDialog(null, "Balance Added");

            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }

        }


  public void balance (long a){

      try{
          Class.forName("com.mysql.jdbc.Driver");
          Connection con = DriverManager.getConnection(
                  "jdbc:mysql://localhost:3306/atmdb", "root", "");
          Statement stmt = con.createStatement();
          String qry="select * from users where Accont_No = "+a+" ";
          ResultSet rs = stmt.executeQuery(qry);
          while (rs.next()){
              JOptionPane.showMessageDialog(null,"Available Balance ="+rs.getString("Balance"));
              }
          con.close();
      }
      catch(Exception e){
          System.out.println(e);
      }
  }
  public void details(Long a){
        try{
      Class.forName("com.mysql.jdbc.Driver");
      Connection con = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/atmdb", "root", "");
      Statement stmt = con.createStatement();
      String qry="select * from users where Accont_No = "+a+" ";
      ResultSet rs = stmt.executeQuery(qry);
      while (rs.next()) {
          String ckn = rs.getString("Name");
          String cku = rs.getString("Accont_no");
          String ckb = rs.getString("Balance");
          JOptionPane.showMessageDialog(null,"Name : "+ckn+"\nAccount NO : "+cku+"\nAvailable Balance ="+ckb);
      }
        }
      catch(Exception e){
                System.out.println(e);
            }
      }




}

