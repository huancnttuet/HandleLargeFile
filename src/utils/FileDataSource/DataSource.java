package utils.FileDataSource;

import java.util.List;

import components.Device.Device;

public interface DataSource {
    void writeData(String data);

    void writeDataNotOverride(String data);

    List<Device> readData();

    void pipeline(String inputFilename, String ouputFilename, int min, int max);
}
