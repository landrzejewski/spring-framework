package pl.training;

import lombok.Data;

import java.time.Instant;

@Data
public class ServerTime {

    private Instant timestamp = Instant.now();

}
