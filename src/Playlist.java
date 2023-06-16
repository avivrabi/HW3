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

//TODO: something after lunch!
    /*
    public void addSong(Song newSong) {
        if (!this.inPlaylist(newSong)) {

            SNode newTail = new SNode(newSong, null);
            this.tail.nextSong = newTail;
            this.tail = newTail;


        } else {
            throw new SongAlreadyExistsException();
        }
    }

     */

//    public void addSong(Song newSong) {
//        if (!this.inPlaylist(newSong)) {
//            head = new SNode(newSong, head);
//        }
//        else {
//            throw new SongAlreadyExistsException();
//        }
//    }



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
            // after the first clone we'll get the playlist tail to head
            //Playlist reverseClonedPlaylist = (Playlist) super.clone();
            Playlist reverseClonedPlaylist = new Playlist();
            for(Song s: this) {
                reverseClonedPlaylist.addSong(s.clone());
            }
            // cloned again to get the correct order
            //Playlist clonedPlaylist = (Playlist) super.clone();
            Playlist clonedPlaylist = new Playlist();
            for(Song s: reverseClonedPlaylist) {
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
