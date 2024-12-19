package com.common.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseObject<T> {
    @Builder.Default
    @JsonProperty("isSuccess") // Sử dụng annotation này de giu isSuccess
    private boolean isSuccess = false;
    @Builder.Default
    private T data = null ;
    private String message;
    @JsonIgnore // Thêm annotation này để bỏ qua khi serialize( ko sinh them cái success)
    public boolean isSuccess() {
        return isSuccess;
    }
}
