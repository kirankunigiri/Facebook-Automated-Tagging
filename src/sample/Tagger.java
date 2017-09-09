package sample;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kiran Kunigiri on 9/7/2017.
 * This class uses the Robot class to
 * tag a person on Facebook given their name.
 */
public class Tagger {

    static Robot robot = null;
    static final int DELAY = 1200;

    public static void tagGroup(Group group) {

        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(DELAY);
        } catch (Exception e) {

        }

        String names = group.getContent();
        String end = "";
        if (names.contains(":")) {
            end = names.substring(names.indexOf(":") + 1).trim();
            names = names.substring(0, names.indexOf(":"));
        }

        String[] people = names.split(",");
        for (int i = 0; i < people.length; i++) {
            String content = "@" + people[i].trim();
            tagPerson(content);
        }

        typeString(end);
    }

    public static void tagPerson(String person) {
        typeString(person);

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (Exception e) {

        }

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (Exception e) {

        }

        typeString(" ");

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (Exception e) {

        }
    }

    public static void typeString(String text) {
        System.out.println(text);
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);


        int pasteKey = 0;
        if (OSValidator.isWindows()) {
            pasteKey = KeyEvent.VK_CONTROL;
        } else {
            pasteKey = KeyEvent.VK_META;
        }

        robot.keyPress(pasteKey);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(pasteKey);
    }


}


// Taken from a tutorial
class OSValidator {

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static void main(String[] args) {

        System.out.println(OS);

        if (isWindows()) {
            System.out.println("This is Windows");
        } else if (isMac()) {
            System.out.println("This is Mac");
        } else if (isUnix()) {
            System.out.println("This is Unix or Linux");
        } else if (isSolaris()) {
            System.out.println("This is Solaris");
        } else {
            System.out.println("Your OS is not support!!");
        }
    }

    public static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }

    public static boolean isMac() {

        return (OS.indexOf("mac") >= 0);

    }

    public static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );

    }

    public static boolean isSolaris() {

        return (OS.indexOf("sunos") >= 0);

    }

}