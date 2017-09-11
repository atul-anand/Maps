package com.zemoso.atul.maps.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zemoso.atul.maps.R;
import com.zemoso.atul.maps.javabeans.RegistryUser;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfile extends Fragment {

    private static final String TAG = MyProfile.class.getSimpleName();

    private SharedPreferences.Editor mEditor;

    private RegistryUser mUser;

    private TextView mUsername;
    private TextView mName;
    private TextView mRole;
    private Button mLogout;

    private String username;
    private String f_name;
    private String l_name;
    private String name;
    private String role;


    public MyProfile() {
        // Required empty public constructor
    }

    public static MyProfile newInstance() {
        return new MyProfile();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Realm realm = Realm.getDefaultInstance();
        mUser = realm.where(RegistryUser.class).findFirst();
        Log.d(TAG, String.valueOf(mUser));

        mUsername = view.findViewById(R.id.profile_username);
        mName = view.findViewById(R.id.profile_name);
        mRole = view.findViewById(R.id.profile_role);
        mLogout = view.findViewById(R.id.profile_logout);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Created");

        getActivity().setTitle(getResources().getString(R.string.nav_profile));

        username = mUser.getUsername();
        f_name = mUser.getF_name();
        l_name = mUser.getL_name();
        name = f_name + " " + l_name;
        role = mUser.getRole();

        Log.d(TAG, username);
        Log.d(TAG, name);
        Log.d(TAG, role);

        mUsername.setText(username);
        mName.setText(name);
        mRole.setText(role);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                mEditor.putString("access_token", "");
                mEditor.putString("refresh_token", "");
                mEditor.apply();
                logout();
            }
        });


    }

    private void logout() {
        RealmConfiguration realmConfiguration = Realm.getDefaultConfiguration();
        Realm.deleteRealm(realmConfiguration);
        Realm.setDefaultConfiguration(realmConfiguration);
        getActivity().finish();
    }
}
