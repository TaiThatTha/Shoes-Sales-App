import com.example.shopgiay.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://7b95-2405-4803-c77d-cce0-7dab-2c37-1849-e761.ngrok-free.app"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Sử dụng Gson cho JSON
            .build()
            .create(ApiService::class.java)
    }
}

