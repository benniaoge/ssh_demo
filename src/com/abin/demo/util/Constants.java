package com.abin.demo.util;

public class Constants {
	
	public static final class DeviceType {
		
		public static final String IDFA = "idfa";
		
		public static final String IMEI = "imei";
		
		public static final String MAC = "mac";
	}
	
	public static final class PlatformType {
		
		public static final String PC = "PC";
		
		public static final String MOBILE = "MOBILE";
		
	}
	
	public static final class DeviceFileStatus {
		
		public static final String INIT = "0";
		
		public static final String BUILD = "1";
		
		public static final String BUILDING = "2";
		
		public static final String FINISH = "3";
		
		public static final String DEL = "4";
		
	}
	
	public static final class TargetStatus {
		
		public static final Integer INIT = 0;
		
		public static final Integer CM_OK = 1;
		
	}
	
	public static final class DictTag {
		
		public static final String FILE_STATUS = "file_status";
		
		public static final String TARGET_STATUS = "target_status";
	}
	
	public static final class RedisKey {
		
		public static final String DEVICE_QUEUE = "mobile_user_data_mq";
		
		public static final String DEVICE_BAK_PREFIX = "target-";
		
		public static final String HZ_DEVICE_QUEUE = "HZ-BAK-DEVICE";
	}

}
