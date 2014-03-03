package com.darmody.buumanagementsystem.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;


/**
 * 2013.9.3 21:23
 * @author Caihuanyu
 * @content 工具类
 */

public class Utility {

	public final static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public final static SimpleDateFormat YMDdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final int BUFFER_SIZE = 16 * 1024; 
	
	public static String getSystemCurrentTime() {
		
		return df.format(new Date());
	}
	
	public static int dateCompare(String t1, String t2) throws ParseException {
		
		 Calendar c1=Calendar.getInstance();  
	     
		 Calendar c2=Calendar.getInstance();  
	      
		 c1.setTime(Utility.YMDdf.parse(t1));  
	     
		 c2.setTime(Utility.YMDdf.parse(t2));  
	       
	      return c1.compareTo(c2);  
		
//		if(date1.before(date2) || date1.equals(date2)) {
//			
//			return true;
//		}
//		
//		return false;
	}
	
	public static void createHtml(String content, String url) throws IOException {
		
			File newHtml = new File(url);

			newHtml.createNewFile();
			
			FileOutputStream fileOutput = new FileOutputStream(newHtml);
			
			fileOutput.write(content.getBytes("utf-8"));	
	}
	
	public static void copyFile(File src, File dst) {  
        try {  
            InputStream in = null;  
            OutputStream out = null;  
            try {  
                in = new BufferedInputStream(new FileInputStream(src),  
                        BUFFER_SIZE);  
                out = new BufferedOutputStream(new FileOutputStream(dst),  
                        BUFFER_SIZE);  
                byte[] buffer = new byte[BUFFER_SIZE];  
                while (in.read(buffer) > 0) {  
                    out.write(buffer);  
                }  
            } finally {  
                if (null != in) {  
                    in.close();  
                }  
                if (null != out) {  
                    out.close();  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
	
	public static void zipFolder(String inputFileName, String zipFileName) throws Exception {
    
		zip(zipFileName, new File(inputFileName));
    }

    private static void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        zip(out, inputFile, "resultDetails");
        out.close();
    }

    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
           File[] fl = f.listFiles();
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
           base = base.length() == 0 ? "" : base + "/";
           for (int i = 0; i < fl.length; i++) {
           zip(out, fl[i], base + fl[i].getName());
         }
        }else {
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
           FileInputStream in = new FileInputStream(f);
           int b;
           System.out.println(base);
           while ( (b = in.read()) != -1) {
            out.write(b);
         }
         in.close();
       }
    }
	
	public static void zipFiles(List<String> files, String zipFile) throws IOException {
		
		File file = new File(zipFile);
		
		System.out.println(zipFile);
		
		file.createNewFile();
		
		FileOutputStream out = new FileOutputStream(zipFile);
		
		ZipOutputStream zipOut = new ZipOutputStream(out);
		
		for(int i = 0; i < files.size(); i++) {
			
			FileInputStream in = new FileInputStream(files.get(i));
			
			String fileName = files.get(i).substring(files.get(i).lastIndexOf('/') + 1, files.get(i).length());
			
			ZipEntry entry = new ZipEntry(fileName);
			
			zipOut.putNextEntry(entry);
			
			int nNumber = 0;
			
			byte[] buffer = new byte[512];
			
			while((nNumber = in.read(buffer)) != -1) {
				
				zipOut.write(buffer, 0, nNumber);
			}
			
			in.close();
		}
		
		zipOut.close();
		
		out.close();
	}
}
