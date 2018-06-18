package br.com.teatrou.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(value = "teatrouapi")
public @Data class TeatrouApiProperty {

	private final Seguranca seguranca = new Seguranca();
	private final Mail mail = new Mail();
	private final S3 s3 = new S3();
	private String originPermitida = "http://localhost:4200";

	public @Data static class S3 {
		private String accessKeyId;
		private String secretKey;
		private String bucket = "tp-teatrou-arquivos";

	}

	public @Data static class Seguranca {
		private boolean enableHttps;

	}
	
	public @Data static class Mail {
		private String host;
		private Integer port;
		private String username;
		private String password;
		
	}
}
