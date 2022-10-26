/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package parseconfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class ParseConfig {
    private static final Path path = Paths.get("configtest.txt");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println(path.toAbsolutePath());
        File file = new File(path.toAbsolutePath().toString());
        
        try {
            BufferedReader bufferReader = new BufferedReader(new FileReader(file));
            String line;
            HashMap<String,?> rutine = new HashMap();
            Set<String> rutineDeep = new HashSet();
            boolean createRutine = false;
            boolean createFields = false;
            int nestedLevel = 0;
            
            try {
                

                while((line = bufferReader.readLine()) != null){
                    line = line.trim();
                    System.out.println(line);
                    if(line.contains("{")){
                        createRutine = true;
                        nestedLevel++;
                    }
                    
                    if(line.contains("}")){
                        nestedLevel--;
                        if(nestedLevel < 0){
                            // if we reach -1 it means there is an error
                            // on the keys they are unbalansed
                            throw new Exception("Unbalanced brackets");
                        }
                    }
                    
                    if(!line.contains("{")) createFields = true;
                    
                    if(createRutine) {
                        // Create rutine
                        String rutineName = line.substring(0, line.indexOf("{"));
                        rutineDeep.add(rutineName);
                        createRutine = false;
                    }
                    
                    if(createFields) {
                        // split by spaces and check if the length is more
                        // than 2 so we have a key value pair
                        // otherwise we have a flag
                        createFields = false;

                        String[] fields = line.split("\\s+");
                        if(fields.length > 1){
                            HashMap<String, List<String>> keys = new HashMap<>();
                            List<String> options = new ArrayList<>();
                            String key = fields[0];
                            
                            for(int i = 1; i<fields.length; i++){
                                options.add(fields[i]);
                            }
                            
                            keys.put(fields[0], options);
                            
                            System.out.println("Values in keys");
                            System.out.println(keys.toString());
                            
                        }else {
                            // flag
                        }
                    }
                    
                   
                    
                }
                
            } catch (IOException ex) {
                Logger.getLogger(ParseConfig.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ParseConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            
        }catch(FileNotFoundException e) {
            System.out.print(e.getMessage());
        }
    }
    
}


class Section {
    public Section (){
        
    }
}
