package sahil.clickclean.model;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import sahil.clickclean.SharedPreferenceSingleton;

/**
 * Created by joey on 2/12/16.
 */
public class UserData {
    private static UserData ourInstance = new UserData();
    private String email;
    private String phone;
    private String firstname;
    private String lastname;
    private String address;
    private String _id;
    private Context context;

    private UserData() {
    }

    public static UserData getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new UserData();
            ourInstance.getUserData(context);
        }

        try {
            String id= ourInstance.get_id();
            if(id==null)throw new Exception();
        } catch (Exception e) {
            ourInstance = null;
            ourInstance = new UserData();
            ourInstance.getUserData(context);
        }

        return ourInstance;
    }

//        public void initUserData(String data, Context context) throws Exception {
//        this.context = context;
//        JSONObject userdata = new JSONObject(data);
//
//        setUser_id(userdata.getLong("id"));
//        setUsername(userdata.getString("username"));
//        setEmail(userdata.getString("email"));
//        setContact(userdata.getString("contact"));
//
//    }
    public void initUserData(User user,Context context)  {
        this.context = context;


        setUser_id(user.get_id());
        setFirstname(user.getFirstname());
        setLastname(user.getLastname());
        setEmail(user.getEmail());
        setPhone(user.getPhone());
        setAddress(user.getAddress());

    }

    public boolean getUserData(Context context) {
        this.context = context;
        try {

            this._id = SharedPreferenceSingleton.getInstance(context).getString("_id");
            this.firstname = SharedPreferenceSingleton.getInstance(context).getString("firstname");
            this.phone = SharedPreferenceSingleton.getInstance(context).getString("phone");
            this.email = SharedPreferenceSingleton.getInstance(context).getString("email");
            this.lastname=SharedPreferenceSingleton.getInstance(context).getString("lastname");
            this.address=SharedPreferenceSingleton.getInstance(context).getString("address");


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if(this._id.length()==0)return false;
        return true;
    }

    public User getUser() throws JSONException {
        User user=new User();
        user.setEmail(this.email);
        user.set_id(this._id);
        user.setFirstname(this.firstname);
        user.setPhone(this.phone);
        user.setLastname(this.lastname);
        user.setAddress(this.address);

        return user;
    }


    public void setUser_id(String user_id) {
        this._id = user_id;
        SharedPreferenceSingleton.getInstance(context).put("_id", user_id);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        SharedPreferenceSingleton.getInstance(context).put("email", email);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
        SharedPreferenceSingleton.getInstance(context).put("firstname", firstname);

    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
        SharedPreferenceSingleton.getInstance(context).put("lastname", lastname);

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        SharedPreferenceSingleton.getInstance(context).put("address", address);

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        SharedPreferenceSingleton.getInstance(context).put("phone", phone);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }





}
