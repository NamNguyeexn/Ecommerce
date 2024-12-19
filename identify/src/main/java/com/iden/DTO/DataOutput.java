package com.iden.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DataOutput<T> {
    @Builder.Default
    @JsonProperty("isSuccess") // Sử dụng annotation này de giu isSuccess
    private boolean isSuccess = false;
    @Builder.Default
    private T data = null ;
    private String message;

    public DataOutput<T> setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
        return this;
    }
    public DataOutput<T> setData(T data) {
        this.data = data;
        return this;
    }
    public DataOutput<T> setMessage(String message) {
        this.message = message;
        return this;
    }
    @JsonIgnore // Thêm annotation này để bỏ qua khi serialize( ko sinh them cái success)
    public boolean isSuccess() {
        return isSuccess;
    }
}
