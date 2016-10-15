CREATE TABLE 'favorite'
    (_id INTEGER PRIMARY KEY AUTOINCREMENT,
    mov_id INTEGER,
    title TEXT,
    overview TEXT,
    poster_id TEXT,
    genre TEXT,
    rating REAL,
    release_date TEXT,
    poster_image BLOB,
    grid_image BLOB);