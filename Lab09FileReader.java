//Chase Moskowitz

package com.mycompany.lab09filereader;

import java.util.Scanner; 
import java.io.*;

public class Lab09FileReader 
{

    public static void main(String[] args) throws IOException
    {
       String line = null;
       String zipCode = null;
       String city = null;
       String[] token;
       int badRecordCount=0;
       int RecordCount=0;
       int poBoxcount=0;
       int militaryCount=0;
       int standardCount=0;
       int njCount=0;
       int otherCount=0;
       
        PrintWriter outFile = new PrintWriter("BadZips.txt");
        PrintWriter outFileNJ = new PrintWriter("NJZips.txt");
         
        File csvFile = new File("free-zipcode-database.csv");
        Scanner csvFileScanner = new Scanner(csvFile);
        
        if (csvFileScanner.hasNext())
        {
           line=csvFileScanner.nextLine();
        }
       
       while (csvFileScanner.hasNext())
       {
           RecordCount ++;
           line=csvFileScanner.nextLine();
           token = line.split(",");
           
          for (int i=0; i<token.length; i++)
          {
             token[i]= token[i].replace('"',' ');
             token[i]=token[i].trim();
          }
          
          if (token.length!=19)
          {
              badRecordCount ++;
              outFile.println(line);         
          }
          else
          {
              if (token[6].equalsIgnoreCase("Po Box Only"))
              {
                  poBoxcount ++;
              }
              else if (token[6].equalsIgnoreCase("Military"))
              {
                  militaryCount++;
              }
              else if (token[6].equalsIgnoreCase("Standard"))
              {
                  standardCount++;
                  
                  if (token[4].equalsIgnoreCase("NJ"))  
                  {
                      njCount++;
                      zipCode=token[0];
                      city=token[3];
                      
                      outFileNJ.println(zipCode + ", " + city); 
                  }
              }
              else 
              {
                  otherCount ++;
              }
          }
       }
               
        
        outFile.close();
        outFileNJ.close();
        csvFileScanner.close();
        
        System.out.printf("%-10s%,20d", "# Po Box", poBoxcount);
        System.out.printf("\n%10s%,20d", "# Military", militaryCount);
        System.out.printf("\n%10s%,20d - NJ %,d", "# Standard",
                standardCount, njCount);
        System.out.printf("\n%-10s%,20d", "# Other", otherCount);
        System.out.printf("\n\n%-10s%,12d", "Total Records Read", RecordCount);
        System.out.printf("\n\n%-10s%,14d", "Bad Records Read", badRecordCount);
    }
}