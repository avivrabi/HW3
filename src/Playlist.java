import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Playlist implements Cloneable, Iterable<Song>, FilteredSongIterable<Song> {

    SNode head;
    SNode tail;

    //filters for scanning
    private String artistFilter;
    private Enum genreFilter;
    private int durationFilter = Integer.MAX_VALUE;
    private boolean filterFlag=false;


    public Playlist() {
    }

    public void addSong(Song newSong) {
        if (!inPlaylist(newSong)) {
            SNode newTail = new SNode(newSong, null);
            if (tail != null) {
                tail.nextSong = newTail;
            } else {
                head = newTail;
            }
            tail = newTail;
        } else {
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
    //TODO: dangerous - may be a null pointer if we want to remove last
        //TODO: check again after changing order
        SNode prevNode = head;
        boolean isFirst = true;
        for (Song s : this) {
            if (s.equals(song)) {
                if (prevNode.nextSong != tail){
                    prevNode.nextSong = prevNode.nextSong.nextSong;
                }
                else {
                    prevNode.nextSong = null;
                    tail = prevNode;
                }
                return true;
            }
            if (!isFirst){
                prevNode=prevNode.nextSong;
            }
            else isFirst=false;
        }
        return false;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        SNode currNode = head;
        while ( currNode.nextSong != null ){
            sb.append("(");
            sb.append(currNode.currSong.toString());
            sb.append("), ");
            currNode = currNode.nextSong;
        }

        // add tail
        sb.append("(");
        sb.append(currNode.currSong.toString());
        sb.append(")");

        sb.append("]");
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
        Set<Song> songSet = new HashSet<>();
        for(Song s : this) {
            songSet.add(s);
        }
        return songSet;
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
    public Iterator<Song> iterator() {
        return new PlaylistIterator();
    }


    public class PlaylistIterator implements Iterator<Song> {
        SNode currNode = head;

        @Override
        public boolean hasNext() {
            return !(currNode == null);
            // || currNode.nextSong == null
        }

        @Override
        public Song next() {
            Song res;
            if(filterFlag) { // if the flag is off it's mean we didn't change anny filter yet, therefore we can run normally
                while (!currNode.currSong.conditionsExist(artistFilter, genreFilter, durationFilter)){
                    currNode = currNode.nextSong; // inc to next node until find the one that fits with the filters conditions
                }
                res = new Song(currNode.currSong);
            }
            else {
                res = new Song(currNode.currSong);
                currNode = currNode.nextSong;
            }
            return res;
        }
    }

    // filter funcs are basicly setters that also turn on the flag
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
        if (duration < Integer.MAX_VALUE) {
            filterFlag=true;
        }
    }
}


