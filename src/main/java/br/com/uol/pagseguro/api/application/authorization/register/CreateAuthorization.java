package br.com.uol.pagseguro.api.application.authorization.register;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.application.authorization.AuthorizationRegistration;
import br.com.uol.pagseguro.api.application.authorization.AuthorizationRegistrationBuilder;
import br.com.uol.pagseguro.api.application.authorization.RegisteredAuthorization;
import br.com.uol.pagseguro.api.common.domain.PermissionCode;
import br.com.uol.pagseguro.api.credential.Credential;

public class CreateAuthorization {
	public static void main(String[] args) {


	    String appId = "app2072756820";
	    String appKey = "147785218888FD4554F12F9218A58BA3";

	    try{

	      final PagSeguro pagSeguro = PagSeguro.instance(Credential.applicationCredential(appId,
	          appKey), PagSeguroEnv.SANDBOX);


	      // Registra as autorizaÃ§Ãµes
	      AuthorizationRegistration authorizationRegistration =
	          new AuthorizationRegistrationBuilder()
	              .withNotificationUrl("www.lojatesteste.com.br/notification")
	              .withReference("REF_001")
	              .withRedirectURL("www.lojatesteste.com.br")
	              .addPermission(PermissionCode.Code.CREATE_CHECKOUTS)
	              .build();

	      RegisteredAuthorization ra = pagSeguro.authorizations().register(authorizationRegistration);
	      System.out.print(ra);

	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

}
