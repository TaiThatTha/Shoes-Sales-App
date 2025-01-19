import com.example.shopgiay.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://76d9-42-114-220-160.ngrok-free.app/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Sử dụng Gson cho JSON
            .build()
            .create(ApiService::class.java)
    }
}

