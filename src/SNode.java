public class SNode{
    Song currSong;
    SNode nextSong;

    public SNode(Song currSong, SNode nextSong) {
        this.currSong = currSong;
        this.nextSong = nextSong;
    }
    // TODO: see if needed
    public SNode(Song currSong) {
        this.currSong = currSong;
        this.nextSong = null;
    }

}
