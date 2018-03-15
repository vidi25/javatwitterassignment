package edu.knoldus.services;

import edu.knoldus.models.Post;
import edu.knoldus.connection.TwitterConnection;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class TwitterOperations {

    private Twitter twitter = TwitterConnection.getTwitterInstance();
    private List<Status> tweets = null;

    /**
     * returns tweets of a particular user.
     * @param user String of user name whose tweets need to be found
     * @return Completable future of List of Status
     */
    public CompletableFuture<List<Status>> getTweetsOfAUser(String user) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                Query query = new Query(user);
                query.setCount(100);
                tweets = this.twitter.search(query).getTweets();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return tweets;
        }).handle((result, ex) -> {
            if (ex != null) {
                return Collections.emptyList();
            }
            return result;
        });

    }

    /**
     * creates a list of Post class which contains text,date of creation,
     * favourite count,reTweet count.
     * @param tweetsList List of status
     * @return List of Post class
     */
    public List<Post> createPosts(CompletableFuture<List<Status>> tweetsList) {

        List<Post> posts = new ArrayList<>();
        tweetsList.thenAccept(tweets -> tweets.forEach(
                tweet -> {
                    String data = tweet.getText();
                    Date date = tweet.getCreatedAt();
                    int noOfLikes = tweet.getFavoriteCount();
                    int noOfReTweets = tweet.getRetweetCount();
                    posts.add(new Post(data, date, noOfLikes, noOfReTweets));
                }
        ));

        return posts;
    }

    /**
     * sorts Posts by date of creation to get posts newer to older.
     * @param posts List of Post class
     * @return List of Post class
     */
    public List<Post> sortPostsByDateNewerToOlder(List<Post> posts) {

        posts.sort(Comparator.comparing(Post::getDateOfCreation));
        return posts;

    }

    /**
     * sorts Posts by date of creation to get posts older to newer.
     * @param posts List of Post class
     * @return List of Post class
     */
    public List<Post> sortPostsByDateOlderToNewer(List<Post> posts) {

        posts.sort(Comparator.comparing(Post::getDateOfCreation).reversed());
        return posts;

    }

    /**
     * sorts Posts by reTweet count in descending order.
     * @param posts List of Post class
     * @return List of Post class
     */
    public List<Post> sortPostsByReTweetCount(List<Post> posts) {

        posts.sort(Comparator.comparing(Post::getReTweetCount).reversed());
        return posts;
    }

    /**
     * sorts Posts by favourite count in descending order.
     * @param posts List of Post class
     * @return List of Post class
     */
    public List<Post> sortPostsByLikeCount(List<Post> posts) {

        posts.sort(Comparator.comparing(Post::getLikeCount).reversed());
        return posts;
    }

    /**
     * returns a list of tweets of a particular date of a particular user.
     * @param user String of user name whose tweets need to be found
     * @param date LocalDate of date of which tweets need to be found
     * @return Completable future of list of status
     */
    public CompletableFuture<List<Status>> getListOfTweetsOfADate(String user, LocalDate date) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                Query query = new Query(user);
                query.setSince(date.toString());
                query.setUntil(date.plusDays(1).toString());
                tweets = twitter.search(query).getTweets();
            } catch (TwitterException ex) {
                ex.printStackTrace();
            }
            return tweets;
        }).handle((result, ex) -> {
            if (ex != null) {
                return Collections.emptyList();
            }
            return result;
        });
    }
}
