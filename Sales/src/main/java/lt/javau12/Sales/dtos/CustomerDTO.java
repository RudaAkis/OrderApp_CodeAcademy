package lt.javau12.Sales.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.javau12.Sales.models.Customer;

public class CustomerDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;
    private String email;

    private String imageURL; //Instead of saving the byte array that we have in entity we are using a string to get the URL
    //String that we will get instead of parsing byte array via JSON

    public CustomerDTO() {
    }

    public CustomerDTO(Long id, String name, String email, String imageURL) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imageURL = imageURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
