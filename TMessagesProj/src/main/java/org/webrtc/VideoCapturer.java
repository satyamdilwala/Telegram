package org.webrtc;

import android.content.Context;

/**
 * Stub implementation of VideoCapturer for compilation purposes.
 * This is a placeholder - VoIP functionality will not work properly.
 */
public interface VideoCapturer {
    
    interface CapturerObserver {
        void onCapturerStarted(boolean success);
        void onCapturerStopped();
        void onFrameCaptured(VideoFrame frame);
    }
    
    void initialize(SurfaceTextureHelper surfaceTextureHelper, Context context, CapturerObserver capturerObserver);
    void startCapture(int width, int height, int framerate);
    void stopCapture() throws InterruptedException;
    void changeCaptureFormat(int width, int height, int framerate);
    void dispose();
    boolean isScreencast();
}
