package br.com.teatrou.storage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.SetObjectTaggingRequest;
import com.amazonaws.services.s3.model.Tag;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.Permission;

import br.com.teatrou.config.property.TeatrouApiProperty;

@Component
public class S3 {

	private static final Logger LOGGER = LoggerFactory.getLogger(S3.class);

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private TeatrouApiProperty property;


	public String salvarTemporariamente(MultipartFile arquivo) {
		AccessControlList accessControlList = new AccessControlList();
		accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(arquivo.getContentType());
		metadata.setContentLength(arquivo.getSize());

		String nomeUnico = gerarNomeUnico(arquivo.getOriginalFilename());

		try {
			PutObjectRequest objectRequest = new PutObjectRequest(
					property.getS3().getBucket(),
					nomeUnico,
					arquivo.getInputStream(),
					metadata)
					.withCannedAcl(CannedAccessControlList.PublicRead);

            objectRequest.setTagging(new ObjectTagging(
            		 Arrays.asList(new Tag("temp", "true"))));

			amazonS3.putObject(objectRequest);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Arquivo {} enviado com sucesso para o s3.",
						arquivo.getOriginalFilename());
			}

			return nomeUnico;

		} catch (IOException e) {
			throw new RuntimeException("Erro ao tentar enviar o arquivo para o s3", e);
		}
	}

	public void salvar(String anexo) {
		SetObjectTaggingRequest request = new SetObjectTaggingRequest(
				property.getS3().getBucket(),
				anexo,
				new ObjectTagging(Collections.emptyList()));

		amazonS3.setObjectTagging(request);
	}

	public void remover(String anexo) {
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
				property.getS3().getBucket(), anexo);

		amazonS3.deleteObject(deleteObjectRequest);
	}

	public void substituir(String anexoAntigo, String anexoNovo) {
		if(StringUtils.hasText(anexoAntigo)) {
			remover(anexoAntigo);
		}

		salvar(anexoNovo);
	}

	public String configurarUrl(String nome) {
		return "\\\\" + property.getS3().getBucket() +
		        ".s3.amazonaws.com/" + nome;
	}


	private String gerarNomeUnico(String anexo) {
		return UUID.randomUUID().toString() + "_" + anexo;
	}








}
