package com.fc.pay.bill.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fc.pay.common.constants.PayConstants;

/**
 * zip工具类
 * 
 * @author zhanjq
 *
 */
public class ZipUtil {
	
	private static final Logger log = LoggerFactory.getLogger(ZipUtil.class);

	private static final int DEFAULT_BUFFER_LENGTH = 1024;

	private static final Charset DEFAULT_CHARSET = Charset.forName(PayConstants.UTF8_ENCODING);
	
	/**
	 * 解压文件(指定字符集)
	 * @param sourceZipPath
	 * @param targetDirPath
	 * @throws IOException
	 */
	public static boolean unzip(String sourceZipPath, String targetDirPath, Charset charset) {
		
		log.info("******************账单zip解压开始********************");
		
		/********************** 检查zip压缩文件路径合法性 *********************/

		if (StringUtils.isEmpty(sourceZipPath)) {
			log.error("zip文件参数为空");
			return false;
		}
		
		if(!isEndsWithZip(sourceZipPath)){
			log.error("目标zip文件缺少zip后缀");
			return false;
		}

		File sourceZip = new File(sourceZipPath);
		if (sourceZip == null || !sourceZip.exists() || !sourceZip.isFile()) {
			log.error("zip文件不存在");
			return false;
		}

		if (!sourceZip.canRead()) {
			log.error("zip文件不可读");
			return false;
		}
		
		/********************** 检查zip解压缩目录路径合法性 *********************/
		
		if (StringUtils.isEmpty(targetDirPath)) {
			log.error("解压解压缩目录参数为空");
			return false;
		}

		File targetDir = new File(targetDirPath);
		if (!targetDir.exists()) {
			if (!targetDir.mkdirs()) {
				log.error("创建解压目录失败");
				return false;
			}
			log.info("创建解压目录成功");
		}
		
		boolean isError = false;
		ZipArchiveInputStream is = null;
		try {
			is = new ZipArchiveInputStream(new BufferedInputStream(
					new FileInputStream(sourceZip), DEFAULT_BUFFER_LENGTH), charset.name());
			ZipArchiveEntry entry = null;
			while ((entry = is.getNextZipEntry()) != null) {
				if (entry.isDirectory()) {
					File directory = new File(targetDir, entry.getName());
					directory.mkdirs();
				} else {
					OutputStream os = null;
					try {
						os = new BufferedOutputStream(new FileOutputStream(
								new File(targetDir, entry.getName())),
								DEFAULT_BUFFER_LENGTH);
						IOUtils.copy(is, os);
					} finally {
						IOUtils.closeQuietly(os);
					}
				}
			}
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
			isError = true;
		} catch (IOException e) {
			log.error(e.getMessage());
			isError = true;
		} finally {
			IOUtils.closeQuietly(is);
		}
		
		log.info("******************账单zip解压结束********************");
		
		return !isError;
	}
	
	/**
	 * 解压文件(默认UTF-8)
	 * @param zipFilePath
	 * @param outDirStr
	 * @throws IOException
	 */
	public static void unzip(String zipFilePath, String outDirStr) {
		unzip(zipFilePath, outDirStr, DEFAULT_CHARSET);
	}
	
	/**
	 * 压缩文件或目录
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	public static boolean zip(File source, File target) throws Exception {  		
		log.info("******************账单zip压缩开始********************");
        File targetDir = target.getParentFile();  
        if (!targetDir.exists()) {
        	targetDir.mkdirs(); 
        }  
        /** 压缩文件 */
        if (source.isFile()) {  
            try {
				zipFile(source, target);
			} catch (IOException e) {
				log.error(e.getMessage());
				return false;
			}  
            log.info("******************账单zip压缩结束********************");
            return true;
        } 
        /** 压缩目录 */    
        ZipArchiveOutputStream zaos = null; 
        try {  
        	zaos = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(target), DEFAULT_BUFFER_LENGTH));  
            zipDir(zaos, source, "");  
            zaos.closeArchiveEntry();
        } catch (FileNotFoundException e) {
			log.error(e.getMessage());
			return false;
		} catch (IOException e) {
			log.error(e.getMessage());
			return false;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		} finally {  
            IOUtils.closeQuietly(zaos);  
        }    
        log.info("******************账单zip压缩结束********************");
        return true;
    }  
	
	/**
	 * 压缩文件
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException 
	 * @throws IOException
	 */
	private static void zipFile(File sourceFile, File targetFile) throws IOException {
		log.info("******************文件********************");
		ZipArchiveOutputStream zaos = null;
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(sourceFile),
					DEFAULT_BUFFER_LENGTH);
			zaos = new ZipArchiveOutputStream(new BufferedOutputStream(
					new FileOutputStream(targetFile), DEFAULT_BUFFER_LENGTH));
			ZipArchiveEntry entry = new ZipArchiveEntry(sourceFile.getName());
			entry.setSize(sourceFile.length());
			zaos.putArchiveEntry(entry);
			IOUtils.copy(is, zaos);
			zaos.closeArchiveEntry();
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(zaos);
		}
	}
	
	/**
	 * 压缩目录
	 * @param zaos
	 * @param srcDir
	 * @param targetDir
	 * @throws IOException
	 */
	private static void zipDir(ZipArchiveOutputStream zaos, File sourceDir, String targetDir) throws Exception {  
		log.info("******************目录********************");
        File[] fileList = sourceDir.listFiles();  
        if (fileList == null || fileList.length < 1) {  
            throw new Exception("待压缩文件不存在");
        }  
        InputStream is = null; 
        for (int i = 0; i < fileList.length; i++) {  
            File file = fileList[i];  
            if(file.getName().toLowerCase().endsWith(".zip")){
            	log.debug("zip文件不压缩>>>"+file.getName());
            	continue;
            }
            zaos.putArchiveEntry(new ZipArchiveEntry(file, targetDir + file.getName()));  
            //判断此文件是否是一个文件夹 
            if (file.isDirectory()) {  
                zipDir(zaos, file, targetDir + file.getName() + "/");
            } else {  
                try {  
                    is = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_LENGTH);  
                    IOUtils.copy(is, zaos);  
                } finally {  
                    IOUtils.closeQuietly(is);
                }  
            }  
        }  
    }  
	
	/**
	 * 判断是否是zip文件后缀
	 * 
	 * @param fileName
	 * @return
	 */
	private static boolean isEndsWithZip(String fileName) {
		if (fileName != null && !"".equals(fileName.trim())) {
			if (fileName.endsWith(".ZIP") || fileName.endsWith(".zip")) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		
		log.info("解压结果:"+ZipUtil.unzip("D:/drilldata/zip/merchant/20161118/fengche_20161118.zip", "C:/merchant明细汇总", Charset.forName("gbk")));
	
		log.info("压缩结果:"+ZipUtil.zip(new File("C:/merchant明细汇总"),new File("C:/账务明细%^%^&^&123456ABC.zip")));
		
		
	}

}
