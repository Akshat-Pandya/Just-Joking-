package com.example.just_joking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private JokeTemplate swipedJokeTemplate;
    private ImageView optionsMenu;
    private CardStackView cardStackView;
    private CardAdapter adapter;
    private static Direction lastSwipedDirection;
    private CardStackLayoutManager cardStackLayoutManager;
    private List<JokeTemplate> datalist;
    private   JsonApiRequest jsonApiRequest;
    private static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ImageView rightOverlay,leftOverlay;
    private ImageView like,dislike,refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        sharedPreferences = getSharedPreferences("LikedJokes", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        like=findViewById(R.id.like);
        refresh=findViewById(R.id.refresh);
        rightOverlay=findViewById(R.id.rightOverlay);
        leftOverlay=findViewById(R.id.leftOverlay);
        dislike=findViewById(R.id.dislike);
        optionsMenu=findViewById(R.id.mymenu);
        cardStackView=findViewById(R.id.cardStack);
        datalist = new ArrayList<>();
        jsonApiRequest = new JsonApiRequest(this);
        getData();
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=cardStackLayoutManager.getTopPosition();
                JokeTemplate jokeTemplate=datalist.get(position);
                saveJoke(jokeTemplate);
                Toast.makeText(MainActivity.this, "Liked", Toast.LENGTH_SHORT).show();
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardStackView.swipe();
            }
        });
        cardStackLayoutManager=new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                if (direction == Direction.Right) {
                    rightOverlay.setVisibility(View.VISIBLE);
                    leftOverlay.setVisibility(View.GONE);
                } else if (direction == Direction.Left) {
                    leftOverlay.setVisibility(View.VISIBLE);
                    rightOverlay.setVisibility(View.GONE);
                } else {
                    // If the card is dragged vertically or the drag is canceled, hide both overlays
                    rightOverlay.setVisibility(View.GONE);
                    leftOverlay.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCardSwiped(Direction direction) {

                rightOverlay.setVisibility(View.INVISIBLE);
                leftOverlay.setVisibility(View.INVISIBLE);

                if(direction.equals(Direction.Right))
                {
                    int position=cardStackLayoutManager.getTopPosition();
                    Log.d("LIKED",swipedJokeTemplate.getJoke());

                    saveJoke(swipedJokeTemplate);



                }
                if(direction.equals(Direction.Left))
                {
                    lastSwipedDirection=Direction.Left;
                    Log.d("DISLIKED",swipedJokeTemplate.getJoke());
                }
            }

            @Override
            public void onCardRewound() {

            }

            @Override
            public void onCardCanceled() {
                rightOverlay.setVisibility(View.INVISIBLE);
                leftOverlay.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCardAppeared(View view, int position) {

            }

            @Override
            public void onCardDisappeared(View view, int position) {
                 swipedJokeTemplate=datalist.get(position);
            }
        });
        cardStackLayoutManager.setMaxDegree(20.0f);
        cardStackLayoutManager.setSwipeThreshold(0.4f);
        cardStackLayoutManager.setCanScrollHorizontal(true);
        cardStackLayoutManager.setCanScrollVertical(true);
        cardStackLayoutManager.setDirections(Direction.FREEDOM);
        cardStackView.setLayoutManager(cardStackLayoutManager);
        adapter=new CardAdapter(this,datalist);
        cardStackView.setAdapter(adapter);

        optionsMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });
    }

    private String generateUniqueKey(JokeTemplate swipedJokeTemplate) {
        return Integer.toHexString((swipedJokeTemplate.getType() + swipedJokeTemplate.getJoke()).hashCode());
    }

    private void showMenu(View view)
    {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
        popupMenu.inflate(R.menu.options_menu);

        // Set an item click listener
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.likedJokes:
                        printAllContents(MainActivity.this);
                        Intent intent=new Intent(getApplicationContext(),LikedJokes.class);
                        startActivity(intent);
                        return true;
                    case R.id.AboutUs:
                        Intent intent1 = new Intent(getApplicationContext(),AboutUs.class);
                        startActivity(intent1);
                        return true;
                    // Add cases for other menu items if needed
                    default:
                        return false;
                }
            }
        });

        // Show the PopupMenu
        popupMenu.show();

    }

    public static void printAllContents(Context context) {
        LikedJokes.likedJokeList.clear();
        // get all data from shrd. pref. file named likedjokes
        Map<String, ?> allContents = context.getSharedPreferences("LikedJokes",Context.MODE_PRIVATE).getAll();
        for (Map.Entry<String, ?> entry : allContents.entrySet()) {
            String key = entry.getKey();
            if(key.endsWith("type"))
            {
                String jokelikedkey=key.replace("type","text");
                String jokeliked=sharedPreferences.getString(jokelikedkey,"BINOD");
                Object joketype = entry.getValue();
                Log.d("SUCCESS","type = "+joketype+"\tjoke = "+jokeliked);
                LikedJokes.likedJokeList.add(new JokeTemplate(jokeliked,joketype.toString()));
            }
        }
    }
    private void saveJoke(JokeTemplate swipedJokeTemplate) {
        String unique_key="liked_joke"+generateUniqueKey(swipedJokeTemplate);
        editor.putString(unique_key + "_type", swipedJokeTemplate.getType());
        editor.putString(unique_key + "_text", swipedJokeTemplate.getJoke());

        // Apply changes
        editor.apply();
        Log.d("SAVED","Changes saved");
    }

    private void showData() { // for testing purposes
        if(datalist.size()!=0)
        {
            for(JokeTemplate temp : datalist)
                Log.d("JOKES" , temp.getJoke());
        }
    }
    private void getData() {
       RequestQueue queue=Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "https://v2.jokeapi.dev/joke/Any?type=single&amount=10", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("jokes");
                    if(jsonArray!=null)
                    {
                        datalist.clear();
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            String joke=jsonObject.getString("joke");
                            String category=jsonObject.getString("category");
                            JokeTemplate jokeTemplate=new JokeTemplate(joke,category);
                            datalist.add(jokeTemplate);
                        }
                        Log.d("SUCCESS","Data added ");
                        showData();
                        adapter.setDataList(datalist);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Log.d("Failed" , "No data received");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }
}