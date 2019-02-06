package com.search.github.githubsearcher.model;


public class GitHub {
    private String avatar;
    private String numberofstars;
    private int stargazers;
    private String name;

    public int getStargazers() {
        return stargazers;
    }

    public void setStargazers(int stargazers) {
        this.stargazers = stargazers;
    }

    private String description;

    @Override
    public String toString() {
        return "GitHub{" +
                "avatar='" + avatar + '\'' +
                ", numberofstars='" + numberofstars + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public GitHub() {
    }

    public GitHub(String avatar, String numberofstars, String name, String description) {
        this.avatar = avatar;
        this.numberofstars = numberofstars;
        this.name = name;

        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNumberofstars() {
        return numberofstars;
    }

    public void setNumberofstars(String numberofstars) {
        this.numberofstars = numberofstars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
