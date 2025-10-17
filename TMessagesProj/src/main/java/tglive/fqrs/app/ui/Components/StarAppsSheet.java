package tglive.fqrs.app.ui.Components;

import static tglive.fqrs.app.LocaleController.getString;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tglive.fqrs.app.MessagesController;
import tglive.fqrs.app.R;
import tglive.fqrs.app.tgnet.TLRPC;

public class StarAppsSheet extends BottomSheetWithRecyclerListView {

    private DialogsBotsAdapter adapter;

    public StarAppsSheet(Context context) {
        super(context, null, true, false, false, null);

        fixNavigationBar();
        handleOffset = true;
        setShowHandle(true);

        setSlidingActionBar();

        recyclerListView.setPadding(backgroundPaddingLeft, 0, backgroundPaddingLeft, 0);
        recyclerListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                adapter.checkBottom();
            }
        });
        recyclerListView.setOnItemClickListener((view, position) -> {
            position--;
            Object obj = adapter.getObject(position);
            if (obj instanceof TLRPC.User) {
                MessagesController.getInstance(currentAccount).openApp(attachedFragment, (TLRPC.User) obj, null, 0, null);
            }
        });
    }

    @Override
    protected CharSequence getTitle() {
        return getString(R.string.SearchAppsExamples);
    }

    @Override
    protected RecyclerListView.SelectionAdapter createAdapter(RecyclerListView listView) {
        adapter = new DialogsBotsAdapter(listView, getContext(), currentAccount, 0, true, resourcesProvider);
        adapter.setApplyBackground(false);
        return adapter;
    }


}
