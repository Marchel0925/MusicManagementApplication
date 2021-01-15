package repository;

import java.util.*;
import entities.Artist;


public class ArtistRepository extends GenericRepository<Artist> {

    private static final String HIBERNATE_SELECT_QUERY = "from Artist";

    public Artist findOne(Integer id) {
        return super.findOne(id, Artist.class);
    }

    public List<Artist> findAll() {
        return super.findAll(HIBERNATE_SELECT_QUERY, Artist.class);
    }

}
