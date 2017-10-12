package playback.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class PlaybackAPIApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PlaybackAPIApplication.class, args);
    }

}
