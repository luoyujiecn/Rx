package com.kxj.rx.http.result;

public class ServerListResult<T> {
    private String reason;
    private int error_code;
    private DataList<T> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public DataList<T> getResult() {
        return result;
    }

    public void setResult(DataList<T> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ServerListResult{" +
                "reason='" + reason + '\'' +
                ", error_code=" + error_code +
                ", result=" + result +
                '}';
    }
}
