package com.hansol.timereport.global.exception;

import com.hansol.timereport.global.exception.response.BaseResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
	BaseResponseStatus status;
}
