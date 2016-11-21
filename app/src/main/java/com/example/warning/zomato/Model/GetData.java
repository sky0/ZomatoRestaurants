package com.example.warning.zomato.Model;

/**
 * Created by warning on 11/19/16.
 */

public class GetData {

    String name;
    String rating;
    String image;
    String cuisines;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public GetData()
    {

    }
    public GetData(String name,
            String rating,
            String image,
            String cuisines,
                   String id)
    {
        this.name=name;
        this.rating=rating;
        this.image=image;
        this.cuisines=cuisines;
        this.id=id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





}
