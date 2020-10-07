package com.js.recoder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.js.recoder.R;

public class FileViewFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private static final String LOG_TAG = FileViewFragment.class.getSimpleName();

    private int position;

    FileViewFragment (){}

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_file, container, false);

        return v;
    }
}
