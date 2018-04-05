package com.example.inclass08;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MainFragment extends Fragment {
    EditText name, email;
    RadioGroup department;
    SeekBar mood;
    String selected;

    static String STUDENT_KEY="STUDENT";
    String NAME_KEY="NAME";
    String EMAIL_KEY="EMAIL";
    String DEPARTMENT_KEY="DEPARTMENT";
    String MOOD_KEY="MOOD";

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        name=getView().findViewById(R.id.editTextName);
        email=getView().findViewById(R.id.editTextEmail);
        department = getView().findViewById(R.id.RadioGroupDept);
        mood=getActivity().findViewById(R.id.seekBarMood);




        Button btn = (Button) getView().findViewById(R.id.buttonSubmit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String iname,iemail,idepartment;
                double imood;
                iname= name.getText().toString();
                iemail= email.getText().toString();
                imood=mood.getProgress();
                int id = department.getCheckedRadioButtonId();
                String dept = "";
                if (id==R.id.radioButtonSIS) {
                    // Log.d("demo","The Color Selected is Red");
                    dept = "SIS";
                } else if (id == R.id.radioButtonCS) {
                    dept="CS";
                    //Log.d("demo","The Color Selected is Green");
                } else if (id == R.id.radioButtonBIO) {
                    dept="BIO";
                    //Log.d("demo", "The Color Selected is Blue");
                }
                else if (id == R.id.radioButtonOthers) {
                    dept="Others";
                    // Log.d("demo","The Color Selected is Blue");
                } else if (id == -1)
                {
                    //Log.d("demo","No Department is Selected");
                }


                if((iname==null||iname=="")||(iemail==null||iemail=="")||(id==-1))
                {
                    Toast t=Toast.makeText(getContext(),"Input Cannot be Empty",Toast.LENGTH_SHORT);
                    t.show();
                    //idepartment= Integer.parseInt(department.getText().toString());
                    //imood= Integer.parseInt(IInches.getText().toString());
                }
                else
                {

                    Student student=new Student(iname,iemail,dept,imood);
                    mListener.onFragmentInteraction(student);
/*                    Intent intent= new Intent(MainActivity.this,DisplayActivity.class);
                    intent.putExtra(STUDENT_KEY,new Student(iname,iemail,dept,imood));

                    startActivity(intent);

*/
                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
*/
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Student student);
    }
}
