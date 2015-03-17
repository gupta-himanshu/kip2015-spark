/**File to make client for twitter authentication*/

import twitter4j.Twitter
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * @author knoldus
 */
/**class for set consumer key and access token to communication with twitter*/
class TwitterClient {
  val CONSUMER_KEY: String = "Tn6mCikBNxLviA6znN4FgIXfY"
  val CONSUMER_KEY_SECRET: String = "JoRN26wNoPUuUYsgR4zKwre82zTY53r8rDzy6nLSrS4cMqiRzg"
  val ACCESS_TOKEN = "199435611-ancQT2HKivvIrlrKg2FYLTBoQyA0zsISGhDbO7ug"
  val ACCESS_TOKEN_SECRET = "wHaw4X7ok2uWXVGvOAOzaSgZvRovK4xFY4CAMLoNuMOy8"

  /**
   * Function to make a instance of twitter and set keys for twitter authentication
   * @return Twitter
   */
  def start(): Twitter = {
    val twitter: Twitter = new TwitterFactory().getInstance();
    twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
    twitter.setOAuthAccessToken(new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET))
    twitter
  }
}