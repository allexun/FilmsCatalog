package ru.cadmean.filmscatalog

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL

class FilmsViewModel : ViewModel() {
    private val apiKey = "6ccd72a2a8fc239b13f209408fc31c33"

    private val gson = Gson()

    val films: MutableLiveData<List<Film>> = MutableLiveData()
    val images: HashMap<String, Bitmap> = HashMap()
    val downloadedImage: MutableLiveData<Film> = MutableLiveData()

    private val tag = "FilmsViewModel"

    fun loadFilms() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url =
                    URL("https://api.themoviedb.org/3/discover/movie?api_key=$apiKey&language=ru")
                val text = url.readText()
                Log.d("Films", text)

                val response = gson.fromJson(text, FilmsResponse::class.java)
                val films = response.results ?: return@launch
                this@FilmsViewModel.films.postValue(films)

                for (film in films) {
                    if (film.posterPath.isEmpty() || images[film.posterPath] != null)
                        continue

                    val url = URL("https://image.tmdb.org/t/p/original${film.posterPath}")
                    val imageData = url.readBytes()
                    val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
                    images[film.posterPath] = bitmap
                    downloadedImage.postValue(film)
                    Log.d(tag, "Downloaded image: ${film.posterPath}")
                }
            } catch (ex: IOException) {
                Log.d(tag, "Failed to load films: $ex")
            }
        }
    }
}