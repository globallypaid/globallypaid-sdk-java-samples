package com.globallypaid.service;

import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.http.RequestOptions;
import com.globallypaid.model.ChargeRequest;
import com.globallypaid.model.ChargeResponse;
import com.globallypaid.model.PaymentInstrumentToken;
import java.io.IOException;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class ChargeService {

  public static final String GLOBALLYPAID_USE_SANDBOX = "GLOBALLYPAID_USE_SANDBOX";

  public ChargeResponse charge(PaymentInstrumentToken paymentInstrumentToken)
      throws IOException, GloballyPaidException {

    ChargeResponse chargeResponse = null;

    String sandbox =
        Objects.isNull(System.getenv(GLOBALLYPAID_USE_SANDBOX))
            ? "true"
            : System.getenv(GLOBALLYPAID_USE_SANDBOX);
    GloballyPaid globallyPaid =
        new GloballyPaid(
            Config.builder()
                .apiKey(System.getenv("GLOBALLYPAID_API_KEY"))
                .appIdKey(System.getenv("GLOBALLYPAID_APP_ID_KEY"))
                .sharedSecretApiKey(System.getenv("GLOBALLYPAID_SHARED_SECRET_API_KEY"))
                .sandbox(sandbox)
                .build());

    if (paymentInstrumentToken != null && !paymentInstrumentToken.getId().isEmpty()) {
      RequestOptions requestOptions =
          RequestOptions.builder().connectTimeout(50 * 1000).readTimeout(100 * 1000).build();

      ChargeRequest gpChargeRequest =
          ChargeRequest.builder()
              .source(paymentInstrumentToken.getId())
              .amount(160)
              .currencyCode("USD")
              .clientCustomerId("4444687")
              .clientInvoiceId("123456")
              .clientTransactionId("154896575")
              .clientTransactionDescription("ChargeWithToken from sdk client!")
              .capture(true)
              .savePaymentInstrument(false)
              .build();

      chargeResponse = globallyPaid.charge(gpChargeRequest, requestOptions);
      System.out.println(chargeResponse.toString());
    }
    return chargeResponse;
  }
}
