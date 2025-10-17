package org.webrtc;

/**
 * Stub implementation of VideoSink for compilation purposes.
 * This is a placeholder - VoIP functionality will not work properly.
 */
public interface VideoSink {
    void onFrame(VideoFrame frame);
    void setParentSink(VideoSink parent);
}
