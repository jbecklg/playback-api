package playback.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playback.api.model.*;

import java.util.UUID;

@RestController
public class PlaybackController {

    private final PlaybackManifestGenerator playbackManifestGenerator;

    public PlaybackController(final PlaybackManifestGenerator playbackManifestGenerator) {
        this.playbackManifestGenerator = playbackManifestGenerator;
    }

    @RequestMapping(value = "/")
    public @ResponseBody
    ResponseEntity<PlaybackManifest> getPlaybackManifest(
            @RequestParam(value = "titleId") UUID titleId,
            @RequestParam(value = "userId") UUID userId,
            @RequestParam(value = "deviceType") DeviceType deviceType) {

        try {
            return ResponseEntity.ok(playbackManifestGenerator.generatePlaybackManifest(titleId, deviceType, userId));
        } catch (InactiveUserException iue) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}