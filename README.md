# producer-consumer-transform
GitHub Repo for my implementation of BetterCloud's Producer-Consumer-Transform coding assignment. 

### Running Producer Project:
1. cd producer-consumer-transform/producer/producer
2. mvn sping-boot:run
3. Producer should now be running at http://localhost:4000

### Running Consumer Project:
1. cd producer-consumer-transform/consumer/consumer.
2. mvn sping-boot:run
3. Consumer should now be running at http://localhost:4001

### Running Transform Project:
1. cd producer-consumer-transform/transform/transform
2. mvn spring-boot:run
3. Transform should now be running at http://localhost:4002

##Produce Route:
GET http://localhost:4000/produce
Hitting this route begins the Produce, Consume Transform process. Make sure that the consumer
and transform is also runnning, or you will get ConnectionRefused errors!

##Consume Route:
POST http://localhost:4001/consume
body: jsonString: json String to be consumed
This route gets hit by the producer every time that it finishes parsing a file.
The consumer then sends a POST request to the transformer's transform route.

##Transform Route:
POST http://localhost:4002/transform
body: jsonString: json String to be consumed.
This route gets hit by the consumer with each tally that it should update.
After processing is done, the transform project logs to the console the contents of the updated tally.

## Future Work:
-Unit test coverage for parse method of ProducerController.
-Integration tests  for each controller.
-Shared package to use for the model. (Both consumer and transform use a ConcurrentHashMap<Integer, String>,
so we could get some code reuse out of this.

Questions or concerns, message me agiron123 on Github.
Or email me agiron123@gmail.com