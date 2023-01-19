package org.example;

import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class MergeJava {
    public static void main(String[] args) {
        boolean isAscending = checkAscending(args);
        boolean isStrings = checkStrings(args);
        String outputFileName = SearchOutputFileName(args);
        List<String> inputFileNames = new ArrayList<>();
        inputFileNames = SearchInputFileNames(args, outputFileName);
        if (isStrings)
            sortStringArray(inputFileNames, isAscending, outputFileName);
        else
            sortIntArray(inputFileNames, isAscending, outputFileName);
        System.out.println("Done");
    }

    public static boolean checkAscending(String[] args) {
        boolean temp_result = true;
        List<String> temp_list = Arrays.asList(args);
        if ((temp_list.contains("-a")) && (temp_list.contains("-d"))) { // Проверка, что бы не ввоидились сразу 2 ключа для одного параметра сортировок
            System.out.println("Программа прекратила свою работу из-за ввода двух противоположных ключей для " +
                    "одного из параметров сортировки - режим сортировки.");
            System.out.println("Пожалуйста, выберите один из ключей для запуска программы");
            System.exit(1);
        } else if ((temp_list.indexOf("-a") != 0 && temp_list.indexOf("-a") != 1 && temp_list.indexOf("-a") != -1) || (temp_list.indexOf("-d") != 0 && temp_list.indexOf("-d") != 1 && temp_list.indexOf("-d") != -1)) { // Проверка, что параметр режима сортировки находиться в начале
            System.out.println("Нарушен порядок расположения параметра для режима сортировки по возрастанию/убыванию в" +
                    " параметрах запуска программы");
            System.out.println("Пожалуйста, соблюдайте порядок следования параметров программы");
            System.exit(2);
        } else if (temp_list.contains("-a") || !(temp_list.contains("-a") || temp_list.contains("-d"))) {                          // Проверяем какой ключ был введен, если веден -a - по возрастанию, если же ключей нет - аналогично
            System.out.println("Выбран режим сортировки по возрастанию (по умолчанию)");
        } else if (temp_list.contains("-d")) {                                                               // -d по убыванию
            System.out.println("Выбран режим сортировки по убыванию");
            temp_result = false;
        }
        return temp_result;
    }

    public static boolean checkStrings(String[] args) {
        boolean temp_result = true;
        List<String> temp_list = Arrays.asList(args);
        if ((temp_list.contains("-s")) && (temp_list.contains("-i"))) {
            System.out.println("Программа прекратила свою работу из-за ввода двух противоположных ключей для " +
                    "одного из параметров сортировки - тип данных");
            System.out.println("Пожалуйста, выберите один из ключей для запуска программы");
            System.exit(3);
        } else if (!(temp_list.contains("-s") || temp_list.contains("-i"))) {
            System.out.println("Программа прекратила свою работу из-за пропущщенного обязательного ключа для " +
                    "одного из параметров сортировки - тип данных");
            System.out.println("Пожалуйста, укажите ключ типа данных для запуска программы");
            System.exit(4);
        } else if ((temp_list.indexOf("-s") != 0 && (temp_list.indexOf("-s") != 1)) && (temp_list.indexOf("-i") != 0 && (temp_list.indexOf("-i") != 1))) { // Проверка, что параметр сортировки находиться в 1-ом аргументе
            System.out.println("Нарушен порядок расположения параметра для типа данных в" +
                    " параметрах запуска программы");
            System.out.println("Пожалуйста, соблюдайте порядок следования параметров программы");
            System.exit(5);
        } else if (temp_list.contains("-s")) {                          // Проверяем какой ключ был введен, если веден -a - по возрастанию, если же ключей нет - аналогично
            System.out.println("Выбран тип данных - строки");
        } else if (temp_list.contains("-i")) {                                                               // -d по убыванию
            System.out.println("Выбран тип данных - целые числа");
            temp_result = false;
        }
        return temp_result;
    }

    public static String SearchOutputFileName(String[] args) {
        List<String> temp_list = Arrays.asList(args);
        File temp_result = new File(temp_list.get(1));
        int tempIndex = 1;
        while (tempIndex <= 2) {
            if (!temp_result.exists() && !temp_result.isFile() && tempIndex == 2) {
                System.out.println("Прекращение работы программы.\nВыходного файла не существует.");
                System.exit(6);
            } else if (temp_result.exists() && temp_result.isFile()) {
                break;
            } else {
                tempIndex++;
                temp_result = new File(temp_list.get(tempIndex));
            }
        }
        return temp_list.get(tempIndex);
    }

    public static List<String> SearchInputFileNames(String[] args, String outputFileNames) {
        List<String> resultSearch = new ArrayList<>();
        List<String> temp_list = Arrays.asList(args);
        if (temp_list.size() < 3) {
            System.out.println("Прекращение работы программы.\nВходные файлы не введены.");
            System.exit(7);
        }
        File temp_path = new File(temp_list.get(2));
        int tempIndex = 2;
        while (tempIndex < temp_list.size()) {
            if (temp_path.equals(new File(outputFileNames))) {
                tempIndex++;
                temp_path = new File(temp_list.get(tempIndex));
                continue;
            } else if (!temp_path.exists() && !temp_path.isFile() && tempIndex == temp_list.size() - 1 && resultSearch.size() == 0) {
                System.out.println("Прекращение работы программы.\nВходные файлы не обнаружены.");
                System.exit(8);
            } else if (temp_path.exists() && temp_path.isFile()) {
                resultSearch.add(temp_list.get(tempIndex));
                if (tempIndex + 1 != temp_list.size()) {
                    tempIndex++;
                    temp_path = new File(temp_list.get(tempIndex));
                } else break;
            } else if (tempIndex + 1 != temp_list.size()) {
                tempIndex++;
                temp_path = new File(temp_list.get(tempIndex));
            } else break;
        }
        return resultSearch;
    }

    public static void sortStringArray(List<String> inputFileNames, boolean isAscending, String outputFileName) {
            FileInputStream inputStream = null;
            Scanner scanner = null;
            File tempOutputFile1 = new File("tempOutputFile.txt");
            FileWriter tempOutputFileWriter1 = null;
            File tempOutputFile2 = new File("tempOutputFile2.txt");
            FileWriter tempOutputFileWriter2 = null;
            for (int i = 0; i < inputFileNames.size(); i++) {
                if (i == 0) {
                    try {
                        inputStream = new FileInputStream(CheckAscedingStringInFile(inputFileNames.get(i)));
                        scanner = new Scanner(inputStream);
                        try {
                            String correctlyValue = "";
                            tempOutputFileWriter1 = new FileWriter(tempOutputFile1, Charset.forName("utf-8"), false);
                            while (scanner.hasNextLine()) {
                                correctlyValue = scanner.nextLine();
                                if (correctlyValue.indexOf(" ") != -1 || correctlyValue.equals(""))
                                    continue;
                                tempOutputFileWriter1.write(correctlyValue);
                                tempOutputFileWriter1.write("\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                tempOutputFileWriter1.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    if (scanner != null) {
                        scanner.close();
                    }
                } else {
                    FileInputStream secondInputStream = null;
                    Scanner secondScanner = null;
                    try {
                        String tempStringValue1 = "";
                        String tempStringValue2 = "";
                        boolean isEmptyIntValue = false;
                        boolean isErrElement = false;
                        String correctValue = "";
                        inputStream = new FileInputStream("tempOutputFile.txt");
                        scanner = new Scanner(inputStream);
                        secondInputStream = new FileInputStream(CheckAscedingStringInFile(inputFileNames.get(i)));
                        secondScanner = new Scanner(secondInputStream);
                        if (scanner.hasNextLine())
                            tempStringValue1 = scanner.nextLine();
                        else isEmptyIntValue = true;
                        if (!secondScanner.hasNextLine())
                            continue;
                        else {
                            try {
                                tempOutputFileWriter2 = new FileWriter(tempOutputFile2, Charset.forName("utf-8"), false);
                                while (scanner.hasNextLine() && secondScanner.hasNextLine()) {
                                    while ((correctValue.indexOf(" ") != -1 || correctValue.equals("")) && secondScanner.hasNextLine())
                                        correctValue = secondScanner.nextLine();
                                    if (correctValue.indexOf(" ") == -1 && !correctValue.equals(""))
                                        tempStringValue2 = correctValue;
                                    else {
                                        isEmptyIntValue = true;
                                        continue;
                                    }
                                    if (tempStringValue1.compareTo(tempStringValue2) < 0) {
                                        tempOutputFileWriter2.append(tempStringValue1);
                                        tempOutputFileWriter2.append("\n");
                                        tempStringValue1 = scanner.nextLine();
                                    } else {
                                        tempOutputFileWriter2.append(tempStringValue2);
                                        tempOutputFileWriter2.append("\n");
                                        correctValue = "";
                                        if (!secondScanner.hasNextLine())
                                            isEmptyIntValue = true;
                                    }
                                }
                                if (!scanner.hasNextLine()) {
                                    while (secondScanner.hasNextLine()) {
                                        while ((correctValue.indexOf(" ") != -1 || correctValue.equals("")) && secondScanner.hasNextLine())
                                            correctValue = secondScanner.nextLine();
                                        if (correctValue.indexOf(" ") == -1 && !correctValue.equals(""))
                                            tempStringValue2 = correctValue;
                                        else {
                                            isErrElement = true;
                                            if (!isEmptyIntValue) {
                                                tempOutputFileWriter2.append(tempStringValue1);
                                                tempOutputFileWriter2.append("\n");
                                                isEmptyIntValue = true;
                                            }
                                            continue;
                                        }
                                        if (!isEmptyIntValue) {
                                            if (tempStringValue1.compareTo(tempStringValue2) < 0) {
                                                tempOutputFileWriter2.append(tempStringValue1);
                                                tempOutputFileWriter2.append("\n");
                                                isEmptyIntValue = true;
                                            }
                                        }
                                        if (secondScanner.hasNextLine()) {
                                            tempOutputFileWriter2.append(String.valueOf(tempStringValue2));
                                            tempOutputFileWriter2.append("\n");
                                            correctValue = "";
                                        }
                                    }
                                    if (!isEmptyIntValue) {
                                        if (tempStringValue1.compareTo(tempStringValue2) < 0) {
                                            tempOutputFileWriter2.append(tempStringValue1);
                                            tempOutputFileWriter2.append("\n");
                                            tempOutputFileWriter2.append(tempStringValue2);
                                        } else {
                                            tempOutputFileWriter2.append(tempStringValue2);
                                            tempOutputFileWriter2.append("\n");
                                            tempOutputFileWriter2.append(tempStringValue1);
                                        }
                                    }  else if (!isErrElement) tempOutputFileWriter2.append(tempStringValue2);
                                } else if (!secondScanner.hasNextLine()) {
                                    while (scanner.hasNextLine()) {
                                        if (!isEmptyIntValue) {
                                            if (tempStringValue2.compareTo(tempStringValue1) < 0) {
                                                tempOutputFileWriter2.append(tempStringValue2);
                                                tempOutputFileWriter2.append("\n");
                                                isEmptyIntValue = true;
                                            }
                                        }
                                        tempOutputFileWriter2.append(String.valueOf(tempStringValue1));
                                        tempOutputFileWriter2.append("\n");
                                        tempStringValue1 = scanner.nextLine();
                                    }
                                    if (!isEmptyIntValue) {
                                        if (tempStringValue1.compareTo(tempStringValue2) < 0) {
                                            tempOutputFileWriter2.append(tempStringValue1);
                                            tempOutputFileWriter2.append("\n");
                                            tempOutputFileWriter2.append(tempStringValue2);
                                        } else {
                                            tempOutputFileWriter2.append(tempStringValue2);
                                            tempOutputFileWriter2.append("\n");
                                            tempOutputFileWriter2.append(tempStringValue1);
                                        }
                                    } else tempOutputFileWriter2.append(tempStringValue1);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    tempOutputFileWriter1.close();
                                    tempOutputFileWriter2.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (secondInputStream != null) {
                            try {
                                secondInputStream.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }
                }
                if (i != 0) {
                    try {
                        Files.copy(tempOutputFile2.toPath(), tempOutputFile1.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        Files.delete(tempOutputFile2.toPath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (isAscending) {
                try {
                    Files.copy(tempOutputFile1.toPath(), new File(outputFileName).toPath() , StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                ReversedLinesFileReader tempReversedFile = null;
                File finalOutputFile1 = new File(outputFileName);
                FileWriter finalOutputFileWriter1 = null;
                try {
                    finalOutputFileWriter1 = new FileWriter(finalOutputFile1, Charset.forName("utf-8"), false);
                    tempReversedFile = new ReversedLinesFileReader(tempOutputFile1);
                    String line = "";
                    line = tempReversedFile.readLine();
                    while (line != null) {
                        finalOutputFileWriter1.write(line);
                        finalOutputFileWriter1.write("\n");
                        line = tempReversedFile.readLine();
                    }
                    tempReversedFile.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        tempReversedFile.close();
                        finalOutputFileWriter1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    public static void sortIntArray(List<String> inputFileNames, boolean isAscending, String outputFileName) {
        FileInputStream inputStream = null;
        Scanner scanner = null;
        File tempOutputFile1 = new File("tempOutputFile.txt");
        FileWriter tempOutputFileWriter1 = null;
        File tempOutputFile2 = new File("tempOutputFile2.txt");
        FileWriter tempOutputFileWriter2 = null;
        for (int i = 0; i < inputFileNames.size(); i++) {
            if (i == 0) {
                try {
                    inputStream = new FileInputStream(CheckAscedingIntInFile(inputFileNames.get(i)));
                    scanner = new Scanner(inputStream);
                    try {
                        String correctlyValue = "";
                        tempOutputFileWriter1 = new FileWriter(tempOutputFile1, Charset.forName("utf-8"), false);
                        while (scanner.hasNextLine()) {
                            correctlyValue = scanner.nextLine();
                            if (correctlyValue.indexOf(" ") != -1 || correctlyValue.equals("") || correctlyValue.matches(".*\\D+.*"))
                                continue;
                            tempOutputFileWriter1.write(correctlyValue);
                            tempOutputFileWriter1.write("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            tempOutputFileWriter1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (scanner != null) {
                    scanner.close();
                }
            } else {
                FileInputStream secondInputStream = null;
                Scanner secondScanner = null;
                try {
                    int tempIntValue1 = 0;
                    int tempIntValue2 = 0;
                    String correctValue = "";
                    boolean isErrElement = false;
                    boolean isEmptyIntValue = false;
                    inputStream = new FileInputStream("tempOutputFile.txt");
                    scanner = new Scanner(inputStream);
                    secondInputStream = new FileInputStream(CheckAscedingIntInFile(inputFileNames.get(i)));
                    secondScanner = new Scanner(secondInputStream);
                    if (scanner.hasNextLine())
                        tempIntValue1 = Integer.parseInt(scanner.nextLine());
                    else isEmptyIntValue = true;
                    if (!secondScanner.hasNextLine())
                        continue;
                    else {
                        try {
                            tempOutputFileWriter2 = new FileWriter(tempOutputFile2, Charset.forName("utf-8"), false);
                            while (scanner.hasNextLine() && secondScanner.hasNextLine()) {
                                while ((correctValue.indexOf(" ") != -1 || correctValue.equals("") || correctValue.matches(".*\\D+.*")) && secondScanner.hasNextLine())
                                    correctValue = secondScanner.nextLine();
                                if (correctValue.indexOf(" ") == -1 && !correctValue.equals("") && !correctValue.matches(".*\\D+.*"))
                                    tempIntValue2 = Integer.parseInt(correctValue);
                                else {
                                    isEmptyIntValue = true;
                                    continue;
                                }
                                if (tempIntValue1 <= tempIntValue2) {
                                    tempOutputFileWriter2.append(String.valueOf(tempIntValue1));
                                    tempOutputFileWriter2.append("\n");
                                    tempIntValue1 = Integer.parseInt(scanner.nextLine());
                                } else { // if (secondScanner.hasNextLine())
                                    tempOutputFileWriter2.append(String.valueOf(tempIntValue2));
                                    tempOutputFileWriter2.append("\n");
                                    correctValue = "";
                                    if (!secondScanner.hasNextLine())
                                        isEmptyIntValue = true;
                                }
                            }
                            if (!scanner.hasNextLine()) {
                                while (secondScanner.hasNextLine()) {
                                    while ((correctValue.indexOf(" ") != -1 || correctValue.equals("") ||  correctValue.matches(".*\\D+.*")) && secondScanner.hasNextLine())
                                        correctValue = secondScanner.nextLine();
                                    if (correctValue.indexOf(" ") == -1 && !correctValue.equals("") && !correctValue.matches(".*\\D+.*"))
                                        tempIntValue2 = Integer.parseInt(correctValue);
                                    else {
                                        isErrElement = true;
                                        if (!isEmptyIntValue) {
                                            tempOutputFileWriter2.append(String.valueOf(tempIntValue1));
                                            tempOutputFileWriter2.append("\n");
                                            isEmptyIntValue = true;
                                        }
                                        continue;
                                    }
                                    if (!isEmptyIntValue) {
                                        if (tempIntValue1 <= tempIntValue2) {
                                            tempOutputFileWriter2.append(String.valueOf(tempIntValue1));
                                            tempOutputFileWriter2.append("\n");
                                            isEmptyIntValue = true;
                                        }
                                    }
                                    if (secondScanner.hasNextLine()) {
                                        tempOutputFileWriter2.append(String.valueOf(tempIntValue2));
                                        tempOutputFileWriter2.append("\n");
                                        correctValue = "";
                                    }
                                }
                                if (!isEmptyIntValue) {
                                    if (tempIntValue1 <= tempIntValue2) {
                                        tempOutputFileWriter2.append(String.valueOf(tempIntValue1));
                                        tempOutputFileWriter2.append("\n");
                                        tempOutputFileWriter2.append(String.valueOf(tempIntValue2));
                                    } else {
                                        tempOutputFileWriter2.append(String.valueOf(tempIntValue2));
                                        tempOutputFileWriter2.append("\n");
                                        tempOutputFileWriter2.append(String.valueOf(tempIntValue1));
                                    }
                                } else if (!isErrElement) tempOutputFileWriter2.append(String.valueOf(tempIntValue2)); // было два
                            } else if (!secondScanner.hasNextLine()) {
                                while (scanner.hasNextLine()) {
                                    if (!isEmptyIntValue) {
                                        if (tempIntValue2 <= tempIntValue1) {
                                            tempOutputFileWriter2.append(String.valueOf(tempIntValue2));
                                            tempOutputFileWriter2.append("\n");
                                            isEmptyIntValue = true;
                                        }
                                    }
                                    tempOutputFileWriter2.append(String.valueOf(tempIntValue1));
                                    tempOutputFileWriter2.append("\n");
                                    tempIntValue1 = Integer.parseInt(scanner.nextLine());
                                }
                                if (!isEmptyIntValue) {
                                    if (tempIntValue1 <= tempIntValue2) {
                                        tempOutputFileWriter2.append(String.valueOf(tempIntValue1));
                                        tempOutputFileWriter2.append("\n");
                                        tempOutputFileWriter2.append(String.valueOf(tempIntValue2));
                                    } else {
                                        tempOutputFileWriter2.append(String.valueOf(tempIntValue2));
                                        tempOutputFileWriter2.append("\n");
                                        tempOutputFileWriter2.append(String.valueOf(tempIntValue1));
                                    }
                                } else tempOutputFileWriter2.append(String.valueOf(tempIntValue1));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                tempOutputFileWriter1.close();
                                tempOutputFileWriter2.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (secondInputStream != null) {
                        try {
                            secondInputStream.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }
            if (i != 0) {
                try {
                    Files.copy(tempOutputFile2.toPath(), tempOutputFile1.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Files.delete(tempOutputFile2.toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (isAscending) {
            try {
                Files.copy(tempOutputFile1.toPath(), new File(outputFileName).toPath() , StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else reversedTempFile(tempOutputFile1.getName(), outputFileName);
    }

    public static String CheckAscedingIntInFile(String nameFile) {
        FileInputStream inputStream = null;
        Scanner scanner = null;
        boolean isAscedingInFile = true;
        try {
            inputStream = new FileInputStream(nameFile);
            scanner = new Scanner(inputStream);
            List<Integer> tempValue = new ArrayList<>();
            String tempStringValue = "";
            if (scanner.hasNextLine()) {
                tempStringValue = scanner.nextLine();
                if (tempStringValue.indexOf(" ") == -1 && !tempStringValue.equals("") && !tempStringValue.matches(".*\\D+.*"))
                    tempValue.add(Integer.parseInt(tempStringValue));
                if (scanner.hasNextLine()) {
                    while (scanner.hasNextLine() && tempValue.size() != 2) {
                        tempStringValue = scanner.nextLine();
                        if (tempStringValue.indexOf(" ") == -1 && !tempStringValue.equals("") && !tempStringValue.matches(".*\\D+.*"))
                            tempValue.add(Integer.parseInt(tempStringValue));
                    }
                }
                if (tempValue.size() == 2)
                    if (tempValue.get(0) > tempValue.get(1))
                        isAscedingInFile = false;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (isAscedingInFile)
            return nameFile;
        else {
            return reversedTempFile(nameFile, "tempInFile.txt");
        }
    }

    public static String reversedTempFile(String inputFile, String outputFile) {
        ReversedLinesFileReader tempReversedFile = null;
        File tempInputFile1 = new File(outputFile);
        FileWriter tempInputWriter1 = null;
        try {
            tempInputWriter1 = new FileWriter(tempInputFile1, Charset.forName("utf-8"), false);
            tempReversedFile = new ReversedLinesFileReader(new File(inputFile));
            String line = "";
            line = tempReversedFile.readLine();
            while (line != null) {
                tempInputWriter1.write(line);
                tempInputWriter1.write("\n");
                line = tempReversedFile.readLine();
            }
            tempReversedFile.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                tempInputWriter1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputFile;
    }

    public static String CheckAscedingStringInFile(String nameFile) {
        FileInputStream inputStream = null;
        Scanner scanner = null;
        boolean isAscedingInFile = true;
        try {
            inputStream = new FileInputStream(nameFile);
            scanner = new Scanner(inputStream);
            List<String> tempValue = new ArrayList<>();
            String tempStringValue = "";
            if (scanner.hasNextLine()) {
                tempStringValue = scanner.nextLine();
            if (tempStringValue.indexOf(" ") == -1 && !tempStringValue.equals(""))
                tempValue.add(tempStringValue);
            if (scanner.hasNextLine()) {
                while (scanner.hasNextLine() && tempValue.size() != 2) {
                    tempStringValue = scanner.nextLine();
                    if (tempStringValue.indexOf(" ") == -1 && !tempStringValue.equals(""))
                        tempValue.add(tempStringValue);
                }
            }
            if (tempValue.size() == 2)
                if (tempValue.get(0).compareTo(tempValue.get(1)) > 0 )
                    isAscedingInFile = false;
        }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (isAscedingInFile)
            return nameFile;
        else {
            return reversedTempFile(nameFile, "tempInFile.txt");
        }
    }
}