package net.dgg.dqc.backservice.entity.parse;

import java.util.List;

/**
 * 查询开庭公告-开庭公告详情查询
 * Created by jiangsh on 2018/6/5 11:29
 */
public class Ktgg {
    private String Province; //省份
    private String Case_Reason; //案由
    private String Schedule_Time; //排期日期
    private String Execute_Gov; //法院
    private String Undertake_Department; //承办部门
    private String Execute_Unite; //法庭
    private String Chief_Judge; //审判长/主审人
    private String Open_Time; //开庭日期
    private String Case_No; //案号

    private List<Prosecutor> prosecutor; //上诉人
    private List<Defendant> defendant; //被上诉人

    public List<Prosecutor> getProsecutor() {
        return prosecutor;
    }

    public void setProsecutor(List<Prosecutor> prosecutor) {
        this.prosecutor = prosecutor;
    }

    public List<Defendant> getDefendant() {
        return defendant;
    }

    public void setDefendant(List<Defendant> defendant) {
        this.defendant = defendant;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCase_Reason() {
        return Case_Reason;
    }

    public void setCase_Reason(String case_Reason) {
        Case_Reason = case_Reason;
    }

    public String getSchedule_Time() {
        return Schedule_Time;
    }

    public void setSchedule_Time(String schedule_Time) {
        Schedule_Time = schedule_Time;
    }

    public String getExecute_Gov() {
        return Execute_Gov;
    }

    public void setExecute_Gov(String execute_Gov) {
        Execute_Gov = execute_Gov;
    }

    public String getUndertake_Department() {
        return Undertake_Department;
    }

    public void setUndertake_Department(String undertake_Department) {
        Undertake_Department = undertake_Department;
    }

    public String getExecute_Unite() {
        return Execute_Unite;
    }

    public void setExecute_Unite(String execute_Unite) {
        Execute_Unite = execute_Unite;
    }

    public String getChief_Judge() {
        return Chief_Judge;
    }

    public void setChief_Judge(String chief_Judge) {
        Chief_Judge = chief_Judge;
    }

    public String getOpen_Time() {
        return Open_Time;
    }

    public void setOpen_Time(String open_Time) {
        Open_Time = open_Time;
    }

    public String getCase_No() {
        return Case_No;
    }

    public void setCase_No(String case_No) {
        Case_No = case_No;
    }

    public static class Prosecutor{
        private String Name; //上诉人
        private String KeyNo; //KeyNo

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getKeyNo() {
            return KeyNo;
        }

        public void setKeyNo(String keyNo) {
            KeyNo = keyNo;
        }
    }

    public static class Defendant{
        private String Name; //被上诉人
        private String KeyNo; //KeyNo

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getKeyNo() {
            return KeyNo;
        }

        public void setKeyNo(String keyNo) {
            KeyNo = keyNo;
        }
    }
}


