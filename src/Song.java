import java.sql.Date;
import java.sql.Timestamp;

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
    public String toString() { // TODO: check
        int minute = duration / 60;
        int seconds= duration % 60;
        StringBuilder formatDuration = new StringBuilder(minute+":");
        formatDuration.append(seconds<10 ? "0" + seconds : seconds);
        return songName + ", " + artist+ ", " + genre.toString() + ", " + formatDuration;
    }
    @Override
    public boolean equals(Object other){
        if(other instanceof Song){
            return ((Song) other).songName.equals(this.songName)
                && ((Song) other).artist.equals(this.artist);
        }
        return false;
    }

    /**
     * Calculates a hash code for a given song considering it's name and artist.
     * @return a uniform integer to every equal songs
     */
    @Override
    public int hashCode() {
        return 17 * this.songName.hashCode() + 31 * this.artist.hashCode(); //using prime number to raise the likelihood of unique hashcode
    }

    // TODO: finish clone - choose way

    @Override
    public Song clone() {
        try {

            Song res = (Song) super.clone();
            res.songName= new String(this.songName);
            res.artist= new String(this.artist);
            res.genre = this.genre;
            return res;

        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    public void setDuration(int duration){
        if(duration<=0){ //TODO: check if a song duration can be 0
            System.out.println("illegal duration! erase me its not require");//TODO: comment it
        }
        else this.duration=duration;
    }

    public enum Genre {
        POP,
        ROCK,
        HIP_HOP,
        COUNTRY,
        JAZZ,
        DISCO
    }

    public boolean conditionsExist(String artistFilter, Enum genreFilter, int durationFilter){
        return (this.artist.equals(artistFilter) && this.genre.equals(genreFilter) && this.duration <= durationFilter);
    }
}
