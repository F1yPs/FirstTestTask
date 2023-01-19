Инструкция по запуску

+ Версия Java - 19.0.1
+ Система сборки - Maven 3.8.7
+ Использоваталсь библиотека Apache Commons IO 2.11.0 
Для подключения к Maven:
https://mvnrepository.com/artifact/commons-io/commons-io/2.11.0
Добавления в pom.xml
</properties>
    <dependencies>
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.11.0</version>
        </dependency>
    </dependencies>
	
+ Apache Maven Shade Plugin 3.4.1
https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-shade-plugin/3.4.1
ДОбавление в pom.xml
<dependency>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.4.1</version>
</dependency>
...
<build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.4.1</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
...

Параметры программы задаются при запуске через аргументы командной строки, строго по порядку:

1. режим сортировки (-a или -d), необязательный, по умолчанию сортируем по возрастанию;
2. тип данных (-s или -i), обязательный;
3. имя выходного файла, обязательное, должен быть уже создан до начала выполнения программы;
4. остальные параметры – имена входных файлов, не менее одного.

Файлы должно находиться на одном уровне с директорией src.

Для работы с программой, необходимо перейти в директорию проекта, после чего собрать проект:
```
mvn package
```
Примеры запуска из командной строки для Windows(если файлы находяться в корне проекта):
+ java -cp target/TestTaskJava-1.0-SNAPSHOT.jar org.example.MergeJava -i -a out.txt in.txt (для целых чисел по возрастанию)
+ java -cp target/TestTaskJava-1.0-SNAPSHOT.jar org.example.MergeJava -s out.txt in1.txt in2.txt in3.txt (для строк по возрастанию)
+ java -cp target/TestTaskJava-1.0-SNAPSHOT.jar org.example.MergeJava -d -s out.txt in1.txt in2.txt (для строк по убыванию)

Если разместить входные и выходные файлы в отдельную директорию dirName, то примеры запуска будет следующие:
+ java -cp target/TestTaskJava-1.0-SNAPSHOT.jar org.example.MergeJava -s ./dirName/out.txt ./dirName/in4.txt ./dirName/in5.txt



