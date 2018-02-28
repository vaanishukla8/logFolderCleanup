package logsDeletionPractice;
import java.io.File;
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
	      File logFolder = new File("C:\\Users\\Vaani\\Desktop\\hi");
	      boolean deleteStatus = false;
	      long folderSize = 0;
	      DateFormat df = new SimpleDateFormat("MMMddyyyy"); 
		  Date today = new Date();
		  String todayDate = df.format(today); 
		  File backupLogFolder = new File("C:\\Users\\Vaani\\Desktop\\backup"+ todayDate);
	      
	      //getting folder size
	      folderSize = getFolderSize(logFolder);
	      System.out.println("Folder size is: " +folderSize +"Bytes");
	      
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
	      if(folderSize > 100000) {
	    	  deleteFolder(logFolder);
	    	  deleteStatus = true;
	      }
	      if(deleteStatus == true)
	    	  System.out.println("Folder Deleted");
	      else
	    	  System.out.println("Error in deleting");
	      
	   }
	
	   //function to fetch the file of the log folder
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
}
