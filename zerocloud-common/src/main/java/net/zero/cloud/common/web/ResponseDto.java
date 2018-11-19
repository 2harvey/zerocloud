package net.zero.cloud.common.web;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ResponseDto<T> {

    @NonNull
    private Integer code;
    @NonNull
    private String message;
    private T data;

    public ResponseDto(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseDto(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseDto(Integer code) {
        this.code = code;
        this.message = ResponseCode.getDesciption(code);
    }
}
