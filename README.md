# JPA, 파일업로드, 예외처리, AOP+Annotation을 이용한 공통처리

## Comment
적지 않은 생각들로 Rest Api를 구성하게 되었다. 차근히 정리 해보자.

## 구성요소
1. [업로드 공통 모듈](https://github.com/lhy880518/common-image-upload-module)을 사용하여 Rest Api를 통한 S3 File upload
    * 위의 사항을 오버로드하여 File객체일때와 MultipartFile 두 객체 모두 가능하도록 리펙토링
2. 로컬 테스트 진행 중 CORS 해결을 위한 WebConfig.java 추가
3. @RestControllerAdvice를 통한 에러 핸들링
    * AmazonConcept.java에 에러 핸들링에 사용할 FileUploadException객체 사용
    ```
    private Optional<File> convert(MultipartFile file){
            File convertFile = new File(file.getOriginalFilename());
            try {
                if(convertFile.createNewFile()) {
                    try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                        fos.write(file.getBytes());
                    }
                    return Optional.of(convertFile);
                }
            } catch (IOException e) {
                throw new FileUploadException("AmazonServiceException="+e);
            }
    
            return Optional.empty();
        }
    ```
    * RestControllerAdvice에 선언하여 아래와 같이 에러 핸들링
    ```
    @RestControllerAdvice
    public class SampleRestControllerAdvice {
    
        @ExceptionHandler(FileUploadException.class)
        public ResponseEntity<ErrorResponse> FileUploadException(FileUploadException exception){
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage()));
        }
    
        @ExceptionHandler(InvalidParameterException.class)
        public ResponseEntity<ErrorResponse> invalidParams(HttpServletRequest req, InvalidParameterException e) {
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> SqlException(Exception exception){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "일시적인 서버 오류 입니다. 잠시 후 다시 시도해 주세요."));
        }
    }
    ```
4. @Valid, BindingResult 사용하여 객체 Validation처리를 하려 했으나 모든 소스에 아래와 같은 공통 에러 코드를 넣어야 했기에 이를 해결하고자 AOP와 커스텀 annotation 하용하여 공통 처리 
    ```
    if(bindingResult.hasErrors()){
                throw new InvalidParameterException(bindingResult.getFieldError().getDefaultMessage());
            }
    ```
    * 커스텀 annotation 선언  
    ```
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface HandleBindingResult {
        Class<? extends RuntimeException> value() default RuntimeException.class;
    }
    ```
    * AOP 사용하여 아래와 같이 처리
    ```
    @Aspect
    @Component
    public class BindingResultAspect {
    
        @Around("@annotation(com.test.springbootrest.exception.HandleBindingResult)")
        public Object proceed(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            Method method = methodSignature.getMethod();
            HandleBindingResult handleBindingResult = method.getAnnotation(HandleBindingResult.class);
            Class<? extends RuntimeException> value = handleBindingResult.value();
    
            Object[] args = proceedingJoinPoint.getArgs();
            for(Object arg : args){
                if(arg instanceof BindingResult){
                    BindingResult result = (BindingResult) arg;
                    if(result.hasErrors()){
                        // here
                        throw value.getConstructor(new Class[]{String.class}).newInstance(result.getFieldErrors().get(0).getDefaultMessage());
                    }
                }
            }
            return proceedingJoinPoint.proceed();
        }
    }
    ```
    위의 코드 중 // here아래 부분은 자바 reflaction을 사용하여 처리 
5. yml코드 중 multipart 선언으로 서버 업로드 파일 최대 용량 지정
    ```
    spring:
      profiles:
        active: local
    
    ---
    spring:
      profiles: local
    
      servlet:
        multipart:
          max-file-size: 10MB
          max-request-size: 10MB
    
      datasource:
        platform: h2
        url: jdbc:h2:tcp://localhost/~/edithome
        username: sa
        password: sa
        driver-class-name: org.h2.Driver
      jpa:
        database-platform: H2
        show-sql: false
        hibernate:
          ddl-auto: create-drop
    ```

## 참고 사이트
1. [에러 처리 관련 참고 페이지](https://velog.io/@ssseungzz7/Java-Exception-handling)
2. [Java Reflection 개념 및 사용법](https://gyrfalcon.tistory.com/entry/Java-Reflection)
3. [예외처리 관련 참고 페이지](https://jeong-pro.tistory.com/195)
4. [Optional 객체 사용](http://tcpschool.com/java/java_stream_optional), [바르게 사용하는 Optional](http://homoefficio.github.io/2019/10/03/Java-Optional-%EB%B0%94%EB%A5%B4%EA%B2%8C-%EC%93%B0%EA%B8%B0/)

