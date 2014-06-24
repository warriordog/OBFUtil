package net.acomputerdog.OBFUtil.parse.types;

import net.acomputerdog.OBFUtil.deobfuscator.TargetType;
import net.acomputerdog.OBFUtil.parse.FileParser;
import net.acomputerdog.OBFUtil.table.OBFTable;
import net.acomputerdog.core.file.TextFileReader;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public abstract class CSVFileParser implements FileParser {

    public abstract void writeCSVToTable(File source, CSVFile csv, OBFTable table);

    public abstract CSVFile readCSVFromTable(File source, OBFTable table);

    /**
     * Loads all entries located in a File into an OBFTable.
     *
     * @param file      The file to load from.  Must exist.
     * @param table     The table to write to.
     * @param overwrite If true overwrite existing mappings.
     */
    @Override
    public void loadEntries(File file, OBFTable table, boolean overwrite) throws IOException {
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
                    String[] items = line.split(Pattern.quote(","));
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

    private void trimStrings(String[] strings) {
        for (int index = 0; index < strings.length; index++) {
            strings[index] = strings[index].trim();
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
        //todo: implement
    }

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

        public void addCategories(String... newCats) {
            for (String cat : newCats) {
                catNames.add(cat);
                categories.put(cat, new ArrayList<String>());
            }
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

        public void printCSV() {
            //todo rewrite to output to string/stream
            System.out.println(mergeArray(getCategories()));
            int row = 0;
            while (row < size()) {
                System.out.println(mergeArray(getRow(row)));
                row++;
            }
        }

        private String mergeArray(String[] strings) {
            StringBuilder builder = new StringBuilder();
            for (int index = 0; index < strings.length; index++) {
                builder.append(strings[index]);
                if (index < strings.length - 1) {
                    builder.append(",");
                }
            }
            return builder.toString();
        }
    }
}
