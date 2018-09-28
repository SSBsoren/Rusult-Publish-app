package com.soren.sagen.driemsfirebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;


    //Getting reference to Firebase Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = database.getReference();

    private static final String userId = "160122";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );



        //initializing our RecyclerView
        mRecyclerView=(RecyclerView)findViewById( R.id.details_recycler_view );


        if (mRecyclerView !=null)
        {
            //to enable optimization of recycleview
            mRecyclerView.setHasFixedSize( true );


        }

        //using Staggered grid in reyclerView
        mLayoutManager=new StaggeredGridLayoutManager( 1,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager( mLayoutManager );

        FirebaseRecyclerAdapter<Details_card,CardViewHolder>adapter=new FirebaseRecyclerAdapter<Details_card,CardViewHolder>(


                Details_card.class,
                R.layout.card_item,
                CardViewHolder.class,


                //referencing the node where we want the database to store the data from our Object
                mDatabaseReference.child("users").child(userId).child("driems").getRef()
        ) {
            @Override
            protected void populateViewHolder(CardViewHolder viewHolder, Details_card model, final int position) {

                viewHolder.tvCardTitle.setText(model.getTitle());
                viewHolder.descTv.setText(model.getDesc());

                viewHolder.tvCardTitle.setOnClickListener( new View.OnClickListener() {
                    @Override

                    public void onClick(View view) {

                     switch (position){
                         case 0:
                             Intent intnt=new Intent( MainActivity.this,ResultsActivity.class );
                             startActivity( intnt );
                             break;
                         case 1:
                             Intent intent=new Intent( MainActivity.this,EnterRegIdActivty.class );
                             startActivity( intent );
                             break;
                         case 2:
                             Toast.makeText( getApplicationContext()," Not Available",Toast.LENGTH_SHORT ).show();
                             break;
                     }

                    }
                } );
            }
        };
        mRecyclerView.setAdapter( adapter );
    }

    //view holder for firebase UI
    public static class CardViewHolder extends RecyclerView.ViewHolder{

        TextView tvCardTitle;
        TextView descTv;
        public CardViewHolder(View itemView) {
            super( itemView );
            tvCardTitle=itemView.findViewById( R.id.title_text );
            descTv=itemView.findViewById( R.id.det_desc );
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}
