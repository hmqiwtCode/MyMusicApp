package com.example.serviceboundmusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListMusicFragment extends Fragment{

    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView cv_list_audio;
    private ConstraintLayout player_sheet;

    private ImageButton btn_play_audio;
    private TextView tv_fileName;

    private SeekBar player_seekbar;

    private boolean isBound = false;
    private boolean isPlay = false;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ListMusicFragment() {

    }


    public static ListMusicFragment newInstance(String param1, String param2) {
        ListMusicFragment fragment = new ListMusicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_music, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("ok","Meo workd");

        final Intent intent = new Intent(getContext(), MyService.class);



        btn_play_audio = view.findViewById(R.id.btn_player_play);
        if (ConnectServiceM.isBound){
            btn_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_24));
        }else {
            btn_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24));
        }
        btn_play_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectServiceM.isBound){
                    pauseAudio();
                }else{
                    resumeAudio(intent);
                }

            }
        });
    }

    private void pauseAudio(){
        getContext().unbindService(ConnectServiceM.getService());
        btn_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24));
        ConnectServiceM.isBound = false;
    }

    private void resumeAudio(Intent intent){
        getContext().bindService(intent, ConnectServiceM.getService(),
                Context.BIND_AUTO_CREATE);
        btn_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_24));
        ConnectServiceM.isBound = true;


    }




}