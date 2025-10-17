package org.webrtc;

/**
 * Stub implementation of VideoFrame for compilation purposes.
 * This is a placeholder - VoIP functionality will not work properly.
 */
public class VideoFrame {
    
    public VideoFrameBuffer getBuffer() {
        return new VideoFrameBuffer();
    }
    
    public static class VideoFrameBuffer {
        public int getHeight() {
            return 0;
        }
        
        public int getWidth() {
            return 0;
        }
    }
}
