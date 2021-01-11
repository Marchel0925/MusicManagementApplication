package repository;

import java.util.List;
import entities.Music;

public class MusicRepository extends GenericRepository<Music> {

    private static final String HIBERNATE_SELECT_QUERY = "from Music";

    public Music findOne(Integer id) {
        return super.findOne(id, Music.class);
    }

    public List<Music> findAll() {
        return super.findAll(HIBERNATE_SELECT_QUERY, Music.class);
    }
}
