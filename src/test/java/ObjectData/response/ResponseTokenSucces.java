package ObjectData.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.beans.JavaBean;

@Getter
public class ResponseTokenSucces {
    @JsonProperty("token")
    private String token;
    @JsonProperty("expires")
    private String expires;
    @JsonProperty("status")
    private String status;
    @JsonProperty("result")
    private String result;

}
