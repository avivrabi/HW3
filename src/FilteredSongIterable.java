public interface FilteredSongIterable<Song> extends Iterable<Song> {
    public void filterArtist(String artist);
    public void filterGenre(Enum genre);
    public void filterDuration(int duration);

}
