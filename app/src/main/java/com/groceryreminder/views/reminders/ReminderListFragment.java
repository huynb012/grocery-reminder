package com.groceryreminder.views.reminders;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.groceryreminder.R;
import com.groceryreminder.models.Reminder;
import com.melnykov.fab.FloatingActionButton;

import org.solovyev.android.views.llm.DividerItemDecoration;
import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.List;

public class ReminderListFragment extends Fragment {

    private static final String REMINDERS_KEY = "REMINDERS_KEY";
    private List<Reminder> reminders;
    private OnAddReminderRequestListener onAddReminderRequestListener;
    private OnReminderDataChangeListener onReminderDataChangeListener;
    private RemindersRecyclerViewAdapter adapter;

    public static ReminderListFragment newInstance(List<Reminder> reminders) {
        ReminderListFragment fragment = new ReminderListFragment();
        Bundle args = new Bundle();
        args.putSerializable(REMINDERS_KEY, (java.io.Serializable) reminders);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReminderListFragment() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            this.reminders = (List<Reminder>)getArguments().getSerializable(REMINDERS_KEY);
        }
        Log.d("ReminderListFragment","In onCreateView");
        // TODO: Change Adapter to display your content
        View root = inflater.inflate(R.layout.reminder_list_fragment, container, false);
        RecyclerView recyclerView = wireListView(root);
        wireAddReminderRequestButton(root, recyclerView);

        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("ReminderListFragment", "In onAttach");
        onAddReminderRequestListener = (OnAddReminderRequestListener)activity;
        onReminderDataChangeListener = (OnReminderDataChangeListener)activity;
    }

    private RecyclerView wireListView(View root) {
        RecyclerView list = (RecyclerView)root.findViewById(R.id.reminders_recycler_view);
        list.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(linearLayoutManager);
        this.adapter = new RemindersRecyclerViewAdapter(reminders, onReminderDataChangeListener);
        list.setAdapter(this.adapter);
        setReminderListOnTouchListener(list);

        return list;
    }

    private void setReminderListOnTouchListener(RecyclerView list) {
        ReminderSwipeListener swipeListener = new ReminderSwipeListener(reminders, (RemindersRecyclerViewAdapter)list.getAdapter());
        SwipeableRecyclerViewTouchListener swipeableRecyclerViewTouchListener = new SwipeableRecyclerViewTouchListener(list, swipeListener);
        list.addOnItemTouchListener(swipeableRecyclerViewTouchListener);
    }

    private void wireAddReminderRequestButton(View root, RecyclerView list) {
        FloatingActionButton fab = (FloatingActionButton)root.findViewById(R.id.fab);
        fab.attachToRecyclerView(list);
        fab.setOnClickListener(new RequestAddReminderClickListener(this.onAddReminderRequestListener));
    }

    public OnAddReminderRequestListener getOnAddReminderRequestListener() {
        return onAddReminderRequestListener;
    }

    public void setReminders(List<Reminder> reminders) {
        this.adapter.setReminders(reminders);
    }
}
