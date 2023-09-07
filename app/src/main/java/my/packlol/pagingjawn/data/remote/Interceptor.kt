package my.packlol.pagingjawn.data.remote

import android.content.ContentValues.TAG
import android.util.Log
import okhttp3.Call
import okhttp3.Connection
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

class MyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.also {
            println(chain.request())
        }
       var intercept =  chain.proceed(chain.request())
        Log.d("ERORRRRRRRRe", "intercept: ${intercept.body} ${intercept.headers}")
        println(intercept.body.toString())
        return intercept
    }

}