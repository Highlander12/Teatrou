package br.com.teatrou.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;

import br.com.teatrou.config.RestApiController;
import br.com.teatrou.config.property.TeatrouApiProperty;

@RestApiController("tokens")
public class TokenResource {

	@Autowired
	private TeatrouApiProperty teatrouApiProperty;


	/**
	 * <p>
	 *  Invalida o token do usuario, Logout
	 * </p>
	 * @param req
	 * @param resp
	 */
	@DeleteMapping("/revoke")
	public void revoke(HttpServletRequest req, HttpServletResponse resp){
		Cookie cookie = new Cookie("refreshToken", null);

		cookie.setHttpOnly(true);
		cookie.setSecure(teatrouApiProperty.getSeguranca().isEnableHttps());
		cookie.setPath(req.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);
		resp.addCookie(cookie);
		resp.setStatus(HttpStatus.NO_CONTENT.value());

	}

}
