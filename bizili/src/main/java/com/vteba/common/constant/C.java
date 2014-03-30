package com.vteba.common.constant;

public final class C {
	public static final class Subject {
		public static final String TREE = "c_subject_tree";
	}
	
	public static final class Resources {
		public static final String JSON = "c_resources_json";
	}
	
	public static final class Auth {
		public static final String JSON = "c_auth_json";
	}
	
	public static void main(String[] aa) {
		System.err.println(C.Subject.class.getName());
	}
}
