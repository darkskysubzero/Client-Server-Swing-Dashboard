/*
        Filename    :   Server
        Author      :   Aysham Hameed
        Created     :   15th October 2018
        OS          :   Windows 10
        Version     :   Netbeans IDE 8.2
        Desription  :   The Server UI, starts and shutdowns server.
 */


package UI.Server;   //contained in UI.Server package.
                    
import java.awt.Color;  //imported for changing button colors.
import javax.swing.*;   //imported for showing dialogs.
import java.net.*;  //imported to work with sockets.
import java.io.*;   //imported to work with streams.


public class Server extends javax.swing.JFrame { //main class Server extends
                                                 //JFrame to display frame
    
    boolean loggedIn = false;   //flag variable to verify login.
    ServerSocket serverSocket;  //creating ServerSocket object.
    boolean listening;  //flag variable to check server status
    
    
    
    
    
    
    //SERVER CONSTRUCTOR
    public Server() {  
        initComponents();
        listening = false; //set listening to false by default.
      
    }
    
    //INSTANTIATES SERVER SOCKET OBJECT & STARTS LISTENING
    public void turnOnServer(){
        if(listening==false){
            try{    
                serverSocket = new ServerSocket(7777);  //listening on port 7777.
                listening =true;    //set listening true.
                System.out.println("Server closed :" + serverSocket.isClosed());
            }catch (IOException e){ //catch IO exception.
                System.out.println(e.toString());   //print exception message.
                System.exit(1); }//exit process.
             jLabel1.setText("Server status : online"); //updating server status.
        } else {
            ///UPDATING SERVER STATUS USING ANONYMOUS THREAD OBJECT
            Thread timer = new Thread(new Runnable() {  //create new thread
                public void run() { //using overriden run method
                    //chaning text
                   jLabel1.setText("Server status : already running!");
                   try{
                   Thread.sleep(1000);  //pause for 1 second
                   }catch (InterruptedException e){} //catching exception
                   jLabel1.setText("Server status : online"); //update text
                }
            });timer.start(); //starts thread object.      
        }  //LISTENING INSIDE THREAD
            Thread t = new Thread(new Runnable() {
                public void run() {       
                    /*
                    EXPLANATION: so while the server is listening then create
                                 a new session. (Meaning allow clients to connect
                                 to the server)
                    */
                    
                     while(listening){
                        try{
                             new Session(serverSocket.accept());
                             System.out.println("Listening!");
                        }catch (IOException e){
                             System.out.println(e.toString());
                        }
                     }
                }
            });
            t.start();
    }

    //CLOSES SERVER SOCKET 
    public void shutdownServer(){        
        //RUNNING INSDE ANONYMOUSE THREAD
        Thread s = new Thread(new Runnable() {
            @Override
            public void run() {;
                try{
                    /*
                    EXPLANATION: Create a socket. Create outputstream object.
                                 Tell self (Server) to shutdow.
                                 And close serverSocket
                                 And set listening to false so can check if
                                 server is online or offline (SEE OTHER
                                 METHODS)
                    */
                     Socket sSocket = new Socket("localhost",7777);
                     ObjectOutputStream send = new ObjectOutputStream(sSocket.getOutputStream());
                     send.writeObject("shutdown");
                     serverSocket.close();
                
                listening = false;
                System.out.println("Server closed :" + serverSocket.isClosed());
                jLabel1.setText("Server status : offline"); //updating server status.
                
                }catch (IOException e){ //catch IOException 
                }
            }
        });        
        s.start(); //start thread
       
    }
    
    //LOGIN REQUEST
    public void login() {
        
        JTextField username = new JTextField("admin"); //get username
        JPasswordField password = new JPasswordField("password");//get password
        //create input fields
        Object message[] = {"Username : ", username, "Password : ", password};
        //display input fields
        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        
        //based on option slected
        if (option == JOptionPane.OK_OPTION) {
            //if ok option is selected the check is username = "admin
            if (username.getText().equals("admin")) {
                //if username is correct then check if password is correct
                if (password.getText().equals("password")) {
                    //if password is correct then set
                    //logged in = true
                    loggedIn = true;
                    //display welcome message
                    JOptionPane.showMessageDialog(null, "Welcome admin!");
                } else 
                    //if password is wrong show correct message    
                    if (!password.getText().equals("password")) {
                    JOptionPane.showMessageDialog(null, "Wrong password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else 
                //if username is wrong show correct message
                if (!username.getText().equals("admin")) {
                JOptionPane.showMessageDialog(null, "Wrong username!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            //if cancel button is pressed then exit system
            System.exit(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        shutdownButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server UI");
        setBackground(new java.awt.Color(51, 51, 51));
        setResizable(false);
        setSize(new java.awt.Dimension(400, 300));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        startButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        startButton.setForeground(new java.awt.Color(255, 255, 255));
        startButton.setText("Start");
        startButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 204), 1, true));
        startButton.setContentAreaFilled(false);
        startButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButtonMouseExited(evt);
            }
        });
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        shutdownButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        shutdownButton.setForeground(new java.awt.Color(255, 255, 255));
        shutdownButton.setText("Shutdown");
        shutdownButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 0, 102), 1, true));
        shutdownButton.setContentAreaFilled(false);
        shutdownButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        shutdownButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                shutdownButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                shutdownButtonMouseExited(evt);
            }
        });
        shutdownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shutdownButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Server status : offline");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(93, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shutdownButton, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(shutdownButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(416, 339));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

    }//GEN-LAST:event_formWindowActivated
    
    
    //WHEN WINDOW IS OPENED (THE FIRST THING TO BE DONE)
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        while (loggedIn == false) {//while not logged int 
            login();    //keep displayin login request.
        }

    }//GEN-LAST:event_formWindowOpened
    
    //START BUTTON HOVER IN
    private void startButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startButtonMouseEntered
        startButton.setContentAreaFilled(true); //make button colorful
        startButton.setBackground(new Color(0, 202, 106));  //change to color
        startButton.setForeground(Color.black); //change font to black
    }//GEN-LAST:event_startButtonMouseEntered

    //START BUTTON HOVER OUT
    private void startButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startButtonMouseExited
        startButton.setContentAreaFilled(false); //make button transparent
        startButton.setForeground(Color.white); //change font color to white
    }//GEN-LAST:event_startButtonMouseExited

    //SHUTDOWN BUTTON HOVER IN
    private void shutdownButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shutdownButtonMouseEntered
        shutdownButton.setContentAreaFilled(true);//make button colorful
        shutdownButton.setBackground(new Color(204, 0, 102));//change to color
        shutdownButton.setForeground(Color.black);//change font to black
    }//GEN-LAST:event_shutdownButtonMouseEntered

    //SHUTDOWN BUTTON HOVER OUT
    private void shutdownButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shutdownButtonMouseExited
        shutdownButton.setContentAreaFilled(false); //make button transparent
        shutdownButton.setForeground(Color.white);//change font color to white
    }//GEN-LAST:event_shutdownButtonMouseExited

    //STARTS SERVER
    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        turnOnServer(); //turn on server
    }//GEN-LAST:event_startButtonActionPerformed

    //SHUTDOWNS SERVER
    private void shutdownButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shutdownButtonActionPerformed
        shutdownServer(); //turn off server
    }//GEN-LAST:event_shutdownButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton shutdownButton;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
}
