/*
        Filename    :   Protocol
        Author      :   Aysham Hameed
        Created     :   15th October 2018
        OS          :   Windows 10
        Version     :   Netbeans IDE 8.2
        Desription  :   Decodes each request from client.
 */


package UI.Server;  //contained in UI.Server package

import java.sql.*;  //importing sql package


public class Protocol{  //Class name ="Protocol"
    
    //VARIBLES
    //database driver name
    String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; 
    //database source url
    String sourceURL = "jdbc:sqlserver://localhost:1433;databaseName=pDatabase";
    String dusername  = "sa";       //database username
    String dpassword = "123456";    //database password
    Connection connection = null;   //connection object
    
    String [] animalName; //used to store animal names
    String [] animalID; //used to store animal id
    String [] description; //used to store animal description
    String [] speciesIDFK; //used to store species ID FK 
    
    String [] speciesName;//used to store species names
    String [] speciesID;//used to store species id
    
    public String SEARCH_TABLE = ""; //variable to get name of table
            
    Protocol(){
        //Setting up database driver and connection
        try{
         Class.forName(driverName); 
         connection= DriverManager.getConnection(sourceURL,dusername,dpassword);
        }catch (ClassNotFoundException e){System.out.println(e.toString());}
        catch (SQLException e){System.out.println(e.toString());}      
        System.out.println("Database connected!");
        //=====================================================================
    }
    
    public int getAnimalTableCount(String name){
        
        //Gets the number of records in Animal table
        int count=0;
        try{
            Statement statement = connection.createStatement();
            //displays all 
            String query = "select  distinct animalName from Animals where "
                    + "(Animals.animalName like '%"+name+"%')"; 
            ResultSet rec = statement.executeQuery(query);
            //counts by incrementing count variable (NUMBER OF VARIABLES)
            while(rec.next()){
                count++;
            }
            rec.close();
            
            //catches SQL exception
        }catch (SQLException e){
            System.out.println(e.toString());
        }
        return  count;
    }
    
    public int getSpeciesTableCount(String name){
        
        //Gets the number of records in Species table
        int count=0;
        try{
            Statement statement = connection.createStatement();
             //displays all 
            String query = "select  distinct speciesName from Species where "
                    + "(Species.speciesName like '%"+name+"%')"; 
            ResultSet rec = statement.executeQuery(query);
            
            //counts by incrementing count variable (NUMBER OF VARIABLES)
            while(rec.next()){
                count++;
            }               
            rec.close();
            //catches SQL exception
        }catch (SQLException e){
            System.out.println(e.toString());
        }
        return  count;
    }
 
    public String checkSearchName(String name, String table){
        boolean NAMEfound=false; //set name found to false
        try{    
                Statement statement = connection.createStatement();
                String query="";//blank query
                ResultSet rec=null; //rec object set to null
                //============================================================   
                //if table = "Animals" then
                if(table.equals("Animals")){ 
                 SEARCH_TABLE = "Animals";
                //make query find all different animals from Animal table where
                //name is found part of            
                query = "select distinct * from Animals where"
                        + " (Animals.animalName like '%"+name+"%')";        
                
                rec = statement.executeQuery(query);//execute query          
                //redefines array size to number of records in animal table
                animalName = new String[getAnimalTableCount(name)];
                animalID = new String[getAnimalTableCount(name)];
                description = new String[getAnimalTableCount(name)];
                speciesIDFK = new String[getAnimalTableCount(name)];
                
                int counter=0; //used as an index for inserting into array
                while(rec.next()){
                    //if record starts with name or contains part of name then
                    if(rec.getString("animalName").contains(name) || 
                            rec.getString("animalName").equalsIgnoreCase(name)){
                        NAMEfound =  true; //make name found true
                        String n = rec.getString("animalName"); //get name
                        String i = rec.getString("animalID");   //get id
                        String d = rec.getString("animalDesc"); //get description
                        String f = rec.getString("speciesID");  // get fk
                        counter++;  //increase counter
                        animalName[counter-1] = n; //& add name to array
                        animalID[counter-1] = i; //add animalID to arrayID
                        description[counter-1] = d; //adding description
                        speciesIDFK[counter-1] = f; //adding fk
                        
                        System.out.println(rec.getString(1));      
                        //if name is not found
                    } else if(!rec.getString("animalName").contains(name)){
                        NAMEfound = false;//set namefound to false
                    }
                }
                
                }     
                
                //============================================================
                
                //else if table = "Species" then
                else if(table.equals("Species")){
                   SEARCH_TABLE = "Species";
                //make query find all different species from Species table where
                //name is found part of    
                    query = "select distinct * from Species "
                            + "where (Species.speciesName like '%"+name+"%')";
                    
                    rec = statement.executeQuery(query);//execute query
                    //redefines array size to number of records in species table
                    speciesName = new String[getSpeciesTableCount(name)];
                    speciesID = new String[getSpeciesTableCount(name)];
                    
                    int counter=0; //used as an index for inserting into array
                    while(rec.next()){
                    //if record starts with name or contains part of name then
                    if(rec.getString("speciesName").contains(name) || 
                            rec.getString("speciesName").equalsIgnoreCase(name)){
                        NAMEfound =  true; //make name found true
                        String i = rec.getString("speciesID");//get id
                        String n = rec.getString("speciesName"); //get name
                                              
                        counter++;  //increase counter
                        speciesID[counter-1] = i; //add animalID to arrayID
                        speciesName[counter-1] = n; //& add name to array
                        
                        System.out.println(rec.getString(1));      
                        //if name is not found
                    } else if(!rec.getString("speciesName").contains(name)){
                        NAMEfound = false;//set namefound to false
                    }
                }
   
                    
                    
                }
                
                //============================================================
                
                
                
                
         
                rec.close(); //closes resultSet
                
        }catch (SQLException e){
            System.out.println(e.toString());    
        }
        if(NAMEfound==true){ //------------------------------------------
            return "nameExists"; 
        } else              //RETURNS VALUES AS 'Yes' or 'No'
        {
            return "!nameExists";
        }  //--------------------------------------------------------------
}

    public String[] getAnimalNames(){
      return animalName; //returns animal names
    }
    
    public String[] getAnimalID(){
        return animalID; //returns animal id
    }
    
    public String[] getSpeciesNames(){
        return  speciesName;    //returns species names
    }
    
    public String[] getSpeciesID(){
        return speciesID;   //returns species id
    }
    
    public String[] getDescription(){
        return  description;    //returns animal description
    }
    
    public String[] getSpeciesIDFK(){
        return  speciesIDFK; //returns animal speciesID FK
    }    
    
    public String doesUserExist(String username, String password) {
            boolean userNameExists = false; //flag variable 1
            boolean passwordMatched = false; //flag variable 2 
        try{
            //statement object
            Statement statement = connection.createStatement();
            //query 
            String query = "Select * from Users";
            //result object
            ResultSet rec = statement.executeQuery(query);
            //go through records
            while(rec.next()){
                //if username is found
                if(username.equals(rec.getString("userName"))){
                    userNameExists = true;//set userNameExist = true
                } 
                //if username exists & password is found then 
                if(userNameExists==true && password.equals(rec.getString("userP"
                        + "assword"))){
                    passwordMatched = true; //set passWord matched = true
                }
            }
        }catch (SQLException e){ //catch SQL exception 
            System.out.println(e.toString()); //display exception message
        }
        String output = ""; //output variable
        //if username exists & password is matched then 
        if(userNameExists==true && passwordMatched==true){
            output = "Yes";    //update output
        } else if(userNameExists==false){ //if username does not exist
            output = "User does not exist!";//update output
            //if username exists but password is wrong then 
        } else if(userNameExists==true && passwordMatched==false){
            output ="Wrong password!";  //update input
        }
        
        return output;//returns output back
    }
    
    public boolean doesSpeciesExist(String speciesID){
        //Recivies parameters speciesID,speciesName
         int rowsadded; //rowsadded integer
         boolean doesExist = false; //flag varible
         String result = ""; //blank string
         try{
             //creates statement object
            Statement statement = connection.createStatement();

            //display query (DISPLAYS speciesID only from Species table)
            String displaySpeciesID = "SELECT speciesID from Species";
            //executes display query
            ResultSet rec = statement.executeQuery(displaySpeciesID);
            
            while(rec.next()){//if species if found
                    if(speciesID.equals(rec.getString("speciesID"))){
                        doesExist = true;//set does exist to true
                    }
                }
             //catches SQL exception    
            }catch (SQLException e){System.out.println(e.toString());}
         
         return doesExist;
    }
          
    public String insertIntoAnimals(String animalID, String animalName, String 
            description, String speciesID){
        //Recivies parameters animalID, animalName, description, speciesID
        int rowsadded;  //rowsadded integer
        
    if(doesSpeciesExist(speciesID)){    
        
        try{    
            //creates statement object
            Statement statement = connection.createStatement();
            //insert query (SELF EXPLAINATORY)
            String query = "INSERT INTO Animals VALUES "
                    + "('"+animalID+"','"+animalName+"','"+description+"','"+
                    speciesID+"')";
            //executes query
            rowsadded = statement.executeUpdate(query);
            //catches SQL exception
        }catch (SQLException e){System.out.println(e.toString());}
        //returns message
        return "Successully added to Animals table!";
    } else {
        return "Species does not exist!";
    }
       
    }
    
    public String insertIntoSpecies(String speciesID, String speciesName){
        //Recivies parameters speciesID,speciesName
         int rowsadded; //rowsadded integer
         String result = ""; //blank string
         try{
             //creates statement object
            Statement statement = connection.createStatement();
             //insert query (SELF EXPLAINATORY)
            String insert = "INSERT INTO Species VALUES "
                    + "('"+speciesID+"','"+speciesName+"')";
            
            //display query (DISPLAYS speciesID only from Species table)
            String displaySpeciesID = "SELECT speciesID from Species";
            //executes display query
            ResultSet rec = statement.executeQuery(displaySpeciesID);
            
            boolean doesSpeciesExist=false; //flag varible
            while(rec.next()){//if species if found
                if(speciesID.equals(rec.getString("speciesID"))){
                    doesSpeciesExist = true;//set does exist to true
                }
            }
            //species does not exist then 
            if(doesSpeciesExist==false){
            //execute insert query
            rowsadded = statement.executeUpdate(insert);
            //update result message
            result = "Successully added to Species table!";
            }else
          
            //else if it does exist then 
            if(doesSpeciesExist==true){
                //update result message
                result = "Species already exists!";
            }
            
             //catches SQL exception
        }catch (SQLException e){System.out.println(e.toString());}
        //returns result
        return result;
    }
    
    public String deleteRecord(String animalID){
        int rowsdeleted; //rowsadded integer
        String result="";//blank string
        try{
            //creates statement object
            Statement statement = connection.createStatement();
            //display query
            String displayAnimals = "Select * from Animals";
            //delete query including speciesID (input)
            String deleteQuery = "DELETE FROM Animals WHERE animalID='"+animalID
                    +"'";
            //executes displat query
            ResultSet rec = statement.executeQuery(displayAnimals);
                    
            boolean doesExist=false; //flag variable
            while(rec.next()){//go through each record
                //if animalID is found
                if(animalID.equals(rec.getString("animalID"))){
                    doesExist = true;//set doesExist to true
                }
            }
            
            //if animal id exists
            if(doesExist==true){ 
                //the execute delete query
                rowsdeleted = statement.executeUpdate(deleteQuery);
                //return correct message
                result = "Successfully deleted from Animals table!";
            } else
            
            //if it does not exists
            if(doesExist==false){
                //return correct message
                result = "Animal does not exist!";
            }
            
            //catches SQL exception & prints exception message
        }catch (SQLException e){System.out.println(e.toString());}
        
        //returns result
        return result;
    }
    
    public String processInput(String s){
        String output=""; //blank string
        
        //SEARCHING ---------------------------------------->
        if(s.startsWith("s~")){
             //s~bob=Species
             String name = s.replace("s~","");
             name = name.substring(0,s.indexOf("=")-2);
             String table = s.substring(s.indexOf("=")+1, s.length());
            
             output= checkSearchName(name, table);
        } else
            
        
        //LOGIN --------------------------------------------
        if(s.startsWith("login~")){    
            //login~username;password
            s = s.replace("login~", "");
            //username;password
            String lusername = s.substring(0,s.indexOf(";"));
            String lpassword = s.substring(s.indexOf(";")+1, s.length());
            
            output = doesUserExist(lusername, lpassword);
            
        } else
            
        //INSERTING ----------------------------------------->
        if(s.startsWith("i~")){
             //i~Animals;animalID,animalName,description,speciesID
             //i~Species;speciesID,speciesName
             s = s.replace("i~", "");
             
             //Animals;animalID,animalName,description,speciesID
             if(s.startsWith("Animals")){
                 s = s.replace("Animals;", "");//animalID,animalName,description,speciesID
                
                 String animalID = s.substring(0,s.indexOf(","));   
                 s = s.replace(animalID+",", "");//animalName,description,speciesID
                 
                 String animalName = s.substring(0,s.indexOf(","));
                 s = s.replace(animalName+",",""); //description,speciesID
                 
                 String description = s.substring(0,s.indexOf(","));
                 s = s.replace(description+",", "");  //speciesID
                 String speciesIDFK = s;
                
                output = insertIntoAnimals(animalID, animalName, description, speciesIDFK);
                 
             }else 
                 
             //~Species;speciesID,speciesName    
             if(s.startsWith("Species")){
                 s = s.replace("Species;", "");  //speciesID,speciesName   
                 
                 String speciesID = s.substring(0,s.indexOf(","));
                 s = s.replace(speciesID+",", "");
                 
                 String speciesName = s;  //speciesName

                output = insertIntoSpecies(speciesID, speciesName);
             }
             
        } else
        
            
            
        //DELETING ------------------------------------------>   
        if(s.startsWith("d~")){
            //d~animalID 
            s = s.replace("d~", "");
            //animalID
            String animalID = s;
             output= deleteRecord(animalID);
        }else
        
        //SHUTDOWN----------------------------------------->
        if(s.equals("shutdown")){ //if request = shutdown
            output = "shutdown"; //then return shutdown
        }
        else //even though cannot have invalid statement but 
            // just returning
        //INVALID STATEMENT ---------------------------------> 
        {
            output = "Invalid statement!";
        }
        
        
        return  output;
    }
    
}