public class Song implements Cloneable {
    String songName;
    String artist;
    Enum genre;
    int duration;

    public Song(String songName, String artist, Enum genre, int duration){
        this.songName = songName;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
    }

    public Song(Song song){
        this.songName = song.songName;
        this.artist = song.artist;
        this.genre = song.genre;
        this.duration = song.duration;
    }


    @Override
    public String toString() {
        return super.toString(); //TODO
    }
    @Override
    public boolean equals(Object other){
        return true;
    }
    @Override
    public int hashCode() {
        return 0;

    }

    // TODO: finish clone - choose way
//    @Override
//    public Song clone(){
//        return new Song(this);
//    }
    @Override
    public Song clone() {
        try {
            return (Song) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public enum Genre {
        POP,
        ROCK,
        HIP_HOP,
        COUNTRY,
        JAZZ,
        DISCO
    }
}
