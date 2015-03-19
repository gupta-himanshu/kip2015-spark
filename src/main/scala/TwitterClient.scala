/**Set keys for twitter authentication*/
import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken;
import com.typesafe.config.ConfigFactory

/**
 * @author knoldus
 */
/**class for set twitter keys*/
class TwitterClient {
  
  val config = ConfigFactory.load();
  val CONSUMER_KEY: String = config.getString("twitter4j.oauth.consumerKey")
  val CONSUMER_KEY_SECRET: String = config.getString("twitter4j.oauth.consumerSecret")
  val ACCESS_TOKEN = config.getString("twitter4j.oauth.accessToken")
  val ACCESS_TOKEN_SECRET = config.getString("twitter4j.oauth.accessTokenSecret")
 
  /**
  *This function to set consumer key and access token for twitter authentication 
  * @return Twitter
  */
def start(): Twitter = {
    val twitter: Twitter = new TwitterFactory().getInstance();
    twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
    twitter.setOAuthAccessToken(new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET))
    twitter
  }
}