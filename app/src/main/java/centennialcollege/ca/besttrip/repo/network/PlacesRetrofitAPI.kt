package centennialcollege.ca.besttrip.repo.network
/**
 * MAPD711 Assignment 3
 * @Authors
 * Student Name: Oscar Miralles Fernandez
 * Student ID: 301250756
 * Student Name: Carlos Hernandez Galvez
 * Student ID: 301290263
 **/
import centennialcollege.ca.besttrip.datamodel.PlaceDetails
import centennialcollege.ca.besttrip.datamodel.PlaceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesRetrofitAPI {
    /**
     * GET near places. Connect with api server to get places near a location by category
     * @param location latitude and longitude of the city that the user is looking for
     * @param category Type of places (restaurants, museums, etc)
     * @return PlaceResponse. Include a list of places near the location for the category
     */
    @GET("nearbysearch/json")
    suspend fun getPlacesNear(
        @Query("location") location: String?,
        @Query("radius") radius: Int = 15000,
        @Query("type") category: String?,
        @Query("key") key: String = "AIzaSyDjTKoWhmal9SZXzoti5lpDJuJ7MP3TM_E"
    ): PlaceResponse

    /**
     * GET details about a place. Connect with api server to get place details
     * @param location latitude and longitude of the city that the user is looking for
     * @param category Type of places (restaurants, museums, etc)
     * @return PlaceResponse. Include a list of places near the location for the category
     */
    @GET("details/json")
    suspend fun getPlaceDetails(
        @Query("place_id") place_id: String?,
        @Query("key") key: String = "AIzaSyDjTKoWhmal9SZXzoti5lpDJuJ7MP3TM_E"
    ): PlaceDetails
}