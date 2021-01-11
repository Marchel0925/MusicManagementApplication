package utils;

public enum RedirectEnums {
    TO_MAIN("/ui/main.fxml"),
    TO_ARTIST_TABLE("/ui/list_artists.fxml"),
    TO_MUSIC_TABLE("/ui/list_music.fxml"),
    TO_ADD_SONG_WINDOW("/ui/add_song.fxml"),
    TO_ADD_ARTIST_WINDOW("/ui/add_artist.fxml"),
    TO_AUTHOR_INFORMATION("/ui/author_information.fxml");

    private final String path;

    RedirectEnums(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
