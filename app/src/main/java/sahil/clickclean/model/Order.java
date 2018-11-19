package sahil.clickclean.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Order {

    private String _id;
    private String orderdate;
    private String pickup_date;
    private String status;
    private String userid;
    private String address;
    private String longitude;
    private String latitude;
    private String create_time;
    private String total;
    private User user;
    private String pickup_otp;
    private String delivered_otp;

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String offer,code;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public String getOfferid() {
        return offerid;
    }

    public void setOfferid(String offerid) {
        this.offerid = offerid;
    }

    private String offerid;

    public Order(JSONObject order) throws JSONException {
        if(order.has("_id"))this._id = order.getString("_id");
        if(order.has("pickup_date"))this.pickup_date = order.getString("pickup_date");
        if(order.has("userid"))this.userid = order.getString("userid");
        if(order.has("address"))this.address = order.getString("address");
        if(order.has("latitude"))this.latitude = order.getString("latitude");
        if(order.has("longitude"))this.longitude = order.getString("longitude");
        if(order.has("created_at"))this.create_time = order.getString("created_at");
        if(order.has("status"))this.status = order.getString("status");
        if(order.has("service"))this.orderservice = order.getString("service");
        if(order.has("total"))this.total = order.getString("total");
        if(order.has("userid"))this.user = new User(new JSONObject(order.getString("userid")));
        if(order.has("pickup_otp"))this.pickup_otp = order.getString("pickup_otp");
        if(order.has("delivered_otp"))this.delivered_otp= order.getString("delivered_otp");
        if(order.has("type"))this.type= order.getString("type");
        if(order.has("offer"))this.offer= order.getString("offer");
        if(order.has("code"))this.code= order.getString("code");
        if(order.has("offerid"))this.offerid = order.getString("offerid");


    }

    public String getPickup_otp() {
        return pickup_otp;
    }

    public void setPickup_otp(String pickup_otp) {
        this.pickup_otp = pickup_otp;
    }

    public String getDelivered_otp() {
        return delivered_otp;
    }

    public void setDelivered_otp(String delivered_otp) {
        this.delivered_otp = delivered_otp;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }


    private ArrayList<RateCard> clothList;

    public ArrayList<RateCard> getRateCardList() {
        return clothList;
    }

    public void setRateCardList(ArrayList<RateCard> clothList) {
        this.clothList = clothList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<RateCard> getClothList() {
        return clothList;
    }

    public void setClothList(ArrayList<RateCard> clothList) {
        this.clothList = clothList;
    }

    public String getPickup_date() {
        return pickup_date;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderservice() {
        return orderservice;
    }

    public void setOrderservice(String orderservice) {
        this.orderservice = orderservice;
    }

    private String orderservice;


    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrderpickupdate() {
        return pickup_date;
    }

    public void setOrderpickupdate(String orderpickupdate) {
        this.pickup_date = orderpickupdate;
    }

    public String getOrderstatus() {
        return status;
    }

    public void setOrderstatus(String orderstatus) {
        this.status = orderstatus;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }



}
