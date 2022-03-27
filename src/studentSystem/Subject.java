package studentSystem;

import java.util.*;

public class Subject {

    private String nameOfSubj;
    private Teacher leadTCHR;
    private Teacher checkTCHR;
    private Map<Student, Integer> subjMarks = new HashMap<>();

    public Subject(String nameOfSubj, Teacher leadTCHR, Teacher checkTCHR) {
        this.nameOfSubj = nameOfSubj;
        this.leadTCHR = leadTCHR;
        this.checkTCHR = checkTCHR;
    }

    public Subject(String nameOfSubj) {
        this.nameOfSubj = nameOfSubj;
    }

    public String getNameOfSubj() {
        return nameOfSubj;
    }

    public Teacher getLeadTCHR() {
        return leadTCHR;
    }

    public void setLeadTCHR(Teacher leadTCHR) {
        this.leadTCHR = leadTCHR;
    }

    public Teacher getCheckTCHR() {
        return checkTCHR;
    }

    public void setCheckTCHR(Teacher checkTCHR) {
        this.checkTCHR = checkTCHR;
    }

    public void setSubjMarks(Student st, int mark) {
            subjMarks.put(st, mark);
    }

    public Map<Student, Integer> getSubjMarks() {
        return subjMarks;
    }

    @Override
    public String toString() {
        if ((leadTCHR != null) && (checkTCHR != null)) {
            return nameOfSubj +
                    ": ведёт= " + leadTCHR.getName() +
                    ", проверяет= " + checkTCHR.getName();
        } else {
            return nameOfSubj;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(nameOfSubj, subject.nameOfSubj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfSubj);
    }
}
