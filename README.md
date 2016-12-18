Two workers are implemented here: Consumer and Indexer

## Consumer
	1. Consumes tweets
	2. List of user names mentioned in TwitterUtils
	3. Period is hardcoded in TwitterUtils
	4. Offsets managed in redis (comments in the code)


## Indexer
	1. Reads from queue and indexes it (for now just logs)
	2. No offset management here, assuming the queue will manage its own offset

# Queue
  Queue used here is a file based queue [Hardcoded for now - /tmp/queue]

# Build Instructions

  1. Clone repo
  2. Install Redis

	```java
   $ wget http://download.redis.io/releases/redis-3.2.6.tar.gz
	$ tar xzf redis-3.2.6.tar.gz
	$ cd redis-3.2.6
	$ make
  ```

  3. Run the app

	``` java
  java -Dtwitter4j.debug=true -Dtwitter4j.oauth.consumerKey=xxxxxx -Dtwitter4j.oauth.consumerSecret=xxxxxxxxxxxxx -Dtwitter4j.oauth.accessToken=xxxxxxxxxxxxxxx -Dtwitter4j.oauth.accessTokenSecret=xxxxxxxxxxxxxxx -Dlog4j.configuration=file:target/classes/conf/log4j.properties -cp target/twitter-1.0-SNAPSHOT.jar:target/* com.twitter.app.App
  ```

