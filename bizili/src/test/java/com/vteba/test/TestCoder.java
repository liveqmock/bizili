package com.vteba.test;

import com.vteba.ext.codegen.CodeBuilder;
import com.vteba.ext.codegen.DB;
import com.vteba.ext.codegen.KeyType;
import com.vteba.ext.codegen.TempType;

public class TestCoder {

	public static void main(String[] args) {
	    //项目绝对路径
		String rootPath = "D:/Documents/GitHub/bizili/bizili/";
		CodeBuilder builder = new CodeBuilder(rootPath, TempType.Base);
		builder.setConfigFilePath("src/main/resources/config.properties")
		.setSrcPath("src/main/java/")
		.schema("skmbw")
		.className("User")
		.setDb(DB.MySQL)// 可以不使用，只要jdbc url是正确的
		.keyType(KeyType.Long)
		.tableDesc("系统用户")
		.tableName("user")
		.module("com.vteba.finance2")
		.setPojo(false)
		.setMongo(false)
		.setSpringDao(true)
		.build();

	}

}
