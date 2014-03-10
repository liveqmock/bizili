package com.vteba.web.action;

import java.text.DateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;

public class CustomFormatter extends CustomDateEditor {

	public CustomFormatter(DateFormat dateFormat, boolean allowEmpty) {
		super(dateFormat, allowEmpty);
		
	}

	public CustomFormatter(DateFormat dateFormat, boolean allowEmpty,
			int exactDateLength) {
		super(dateFormat, allowEmpty, exactDateLength);
	}

}
