/**
 * Copyright 2014 NAVER Corp.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.navercorp.pinpoint.plugin.tomcat.interceptor;

import com.navercorp.pinpoint.bootstrap.MetadataAccessor;
import com.navercorp.pinpoint.bootstrap.context.AsyncTraceId;
import com.navercorp.pinpoint.bootstrap.context.SpanEventRecorder;
import com.navercorp.pinpoint.bootstrap.context.Trace;
import com.navercorp.pinpoint.bootstrap.context.TraceContext;
import com.navercorp.pinpoint.bootstrap.interceptor.MethodDescriptor;
import com.navercorp.pinpoint.bootstrap.interceptor.SpanAsyncEventSimpleAroundInterceptor;
import com.navercorp.pinpoint.bootstrap.plugin.annotation.Group;
import com.navercorp.pinpoint.bootstrap.plugin.annotation.Name;
import com.navercorp.pinpoint.plugin.tomcat.TomcatConstants;

/**
 * 
 * @author jaehong.kim
 *
 */
@Group(TomcatConstants.TOMCAT_SERVLET_ASYNC_SCOPE)
public class AsyncContextImplDispatchMethodInterceptor extends SpanAsyncEventSimpleAroundInterceptor implements TomcatConstants {

    public AsyncContextImplDispatchMethodInterceptor(TraceContext traceContext, MethodDescriptor methodDescriptor, @Name(METADATA_ASYNC_TRACE_ID) MetadataAccessor asyncTraceIdAccessor) {
        super(traceContext, methodDescriptor, asyncTraceIdAccessor);
    }

    @Override
    protected void doInBeforeTrace(SpanEventRecorder recorder, AsyncTraceId asyncTraceId, Object target, Object[] args) {
        recorder.recordServiceType(TOMCAT_METHOD);
    }

    @Override
    protected void doInAfterTrace(SpanEventRecorder recorder, Object target, Object[] args, Object result, Throwable throwable) {
        recorder.recordApi(methodDescriptor);
        recorder.recordException(throwable);
    }
}