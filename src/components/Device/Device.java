package components.Device;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Device {
    private String code;
    private String name;
    private String owner;
    private Date inputDate;
    private int warrentyYear;

    static List<String> stubName = Arrays.asList("MSC ERICSSON", "BTS NOKIA", "OCS HUAWEI", "EPC ERICSSON");

    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    public Device() {
        Random randomizer = new Random();
        this.code = randomIdentifierCode();
        this.name = stubName.get(randomizer.nextInt(stubName.size()));
        this.owner = randomIdentifierOwner();
        long ms = -946771200000L + (Math.abs(randomizer.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));
        this.inputDate = new Date(ms);
        int range = (10 - 1) + 1;
        this.warrentyYear = (int) (Math.random() * range) + 1;
    }

    public Device(String code, String name, String owner, Date inputDate, int warrentyYear) {
        this.setCode(code);
        this.setName(name);
        this.setOwner(owner);
        this.setInputDate(inputDate);
        this.setWarrentyYear(warrentyYear);
    }

    public Device(String[] deviceString) {
        this.setCode(deviceString[0]);
        this.setName(deviceString[1]);
        this.setOwner(deviceString[2]);
        this.setInputDate(StringToDate(deviceString[3]));
        this.setWarrentyYear(Integer.parseInt(deviceString[4]));
    }

    public int getWarrentyYear() {
        return warrentyYear;
    }

    public void setWarrentyYear(int warrentyYear) {
        this.warrentyYear = warrentyYear;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String toString() {
        return (this.getCode() + "," + this.getName() + "," + this.getOwner() + "," + DateToString(this.inputDate) + ","
                + String.valueOf(this.warrentyYear) + "\n");
    }

    // class variable
    final String lexicon2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abc def gh ik lnm gh qweoz xpocr";
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    final java.util.Random rand = new java.util.Random();

    // consider using a Map<String,Boolean> to say whether the identifier is being
    // used or not
    final Set<String> identifiers = new HashSet<String>();

    public String randomIdentifierCode() {
        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = 11;
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if (identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }

    public String randomIdentifierOwner() {
        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = rand.nextInt(5) + 16;
            for (int i = 0; i < length; i++) {
                builder.append(lexicon2.charAt(rand.nextInt(lexicon.length())));
            }

        }
        return builder.toString();
    }

    public String DateToString(Date date) {
        String output = "";
        try {
            output = df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public Date StringToDate(String date) {
        Date output = new Date();
        try {
            output = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }
}
