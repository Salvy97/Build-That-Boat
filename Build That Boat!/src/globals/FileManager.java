package globals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileManager 
{		
	public static boolean exists(String path) 
	{
	    File file = new File(path);
		return file.exists();
	}
	
	public static void writeRow(String path, String txt) 
	{
	    FileInputStream fstream = null; 
	    DataInputStream in = null;
	    BufferedWriter out = null;
	    
	    try 
	    {
	       // apro il file
	       fstream = new FileInputStream(path);
	     
	       // prendo l'inputStream
	       in = new DataInputStream(fstream);
	       BufferedReader br = new BufferedReader(new InputStreamReader(in));
	       String strline;
	       StringBuilder fileContent = new StringBuilder();
	     
	       // Leggo il file riga per riga
	       while ((strline = br.readLine()) != null) 
	       {
	    	   fileContent.append(strline);
	    	   fileContent.append(System.getProperty("line.separator"));
		   }

	       fileContent.append(txt);
		   fileContent.append(System.getProperty("line.separator"));
	       // Sovrascrivo il file con il nuovo contenuto (aggiornato)
	       FileWriter fstreamWrite = new FileWriter(path);
	       out = new BufferedWriter(fstreamWrite);
	       out.write(fileContent.toString());
	    } 
	    catch (Exception e) 
	    {
	       e.printStackTrace();
	    } 
	    finally 
	    {
	       // chiusura dell'output e dell'input
	       try 
	       {
	          fstream.close();
	          out.flush();
	          out.close();
	          in.close();
	       } 
	       catch (IOException e) 
	       {
	          e.printStackTrace();
	       }
	    }
	}
	
	public static void emptyFile(String path) 
	{    
	    try 
	    {
	        File file = new File(path);
	        FileWriter fw = new FileWriter(file);
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write("");
	        bw.flush();
	        bw.close();
	    }
	    catch(IOException e) 
	    {
	        e.printStackTrace();
	    }
	}
	
	public static void deleteRow(String path,String a) 
	{
	    FileInputStream fstream = null; 
	    DataInputStream in = null;
	    BufferedWriter out = null;
	    
	    try 
	    {
	       // apro il file
	       fstream = new FileInputStream(path);
	     
	       // prendo l'inputStream
	       in = new DataInputStream(fstream);
	       BufferedReader br = new BufferedReader(new InputStreamReader(in));
	       String strline;
	       StringBuilder fileContent = new StringBuilder();
	     
	       // Leggo il file riga per riga
	       while ((strline = br.readLine()) != null) 
	       {
	          if(!strline.equals(a))
	          {
	        	  fileContent.append(strline);
	        	  fileContent.append(System.getProperty("line.separator"));
			  }
		   }
	  
	       // Sovrascrivo il file con il nuovo contenuto (aggiornato)
	       FileWriter fstreamWrite = new FileWriter(path);
	       out = new BufferedWriter(fstreamWrite);
	       out.write(fileContent.toString());
	     
	    } 
	    catch (Exception e) 
	    {
	       e.printStackTrace();
	    } 
	    finally 
	    {
	       // chiusura dell'output e dell'input
	       try 
	       {
	          fstream.close();
	          out.flush();
	          out.close();
	          in.close();
	       } 
	       catch (IOException e) 
	       {
	          e.printStackTrace();
	       }
	    }
	 }
	
	public static ArrayList<String> content(String path) 
	{
	    FileInputStream fstream = null; 
	    DataInputStream in = null;

	    ArrayList<String> s=new ArrayList<String>();
	    
	    try 
	    {
	       // apro il file
	       fstream = new FileInputStream(path);
	     
	       // prendo l'inputStream
	       in = new DataInputStream(fstream);
	       BufferedReader br = new BufferedReader(new InputStreamReader(in));
	       String strline;
	     
	       // Leggo il file riga per riga
	       while ((strline = br.readLine()) != null) 	 
	    	   s.add(strline);
	       
	       br.close();  	
	       return s;     
	    } 
	    catch (Exception e) 
	    {
	    	e.printStackTrace();
	    }
	    return null;
	}
}
