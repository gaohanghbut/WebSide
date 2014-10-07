package cn.hang.mvc.pipeline.valve;

import java.lang.reflect.Method;

public class ControlInvokeResult {
    private final Object result;
    private final Method method;
    public ControlInvokeResult(Object result, Method method) {
        super();
        this.result = result;
        this.method = method;
    }
    public boolean isNull() {
        return getResult() == null;
    }
    public Method getMethod() {
        return method;
    }
    public Object getResult() {
        return result;
    }
}