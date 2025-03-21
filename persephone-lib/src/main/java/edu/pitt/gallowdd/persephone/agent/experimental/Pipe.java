package edu.pitt.gallowdd.persephone.agent.experimental;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;

public class Pipe {

  public static void main(String[] args) throws Exception
  {
    String inputTopic = "input-topic";
    
    Properties props = new Properties();
    
//    Properties streamsConfiguration = new Properties();
//    streamsConfiguration.put(
//      StreamsConfig.APPLICATION_ID_CONFIG, 
//      "wordcount-live-test");
    
//    private String bootstrapServers = "localhost:9092";
//    streamsConfiguration.put(
//      StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, 
//      bootstrapServers);
    
//    streamsConfiguration.put(
//        StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, 
//        Serdes.String().getClass().getName());
//      streamsConfiguration.put(
//        StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, 
//        Serdes.String().getClass().getName());
    
    final Path stateDirectory = Files.createTempDirectory("kafka-streams");
//    streamsConfiguration.put(
//      StreamsConfig.STATE_DIR_CONFIG, this.stateDirectory.toAbsolutePath().toString());
    
    
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-live-test");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.put(StreamsConfig.STATE_DIR_CONFIG, stateDirectory.toAbsolutePath().toString());
    
    
    final StreamsBuilder builder = new StreamsBuilder();
    KStream<String, String> textLines = builder.stream(inputTopic);
    Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);;

    KTable<String, Long> wordCounts = textLines
      .flatMapValues(value -> Arrays.asList(pattern.split(value.toLowerCase())))
      .groupBy((key, word) -> word)
      .count();

    wordCounts.toStream()
      .foreach((word, count) -> System.out.println("word: " + word + " -> " + count));
    
    String outputTopic = "output-topic";
    wordCounts.toStream()
      .to(outputTopic, Produced.with(Serdes.String(), Serdes.Long()));

    Topology topology = builder.build();
    KafkaStreams streams = new KafkaStreams(topology, props);
    streams.start();

    Thread.sleep(3000000);
    streams.close();

//    // attach shutdown handler to catch control-c
//    Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
//        @Override
//        public void run() {
//            System.out.println("Shutting Down Pipe App");
//            streams.close();
//            latch.countDown();
//        }
//    });

//    try {
//        streams.start();
//        //latch.await();
//    } catch (Throwable e) {
//        System.exit(1);
//    }
//    System.exit(0);

  }

}
