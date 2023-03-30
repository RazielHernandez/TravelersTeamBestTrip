package centennialcollege.ca.besttrip.repo.network

import centennialcollege.ca.besttrip.datamodel.TravelerUser
import centennialcollege.ca.besttrip.datamodel.TravelerUserList
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

object RetrofitConstants {
    const val apiKey = "apiKey: l4EgHRndn5uebkLmdKO21YhsvtQ9ueb5Ws6RYQOHD15CAFyUERIcWZKuv0wLMyXx"
    const val content_type = "Content-Type: application/json"
}

interface TravelersRetrofitAPI {

    companion object {
        const val apiKey = "apiKey: l4EgHRndn5uebkLmdKO21YhsvtQ9ueb5Ws6RYQOHD15CAFyUERIcWZKuv0wLMyXx"
        const val content_type = "Content-Type: application/json"
    }
    /**
     * GET list of users
     * @param cluster cluster in mongodb
     * @param database database
     * @param collection collection
     * @return TravelersUserList. Include a list of users in database
     */
    @Headers(RetrofitConstants.apiKey,RetrofitConstants.content_type)
    @POST("action/find")
    suspend fun getAllUsers(
        @Query("dataSource") cluster: String = "Cluster0",
        @Query("database") database: String = "sample_travelers",
        @Query("collection") collection: String = "travelers_users",
    ): TravelerUserList
}