package main.java.playback.model;

import main.java.asset.Asset;
import main.java.common.model.ApiRequest;
import main.java.session.Session;

/**
 * A device request to fetch the playback manifest.
 */
public class PlaybackManifestRequest extends ApiRequest {
    private Asset asset;

    /**
     * Construct a playback request.
     *
     * @param session User's session.
     * @param asset Requested asset.
     */
    public PlaybackManifestRequest(Session session, Asset asset) {
        super(session);
        this.asset = asset;
    }

    public Asset getAsset() {
        return asset;
    }
}
