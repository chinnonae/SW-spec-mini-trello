package com.ske.minitrello.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ske.minitrello.R;
import com.ske.minitrello.adapters.CardAdapter;
import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardKeeper;
import com.ske.minitrello.models.CardList;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class CardListFragment extends Fragment implements Observer {

    private CardList cardList;
    private List<Card> cards;
    private CardAdapter cardAdapter;
    private int position;
    private OnFragmentInteractionListener mListener;

    public CardListFragment() {
    }

    public static CardListFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        CardListFragment fragment = new CardListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int position = getArguments().getInt("position");
        cardList = CardKeeper.getInstance().getLists().get(position);
        cards = cardList.getCards();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_card_list, container, false);

        cardAdapter = new CardAdapter(getActivity(),
                R.layout.card_list_item,
                cards);

        ListView lv = (ListView) rootView.findViewById(R.id.card_listView);
        lv.setAdapter(cardAdapter);
        ImageView add_button = (ImageView)rootView.findViewById(R.id.add_button);
        TextView cardListTitle = (TextView) rootView.findViewById(R.id.cardlist_title);
        cardListTitle.setText(cardList.getName());

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCardDialog acd = new AddCardDialog();
                int position = getArguments().getInt("position");
                acd.addObserver(CardListFragment.this);
                acd.showDialog(getActivity(), position);
            }
        });

        return rootView;
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

    @Override
    public void update(Observable observable, Object data) {
        cardAdapter.notifyDataSetChanged();
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
