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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListMusicFragment extends Fragment implements MusicListAdapter.OnItemListClickListener{

    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView cv_list_audio;
    private ConstraintLayout player_sheet;

    private ImageButton btn_play_audio;
    private MusicListAdapter musicListAdapter;

    private SeekBar player_seekbar;
    private TextView tv_file_name;
    private TextView tv_status;

    private boolean isCollapsed = false;



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
        player_sheet = view.findViewById(R.id.player_sheet);
        cv_list_audio = view.findViewById(R.id.cv_list_audio);
        tv_file_name = view.findViewById(R.id.tv_file_name);
        tv_status = view.findViewById(R.id.player_header_title);

        ConnectServiceM.intent = new Intent(getContext(),MyService.class);

        try {
            musicListAdapter = new MusicListAdapter(getMusicInRawFolder(),this);
            cv_list_audio.setLayoutManager(new LinearLayoutManager(getContext()));
            cv_list_audio.setAdapter(musicListAdapter);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        bottomSheetBehavior = BottomSheetBehavior.from(player_sheet);
        player_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCollapsed) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    isCollapsed = false;
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    isCollapsed = true;
                }
            }
        });

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        try {
            ConnectServiceM.intent.putExtra("music",getMusicInRawFolder().get(0));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
                    resumeAudio(ConnectServiceM.intent);
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
        if (ConnectServiceM.isBound){
            getContext().unbindService(ConnectServiceM.getService());
        }
        getContext().bindService(intent, ConnectServiceM.getService(),
                Context.BIND_AUTO_CREATE);
        btn_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_24));
        ConnectServiceM.isBound = true;


    }


    private List<Music> getMusicInRawFolder() throws IllegalAccessException {
        List<Music> listMusic = new ArrayList<>();
        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++) {
            listMusic.add(new Music(fields[count].getInt(fields[count]),fields[count].getName()));
        }
        Log.d("huhuhu",listMusic.get(0).toString());
        return listMusic;
    }


    @Override
    public void onClickListener(Music music, int position) {
        ConnectServiceM.intent.putExtra("music",music);
        resumeAudio(ConnectServiceM.intent);
        setInfo(music);
     }


     private void setInfo(Music music){
         tv_file_name.setText(music.getName());
         tv_status.setText("Playing");
     }

}