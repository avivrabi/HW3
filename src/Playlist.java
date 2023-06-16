import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Playlist implements Cloneable, Iterable<Song> {

    SNode head;
    SNode tail;
    SNode pointer;
    int size = 0;

    // constructor - should stay empty or at least not get anything
    public Playlist() {
    }

    public void addSong(Song newSong) {
        if (!this.inPlaylist(newSong)) {
            head = new SNode(newSong, head);
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
    //TODO: dangerous
        for (Song s : this) {
            if (s.equals(song)) {
                pointer.nextSong=pointer.nextSong.nextSong;
                return true;
            }
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
            sb.append("),");
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
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Playlist other = (Playlist) obj;
        Set<Song> thisSongSet = new HashSet<>();
        SNode currentThis = head;
        while (currentThis != null) {
            thisSongSet.add(currentThis.currSong);
            currentThis = currentThis.nextSong;
        }
        Set<Song> otherSongSet = new HashSet<>();
        SNode currentOther = other.head;
        while (currentOther != null) {
            otherSongSet.add(currentOther.currSong);
            currentOther = currentOther.nextSong;
        }
        return thisSongSet.equals(otherSongSet);
    }

    /**
     * Calculates a hash code for a given playlist
     * @return playlist's hashcode
     */
    @Override
    public int hashCode() {
        int hashCode = 0;
        for(Song song: this) {
            hashCode = 31 * hashCode + song.hashCode();
        }
        return hashCode;
    }

    @Override
    public Playlist clone() {
        try {
            Playlist clone = (Playlist) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
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
            return !(currNode == null || currNode.nextSong == null);
        }

        @Override
        public Song next() {
            pointer = currNode;
            currNode = currNode.nextSong;
            return new Song(currNode.currSong);
        }
    }
}
