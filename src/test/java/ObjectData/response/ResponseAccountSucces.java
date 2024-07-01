package ObjectData.response;

import ObjectData.model.BookModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
@Getter
public class ResponseAccountSucces {

    @JsonProperty("userID")
    private String userId;
    @JsonProperty("username")
    private String username;
    @JsonProperty("books")
    private List<BookModel> book;
}
