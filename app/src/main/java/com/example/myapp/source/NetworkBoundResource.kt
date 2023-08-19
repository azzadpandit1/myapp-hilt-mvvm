package com.example.myapp.source

import com.example.myapp.utils.Resource
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {

    /*
    //First step, fetch data from the local cache
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))
        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)*/

    val data = query().first()
    //If shouldFetch returns true,
    val resource = if (shouldFetch(data)) {

        //Dispatch a message to the UI that you're doing some background work
        emit(Resource.Loading(data))

        try {

            //make a networking call
            val resultType = fetch()

            //save it to the database
            saveFetchResult(resultType)

            //Now fetch data again from the database and Dispatch it to the UI
            query().map { Resource.Success(it) }

        } catch (throwable: Throwable) {

            //Dispatch any error emitted to the UI, plus data emmited from the Database
            query().map { Resource.Error(throwable, it) }

        }

        //If should fetch returned false
    } else {
        //Make a query to the database and Dispatch it to the UI.
        query().map { Resource.Success(it) }
    }

    //Emit the resource variable
    emitAll(resource)
}



