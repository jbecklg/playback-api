package playback.api.service;

import java.net.URI;
import java.util.List;
import java.util.UUID;

public interface SteeringService {

    public List<URI> getStreamURLs(UUID streamId);

}
