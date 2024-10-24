package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class SimpleMsgKeyProducer {
    private static final Logger log = LoggerFactory.getLogger(SimpleMsgKeyProducer.class);
    private final static String TOPIC_NAME = "test1"; //send TOPIC 명
    private final static String BOOTSTRAP_SERVERS = "localhost:9092"; //Kafka cluster 서버

    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS); //--bootstrap-server 설정
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); //키 직렬화 방식(String)
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); //값 직렬화 방식(String)

        KafkaProducer<String,String> producer = new KafkaProducer<>(config); //Kafka Producer 인스턴스 생성(config 입력)
        String messageValue = "안녕하세요메세지테스트입니다."; //보낼 메세지
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, "msgKey","23"); //Kafka record 전송및 생성 인스턴스
        producer.send(record); //실제 전송하진않고 배치에 포함되게 하는것인듯
        log.info("{}", record);
        producer.flush(); //실제 전송하는 로직(배치형식으로 내부버퍼에 저장했다가 전송함 몇개가 한계일까?) 및 flush 하는듯
        producer.close(); //전송 종료 (당연하지만 close된 이후에는 전송안됨)
    }
}
