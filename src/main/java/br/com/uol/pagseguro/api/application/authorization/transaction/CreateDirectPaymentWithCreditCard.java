package br.com.uol.pagseguro.api.application.authorization.transaction;

import java.math.BigDecimal;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.common.domain.builder.AddressBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.CreditCardBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentItemBuilder;
import br.com.uol.pagseguro.api.common.domain.enums.Currency;
import br.com.uol.pagseguro.api.common.domain.enums.State;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.transaction.register.DirectPaymentRegistrationBuilder;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;


public class CreateDirectPaymentWithCreditCard {
	public static void main(String[] args){
	    try{

	      String sellerEmail = "fehhgonnelli@hotmail.com";
	      String sellerToken = "15668CF2AC624BBFBAEFEB6404FD1CB0";

	      final PagSeguro pagSeguro = PagSeguro
	          .instance(new SimpleLoggerFactory(), new JSEHttpClient(),
	              Credential.sellerCredential(sellerEmail, sellerToken), PagSeguroEnv.SANDBOX);

	      // Checkout transparente (pagamento direto) com cartao de credito
	      TransactionDetail creditCardTransaction =
	          pagSeguro.transactions().register(new DirectPaymentRegistrationBuilder()
	              .withPaymentMode("default")
	              .withCurrency(Currency.BRL)
	              .addItem(new PaymentItemBuilder()//
	                  .withId("0001")//
	                  .withDescription("Produto PagSeguroI") //
	                  .withAmount(new BigDecimal(99999.99))//
	                  .withQuantity(1))
	              .withNotificationURL("www.sualoja.com.br/notification")
	              .withReference("LIBJAVA_DIRECT_PAYMENT")
	          ).withCreditCard(new CreditCardBuilder()
	              .withBillingAddress(new AddressBuilder() //
	                  .withPostalCode("99999999")
	                  .withCountry("BRA")
	                  .withState(State.XX)//
	                  .withCity("Cidade Exemplo")
	                  .withComplement("99o andar")
	                  .withDistrict("Jardim Internet")
	                  .withNumber("9999")
	                  .withStreet("Av. PagSeguro")
	              )
	                         
	              .withToken("token")
	          );
	      System.out.println(creditCardTransaction);

	    }catch (Exception e){
	      e.printStackTrace();
	    }
	  }

}
