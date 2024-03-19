package in.reqres.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDataResponseModel {

    @JsonProperty("data")
    UserResponseModel user;

    @Data
    public static class UserResponseModel {
        Integer id;
        String email;
        @JsonProperty("first_name")
        String firstName;
        @JsonProperty("last_name")
        String lastName;
        String avatar;
    }
}
