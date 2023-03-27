package com.example.loginwithgg;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.loginwithgg.databinding.ActivityBookBinding;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String EXTRA_BOOK_ID = "bookId";
    ActivityBookBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
//        binding.favoriteBtn.setOnClickListener(view -> {
//            int bookId = intent.getIntExtra(EXTRA_BOOK_ID, -1);
//            Toast.makeText(this, String.valueOf(bookId), Toast.LENGTH_SHORT).show();
//        });

        if (intent != null) {
            int bookId = intent.getIntExtra(EXTRA_BOOK_ID, -1);
            if (bookId != -1) {
                Book incomingBook = Utils.getInstance().getBookById(bookId);
//                Toast.makeText(this, incomingBook.getName(), Toast.LENGTH_SHORT).show();
                if (incomingBook != null) {
                    setData(incomingBook);

                    handleAlreadyReadBook(incomingBook);
                    handleWantToReadBook(incomingBook);
                    handleCurrentlyReading(incomingBook);
                    handleFavoriteBook(incomingBook);
                }
            }
        }
    }

    private void handleAlreadyReadBook(Book incomingBook) {
        ArrayList<Book> alreadyReadBookList = Utils.getInstance().getAlreadyReadBooks();

        boolean existedInAlready = false;

        for (Book book : alreadyReadBookList) {
            if (book.getId() == incomingBook.getId()) {
                existedInAlready = true;
            }
        }

        if (existedInAlready){
            binding.alreadyReadBtn.setEnabled(false);
        } else {
            binding.alreadyReadBtn.setOnClickListener(view -> {
                if (Utils.getInstance().addBookToAlready(incomingBook)){
                    Toast.makeText(this, "Book added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, AlreadyReadBooksActivity.class));
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void handleWantToReadBook(Book incomingBook) {
        ArrayList<Book> wantToReadBookList = Utils.getWantToReadBooks();

        boolean existedInAlready = false;

        for (Book book : wantToReadBookList) {
            if (book.getId() == incomingBook.getId()) {
                existedInAlready = true;
            }
        }

        if (existedInAlready){
            binding.wantToReadBtn.setEnabled(false);
        } else {
            binding.wantToReadBtn.setOnClickListener(view -> {
                if (Utils.addBookToWantToRead(incomingBook)){
                    Toast.makeText(this, "Book added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, WantToReadActivity.class));
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void handleCurrentlyReading(Book incomingBook) {
        ArrayList<Book> currentlyReadingBookList = Utils.getCurrentlyReadingBooks();

        boolean existedInAlready = false;

        for (Book book : currentlyReadingBookList) {
            if (book.getId() == incomingBook.getId()) {
                existedInAlready = true;
            }
        }

        if (existedInAlready){
            binding.currentReadingBtn.setEnabled(false);
        } else {
            binding.currentReadingBtn.setOnClickListener(view -> {
                if (Utils.addBookToCurrentlyReading(incomingBook)){
                    Toast.makeText(this, "Book added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, CurrentlyReadingActivity.class));
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void handleFavoriteBook(Book incomingBook) {
        ArrayList<Book> favoriteBookList = Utils.getFavoriteBooks();

        boolean existedInAlready = false;

        for (Book book : favoriteBookList) {
            if (book.getId() == incomingBook.getId()) {
                existedInAlready = true;
            }
        }

        if (existedInAlready){
            binding.favoriteBtn.setEnabled(false);
        } else {
            binding.favoriteBtn.setOnClickListener(view -> {
                if (Utils.addBookToFavorite(incomingBook)){
                    Toast.makeText(this, "Book added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, FavoriteActivity.class));
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setData (Book book)
    {
        binding.NameOfBook.setText(book.getName());
        binding.authorOfBook.setText(book.getAuthor());
        binding.numberPageOfBook.setText(String.valueOf(book.getPages()));
        binding.longDesDetail.setText(book.getLongDescription());
        Glide.with(this)
                .asBitmap()
                .load(book.getImageUrl())
                .into(binding.imgOfBook);
    }
}