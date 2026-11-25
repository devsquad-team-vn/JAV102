package com.polycoffee.service;

/**
 * Doi tuong dong goi ket qua xu ly tu tang Service tra ve cho Servlet.
 *
 * @param <T> Kieu du lieu tra ve (Entity hoac du lieu nhap tam khi validate loi)
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
public class ServiceResult<T> {
    private final boolean success;
    private final String code;
    private final String message;
    private final T data;

    private ServiceResult(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ServiceResult<T> ok(String message, T data) {
        return new ServiceResult<>(true, "OK", message, data);
    }

    public static <T> ServiceResult<T> ok(String code, String message, T data) {
        return new ServiceResult<>(true, code, message, data);
    }

    public static <T> ServiceResult<T> fail(String message) {
        return new ServiceResult<>(false, "ERROR", message, null);
    }

    public static <T> ServiceResult<T> fail(String code, String message, T data) {
        return new ServiceResult<>(false, code, message, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
