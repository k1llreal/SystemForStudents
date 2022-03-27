package studentSystem;


import java.io.*;
import java.util.*;


public class Main {
    private static final String PATH = "src/studentSystem/mainFile.txt";
    static Scanner in = new Scanner(System.in);
    static Scanner sc = new Scanner(System.in);
    static Set<Student> students = new HashSet<>();
    static Set<Teacher> teachers = new HashSet<>();
    static Set<Subject> subjects = new HashSet<>();

    public static void main(String[] args) {
        readFile();
        System.out.println("\n\n\n<<<<<<<<СИСТЕМА УЧЕТА УСПЕВАЕМОСТИ СТУДЕНТОВ>>>>>>>>");
        mainPage();
    }

    public static void mainPage() {
        textMenu();
        do {
            String choice = in.next();
            //проверка введённого числа с помощью регулярного выражения
            if (choice.matches("[0-8]")) {
                int info = Integer.parseInt(choice);
                switch (info) {
                    case 0 -> {
                        saveFile();
                        sc.close();
                        in.close();
                        System.out.println("-_-_-_-_-_-_-_-Вы вышли из системы_-_-_-_-_-_-_-_-_");
                        System.exit(1);
                    }
                    case 1 -> {
                        System.out.println("""
                                -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
                                \t 1. Получить список студентов
                                \t 2. Добавить студента
                                \t 3. Удалить студента
                                \t 4. Получить рейтинг конкретного студента
                                -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
                                --Введите действие или введите 0 для возвращения назад""");
                        String choice2 = in.next();
                        //проверка введённого числа с помощью регулярного выражения
                        if (choice2.matches("[1-4]")) {
                            int info2 = Integer.parseInt(choice2);
                            switch (info2) {
                                case 1 -> printSet(students); //вывод списка студентов
                                case 2 -> addStud(); //добавление студента
                                case 3 -> removeStud(); //удаление студента
                                case 4 -> {
                                    System.out.println(" --Введите информацию о студенте в формате: \"Имя Фамилия\"");
                                    String nameSTD = sc.nextLine();
                                    Student std = new Student(nameSTD);
                                    getStudRating(std); //получаем рейтинг студента
                                }
                                default -> {
                                }
                            }
                        }
                        textHelpMenu();
                    }
                    case 2 -> {
                        System.out.println("""
                                -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
                                \t 1. Получить список преподавателей
                                \t 2. Добавить преподавателя
                                \t 3. Удалить преподавателя
                                \t 4. Получить рейтинг конкретного преподавателя
                                -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
                                --Введите действие или введите 0 для возвращения назад""");
                        String choice3 = in.next();
                        //проверка введённого числа с помощью регулярного выражения
                        if (choice3.matches("[1-4]")) {
                            int info3 = Integer.parseInt(choice3);
                            switch (info3) {
                                case 1 -> printSet(teachers); //вывод списка преподавателей
                                case 2 -> addTeach(); //добавление преподавателя
                                case 3 -> removeTeach(); //удаление преподавателя
                                case 4 -> {
                                    System.out.println(" --Введите информацию о преподавателе в формате: \"Имя Фамилия\"");
                                    String nameTCHR = sc.nextLine();
                                    Teacher tch = new Teacher(nameTCHR);
                                    getTeachRating(tch); //получаем рейтинг преподавателя
                                }
                                default -> {
                                }
                            }
                        }
                        textHelpMenu();
                    }
                    case 3 -> {
                        System.out.println("""
                                -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
                                \t 1. Получить список предметов
                                \t 2. Добавить предмет
                                \t 3. Удалить предмет
                                -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
                                --Введите действие или введите 0 для возвращения назад""");
                        String choice4 = in.next();
                        //проверка введённого числа с помощью регулярного выражения
                        if (choice4.matches("[1-3]")) {
                            int info4 = Integer.parseInt(choice4);
                            switch (info4) {
                                case 1 -> printSet(subjects); //вывод списка предметов
                                case 2 -> addSubject(); //добавление предмета
                                case 3 -> removeSubject(); //удаление предмета
                                default -> {
                                }
                            }
                        }
                        textHelpMenu();
                    }
                    case 4 -> {
                        System.out.println("--Введите предмет для добавления оценок" +
                                "\n--Список существующих предметов: " + getSubjList());
                        String s = sc.nextLine();
                        Subject sb = new Subject(s);
                        //проверка наличия предмета в системе
                        if (subjects.contains(sb)) {
                            System.out.println("--Предмет найден!");
                            //получаем при помощи stream объект предмета, чтобы узнать ведущего и проверяющего преподавателя и добавить их в обновлённом объекте предмета с оценками
                            Subject teach = subjects.stream().filter(el -> el.equals(sb)).toList().get(0);
                            sb.setLeadTCHR(teach.getLeadTCHR()); //добавляем ведущего преподавателя
                            sb.setCheckTCHR(teach.getCheckTCHR()); //добавляем проверяющего преподавателя
                            setSubjectMark(sb); //метод для простановки оценок
                        } else {
                            System.out.println("--Такого предмета не существует!");
                        }
                        textHelpMenu();
                    }
                    case 5 -> {
                        System.out.print("--Рейтинг студентов:");
                        getAllStudRating();
                        System.out.print("\n\n--Рейтинг преподавателей:");
                        getAllTeachRating();
                        System.out.println();
                        textHelpMenu();
                    }
                    case 6 -> {
                        getMarks();
                        textHelpMenu();
                    }
                    case 7 -> {
                        saveFile();
                        System.out.println("-Информация успешно сохранена!");
                        textHelpMenu();
                    }
                    case 8 -> textMenu();
                    default -> {
                    }
                }
            }
        } while (true);
    }

    public static void textMenu() {
        System.out.println("""
                -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_
                \t 1. Список действий со студентами
                \t 2. Список действий с преподавателями
                \t 3. Список действий с предметами
                \t 4. Генерация оценок для студентов по предметам
                \t 5. Подсчёт и вывод рейтинга студентов и преподавателей
                \t 6. Вывод списка оценок студентов
                \t 7. Сохранить информацию
                -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_
                -Введите действие из ОСНОВНОГО МЕНЮ или введите 0 для сохранения и выхода из программы""");
    }

    public static void textHelpMenu() {
        System.out.println("\n-Введите действие из ОСНОВНОГО МЕНЮ или введите 0 для выхода из программы. (Для просмотра основного меню введите 8)");
    }

    public static void readFile() {
        try {
            BufferedReader in1 = new BufferedReader(new FileReader(PATH));
            BufferedReader in2 = new BufferedReader(new FileReader(PATH));
            BufferedReader in3 = new BufferedReader(new FileReader(PATH));
            if (Objects.equals(in1.readLine(), ">>>Students<<<")) {
                while (!Objects.equals(in2.readLine(), ">>>Teachers<<<")) {
                    Student st = new Student(in3.readLine());
                    if (!st.getName().equals(">>>Students<<<")) {
                        students.add(st);
                    }
                }
                in3.readLine();
                while (!Objects.equals(in2.readLine(), ">>>Subjects and marks<<<")) {
                    Teacher tc = new Teacher(in3.readLine());
                    teachers.add(tc);
                }
                in3.readLine();
                while (!Objects.equals(in2.readLine(), null)) {
                    String s = in3.readLine();
                    String[] parts = s.split(" : ");
                    Teacher tc1 = new Teacher(parts[1]);
                    Teacher tc2 = new Teacher(parts[2]);
                    Subject sb = new Subject(parts[0], tc1, tc2);
                    if (!Objects.equals(parts[3], "0")) {
                        for (int i = 3; i < parts.length; i++) {
                            String[] studAndMark = parts[i].split("=");
                            Student st = new Student(studAndMark[0]);
                            int mark = Integer.parseInt(studAndMark[1]);
                            sb.setSubjMarks(st, mark);
                        }
                    }
                    subjects.add(sb);
                }
                System.out.println(">>>Загрузка сохранённых данных выполнена успешно");
            } else {
                System.out.println("!!!!!--Ошибка чтения файла. Сохранённая информация не загружена.!!!!!");
            }
        } catch (IOException e) {
            System.out.println("!!!!!--Ошибка чтения файла. Сохранённая информация не загружена.!!!!!");
        }
    }

    public static void saveFile() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(PATH));
            out.write(">>>Students<<<\n");
            for (Student student : students) {
                out.write(student.getName());
                out.newLine();
            }
            out.write(">>>Teachers<<<\n");
            for (Teacher teacher : teachers) {
                out.write(teacher.getName());
                out.newLine();
            }
            out.write(">>>Subjects and marks<<<\n");
            for (Subject subject : subjects) {
                if (!subject.getSubjMarks().isEmpty()) {
                    out.write(subject.getNameOfSubj() + " : " + subject.getLeadTCHR() + " : " + subject.getCheckTCHR()
                            + " : ");
                    for (Student st : students) {
                        out.write(st.getName() + "=" + subject.getSubjMarks().get(st) + " : ");
                    }
                    out.newLine();
                } else {
                    out.write(subject.getNameOfSubj() + " : " + subject.getLeadTCHR() + " : " + subject.getCheckTCHR() + " : " + 0);
                    out.newLine();
                }
            }
            out.close();
        } catch (IOException e) {
            System.out.println("--Ошибка сохранения");
        }
    }

    public static void printSet(Set<?> set) {
        for (Object o : set) {
            System.out.println(o);
        }
    }

    public static void addStud() {
        System.out.println("--Введите информацию о студенте в формате: \"Имя Фамилия\"");
        String s = sc.nextLine();
        String[] parts = s.split(", ");
        String name = parts[0];
        Student std = new Student(name);
        if (!students.contains(std)) {
            students.add(std);
            System.out.println("--Студент успешно добавлен!");
        } else {
            System.out.println("--Такой студент уже есть!");
        }
    }

    public static void removeStud() {
        System.out.println("--Введите информацию о студенте, которого нужно удалить в формате: \"Имя Фамилия\" ");
        String s = sc.nextLine();
        Student st = new Student(s);
        if (students.contains(st)) {
            students.remove(st);
            System.out.println("--Студент " + st.getName() + " успешно удалён!");
        } else {
            System.out.println("--Такого студента не найдено!");
        }
    }

    public static void addTeach() {
        System.out.println("--Введите информацию о преподавателе в формате: \"Имя Фамилия\"");
        String s = sc.nextLine();
        String[] parts = s.split(", ");
        String name = parts[0];
        Teacher tr = new Teacher(name);
        if (!teachers.contains(tr)) {
            teachers.add(tr);
            System.out.println("--Преподаватель успешно добавлен!");
        } else {
            System.out.println("--Такой преподаватель уже есть!");
        }
    }

    public static void removeTeach() {
        System.out.println("--Введите информацию о преподавателе, которого нужно удалить в формате: \"Имя Фамилия\" ");
        String s = sc.nextLine();
        Teacher tr = new Teacher(s);
        if (teachers.contains(tr)) {
            teachers.remove(tr);
            System.out.println("--Преподаватель " + tr.getName() + " успешно удалён!");
        } else {
            System.out.println("--Такого преподавателя не найдено!");
        }
    }

    public static void setSubjectMark(Subject sub) {
        for (Student st : students) {
            System.out.println("--Введите оценку студента в 100-бальной системе: " + st.getName()
                    + " по предмету: " + sub.getNameOfSubj() + " (если оценки нет введите 0) -->");
            try {
                String s2 = sc.nextLine();
                int mark = Integer.parseInt(s2);
                if ((mark >= 0) && (mark <= 100)) {
                    sub.setSubjMarks(st, mark);
                    //обновляем предмет в списке
                    subjects.remove(sub);
                    subjects.add(sub);
                    System.out.println("--Оценка успешно обновлена!");
                } else {
                    System.out.println("--Неверный формат оценки! Оценка для студента: " + st.getName() + " не добавлена."
                            + " Попробуйте заново!");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("--Неверный формат оценки! Оценка для студента: " + st.getName() + " не добавлена."
                        + " Попробуйте заново!");
                return;
            }
        }
        System.out.println("--Введённые оценки успешно обновлены!");
    }

    public static void addSubject() {
        System.out.println("--Введите информацию о предмете в формате: \"Название, ведущий преподаватель, проверяющий преподаватель\"" +
                "\n--Список преодавателей: " + getTeachList());
        String s = sc.nextLine();
        String[] arrStr = s.split(", ");
        try {
            Subject sub = new Subject(arrStr[0]);
            //проверка наличия в системе предмета с таким названием
            if (!subjects.contains(sub)) {
                Teacher tc1 = new Teacher(arrStr[1]);
                Teacher tc2 = new Teacher(arrStr[2]);
                //проверка существует ли такой преподаватель1 в системе
                if (teachers.contains(tc1)) {
                    //проверка существует ли такой преподаватель2 в системе
                    if (teachers.contains(tc2)) {
                        //условие (один преподаватель не может одновременно вести и проверять предмет)
                        if (!tc1.equals(tc2)) {
                            Subject sb = new Subject(arrStr[0], tc1, tc2);
                            subjects.add(sb);
                        } else {
                            System.out.println("--Один преподаватель не может одновременно вести и проверять предмет!");
                        }
                    } else {
                        System.out.println("--Преподавателя с именем " + tc2.getName() + " не существует в системе!");
                    }
                } else {
                    if (!teachers.contains(tc2)) {
                        System.out.println("--Преподавателей с именем " + tc1.getName() + " и " + tc2.getName() + " не существует в системе!");
                    } else {
                        System.out.println("--Преподавателя с именем " + tc1.getName() + " не существует в системе!");
                    }
                }
            } else {
                System.out.println("--Предмет с таким названием уже имеется в системе! " +
                        "Что бы изменить ведущего/проверяющего преподавателя удалите существующий предмет и создайте новый");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("--Введённые вами данные не соответствуют формату. Попробуйте ещё раз!");
        }
    }

    public static void removeSubject() {
        System.out.println("--Введите информацию о предмете в формате: \"Название\"");
        String s = sc.nextLine();
        Subject sub = new Subject(s);
        //проверка наличия предмета в системе
        if (subjects.contains(sub)) {
            subjects.remove(sub);
            System.out.println("--Предмет " + sub.getNameOfSubj() + " успешно удалён");
        } else {
            System.out.println("--Предмета " + sub.getNameOfSubj() + " не существует в системе");
        }
    }

    public static void getMarks() {
        for (Subject sub : subjects) {
            System.out.println(sub.getNameOfSubj() + sub.getSubjMarks());
        }
    }

    public static void getStudRating(Student std) {
        //проверка существует ли студент в системе
        if (students.contains(std)) {
            int rating = 0;
            int count = 0;
            for (Subject sub : subjects) {
                //проверка имеются ли у студента оценки
                if (sub.getSubjMarks().get(std) != null) {
                    rating += sub.getSubjMarks().get(std);
                    count++;
                }
            }
            //если оценок нет, то count = 0 и следовательно рейтинг тоже = 0
            if (count != 0) {
                rating = rating / count;
                std.setRating(rating);
                //обновляем студента в системе с новым рейтингом
                students.remove(std);
                students.add(std);
                System.out.format("\n>>>%20s  |  рейтинг = %2d", std.getName(), std.getRating());
            } else {
                System.out.format("\n>>>%20s  |  рейтинг = %2d", std.getName(), 0);
            }
        } else {
            System.out.println("--Студент не найден!");
        }
    }

    public static void getAllStudRating() {
        int i = 0;
        //создаём список имён студентов и по именам перебираем их рейтинг.
        //сделал так в обход ошибки ConcurrentModificationException (Iterator не помог)
        String[] stdNames = new String[students.size()];
        for (Student student : students) {
            stdNames[i] = student.getName();
            i++;
        }
        for (String stdName : stdNames) {
            Student st = new Student(stdName);
            getStudRating(st);
        }
    }

    public static void getTeachRating(Teacher tc) {
        //проверка существует ли преподаватель в системе
        if (teachers.contains(tc)) {
            int rating = 0;
            int count = 0;
            for (Subject sub : subjects) {
                // проверка существуют ли предметы, которые ведёт этот преподаватель
                if (Objects.equals(sub.getLeadTCHR().getName(), tc.getName())) {
                    for (Student st : students) {
                        //проверка имеются ли у студентов оценки по предмету.
                        if (sub.getSubjMarks().get(st) != null) {
                            rating += sub.getSubjMarks().get(st);
                            count++;
                        }
                    }
                }
            }
            //если count = 0 значит у студентов нет оценок и рейтинг = 0.
            if (count != 0) {
                rating = rating / count;
                tc.setRating(rating);
                //обновляем препода в системе с новым рейтингом
                teachers.remove(tc);
                teachers.add(tc);
                System.out.format("\n>>>%20s  |  рейтинг = %2d", tc.getName(), tc.getRating());
            } else {
                System.out.format("\n>>>%20s  |  рейтинг = %2d", tc.getName(), 0);
            }
        } else {
            System.out.println("--Преподаватель не найден!");
        }
    }

    public static void getAllTeachRating() {
        //создаём список имён преподов и по именам перебираем их рейтинг.
        //сделал так в обход ошибки ConcurrentModificationException (Iterator не помог)
        int i = 0;
        String[] teachNames = new String[teachers.size()];
        for (Teacher teacher : teachers) {
            teachNames[i] = teacher.getName();
            i++;
        }
        for (String teachName : teachNames) {
            Teacher tcr = new Teacher(teachName);
            getTeachRating(tcr);
        }
    }

    public static String getTeachList() {
        //метод для получения списка преподавателей в системе
        int i = 0;
        String[] teachNames = new String[teachers.size()];
        for (Teacher teacher : teachers) {
            teachNames[i] = teacher.getName();
            i++;
        }
        return Arrays.toString(teachNames);
    }

    public static String getSubjList() {
        //метод для получения списка предметов в системе
        int i = 0;
        String[] subjNames = new String[subjects.size()];
        for (Subject subject : subjects) {
            subjNames[i] = subject.getNameOfSubj();
            i++;
        }
        return Arrays.toString(subjNames);
    }
}
