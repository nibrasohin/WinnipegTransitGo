package comp3350.WinnipegTransitGo.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import comp3350.WinnipegTransitGo.R;
import comp3350.WinnipegTransitGo.businessLogic.location.BusLocationNotifier;
import comp3350.WinnipegTransitGo.objects.TransitListItem;

/**
 * BusListViewFragment
 * Fragment class holding list view that displays
 * timing and location information for upcoming buses
 *
 * @author Abdul-Rasheed
 * @version 1.0
 * @since 23-06-2017
 */

public class BusListViewFragment extends Fragment implements AdapterView.OnItemClickListener, BusLocationNotifier {

    private DisplayAdapter displayAdapter;
    private MainActivity parentActivity;
    ListView mainListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        displayAdapter = new DisplayAdapter(getContext(), R.layout.listview_row, this);
        parentActivity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.bus_list_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainListView = (ListView) view.findViewById(R.id.bus_list);
        mainListView.setAdapter(displayAdapter);
        mainListView.setOnItemClickListener(this);
        parentActivity.updateLocation();
    }

    public void updateListView(List<TransitListItem> displayObjects) {
        clearListView();
        this.displayAdapter.addAll(displayObjects);
        this.displayAdapter.notifyDataSetChanged();
    }

    public void clearListView() {
        this.displayAdapter.clear();
    }

    public boolean isViewAtTop() {
        return mainListView.getFirstVisiblePosition() == 0;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TransitListItem item = displayAdapter.getItem(position);
        if (item != null) {
            parentActivity.showDetailedViewForBus(item);
        }
    }

    @Override
    public void showLocationForBus(TransitListItem item) {
        parentActivity.highlightBusOnMap(item.getBusStopNumber());
    }
}
