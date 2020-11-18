package com.globallypaid.controller;

import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.model.AjaxResponseBody;
import com.globallypaid.model.ChargeResponse;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.service.ChargeService;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChargeController {

  ChargeService chargeService;

  @Autowired
  public ChargeController(ChargeService chargeService) {
    this.chargeService = chargeService;
  }

  @PostMapping("/api/charge")
  public ResponseEntity<?> charge(
      @RequestBody PaymentInstrumentToken paymentInstrumentToken, Errors errors)
      throws IOException {
    AjaxResponseBody result = new AjaxResponseBody();

    // If error, just return a 400 bad request, along with the error message
    if (errors.hasErrors()) {
      result.setMsg(
          errors.getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.joining(",")));

      return ResponseEntity.badRequest().body(result);
    }

    try {
      ChargeResponse chargeResponse = chargeService.charge(paymentInstrumentToken);
      if (Objects.isNull(chargeResponse)) {
        result.setMsg("No result found!");
      } else {
        result.setMsg("Success");
      }
      result.setResult(chargeResponse);
      return ResponseEntity.ok(result);
    } catch (GloballyPaidException e) {
      System.out.println(
          "ChargeSaleTrans ---> Code: "
              + (Objects.isNull(e.getCode()) ? 500 : e.getCode())
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getGloballyPaidError());
      result.setCode(Objects.isNull(e.getCode()) ? 500 : e.getCode());
      result.setMsg("Failed: " + e.getMessage());
      result.setErrorMsg("Api Error: " + e.getGloballyPaidError());
      return ResponseEntity.status(Objects.isNull(e.getCode()) ? 500 : e.getCode()).body(result);
    }
  }
}
