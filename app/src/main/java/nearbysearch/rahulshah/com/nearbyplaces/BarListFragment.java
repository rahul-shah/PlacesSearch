package nearbysearch.rahulshah.com.nearbyplaces;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import nearbysearch.rahulshah.com.nearbyplaces.adapter.BarListAdapter;
import nearbysearch.rahulshah.com.nearbyplaces.model.MapResultObject;
import nearbysearch.rahulshah.com.nearbyplaces.utils.PreferencesManager;

/**
 * A placeholder fragment containing a simple view.
 */
public class BarListFragment extends Fragment
{
    private static final String TAG = "MyMapFragment.java";
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static BarListAdapter mBarListAdapter;
    private static Location mCurrentLocation;

    @BindView(R.id.bar_list_rv)
    RecyclerView mBarListRV;

    public BarListFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static BarListFragment newInstance(MapResultObject apiResponse, Location location) {
        BarListFragment fragment = new BarListFragment();
        Bundle args = new Bundle();
        mCurrentLocation = location;
        //args.putSerializable(ARG_SECTION_NUMBER, apiResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bar_list, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mBarListAdapter = new BarListAdapter(getActivity(),MainActivity.mApiResponseObject.getResults(),mCurrentLocation);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AppController.getInstance());
        mBarListRV.setLayoutManager(mLayoutManager);
        mBarListRV.setItemAnimator(new DefaultItemAnimator());
        mBarListRV.setAdapter(mBarListAdapter);
        mBarListAdapter.notifyDataSetChanged();

    }
}