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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            
            try {
                

                while((line = bufferReader.readLine()) != null){
                    line = line.trim();
                    System.out.println(line);
                    /*Match wathever alphanumeric value and add quotes*/
                    Pattern regex = Pattern.compile("(\\w+)");
                    Pattern colonInBraket = Pattern.compile("(\"\\w+\")(?=(\\s*)(\\{))");
                    Matcher matcher = regex.matcher(line);
                    String lineModified = matcher.replaceAll(match->"\"" + match.group() + "\"");
                    matcher = colonInBraket.matcher(lineModified);
                    lineModified = matcher.replaceAll(match -> match.group(0)+ ":");
                    
                    
                    
                    System.out.println(lineModified);
                    
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
