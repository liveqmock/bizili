/**
 * Copyright 2014 Cenobit Technologies Inc. http://cenobit.es/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.vteba.struts2.json;

import com.opensymphony.xwork2.config.entities.ActionConfig;

import com.vteba.struts2.json.annotations.Json;

public interface JsonServices {

	public String[] determineExcludedFieldsNames(Class<?> actionClass, String methodName);

	public String[] determineExcludedFieldsNames(ActionConfig actionConfig);

	public Json getJsonAnnotation(Class<?> actionClass, String methodName);

	public Json getJsonAnnotation(ActionConfig actionConfig);
}