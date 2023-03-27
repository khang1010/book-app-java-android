package com.example.loginwithgg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookRecViewAdapter extends RecyclerView.Adapter<BookRecViewAdapter.ViewHolder> {
    private static final String TAG = "BookRecViewAdapter";
    private ArrayList<Book> books = new ArrayList<>();
    private Context context;
    private String parentActivity;

    public BookRecViewAdapter(Context context, String parentActivity) {
        this.context = context;
        this.parentActivity = parentActivity;
    }

    public BookRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtName.setText(books.get(position).getName());
        Glide.with(context)
                .asBitmap()
                .load(books.get(position).getImageUrl())
                .into(holder.imageView);
        holder.parentCard.setOnClickListener(view -> {
//            Toast.makeText(context, books.get(position).getId() + " Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, BookActivity.class);
            intent.putExtra(BookActivity.EXTRA_BOOK_ID, books.get(position).getId());
            context.startActivity(intent);
        });

        holder.txtDes.setText(books.get(position).getShortDescription());
        holder.txtAuthor.setText(books.get(position).getAuthor());

        if (books.get(position).getExpanded()){
            TransitionManager.beginDelayedTransition(holder.parentCard);
            holder.expanded.setVisibility(View.VISIBLE);
            holder.down.setVisibility(View.GONE);

            if (parentActivity.equals("allBooks")){
                holder.txtDelete.setVisibility(View.GONE);
            } else if (parentActivity.equals("alreadyReadBooks")){
                holder.txtDelete.setVisibility(View.VISIBLE);
                holder.txtDelete.setOnClickListener(view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to delete "+ books.get(position).getName()+"?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (Utils.getInstance().removeAlreadyReadBook(books.get(position))){
                                Toast.makeText(context, "Book removed", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.create().show();
                });
            } else if (parentActivity.equals("wantToReadBooks")){
                holder.txtDelete.setVisibility(View.VISIBLE);
                holder.txtDelete.setOnClickListener(view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to delete "+ books.get(position).getName()+"?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (Utils.getInstance().removeWantToReadBook(books.get(position))){
                                Toast.makeText(context, "Book removed", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.create().show();
                });
            } else if (parentActivity.equals("currentlyReadingBooks")){
                holder.txtDelete.setVisibility(View.VISIBLE);
                holder.txtDelete.setOnClickListener(view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to delete "+ books.get(position).getName()+"?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (Utils.getInstance().removeCurrentlyReadingBook(books.get(position))){
                                Toast.makeText(context, "Book removed", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.create().show();
                });
            } else {
                holder.txtDelete.setVisibility(View.VISIBLE);
                holder.txtDelete.setOnClickListener(view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to delete "+ books.get(position).getName()+"?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (Utils.getInstance().removeFavoriteBook(books.get(position))){
                                Toast.makeText(context, "Book removed", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.create().show();
                });
            }
        } else {
            TransitionManager.beginDelayedTransition(holder.parentCard);
            holder.expanded.setVisibility(View.GONE);
            holder.down.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView parentCard;
        private ImageView imageView;
        private TextView txtName;
        private ImageView down, up;
        private RelativeLayout expanded;
        private TextView txtAuthor, txtDes;
        private TextView txtDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentCard = itemView.findViewById(R.id.parent);
            imageView = itemView.findViewById(R.id.imgBook);
            txtName = itemView.findViewById(R.id.txtBook);

            down = itemView.findViewById(R.id.downArrowImg);
            up = itemView.findViewById(R.id.upArrowImg);
            expanded = itemView.findViewById(R.id.expandedRelLayout);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtDes = itemView.findViewById(R.id.shortDesText);
            txtDelete = itemView.findViewById(R.id.deleteBtn);

            down.setOnClickListener(view -> {
                Book book = books.get(getAdapterPosition());
                book.setExpanded(!book.getExpanded());
                notifyItemChanged(getAdapterPosition());
            });

            up.setOnClickListener(view -> {
                Book book = books.get(getAdapterPosition());
                book.setExpanded(!book.getExpanded());
                notifyItemChanged(getAdapterPosition());
            });
        }
    }
}
