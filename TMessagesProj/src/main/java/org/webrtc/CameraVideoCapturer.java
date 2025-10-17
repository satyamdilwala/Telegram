package org.webrtc;

/**
 * Stub implementation of CameraVideoCapturer for compilation purposes.
 * This is a placeholder - VoIP functionality will not work properly.
 */
public interface CameraVideoCapturer extends VideoCapturer {
    
    interface CameraEventsHandler extends CapturerObserver {
        void onCameraError(String errorDescription);
        void onCameraDisconnected();
        void onCameraFreezed(String errorDescription);
        void onCameraOpening(String cameraName);
        void onFirstFrameAvailable();
        void onCameraClosed();
    }
    
    interface CameraSwitchHandler {
        void onCameraSwitchDone(boolean isFrontCamera);
        void onCameraSwitchError(String errorDescription);
    }
    
    void switchCamera(CameraEventsHandler eventsHandler);
    void switchCamera(CameraEventsHandler eventsHandler, String cameraName);
    void switchCamera(CameraSwitchHandler switchHandler, String cameraName);
    boolean isScreencast();
}
