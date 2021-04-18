package utils.FileDataSource;

import java.util.List;

import components.Device.Device;

public class Decorator implements DataSource {

    private DataSource wrapper;

    public Decorator(DataSource source) {
        this.wrapper = source;
    }

    @Override
    public void writeData(String data) {
        wrapper.writeData(data);
    }

    @Override
    public List<Device> readData() {
        return wrapper.readData();
    }

    @Override
    public void writeDataNotOverride(String data) {
        wrapper.writeData(data);
    }

    @Override
    public void pipeline(String inputFilename, String ouputFilename, int min, int max) {
        wrapper.pipeline(inputFilename, ouputFilename, min, max);
    }
}
