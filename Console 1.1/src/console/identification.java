/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package console;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author dr.gero
 */
public class identification {
        StringProperty android;
        StringProperty status;
        StringProperty ip;
        StringProperty os;
        StringProperty country;
        StringProperty user;
        StringProperty computer;
        StringProperty jre;
 
        identification(String android, String status, String ip, String os, String country, String user, String computer, String jre) {
            this.android = new SimpleStringProperty(android);
            this.status = new SimpleStringProperty(status);
            this.ip = new SimpleStringProperty(ip);
            this.os = new SimpleStringProperty(os);
            this.country = new SimpleStringProperty(country);
            this.user = new SimpleStringProperty(user);
            this.computer = new SimpleStringProperty(computer);
            this.jre = new SimpleStringProperty(jre);
        }
         
        public StringProperty androidProperty() { return android; }
        public StringProperty statusProperty() { return status; }
        public StringProperty ipProperty() { return ip; }
        public StringProperty osProperty() { return os; }
        public StringProperty countryProperty() { return country; }
        public StringProperty userProperty() { return user; }
        public StringProperty computerProperty() { return computer; }
        public StringProperty jreProperty() { return jre; }
    }
