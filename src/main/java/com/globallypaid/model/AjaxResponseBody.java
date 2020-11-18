package com.globallypaid.model;

public class AjaxResponseBody {
  Integer code;
  String msg;
  String errorMsg;
  ChargeResponse result;

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public ChargeResponse getResult() {
    return result;
  }

  public void setResult(ChargeResponse result) {
    this.result = result;
  }
}
