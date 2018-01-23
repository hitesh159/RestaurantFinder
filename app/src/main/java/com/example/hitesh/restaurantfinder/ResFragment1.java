package com.example.hitesh.restaurantfinder;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResFragment1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResFragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView listView;
    Activity context;
    private List<Details> detailsList;
    private DetailsAdapter detailsAdapter;
    public class GetDetails extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result="";
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestProperty("user-key","efdf4320ab8775f2bd0092ee74bc2292");
                InputStream inputStream=connection.getInputStream();
                InputStreamReader reader=new InputStreamReader(inputStream);
                int data=reader.read();
                while(data!=-1){
                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }
                Log.i("aaye",result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject detail = new JSONObject(s);
                String name = detail.getString("name");
                JSONObject location = new JSONObject(detail.getString("location"));
                String address = location.getString("address");
                detailsList.add(new Details("name", name));
                detailsList.add(new Details("address", address));
                String cuisines = detail.getString("cuisines");
                JSONObject user_rating = new JSONObject(detail.getString("user_rating"));
                String rating = user_rating.getString("aggregate_rating");
                detailsList.add(new Details("cuisines", cuisines));
                detailsList.add(new Details("user-rating", rating));
                detailsAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private OnFragmentInteractionListener mListener;

    public ResFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     *
     * @return A new instance of fragment ResFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static ResFragment1 newInstance(String param1) {
        ResFragment1 fragment = new ResFragment1();
        Bundle args = new Bundle();
        args.putString("resId", param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public void onStart() {
        super.onStart();
//        TextView tv=(TextView)context.findViewById(R.id.fragment1);
//        tv.setText(getArguments().getString("resId"));
        listView = (ListView) context.findViewById(R.id.details);
        detailsList = new ArrayList<>();
        //detailsList.add(new Details("hmm","ouu"));
        detailsAdapter = new DetailsAdapter(getContext(), detailsList);
        listView.setAdapter(detailsAdapter);
        GetDetails task = new GetDetails();
        task.execute("https://developers.zomato.com/api/v2.1/restaurant?res_id=" + getArguments().getString("resId"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity();
        return inflater.inflate(R.layout.fragment_res_fragment1, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
