package utils.FileDataSource;

import java.util.List;

import components.Device.Device;

public class NomalizeDecorator extends Decorator {

    public NomalizeDecorator(DataSource source) {
        super(source);
    }

    @Override
    public void writeData(String data) {
        super.writeData(data);
    }

    @Override
    public void writeDataNotOverride(String data) {
        super.writeDataNotOverride(data);
    }

    @Override
    public List<Device> readData() {
        return nomalizeData(super.readData());
    }

    public List<Device> nomalizeData(List<Device> devices) {
        for (Device device : devices) {
            String oldOwner = device.getOwner();
            String newOwner = nomalizeOwner(oldOwner);
            device.setOwner(newOwner);
        }

        return devices;

    }

    private String nomalizeOwner(String oldOwner) {
        String newOwnn = oldOwner.toLowerCase().trim().replaceAll(" +", " ");
        return capitalizeString(newOwnn);

    }

    public static String capitalizeString(String string) {
        char[] chars = string.toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i])) {
                found = false;
            }
        }
        return String.valueOf(chars);
    }
}
