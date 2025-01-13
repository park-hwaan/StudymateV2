package com.example.studymatetwo.error
import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class ResultCallAdapter(
    private val responseType: Type
): CallAdapter<Type, Call<Result<Type>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<Type>): Call<Result<Type>> = ResultCall(call)
}

private class ResultCall<T>(
    private val delegate: Call<T>
): Call<Result<T>> {
    override fun clone(): Call<Result<T>> = ResultCall(delegate.clone())

    override fun execute(): Response<Result<T>> = throw UnsupportedOperationException()

    override fun enqueue(callback: Callback<Result<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback.onResponse(this@ResultCall, Response.success(response.toResult()))
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                val failure = when(throwable) {
                    is IOException -> AppError.NetworkError
                    else -> AppError.UnexpectedError
                }

                callback.onResponse(this@ResultCall, Response.success(Result.failure(failure)))
            }

            private fun Response<T>.toResult(): Result<T> {
                val body = this.body()
                val errorBody = this.errorBody()?.string()
                if (this.isSuccessful) {
                    return if (body != null) {
                        Result.success(body)
                    } else {
                        Result.failure(AppError.UnexpectedError)
                    }
                }
                if (errorBody == null) {
                    return Result.failure(AppError.UnexpectedError)
                }
                try {
                    val errorResponse = fromJsonToErrorResponse(errorBody)

                    val httpError = getErrorByStatusCode(
                        statusCode = errorResponse.status.toInt(),
                        message = errorResponse.message
                    )

                    return Result.failure(httpError)
                } catch (e: Exception) {
                    return  Result.failure(AppError.UnexpectedError)
                }
            }

        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}