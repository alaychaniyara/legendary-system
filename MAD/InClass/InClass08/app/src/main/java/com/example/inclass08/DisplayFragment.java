package com.example.inclass08;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class DisplayFragment extends Fragment {
    TextView rname,remail,rdept,rmood;
    static final int NAME_CODE=100;
    static final int EMAIL_CODE=200;
    static final int DEPT_CODE=300;
    static final int MOOD_CODE=400;
    private Student dstudent;
    static String EDIT_KEY="a";
static  String ARG_PARAM1="1";
    static  String ARG_PARAM2="2";
    static  String ARG_PARAM3="3";
    private String mParam1;
    private String mParam2;

    private OnDisplayFragmentInteractionListener mListener;

    public DisplayFragment() {
        // Required empty public constructor
    }


    public static DisplayFragment newInstance(Student student) {
        DisplayFragment fragment = new DisplayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM3,student);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dstudent= (Student) getArguments().getSerializable(ARG_PARAM3);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
View view =inflater.inflate(R.layout.fragment_display, container, false);
        rname=view.findViewById(R.id.textViewIname);
        remail=view.findViewById(R.id.textViewIemail);
        rdept=view.findViewById(R.id.textViewDept);
        rmood=view.findViewById(R.id.textViewImood);

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //sdstudent = savedInstanceState.g

        rname.setText((CharSequence) dstudent.name);
        remail.setText(dstudent.email);
        rdept.setText(dstudent.department);
        rmood.setText(Double.toString(dstudent.mood)+ "% Positive");

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
     //       mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDisplayFragmentInteractionListener) {
            mListener = (OnDisplayFragmentInteractionListener) context;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnDisplayFragmentInteractionListener {
        // TODO: Update argument type and name
     //   void onFragmentInteraction(Uri uri);
    }
}
