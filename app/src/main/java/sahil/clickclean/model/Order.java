package sahil.clickclean.model;

import org.json.JSONObject;

public class Order {
    public Order(JSONObject order) {
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
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

    private String orderid;
    private String orderdate;
    private String orderpickupdate;
    private String orderstatus;

}
