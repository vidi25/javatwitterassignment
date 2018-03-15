package edu.knoldus;

import edu.knoldus.models.Post;
import edu.knoldus.services.TwitterOperations;
import twitter4j.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TwitterApplication {

    public static void main(String[] args) throws InterruptedException {

        TwitterOperations twitterOperations = new TwitterOperations();
        final LocalDate date = LocalDate.of(2018,3,11);
        final String user = "@ipl";
        CompletableFuture<List<Status>> tweets = twitterOperations.getTweetsOfAUser(user);
        Thread.sleep(5000);
        List<Post> postList = twitterOperations.createPosts(tweets);
        System.out.println("Posts newer to older are = " + twitterOperations.sortPostsByDateNewerToOlder(postList));
        System.out.println("Posts older to newer are = " + twitterOperations.sortPostsByDateOlderToNewer(postList));
        System.out.println("\n\n\nPosts higher to lower by reTweet count = "
                + twitterOperations.sortPostsByReTweetCount(postList));
        System.out.println("\n\n\nPosts higher to lower by like count are = "
                + twitterOperations.sortPostsByLikeCount(postList));
        System.out.println("\n\n\nTweets of " + user + " of date " + date + " = ");
        twitterOperations.getListOfTweetsOfADate(user,date).thenAccept(System.out::println);
        Thread.sleep(5000);
    }

}
