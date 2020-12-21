package com.js.recoder.fragment;

import android.os.Bundle;
import android.os.FileObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.js.recoder.R;
import com.js.recoder.adapter.FileViewerAdapter;

public class FileViewFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private static final String LOG_TAG = FileViewFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private FileViewerAdapter fileViewerAdapter;

    private int position;

    public FileViewFragment (){}

    public static FileViewFragment newInstance(int position) {

        Bundle b = new Bundle();

        FileViewFragment fragment = new FileViewFragment();
        b.putInt(ARG_POSITION, position);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);

    }
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_file, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        // newest to oldest
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);

        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        fileViewerAdapter = new FileViewerAdapter(getActivity(), llm);
        recyclerView.setAdapter(fileViewerAdapter);

        return view;
    }

    FileObserver observer = new FileObserver(android.os.Environment.getExternalStorageDirectory().toString() + "/VoiceRecorder/") {
        @Override
        public void onEvent(int event, @Nullable String file) {
            if(event == FileObserver.DELETE){
                // user deletes a recording file out of the app

                String filePath = android.os.Environment.getExternalStorageDirectory().toString()
                        + "/SoundRecorder" + file + "]";

                Log.d(LOG_TAG, "File deleted ["
                        + android.os.Environment.getExternalStorageDirectory().toString()
                        + "/SoundRecorder" + file + "]");

                // remove file from database and recyc
                // lerview
                fileViewerAdapter.removeOutOfApp(filePath);
            }
        }
    };
}
