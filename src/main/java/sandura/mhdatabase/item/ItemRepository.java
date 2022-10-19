package sandura.mhdatabase.item;

import sandura.mhdatabase.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRepository {

    Logger log = new Logger(Logger.LoggingLevel.INFO);
    public ItemRepository() {
        File itemsFileDatabase = new File("./src/main/resources/item.db");
        log.logDebug(itemsFileDatabase.getAbsolutePath());
        log.logDebug(itemsFileDatabase.isFile());
        log.logDebug(itemsFileDatabase.isDirectory());
        Map<String, String> itemRepositoryMap = new HashMap<>();
        try {
            List<String> items = Files.readAllLines(itemsFileDatabase.toPath());
            for (int i = 0; i < items.size(); i++) {
                if (i == 0) {
                    log.logDebug("Items table header:");
                    log.logDebug(items.get(i));
                } else {
                    String itemRow = items.get(i);
                    log.logDebug(itemRow);
                    String regex = ",";
                    String[] split = itemRow.split(regex);
                    if (split.length > 0) {

                        String id = split[0];
                        if (split.length > 1) {
                            String dataString = "";
                            for (int j = 1; j < split.length; j++) {
                                if (j == (split.length - 1)) {
                                    dataString += split[j];
                                } else {
                                    dataString += split[j] + ";";
                                }
                            }
                            itemRepositoryMap.put(id, dataString);
                        }
                    }

                }
            }
            setItems(itemRepositoryMap);
            //TODO add table with locations and add item which is located in many zones
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
    private Map<String,String> items;

    public Map<String,String> getItems() {
        return items;
    }

    public void setItems(Map<String,String> items) {
        this.items = items;
    }
}
