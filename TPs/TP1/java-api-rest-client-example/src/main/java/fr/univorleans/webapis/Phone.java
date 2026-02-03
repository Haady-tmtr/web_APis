package fr.univorleans.webapis;

import org.json.JSONObject;

public class Phone {

    private int id;
    private String type;
    private String number;

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPerson() {
        return person;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    private String person;

    private Phone() {};

    public static Phone fromJSON(JSONObject json){
        Phone p = new Phone();

        p.id = json.getInt("id");
        p.number = json.getString("number");
        p.type = json.getString("type");
        p.person = json.getString("person");

        return p;



    }


}