# Recycling is Easy, No Adapter !
[ ![Download](https://api.bintray.com/packages/kucingapes/utsman/com.utsman.recycling-paged/images/download.svg) ](https://bintray.com/kucingapes/utsman/com.utsman.recycling-paged/_latestVersion)

<br>
Make easy and faster RecyclerView without adapter, design for kotlin. <br>
Support normal and paged adapter RecyclerView base on Android Paging Library

![](https://i.ibb.co/DkQ1Lmn/carbon.png)

## Download for AndroidX
***For standard adapter***
```gradle
implementation "com.utsman.recycling:recycling:${latest}"
```

***For paged adapter***
```gradle
implementation "com.utsman.recycling-paged:recycling:${latest}"
```

## Download for Android
***For standard adapter***
```gradle
implementation "com.utsman.recycling-android:recycling:${latest}"
```

***For paged adapter***
```gradle
implementation "com.utsman.recycling-paged-android:recycling:${latest}"
```

## Setup

***For standard*** use ```.setupAdapter<>```
```kotlin
recyclerView.setupAdapter<Item>(R.layout.item_view) { adapter, context, list ->
    ...
}
```

***For paging*** use ```.setupAdapterPaged<>```
```kotlin
recyclerView.setupAdapterPaged<Item>(R.layout.item_view) { adapter, context, list ->
    ...
}
```

#### Setup Parameter

| Setup parameter  | Desc |
|---|---|
| ```bind { }``` | recycling your holder |
| ```adapter```  | get adapter |
| ```context``` | get context |
| ```list``` | get current list |
| ```setLayoutManager(layout_manager)``` | recycling layout manager |
| ```setDivider(divider)``` | add divider |
| ```submitList(list)``` | submit your list |
| ```submitItem(item)``` | add item in list (**only for normal adapter**) |
| ```submitNetwork(networkState)``` | submit network state |
| ```fixGridSpan(column_size)``` | fix grid span for grid layout when network state enabled |
| ```onPagingListener(layoutManager)``` | paging helper (**only for normal adapter**) |
| ```addLoader(layout) {  }``` | add loader |


## Bind
In lamba of recycling, use ```bind``` to instead viewholder 

```kotlin
recyclerView.setupAdapterPaged<Item>(R.layout.item_view) { adapter, context, list ->
    
    // recycling your holder
    bind { itemView, position, item ->
        // bind view
        itemView.img_view.load(item?.url)
        itemView.setOnClickListener {
            toast("Click on $position")
        }
    }

}
```

| Bind parameter  | Desc |
|---|---|
| ```itemView```  | itemView |
| ```item``` | item |
| ```position``` | item position |


## Layout Manager
Default layout manager is ```LinearLayoutManager```, you can set layout manager with
```kotlin
recyclerView.setupAdapterPaged<Item>(R.layout.item_view) { adapter, context, list ->
    
    // recycling your layout manager
    setLayoutManager(GridLayoutManager(context))

}
```

## Submit List
You can set list / submit list inside lamba with ```submitList(list)```
```kotlin
recyclerView.setupAdapterPaged<Item>(R.layout.item_view) { adapter, context, list ->
    ...
    
    submitList(yourList)
}
```

## Network State Loader (Optional)
This library support for network loader, use paged recycling is recommended
#### Create your loader layout
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:gravity="center"
    android:orientation="vertical">

    <ProgressBar
        android:id="@+id/progress_circular"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="30dp"
        tools:ignore="UnusedAttribute"
        android:indeterminateTint="@color/colorPrimary"
        android:indeterminateTintMode="src_atop" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Error"
        android:textColor="@android:color/holo_red_light"
        android:id="@+id/error_text_view"
        android:layout_margin="12dp"
        tools:ignore="HardcodedText" />

</LinearLayout>
```

#### Create loader
For create loader, use ```addLoader(layout)``` bind your id in layout loader
```kotlin
addLoader(R.layout.item_loader) {
    idLoader = R.id.progress_circular
    idTextError = R.id.error_text_view
}
```

#### Submit your NetworkState
Add network state in viewmodel or etc for user to see data process
```kotlin
// for error state
networkState.postValue(NetworkState.error("error network: ${t.message}"))

// for loading state
networkState.postValue(NetworkState.LOADING)

// for loaded state
networkState.postValue(NetworkState.LOADED)

// and submit in your recycling
submitNetworkState(networkState)
```

#### Fix progressBar position for grid layout
Use ```fixGridSpan(column_size)```
```kotlin
recyclerView.setupAdapterPaged<Item>(R.layout.item_view) { adapter, context, list ->

    ...
    
    val layoutManager = GridLayoutManager(context, 2)
    setLayoutManager(layoutManager)
    fixGridSpan(2)

}
```
#### For standard recycling (not recommended)
Use ```onPagingListener(layoutManager)``` for paging recycler
```kotlin
recyclerView.setupAdapter<Item>(R.layout.item_view) { adapter, context, list ->

    ...
   
    onPagingListener(layoutManager) { page, itemCount ->
        // recycling data with page + 1
    }

}
```

## Sample Code

#### Super Simple Sample
Just list of string
```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listData = listOf("satu", "dua", "tiga", "empat")

        main_recycler_view.setupAdapter<String>(R.layout.simple_item_view) { adapter, context, list ->
            bind { itemView, position, item ->
                itemView.name_item.text = item
            }

            submitList(listData)
        }
    }
}
```

#### Advanced Sample with API, pattern and NetworkState
For this sample code, I using Pexel Api for get photos and viewmodel pattern
```kotlin
// View Model
class SampleViewModel : ViewModel() {

    private val instance = RetrofitInstance.create()
    private val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    // get list
    fun getCuratedPhoto(perPage: Int, page: Int): LiveData<List<Pexel>?> {
        val newList: MutableLiveData<List<Pexel>?> = MutableLiveData()

        // network state is loading
        networkState.postValue(NetworkState.LOADING)

        // call api
        instance.getCuratedPhoto(perPage, page)
            .enqueue(object : Callback<Responses> {
                override fun onFailure(call: Call<Responses>, t: Throwable) {

                    // network state error with error message
                    networkState.postValue(NetworkState.error("error network: ${t.message}"))
                }

                override fun onResponse(call: Call<Responses>, response: Response<Responses>) {
                    val listResponse = response.body()?.photos
                    newList.postValue(listResponse)

                    // network state loaded
                    networkState.postValue(NetworkState.LOADED)
                }

            })

        return newList
    }

    // get network state
    fun getNetworkState(): LiveData<NetworkState> = networkState

}

// Activity
class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this)[PexelViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setup recycling
        main_recycler_view.setupAdapter<Pexel>(R.layout.item_view) { adapter, context, list ->

            // bind is viewholder function
            bind { itemView, position, item ->

                itemView.img_view.load(item?.src?.small)
                itemView.setOnClickListener {
                    Toast.makeText(context, "wee ${adapter.itemCount} - ${list.size} - $position", Toast.LENGTH_SHORT).show()
                }
            }

            // add loader state
            addLoader(R.layout.item_loader) {
                idLoader = R.id.progress_circular
                idTextError = R.id.error_text_view
            }

            // using grid layout manager
            val layoutManager = GridLayoutManager(this@MainActivity, 2)
            setLayoutManager(layoutManager)
            
            // for grid layout manager, loader by default is ugly, to fix use fixGridSpan
            fixGridSpan(2)

            // call function setupData with page 1
            setupData(this, 1)

            // use paging listener for endless recycler view and loaded data
            onPagingListener(layoutManager) { page, itemCount ->
            
                // call function setup data with page +1
                setupData(this@setupAdapter, page+1)
            }
        }
    }

    // function for setup data
    private fun setupData(recycling: Recycling<Pexel>, page: Int) {
        viewModel.getCuratedPhoto(20, page).observe(this, Observer {
            
            // submit list from viewmodel into recycling
            recycling.submitList(it)
        })

        viewModel.getNetworkState().observe(this, Observer {
        
            // submit network state from viewmodel into recycling
            recycling.submitNetworkState(it)
        })
    }
}

```

#### Sample Projects
[Pexel app](https://github.com/utsmannn/Recycling/tree/master/app/src/main/java/com/utsman/recycling/sample) <br>
[Pexel app paging](https://github.com/utsmannn/Recycling/tree/master/apppaged/src/main/java/com/utsman/recycling/samplepaged)

---
```

Copyright 2019 Muhammad Utsman

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
