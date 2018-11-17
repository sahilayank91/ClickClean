package sahil.clickclean.model;

import android.provider.Telephony;

import org.json.JSONObject;

public class RateCard {

    public String getCloth() {
        return cloth;
    }

    public void setCloth(String cloth) {
        this.cloth = cloth;
    }

    public String getWashandiron() {
        return washandiron;
    }

    public void setWashandiron(String washandiron) {
        this.washandiron = washandiron;
    }

    public String getWash() {
        return wash;
    }

    public void setWash(String wash) {
        this.wash = wash;
    }

    public String getIron() {
        return iron;
    }

    public void setIron(String iron) {
        this.iron = iron;
    }

    private String cloth;
    private String washandiron;
    private String wash;
    private String iron;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String icon;


    public RateCard(JSONObject rate){

    }
    public RateCard(){

    }
}
