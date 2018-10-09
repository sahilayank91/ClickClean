package sahil.clickclean.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class User {


    private String _id;
    private String firstname;
    private String lastname;
    private String address;
    private String role;
    private String phone;
    private Long secondary_mobileno;
    private Long dob;

    public User(JSONObject jsonObject) throws JSONException {
        if(jsonObject.has("_id"))this._id=jsonObject.getString("_id");
        if(jsonObject.has("name"))this.firstname=jsonObject.getString("firstname");
        if(jsonObject.has("phone"))this.phone=jsonObject.getString("phone");
        if(jsonObject.has("email"))this.email=jsonObject.getString("email");
        if(jsonObject.has("address"))this.address=jsonObject.getString("address");
    }

    public User() {

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    private String email;

    private Date created_at;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String mobileno) {
        this.phone = mobileno;
    }

    public Long getSecondary_mobileno() {
        return secondary_mobileno;
    }

    public void setSecondary_mobileno(Long secondary_mobileno) {
        this.secondary_mobileno = secondary_mobileno;
    }

    public Long getDob() {
        return dob;
    }

    public void setDob(Long dob) {
        this.dob = dob;
    }

}
