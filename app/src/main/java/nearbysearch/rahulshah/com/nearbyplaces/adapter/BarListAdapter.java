package nearbysearch.rahulshah.com.nearbyplaces.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import nearbysearch.rahulshah.com.nearbyplaces.R;
import nearbysearch.rahulshah.com.nearbyplaces.model.PlacesSearchResults;
import nearbysearch.rahulshah.com.nearbyplaces.utils.DistanceCalculatorUtil;
import nearbysearch.rahulshah.com.nearbyplaces.utils.PreferencesManager;

/**
 * Created by C0244308 on 13/01/2017.
 */

public class BarListAdapter extends RecyclerView.Adapter<BarListAdapter.AccountsViewHolder>
{
    private List<PlacesSearchResults> mListOfAccounts = new ArrayList<>();
    private Context mContext;
    private Location mCurrentLocation;
    private PlacesSearchResults mPlaceSearchData;

    public BarListAdapter(Context context,List<PlacesSearchResults> listOfAccounts, Location location)
    {
        mContext = context;
        mListOfAccounts = listOfAccounts;
        mCurrentLocation = location;
    }

    @Override
    public AccountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)   {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        switch(viewType)
        {
            case VIEW_TYPES.Header:
                view = inflater.inflate(R.layout.list_view_header,parent,false);
                break;

            case VIEW_TYPES.Normal:
                view = inflater.inflate(R.layout.bar_list_row,parent,false);
                break;

            default:
                view = inflater.inflate(R.layout.bar_list_row,parent,false);
                break;

        }

        AccountsViewHolder viewHolder = new AccountsViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return VIEW_TYPES.Header;
        }
        else {
            return VIEW_TYPES.Normal;
        }
    }

    @Override
    public void onBindViewHolder(AccountsViewHolder holder, int position) {
        mPlaceSearchData = mListOfAccounts.get(position);
        if(position != 0) {
            holder.mPlaceName.setText(mPlaceSearchData.getName());
            holder.mPlaceDistance.setText(String.valueOf(DistanceCalculatorUtil.distance(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),mPlaceSearchData.getPlaceGeometry().getLocation().getLat(),mPlaceSearchData.getPlaceGeometry().getLocation().getLng())));
        }

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", mPlaceSearchData.getPlaceGeometry().getLocation().getLat(),mPlaceSearchData.getPlaceGeometry().getLocation().getLng());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListOfAccounts.size();
    }

    public class AccountsViewHolder extends RecyclerView.ViewHolder
    {
        private View mItemView;
        public TextView mPlaceName;
        public TextView mPlaceDistance;

        public View getView()
        {
            return mItemView;
        }

        public AccountsViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;

            mPlaceName = (TextView) itemView.findViewById(R.id.bar_name);
            mPlaceDistance = (TextView) itemView.findViewById(R.id.bar_distance);
        }
    }

    private class VIEW_TYPES
    {
        public static final int Header = 1;
        public static final int Normal = 2;
    }
}
