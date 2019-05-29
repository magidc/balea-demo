/*
 *
 *  Copyright 2019 magidc.io
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */
package io.magidc.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class UserIdFilter implements Filter {
	private static final String USER_ID_HEADER_NAME = "user-id";

	public static Long getRequestUserId() {
		if (RequestContextHolder.getRequestAttributes() == null)
			return null;
		return (Long) RequestContextHolder.getRequestAttributes().getAttribute(UserIdFilter.USER_ID_HEADER_NAME, RequestAttributes.SCOPE_REQUEST);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		RequestContextHolder.getRequestAttributes().setAttribute(USER_ID_HEADER_NAME, Long.parseLong(httpServletRequest.getHeader(USER_ID_HEADER_NAME)),
				RequestAttributes.SCOPE_REQUEST);
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
