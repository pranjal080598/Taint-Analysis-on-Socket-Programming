import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Arrays;

public class IdentifyTaint {
    public static void main(String[] args) {

      int server_taint_flag = 0;
      int port_sanitization_flag = 0;

      // convert the java code to text file
        String fileName = "Client.java";
        String outputFileName = String.format("%s.txt", fileName.replace('.', '_'));
        try (BufferedReader br = new BufferedReader(new FileReader(fileName));
             PrintWriter pw = new PrintWriter(new FileWriter(outputFileName))) {
            int count = 1;
            String line;
            while ((line = br.readLine()) != null) {
                pw.printf("%03d %s%n", count, line);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // list of rules for identifying taint incase of user input
        List<String> input_rules = Arrays.asList("new Scanner", "new BufferedReader", "readLine");

        // list of rules for identifying taint incase of connection to server
        List<String> connection_rules = Arrays.asList("new Socket");

        // list of rules for sanitization
        // only considering the regex validation as an example
        List<String> port_sanitization_rules = Arrays.asList("matches");

        // to check if input sanitization is done
        try {
            List<String> content = Files.readAllLines(Paths.get(outputFileName), StandardCharsets.UTF_8);

            // iterate through the list to check for any user input
            for (String s : content) {
              for (String s1 : port_sanitization_rules){
                if (s.contains(s1)) {
                    System.out.println("\nInput port number is sanitized: \n" + s );
                    System.out.println("\n\nHence, the taint is not propagated to the server uisng port number" );
                    port_sanitization_flag = 1;
                    break;
                  }
                }
            }
          }
          catch (Exception e) {
                 e.printStackTrace();
          }

if(port_sanitization_flag == 0){
        // convert the text file to a list
        try {
            List<String> content = Files.readAllLines(Paths.get(outputFileName), StandardCharsets.UTF_8);

            // iterate through the list to check for any user input
            for (String s : content) {
              for (String s1 : input_rules){
                if (s.contains(s1)) {
                    System.out.println("\nSource Taint could be found at: \n" + s );
                    break;
                  }
                }
            }

          // iterate through the list to check for any connection rules
            for (String s : content) {
              for (String s1 : connection_rules){
                if (s.contains(s1)) {
                    System.out.println("\nTaint could be propagated to Server using: \n" + s );
                    server_taint_flag = 1;
                    break;
                  }
                }
              }
          }
          catch (Exception e) {
                 e.printStackTrace();
          }

          // analyse the server code
          if (server_taint_flag == 1)
          {
            // convert the java code to text file
              String serverFileName = "Server.java";
              String serverOutputFileName = String.format("%s.txt", serverFileName.replace('.', '_'));
              try (BufferedReader br = new BufferedReader(new FileReader(serverFileName));
                   PrintWriter pw = new PrintWriter(new FileWriter(serverOutputFileName))) {
                  int count = 1;
                  String line;
                  while ((line = br.readLine()) != null) {
                      pw.printf("%03d %s%n", count, line);
                      count++;
                  }
              } catch (Exception e) {
                  e.printStackTrace();
              }

              // list of rules for identifying taint incase of connection to server
              List<String> server_connection_rules = Arrays.asList("new ServerSocket");
              try {
                  List<String> content = Files.readAllLines(Paths.get(serverOutputFileName), StandardCharsets.UTF_8);

              // iterate through the list to check for any server rules
                for (String s : content) {
                  for (String s1 : server_connection_rules){
                    if (s.contains(s1)) {
                        System.out.println("\nTaint is in server code at: \n" + s );
                        break;
                      }
                    }
                  }
              }
            catch (Exception e) {
                e.printStackTrace();
            }
          }
        }

}
}
