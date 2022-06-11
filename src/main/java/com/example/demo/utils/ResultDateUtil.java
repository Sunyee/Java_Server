package com.example.demo.utils;

import com.example.demo.entity.Result;

public class ResultDateUtil {

    public static <T> Result<T> getResultSuc(T tClass, String msg) {
        Result<T> result = new Result<T>();
        result.setCode(0);
        result.setMsg(msg);
        result.setData(tClass);
        return result;
    }

    public static <T> Result<T> getResultFail(T tClass, String msg) {
        Result result = new Result();
        result.setCode(100);
        result.setMsg(msg);
        result.setData(msg);
        return result;
    }
}
