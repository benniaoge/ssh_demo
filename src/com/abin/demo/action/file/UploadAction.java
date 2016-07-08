package com.abin.demo.action.file;

import java.io.File;

import org.abin.core.exception.BusinessException;
import org.abin.core.struts.action.BaseActionSupport;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.abin.demo.file.service.FileService;

@InterceptorRefs(value = { @InterceptorRef("myFileUpload"), @InterceptorRef("eboxWebStack") })
@Result(name = "search", location = "file-search.do", type = "redirect")
public class UploadAction extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private File file;

	private String fileFileName;

	private String fileContentType;

	private FileService fileService;

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String save() throws Exception {
		if (!"text/plain".equalsIgnoreCase(fileContentType)) {
			throw new BusinessException("文件类型错误.");
		}

		fileService.uploadFile(file, fileFileName);
		return "search";
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public FileService getFileService() {
		return fileService;
	}

	@Autowired
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

}
