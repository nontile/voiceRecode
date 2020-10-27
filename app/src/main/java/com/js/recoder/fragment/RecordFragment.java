package com.js.recoder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.js.recoder.R;


public class RecordFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private static final String LOG_TAG = RecordFragment.class.getSimpleName();

    private int position;
    private FloatingActionButton recordBtn = null;
    private Button pauseBtn = null;

    private TextView recodingTv;
    private int recodingTvCount = 0;

    private boolean startRecording = true;
    private boolean pauseRecording = true;

    private Chronometer chronometer = null;
    long timeWhenPaused = 0; //stores time when user clicks pause button

    public static RecordFragment newInstance(int position) {

        Bundle b = new Bundle();

        RecordFragment fragment = new RecordFragment();
        b.putInt(ARG_POSITION, position);
        fragment.setArguments(b);
        return fragment;
    }

    public RecordFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_record, container, false);
        chronometer = view.findViewById(R.id.chronometer);
        recodingTv = view.findViewById(R.id.recordingTv);
        recordBtn = view.findViewById(R.id.recordBtn);
        recordBtn.setBackgroundColor(getResources().getColor(R.color.primary));
        recordBtn.setRippleColor(getResources().getColor(R.color.primary_dark));
        recordBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onRecord(startRecording);
                startRecording = !startRecording;
            }
        });
        return view;
    }

    //TODO: start, stop, recording, pause
    private void onRecord(boolean start){
        if(start) {
            recordBtn.setImageResource(R.drawable.ic_stop_36);

        }else{
            recordBtn.setImageResource(R.drawable.ic_mic_36);
        }
    }
}
