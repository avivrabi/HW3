import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Playlist extends LinkedList<Song> implements Cloneable, Iterable<Song>, FilteredSongIterable<Song>, OrderedSongIterable {

    // filters for scanning
    private String artistFilter;
    private Enum genreFilter;
    private int durationFilter = Integer.MAX_VALUE;

    private Enum scanningOrder = ScanningOrder.ADDING;


    public Playlist() {
    }

    public void addSong(Song newSong) {
        if (!inPlaylist(newSong)) {
            super.addLast(newSong);
        }
        else {
            throw new SongAlreadyExistsException();
        }
    }


    public boolean inPlaylist(Song candidate) {
        for(Song s: this) {
            if (s.equals(candidate)) return true;
        }
        return false;
    }

    public boolean removeSong(Song song) {
        return super.remove(song);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Song s: this){
            sb.append("(");
            sb.append(s.toString());
            if(s!= this.getLast()){
                sb.append("), ");
            }
            else sb.append(")]");
        }

        return sb.toString();
    }


    /**
     * Compares between two playlists to check if equal.
     * The playlist order doesn't matter and therefore compares two sets.
     * @param obj Playlist that is a candidate to be equal to
     * @return boolean represents if the two playlists are equal
     */
    @Override
    public boolean equals(Object obj) {
        // if the address is the same
        if (this == obj) {
            return true;
        }
        // if the playlist is empty or object don't share the same class
        if (obj == null || getClass() != obj.getClass()) { //TODO: check getClass
            return false;
        }

        Playlist other = (Playlist) obj;

        Set<Song> thisSongSet = this.makeSet();
        Set<Song> otherSongSet = other.makeSet();

        return thisSongSet.equals(otherSongSet);
    }

    /**
     * Calculates a hash code for a given playlist set
     * @return playlist's hashcode
     */
    @Override
    public int hashCode() {
        return this.makeSet().hashCode();
    }

    private Set<Song> makeSet() {
        return new HashSet<>(this);
    }

    @Override
    public Playlist clone() {
        try {
            Playlist clonedPlaylist = new Playlist();

            for(Song s: this) {
                clonedPlaylist.addSong(s.clone());
            }
            return clonedPlaylist;
        // CloneNotSupportedException
        } catch (SongAlreadyExistsException e) {
            return null;
        }
    }



    @Override
    public void setScanningOrder(Enum order) {
        this.scanningOrder = order;
    }


    @Override
    public Iterator<Song> iterator() {
        return new PlaylistIterator();
    }

    private class PlaylistIterator implements Iterator<Song> {
        private Playlist sortedPlaylist= new Playlist();
        private Iterator<Song> songIterator = Playlist.super.iterator();
        private Song nextSong;

        public PlaylistIterator() {
            findNextSong();
        }

        private void findNextSong() {
            while (songIterator.hasNext()) {
                Song song = songIterator.next();
                if (filterMatches(song)) {
                    nextSong = song;
                    return;
                }
            }
            nextSong = null;
        }

        private boolean filterMatches(Song song) {
            if (artistFilter != null && !song.artist.equals(artistFilter))
                return false;
            if (genreFilter != null && song.genre != genreFilter)
                return false;
            return song.duration <= durationFilter;
        }

        @Override
        public boolean hasNext() {
            return nextSong != null;
        }

        @Override
        public Song next() {
            Song currentSong = nextSong;
            findNextSong();
            return currentSong;
        }

    }

    @Override
    public void filterArtist(String artistName) {
        this.artistFilter = artistName;
        if (artistFilter != null) {
            filterFlag=true;
        }
    }
    @Override
    public void filterGenre(Enum genre) {
        this.genreFilter = genre;
        if (genreFilter != null) {
            filterFlag=true;
        }

    }

    @Override
    public void filterDuration(int duration) {
        this.durationFilter = duration;
    }
}


