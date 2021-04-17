package utils.FileDataSource;

import java.util.List;

import components.Device.Device;

public class ExportDataDecorator extends Decorator {

    private final int MAX = 100000000;;

    public ExportDataDecorator(DataSource source) {
        super(source);
    }

    @Override
    public void writeData(String data) {
        super.writeData(data);
    }

    @Override
    public void writeDataNotOverride(String data) {
        for (int i = 0; i < 5; i++) {
            super.writeDataNotOverride(data);
        }
    }

    @Override
    public List<Device> readData() {
        return super.readData();
    }

}
