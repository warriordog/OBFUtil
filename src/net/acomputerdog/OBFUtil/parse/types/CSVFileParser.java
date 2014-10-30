package net.acomputerdog.OBFUtil.parse.types;

import net.acomputerdog.OBFUtil.parse.FileParser;
import net.acomputerdog.OBFUtil.table.OBFTable;
import net.acomputerdog.core.file.TextFileReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Reads and writes obfuscation mappings to a .csv file.  Due to variations in CSV formats, this class is abstract so that subclasses can identify the correct data to read.
 */
public abstract class CSVFileParser implements FileParser {
    private static final String PATTERN_COMMA = Pattern.quote(",");

    /**
     * Writes the data in a CSVFile to an OBFTable
     *
     * @param source The file where the data originated from.
     * @param csv    The CSVFile to read from
     * @param table  The OBFTable to write to.
     */
    protected abstract void writeCSVToTable(File source, CSVFile csv, OBFTable table);

    /**
     * Creates a CSVFile representing the data in an OBFTable
     *
     * @param source The file where the data originated from.
     * @param table  The table containing the data.
     * @return Return a CSVFile representing the data in the OBFTable
     */
    protected abstract CSVFile readCSVFromTable(File source, OBFTable table);

    /**
     * Loads all entries located in a File into an OBFTable.
     *
     * @param file      The file to load from.  Must exist.
     * @param table     The table to write to.
     * @param overwrite If true overwrite existing mappings.
     */
    @Override
    public void loadEntries(File file, OBFTable table, boolean overwrite) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File must not be null!");
        }
        TextFileReader reader = null;
        try {
            CSVFile csv = new CSVFile();
            reader = new TextFileReader(file);
            String[] lines = reader.readAllLines();
            int lineNum = 0;
            int realLineNum = 0;
            String[] categories = new String[0];
            for (String line : lines) {
                if (!isLineEmpty(line)) {
                    String[] items = line.split(PATTERN_COMMA);
                    if (lineNum == 0) {
                        categories = items;
                        lineNum = 0;
                    } else {
                        int itemNum = 0;
                        for (String item : items) {
                            if (itemNum < categories.length) {
                                csv.addItem(categories[itemNum], item);
                                itemNum++;
                            } else {
                                String newItem = csv.getItem(categories[itemNum - 1], csv.size() - 1) + "," + (item);
                                csv.setItem(categories[itemNum - 1], csv.size() - 1, newItem);
                            }
                        }
                        while (itemNum < categories.length) {
                            csv.addItem(categories[itemNum], "");
                            itemNum++;
                        }

                    }
                    lineNum++;
                }
                realLineNum++;
            }
            this.writeCSVToTable(file, csv, table);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * Saves all entries located in an OBFTable into a file.
     *
     * @param file  The file to write to.  Must exist.
     * @param table The table to read from
     * @throws java.io.IOException
     */
    @Override
    public void storeEntries(File file, OBFTable table) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File must not be null!");
        }
        Writer out = null;
        try {
            out = new BufferedWriter(new FileWriter(file));

            CSVFile csv = this.readCSVFromTable(file, table);
            out.write(csv.toString());

        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    private boolean isLineEmpty(String line) {
        if (line.isEmpty()) {
            return true;
        } else {
            line = line.trim();
            char[] chars = line.toCharArray();
            if (chars[0] == ',') {
                return true;
            }
            for (char chr : chars) {
                if (chr != ',' && !Character.isWhitespace(chr)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * A code structure representing a CSV file
     */
    public static class CSVFile {
        private final Map<String, List<String>> categories = new HashMap<String, List<String>>();
        private final List<String> catNames = new ArrayList<String>();
        private int size = 0;

        private List<String> getCategory(String name) {
            List<String> category = categories.get(name);
            if (category == null) {
                categories.put(name, category = new ArrayList<String>());
                catNames.add(name);
            }
            return category;
        }

        public String[] getCategories() {
            return catNames.toArray(new String[catNames.size()]);
        }

        public String getItem(String category, int index) {
            return getCategory(category).get(index);
        }

        public void setItem(String category, int index, String item) {
            getCategory(category).set(index, item);
        }

        public void addItem(String category, String item) {
            List<String> catList = getCategory(category);
            catList.add(item);
            size = catList.size();
        }

        public int size() {
            return size;
        }

        public String[] getRow(int row) {
            if (row > size) {
                throw new IllegalArgumentException("Row number " + row + " is greater than maximum " + size);
            }
            String[] rowData = new String[categories.size()];
            int index = 0;
            for (String str : getCategories()) {
                List<String> category = getCategory(str);
                rowData[index] = getRow(category, row);
                index++;
            }
            return rowData;
        }

        private String getRow(List<String> category, int row) {
            return category.get(row);
        }

        public void setRow(int row, String[] data) {
            int index = 0;
            String[] categoryIDs = getCategories();
            for (String str : data) {
                getCategory(categoryIDs[index]).set(row, str);
                index++;
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            mergeArray(getCategories(), builder);
            int row = 0;
            while (row < size()) {
                builder.append("\n");
                mergeArray(getRow(row), builder);
                row++;
            }
            return builder.toString();
        }

        private void mergeArray(String[] strings, StringBuilder builder) {
            for (int index = 0; index < strings.length; index++) {
                builder.append(strings[index]);
                if (index < strings.length - 1) {
                    builder.append(",");
                }
            }
        }
    }
}
