package com.code.blogstar;

/**
 * Created by karan jain on 19-08-2017.
 */

public class Blog {
    String Title;
    String Description;
    String ImageUrl;
    public Blog()
    {}

    public Blog(String title, String description, String image) {
        this.Title = title;
        this.Description = description;
        this.ImageUrl = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
    }
}
