package com.example.booksroomdemo.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.booksroomdemo.data.database.entities.Book
import com.example.booksroomdemo.databinding.ItemBookBinding

class BookViewHolder(val binding: ItemBookBinding) : ViewHolder(binding.root) {

    fun bind(book: Book) {
        binding.apply {
            model = book
            Glide.with(cover).load(book.cover).into(cover)
        }
        //binding.title.text = book.title
    }

}