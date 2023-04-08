package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.service.CatalogService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NewMovieEventConsumer {


    private CatalogService catalogService;

    public NewMovieEventConsumer(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_MOVIE)
    public void listen(NewMovieEventConsumer.Data message){
        log.info(message.getName());
        catalogService.saveMovie(message);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data{
        private String id;
        private String name;

        private String genre;

        private String urlStream;
    }
}
