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
package es.cenobit.struts2.json.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class JsonInterceptor implements Interceptor {

    private static final long serialVersionUID = -1012900438685739652L;

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
//    	HttpServletRequest request = ServletActionContext.getRequest();
//        HttpServletResponse response = ServletActionContext.getResponse();
//        String contentType = request.getHeader("content-type");
//        if (contentType != null) {
//            int iSemicolonIdx;
//            if ((iSemicolonIdx = contentType.indexOf(";")) != -1)
//                contentType = contentType.substring(0, iSemicolonIdx);
//        }
//
//        Object rootObject = null;
////        if (this.root != null) {
////            ValueStack stack = invocation.getStack();
////            rootObject = stack.findValue(this.root);
////
////            if (rootObject == null) {
////                throw new RuntimeException("Invalid root expression: '" + this.root + "'.");
////            }
////        }
//
//        if ((contentType != null) && contentType.equalsIgnoreCase("application/json")) {
//            // load JSON object
//        	BufferedReader bufferReader = request.getReader();
//            String line;
//            StringBuilder buffer = new StringBuilder();
//
//            try {
//                while ((line = bufferReader.readLine()) != null) {
//                    buffer.append(line);
//                }
//            } catch (IOException e) {
//                
//            }
//
//        	Object obj = FastJsonUtils.fromJson(buffer.toString(), clazz);
//
//        }
//    	AsmUtils.get().invokeMethod(target, methodName, args);
        return invocation.invoke();
    }

}
