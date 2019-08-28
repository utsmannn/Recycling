## Recycling is Easy, No Adapter !
[ ![Download](https://api.bintray.com/packages/kucingapes/utsman/com.utsman.recycling-paged/images/download.svg) ](https://bintray.com/kucingapes/utsman/com.utsman.recycling-paged/_latestVersion)
<br>
Make easy and faster RecyclerView without adapter, design for kotlin. <br>
Support normal and paged adapter RecyclerView base on Android Paging Library

![](https://i.ibb.co/DkQ1Lmn/carbon.png)


### Download
***For standard adapter***
```gradle
implementation 'com.utsman.recycling:recycling:0.1.3'
```

***For paged adapter***
```gradle
implementation 'com.utsman.recycling-paged:recycling:0.1.3'
```

### Setup

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

| Setup parameter  | Desc |
|---|---|
| ```bind { }``` | setup your holder |
| ```adapter```  | get adapter |
| ```context``` | get context |
| ```list``` | get current list |
| ```setLayoutManager(layout_manager)``` | setup layout manager |
| ```setDivider(divider)``` | add divider |
| ```submitList(list)``` | submit your list |
| ```submitNetwork(networkState)``` | submit network state |
| ```fixGridSpan(column_size)``` | fix grid span for grid layout when network state enabled |
| ```onPagingListener(layoutManager)``` | paging helper for standard recycler view |

### Bind
In lamba of setup, use ```bind``` to instead viewholder 

```kotlin
recyclerView.setupAdapterPaged<Item>(R.layout.item_view) { adapter, context, list ->
    
    // setup your holder
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


### Layout Manager
Default layout manager is ```LinearLayoutManager```, you can set layout manager with
```kotlin
recyclerView.setupAdapterPaged<Item>(R.layout.item_view) { adapter, context, list ->
    
    // setup your layout manager
    setLayoutManager(GridLayoutManager(context))

}
```

### Submit List
You can set list / submit list inside lamba with ```submitList(list)```
```kotlin
recyclerView.setupAdapterPaged<Item>(R.layout.item_view) { adapter, context, list ->
    ...
    
    submitList(yourList)
}
```

### Network State Loader
This library support for network loader, use paged setup is recommended
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

#### Create LoaderIdentifierId for identifier view and id
```kotlin
val identifierId = LoaderIdentifierId.Builder()
    .setLoaderRes(R.layout.item_loader)
    .setIdProgressLoader(R.id.progress_circular)
    .setIdTextViewError(R.id.error_text_view)
    .build()
```

#### Add identifier to your setup
<pre>
recyclerView.setupAdapterPaged<Item>(R.layout.item_view, <b>identifierId</b>) { adapter, context, list ->
    ...

}
</pre>

#### Fix progressBar position for grid layout
Use ```fixGridSpan(column_size)```
```kotlin
recyclerView.setupAdapterPaged<Item>(R.layout.item_view, identifierId) { adapter, context, list ->

    ...
    
    val layoutManager = GridLayoutManager(this@MainActivity, 2)
    setLayoutManager(layoutManager)
    fixGridSpan(2)

}
```
#### For standard setup (not recommended)
Use ```onPagingListener(layoutManager)``` for paging recycler
```kotlin
recyclerView.setupAdapter<Item>(R.layout.item_view, identifierId) { adapter, context, list ->

    ...
   
    onPagingListener(layoutManager) { page, itemCount ->
        // setup data with page + 1
    }

}
```

#### Sample
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
