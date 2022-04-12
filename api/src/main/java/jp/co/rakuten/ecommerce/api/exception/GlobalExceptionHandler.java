package jp.co.rakuten.ecommerce.api.exception;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler  {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public String handelMissingServletRequestParameterException(MethodArgumentNotValidException e, Model model) {

        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        StringBuilder sb=new StringBuilder();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            sb.append("'").append(fieldError.getField()).append("' ").append(fieldError.getDefaultMessage()).append("! ");
        }
        stringObjectHashMap.put("message",sb.toString());
        return JSON.toJSONString(stringObjectHashMap);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public String handelNotFoundException(NotFoundException e, Model model) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("message",e.getMessage());
        return JSON.toJSONString(stringObjectHashMap);
    }

}
