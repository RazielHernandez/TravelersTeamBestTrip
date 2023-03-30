package centennialcollege.ca.besttrip.repo.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TravelersService {

    var baseURL = "https://us-east-1.aws.data.mongodb-api.com/app/data-rauiy/endpoint/data/v1/"

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //API instance
    val retrofit = retrofitBuilder.create(TravelersRetrofitAPI::class.java)

}