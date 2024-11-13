import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class RequestLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        // So'rovni body bilan chiqarish
        if (request.body != null) {
            val buffer = okio.Buffer()
            request.body?.writeTo(buffer)
            val body = buffer.readUtf8()
            println("Request Body: $body") // Bu yerda bodyni chiqarish
        }

        return chain.proceed(request)
    }
}
