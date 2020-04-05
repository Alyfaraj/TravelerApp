package com.dev.Traveler.ui.Populer;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.Traveler.models.Posts;
import com.dev.Traveler.R;
import com.dev.Traveler.ui.SearchActivity;
import com.dev.Traveler.adapter.MyFotosAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopulerFragment extends Fragment {
    View view;
   // ProgressBar bar;
    FloatingActionButton fab;
    private RecyclerView recyclerView;
    private MyFotosAdapter myFotosAdapter;
    private List<Posts> postsList;

    FirebaseUser firebaseUser;
    String profileid;

    public PopulerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_populer, container, false);
      //  bar=view.findViewById(R.id.progress_circular1);
        fab = view.findViewById(R.id.FAB_addperson);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = view.findViewById(R.id.recycler_view1);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        postsList = new ArrayList<>();
        myFotosAdapter = new MyFotosAdapter(getContext(), postsList);
        recyclerView.setAdapter(myFotosAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
      //  myFotos();


        return view;
    }



    private void myFotos() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final Posts posts = snapshot.getValue(Posts.class);
                        postsList.add(posts);
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(posts.getPostid());
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() < 3) {
                                    postsList.remove(posts);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                }
                Collections.reverse(postsList);
                myFotosAdapter.notifyDataSetChanged();
            //    bar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private int poublerimage(final Posts posts) {
        final int[] i = new int[1];
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(posts.getPostid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           i[0] = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return i[0];

    }

}
