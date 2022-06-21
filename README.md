# S3 image upload example

## 필요한 dependency
- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-aws  
```groovy
implementation 'org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE'
```

## 필요한 설정
### accesskey와 secretkey
- IAM user `액세스 키 – 프로그래밍 방식 액세스` `AmazonS3FullAccess` 정책으로 생성
```yaml
# region과 stack auto 설정은 꼭 필요함 (AmazonS3를 직접 빈으로 등록하면, credentials 정보는 필요없음)
cloud:
  aws:
    credentials:
      accessKey: ${ACCESS_KEY}
      secretKey: ${SECRET_KEY}
    region:
      static: ${REGION}
    stack:
      auto: false

# 요청되는 사이즈 제한 
spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB
```

### 에러 관련

- ec2 관련 에러
- 실행에는 문제가 없지만, 애플리케이션 실행이 지연된다.
```
com.amazonaws.SdkClientException: Failed to connect to service endpoint: 
	at com.amazonaws.internal.EC2ResourceFetcher.doReadResource(EC2ResourceFetcher.java:100) ~[aws-java-sdk-core-1.11.792.jar:na]
	at com.amazonaws.internal.EC2ResourceFetcher.doReadResource(EC2ResourceFetcher.java:70) ~[aws-java-sdk-core-1.11.792.jar:na]
	at com.amazonaws.internal.InstanceMetadataServiceResourceFetcher.readResource(InstanceMetadataServiceResourceFetcher.java:75) ~[aws-java-sdk-core-1.11.792.jar:na]
	at com.amazonaws.internal.EC2ResourceFetcher.readResource(EC2ResourceFetcher.java:66) ~[aws-java-sdk-core-1.11.792.jar:na]
	at com.amazonaws.util.EC2MetadataUtils.getItems(EC2MetadataUtils.java:402) ~[aws-java-sdk-core-1.11.792.jar:na]
	at com.amazonaws.util.EC2MetadataUtils.getData(EC2MetadataUtils.java:371) ~[aws-java-sdk-core-1.11.792.jar:na]
```
- 인텔리제이에서 실행할 때는, `Edit configurations`에 `VM options`에 아래와 같이 입력해서 ec2 metadata를 사용하지 않도록 설정
- `-Dcom.amazonaws.sdk.disableEc2Metadata=true`


- jar 파일로 실행할 때는,   
- `java jar -Dcom.amazonaws.sdk.disableEc2Metadata=true s3pratice.jar` 과 같이 실행 


- 아래와 같이 익셉션은 발생하지만, 애플리케이션 실행이 지연되지는 않음
- `com.amazonaws.AmazonClientException : EC2 Instance Metadata Service is disabled`


### public read access control 부여
- bucket policy를 통해서 권한을 부여할 수 도 있지만, (호눅스 강의를 참고)  
 `putObjectRequest.withCannedAcl()`메서드를 통해서 직접 각 객체마다 엑세스 권한을 설정 할 수 있다.
