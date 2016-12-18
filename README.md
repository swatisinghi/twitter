Two workers are implemented here: Consumer and Indexer

Consumer

1. Consumes tweets

2. List of user names mentioned in TwitterUtils

3. Period is hardcoded in TwitterUtils

4. Offsets managed in redis (comments in the code)


Indexer

1. Reads from queue and indexes it (for now just logs)

2. No offset management here, assuming the queue will manage its own offset

Queue

Queue used here is a file based queue
