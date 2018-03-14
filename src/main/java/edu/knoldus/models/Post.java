package edu.knoldus.models;

import java.util.Date;

public class Post {

    private String tweetData;
    private final Date dateOfCreation;
    private int likeCount;
    private int reTweetCount;

    public Post(String tweetData, Date createdDate, int likeCount, int reTweetCount) {
        this.tweetData = tweetData;
        this.dateOfCreation =  createdDate == null ? null : (Date)createdDate.clone();
        this.likeCount = likeCount;
        this.reTweetCount = reTweetCount;
    }

    public Date getDateOfCreation() {
        return (Date)dateOfCreation.clone();
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getReTweetCount() {
        return reTweetCount;
    }

    @Override
    public String toString() {
        return "Post: " + this.tweetData + " " + this.dateOfCreation + " " + this.likeCount + " " + this.reTweetCount;
    }
}
