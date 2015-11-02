package com.abin.demo.dict;

public enum DictType {
	
	/**
	 * 查评计划状态
	 */
	PLAN_STATE("1"),
	
	/**
	 * 执行状态
	 */
	EXECUTION_STATE("2"),
	
	/**
	 * 查评规范审查标致
	 */
	APPRAISE_CHECK("3"),
	
	/**
	 * 查评规范发布标致
	 */
	APPRAISE_RELEASE("4"),
	
	/**
	 * 性别
	 */
	SEX("5"),
	
	/**
	 * 是否
	 */
	IS_NO("6"),
	
	/**
	 * 查评周期状态
	 */
	PERIODIC_STATE("7"),
	
	/**
	 * 查评周期封存情况
	 */
	PERIODIC_CLOSE("8"),
	
	/**
	 * 专家状态
	 */
	EXP_STATE("9"),
	
	/**
	 * 部门类型
	 */
	DEPT_TYPE("10");
	
	private String value;
	
	private DictType(String str) {
		this.value = str;
	}
	
	public String value() {
		return value;
	}
}
