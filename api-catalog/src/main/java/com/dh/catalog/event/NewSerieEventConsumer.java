package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.service.CatalogService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NewSerieEventConsumer {

    private CatalogService catalogService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_SERIE)
    public void listen(NewSerieEventConsumer.Data message){
        catalogService.saveSerie(message);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data{
        private String name;

        private String genre;

    }
}
