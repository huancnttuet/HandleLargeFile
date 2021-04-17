package utils.FileDataSource;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import components.Device.Device;

public class SortDecorator extends Decorator {

    public SortDecorator(DataSource source) {
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
        return sortData(super.readData());
    }

    private List<Device> sortData(List<Device> devices) {
        Collections.sort(devices, (a, b) -> a.getWarrentyYear() < b.getWarrentyYear() ? -1
                : a.getWarrentyYear() == b.getWarrentyYear() ? 0 : 1);
        return devices;
    }

}
