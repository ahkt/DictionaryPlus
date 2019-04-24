package com.javadi.dictionary.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.javadi.dictionary.App;
import com.javadi.dictionary.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.myViewHolder> {

    Context mContext;
    List<String> words=new ArrayList<>();
    TextToSpeech t1;

    public FavoriteAdapter(Context context,List<String> words){
        this.words=words;
        this.mContext=context;
        t1 = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.favorite_view_holder,viewGroup,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder myViewHolder, int i) {
        myViewHolder.tvEnglishFavorite.setText(words.get(i));
        myViewHolder.imgPronunceFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.speak(myViewHolder.tvEnglishFavorite.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setMessage(App.dbHelper.translate(myViewHolder.tvEnglishFavorite.getText().toString() ));
                dialog=builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView tvEnglishFavorite;
        ImageView imgPronunceFavorite;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEnglishFavorite=(TextView)itemView.findViewById(R.id.tv_english_favorite);
            imgPronunceFavorite=(ImageView)itemView.findViewById(R.id.img_pronunce_favorite);
        }
    }
}
