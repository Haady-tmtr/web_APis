package fr.univorleans.webapis;

import org.json.JSONArray;
import org.json.JSONObject;

public class Person {
    private int id;
    private int age;
    private String firstname;
    private String lastname;
    private boolean is_alive;

    public Phone getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(Phone phone_number) {
        this.phone_number = phone_number;
    }

    private Phone phone_number;


    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setIs_alive(boolean is_alive) {
        this.is_alive = is_alive;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    private String uri; // stocke le @id de JSON


    // Constructeur privé pour forcer l'usage de la Factory
    private Person() {}

    // --- LA FACTORY ---
    public static Person fromJSON(JSONObject json) {
        Person p = new Person();


        p.id = json.getInt("id"); // ou json.getInt("id")
        p.age = json.getInt("age");
        p.firstname = json.getString("firstname");
        p.lastname = json.getString("lastname");

        // Pour is_alive, on met true par défaut si absent (comme demandé par le prof)
        p.is_alive = json.optBoolean("is_alive", true);
        p.uri = json.getString("@id");
        // p.phone_number = Phone.fromJSON(json.getJSONObject("phoneNumbers")); => phoneNumbers est une liste

        // On récupère d'abord la liste (le tableau [])
        //JSONArray phoneArray = json.getJSONArray("phoneNumbers");

        // On prend le premier élément de la liste (index 0) pour le transformer en objet Phone
        //if (!phoneArray.isEmpty()) {
           // p.phone_number = Phone.fromJSON(phoneArray.getJSONObject(0));
        //}

        return p;
    }

    // Getters
    public int getId() { return id; }
    public int getAge() { return age; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public boolean isIs_alive() { return is_alive; }
}