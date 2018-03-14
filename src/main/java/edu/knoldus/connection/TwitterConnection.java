package edu.knoldus.connection;


import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class TwitterConnection {

    public static Twitter getTwitterInstance() {

        return TwitterFactory.getSingleton();
    }
}
