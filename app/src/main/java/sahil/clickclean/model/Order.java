package sahil.clickclean.model;

import org.json.JSONObject;

import java.util.Date;

public class Order {
    public Order(JSONObject order) {
    }

    private String _id;
    private String orderdate;
    private String orderpickupdate;
    private String orderstatus;
    private String userid;
    private String address;
    private Long longitude;
    private Long latitude;
    private Date create_time;

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrderpickupdate() {
        return orderpickupdate;
    }

    public void setOrderpickupdate(String orderpickupdate) {
        this.orderpickupdate = orderpickupdate;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }



}
