package com.alexcaranha.jquerybyhumming.screen.about;

import com.alexcaranha.jquerybyhumming.mvp.IModel;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public class About_Model implements IModel{

    private String systemTitle, systemDescription;
    private String authorTitle, authorDescription;
    private String leaderTitle, leaderDescription;

    public void setSystemTitle(String systemTitle) {
        this.systemTitle = systemTitle;
    }

    public void setSystemDescription(String systemDescription) {
        this.systemDescription = systemDescription;
    }

    public void setAuthorTitle(String authorTitle) {
        this.authorTitle = authorTitle;
    }

    public void setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
    }

    public void setLeaderTitle(String leaderTitle) {
        this.leaderTitle = leaderTitle;
    }

    public void setLeaderDescription(String leaderDescription) {
        this.leaderDescription = leaderDescription;
    }

    public String getSystemTitle() {
        return systemTitle;
    }

    public String getSystemDescription() {
        return systemDescription;
    }

    public String getAuthorTitle() {
        return authorTitle;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public String getLeaderTitle() {
        return leaderTitle;
    }

    public String getLeaderDescription() {
        return leaderDescription;
    }
    
    public Map<String, Object> getVariables() {
        return null;
    }

    /*
    public void saveToXML(String pathXML) throws FileNotFoundException {
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("about", Database_Model.class);

        FileOutputStream fs = new FileOutputStream(pathXML);
        xstream.toXML(this, fs);
    }
    */

    /*
    public void loadFromXML(String pathXML) {
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("about", Database_Model.class);

        InputStream inputStream = Utils.getInputStreamFromPath(pathXML);
        Database_Model about = (Database_Model) xstream.fromXML(inputStream);

        this.system = about.getAboutSystem();
        this.author = about.getAboutAuthor();
        this.leader = about.getAboutLeader();
    }
    */

    /*
    public static void main(String[] args) throws Exception {
        String pathXML = Utils.getDirExecution() + File.separator + "About.xml";

        Database_Model factory = new Database_Model();

        factory.saveToXML(pathXML);
        factory.printDataInConsole();

        factory.setAboutSystem("JQueryByHummingSystem.");
        factory.setAboutAuthor("Alex Libório Caranha.");
        factory.setAboutLeader("Luiz Wagner Pereira Biscainho.");

        factory.loadFromXML(pathXML);
        factory.printDataInConsole();

        factory.setAboutSystem("JQueryByHummingSystem.");
        factory.setAboutAuthor("Alex Libório Caranha.");
        factory.setAboutLeader("Luiz Wagner Pereira Biscainho.");
        factory.saveToXML(pathXML);

        factory = new Database_Model();
        factory.printDataInConsole();

        factory.loadFromXML(pathXML);
        factory.printDataInConsole();
    }
    */
}