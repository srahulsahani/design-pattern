package creational_pattern.builder.http_request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    //mandatory fields
    private final String url;
    private final String method;

    //optional fields
    private final Map<String,String> headers;
    private final Map<String,String> queryParams;
    private final String body;
    private final int timeout;
    private final String bearerToken;
    private final int retryCount;


    //Private Constructor
    private HttpRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers;
        this.queryParams = builder.queryParams;
        this.body = builder.body;
        this.timeout = builder.timeout;
        this.bearerToken = builder.bearerToken;
        this.retryCount = builder.retryCount;
    }

    //Getters
    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String getBody() {
        return body;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public int getRetryCount() {
        return retryCount;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", headers=" + headers +
                ", queryParams=" + queryParams +
                ", body='" + body + '\'' +
                ", timeout=" + timeout +
                ", bearerToken='" + bearerToken + '\'' +
                ", retryCount=" + retryCount +
                '}';
    }

    public static class Builder{
        //mandatory fields
        private String url;
        private String method;

        //optional fields
        private Map<String,String> headers = new HashMap<>();
        private Map<String,String> queryParams = new HashMap<>();
        private String body;
        private int timeout = 3000;
        private String bearerToken;
        private int retryCount = 0;

        public Builder url(String url){
            this.url = url;
            return this;
        }

        public Builder methods(String methods){
            this.method = methods;
            return this;
        }

        public Builder body(String body){
            this.body = body;
            return this;
        }
        public Builder timeout(int timeout){
            this.timeout = timeout;
            return this;
        }

        public Builder bearerToken(String bearerToken){
            this.bearerToken = bearerToken;
            return this;
        }

        public Builder retryCount(int retryCount){
            this.retryCount = retryCount;
            return this;
        }

        public Builder addHeaders(String key, String value){
            this.headers.put(key,value);
            return this;
        }

        public Builder addQueryParam(String key, String value){
            this.queryParams.put(key,value);
            return this;
        }

        public HttpRequest build(){
            if(url == null || url.isBlank()){
                throw new IllegalArgumentException("URL is mandatory");
            }

            if(method == null || method.isBlank()){
                throw new IllegalArgumentException("Method is mandatory");
            }
            return new HttpRequest(this);
        }
    }

}
