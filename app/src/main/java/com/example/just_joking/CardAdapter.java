package com.example.just_joking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    Context context;
    List<JokeTemplate> datalist;

    public CardAdapter(Context context, List<JokeTemplate> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.jokes_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.jokeText.setText(datalist.get(position).getJoke());
        holder.jokeType.setText(datalist.get(position).getType());
        int randomNumber = generateRandomNumber(1, 31);
        switch (randomNumber)
        {
            case 1 :
                holder.jokeImage.setImageResource(R.drawable.joke1);
                break;
            case 2 :
                holder.jokeImage.setImageResource(R.drawable.joke2);
                break;
            case 3 :
                holder.jokeImage.setImageResource(R.drawable.joke3);
                break;
            case 4 :
                holder.jokeImage.setImageResource(R.drawable.joke4);
                break;
            case 5 :
                holder.jokeImage.setImageResource(R.drawable.joke5);
                break;
            case 6 :
                holder.jokeImage.setImageResource(R.drawable.joke6);
                break;
            case 7 :
                holder.jokeImage.setImageResource(R.drawable.joke7);
                break;
            case 8 :
                holder.jokeImage.setImageResource(R.drawable.joke8);
                break;
            case 9 :
                holder.jokeImage.setImageResource(R.drawable.joke9);
                break;
            case 10 :
                holder.jokeImage.setImageResource(R.drawable.joke11);
                break;
            case 11:
                holder.jokeImage.setImageResource(R.drawable.joke12);
                break;
            case 12 :
                holder.jokeImage.setImageResource(R.drawable.joke13);
                break;
            case 13 :
                holder.jokeImage.setImageResource(R.drawable.joke14);
                break;
            case 14 :
                holder.jokeImage.setImageResource(R.drawable.joke15);
                break;
            case 15 :
                holder.jokeImage.setImageResource(R.drawable.joke16);
                break;
            case 16 :
                holder.jokeImage.setImageResource(R.drawable.joke1);
                break;
            case 17 :
                holder.jokeImage.setImageResource(R.drawable.joke17);
                break;
            case 18:
                holder.jokeImage.setImageResource(R.drawable.joke18);
                break;
            case 19 :
                holder.jokeImage.setImageResource(R.drawable.joke19);
                break;
            case 20 :
                holder.jokeImage.setImageResource(R.drawable.joke20);
                break;
            case 21 :
                holder.jokeImage.setImageResource(R.drawable.joke21);
                break;
            case 22 :
                holder.jokeImage.setImageResource(R.drawable.joke22);
                break;
            case 23 :
                holder.jokeImage.setImageResource(R.drawable.joke23);
                break;
            case 24:
                holder.jokeImage.setImageResource(R.drawable.joke24);
                break;
            case 25 :
                holder.jokeImage.setImageResource(R.drawable.joke25);
                break;
            case 26 :
                holder.jokeImage.setImageResource(R.drawable.joke26);
                break;
            case 27 :
                holder.jokeImage.setImageResource(R.drawable.joke27);
                break;
            case 28 :
                holder.jokeImage.setImageResource(R.drawable.joke28);
                break;
            case 29 :
                holder.jokeImage.setImageResource(R.drawable.joke29);
                break;
            case 30 :
                holder.jokeImage.setImageResource(R.drawable.joke30);
                break;
            case 31 :
                holder.jokeImage.setImageResource(R.drawable.joke31);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    private static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public void setDataList(List<JokeTemplate> datalist) {
        this.datalist.addAll(datalist);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView jokeImage;
        TextView jokeText,jokeType;
        ImageView shareButton;
        RelativeLayout cardLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // view binding
            jokeImage=itemView.findViewById(R.id.jokeimage);
            jokeText=itemView.findViewById(R.id.jokeTextview);
            jokeType=itemView.findViewById(R.id.joke_typeTextView);
            shareButton=itemView.findViewById(R.id.shareButton);
            cardLayout=itemView.findViewById(R.id.cardLayout);
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean resultSharing= shareCard(cardLayout);
                    }

            });
            shareButton.setVisibility(View.VISIBLE);
        }
        private boolean shareCard(RelativeLayout cardLayout) {
            shareButton.setVisibility(View.INVISIBLE);
            // Capture the screenshot
            Bitmap screenshot = captureRelativeLayoutScreen(cardLayout);

            // Create an intent to share image
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");

            // Save the screenshot to a temporary file
            String imagePath = MediaStore.Images.Media.insertImage(context.getContentResolver(), screenshot, "Screenshot", null);
            Uri imageUri = Uri.parse(imagePath);

            // Share the screenshot
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Start the chooser for sharing
            context.startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        }

        private Bitmap captureRelativeLayoutScreen(RelativeLayout cardLayout) {
            cardLayout.setDrawingCacheEnabled(true);
            Bitmap screenshot = Bitmap.createBitmap(cardLayout.getDrawingCache());
            cardLayout.setDrawingCacheEnabled(false);
            shareButton.setVisibility(View.VISIBLE);
            return screenshot;
        }
    }


}
