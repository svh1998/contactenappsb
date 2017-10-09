package nl.sandhoofd.contactenapp;

/**
 * Created by svanh on 2-10-2017.
 */

public class Contact {
    private String name,email,phone, id, image;



    public Contact(String id, String name, String email, String phone, String image ) {
        this.setName(name);
        this.id = id;
        this.setEmail(email);
        this.setPhone(phone);
        this.setImage(image);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
