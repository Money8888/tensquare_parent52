package entity;

public class Result {
    private boolean flags;
    private Integer code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(boolean flags, Integer code, String message) {
        this.flags = flags;
        this.code = code;
        this.message = message;
    }

    public Result(boolean flags, Integer code, String message, Object data) {
        this.flags = flags;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isFlags() {
        return flags;
    }

    public void setFlags(boolean flags) {
        this.flags = flags;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
