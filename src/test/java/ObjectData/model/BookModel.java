package ObjectData.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.swing.*;
@Getter
public class BookModel {

    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("title")
    private String title;
    @JsonProperty("subtitle")
    private String subtitle;
    @JsonProperty("author")
    private String author;
    @JsonProperty("publish_date")
    private String publish_date;
    @JsonProperty("publisher")
    private String publisher;
    @JsonProperty("pages")
    private Integer pages;
    @JsonProperty("description")
    private String description;
    @JsonProperty("website")
    private String website;

}
