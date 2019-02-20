package com.securescm.shipment;

import com.securescm.shipment.kafka.MessageListener;
import com.securescm.shipment.kafka.MessageProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShipmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShipmentApplication.class, args);
	}
        
        @Bean
        public MessageProducer messageProducer(){
          return new MessageProducer();
        }
    
	@Bean
	public MessageListener messageListener() {
		return new MessageListener();
	}

}

