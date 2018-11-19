package net.zero.cloud.common.web;


public enum  ResponseCode {

  SERVER_EXCEPTION(100103, "服务不可用"),
  FIELD_ERROR(100100,"请求字段错误"),
  INVALID_REQUEST(100104,"非法请求"),
  INVALID_USER(630001,"无效用户"),
  INVALID_MECHANT(630031,"无效的商户编号"),
  INVALID_SIGN(630032,"签名错误"),
  INVALID_ASSET(630040,"用户资产无效"),
  Insufficient_assets(630041,"资产不足"),
  SUCCESS(100200, "成功");


  private Integer code;
  private String description;

  ResponseCode(Integer code, String description){
    this.code = code;
    this.description = description;
  }

  ResponseCode(Integer code){
    this.code = code;
    this.description = getDescription();
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  static public String getDesciption(Integer code){
    for (ResponseCode c:ResponseCode.values()){
      if (c.getCode()==code){
        return c.getDescription();
      }
    }
    return null;
  }

  public String getDescription() {
    if (this.description==null){
      return getDesciption(this.code);
    }else {
      return this.description;
    }
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
