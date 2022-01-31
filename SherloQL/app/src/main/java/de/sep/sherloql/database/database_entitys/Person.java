package de.sep.sherloql.database.database_entitys;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Person {
    @PrimaryKey
    private int id;
    private String beruf;
    private String vorname;
    private String nachname;

    private String geburtsdaturm;


    public Person(int id, String beruf, String vorname, String nachname){
        this.id = id;
        this.beruf = beruf;
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtsdaturm = geburtsdaturm;
    }

    public int getId() {
        return id;
    }

    public String getBeruf() {
        return beruf;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getGeburtsdaturm() {
        return geburtsdaturm;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBeruf(String beruf) {
        this.beruf = beruf;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public void setGeburtsdaturm(String geburtsdaturm) {
        this.geburtsdaturm = geburtsdaturm;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", beruf='" + beruf + '\'' +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", geburtsdaturm=" + geburtsdaturm +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                Objects.equals(beruf, person.beruf) &&
                Objects.equals(vorname, person.vorname) &&
                Objects.equals(nachname, person.nachname) &&
                Objects.equals(geburtsdaturm, person.geburtsdaturm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, beruf, vorname, nachname, geburtsdaturm);
    }
}