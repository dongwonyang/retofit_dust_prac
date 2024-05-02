package com.example.retrofit_dust_prac

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NetWorkInterface {
    @GET("getCtprvnRltmMesureDnsty")
    suspend fun getDust(@QueryMap param: HashMap<String, String>): Dust
//    suspend fun getDust(
//        @Query("serviceKey") serviceKey: String,
//        @Query("returnType") returnType: String,
//        @Query("numOfRows") numOfRows: String,
//        @Query("pageNo") pageNo: String,
//        @Query("sidoName") sidoName:String,
//        @Query("ver") ver:String
//    ): Dust
}