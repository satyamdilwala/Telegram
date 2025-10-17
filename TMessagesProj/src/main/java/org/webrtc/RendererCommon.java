package org.webrtc;

/**
 * Stub implementation of RendererCommon for compilation purposes.
 * This is a placeholder - VoIP functionality will not work properly.
 */
public class RendererCommon {
    
    public enum ScalingType {
        SCALE_ASPECT_FIT,
        SCALE_ASPECT_FILL,
        SCALE_ASPECT_BALANCED
    }
    
    public interface RendererEvents {
        void onFirstFrameRendered();
        void onFrameResolutionChanged(int videoWidth, int videoHeight, int rotation);
    }
}
