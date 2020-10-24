package com.tosmart.tmdb.network.response;

import java.util.List;

/**
 * @author ggz
 * @date 2020/10/19
 */
public class TvCredits {
    /**
     * cast : [{"character":"","credit_id":"5253857719c2957940208d30","id":19207,"name":"Timothy Bottoms","gender":2,"profile_path":"/qF99X2tCETDjqV9msKsp7CA6usC.jpg","order":0},{"character":"","credit_id":"5253857819c2957940208d42","id":1218332,"name":"Carrie Quinn Dolin","gender":0,"profile_path":"/h5mBgEqeYtL22MnZS6UHtuR3Paw.jpg","order":1},{"character":"","credit_id":"5253857819c2957940208d52","id":29685,"name":"Kurt Fuller","gender":2,"profile_path":"/kV02XLACLFd1YYQdSOgqy6lFmQ3.jpg","order":2},{"character":"","credit_id":"5253857819c2957940208d62","id":6036,"name":"Marcia Wallace","gender":1,"profile_path":"/bDCnZlPdUOyeQN0ewvoEjadBr9V.jpg","order":3},{"character":"","credit_id":"5253857819c2957940208d72","id":34519,"name":"Kristen Miller","gender":1,"profile_path":"/plgKgewVheRyAPbWZSGzo9Zksfq.jpg","order":4},{"character":"","credit_id":"5253857819c2957940208d82","id":158094,"name":"John D'Aquino","gender":2,"profile_path":"/sERXEay5FDr5PSPaRt7TzjBqPxD.jpg","order":5}]
     * crew : [{"credit_id":"5253857819c2957940208da0","department":"Writing","id":60234,"name":"Jonathan Kimmel","gender":2,"job":"Writer","profile_path":null},{"credit_id":"5253857819c2957940208db0","department":"Writing","id":177950,"name":"Kyle McCulloch","gender":2,"job":"Writer","profile_path":null},{"credit_id":"5253857819c2957940208dc8","department":"Production","id":34518,"name":"Matt Stone","gender":2,"job":"Producer","profile_path":"/ActhebxBVSeRtwwVPSPGTmLSUuP.jpg"},{"credit_id":"5253857819c2957940208dd8","department":"Production","id":34517,"name":"Trey Parker","gender":2,"job":"Producer","profile_path":"/A6kLY5TLykcqhzl7tk9Fwp8XRIX.jpg"}]
     * id : 555
     */

    private int id;
    private List<Cast> cast;
    private List<Crew> crew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public static class Cast {
        /**
         * character :
         * credit_id : 5253857719c2957940208d30
         * id : 19207
         * name : Timothy Bottoms
         * gender : 2
         * profile_path : /qF99X2tCETDjqV9msKsp7CA6usC.jpg
         * order : 0
         */

        private String character;
        private String credit_id;
        private int id;
        private String name;
        private int gender;
        private String profile_path;
        private int order;

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public String getCredit_id() {
            return credit_id;
        }

        public void setCredit_id(String credit_id) {
            this.credit_id = credit_id;
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

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }

    public static class Crew {
        /**
         * credit_id : 5253857819c2957940208da0
         * department : Writing
         * id : 60234
         * name : Jonathan Kimmel
         * gender : 2
         * job : Writer
         * profile_path : null
         */

        private String credit_id;
        private String department;
        private int id;
        private String name;
        private int gender;
        private String job;
        private Object profile_path;

        public String getCredit_id() {
            return credit_id;
        }

        public void setCredit_id(String credit_id) {
            this.credit_id = credit_id;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
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

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public Object getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(Object profile_path) {
            this.profile_path = profile_path;
        }
    }
}
