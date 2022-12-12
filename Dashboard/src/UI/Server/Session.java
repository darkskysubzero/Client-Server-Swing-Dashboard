/*
        Filename    :   Session
        Author      :   Aysham Hameed
        Created     :   15th October 2018
        OS          :   Windows 10
        Version     :   Netbeans IDE 8.2
        Desription  :   Allows multiple clients & creates a session for each.
 */


package UI.Server;  //contained in UI.Server package

import java.net.*;  //imported to work with sockets.
import java.io.*;   //imported to work with streams.

public class Session implements Runnable { //Implementing the Runnable class.
    
    //VARIABLES FOR SOCKET, OBJECT STREAMS & THREAD
    public Socket clientSocket;    
    public  ObjectOutputStream send2CLIENT = null;
    public ObjectInputStream getfromCLIENT = null;
    private Thread runner;
    
    
    
    
    //SESSION CONSTRUCTOR RECIEVES A SOCKET AS PARAMETER
    public Session(Socket s){
        clientSocket = s;   //recives socket parameter
        
        //CREATING STREAM OBJECTS
        try{
            //instantiates new ObjectOutputStream
            send2CLIENT = new ObjectOutputStream(clientSocket.getOutputStream());
            //instantiates new ObjectInputStream
            getfromCLIENT = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e){ //catches IO exception
            System.out.println(e.toString());   //prints exceptio message
        }
        
        //STARTING THREAD
        if(runner==null){   //if runner thread is empty
            runner = new Thread(this);  //redefine thread object
            runner.start(); //start runner thread.
        }
        
    }
    
    
    public void run(){
        //WHILE IT IS BUSY WITH CURRENT THREAD
        while(runner==Thread.currentThread()){//while1 start------------------
            //PAUSING FOR 10 MILISECONDS
            try{
                Thread.sleep(10);
            }catch (InterruptedException e){System.out.println(e.toString());}
            
            //KEEP ON LISTENING
            while(true){
                try{
                    //prints out list of active connections.
                    System.out.println("Who is connected? : "+clientSocket.
                                                            getInetAddress());
                    //gets client request
                    String clientRequest = (String) getfromCLIENT.readObject();
                    System.out.println("Client : "+clientRequest);            
                    //new Protocol class object
                    Protocol decoder = new Protocol();        
                    //clientRequest being processed 
                    String output = decoder.processInput(clientRequest);  
                    //sends client output
                    send2CLIENT.writeObject(output);

                    String tableName = decoder.SEARCH_TABLE;
                    //if output when user tries searching is 
                    if(output.equals("nameExists")){ //"nameExists"
                         //sending the client name of table
                         send2CLIENT.writeObject(tableName);  
                        if(tableName.equals("Animals")){ //IF ANIMAL TABLE
                            //GET ANIMAL DATA   //SELECETED
                            String animalID[] = (String[]) decoder.getAnimalID();
                            String animalNames[]=(String[]) decoder.getAnimalNames();
                            String description[]=(String[]) decoder.getDescription();
                            String speciesIDFK[]=(String[]) decoder.getSpeciesIDFK();
                            send2CLIENT.writeObject(animalID);
                            send2CLIENT.writeObject(animalNames);
                            send2CLIENT.writeObject(description);
                            send2CLIENT.writeObject(speciesIDFK);
                        } else 
                        //IF SPECIES TABLE IS SELECTED    
                        if(tableName.equals("Species")){
                            //GET SPECIES DATA
                            String speciesID[] = (String[]) decoder.getSpeciesID();
                            String speciesNames[] =(String[]) decoder.getSpeciesNames();
                            
                            send2CLIENT.writeObject(speciesID);
                            send2CLIENT.writeObject(speciesNames);
                        }    
                    } else
                     
                     if(output.equals("shutdown")){
                         System.out.println("output = "+output);
                         break;
                     }
                    
                    
                    
                    
                }catch (IOException e){ //catching IOException
                    System.out.println(e.toString()); //exception message
                    System.exit(1); //exit process
                } catch (ClassNotFoundException e){ //catch ClassNotFound excep
                    System.out.println(e.toString());   //prints excep message 
                }
                
            }//while2 end---
            
            try{
            clientSocket.close();
            getfromCLIENT.close();
            send2CLIENT.close();
            } catch (IOException e){}
        }//while1 end----------------------------------------------------------
    }
}
