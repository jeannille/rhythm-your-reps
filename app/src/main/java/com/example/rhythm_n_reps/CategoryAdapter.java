//package com.example.rhythm_n_reps;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Adapter;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.RCategoryViewHolder>{
//
//    ArrayList<RDataCategories> courseDataList;
//    Context mcontext;
//
//    public CategoryAdapter(ArrayList<RDataCategories> recyclerDataArrayList, Context mcontext){
//        this.courseDataList = recyclerDataArrayList;
//        this.mcontext = mcontext;
//    }
//
//
//    //implement and override RView Adapter's methods
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        //inflate layout for card
//        View itemVew = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_choose_category, parent, false);
////        return new RecyclerViewAdapter.RecyclerViewHolder(itemVew);
//
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        //set data to layout's views (spinner,
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    public class RCategoryViewHolder{
//
//        private TextView catName;
//        private ImageView catIcon;
//        private CardView cardView;
//
//
////        private EditText mURLEditText;
////        private TextView mTitleTextView;
//
//
//        public RCategoryViewHolder(@NonNull View itemView) {
//            super(itemView);
////            catName = (TextView) itemView.findViewById(R.id.cat_name);
////            catIcon = (ImageView) itemView.findViewById(R.id.cat_icon);
////            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
//
//        }
//
//
//    }
//
//
//    }
//}
