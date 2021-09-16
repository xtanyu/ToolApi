package com.xtyu.toolapi.model.support;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author: 小熊
 * @date: 2021/6/9
 * @description:phone 17521111022
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    private Integer status;


    private String message;


    private String devMessage;


    private T data;

    public BaseResponse(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }


    @NonNull
    public static <T> BaseResponse<T> ok(@Nullable String message, @Nullable T data) {
        return new BaseResponse<>(HttpStatus.OK.value(), message, data);
    }


    @NonNull
    public static <T> BaseResponse<T> ok(@Nullable String message) {
        return ok(message, null);
    }


    public static <T> BaseResponse<T> ok(@Nullable T data) {
        return new BaseResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }
}
