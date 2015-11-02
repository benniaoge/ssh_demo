package org.abin.core.struts.interceptor;

import java.io.File;
import java.util.Locale;

import org.abin.core.exception.BusinessException;
import org.apache.struts2.interceptor.FileUploadInterceptor;

import com.opensymphony.xwork2.ValidationAware;

public class MyFileUploadInterceptor extends FileUploadInterceptor {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected boolean acceptFile(Object action, File file, String filename, String contentType, String inputName, ValidationAware validation, Locale locale) {
        boolean fileIsAcceptable = false;

        // If it's null the upload failed
        if (file == null) {
        	String errMsg = "文件[" + inputName + "]不存在";
        	LOG.warn(errMsg);
        	throw new BusinessException(errMsg);
            
        } else if (maximumSize != null && maximumSize < file.length()) {
            String errMsg = "文件[" + filename + "]超过上传大小";
            LOG.warn(errMsg);
            throw new BusinessException(errMsg);
            
        } else if ((!allowedTypesSet.isEmpty()) && (!(allowedTypesSet.contains(contentType)))) {
            String errMsg = "文件[" + filename + "]类型错误";
            LOG.warn(errMsg);
            throw new BusinessException(errMsg);
            
        } else if ((!allowedExtensionsSet.isEmpty()) && (!allowedExtensionsSet.contains(filename))) {
            String errMsg = "文件[" + filename + "]类型错误";
            LOG.warn(errMsg);
            throw new BusinessException(errMsg);
            
        } else {
            fileIsAcceptable = true;
        }

        return fileIsAcceptable;
    }

}
