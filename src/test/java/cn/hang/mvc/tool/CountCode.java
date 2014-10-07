package cn.hang.mvc.tool;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;

public class CountCode {

	/**
	 * @param args
	 * @throws IOException 
	 */
	static int linecount = 0;
	public static void main(String[] args) throws IOException {
		System.out.println("start...");
		File file = new File("C:/Users/Hang/Desktop/src");
		File outFile = new File("C:/Users/Hang/Desktop/code.java");
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));
		
		LinkedList<File> linkedList = new LinkedList<File>();
		linkedList.addLast(file);
		while (!linkedList.isEmpty()) {
			file = linkedList.removeFirst();
			if (file.isDirectory()) {
				linkedList.addAll(Arrays.asList(file.listFiles()));
			} else if (file.isFile()) {
				if (file.getName().endsWith(".java")) {
					mergeFile(out, file);
				}
			}
		}
		out.flush();
		out.close();
		System.out.println("end...");
		System.out.println("Line count : " + linecount);
	}
	
	static void merge(OutputStream out, String src) throws IOException {
		if (src.trim().equals("")) {
			return;
		}
		if (src.trim().startsWith("//")) {
			return;
		}
		if (src.trim().startsWith("/*")) {
			return;
		}
		if (src.trim().startsWith("*")) {
			return;
		}
		if (src.trim().endsWith("*/")) {
			return;
		}
		out.write(src.getBytes());
		out.write("/n".getBytes());
		System.out.println(src);
		linecount++;
	}
	
	static void mergeFile(OutputStream out, File src) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(src));
			String line;
			while ((line = in.readLine()) != null) {
				merge(out, line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
