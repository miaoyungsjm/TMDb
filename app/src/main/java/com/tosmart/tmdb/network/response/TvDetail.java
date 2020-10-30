package com.tosmart.tmdb.network.response;

import java.util.List;

/**
 * @author ggz
 * @date 2020/10/30
 */
public class TvDetail {
    /**
     * backdrop_path : /ta5oblpMlEcIPIS2YGcq9XEkWK2.jpg
     * created_by : [{"id":1222585,"credit_id":"55fdc50ec3a368132a001852","name":"Tom Kapinos","gender":2,"profile_path":null}]
     * episode_run_time : [45]
     * first_air_date : 2016-01-25
     * genres : [{"id":80,"name":"Crime"},{"id":10765,"name":"Sci-Fi & Fantasy"}]
     * homepage : https://www.netflix.com/title/80057918
     * id : 63174
     * in_production : true
     * languages : ["en"]
     * last_air_date : 2020-08-21
     * last_episode_to_air : {"air_date":"2020-08-21","episode_number":8,"id":2397418,"name":"Spoiler Alert","overview":"Chloe learns a serial killer may have embellished his crimes. Amenadiel worries about his son's health. Pete opens up to Ella.","production_code":"T13.22158","season_number":5,"show_id":63174,"still_path":"/w1fVmFBTVhLkHXPD75I7FGtBzvk.jpg","vote_average":9.833,"vote_count":6}
     * name : Lucifer
     * next_episode_to_air : null
     * networks : [{"name":"FOX","id":19,"logo_path":"/1DSpHrWyOORkL9N2QHX7Adt31mQ.png","origin_country":"US"},{"name":"Netflix","id":213,"logo_path":"/wwemzKWzjKYJFfCeiB57q3r4Bcm.png","origin_country":""}]
     * number_of_episodes : 75
     * number_of_seasons : 5
     * origin_country : ["US"]
     * original_language : en
     * original_name : Lucifer
     * overview : Bored and unhappy as the Lord of Hell, Lucifer Morningstar abandoned his throne and retired to Los Angeles, where he has teamed up with LAPD detective Chloe Decker to take down criminals. But the longer he's away from the underworld, the greater the threat that the worst of humanity could escape.
     * popularity : 638.314
     * poster_path : /4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg
     * production_companies : [{"id":43346,"logo_path":null,"name":"Fox Productions","origin_country":"US"},{"id":1957,"logo_path":"/3T19XSr6yqaLNK8uJWFImPgRax0.png","name":"Warner Bros. Television","origin_country":"US"},{"id":57542,"logo_path":null,"name":"Aggressive Mediocrity","origin_country":"US"},{"id":9993,"logo_path":"/2Tc1P3Ac8M479naPp1kYT3izLS5.png","name":"DC Entertainment","origin_country":"US"},{"id":40041,"logo_path":"/oP8TmVSh9DCP1yhR2yvjnKfMgbg.png","name":"Jerry Bruckheimer Television","origin_country":"US"}]
     * seasons : [{"air_date":"2015-08-10","episode_count":2,"id":70781,"name":"Specials","overview":"","poster_path":"/bQ5FupU7DFTbx9pSgPsEZQwyZKj.jpg","season_number":0},{"air_date":"2016-01-25","episode_count":13,"id":68415,"name":"Season 1","overview":"Bored with being the Lord of Hell, the devil relocates to Los Angeles, where he opens a nightclub and forms a connection with a homicide detective.","poster_path":"/9qvNXKYqZEsYn3g3yn5tXQe0ceB.jpg","season_number":1},{"air_date":"2016-09-19","episode_count":18,"id":78529,"name":"Season 2","overview":"Lucifer returns for another season, but his devil-may-care attitude may soon need an adjustment: His mother is coming to town.","poster_path":"/zfUp0LO9ZpleAZxwMrjQj0n4b3X.jpg","season_number":2},{"air_date":"2017-10-02","episode_count":26,"id":91441,"name":"Season 3","overview":"As Lucifer struggles with an identity crisis, a gruff new police lieutenant shakes up the status quo with Chloe and the rest of the LAPD.","poster_path":"/3wAYNK29W5PHusoFwPMBO5ei8R1.jpg","season_number":3},{"air_date":"2019-05-08","episode_count":10,"id":117593,"name":"Season 4","overview":"As Chloe struggles to come to terms with Lucifer's disturbing revelation, a rogue priest sets out to stop a long-rumored prophecy.","poster_path":"/q1li5do60mMrIONZcjwUNNZZpT4.jpg","season_number":4},{"air_date":"2020-08-21","episode_count":8,"id":152759,"name":"Season 5","overview":"Lucifer makes a tumultuous return to the land of the living in hopes of making things right with Chloe. A devil\u2019s work is never done.","poster_path":"/96D5o8ywmEbGvBfttj1JLXtJRhg.jpg","season_number":5}]
     * status : Returning Series
     * type : Scripted
     * vote_average : 8.5
     * vote_count : 6035
     */

    private int status_code = 0;
    private String backdrop_path;
    private String first_air_date;
    private String homepage;
    private int id;
    private boolean in_production;
    private String last_air_date;
    private LastEpisodeToAirBean last_episode_to_air;
    private String name;
    private Object next_episode_to_air;
    private int number_of_episodes;
    private int number_of_seasons;
    private String original_language;
    private String original_name;
    private String overview;
    private double popularity;
    private String poster_path;
    private String status;
    private String type;
    private double vote_average;
    private int vote_count;
    private List<CreatedByBean> created_by;
    private List<Integer> episode_run_time;
    private List<GenresBean> genres;
    private List<String> languages;
    private List<NetworksBean> networks;
    private List<String> origin_country;
    private List<ProductionCompaniesBean> production_companies;
    private List<SeasonsBean> seasons;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIn_production() {
        return in_production;
    }

    public void setIn_production(boolean in_production) {
        this.in_production = in_production;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public LastEpisodeToAirBean getLast_episode_to_air() {
        return last_episode_to_air;
    }

    public void setLast_episode_to_air(LastEpisodeToAirBean last_episode_to_air) {
        this.last_episode_to_air = last_episode_to_air;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getNext_episode_to_air() {
        return next_episode_to_air;
    }

    public void setNext_episode_to_air(Object next_episode_to_air) {
        this.next_episode_to_air = next_episode_to_air;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public List<CreatedByBean> getCreated_by() {
        return created_by;
    }

    public void setCreated_by(List<CreatedByBean> created_by) {
        this.created_by = created_by;
    }

    public List<Integer> getEpisode_run_time() {
        return episode_run_time;
    }

    public void setEpisode_run_time(List<Integer> episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public List<GenresBean> getGenres() {
        return genres;
    }

    public void setGenres(List<GenresBean> genres) {
        this.genres = genres;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<NetworksBean> getNetworks() {
        return networks;
    }

    public void setNetworks(List<NetworksBean> networks) {
        this.networks = networks;
    }

    public List<String> getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(List<String> origin_country) {
        this.origin_country = origin_country;
    }

    public List<ProductionCompaniesBean> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<ProductionCompaniesBean> production_companies) {
        this.production_companies = production_companies;
    }

    public List<SeasonsBean> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<SeasonsBean> seasons) {
        this.seasons = seasons;
    }

    public static class LastEpisodeToAirBean {
        /**
         * air_date : 2020-08-21
         * episode_number : 8
         * id : 2397418
         * name : Spoiler Alert
         * overview : Chloe learns a serial killer may have embellished his crimes. Amenadiel worries about his son's health. Pete opens up to Ella.
         * production_code : T13.22158
         * season_number : 5
         * show_id : 63174
         * still_path : /w1fVmFBTVhLkHXPD75I7FGtBzvk.jpg
         * vote_average : 9.833
         * vote_count : 6
         */

        private String air_date;
        private int episode_number;
        private int id;
        private String name;
        private String overview;
        private String production_code;
        private int season_number;
        private int show_id;
        private String still_path;
        private double vote_average;
        private int vote_count;

        public String getAir_date() {
            return air_date;
        }

        public void setAir_date(String air_date) {
            this.air_date = air_date;
        }

        public int getEpisode_number() {
            return episode_number;
        }

        public void setEpisode_number(int episode_number) {
            this.episode_number = episode_number;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getProduction_code() {
            return production_code;
        }

        public void setProduction_code(String production_code) {
            this.production_code = production_code;
        }

        public int getSeason_number() {
            return season_number;
        }

        public void setSeason_number(int season_number) {
            this.season_number = season_number;
        }

        public int getShow_id() {
            return show_id;
        }

        public void setShow_id(int show_id) {
            this.show_id = show_id;
        }

        public String getStill_path() {
            return still_path;
        }

        public void setStill_path(String still_path) {
            this.still_path = still_path;
        }

        public double getVote_average() {
            return vote_average;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }
    }

    public static class CreatedByBean {
        /**
         * id : 1222585
         * credit_id : 55fdc50ec3a368132a001852
         * name : Tom Kapinos
         * gender : 2
         * profile_path : null
         */

        private int id;
        private String credit_id;
        private String name;
        private int gender;
        private Object profile_path;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCredit_id() {
            return credit_id;
        }

        public void setCredit_id(String credit_id) {
            this.credit_id = credit_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public Object getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(Object profile_path) {
            this.profile_path = profile_path;
        }
    }

    public static class GenresBean {
        /**
         * id : 80
         * name : Crime
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class NetworksBean {
        /**
         * name : FOX
         * id : 19
         * logo_path : /1DSpHrWyOORkL9N2QHX7Adt31mQ.png
         * origin_country : US
         */

        private String name;
        private int id;
        private String logo_path;
        private String origin_country;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(String logo_path) {
            this.logo_path = logo_path;
        }

        public String getOrigin_country() {
            return origin_country;
        }

        public void setOrigin_country(String origin_country) {
            this.origin_country = origin_country;
        }
    }

    public static class ProductionCompaniesBean {
        /**
         * id : 43346
         * logo_path : null
         * name : Fox Productions
         * origin_country : US
         */

        private int id;
        private Object logo_path;
        private String name;
        private String origin_country;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(Object logo_path) {
            this.logo_path = logo_path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrigin_country() {
            return origin_country;
        }

        public void setOrigin_country(String origin_country) {
            this.origin_country = origin_country;
        }
    }

    public static class SeasonsBean {
        /**
         * air_date : 2015-08-10
         * episode_count : 2
         * id : 70781
         * name : Specials
         * overview :
         * poster_path : /bQ5FupU7DFTbx9pSgPsEZQwyZKj.jpg
         * season_number : 0
         */

        private String air_date;
        private int episode_count;
        private int id;
        private String name;
        private String overview;
        private String poster_path;
        private int season_number;

        public String getAir_date() {
            return air_date;
        }

        public void setAir_date(String air_date) {
            this.air_date = air_date;
        }

        public int getEpisode_count() {
            return episode_count;
        }

        public void setEpisode_count(int episode_count) {
            this.episode_count = episode_count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public int getSeason_number() {
            return season_number;
        }

        public void setSeason_number(int season_number) {
            this.season_number = season_number;
        }
    }
}
