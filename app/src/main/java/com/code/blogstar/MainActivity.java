package com.code.blogstar;
        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;
        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    DatabaseReference dbReference;
    FirebaseDatabase fbdb;
    public static final String TAG="MAINLOLO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv= (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(false);
        fbReference = FirebaseDatabase.getInstance().getReference().child("BLOGDATA");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View view;
        public BlogViewHolder(View itemView)
        {
            super(itemView);
            view=itemView;
        }
        public void setTitle(String title)
        {
            Log.d( TAG , "  setTitle  :  "       +title);
            TextView tv_title = (TextView) view.findViewById(R.id.post_title) ;
            tv_title.setText(title);
        }
        public void setDescription(String desc)
        {
            Log.d( TAG , "  setDescription: " +desc);
            TextView tv_desc= (TextView) view.findViewById(R.id.post_text);
            tv_desc.setText(desc);
        }
        public void setImage(Context cntx,Blog blog,String image)
        {
            ImageView imView= (ImageView) view.findViewById(R.id.post_image);
            Picasso.with(cntx).load(image).fit().into(imView);

        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_settings) {
                // Log setting open event with category="ui", action="open", and label="settings"
                MyApp.tracker().send(new HitBuilders.EventBuilder("ui", "open")
                    .setLabel("settings")
                    .build());
                MyApp.number_of_blogs().set(new Limit(5,50)).setCurrent().build();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Blog,BlogViewHolder> fireAdapter=new FirebaseRecyclerAdapter<Blog, BlogViewHolder>
                (Blog.class,
                R.layout.blog_item,
                BlogViewHolder.class,
                fbReference
                )
        {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position)
            {
                viewHolder.setDescription(model.getDescription());
                viewHolder.setTitle(model.getTitle());
                viewHolder.setImage(getApplicationContext(),model,model.getImageUrl());
            }
        };
        rv.setAdapter(fireAdapter);
    }
}
