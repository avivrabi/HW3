import java.util.Iterator;

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
            sb.append(currNode.currSong.toString());
            sb.append(",");
            currNode = currNode.nextSong;
        }

        // add tail
        sb.append(currNode.currSong.toString());

        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object playlist) {
        return true;
    }

    @Override
    public int hashCode() {
        return 0;

    }

    @Override
    public Iterator iterator() {
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
            Song next = new Song(currNode.currSong);
            return next;
        }
    }
}
