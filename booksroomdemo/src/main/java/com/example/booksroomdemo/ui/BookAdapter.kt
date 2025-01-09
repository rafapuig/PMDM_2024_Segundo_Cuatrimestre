package com.example.booksroomdemo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booksroomdemo.data.database.entities.Book
import com.example.booksroomdemo.databinding.ItemBookBinding

class BookAdapter : RecyclerView.Adapter<BookViewHolder>() {

    private var _books: List<Book> = emptyList()

    var books: List<Book>
        set(value) {
            _books = value
            notifyDataSetChanged()
        }
        get() = _books



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBookBinding.inflate(layoutInflater, parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
        //holder.binding.title.text = book.title
        //holder.binding.model = book
        //Glide.with(holder.binding.cover).load(book.cover).into(holder.binding.cover)
    }

    override fun getItemCount(): Int = books.size
}