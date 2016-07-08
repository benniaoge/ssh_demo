package org.abin.core.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.abin.core.exception.BusinessException;

public class FileProcessor {

	private InputStream is;

	private OutputStream os;

	private FileReader fr;

	private FileWriter fw;

	private BufferedReader br;

	private BufferedWriter bw;

	private void check(File file) {
		String dir = file.getParent();
		
		try {
			File targetDirectory = new File(dir);

			if (!targetDirectory.exists()) {
				targetDirectory.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("Directory creation failed: " + dir, e);
		}
	}

	public boolean readFileByStream(File srcFile, File targetFile) {
		try {
			check(targetFile);
			
			is = new FileInputStream(srcFile);
			os = new FileOutputStream(targetFile);

			byte[] buffer = new byte[500];

			while (-1 != is.read(buffer, 0, buffer.length)) {
				os.write(buffer);
			}
		} catch (Exception e) {
			throw new BusinessException("File read failed.", e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	public boolean readFileByReader(File srcFile, File targetFile) {
		try {
			check(targetFile);
			
			fr = new FileReader(srcFile);
			fw = new FileWriter(targetFile);

			char[] c = new char[1024];
			while (-1 != fr.read(c)) {
				fw.write(c);
			}
		} catch (Exception e) {
			throw new BusinessException("File read failed.", e);
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	public boolean readFileByBuffer(File srcFile, File targetFile) {
		try {
			check(targetFile);
			
			fr = new FileReader(srcFile);
			br = new BufferedReader(fr);

			fw = new FileWriter(targetFile);
			bw = new BufferedWriter(fw);

			String s;
			while ((s = br.readLine()) != null) {
				bw.write(s + "\n");
			}
		} catch (Exception e) {
			throw new BusinessException("File read failed.", e);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	public long readFileForSize(File srcFile, File targetFile) {
		long size = 0;
		
		try {
			check(targetFile);
			
			fr = new FileReader(srcFile);
			br = new BufferedReader(fr);

			fw = new FileWriter(targetFile);
			bw = new BufferedWriter(fw);

			String s;
			while ((s = br.readLine()) != null) {
				bw.write(s + "\n");
				size++;
			}
		} catch (Exception e) {
			throw new BusinessException("File read failed.", e);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return size;
	}

	public List<String> readFileForList(File file) {
		List<String> contents = new ArrayList<String>();

		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			String s;
			while ((s = br.readLine()) != null) {
				contents.add(s);
			}
		} catch (Exception e) {
			throw new BusinessException("File read failed.", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return contents;
	}

}
