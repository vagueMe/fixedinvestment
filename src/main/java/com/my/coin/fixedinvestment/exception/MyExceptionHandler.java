package com.my.coin.fixedinvestment.exception;

import com.my.coin.fixedinvestment.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异常处理器
 */
@Slf4j
@ControllerAdvice
@RestController
public class MyExceptionHandler {
	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(MyException.class)
	public Result<?> handleRRException(MyException e){
		log.error(e.getMessage(), e);
		return Result.error(e.getMessage());
	}


	@ExceptionHandler(Exception.class)
	public Result<?> handleException(Exception e){
		log.error(e.getMessage(), e);
		return Result.error("操作失败，"+e.getMessage());
	}

}
