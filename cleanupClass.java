package logsDeletionPractice;
import java.io.File;
import java.io.*;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class cleanupClass {
	public static void main(String[] argv) throws Exception {
	      File logFolder = new File("hi");
	      boolean deleteStatus = false;
	      long folderSize = 0;
	      DateFormat df = new SimpleDateFormat("MMddyyyy"); 
		  Date today = new Date();
		  String todayDate = df.format(today); 
		  File backupLogFolder = new File("backup"+ todayDate);
	      boolean reStartServer = false;
	     // boolean stopServer = false;
	      //getting folder size
	      folderSize = getFolderSize(logFolder);
	      System.out.println("Folder size is: " +folderSize +" Bytes");
	      
	      //backup folder
	    	if(!logFolder.exists()){
	           System.out.println("Directory does not exist.");
	           System.exit(0);

	        }else{

	           try{
	        	   backupFolder(logFolder,backupLogFolder);
	           }catch(IOException e){
	        	e.printStackTrace();
	                System.out.println("Error while backing up, terminate and start again");
	           }
	        }
	    	System.out.println("Backup folder created");
	      
	      //deleting folder
	      if(folderSize > 2000000000) {
	    	  deleteFolder(logFolder);
	    	  deleteStatus = true;
	      }
	      if(deleteStatus == true)
	    	  System.out.println("Folder Deleted");
	      else
	    	  System.out.println("Error in deleting");
	      
	      //Restarting the instance.
	     //stopServer = runStopScript();
	     //System.out.println("server stopped");
	    // System.out.println(stopServer);
	     /* if(stopServer == true) {
	    	  startServer = runStartScript();
	      }*/
	      //System.out.println("server started");
	      //System.out.println(startServer);
	      reStartServer = runStopScript();
	      if(reStartServer == true) {
	    	  System.out.println("Server instance restarted");
	      }
	      
	      
	   }
	
	   //function to fetch the size of the log folder
	   public static long getFolderSize(File dir) {
		    long length = 0;
		    File[] files = dir.listFiles();
		 
		    int count = files.length;
		 
		    for (int i = 0; i < count; i++) {
		        if (files[i].isFile()) {
		            length += files[i].length();
		        }
		        else {
		            length += getFolderSize(files[i]);
		        }
		    }
		    return length;
	   } 

	   //function to create a backup of the log folder
	   public static void backupFolder(File src, File dest)
		    	throws IOException{

		    	if(src.isDirectory()){

		    		//if directory not exists, create it
		    		if(!dest.exists()){
		    		   dest.mkdir();
		    		}

		    		//list all the directory contents
		    		String files[] = src.list();

		    		for (String file : files) {
		    		   //construct the src and dest file structure
		    		   File srcFile = new File(src, file);
		    		   File destFile = new File(dest, file);
		    		   //recursive copy
		    		   backupFolder(srcFile,destFile);
		    		}

		    	}else{
		    		//if file, then copy it
		    		//Use bytes stream to support all file types
		    		    InputStream in = new FileInputStream(src);
		    	        OutputStream out = new FileOutputStream(dest);

		    	        byte[] buffer = new byte[1024];

		    	        int length;
		    	        //copy the file content in bytes
		    	        while ((length = in.read(buffer)) > 0){
		    	    	   out.write(buffer, 0, length);
		    	        }

		    	        in.close();
		    	        out.close();
		    	}
		    }
	   
	   //function to delete the original log folder
	   public static boolean deleteFolder(File dir) {
	      if (dir.isDirectory()) {
	         String[] children = dir.list();
	         for (int i = 0; i < children.length; i++) {
	            boolean success = deleteFolder (new File(dir, children[i]));
	            
	            if (!success) {
	               return false;
	            }
	         }
	      }
	      return dir.delete();
	   }
	   
	   //function to stop the server instance
	   public static boolean runStopScript() {
		    try {
	            Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"stopManagedWebLogic_custom.cmd Test_Server http://m4okia10:7001\"");
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }/*finally {
	    		 System.out.println("Server stopped");
	    		 boolean startStatus = runStartScript();
	    		 if(startStatus == true)
	    			 return true;
	    		 else
	    			 return false;
	    	 }*/
	        /*try {
	        	//System.out.println("Before running.");
	            p = run.exec("cmd.exe /k" +cmd);
	            //System.out.println("After running.");
	            //p.waitFor();
	            
	            System.out.println("RUN.COMPLETED.SUCCESSFULLY");
	            return true;
	 	   }
	        catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("ERROR.RUNNING.CMD");
	            //p.destroy();
	            return false;
	        }
	        finally {
	        	
	        	p.destroy();
	        }*/
	   }  
	   //function to start the server instance
  /*   public static boolean runStartScript() {
    	 try {
	            Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"startManagedWebLogic.cmd Test_Server http://m4okia10:7001\"");
	            System.out.println("Server Started");
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }

		   Runtime run = Runtime.getRuntime();
	        Process p = null;
	        String cmd = "startManagedWebLogic.cmd Test_Server http://m4okia10:7001";
	        try {
	            p = run.exec(cmd);
	            p.getErrorStream();
	            System.out.println("RUN.COMPLETED.SUCCESSFULLY");
	            return true;
	 	   }
	        catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("ERROR.RUNNING.CMD");
	            p.destroy();
	            return false;
	        }
	   }*/
}
