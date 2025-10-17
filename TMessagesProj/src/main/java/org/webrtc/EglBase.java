package org.webrtc;

/**
 * Stub implementation of EglBase for compilation purposes.
 * This is a placeholder - VoIP functionality will not work properly.
 */
public interface EglBase {
    
    interface Context {
        // Stub interface
    }
    
    interface Config {
        // Stub interface
    }
    
    static final Config CONFIG_PLAIN = new Config() {};
    
    Context getEglBaseContext();
    
    static EglBase create(Context sharedContext, Config config) {
        return new EglBase() {
            @Override
            public Context getEglBaseContext() {
                return new Context() {};
            }
        };
    }
}
