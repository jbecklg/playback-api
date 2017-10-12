package playback.api.impl;

import org.springframework.stereotype.Service;
import playback.api.service.SteeringService;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class SteeringServiceImpl implements SteeringService {

    public List<URI> getStreamURLs(UUID streamId) {

        return Arrays.asList(
                URI.create("https://cdn-a.netflix.com/" + streamId.toString()),
                URI.create("https://cdn-b.netflix.com/" + streamId.toString()),
                URI.create("https://cdn-c.netflix.com/" + streamId.toString()),
                URI.create("https://cdn-d.netflix.com/" + streamId.toString())
        );
    }
}
