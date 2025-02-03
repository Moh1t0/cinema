package org.javaacademy.cinema.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

     @Bean
     public OpenAPI customApi() {
          Info info = new Info()
                  .title("Api для кинотеатра")
                  .description("""
                          Это API для управления афишей кинотеатра. Данное API предоставляет администратору возможность
                          создания кино, сеансов и смотреть проданные билеты.
                          Пользователь имеет доступ к просмотру доступых фильмов и покупки билетов."""
                  );
          return new OpenAPI().info(info);
     }
}
