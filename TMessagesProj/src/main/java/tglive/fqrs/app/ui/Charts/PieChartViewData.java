package tglive.fqrs.app.ui.Charts;

import android.animation.Animator;

import tglive.fqrs.app.ui.Charts.data.ChartData;
import tglive.fqrs.app.ui.Charts.view_data.StackLinearViewData;

public class PieChartViewData extends StackLinearViewData {

    float selectionA;
    float drawingPart;
    Animator animator;

    public PieChartViewData(ChartData.Line line) {
        super(line);
    }
}
