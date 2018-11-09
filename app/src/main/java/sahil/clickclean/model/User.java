package sahil.clickclean.model;

import android.util.Log;

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
    private String secondary_mobileno;
    private Long dob;
    private String email;
    private Date created_at;
    private String city;
    private String pincode;

    public User(JSONObject data) throws JSONException {
        if(data.has("_id"))this._id=data.getString("_id");
        if(data.has("firstname"))this.firstname=data.getString("firstname");
        if(data.has("phone"))this.phone=data.getString("phone");
        if(data.has("email"))this.email=data.getString("email");
        if(data.has("address"))this.address=data.getString("address");
        if(data.has("lastname"))this.lastname=data.getString("lastname");
        if(data.has("role"))this.role = data.getString("role");
        if(data.has("secondary_mobileno"))this.secondary_mobileno = data.getString("secondary_mobileno");
        if(data.has("city"))this.city = data.getString("city");
        if(data.has("pincode"))this.pincode = data.getString("pincode");
    }

    public User() {

    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
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

    public String getSecondary_mobileno() {
        return secondary_mobileno;
    }

    public void setSecondary_mobileno(String secondary_mobileno) {
        this.secondary_mobileno = secondary_mobileno;
    }

    public Long getDob() {
        return dob;
    }

    public void setDob(Long dob) {
        this.dob = dob;
    }

}
