package henu.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;

import henu.util.ExcelReader;
import henu.util.ExcelWriter;

public class TestFile {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		File file =new File("config");
		File outfile=new File("config.zip");
		if(file.isDirectory()) {
			FileOutputStream fout=new FileOutputStream(outfile);
			ZipOutputStream zout = new ZipOutputStream(fout);
			zout.putNextEntry(new ZipEntry(file+File.separator));
			File[] files=file.listFiles();
			if(files!=null&&files.length!=0) {
				for(File f:files) {
					if(f.isFile()) {
						System.out.println(f.getName());
						//将这些文件都压缩到zip
						zout.putNextEntry(new ZipEntry(file+File.separator+f.getName()));
						zout.write(FileUtils.readFileToByteArray(f));
						zout.flush();
						zout.closeEntry();
					}
				}
			}
			zout.flush();
			zout.closeEntry();
			zout.close();
			fout.close();
		}
		
//		
//		ExcelReader reader=ExcelReader.readFile(file);
//		
		System.out.println(outfile.getAbsolutePath());
//		
//		FileOutputStream out;
//		try {
//			out = new FileOutputStream(file);
//
//			ExcelWriter writer = new ExcelWriter();
//			writer.write(0, 0, "123");
//			writer.write(0, 1, "123");
//			
//			
//			writer.saveToFile(out);
//			writer.close();
//			out.close();
//			System.out.println(file.getAbsolutePath());
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
