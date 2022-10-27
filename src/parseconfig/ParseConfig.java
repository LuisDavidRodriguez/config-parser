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
import java.util.Arrays;
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
        String jsonRepresentation = "{";
        
        try {
            BufferedReader bufferReader = new BufferedReader(new FileReader(file));
            String line;
            
            try {
                

                while((line = bufferReader.readLine()) != null){
                    line = line.trim();
                    if(line.isEmpty()) continue;
                    System.out.println(line);
                    String lineModified;
                    /*Match wathever alphanumeric value and add quotes*/
                    Pattern regex = Pattern.compile("(\\w+)");
                    /*Match the world that infront have the culy bracket*/
                    Pattern colonInBraket = Pattern.compile("(\"\\w+\")(?=(\\s*)(\\{))");
                    /*Match the field*/
                    Pattern colonField = Pattern.compile("^(\"\\w+\")(?!.+\\{|\\b)");
                    /*Match if it is only a flag*/
                    Pattern isFlag = Pattern.compile("^(\"\\w+\":)$");
                    
                    Matcher matcher = regex.matcher(line);
                    lineModified = matcher.replaceAll(match->"\"" + match.group() + "\"");
                    
                    matcher = colonInBraket.matcher(lineModified);
                    lineModified = matcher.replaceAll(match -> match.group(0)+ ":");
                    
                    matcher = colonField.matcher(lineModified);
                    lineModified = matcher.replaceAll(match -> match.group()+ ":");
                    
                    matcher = isFlag.matcher(lineModified);
                    lineModified = matcher.replaceAll(match -> match.group() + true);
                    
                    // in case a space separated list items like port we want to
                    // create an string repesentation of a Json array
                    if(!lineModified.contains("{")){
                        String[] values = lineModified.split("\\s+");
                        
                        if(values.length > 2){
                            String[] ports = Arrays.copyOfRange(values, 1, values.length);
                            lineModified = values[0] + Arrays.toString(ports);
                        }
                        
                    }
                    
                    
                    jsonRepresentation += lineModified + ",";
                }
                
                jsonRepresentation += "}";
                
                // Mm I hadn't add the line to prevent the empty lines to pass
                // throug the prosses that is why it was adding extra comas
                // I will keep this lines then after some tests I can prune the
                // code
                // ok at the end just remove the {,
                // ,}
                // , at the end
                Pattern removeComas = Pattern.compile("(?<=\\{),|,(?=\\})|,+$");
                jsonRepresentation = jsonRepresentation.replaceAll(removeComas.toString(), "");
                // remove ,, comas
                jsonRepresentation = jsonRepresentation.replaceAll(",,+", ",");
                System.out.println(jsonRepresentation);
                

                
            } catch (IOException ex) {
                Logger.getLogger(ParseConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            
        }catch(FileNotFoundException e) {
            System.out.print(e.getMessage());
        }
    }
    
}
