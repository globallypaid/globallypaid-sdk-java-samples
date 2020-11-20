# GloballyPaid Java SDK Samples (Example of use)

The simple usage of the [GloballyPaid Java SDK][globallypaid-sdk-java] library.

## Usage

Clone this repository.

## Sample

Sample Spring Boot MVC application that showcases charge sale transaction 
done through a UI credit card form using the [Globally Paid JS SDK][globallypaid-js-sdk].

It uses this dependency in the POM:

```xml
<dependency>
  <groupId>com.globallypaid</groupId>
  <artifactId>globallypaid-java</artifactId>
  <version>1.0.1</version>
</dependency>
```

To run the project sample you need to set the following 
Environment variables (see [GloballyPaid Java SDK][globallypaid-sdk-java-env]):

* `PUBLISHABLE_API_KEY`
* `APP_ID`
* `SHARED_SECRET`
* `USE_SANDBOX`

##### Make a Charge Sale Transaction with Javascript SDK integration
```java
import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.http.RequestOptions;
import com.globallypaid.model.ChargeRequest;
import com.globallypaid.model.ChargeResponse;
import com.globallypaid.model.PaymentInstrumentToken;
import org.springframework.stereotype.Service;

@Service
public class ChargeService {

  public ChargeResponse charge(PaymentInstrumentToken paymentInstrumentToken)
      throws GloballyPaidException {

    ChargeResponse chargeResponse = null;

    GloballyPaid globallyPaid =
        new GloballyPaid(
            Config.builder()
                .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                .appId(System.getenv("APP_ID"))
                .sharedSecret(System.getenv("SHARED_SECRET"))
                .sandbox(System.getenv("USE_SANDBOX"))
                .build());

    if (paymentInstrumentToken != null && !paymentInstrumentToken.getId().isEmpty()) {
      RequestOptions requestOptions =
          RequestOptions.builder().connectTimeout(50 * 1000).readTimeout(100 * 1000).build();

      ChargeRequest gpChargeRequest =
          ChargeRequest.builder()
              .source(paymentInstrumentToken.getId())
              .amount(160)
              .currencyCode("USD")
              .clientCustomerId("XXXXXXX")
              .clientInvoiceId("XXXXXX")
              .clientTransactionId("XXXXXXXXX")
              .clientTransactionDescription("Charge Sale sample!")
              .capture(true)
              .savePaymentInstrument(false)
              .build();

      chargeResponse = globallyPaid.charge(gpChargeRequest, requestOptions);
      System.out.println(chargeResponse.toString());
    }
    return chargeResponse;
  }
}
```
Please visit [GloballyPaid Java SDK][examples] for more examples.

##### Run

Run the Sample MVC Application, fill the form with test data and click `Submit` button.

[java-sdk-sample]: https://github.com/globallypaid/globallypaid-sdk-java-samples
[globallypaid-js-sdk]: https://github.com/globallypaid/js-sdk-v2-sample
[globallypaid-sdk-java-env]: https://github.com/globallypaid/globallypaid-sdk-java#env-variables
[globallypaid-sdk-java]: https://github.com/globallypaid/globallypaid-sdk-java
[examples]: https://github.com/globallypaid/globallypaid-sdk-java/blob/master/src/main/java/com/globallypaid/example/
