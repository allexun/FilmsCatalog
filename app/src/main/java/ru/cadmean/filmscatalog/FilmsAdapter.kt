package ru.cadmean.filmscatalog

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FilmsAdapter(
        private val images: HashMap<String, Bitmap>
    ) : RecyclerView.Adapter<FilmsAdapter.FilmViewHolder>() {

    var films = emptyList<Film>()

    private lateinit var recyclerView: RecyclerView

    class FilmViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.film_title)
        val descriptionTextView: TextView = view.findViewById(R.id.film_description)
        val imageView: ImageView = view.findViewById(R.id.film_image)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val filmItemView = LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
        return FilmViewHolder(filmItemView)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = films[position]
        holder.titleTextView.text = film.title
        holder.descriptionTextView.text = film.overview
        val image = images[film.posterPath] ?: return
        holder.imageView.setImageBitmap(image)
    }

    override fun getItemCount(): Int {
        return films.size
    }
}