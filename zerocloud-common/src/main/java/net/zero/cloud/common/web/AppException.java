package net.zero.cloud.common.web;

import lombok.Data;

@Data
public class AppException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private Object attach;
  private Integer errCode;

  private String message;

  private String[] format;

  public AppException(Integer errCode) {
    super();
    this.errCode = errCode;
    this.message = ResponseCode.getDesciption(errCode);
  }

  public AppException(Integer errCode, String message, Object... format) {
    super();
    this.errCode = errCode;
    this.message = String.format(message,format);
  }
/*

  public AppException(Integer errCode, String message, Object attach, String... format) {
    super();
    this.errCode = errCode;
    this.message = String.format(message,format);
    //this.format = format;
    this.attach = attach;
  }
*/

  public String getCodeMessage() {
    return this.message;
  }
/*
  @Override
  public String getRespMsg() {
    return "respCode:" + this.getErrCode() + ":" + (this.respMsg==null?"【空白】":this.respMsg);
  }
*/
}
