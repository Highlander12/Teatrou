package br.com.teatrou.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import br.com.teatrou.config.property.TeatrouApiProperty;


@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken>{

	public static final String POST_ACCESS_TOKEN = "postAccessToken";

	@Autowired
	private TeatrouApiProperty property;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals(POST_ACCESS_TOKEN);
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> converterType,
			ServerHttpRequest req, ServerHttpResponse resp) {

		HttpServletRequest request = ((ServletServerHttpRequest) req).getServletRequest();
		HttpServletResponse response = ((ServletServerHttpResponse) resp).getServletResponse();
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		String refreshToken = body.getRefreshToken().getValue();
		cookieAddRefreshToken(refreshToken, request, response);
		removerRefreshTokenBody(token);

		return body;
	}


	private void removerRefreshTokenBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);
	}

	private void cookieAddRefreshToken(String refreshToken, HttpServletRequest request,
			HttpServletResponse response) {
		Cookie requestTokenCookie = new Cookie("RefreshToken", refreshToken);

		requestTokenCookie.setHttpOnly(true);
		requestTokenCookie.setSecure(property.getSeguranca().isEnableHttps());
		requestTokenCookie.setPath(request.getContextPath() + "/oauth/token");
		requestTokenCookie.setMaxAge(3600 * 24 * 30);
		response.addCookie(requestTokenCookie);
	}

}
