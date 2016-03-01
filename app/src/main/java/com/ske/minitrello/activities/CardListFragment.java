package com.ske.minitrello.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.adapters.CardAdapter;
import com.ske.minitrello.models.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CardListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private List<Card> cards;
    private CardAdapter cardAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;

    private OnFragmentInteractionListener mListener;

    public CardListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CardListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardListFragment newInstance(String param1) {
        CardListFragment fragment = new CardListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_card_list, container, false);

        cards = new ArrayList<Card>();
        cards.add(new Card("Title1", "Description"));
        if (!mParam1.equals("2")) {
            cards.add(new Card("Title2", "Description"));
            cards.add(new Card("Title3", "Description"));
            cards.add(new Card("Title4", "Description"));
            cards.add(new Card("Title5", "Description"));
            cards.add(new Card("Title6", "Description"));
            cards.add(new Card("Title7", "Description"));
            cards.add(new Card("Title8", "Description"));
            cards.add(new Card("Title9", "Description"));
            }


        cardAdapter = new CardAdapter(getActivity(),
                R.layout.card_list_item,
                cards);

        ListView lv = (ListView) rootView.findViewById(R.id.card_listView);
        lv.setAdapter(cardAdapter);

        TextView cardListTitle = (TextView) rootView.findViewById(R.id.cardlist_title);
        cardListTitle.setText("CardList Title " + mParam1);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            Log.i("BPress", "BUtton PResssed");
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
