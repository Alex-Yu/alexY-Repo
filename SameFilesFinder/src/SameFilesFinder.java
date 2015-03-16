import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by Alex on 07.02.2015.
 */
public class SameFilesFinder {
    private static Properties properties;

    private final static String WORKING_DIRECTORY = System.getProperty("user.dir");
    private final static String PROPERTIES_PATH = "\\src\\resources\\common_en.properties";

    public static void main(String[] args) {
        try {
            //Loading .properties
            properties = getProperties(WORKING_DIRECTORY + PROPERTIES_PATH);

            //Getting folder for searching
            boolean isValidPath = false;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String searchingPath = "";
            while (!isValidPath) {
                System.out.print(properties.getProperty("select.dir"));
                searchingPath = reader.readLine();
                //if 'exit'
                if (searchingPath.equalsIgnoreCase(properties.getProperty("exit"))) {
                    System.out.println(properties.get("bye"));
                    return;
                }
                //Checking validity of searchingPath
                File file = new File(searchingPath);
                if (!file.exists()) {
                    System.out.println(properties.get("incorrect.path"));
                } else if (!file.isDirectory()) {
                    System.out.println(properties.get("not.dir"));
                } else if (!file.canRead()) {
                    System.out.println(properties.get("cant.read.dir"));
                } else {
                    //checking if searchingPath is like 'c:'
                    char lastChar = searchingPath.charAt(searchingPath.length() - 1);
                    if (lastChar == ':') {
                        searchingPath += "\\";   //Adding '\' to the end of searchingPath
                    }
                    isValidPath = true;
                }
            }
            reader.close();
            File directoryForSearching = new File(searchingPath);
            System.out.printf(properties.getProperty("you.select"), directoryForSearching.getAbsolutePath());
            System.out.println(properties.getProperty("scan.dir"));
            //Getting map of all files in searchPath and their sizes
            Map<File, String> filesMap = getFilesSizeMap(searchingPath);
            if (!hasFiles(filesMap)) {
                return;
            }
            System.out.printf(properties.getProperty("found.files"), filesMap.size());

            System.out.println(properties.getProperty("search.same.size"));
            //Remove all files with different sizes from filesMap
            removeSingles(filesMap);
            System.out.printf(properties.getProperty("found.files.same.size"), filesMap.size());
            if (!hasFiles(filesMap)) {
                return;
            }

            System.out.println(properties.getProperty("calc.hash"));
            //Calculate hash for each file in filesMap and write them to filesMap
            filesMap = getFilesHashesMap(filesMap);

            //Remove all files with different hashes from filesMap
            removeSingles(filesMap);
            if (!hasFiles(filesMap)) {
                return;
            }

            //Get resulting list of same files
            List<List<File>> sameFilesList = getSameFilesList(filesMap);

            System.out.printf(properties.getProperty("found.same.files"), filesMap.size(), sameFilesList.size());
            //Print result to console
            for (int i = 0; i < sameFilesList.size(); i++) {
                System.out.println("-----------------------------------------------------------------------------");
                System.out.printf(properties.getProperty("group"), i + 1);
                List<File> sameFiles = sameFilesList.get(i);
                for (File file : sameFiles) {
                    System.out.println(file.toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Properties getProperties(String path) throws IOException {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(path);
        properties.load(fis);


        return properties;
    }

    private static boolean hasFiles(Map<File, String> filesMap) {
        boolean result = true;
        if (filesMap.size() == 0) {
            System.out.println(properties.getProperty("sep"));
            System.out.println(properties.getProperty("no.same.files"));
            result = false;
        }
        return result;
    }

    private static List<List<File>> getSameFilesList(Map<File, String> filesMap) {
        List<List<File>> sameFilesList = new ArrayList<>();
        Set<String> hashes = new HashSet(filesMap.values());
        for (String hash : hashes) {
            ArrayList<File> files = new ArrayList<>();
            for (Map.Entry<File, String> pair : filesMap.entrySet()) {
                File file = pair.getKey();
                if (hash.equals(pair.getValue())) {
                    files.add(file);
                }
            }
            sameFilesList.add(files);
        }

        return sameFilesList;
    }

    private static Map<File, String> getFilesHashesMap(Map<File, String> filesMap) throws NoSuchAlgorithmException {
        Map<File, String> filesHashesMap = new HashMap<>(filesMap);

        MessageDigest messageDigest = MessageDigest.getInstance(properties.getProperty("hash.algorithm"));
        for (Map.Entry<File, String> pair : filesHashesMap.entrySet()) {
            File file = pair.getKey();
            try (FileInputStream fis = new FileInputStream(file);
                 DigestInputStream dis = new DigestInputStream(fis, messageDigest);) {

                //choosing capacity of buffer
                int bufferSize = 0;
                if (file.length() == 0) {
                    filesHashesMap.put(file, "0");  //for empty files
                    continue;
                } else if (file.length() > 8 * 1024) {
                    bufferSize = 8 * 1024;
                } else {
                    bufferSize = (int) file.length();
                }

                //read bytes from file
                while (dis.available() >= 0) {
                    byte[] buffer = new byte[bufferSize];
                    int count = dis.read(buffer);
                    if (count == -1)
                        break;


                }

                //get hash and write to map
                String hash = new String(messageDigest.digest());
                filesHashesMap.put(file, hash);
                dis.close();
                fis.close();
            } catch (IOException e) {
                System.out.printf(properties.getProperty("cant.read"), file.toString());
            }
        }

        return filesHashesMap;
    }

    private static void removeSingles(Map<File, String> filesMap) {
        List<String> values = new LinkedList<>(filesMap.values());
        Collections.sort(values);

        //making a list of not single file sizes
        for (int i = 0; i < values.size(); i++) {
            String current = values.get(i);
            int j = i + 1;
            if (j < values.size() && current.equals(values.get(j))) {
                while (j < values.size() && current.equals(values.get(j))) {
                    values.remove(j);
                }
            } else {
                values.remove(i);
                i--;
            }
        }

        //removing files with single sizes from map
        Iterator<Map.Entry<File, String>> iterator = filesMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<File, String> pair = iterator.next();
            File key = pair.getKey();
            String value = pair.getValue();
            if (!values.contains(value)) {
                iterator.remove();
            }
        }
    }

    private static Map<File, String> getFilesSizeMap(String searchingPath) {
        Map<File, String> fileSizeMap = new HashMap<>();
        Queue<File> queue = new ArrayDeque<>();
        File directory = new File(searchingPath);
        if (directory.canRead()) {
            File[] files = directory.listFiles();
            if (files == null) { //if directory can't be read then return empty map
                System.out.printf(properties.getProperty("cant.read"), directory.toString());
                return fileSizeMap;
            }
            //Fill queue by files and dirs in searchingPath
            for (File file : directory.listFiles()) {
                queue.offer(file);
            }

            //Fill fileSizeMap
            while (!queue.isEmpty()) {
                File fileInQueue = queue.poll();
                if (fileInQueue.isDirectory()) {
                    if (fileInQueue.canRead()) {
                        files = fileInQueue.listFiles();
                        if (files == null) { //if directory can't be read
                            System.out.printf(properties.getProperty("cant.read"), fileInQueue.toString());
                            continue;
                        }
                        for (File file : files) {
                            queue.offer(file);
                        }
                    } else {
                        System.out.printf(properties.getProperty("cant.read"), fileInQueue.toString());
                    }
                } else {
                    String sizeOfFile = String.valueOf(fileInQueue.length());
                    fileSizeMap.put(fileInQueue, sizeOfFile);
                }
            }
        } else {
            System.out.printf(properties.getProperty("cant.read"), directory);
        }

        return fileSizeMap;
    }


}
