package tglive.fqrs.app.ui.Components.FloatingDebug;

import java.util.List;

public interface FloatingDebugProvider {
    List<FloatingDebugController.DebugItem> onGetDebugItems();
}
