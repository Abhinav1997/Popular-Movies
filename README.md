# Popular Movies

![Screenshot](http://abhinavjhanwar.com/ss/popular_movies/s1/ss_main.png)

Get details about new movies sorted as most popular or top rated.<br />
Project created for Udacity's Android Nanodegree program.<br />
Get your own API from [themoviedb](https://www.themoviedb.org/) and add it to ```app/build.gradle```
```
    buildTypes.each {
        it.buildConfigField 'String', 'MOVIE_DB_API_KEY', "\"YOUR_API_KEY\""
    }
```

Replace YOUR_API_KEY with your own API key.<br />

# Libraries used
[Butterknife](https://github.com/JakeWharton/butterknife)<br />
[Retrofit](https://github.com/square/retrofit)<br />
[Picasso](https://github.com/square/picasso)<br />
[PicassoPalette](https://github.com/florent37/PicassoPalette)<br />
[ExpandableTextView] (https://github.com/Manabu-GT/ExpandableTextView) <br />

# Third-party code
Code snippets used from(credited in code as well) : <br />
[Network Check](http://stackoverflow.com/a/6493572) <br />
[GridAutofitLayoutManager](http://stackoverflow.com/a/30256880) <br />
[Recyclerview JSON parsing](https://www.learn2crack.com/2016/02/recyclerview-json-parsing.html) <br />