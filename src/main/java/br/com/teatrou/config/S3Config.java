package br.com.teatrou.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.Tag;
import com.amazonaws.services.s3.model.lifecycle.LifecycleFilter;
import com.amazonaws.services.s3.model.lifecycle.LifecycleTagPredicate;

import br.com.teatrou.config.property.TeatrouApiProperty;

@Configuration
public class S3Config {

	@Autowired
	private TeatrouApiProperty property;

	@Bean
	public AmazonS3 amazonS3() {
		AWSCredentials credentials = new BasicAWSCredentials(
				property.getS3().getAccessKeyId(),
				property.getS3().getSecretKey());

		AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.SA_EAST_1)
				.build();

		if(!amazonS3.doesBucketExistV2(property.getS3().getBucket())) {
			configurationBucket(amazonS3);
		}

		return amazonS3;
	}

	private void configurationBucket(AmazonS3 amazonS3) {
		amazonS3.createBucket(new CreateBucketRequest(property.getS3().getBucket()));

		BucketLifecycleConfiguration.Rule ruleExpiration =
				new BucketLifecycleConfiguration.Rule()
				.withId("Regra de expiração de arquivos temporários")
				.withFilter(new LifecycleFilter(
						new LifecycleTagPredicate(new Tag("temp", "true"))))
				.withExpirationInDays(1)
				.withStatus(BucketLifecycleConfiguration.ENABLED);

		BucketLifecycleConfiguration configuration = new BucketLifecycleConfiguration()
				.withRules(ruleExpiration);

		amazonS3.setBucketLifecycleConfiguration(property.getS3().getBucket(),
				configuration);
	}

}
