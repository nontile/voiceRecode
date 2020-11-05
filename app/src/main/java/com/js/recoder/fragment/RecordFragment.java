package com.js.recoder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.js.recoder.R;
import com.js.recoder.RecordingService;

import java.io.File;


public class RecordFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private static final String LOG_TAG = RecordFragment.class.getSimpleName();

    private int position;
    private FloatingActionButton recordBtn = null;
    private Button pauseBtn = null;

    private TextView recordingTv;
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
        recordingTv = view.findViewById(R.id.recordingTv);
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

        pauseBtn = (Button) view.findViewById(R.id.pauseBtn);
        pauseBtn.setVisibility(View.GONE); //hide pause button before recording starts
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPauseRecord(pauseRecording);
                pauseRecording = !pauseRecording;
            }
        });
        return view;
    }

    //TODO: start, stop, recording, pause
    private void onRecord(boolean start){
            Intent intent = new Intent(getActivity(), RecordingService.class);

            if (start) {
                // start recording
                recordBtn.setImageResource(R.drawable.ic_stop_36);
                //mPauseButton.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),R.string.toast_recording_start,Toast.LENGTH_SHORT).show();
                File folder = new File(Environment.getExternalStorageDirectory() + "/ChickRecorder");
                if (!folder.exists()) {
                    //folder /SoundRecorder doesn't exist, create the folder
                    folder.mkdir();
                }

                //start Chronometer
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        if (recodingTvCount == 0) {
                            recordingTv.setText(getString(R.string.record_in_progress) + ".");
                        } else if (recodingTvCount == 1) {
                            recordingTv.setText(getString(R.string.record_in_progress) + "..");
                        } else if (recodingTvCount == 2) {
                            recordingTv.setText(getString(R.string.record_in_progress) + "...");
                            recodingTvCount = -1;
                        }
                        recodingTvCount++;
                    }
                });

                //start RecordingService
                getActivity().startService(intent);
                //keep screen on while recording
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                recordingTv.setText(getString(R.string.record_in_progress) + ".");
                recodingTvCount++;

        }else{
            recordBtn.setImageResource(R.drawable.ic_mic_36);
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            timeWhenPaused = 0;
            recordingTv.setText(getString(R.string.record_prompt));
            getActivity().stopService(intent);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        }
    }

    //TODO: implement pause recording
    private void onPauseRecord(boolean pause) {
        if (pause) {
            //pause recording
            pauseBtn.setCompoundDrawablesWithIntrinsicBounds
                    (R.drawable.ic_play_yellow_36 ,0 ,0 ,0);
            recordingTv.setText((String)getString(R.string.resume_recording_button).toUpperCase());
            timeWhenPaused = chronometer.getBase() - SystemClock.elapsedRealtime();
            chronometer.stop();
        } else {
            //resume recording
            pauseBtn.setCompoundDrawablesWithIntrinsicBounds
                    (R.drawable.ic_pause_yello_24 ,0 ,0 ,0);
            recordingTv.setText((String)getString(R.string.pause_recording_button).toUpperCase());
            chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenPaused);
            chronometer.start();
        }
    }
}
