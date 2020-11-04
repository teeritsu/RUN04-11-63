package com.example.apprunner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondFragment extends Fragment  {

    TextView tv_FName,tv_LName,tv_stuts;
    String first_name,last_name,type,email,password,name_event;
    Button bt_search_event;
    EditText edt_search_event;
    private RecyclerView recyclerView;
    List<UserListResponse> userListResponses;

    public static final String url = "http://10.0.2.2:3000/";

    public SecondFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void callData() {
        SecondFragment secondFragment = new SecondFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(secondFragment.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        int type = 1;
        Call call = service.getUserName(type);
        call.enqueue(new Callback(){
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    List<UserListResponse> userListResponses = (List<UserListResponse>) response.body();
//                    Toast.makeText(SecondActivity.this, "Response_eventList" +"  "+ userListResponses.size(), Toast.LENGTH_SHORT).show();
                    recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_user);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                    recyclerView.setLayoutManager(layoutManager);
                    MyRecyclerAdapter adapter = new MyRecyclerAdapter(requireContext(),userListResponses);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentHandle = inflater.inflate(R.layout.fragment_second, container, false);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            first_name = getArguments().getString("first_name");
            last_name = getArguments().getString("last_name");
            type = getArguments().getString("type");
            String email = getArguments().getString("email");
            String password = getArguments().getString("password");

            tv_FName = fragmentHandle.findViewById(R.id.tv_FName_user);
            tv_LName = fragmentHandle.findViewById(R.id.tv_LName_user);
            tv_stuts = fragmentHandle.findViewById(R.id.tv_stuts_user);
            tv_FName.setText(first_name);
            tv_LName.setText(last_name);
            tv_stuts.setText(type);
            callData();
            return fragmentHandle;
        }

        edt_search_event = (EditText) fragmentHandle.findViewById(R.id.edt_search_user);
        bt_search_event = (Button) fragmentHandle.findViewById(R.id.bt_search_event);
        bt_search_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_event = edt_search_event.getText().toString();
                if(name_event.equals("")){
                    callData();
                }
                Toast.makeText(requireActivity(), name_event, Toast.LENGTH_SHORT).show();
                SecondFragment secondFragment = new SecondFragment();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(secondFragment.url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                OrganizerAPI service = retrofit.create(OrganizerAPI.class);
                int status = 1;
                Call call = service.search_event(status,name_event);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()){
                            List<UserListResponse> userListResponses = (List<UserListResponse>) response.body();
                            //Toast.makeText(MainOrganizer.this, "Response_eventList" +"  "+ userListResponses.size(), Toast.LENGTH_SHORT).show();
                            recyclerView = (RecyclerView)getView().findViewById(R.id.recycler_view_user);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                            recyclerView.setLayoutManager(layoutManager);
                            MyRecyclerAdapter adapter = new MyRecyclerAdapter(requireContext(),userListResponses);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return inflater.inflate(R.layout.fragment_second, container, false);
    }
}